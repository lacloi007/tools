package tuanpv.tool.profile.f3;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("unused")
public class TestTF {
	private static String PAGE = "http://truyenfull.vn/tien-nghich/";
	private static String CHAPTER = "https://truyenfull.vn/song-cung-van-tue/chuong-829/";

	public static void main(String[] args) throws IOException {
		Document document = Jsoup.connect(CHAPTER).userAgent("Mozilla").cookie("auth", "token").timeout(20000).get();
	}
}
