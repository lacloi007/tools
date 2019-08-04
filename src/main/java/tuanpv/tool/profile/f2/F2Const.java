package tuanpv.tool.profile.f2;

import java.io.File;

import tuanpv.tool.Constant;
import tuanpv.tool.utils.AppUtils;

public class F2Const {
	public static final int COVER_WIDTH = 600;
	public static final int COVER_HEIGHT = 800;

	public static final int TIMEOUT = 2000;
	public static final String SPLITTER = ",";

	// define String for formatting something
	public static final String FORMAT_CHAPTER_HTML = "chapter-%05d.html";
	public static final String FORMAT_CHAPTER_CODE = "chapter%05d";
	public static final String FORMAT_REPOSITORY_PROPERTIES = "%sRepository.properties";
	public static final String FORMAT_DOMAIN_PROPERTIES = "%s.properties";

	// define Path
	public static final String PATH_HTML = "html";
	public static final String PATH_XML = "xml";
	public static final String PATH_KINDLE = "kindle";
	public static final String PATH_IMAGE = "image";

	// define profile sub
	public static final String SUB_BOOK = "book";
	public static final String SUB_DOMAIN = "domain";
	public static final String SUB_CHAPTER = "chapter";

	// define profile key
	public static final String KEY_PARSER = "parser";
	public static final String KEY_DOMAIN = "domain";
	public static final String KEY_PAGE = "page";
	public static final String KEY_CHAPTERS = "chapters";
	public static final String KEY_UUID = "uuid";
	public static final String KEY_DATE = "date";

	// define book type
	public static final String BOOK_TYPE_NORMAL = "1";

	// define repository code
	public static final String STR_TEMPLATE_MAIN_URL = "book.main.url";
	public static final String STR_TEMPLATE_PAGE_URL = "book.page.url";

	// define book keys
	public static final String BOOK_KEYS = "keys";
	public static final String BOOK_KEYS_TYPES = "keys.types";
	public static final String BOOK_KEYS_SELECTORS = "keys.selectors";

	// define book parse types
	public static final int BOOK_ITEM_PARSE_TYPE_NORMAL = 1;
	public static final int BOOK_ITEM_PARSE_TYPE_CONTENT = 2;
	public static final int BOOK_ITEM_PARSE_TYPE_ARRAY = 3;

	// define book parameter keys
	public static final String[] PARAM_KEYS = { F2Utils.keyOfSubBook(Constant.KEY_CODE),
			F2Utils.keyOfSubBook(F2Const.KEY_PAGE) };

	// define VM
	public static final String VM_HTML_CHAPTER = AppUtils.join(File.separator, PATH_HTML, "chapter.html");
	public static final String VM_HTML_TOC = AppUtils.join(File.separator, PATH_HTML, "toc.html");
	public static final String VM_HTML_WELCOME = AppUtils.join(File.separator, PATH_HTML, "welcome.html");
	public static final String VM_XML_BOOK_NCX = AppUtils.join(File.separator, PATH_XML, "book.ncx");
	public static final String VM_BOOK_OPF = "book.opf";

	// define file
	public static final String FILE_COVER = "cover.jpg";
	public static final String FILE_BOOK_MOBI = "book.mobi";
}
