package tuanpv.tool.domain;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;

import tuanpv.common.pool.ThreadPool;

public interface BookParser {
	public Document getDocument(String url) throws Exception;

	public String getPageTitle(Document document, Map<String, Object> map) throws Exception;

	public String getPageContent(Document document, Map<String, Object> map) throws Exception;

	public String parseNormal(Element innerHtml);

	public String[] parseArray(Elements innerHtml);

	public String parseContent(Element innerHtml);

	public int getLastPage(Document document, Map<String, Object> map, Map<String, Object> book);

	public void processListPage(ApplicationContext context, Document document, ThreadPool pool, String path,
			Map<String, Object> map, List<Map<String, Object>> list);
}
