package tuanpv.tool.task;

import java.io.File;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.BookParser;
import tuanpv.tool.profile.f2.F2Const;
import tuanpv.tool.profile.f2.F2Utils;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.VelocityUtils;

public class TaskProcessBook implements Runnable {
	private String path;
	private Map<String, Object> chapter, config, map;
	private ApplicationContext context;
	private BookParser parser;

	public TaskProcessBook(ApplicationContext context, BookParser parser, Map<String, Object> map,
			Map<String, Object> config, Map<String, Object> chapter, String path) {
		this.path = path;
		this.chapter = chapter;
		this.config = config;
		this.map = map;
		this.parser = parser;
		this.context = context;
	}

	@Override
	public void run() {
		try {
			String filePath = AppUtils.join(File.separator, path,
					chapter.get(F2Utils.keyOfSubChapter(Constant.KEY_HTML)).toString());
			File destination = new File(filePath);
			if (destination.exists())
				return;

			// create document
			String url = chapter.get(F2Utils.keyOfSubChapter(Constant.KEY_URL)).toString();
			Document document = parser.getDocument(url);

			// parse title
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_TITLE), parser.getPageTitle(document, config));

			// parse content
			String content = parser.getPageContent(document, config);
			chapter.put(F2Utils.keyOfSubChapter(Constant.KEY_CONTENT), content);
			if (content.length() < 100)
				System.err.println(String.format(Constant.LOG_STR, "SHR-URL", url));

			// generate HTML
			VelocityUtils.generateFile(context, map, chapter, F2Const.VM_HTML_CHAPTER, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}