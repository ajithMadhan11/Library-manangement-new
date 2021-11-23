public class Librarian extends Users implements LibrarianServices {

    Librarian(String userID, String name, String password, int type) {
        this.userId = userID;
        this.userName = name;
        this.password = password;
        this.userType = type;
    }

    /**
     * This method is to added users to Database.
     * 
     * @param type - The type of user to be added in database.
     */
    public void addUser(int type) {
        // getting the library database instance
        LibraryDatabase lib = LibraryDatabase.getInstance();
        String name, pass;

        // getting the username and password of user
        System.out.println("Enter the user's name:");
        name = sc.nextLine();
        System.out.println("Enter the password:");
        pass = sc.nextLine();
        String userId;

        // if the type is Staff then creating a staff object with given data and adding
        // it to the database.
        if (type == Users.Constants.STAFF) {
            userId = "SF" + LibraryDatabase.lastStaffId++;
            Staff staff = new Staff(userId, name, pass, Users.Constants.STAFF);
            lib.users.add(staff);
            System.out.println("Staff has been created with the id : " + userId + "! ");

            // if the type is Student then creating a Student object with given data and
            // adding it to the database.
        } else if (type == Users.Constants.STUDENT) {
            userId = "ST" + LibraryDatabase.lastStudnetId++;
            Student student = new Student(userId, name, pass, Users.Constants.STAFF);
            lib.users.add(student);
            System.out.println("Student has been created with the id : " + userId + "! ");
        }

    }

    /**
     * This method is used to remove the user from database
     */
    public void removeUser() {

        // getting the library database instance
        LibraryDatabase lib = LibraryDatabase.getInstance();
        String id;
        lib.displayUsers();

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
        // checking whether the user has borrowed any books or not
        if (borrower.borrowlist.size() != 0) {
            System.out.println("Can't remove this user because he/she has some pending books to be returned.");
        }

        // checking whether the user has any pending fines or not.
        if (borrower.getFine() != 0) {
            System.out.println("Can't remove this user because he/she has some pending fines to be paid.");
            return;
        }

        // removing user from database.
        lib.users.remove(user);
        System.out.println("User with ID " + id + " has been removed sucessfully");
    }

    /**
     * This method is used to add books to the database.
     */
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

    // this method is t view to adimin report
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
        for (Users user : lib.users) {
            if (user.userId.startsWith("SF")) {
                Borrower borrower = (Borrower) user;
                System.out.format(formatOutput, borrower.userId, borrower.userName, borrower.getFine());
                borrower.displayBorrowedBooks();
                System.out.println();
            }
        }
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Student Detail :");
        System.out.println("------------------------------------------------------------------------------");
        System.out.format(format, "Studnet ID", "Studnet Name", "Pending fine", "Borrowed Books");
        System.out.println();
        for (Users user : lib.users) {
            if (user.userId.startsWith("ST")) {
                Borrower borrower = (Borrower) user;
                System.out.format(formatOutput, borrower.userId, borrower.userName, borrower.getFine());
                borrower.displayBorrowedBooks();
                System.out.println();
            }
        }
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Books Detail :");
        System.out.println("------------------------------------------------------------------------------");
        System.out.format(format, "Book ID", "Book Name", "Author Name", "Available copies");
        System.out.println();
        for (Book book : lib.books) {
            System.out.format(format, book.bookId, book.bookName, book.authorName, book.available);
            System.err.println();
        }
        System.out.println("------------------------------------------------------------------------------");

    }
}
