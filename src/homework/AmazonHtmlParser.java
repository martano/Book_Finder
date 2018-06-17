package homework;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.logging.Level;

public class AmazonHtmlParser extends HtmlParser {
    private static final String URL_START = "https://www.amazon.com/gp/search/ref=sr_adv_b/?search-alias=stripbooks&unfiltered=1&field-keywords=&field-author=&field-title=&field-isbn=";

    protected String getUrl(ISBN isbn, String systemLocale) {
        StringBuilder buildUrl = new StringBuilder(URL_START);
        buildUrl.append(isbn.getIsbn());
        return buildUrl.toString();
    }

    protected String checkSearchResults(Document document) {
        String bookUrl = "";
        try {
            Element countMessage = document.getElementById("s-result-count");
            if(countMessage != null) {
                Element bookLink = document.selectFirst("li.s-result-item a");
                bookUrl = bookLink.attr("href");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogWriter loggerW = LogWriter.getInstance();
            loggerW.appLogger.log(Level.WARNING, "Error in AmazonHtmlParser getting book url", e);
        }
        return bookUrl;
    }

    public Book getBookDetails(String bookUrl) {
        Book book = null;
        try {
            Document document = Jsoup.connect(bookUrl).get();
            Element priceElement = document.selectFirst("div#buybox .a-color-price");
            if(priceElement == null) {
                priceElement = document.selectFirst("div.a-box-inner .a-color-price");
            }
            String bookPriceDisp = priceElement.text();
            Double bookPrice = Double.parseDouble(bookPriceDisp.substring(1, 6));

            Element bookTitleElement = document.selectFirst("h1#title");
            String bookTitle = bookTitleElement.text();
            book = new Book(bookUrl, bookTitle, bookPriceDisp, bookPrice);
        } catch (Exception e) {
            e.printStackTrace();

            LogWriter loggerW = LogWriter.getInstance();
            loggerW.appLogger.log(Level.WARNING, "Error in AmazonHtmlParser getting book details", e);
        }
        return book;
    }
}
