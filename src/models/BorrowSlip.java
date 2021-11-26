package src.models;

import java.time.LocalDate;

public class BorrowSlip {
    public String bookId;
    public String userId;
    public LocalDate issueDate;
    public LocalDate dueDate;

    public BorrowSlip(String bookId, String userId, LocalDate issueDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

}
