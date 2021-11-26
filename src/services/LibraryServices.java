package src.services;

import src.models.Book;
import src.models.Users;

public interface LibraryServices {
    public Users getUser(String userID);

    public Book getBook(String bookId);

    public void displayStaff();

    public void displayStudent();

    public void displayBooks();

}
