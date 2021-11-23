public class Student extends Borrower {

    Student(String userID, String name, String password, int type) {
        this.userId = userID;
        this.userName = name;
        this.password = password;
        this.userType = type;
    }

    /**
     * @Override This method overrides calculatefine method od parent.
     * @param days - number of days due to be calculated.
     * @return fine amount
     */
    public int calculateFine(long days) {
        if (days >= 1) {
            // int fine = (int) Math.abs(days) * 10;
            int fine = (int) days * 10;
            System.out.println("Your fine amount is: ₹" + fine);
            return fine;
        }
        return 0;
    }

    /**
     * @Override this method overrides viewReport of parent method
     */
    public void viewReport() {
        System.out.println("-----------------------------------------------");
        System.out.println();
        System.out.println("Student Details");
        System.out.println("Student ID     : " + this.userId);
        System.out.println("Student Name   : " + this.userName);
        System.out.println("User Type      : Student");
        System.out.println("Pending fine   : ₹" + getFine());
        System.out.println();
        System.out.println("Borrowed Books : ");
        String format = "%1$-15s%2$-20s%3$-20s";
        if (borrowlist.size() == 0) {
            System.out.println("No books Borrowed yet!");
        } else {
            System.out.format(format, "Book ID", "Issued On", "Due date");
            for (Borrow b : borrowlist) {
                System.out.println();
                System.out.format(format, b.bookId, b.issueDate, b.dueDate);
            }
        }
        System.out.println();

        System.out.println("-----------------------------------------------");

    }
}
