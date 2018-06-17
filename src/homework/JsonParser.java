package homework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public String parseGoogleApi(String input) {
        String strIsbnFormatted = "";

        try {
            JSONObject obj = new JSONObject(input);
            JSONArray arr = obj.getJSONArray("items");

            JSONObject volumeInfo = arr.getJSONObject(0).getJSONObject("volumeInfo");
            JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
            JSONObject isbn;
            if (identifiers.getJSONObject(0).get("type").equals("ISBN_13")) {
                isbn = identifiers.getJSONObject(0);
            } else {
                isbn = identifiers.getJSONObject(1);
            }

            strIsbnFormatted = ISBN.format(isbn.getString("identifier"));

        } catch (JSONException e) {
            System.out.println(input.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return strIsbnFormatted;
    }
}
