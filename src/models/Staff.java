package src.models;

import src.Borrower;
import src.CONSTANTS;

public class Staff extends Borrower {

    public Staff(String userID, String name, String password, CONSTANTS type, int maxBooks) {
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
        if (days >= 1) {
            // int fine = (int) Math.abs(days) * 5;
            int fine = (int) days * 5;
            System.out.println("Your fine amount is:" + fine);
            return fine;
        }
        return 0;
    }

}
