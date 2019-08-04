package tuanpv.tool.profile.f2;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component(value = "P202")
public class P202Parser extends P201Parser {
	@Override
	public int getLastPage(Document document, Map<String, Object> config) {
		String selector = config.get(SELECTOR_LAST_PAGE).toString();
		return Integer.parseInt(document.select(selector).first().attr("value"));
	}

	@Override
	public String parseContent(Element element) {
		return replacer(element.html());
	}

	public static String replacer(String input) {
		StringBuilder sb = new StringBuilder();
		String[] list = input.replace("\n", "").split("<br><br>");
		for (int idx = 0; idx < list.length; idx++) {
			sb.append(DEFAULT_TAB + "<p>" + list[idx].replaceAll("<[^>]*>", "") + "</p>");
			if (idx != list.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public Document getDocument(String url) throws Exception {
		enableSSLSocket();

		while (true) {
			try {
				Connection.Response response = Jsoup.connect(url)
						.userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
						.method(Connection.Method.GET).execute();
				if (response.statusCode() == 200)
					return response.parse();

				throw new Exception();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new X509TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} }, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}

	@Override
	public String getPageContent(Document document, Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		String content = document.select(map.get(SELECTOR_CHAPTER_CONTENT).toString()).first().html();
		String[] list = content.replace("\n", "").replace("&nbsp;", "").split("<br><br>");
		for (int idx = 0; idx < list.length; idx++) {
			sb.append(DEFAULT_TAB + "<p>" + list[idx].replaceAll("<[^>]*>", "").replace("-->", "").trim() + "</p>");
			if (idx != list.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}
}
