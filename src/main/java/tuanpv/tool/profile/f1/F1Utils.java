package tuanpv.tool.profile.f1;

import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.PoiUtils;

public class F1Utils {
	public static String keyOfSubDefine(String key) {
		return AppUtils.keyOfSub(F1Const.SUB_DEFINE, key);
	}

	public static String[] trimSplitter(String input) {
		String[] first = splitter(input);
		String[] result = new String[first.length];
		for (int idx = 0; idx < first.length; idx++)
			result[idx] = first[idx].trim();
		return result;
	}

	public static String[] splitter(String input) {
		return input.split(F1Const.SPLITTER);
	}

	public static String readerKey(String... strings) {
		return AppUtils.join(FilenameUtils.EXTENSION_SEPARATOR_STR, strings);
	}

	public static String loadKey(Map<String, Object> definition, String... strings) {
		String key = AppUtils.join(FilenameUtils.EXTENSION_SEPARATOR_STR, strings);
		if (definition.containsKey(key))
			return definition.get(key).toString();
		return StringUtils.EMPTY;
	}

	public static String[] loadArray(Map<String, Object> definition, String... strings) {
		String input = loadKey(definition, strings);
		if (StringUtils.isBlank(input))
			return new String[] {};
		return splitter(input);
	}

	public static Object parseCellValue(Cell cell) {
		Object object = PoiUtils.parseCellValue(cell);
		if (object instanceof String) {
			String string = object.toString();
			if (string.contains("\n")) {
				String[] strings = string.split("\n");
				return strings;
			}

			if (string.contains(",")) {
				String[] strings = string.split(",");
				return strings;
			}
		}
		return object;
	}
}
