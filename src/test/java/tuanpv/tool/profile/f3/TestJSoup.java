package tuanpv.tool.profile.f3;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("unused")
public class TestJSoup {
	private static String PAGE = "http://truyenfull.vn/tien-nghich/";
	private static String CHAPTER = "http://truyenfull.vn/tien-nghich/chuong-1/";

	public static void main(String[] args) throws IOException {
		Document document = Jsoup.connect(PAGE).userAgent("Mozilla").cookie("auth", "token").timeout(20000).get();
		Elements elements = document.select("div.info a[itemprop=author]");
		if (elements != null && !elements.isEmpty()) {
			System.out.println(elements.size());
			for (Element element : elements) {
				System.out.println(element.text());
			}
		} else {
			System.out.println("Empty");
		}
	}
}
