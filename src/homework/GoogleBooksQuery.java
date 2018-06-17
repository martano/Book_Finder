package homework;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class GoogleBooksQuery {
    private static final String URL_START = "https://www.googleapis.com/books/v1/volumes";
    private static final String PARAM_INTITLE = "intitle:";
    private String queryByTitle;

    public GoogleBooksQuery(String query) {
        queryByTitle = query;
    }

    public String queryAPI() {
        String isbn = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        try {
            HttpUriRequest httpURI = buildURL();

            httpClient = HttpClients.createDefault();
            response = httpClient.execute(httpURI);

            JsonParser parser = new JsonParser();
            isbn = parser.parseGoogleApi(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            LogWriter loggerW = LogWriter.getInstance();
            loggerW.appLogger.log(Level.WARNING, "Error in GoogleBooksQuery getting isbn", e);
        } finally {
            try {
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isbn;
    }


    public HttpUriRequest buildURL() throws URISyntaxException {
        String[] wordsInTitle = queryByTitle.split("\\s+");
        StringBuilder urlParamBuilder = new StringBuilder("");
        for (int i=0 ; i < wordsInTitle.length ; i++) {
            urlParamBuilder.append(PARAM_INTITLE);
            urlParamBuilder.append(wordsInTitle[i]);
            urlParamBuilder.append(" ");
        }
        String urlParameters = urlParamBuilder.toString();

        HttpUriRequest httpURI = RequestBuilder.get()
                .setUri(new URI(URL_START))
                .addParameter("q", urlParameters )
                .build();

        return httpURI;
    }
}
