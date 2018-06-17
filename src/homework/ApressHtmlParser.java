package homework;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.logging.Level;

public class ApressHtmlParser extends HtmlParser{
        private static final String URL_START = "https://www.apress.com";
        private static final String URL_QUERY = "/search?query=";

        protected String getUrl(ISBN isbn, String systemLocale) {
            StringBuilder buildUrl = new StringBuilder(URL_START);
            buildUrl.append("/");
            buildUrl.append(systemLocale);
            buildUrl.append(URL_QUERY);
            buildUrl.append(isbn.getIsbn());
            return buildUrl.toString();
        }

        protected String checkSearchResults(Document document) {
            String bookUrl = "";
            try {
                Element countMessage = document.getElementsByClass("result-count-message").first();
                String str = countMessage.text();
                int resultCount = Integer.parseInt(str.substring(8, 9));
                if (resultCount > 0) {
                    Element bookLink = document.selectFirst("div.result-item > a");
                    bookUrl = URL_START + bookLink.attr("href");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogWriter loggerW = LogWriter.getInstance();
                loggerW.appLogger.log(Level.WARNING, "Error in ApressHtmlParser getting book url", e);
            }
            return bookUrl;
        }

        public Book getBookDetails(String bookUrl) {
            Book book = null;
            try {
                Document document = Jsoup.connect(bookUrl).get();
                Element priceElement = document.selectFirst("span.price");
                String bookPriceDisp = priceElement.text();
                Double bookPrice = Double.parseDouble(bookPriceDisp.substring(1));

                Element bookTitleElement = document.selectFirst("div.bibliographic-information h1");
                String bookTitle = bookTitleElement.text();

                book = new Book(bookUrl, bookTitle, bookPriceDisp, bookPrice);
            } catch (Exception e) {
                e.printStackTrace();
                LogWriter loggerW = LogWriter.getInstance();
                loggerW.appLogger.log(Level.WARNING, "Error in ApressHtmlParser getting book details", e);
            }
            return book;
        }
}
