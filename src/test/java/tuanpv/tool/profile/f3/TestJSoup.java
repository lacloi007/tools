package tuanpv.tool.profile.f3;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@SuppressWarnings("unused")
public class TestJSoup {
	private static String PAGE = "http://truyenfull.vn/tien-nghich/";
	private static String CHAPTER = "http://truyenfull.vn/tien-nghich/chuong-1/";

	// public static void main(String[] args) throws IOException {
	// Document document =
	// Jsoup.connect(PAGE).userAgent("Mozilla").cookie("auth",
	// "token").timeout(20000).get();
	// Elements elements = document.select("div.info a[itemprop=author]");
	// if (elements != null && !elements.isEmpty()) {
	// System.out.println(elements.size());
	// for (Element element : elements) {
	// System.out.println(element.text());
	// }
	// } else {
	// System.out.println("Empty");
	// }
	// }
	private static SSLSocketFactory socketFactory() {
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

	public static void main(String[] args) throws IOException {
		String url = "https://truyenfull.vn/than-y-thanh-thu/";
		Document document = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.com").timeout(2000).sslSocketFactory(socketFactory()).get();
		
		Element elem = document.getElementById("nav");
	}
}
