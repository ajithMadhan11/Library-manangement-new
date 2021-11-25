import java.util.ArrayList;

public class Book {
    public String bookId;
    public String bookName;
    public String authorName;
    public int available;
    private int stock;

    ArrayList<BorrowSlip> borrowlist = new ArrayList<BorrowSlip>();

    Book(String id, String bName, String aName, int copies) {
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
        if (user.userType != Users.Constants.LIBRARIAN) {
            throw new Exception("You are not authenticated to make changes in stocks!");
        }
        this.stock = stock;
    }

}
