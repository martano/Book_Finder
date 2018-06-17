package homework;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class HtmlParser {

    public String executeSearch(ISBN isbn, String systemLocale) {
        String bookUrl = "";
        try {
            String searchUrl = getUrl(isbn, systemLocale);

            Document document = Jsoup.connect(searchUrl).get();
            bookUrl = checkSearchResults(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bookUrl;
    }

    abstract protected String getUrl(ISBN isbn, String systemLocale);
    abstract protected String checkSearchResults(Document document);
    abstract public Book getBookDetails(String bookUrl);
}
