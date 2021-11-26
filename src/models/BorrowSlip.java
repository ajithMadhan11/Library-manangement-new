package src.models;

import java.time.LocalDate;

/**
 * This is BorrowSlip model which is used to note the book borrow details by
 * user,where issueDate is the issue date of the borrowed book and dueDate is
 * the due date to return the book
 */
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
