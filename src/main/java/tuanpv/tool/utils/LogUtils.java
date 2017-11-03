package tuanpv.tool.utils;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;

public class LogUtils {
	public static void debug(String key, String content) {
		if (Constant.IS_DEBUG)
			System.out.println(String.format(Constant.LOG_STR, key, content));
	}

	public static void logOut(String key, String content) {
		System.out.println(String.format(Constant.LOG_STR, key, content));
	}

	public static void logOut(String key) {
		System.out.println(String.format(Constant.LOG_MAIN, key));
	}

	public static void logOut(String key, ActionInfo info) {
		System.out.println(String.format(Constant.LOG_MAIN_INFO, key, info.value(), info.description()));
	}

	public static void logErr(String key, String content) {
		System.err.println(String.format(Constant.LOG_STR, key, content));
	}

	public static void logOut(Map<String, Object> map) {
		Map<String, Object> treeMap = new TreeMap<String, Object>(map);
		for (String key : treeMap.keySet()) {
			Object object = map.get(key);
			if (object instanceof String[]) {
				String value = StringUtils.join((String[]) object, ", ");
				logOut(key, value);
				continue;
			}

			logOut(key, object.toString());
		}
	}
}
