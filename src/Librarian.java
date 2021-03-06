package src;

import src.models.Book;
import src.models.BorrowSlip;
import src.models.Staff;
import src.models.Student;
import src.models.Users;
import src.services.LibrarianServices;

public class Librarian extends Users implements LibrarianServices {

    Librarian(String userID, String name, String password, CONSTANTS type) {
        this.userId = userID;
        this.userName = name;
        this.password = password;
        this.userType = type;
    }

    /**
     * This method is used to add user to database
     * 
     * @param type - refers to the type of users to be added
     */
    public void addUser(CONSTANTS type) {
        String name, pass;
        System.out.println("Enter the user's name:");
        name = sc.nextLine();
        System.out.println("Enter the password:");
        pass = sc.nextLine();
        if (type == CONSTANTS.STAFF) {
            createStaff(name, pass);
        } else if (type == CONSTANTS.STUDENT) {
            createStudent(name, pass);
        }
    }

    /**
     * This method is used to create a student using the data
     * 
     * @param name - name of the student
     * @param pass - password of the student
     */
    private void createStudent(String name, String pass) {
        LibraryDatabase lib = LibraryDatabase.getInstance();
        String userId = "ST" + LibraryDatabase.lastStudnetId++;
        Student student = new Student(userId, name, pass, CONSTANTS.STUDENT, Borrower.Constants.STUDENT_MAX_BOOK);
        try {
            lib.getUsers(this).add(student);
            System.out.println("Student has been created with the id : " + userId + "! ");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * This method is used to create a staff using the data
     * 
     * @param name - name of the student
     * @param pass - password of the student
     */
    private void createStaff(String name, String pass) {
        LibraryDatabase lib = LibraryDatabase.getInstance();
        String userId = "SF" + LibraryDatabase.lastStaffId++;
        Staff staff = new Staff(userId, name, pass, CONSTANTS.STAFF, Borrower.Constants.STAFF_MAX_BOOK);
        try {
            lib.getUsers(this).add(staff);
            System.out.println("Staff has been created with the id : " + userId + "! ");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // This method is used to remove the user from database

    public void removeUser() {
        // getting the library database instance
        LibraryDatabase lib = LibraryDatabase.getInstance();
        String id;
        lib.displayStaff();
        lib.displayStudent();
        // getting the userID that to be removed
        System.out.println("Enter UserID to remvove :");
        id = sc.nextLine();
        Users user = lib.getUser(id);
        // Downcasting the user to borrower
        Borrower borrower = (Borrower) user;
        // checking whether the user is valid user or not
        if (user == null) {
            System.out.println("No user found with ID : " + id);
            return;
        }
        if (borrower.checkUserDues() != 1) {
            return;
        }
        // removing user from database.
        try {
            lib.getUsers(this).remove(user);
            System.out.println("User with ID " + id + " has been removed sucessfully");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // This method is used to add books to the database.
    public void addBook() {
        // getting instance of the database
        LibraryDatabase lib = LibraryDatabase.getInstance();
        String bName, aName;
        int copies;

        // adding the book to database
        try {
            System.out.println("Enter the Book name :");
            bName = sc.nextLine();
            System.out.println("Enter the Author name :");
            aName = sc.nextLine();
            System.out.println("Enter the no of copies available :");
            copies = Integer.parseInt(sc.nextLine());
            String id = "B" + LibraryDatabase.lastBookId++;
            lib.books.add(new Book(id, bName, aName, copies));
            System.out.println("Book has been added sucessfully!");

        } catch (Exception e) {
            System.out.println("Something went wrong please try again with valid input!");
        }

    }

    // This method is used to generate report for librarian of entire database
    public void viewReport() {
        LibraryDatabase lib = LibraryDatabase.getInstance();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("---------------------------- LMS LIBRARIAN REPORT ----------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Staffs Detail :");
        System.out.println("------------------------------------------------------------------------------");
        String format = "%1$-15s%2$-20s%3$-20s%4$-20s";
        String formatOutput = "%1$-15s%2$-20s%3$-20s";
        System.out.format(format, "Staff ID", "Staff Name", "Pending fine", "Borrowed Books");
        System.out.println();
        try {
            for (Users user : lib.getUsers(this)) {
                if (user.userId.startsWith("SF")) {
                    Borrower borrower = (Borrower) user;
                    System.out.format(formatOutput, borrower.userId, borrower.userName, borrower.getFine());
                    borrower.displayBorrowedBooks();
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Student Detail :");
        System.out.println("------------------------------------------------------------------------------");
        System.out.format(format, "Studnet ID", "Studnet Name", "Pending fine", "Borrowed Books");
        System.out.println();
        try {
            for (Users user : lib.getUsers(this)) {
                if (user.userId.startsWith("ST")) {
                    Borrower borrower = (Borrower) user;
                    System.out.format(formatOutput, borrower.userId, borrower.userName, borrower.getFine());
                    borrower.displayBorrowedBooks();
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Books Detail :        Total books:" + lib.books.size());
        System.out.println("------------------------------------------------------------------------------");
        System.out.format(format, "Book ID", "Book Name", "Author Name", "Available copies");
        System.out.println();
        for (Book book : lib.books) {
            System.out.format(format, book.bookId, book.bookName, book.authorName, book.available);
            System.err.println();
        }
        System.out.println("------------------------------------------------------------------------------");

    }

    // This method is used to view the book stats
    public void viewBookStat() {
        LibraryDatabase lib = LibraryDatabase.getInstance();
        lib.displayBooks();
        System.out.println("Enter BookID:");
        String bookId = sc.nextLine();
        Book book = lib.getBook(bookId);
        if (book == null) {
            System.out.println("please enter a valid book ID");
            return;
        }
        String format = "%1$-15s%2$-15s%3$-15s%4$-15s";
        int sno = 1;
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(
                "Book Name: " + book.bookName + "  Book Id: " + book.bookId + "  Total copies : " + book.getStock());
        System.out.println("-----------------------------------------------------------------------");
        System.out.format(format, "S no", "Borrowed by", "Issue date", "Due date");
        System.out.println();
        for (BorrowSlip borrow : book.borrowlist) {
            System.out.format(format, sno++, borrow.userId, borrow.issueDate, borrow.dueDate);
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    // This method is used by Admin to view user stats using their UserID
    public void viewUserStat() {
        LibraryDatabase lib = LibraryDatabase.getInstance();
        System.out.println("Enter userID to view userstats:");
        String uid = sc.nextLine();
        Users borrower = lib.getUser(uid);
        if (borrower != null) {
            borrower.viewReport();
            return;
        }
        System.out.println("Please enter a valid ID:");
    }

    // This method is used to change the book stocks availablity
    public void changeStocks() {
        LibraryDatabase lib = LibraryDatabase.getInstance();
        System.out.println("Enter BookID whose stock has to be altered:");
        String bookId = sc.nextLine();
        Book book = lib.getBook(bookId);
        System.out.println("Current Stock : " + book.getStock());
        try {
            System.out.println("Enter new stock:");
            int newStock = Integer.parseInt(sc.nextLine());
            int borrowedBooks = book.getStock() - book.available;
            int newBooks = newStock - book.getStock();
            if (newStock < borrowedBooks) {
                System.out.println("Invalid stock , " + borrowedBooks + " books already been borrowed by users.");
                return;
            }
            book.setStock(newStock, this);
            book.available += newBooks;
            System.out.println("Stock updated Sucessfully");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}