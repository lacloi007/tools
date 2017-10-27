package tuanpv.tool;

public class Constant {

	// define file type
	public static final String FILE_TYPE_XLS = "xls";
	public static final String FILE_TYPE_XLSX = "xlsx";

	// define file name
	public static final String FILE_APPLICATION_CONTEXT = "applicationContext.xml";
	public static final String FILE_OPTIONS = "options.xml";
	public static final String FILE_PROFILE = "profiles.xml";

	// OPTIONS.XML node and attribute name
	public static final String OPTION_NAME = "option";
	public static final String OPTION_ATTR_OPT = "opt";
	public static final String OPTION_ATTR_LONG_OPT = "longOpt";
	public static final String OPTION_ATTR_REQUIRED = "required";
	public static final String OPTION_ATTR_DESCRIPTION = "description";
	public static final String OPTION_ATTR_HAS_ARGUMENT = "hasArg";

	// PROFILE.XML node and attribute name
	public static final String PROFILE_ACTION_ATTR_BEAN = "bean";
	public static final String PROFILE_ACTION_ATTR_CLASS = "class";
	public static final String PROFILE_NAME = "profile";
	public static final String PROFILE_ATTR_ID = "id";
	public static final String PROFILE_ACTION_NAME = "action";

	// define SUB
	public static final String SUB_SPLITTER = "-";
	public static final String SUB_ARGS = "args";
	public static final String SUB_ACTION = "action";

	// define KEY
	public static final String KEY_TYPE = "type";
	public static final String KEY_OUTPUT = "output";
	public static final String KEY_EXTENSION = "ext";

	// define PATH
	public static final String PATH_CONFIG = "config";
	public static final String PATH_DATA = "data";
	public static final String PATH_STRUCTURE = "structure";
	public static final String PATH_TEMPLATE = "template";

	// define LOG
	public static final String LOG_STR = "| > %-18s : %s";
	public static final String LOG_MAIN = "- %-20s : -----------";
	public static final String LOG_SUBJECT = "--- %10s %s ---------------";
}
