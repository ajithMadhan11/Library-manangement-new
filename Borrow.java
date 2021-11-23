import java.time.LocalDate;

public class Borrow {
    String bookId;
    LocalDate issueDate;
    LocalDate dueDate;

    Borrow(String bookId, LocalDate issueDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

}
