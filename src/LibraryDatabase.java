package src;

import java.util.ArrayList;

import src.services.LibraryServices;
import src.models.Book;
import src.models.Staff;
import src.models.Student;
import src.models.Users;

public class LibraryDatabase implements LibraryServices {

    // Static fields to create unique userIDs
    static int lastStudnetId = 2004;
    static int lastBookId = 1005;
    static int lastStaffId = 3003;

    // users and books array.
    private ArrayList<Users> users;
    public ArrayList<Book> books;

    /**
     * Creating the constructor as private So that an instance of this class can't
     * be created outside this class
     * 
     */
    private LibraryDatabase() {

        // initializing all data in the database.
        users = new ArrayList<Users>();
        books = new ArrayList<Book>();
        users.add(new Librarian("LB1001", "Admin", "Admin", CONSTANTS.LIBRARIAN));
        users.add(new Staff("SF3001", "Ashik", "staff123", CONSTANTS.STAFF, Borrower.Constants.STAFF_MAX_BOOK));
        users.add(new Staff("SF3002", "Fathima", "staff123", CONSTANTS.STAFF, Borrower.Constants.STAFF_MAX_BOOK));
        users.add(new Student("ST2001", "Ajith", "student123", CONSTANTS.STUDENT, Borrower.Constants.STUDENT_MAX_BOOK));
        users.add(
                new Student("ST2002", "Harsha", "student123", CONSTANTS.STUDENT, Borrower.Constants.STUDENT_MAX_BOOK));
        users.add(
                new Student("ST2003", "Nivetha", "student123", CONSTANTS.STUDENT, Borrower.Constants.STUDENT_MAX_BOOK));

        books.add(new Book("B1001", "Twilight", "Stephenie Meyer", 0));
        books.add(new Book("B1002", "Eclipse", "Stephenie Meyer", 5));
        books.add(new Book("B1003", "Atonement", "Ian McEwan", 5));
        books.add(new Book("B1004", "Birdsong", "Faulks", 5));
    }

    // Creating a static reference to the instance methd of library to use outside
    private static LibraryDatabase library = new LibraryDatabase();

    /**
     * This method is used to get the user object using userID
     * 
     * @param userID -userID of user
     * @return user object if the ID is valid or returns null
     */
    public Users getUser(String userID) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).userId.equals(userID)) {
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * This method is used to get the book object using bookID
     * 
     * @param bookID -bookID of book
     * @return book object if the ID is valid or returns null
     */

    public Book getBook(String bookId) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).bookId.equals(bookId)) {
                return books.get(i);
            }
        }
        return null;
    }

    // This methdod helps to display all staffs
    public void displayStaff() {
        String format = "%1$-15s%2$-20s";
        System.out.println("-------------------------------");
        System.out.println("All Staffs:");
        System.out.println("-------------------------------");
        System.out.format(format, "Staff ID", "Staff Name");
        System.out.println();
        for (Users user : users) {
            if (user.userId.startsWith("SF")) {
                System.out.format(format, user.userId, user.userName);
                System.out.println();
            }
        }
        System.out.println("-------------------------------");
    }

    // This method is used to display all students
    public void displayStudent() {
        String format = "%1$-15s%2$-20s";
        System.out.println("-------------------------------");
        System.out.println("All Students:");
        System.out.println("-------------------------------");
        System.out.format(format, "Studnet ID", "Studnet Name");
        System.out.println();
        for (Users user : users) {
            if (user.userId.startsWith("ST")) {
                System.out.format(format, user.userId, user.userName);
                System.out.println();
            }
        }
    }

    // This method is used to display books details
    public void displayBooks() {
        String format = "%1$-15s%2$-20s%3$-20s";
        System.out.println("------------------------------------------------------");
        System.out.println("All Books:");
        System.out.println("------------------------------------------------------");
        System.out.format(format, "Book ID", "Book Name", "Available copies");
        System.out.println();
        for (Book book : books) {
            System.out.format(format, book.bookId, book.bookName, book.available);
            System.out.println();
        }
        System.out.println("------------------------------------------------------");

    }

    /**
     * This method gets and book object and returns if the book is available in
     * library or not
     * 
     * @param book - book object to be searched.
     * @return - true if the book available and false if not
     */
    public boolean checkBookAvailability(Book book) {
        if (book == null) { // checking if the BookID is valid
            System.out.println("Please enter a valid Book ID!");
            return false;

        } else if (book.available == 0) { // checking whether the book is available for borrowing
            System.out.println(
                    "The book \"" + book.bookName + "\" is not available Today please try again after some days!");
            return false;

        }
        return true;
    }

    // This is an static method that returns the library instance
    public static LibraryDatabase getInstance() {
        return library;
    }

    /**
     * This method returns the user details
     * 
     * @param user - User trying to access this methd
     * @return the users if he is an librarian .
     * @throws Exception if he is not an librarian.
     */
    public ArrayList<Users> getUsers(Users user) throws Exception {
        if (user.userType != CONSTANTS.LIBRARIAN) {
            throw new Exception("You are not authenticated to access this data");
        }
        return users;
    }

}
