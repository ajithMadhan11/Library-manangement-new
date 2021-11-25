import java.util.Scanner;

abstract class Users {

    static class Constants {
        public static final int LIBRARIAN = 1;
        public static final int STAFF = 2;
        public static final int STUDENT = 3;
        public static final int STUDENT_MAX_BOOK = 2;
        public static final int STAFF_MAX_BOOK = 3;
    }

    protected String userId;
    protected String userName;
    protected int userType;
    protected String password;
    Scanner sc = new Scanner(System.in);

    /**
     *
     * @param userID   the string username
     * @param password the string password
     * @return returns true if the login is successful else returns false
     */

    public boolean checkCredentials(String userID, String password) {
        return (userID.equals(this.userId) && password.equals(this.password)) ? true : false;
    }

    public abstract void viewReport();
}
