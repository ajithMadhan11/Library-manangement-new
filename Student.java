public class Student extends Borrower {

    Student(String userID, String name, String password, int type, int maxBooks) {
        this.userId = userID;
        this.userName = name;
        this.password = password;
        this.userType = type;
        this.maxBooks = maxBooks;
    }

    /**
     * @Override This method overrides calculatefine method od parent.
     * @param days - number of days due to be calculated.
     * @return fine amount
     */
    public int calculateFine(long days) {
        if (Math.abs(days) >= 1) {
            int fine = (int) Math.abs(days) * 10;
            // int fine = (int) days * 10;
            System.out.println("Your fine amount is: ₹" + fine);
            return fine;
        }
        return 0;
    }

}
