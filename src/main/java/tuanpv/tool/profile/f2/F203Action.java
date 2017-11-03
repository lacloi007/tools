package tuanpv.tool.profile.f2;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;

import tuanpv.common.pool.ThreadPool;
import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.BookParser;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.task.TaskProcessBook;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;
import tuanpv.tool.utils.VelocityUtils;

/**
 * Process the content of book and generate it to HTML, XML
 * 
 * @author TuanPV
 */

@ActionInfo(value = "F203", description = "Process the content of book and generate it to HTML, XML")
public class F203Action implements ProfileAction {
	private static final int TYPE_NORMAL_START_IDX = 2;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {
		String bookCd = map.get(F2Utils.keyOfSubBook(Constant.KEY_CODE)).toString();

		// get the configuration of book domain
		Map<String, Object> config = (Map<String, Object>) map.get(F2Utils.keyOfSubBook(Constant.KEY_CONFIG));

		// get parser from container
		BookParser parser = (BookParser) context.getBean(map.get(F2Utils.keyOfSubBook(F2Const.KEY_PARSER)).toString());

		// get document of main page
		String url = VelocityUtils.generateString(context, F2Const.STR_TEMPLATE_MAIN_URL, F2Utils.bookParams(bookCd));
		Document document = parser.getDocument(url);

		// process main page
		Map<String, Object> book = parser.processMainPage(document, config);

		// create list of Chapter
		List<Map<String, Object>> chapters = new ArrayList<>();

		// check type of book is 1
		String bookType = map.get(F2Utils.keyOfSubBook(Constant.KEY_TYPE)).toString();
		if (StringUtils.equals(F2Const.BOOK_TYPE_NORMAL, bookType)) {

			// process the chapter in main page
			parser.processListPage(document, config, chapters);

			// process last page
			int lastPage = parser.getLastPage(document, config);
			for (int pageIdx = TYPE_NORMAL_START_IDX; pageIdx <= lastPage; pageIdx++) {

				// get document of each page
				url = VelocityUtils.generateString(context, F2Const.STR_TEMPLATE_PAGE_URL,
						F2Utils.bookParams(bookCd, pageIdx));
				document = parser.getDocument(url);

				// process the chapter in main page
				parser.processListPage(document, config, chapters);
			}

			// log processing
			LogUtils.logOut("process", "load " + lastPage + " pages is success!");
		}

		if (chapters.isEmpty() == false) {

			// put list of chapter to book data
			book.put(F2Utils.keyOfSubBook(F2Const.KEY_CHAPTERS), chapters);

			// generate HTML of chapter
			generateChapter(context, parser, map, config, chapters);

			// generate other resource
			generateOther(context, map, book);
		}

		// generate book cover
		generateCover(document, parser, map, config);

		return map;
	}

	/**
	 * @param context
	 * @param parser
	 * @param map
	 * @param config
	 * @param chapters
	 */
	private void generateChapter(ApplicationContext context, BookParser parser, Map<String, Object> map,
			Map<String, Object> config, List<Map<String, Object>> chapters) {

		// create path of HTML
		String htmlPath = AppUtils.pathOfFile(F2Utils.pathOfBook(map), F2Const.PATH_HTML);

		// create and start Thread pool
		ThreadPool pool = new ThreadPool(10);
		for (Map<String, Object> chapter : chapters) {
			TaskProcessBook task = new TaskProcessBook(context, parser, map, config, chapter, htmlPath);
			pool.execute(task);
		}

		// call stop after all of RUNABLE is done
		pool.stop();
	}

	private void generateOther(ApplicationContext context, Map<String, Object> map, Map<String, Object> book)
			throws Exception {
		String[] FILES = { F2Const.VM_HTML_TOC, F2Const.VM_HTML_WELCOME, F2Const.VM_XML_BOOK_NCX, F2Const.VM_BOOK_OPF };

		for (String vm : FILES) {
			String destination = AppUtils.pathOfFile(F2Utils.pathOfBook(map), vm);

			// check destination file is exist
			File destFile = new File(destination);
			if (destFile.exists())
				continue;

			// generate
			VelocityUtils.generateFile(context, map, book, vm, destination);
		}
	}

	private void generateCover(Document document, BookParser parser, Map<String, Object> map,
			Map<String, Object> config) throws Exception {
		String filePath = AppUtils.pathOfFile(F2Utils.pathOfBook(map), F2Const.FILE_COVER);
		File destination = new File(filePath);
		if (destination.exists())
			return;

		String url = parser.getCoverUrl(document, config);
		URL img = new URL(url);
		FileUtils.copyURLToFile(img, destination);
	}
}