package tuanpv.tool.utils;

import java.util.Map;
import java.util.TreeMap;

import tuanpv.tool.Constant;

public class LogUtils {
	public static void logOut(String key, String content) {
		System.out.println(String.format(Constant.LOG_STR, key, content));
	}

	public static void logOut(String key) {
		System.out.println(String.format(Constant.LOG_MAIN, key));
	}

	public static void logErr(String key, String content) {
		System.err.println(String.format(Constant.LOG_STR, key, content));
	}

	public static void logOut(Map<String, Object> map) {
		Map<String, Object> treeMap = new TreeMap<String, Object>(map);
		for (String key : treeMap.keySet()) {
			logOut(key, map.get(key).toString());
		}
	}
}
