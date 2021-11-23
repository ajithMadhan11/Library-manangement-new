
public class Book {
    public String bookId;
    public String bookName;
    public String authorName;
    public int available;

    Book(String id, String bName, String aName, int copies) {
        this.bookId = id;
        this.bookName = bName;
        this.authorName = aName;
        available = copies;
    }
}
