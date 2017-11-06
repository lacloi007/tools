package tuanpv.tool.profile.f2;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.nodes.Element;

import tuanpv.tool.Constant;
import tuanpv.tool.utils.AppUtils;

public class F2Utils {
	public static String nameOfRepository(String domain) {
		return String.format(F2Const.FORMAT_REPOSITORY_PROPERTIES, domain);
	}

	public static String nameOfDomain(String domain) {
		return String.format(F2Const.FORMAT_DOMAIN_PROPERTIES, domain);
	}

	public static String keyOfSubBook(String key) {
		return AppUtils.keyOfSub(F2Const.SUB_BOOK, key);
	}

	public static String keyOfSubDomain(String key) {
		return AppUtils.keyOfSub(F2Const.SUB_DOMAIN, key);
	}

	public static String keyOfSubChapter(String key) {
		return AppUtils.keyOfSub(F2Const.SUB_CHAPTER, key);
	}

	public static String chapterHtml(int id) {
		return String.format(F2Const.FORMAT_CHAPTER_HTML, id);
	}

	public static String chapterCode(int id) {
		return String.format(F2Const.FORMAT_CHAPTER_CODE, id);
	}

	public static String linkHref(Element link) {
		return link.attr("href");
	}

	public static String linkTitle(Element link) {
		return link.attr("title").trim();
	}

	public static String pathOfBook(Map<String, Object> map) {
		return AppUtils.join(File.separator, map.get(AppUtils.keyOfSubArgs(Constant.KEY_OUTPUT)).toString(),
				map.get(keyOfSubBook(Constant.KEY_CODE)).toString());
	}

	public static String pathOfKindle(Map<String, Object> map) {
		return AppUtils.join(File.separator, map.get(AppUtils.keyOfSubArgs(Constant.KEY_OUTPUT)).toString(),
				F2Const.PATH_KINDLE);
	}

	public static Map<String, Object> bookParams(Object... strings) {
		Map<String, Object> result = new TreeMap<>();

		for (int idx = 0; idx < strings.length; idx++)
			result.put(F2Const.PARAM_KEYS[idx], strings[idx]);

		return result;
	}
}
