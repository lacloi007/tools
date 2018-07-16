package tuanpv.tool.profile.f2;

import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component(value = "P202")
public class P202Parser extends P201Parser {
	@Override
	public int getLastPage(Document document, Map<String, Object> config) {
		String selector = config.get(SELECTOR_LAST_PAGE).toString();
		return Integer.parseInt(document.select(selector).first().attr("value"));
	}

	@Override
	public String parseContent(Element element) {
		return replacer(element.html());
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
	public String getPageContent(Document document, Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		String content = document.select(map.get(SELECTOR_CHAPTER_CONTENT).toString()).first().html();
		String[] list = content.replace("\n", "").replace("&nbsp;", "").split("<br><br>");
		for (int idx = 0; idx < list.length; idx++) {
			sb.append(DEFAULT_TAB + "<p>" + list[idx].replaceAll("<[^>]*>", "").replace("-->", "").trim() + "</p>");
			if (idx != list.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}
}
