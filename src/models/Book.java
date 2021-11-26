package src.models;

import java.util.ArrayList;

import src.CONSTANTS;

/**
 * This model represents the book details, where available is the number of
 * books available in the library and stock is the total number of stock of that
 * book initially present.
 */
public class Book {
    public String bookId;
    public String bookName;
    public String authorName;
    public int available;
    private int stock;

    // This arrayList contains the entire details
    public ArrayList<BorrowSlip> borrowlist = new ArrayList<BorrowSlip>();

    public Book(String id, String bName, String aName, int copies) {
        this.bookId = id;
        this.bookName = bName;
        this.authorName = aName;
        stock = copies;
        available = copies;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock, Users user) throws Exception {
        if (user.userType != CONSTANTS.LIBRARIAN) {
            throw new Exception("You are not authenticated to make changes in stocks!");
        }
        this.stock = stock;
    }

}
