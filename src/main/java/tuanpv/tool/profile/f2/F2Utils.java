package tuanpv.tool.profile.f2;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
	
	public static SSLSocketFactory socketFactory() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			return sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new RuntimeException("Failed to create a SSL socket factory", e);
		}
	}
}
