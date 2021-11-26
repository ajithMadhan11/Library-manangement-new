package src.models;

import src.Borrower;
import src.CONSTANTS;

/**
 * This is Staff Model which inherits the property of Borrower and staff has
 * their userType as "STAFF" and maxBooks represents the maximum number of books
 * a staff can borrow from library (this differs from student)
 */
public class Staff extends Borrower {

    public Staff(String userID, String name, String password, CONSTANTS type, int maxBooks) {
        this.userId = userID;
        this.userName = name;
        this.password = password;
        this.userType = type;
        this.maxBooks = maxBooks;
    }

    /**
     * Fine amount is calculated differently for students and staffs. so this method
     * implemented seperatly for both student and staff by overriding parent menthod
     * 
     * @Override This method overrides calculatefine method od parent.
     * @param days - number of days due to be calculated.
     * @return fine amount
     */
    public int calculateFine(long days) {
        if (days >= 1) {
            // int fine = (int) Math.abs(days) * 5;
            int fine = (int) days * 5;
            System.out.println("Your fine amount is:" + fine);
            return fine;
        }
        return 0;
    }

}
