package tuanpv.tool.profile.f2;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component(value = "P203")
public class P203Parser extends P202Parser {
    @Override
    public String getPageContent(Document document, Map<String, Object> map) throws Exception {
        String content = document.select(map.get(SELECTOR_CHAPTER_CONTENT).toString()).first().html();
        return content.replace("\n", "").replace("&nbsp;", "");
    }
}
