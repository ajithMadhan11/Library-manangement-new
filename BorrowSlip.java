import java.time.LocalDate;

public class BorrowSlip {
    String bookId;
    String userId;
    LocalDate issueDate;
    LocalDate dueDate;

    BorrowSlip(String bookId, String userId, LocalDate issueDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

}
