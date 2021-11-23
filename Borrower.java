import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

abstract class Borrower extends Users implements BorrowerServices {
    private int fine;
    ArrayList<Borrow> borrowlist = new ArrayList<Borrow>(2);

    /**
     * This method is to Borrow a book frm library
     * 
     * @param borrower borrower object to borrow books
     */
    public void borrowBooks(Borrower borrower) {
        // getting the library instance
        LibraryDatabase lib = LibraryDatabase.getInstance();

        // displaying available books to user
        lib.displayBooks();

        // getting user the BookID to borrow
        System.out.println("Enter the BookID to Borrow :");
        String bookId = sc.nextLine();
        Book book = lib.getBook(bookId);

        // checking if the BookID is valid
        if (book == null) {
            System.out.println("Please enter a valid Book ID!");
            return;

            // checking whether the book is available for borrowing
        } else if (book.available == 0) {
            System.out.println(
                    "The book \"" + book.bookName + "\" is not available Today please try again after some days!");
            return;

            // checking whether the user is trying borrow more than 2 books.
        } else if (borrower.borrowlist.size() == 2) {
            System.out.println(
                    "Sorry! you can't Borrow more than 2 books please return the book you had Borrowed previously!");
            return;

            // checking whether the user has any pending fine.
        } else if (borrower.fine > 0) {
            System.out.println("Please pay the pending fine ₹" + borrower.fine + " to borrow more books!");
            return;
        }

        // borrowing book
        book.available--;
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(10);
        Borrow borrow = new Borrow(book.bookId, today, due);
        this.borrowlist.add(borrow);
        System.out.println(
                "Book " + book.bookName + " has been borrowed sucessfully please return it on or before " + due);

    }

    /**
     * This method is to return the borrowed book
     * 
     * @param borrower borrower object to borrow books
     */
    public void returnBook(Borrower borrower) {
        // getting library instance
        LibraryDatabase lib = LibraryDatabase.getInstance();

        // chcking whether the user has borrowed any book
        if (borrower.borrowlist.size() == 0) {
            System.out.println("You havn't borrowed any book yet !");
            return;
        }

        // Displaying the books borrowed by user
        System.out.print("Books to return :");
        for (Borrow borrow : borrower.borrowlist) {
            System.out.print(borrow.bookId + " |");
        }
        System.out.println();
        System.out.println("Enter the BookID to Return :");
        String bookId = sc.nextLine();

        for (Borrow borrow : borrower.borrowlist) {
            if (borrow.bookId.equals(bookId)) {
                // Finding if book is returned withing due date
                long d_days = borrow.dueDate.until(LocalDate.now(), ChronoUnit.DAYS);

                // calculating the fine amount
                this.fine += calculateFine(d_days);
                Book book = lib.getBook(bookId);

                // reverting the book status
                book.available++;

                // remving from array
                borrower.borrowlist.remove(borrow);
                System.out.println("Book has been returned sucessfully!");
                System.out.println("Your pending fine amount is ₹" + borrower.fine);
                return;
            }
        }

        System.out.println("You havn't borrowed any book with such ID");
        return;

    }

    /**
     * This method is to pay fine
     * 
     * @param borrower borrower object to borrow books
     */
    public void payFine(Borrower borrower) {
        try {
            // Returns if user have no pending fine
            System.out.println("Your pending fine amount is:" + this.fine);
            if (this.fine == 0) {
                System.out.println("You don't have any pending fines! Thank you :) ");
                return;
            }
            // Getting amunt to be payed
            System.out.println("Enter amount to pay:");
            int amt = Integer.parseInt(sc.nextLine());

            // Returning if amount is greater than payable amount
            if (amt > this.fine) {
                System.out.println("Entered amount is greater than payabale amount!");
                return;
            }

            // Deducting the payed fine amount
            this.fine -= amt;
            System.out.println("Amount payed sucessfully!");
        } catch (Exception e) {
            System.out.println("Please enter a valid input!");
            return;
        }

    }

    // This is an helper function to display borrowed books
    public void displayBorrowedBooks() {
        if (this.borrowlist.size() == 0) {
            System.out.print("-");
        } else {
            for (Borrow borrow : this.borrowlist) {
                System.out.print(borrow.bookId + " |");
            }
        }
    }

    public int getFine() {
        return this.fine;
    }

    public abstract int calculateFine(long days);

}
