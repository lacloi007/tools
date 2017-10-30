package tuanpv.tool.task;

import java.io.File;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.jsoup.nodes.Document;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.BookParser;


public class TaskProcessBook implements Runnable {
	private final static String TEMPLATE = "html/chapter.html";
	private String path;
	private Map<String, Object> data;
	private VelocityEngine engine;
	private BookParser parser;

	public TaskProcessBook(VelocityEngine engine, String path, Map<String, Object> data, BookParser parser) {
		this.engine = engine;
		this.path = path;
		this.data = data;
		this.parser = parser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			Map<String, Object> chapter = (Map<String, Object>) data.get(Constant.KEY_CHAPTER);
			String filePath = this.path + File.separator + chapter.get(Constant.KEY_HTML);
			File destination = new File(filePath);
			if (destination.exists())
				return;

			// create document
			String url = chapter.get(Constant.KEY_URL).toString();
			Document document = parser.getDocument(url);

			// parse title
			chapter.put(Constant.KEY_TITLE, parser.getPageTitle(document, data));

			// parse content
			String content = parser.getPageContent(document, data);
			chapter.put(Constant.KEY_CONTENT, content);
			if (content.length() < 2000)
				System.err.println(String.format(Constant.LOG_STR, "SHR-URL", url));

			// apply new data of chapter
			data.put(Constant.KEY_CHAPTER, chapter);

			// generate HTML
			//BookUtils.generateHtml(engine, data, TEMPLATE, filePath);

			// Log content
			System.out.println(String.format(Constant.LOG_STR, "HTML", filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}