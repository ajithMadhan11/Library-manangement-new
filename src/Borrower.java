package src;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import src.models.Book;
import src.models.BorrowSlip;
import src.models.Users;
import src.services.BorrowerServices;

public abstract class Borrower extends Users implements BorrowerServices {

    // These constants are used to define the number of maximum books that can be
    // borrowed by students and staff
    public static class Constants {
        public static final int STUDENT_MAX_BOOK = 2;
        public static final int STAFF_MAX_BOOK = 3;
    }

    // Fine amount data of borrower
    private int fine;

    // Maximum number of books a borrower can borrow
    public int maxBooks;

    // This arraylist contains the bookSlip of books borrowed by a borrower
    ArrayList<BorrowSlip> borrowlist = new ArrayList<BorrowSlip>();

    /**
     * This method is used to borrow books from library database
     * 
     * @param borrower -the borrower obj
     * @param lib      - the library instance
     */
    public void borrowBook(Borrower borrower, LibraryDatabase lib) {

        lib.displayBooks();
        System.out.println("Enter the BookID to Borrow :");
        String bookId = sc.nextLine();
        Book book = lib.getBook(bookId);

        if (lib.checkBookAvailability(book) && canUserBorrowBook()) {
            LocalDate due = borrowBook(book, borrower);
            System.out.println(
                    "Book " + book.bookName + " has been borrowed sucessfully please return it on or before " + due);
        }
    }

    /**
     * This method is overloaded to borrow books If both Book and borrow instance is
     * passed to this method it borrows book from library and returns the due date.
     * 
     * @param book     - book obj to borrow
     * @param borrower - the borrower obj
     */
    public LocalDate borrowBook(Book book, Borrower borrower) {
        book.available--;
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(10);
        BorrowSlip borrow = new BorrowSlip(book.bookId, borrower.userId, today, due);
        borrower.borrowlist.add(borrow);
        book.borrowlist.add(borrow);
        return due;
    }

    /**
     * This method helps to return book to the library
     * 
     * @param borrower - borrower obj
     * @param lib      - library instance
     */
    public void returnBook(Borrower borrower, LibraryDatabase lib) {
        if (checkBorrowedBooks(borrower) != 1)
            return;
        System.out.println("\nEnter the BookID to Return :");
        String bookId = sc.nextLine();
        for (BorrowSlip borrow : borrower.borrowlist) {
            if (borrow.bookId.equals(bookId)) {
                borrower.returnBook(borrow, lib);
                System.out.println("Book has been returned sucessfully!");
                System.out.println("Your pending fine amount is ₹" + this.fine);
                return;
            }
        }
        System.out.println("You havn't borrowed any book with such ID");
        return;
    }

    /**
     * This method overloaded to return book to the library If both borrowSlip and
     * libraryDatabase instance is passed to this method this returns the book to
     * the libraryDatabase
     * 
     * @param borrow - borrow obj
     * @param lib    - library instance
     */

    public void returnBook(BorrowSlip borrow, LibraryDatabase lib) {
        // Finding if book is returned withing due date
        long d_days = borrow.dueDate.until(LocalDate.now(), ChronoUnit.DAYS);
        // calculating the fine amount
        this.fine += calculateFine(d_days);
        Book book = lib.getBook(borrow.bookId);
        // reverting the book status
        book.available++;
        // removing from array
        book.borrowlist.remove(borrow);
        this.borrowlist.remove(borrow);

    }

    /**
     * This method helps to pay fine by borrower
     * 
     * @param borrower - the borrower obj
     */
    public void payFine(Borrower borrower) {
        try {
            if (this.fine == 0) {
                System.out.println("You don't have any pending fines! Thank you :) ");
                return;
            }
            System.out.println("Your pending fine amount is:" + this.fine);
            System.out.println("Enter amount to pay:");
            int amt = Integer.parseInt(sc.nextLine());
            if (amt > this.fine) {
                int balance = amt - this.fine;
                this.fine = 0;
                System.out.println("Amount payed sucessfully! your balance amount is : ₹" + balance);
                return;
            }
            this.fine -= amt;
            System.out.println("Amount payed sucessfully!");
        } catch (Exception e) {
            System.out.println("Please enter a valid input!");
            return;
        }
    }

    /**
     * This methods helps to check the user dues
     * 
     * @return positive integer if he has no due else with negative integer
     */
    public int checkUserDues() {
        // checking whether the user has borrowed any books or not
        int check = 1;
        if (this.borrowlist.size() != 0) {
            System.out.println("Can't remove this user because he/she has some pending books to be returned.");
            check = -1;
            return check;
        }
        // checking whether the user has any pending fines or not.
        if (getFine() != 0) {
            System.out.println("Can't remove this user because he/she has some pending fines to be paid.");
            check = -1;
            return check;
        }
        return check;
    }

    // This method is used to display borrowed books by Borrower
    public void displayBorrowedBooks() {
        if (this.borrowlist.size() == 0) {
            System.out.print("-");
        } else {
            for (BorrowSlip borrow : this.borrowlist) {
                System.out.print(borrow.bookId + " |");
            }
        }
    }

    /**
     * This method is used to renew the existing borrowed book
     * 
     * @param borrower - the borrower obj
     */
    public void renewBook(Borrower borrower) {
        if (checkBorrowedBooks(borrower) != 1)
            return;
        System.out.println("\nEnter the BookID to Renew :");
        String bookId = sc.nextLine();
        for (BorrowSlip borrow : borrowlist) {
            if (borrow.bookId.equals(bookId)) {
                long d_days = borrow.dueDate.until(LocalDate.now(), ChronoUnit.DAYS);
                this.fine += calculateFine(d_days);
                borrow.dueDate = borrow.dueDate.plusDays(5);
                System.out.println("Book has been renewed and new Due date is :" + borrow.dueDate);
                return;
            }
        }
        System.out.println("You havn't borrowed any book with such ID");
    }

    public int getFine() {
        return this.fine;
    }

    /**
     * @override this method overrides users method This method is used to view
     *           borrower report
     */
    public void viewReport() {
        System.out.println("-----------------------------------------------");
        System.out.println();
        System.out.println(getUserType(this.userType) + " Details");
        System.out.println("User ID        : " + this.userId);
        System.out.println("User Name      : " + this.userName);
        System.out.println("User Type      : " + getUserType(this.userType));
        System.out.println("Pending fine   : ₹" + getFine());

        System.out.println("\nBorrowed Books : ");
        String format = "%1$-15s%2$-20s%3$-20s";
        if (borrowlist.size() == 0) {
            System.out.println("No books Borrowed yet!");
        } else {
            System.out.format(format, "Book ID", "Issued On", "Due date");
            for (BorrowSlip b : borrowlist) {
                System.out.println();
                System.out.format(format, b.bookId, b.issueDate, b.dueDate);
            }
        }
        System.out.println("\n-----------------------------------------------");
    }

    /**
     * This methods is used to check the borrowed books by users
     * 
     * @param borrower
     * @return a positive integer and prints the borrowed books and if no book
     *         borrowed then returns a negative integer
     */
    private int checkBorrowedBooks(Borrower borrower) {
        int check = 1;
        // chcking whether the user has borrowed any book
        if (borrower.borrowlist.size() == 0) {
            System.out.println("You havn't borrowed any book yet !");
            check = -1;
            return check;
        }
        // Displaying the books borrowed by user
        System.out.print("Books to return :");
        for (BorrowSlip borrow : borrower.borrowlist) {
            System.out.print(borrow.bookId + " |");
        }

        return check;
    }

    /**
     * Checks wheather the user can borrow books or not
     * 
     * @return true if they can borrow or else false
     */
    private boolean canUserBorrowBook() {
        if (borrowlist.size() == this.maxBooks) { // checking whether the user is trying borrow more than 2 books.
            System.out.println("Sorry! you can't Borrow more than " + this.maxBooks
                    + " books please return the book you had Borrowed previously!");
            return false;
        } else if (getFine() > 0) { // checking whether the user has any pending fine.
            System.out.println("Please pay the pending fine ₹" + getFine() + " to borrow more books!");
            return false;
        }
        return true;
    }

    public abstract int calculateFine(long days);

}
