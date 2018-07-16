package tuanpv.tool.domain;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface ComicParser {
	public Document getDocument(String url) throws Exception;

	public String getPageTitle(Document document, Map<String, Object> map) throws Exception;

	public String getPageContent(Document document, Map<String, Object> map) throws Exception;

	public String getCoverUrl(Document document, Map<String, Object> map) throws Exception;

	public String parseNormal(Element innerHtml);

	public String[] parseArray(Elements innerHtml);

	public String parseContent(Element innerHtml);

	public int getLastPage(Document document, Map<String, Object> map);

	public void processListPage(Document document, Map<String, Object> config, List<Map<String, Object>> list);

	public Map<String, Object> processMainPage(Document document, Map<String, Object> config);
}
