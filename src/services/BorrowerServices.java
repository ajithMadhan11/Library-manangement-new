package src.services;

import src.models.Book;
import src.models.BorrowSlip;
import src.LibraryDatabase;
import java.time.LocalDate;

public interface BorrowerServices {
    public void borrowBook(LibraryDatabase lib);

    public LocalDate borrowBook(Book book);

    public void returnBook(LibraryDatabase lib);

    public void returnBook(BorrowSlip borrow, LibraryDatabase lib);

    public void payFine();

    public int calculateFine(long days);

    public void renewBook();

}
