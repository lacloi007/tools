package tuanpv.tool.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import tuanpv.tool.Constant;

public class AppUtils {
	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String today() {
		Date today = new Date();
		return String.format("%tY-%tm-%td", today, today, today);
	}

	public static Object getClassByName(String className) {
		try {
			Class<?> clazz = ClassUtils.getClass(className);
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, String> parseArguments(String[] args) throws Exception {
		Map<String, String> map = new HashMap<>();

		// get Options
		Options options = XmlUtils.parseOptions(Constant.FILE_OPTIONS);
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException exception) {
			System.out.println(exception.getMessage());
			formatter.printHelp("utility-name", options);
			throw exception;
		}

		for (Option option : options.getOptions())
			map.put(keyOfSubArgs(option.getLongOpt()), cmd.getOptionValue(option.getOpt()));

		return map;
	}

	public static String join(String spliter, String... strings) {
		return StringUtils.join(strings, spliter);
	}

	public static String pathOfFileConfig(String fileName) {
		return pathOfFile(Constant.PATH_CONFIG, fileName);
	}

	public static String pathOfFile(String... paths) {
		return join(File.separator, paths);
	}

	public static String keyOfSub(String parent, String key) {
		return join(Constant.SUB_SPLITTER, parent, key);
	}

	public static String keyOfSubArgs(String key) {
		return keyOfSub(Constant.SUB_ARGS, key);
	}

	public static String keyOfSubAction(String key) {
		return keyOfSub(Constant.SUB_ACTION, key);
	}

	public static String keyOfSubData(String key) {
		return keyOfSub(Constant.SUB_DATA, key);
	}

	public static String keyOfSubMsg(String key) {
		return keyOfSub(Constant.SUB_MESSAGE, key);
	}

	public static String pathOfStructure(Map<String, Object> map) {
		return join(File.separator, Constant.PATH_DATA, Constant.PATH_STRUCTURE,
				map.get(keyOfSubArgs(Constant.KEY_TYPE)).toString());
	}

	public static String pathOfTemplate(Map<String, Object> map) {
		return join(File.separator, Constant.PATH_DATA, Constant.PATH_TEMPLATE,
				map.get(keyOfSubArgs(Constant.KEY_TYPE)).toString());
	}

	public static String pathOfEtc(Map<String, Object> map) {
		return join(File.separator, Constant.PATH_DATA, Constant.PATH_ETC,
				map.get(keyOfSubArgs(Constant.KEY_TYPE)).toString());
	}

	public static String pathOfEtcFile(Map<String, Object> map, String filePath) {
		return join(File.separator, Constant.PATH_DATA, Constant.PATH_ETC,
				map.get(keyOfSubArgs(Constant.KEY_TYPE)).toString(), filePath);
	}

	public static String pathOfEtcFile(Map<String, Object> map, String fileName, String fileExtension) {
		String fullFileName = join(FilenameUtils.EXTENSION_SEPARATOR_STR, fileName, fileExtension);
		return pathOfEtcFile(map, fullFileName);
	}

	public static String pathOfExternal() {
		return join(File.separator, Constant.PATH_DATA, Constant.PATH_EXTERNAL);
	}
}
