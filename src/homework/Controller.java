package homework;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private Text actiontarget;
    @FXML
    private TextField searchtext;
    @FXML
    private Hyperlink firstlink;
    @FXML
    private Label firstlabel;
    @FXML
    private Label firsttitle;
    @FXML
    private Text firstprice;
    @FXML
    private Hyperlink secondlink;
    @FXML
    private Label secondlabel;
    @FXML
    private Label secondtitle;
    @FXML
    private Text secondprice;


    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        searchBookAction();
    }

    @FXML
    private void onEnter(ActionEvent event) {
        searchBookAction();
    }

    @FXML
    private void handleFirstLinkAction(ActionEvent event) {
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(Main.getInstance());
        hostServices.showDocument(firstlink.getText());
    }

    @FXML
    private void handleSecondLinkAction(ActionEvent event) {
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(Main.getInstance());
        hostServices.showDocument(secondlink.getText());
    }

    private void searchBookAction() {
        clearPreviousSearch();

        String searchText = searchtext.getText();
        if(!searchText.isEmpty()) {
            GoogleBooksQuery query = new GoogleBooksQuery(searchText);
            String isbnStr = query.queryAPI();
            if(!isbnStr.isEmpty()) {
                ISBN isbnObj = new ISBN();
                isbnObj.setIsbn(isbnStr);

                HtmlParser apressParser = new ApressHtmlParser();
                String apressBookUrl = apressParser.executeSearch(isbnObj, "us");

                HtmlParser amazonParser = new AmazonHtmlParser();
                String amazonBookUrl = amazonParser.executeSearch(isbnObj, "us");

                if(!apressBookUrl.isEmpty() && !amazonBookUrl.isEmpty()) {
                    Book apressBook = apressParser.getBookDetails(apressBookUrl);
                    Book amazonBook = amazonParser.getBookDetails(amazonBookUrl);
                    if(apressBook != null && amazonBook != null) {
                        if (apressBook.getBookPriceMath().compareTo(amazonBook.getBookPriceMath()) < 0) {
                            //display appress first
                            displayBooksInfo("Apress Shop", apressBook.getBookTitle(), apressBookUrl,
                                             apressBook.getBookPriceDisp(),"Amazon Shop", amazonBook.getBookTitle(),
                                             amazonBookUrl, amazonBook.getBookPriceDisp());
                        } else {
                            //display amazon first
                            displayBooksInfo("Amazon Shop", amazonBook.getBookTitle(), amazonBookUrl,
                                             amazonBook.getBookPriceDisp(),"Apress Shop", apressBook.getBookTitle(),
                                             apressBookUrl, apressBook.getBookPriceDisp());
                        }
                    } else if (apressBook != null) {
                        displayBooksInfo("Apress Shop", apressBook.getBookTitle(), apressBookUrl,
                                         apressBook.getBookPriceDisp(),"Amazon non standard page", "", "", "");
                    } else {
                        actiontarget.setText("No search results for: " + searchText);
                    }
                } else if(!apressBookUrl.isEmpty()) {
                    Book apressBook = apressParser.getBookDetails(apressBookUrl);
                    if(apressBook != null) {
                        displayBooksInfo("Apress Shop", apressBook.getBookTitle(), apressBookUrl,
                                apressBook.getBookPriceDisp(), "","", "", "");
                    } else {  actiontarget.setText("No search results for: " + searchText); }
                } else if (!amazonBookUrl.isEmpty()) {
                    Book amazonBook = amazonParser.getBookDetails(amazonBookUrl);
                    if(amazonBook != null) {
                        displayBooksInfo("Amazon Shop", amazonBook.getBookTitle(), amazonBookUrl,
                                amazonBook.getBookPriceDisp(), "","", "", "");
                    } else {  actiontarget.setText("No search results for: " + searchText); }
                } else {
                    actiontarget.setText("No search results for: " + searchText);
                }

            } else {
                actiontarget.setText("No search results for: " + searchText);
            }
        }
        searchtext.clear();
    }


    private void displayBooksInfo(String fLabel, String fTitle, String fLink, String fPrice, String sLabel, String sTitle, String sLink, String sPrice) {
        firstlabel.setText(fLabel);
        firsttitle.setText(fTitle);
        firstlink.setText(fLink);
        firstprice.setText(fPrice);
        secondlabel.setText(sLabel);
        secondtitle.setText(sTitle);
        secondlink.setText(sLink);
        secondprice.setText(sPrice);
    }

    private void clearPreviousSearch() {
        firstlabel.setText("");
        firsttitle.setText("");
        firstlink.setText("");
        firstprice.setText("");
        secondlabel.setText("");
        secondtitle.setText("");
        secondlink.setText("");
        secondprice. setText("");
        actiontarget.setText("");
    }


}
