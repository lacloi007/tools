package tuanpv.tool.profile.f1;

import tuanpv.tool.Constant;

public class F1Const {

	// define sheet name
	public static final String SHEET_MAIN = "main";
	public static final String SHEET_MESSAGE = "message";
	public static final String SHEET_SCREEN_FORMATTER = "screen-%4d";

	// define sheet keys
	public static final String[][] SHEET_MAIN_KEYS = { { Constant.KEY_PACKAGE, "F2" }, { Constant.KEY_PROJECT, "F3" },
			{ Constant.KEY_APP, "F4" } };

	public static final String[][] SHEET_MESSAGE_KEYS = { { Constant.KEY_TRANSLATE, "D1" } };
	public static final int[] SHEET_MESSAGE_DATA_START = { 3, 1 };
	public static final String[][] SHEET_MESSAGE_DATA_KEYS = { { Constant.KEY_TYPE, "2" }, { Constant.KEY_ID, "3" },
			{ Constant.KEY_ENGLISH, "4" }, { Constant.KEY_JAPAN, "5" }, { Constant.KEY_CHINA, "6" } };
}
