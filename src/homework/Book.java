package homework;

import java.math.BigDecimal;

public class Book {

    private String url;
    private String bookTitle;
    private String bookPriceDisp;
    private double bookPrice;

    public Book(String url, String bookTitle, String bookPriceDisp, double bookPrice) {
        this.url = url;
        this.bookTitle = bookTitle;
        this.bookPriceDisp =bookPriceDisp;
        this.bookPrice = bookPrice;
    }

    public double getBookPrice() {
        return bookPrice;
    }
    public String getBookTitle() { return bookTitle; }
    public String getBookPriceDisp() { return bookPriceDisp; }

    public BigDecimal getBookPriceMath() {
        return new BigDecimal(bookPrice);
    }

}
