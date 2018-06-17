package homework;

public class ISBN {
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public static String format(String isbn) {
        if (isbn.length() == 10) {
            isbn = String.format("%s-%s-%s-%s", isbn.substring(0, 1), isbn.substring(1, 3), isbn.substring(3, 9), isbn.substring(9, 10));
        } else if (isbn.length() == 13) {
            isbn = String.format("%s-%s-%s-%s-%s", isbn.substring(0, 3), isbn.substring(3, 4), isbn.substring(4, 6), isbn.substring(6, 12), isbn.substring(12, 13));
        }
        return isbn;
    }
}
