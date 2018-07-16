package tuanpv.tool.profile.f4;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tuanpv.tool.profile.f2.F2Const;

@SuppressWarnings("unused")
public class TestDomain {
	private static String PAGE = "http://m.blogtruyen.com/ajax/Chapter/LoadListChapter?id=11607";
	private static String CHAPTER = "http://m.blogtruyen.com/c182102/song-tu-dao-lu-cua-toi-chap-1";

	public static void main(String[] args) throws IOException {
		Document document = Jsoup.connect(CHAPTER).userAgent("Mozilla").cookie("auth", "token").timeout(20000).get();
		Elements elements = document.select("article.chapter-detail div.content > img");
		if (elements != null && !elements.isEmpty()) {
			System.out.println(elements.size());
			for (Element element : elements) {
				System.out.println(element.attr("src"));
			}
		} else {
			System.out.println("Empty");
		}

		elements = document.select("article.chapter-detail select").first().select("option");
		if (elements != null && !elements.isEmpty()) {
			System.out.println(elements.size());
			for (Element element : elements) {
				System.out.println(element.text());
			}
		} else {
			System.out.println("Empty");
		}
	}

	private static void testPage() throws IOException {
		Document document = Jsoup.connect(PAGE).userAgent("Mozilla").cookie("auth", "token").timeout(20000).get();
		Elements elements = document.select("#listChapter > li > div a");
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
