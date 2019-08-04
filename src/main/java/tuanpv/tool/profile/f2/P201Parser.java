package tuanpv.tool.profile.f2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.BookParser;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.RegexUtils;

@Component(value = "P201")
public class P201Parser implements BookParser {
	public static final String DEFAULT_TAB = "\t\t\t";
	public static final String SELECTOR_LAST_PAGE = "selector.last";
	public static final String SELECTOR_LAST_LIST = "selector.last.list";
	public static final String SELECTOR_CHAPTER_URL = "selector.chapter.url";
	public static final String SELECTOR_CHAPTER_TITLE = "selector.chapter.title";
	public static final String SELECTOR_CHAPTER_CONTENT = "selector.chapter.content";
	public static final String SELECTOR_COVER = "selector.cover";
	public static final String REGEX_LAST_PAGE = "regex.last";

	@Override
	public Document getDocument(String url) throws Exception {
		if (url.contains("https:"))
			return Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com").timeout(F2Const.TIMEOUT)
					.sslSocketFactory(F2Utils.socketFactory()).get();
		return Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").timeout(F2Const.TIMEOUT).get();
	}

	@Override
	public String getPageTitle(Document document, Map<String, Object> map) throws Exception {
		return parseNormal(document.select(map.get(SELECTOR_CHAPTER_TITLE).toString()).first());
	}

	@Override
	public String getPageContent(Document document, Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		String content = document.select(map.get(SELECTOR_CHAPTER_CONTENT).toString()).first().html();
		String[] list = content.replace("\n", "").split("<br><br>");
		for (int idx = 0; idx < list.length; idx++) {
			sb.append(DEFAULT_TAB + "<p>" + list[idx].replaceAll("<[^>]*>", "").replace("-->", "").trim() + "</p>");
			if (idx != list.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}

	public static String replacer(String input) {
		StringBuilder sb = new StringBuilder();
		String[] list = input.replace("\n", "").split("<br><br>");
		for (int idx = 0; idx < list.length; idx++) {
			sb.append(DEFAULT_TAB + "<p>" + list[idx].replaceAll("<[^>]*>", "") + "</p>");
			if (idx != list.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String parseNormal(Element element) {
		return element.html();
	}

	@Override
	public String[] parseArray(Elements elements) {
		String[] result = new String[elements.size()];
		for (int idx = 0; idx < elements.size(); idx++) {
			result[idx] = elements.get(idx).html();
		}
		return result;
	}

	@Override
	public String parseContent(Element element) {
		return replacer(element.html());
	}

	@Override
	public int getLastPage(Document document, Map<String, Object> config) {
		String regex = config.get(REGEX_LAST_PAGE).toString();
		String selector = config.get(SELECTOR_LAST_PAGE).toString();
		String selectorList = config.get(SELECTOR_LAST_LIST).toString();

		Elements elements = document.select(selector);
		Element link = null;
		if (elements.isEmpty()) {
			elements = document.select(selectorList);
			link = elements.last();
		} else
			link = elements.first();

		String data = link.attr("href");
		return RegexUtils.parseInt(regex, data);
	}

	@Override
	public void processListPage(Document document, Map<String, Object> config, List<Map<String, Object>> list) {

		// get the list of link
		Elements links = document.select(config.get(SELECTOR_CHAPTER_URL).toString());

		// String Regex
		String regex = config.get(REGEX_LAST_PAGE).toString();

		// get the current index of list chapter
		int index = list.size();
		for (Element link : links) {
			int id = RegexUtils.parseInt(regex, link.html());
			Map<String, Object> chapter = new HashMap<>();
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_INDEX), index++);
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_ID), id);
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_URL), F2Utils.linkHref(link));
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_NAME), F2Utils.linkTitle(link));
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_HTML), F2Utils.chapterHtml(index));
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_CODE), F2Utils.chapterCode(index));

			// add to list
			list.add(chapter);
		}
	}

	@Override
	public Map<String, Object> processMainPage(Document document, Map<String, Object> config) {
		Map<String, Object> data = new TreeMap<>();

		// load configuration
		String[] keys = config.get(F2Const.BOOK_KEYS).toString().split(F2Const.SPLITTER);
		String[] types = config.get(F2Const.BOOK_KEYS_TYPES).toString().split(F2Const.SPLITTER);
		String[] selectors = config.get(F2Const.BOOK_KEYS_SELECTORS).toString().split(F2Const.SPLITTER);

		// process each of item base on type
		for (int idx = 0; idx < keys.length; idx++) {
			int type = Integer.parseInt(types[idx]);
			switch (type) {
			case F2Const.BOOK_ITEM_PARSE_TYPE_NORMAL:
				data.put(F2Utils.keyOfSubBook(keys[idx]),
						parseNormal(document.select(config.get(selectors[idx]).toString()).first()));
				break;

			case F2Const.BOOK_ITEM_PARSE_TYPE_CONTENT:
				data.put(F2Utils.keyOfSubBook(keys[idx]),
						parseContent(document.select(config.get(selectors[idx]).toString()).first()));
				break;

			case F2Const.BOOK_ITEM_PARSE_TYPE_ARRAY:
				data.put(F2Utils.keyOfSubBook(keys[idx]),
						parseArray(document.select(config.get(selectors[idx]).toString())));
				break;

			default:
				break;
			}
		}

		// add more information
		data.put(F2Utils.keyOfSubBook(F2Const.KEY_UUID), AppUtils.uuid());
		data.put(F2Utils.keyOfSubBook(F2Const.KEY_DATE), AppUtils.today());

		return data;
	}

	@Override
	public String getCoverUrl(Document document, Map<String, Object> map) throws Exception {
		return document.select(map.get(SELECTOR_COVER).toString()).first().attr("src");
	}
}
