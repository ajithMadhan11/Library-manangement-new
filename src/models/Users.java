package src.models;

import java.util.Scanner;

import src.CONSTANTS;

public abstract class Users {

    public String userId;
    public String userName;
    public CONSTANTS userType;
    protected String password;
    public Scanner sc = new Scanner(System.in);

    /**
     * This method is used to verify the credentials of the user
     * 
     * @param userID   - userID of the user
     * @param password - user password
     * @return true if credentials are valid or returns false
     */
    public boolean checkCredentials(String userId, String password) {
        return (userId.equals(this.userId) && password.equals(this.password)) ? true : false;
    }

    /**
     * This method returns the type of user String
     * 
     * @param type -type of user
     * @return the String value of the type
     */
    public String getUserType(CONSTANTS type) {
        return type == CONSTANTS.STAFF ? "Staff" : "Student";
    }

    public abstract void viewReport();
}
