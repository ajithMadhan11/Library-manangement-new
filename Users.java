import java.util.Scanner;

abstract class Users {

    static class Constants {
        public static final int LIBRARIAN = 1;
        public static final int STAFF = 2;
        public static final int STUDENT = 3;
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
    public boolean login(String userID, String password) {
        if ((userID.equals(this.userId) && password.equals(this.password))) {
            return true;
        } else {
            System.out.println("The username or password entered was not correct!");
            return false;
        }
    }

    public abstract void viewReport();
}
