import java.time.LocalDate;

public interface BorrowerServices {
    public void borrowBook(Borrower borrower, LibraryDatabase lib);

    public LocalDate borrowBook(Book book, Borrower borrower);

    public void returnBook(Borrower borrower, LibraryDatabase lib);

    public void returnBook(BorrowSlip borrow, LibraryDatabase lib);

    public void payFine(Borrower borrower);

    public int calculateFine(long days);

    public void renewBook(Borrower borrower);

}
