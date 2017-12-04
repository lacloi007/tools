package tuanpv.tool.profile.f1;

import tuanpv.tool.Constant;

public class F1Const {
	public static final String SPLITTER = ",";
	
	// define file
	public static final String FILE_DEFINITION = "definition.properties";

	// define file
	public static final String SUB_DEFINE = "def";

	// define file
	public static final String KEY_LOADER = "loader";
	public static final String KEY_DATA = "data";
	public static final String KEY_DEFINE = "definition";
	public static final String KEY_CELL = "cell";
	public static final String KEY_ROW = "row";
	public static final String KEY_SUB = "sub";
	public static final String KEY_FORMAT = "format";
	public static final String KEY_LIST = "list";
	public static final String KEY_COLS = "cols";
	public static final String KEY_ITEMS = "items";
	public static final String KEY_NAMES = "names";
	public static final String KEY_SHEET = "sheet";

	// define sheet name
	public static final String SHEET_MAIN = "main";
	public static final String SHEET_MESSAGE = "message";
	public static final String SHEET_SCREEN_FORMATTER = "screen-%4d";

	// define sheet for main keys
	public static final String[][] SHEET_MAIN_KEYS = { { Constant.KEY_PACKAGE, "F2" }, { Constant.KEY_PROJECT, "F3" },
			{ Constant.KEY_APP, "F4" } };

	// define sheet for message keys
	public static final String[][] SHEET_MESSAGE_KEYS = { { Constant.KEY_TRANSLATE, "D1" } };

	// define sheet for message data keys
	public static final int[] SHEET_MESSAGE_DATA_START = { 3, 1 };
	public static final String[][] SHEET_MESSAGE_DATA_KEYS = { { Constant.KEY_TYPE, "2" }, { Constant.KEY_ID, "3" },
			{ Constant.KEY_ENGLISH, "4" }, { Constant.KEY_JAPAN, "5" }, { Constant.KEY_CHINA, "6" } };
}
