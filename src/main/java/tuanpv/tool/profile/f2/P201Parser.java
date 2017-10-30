package tuanpv.tool.profile.f2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import tuanpv.common.pool.ThreadPool;
import tuanpv.tool.Constant;
import tuanpv.tool.domain.BookParser;
import tuanpv.tool.task.TaskProcessBook;
import tuanpv.tool.utils.RegexUtils;

@Component(value = "P201")
public class P201Parser implements BookParser {

	private static final String DEFAULT_TAB = "\t\t\t";
	private static final String SELECTOR_LAST_PAGE = "book.selector.lastPage";
	private static final String SELECTOR_CHAPTER_URL = "book.selector.chapter.url";
	private static final String SELECTOR_CHAPTER_TITLE = "book.selector.chapter.title";
	private static final String SELECTOR_CHAPTER_CONTENT = "book.selector.chapter.content";

	@Override
	public Document getDocument(String url) throws Exception {
		return Jsoup.connect(url).userAgent("Mozilla").cookie("auth", "token").timeout(F2Const.TIMEOUT).get();
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
	public int getLastPage(Document document, Map<String, Object> map, Map<String, Object> book) {
		int lastPage = RegexUtils
				.parseInt(document.select(map.get(SELECTOR_LAST_PAGE).toString()).first().attr("href"));

		book.put("last", lastPage);
		return lastPage;
	}

	@Override
	public void processListPage(ApplicationContext context, Document document, ThreadPool pool, String path,
			Map<String, Object> map, List<Map<String, Object>> list) {
		Elements links = document.select(map.get(SELECTOR_CHAPTER_URL).toString());
		int index = list.size();
		for (Element link : links) {
			int id = F2Utils.linkId(link);
			Map<String, Object> chapter = new HashMap<>();
			chapter.put(Constant.KEY_INDEX, index++);
			chapter.put(Constant.KEY_ID, id);
			chapter.put(Constant.KEY_URL, F2Utils.linkHref(link));
			chapter.put(Constant.KEY_NAME, F2Utils.linkTitle(link));
			chapter.put(Constant.KEY_HTML, F2Utils.chapterHtml(index));
			chapter.put(Constant.KEY_CODE, F2Utils.chapterCode(index));

			// add to list
			list.add(chapter);

			// create data mapping
			Map<String, Object> data = new HashMap<>(map);
			data.put(Constant.KEY_CHAPTER, chapter);

			// add to list
			TaskProcessBook task = new TaskProcessBook(context.getBean(VelocityEngine.class), path, data, this);
			pool.execute(task);
		}
	}

}
