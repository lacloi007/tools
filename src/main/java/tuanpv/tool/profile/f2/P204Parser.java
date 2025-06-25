package tuanpv.tool.profile.f2;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "P204")
public class P204Parser extends P202Parser {
    @Override
    public String getPageContent(Document document, Map<String, Object> map) throws Exception {
        String content = document.select(map.get(SELECTOR_CHAPTER_CONTENT).toString()).first().html();
        return content.replace("\n", "").replace("&nbsp;", "");
    }

    @Override
    public Document getDocument(String url) throws Exception {
        while (true) {
            try {
                Connection.Response response = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
                        .method(Connection.Method.GET).execute();
                if (response.statusCode() == 200 || response.statusCode() == 304)
                    return response.parse();

                throw new Exception();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

    }
}
