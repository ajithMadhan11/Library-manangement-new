package src;

import java.util.Scanner;
import src.models.Staff;
import src.models.Student;
import src.models.Users;

public class Library {

    static String adminTemplate = "\n\nchoose from the following options\n\n" + "1:Add Student\n" + "2:Add Staff\n"
            + "3:Remove user\n" + "4:Add Book\n" + "5:Alter book stock\n" + "6:view Book stats\n" + "7:view Report\n"
            + "8:logout\n" + "9:exit\n";
    static String sTemplate = "\n\nchoose from the following options\n\n" + "1:Pay fine\n" + "2:Borrow book\n"
            + "3:Return Book\n" + "4:Renew Book\n" + "5:view my report\n" + "6:logout\n" + "7:Exit\n";
    static Scanner sc = new Scanner(System.in);

    LibraryDatabase library;

    // Initaial instance of the libraryDatabase is created when library object is
    // created
    Library() {
        library = LibraryDatabase.getInstance();
    }

    /**
     * This method is used to login user to the system
     * 
     * @param type - ENUM value of the user logging in
     */
    public void userLogin(CONSTANTS type) {
        String userID, password;
        // User is asked for the userID and password
        System.out.println("Enter the userID:");
        userID = sc.nextLine();
        System.out.println("Enter the password:");
        password = sc.nextLine();

        // getting the user Object
        Users user = library.getUser(userID);

        // Authentiating user
        if (user == null || !user.checkCredentials(userID, password)) {
            System.out.println("Please enter a valid userID and password!");
            return;
        }

        // checking is the userType and user trying to lgin are same
        if (user.userType != type) {
            System.out.println("The username or password was not correct!");
            return;
        }

        // Switching the control to different type of user according to user type
        switch (type) {
        case LIBRARIAN: {
            Librarian librarian = (Librarian) user;
            LibrarianIO(librarian);
            break;
        }
        case STAFF: {
            Staff staff = (Staff) user;
            StaffIO(staff);
            break;
        }
        case STUDENT: {
            Student student = (Student) user;
            StudentIO(student);
            break;
        }
        default:
            System.out.println("Something went wrong please try again later!");
            break;
        }
    }

    /**
     * This method is the Librarian Interface from where he can access various
     * library features after authenticatin
     * 
     * @param librarian - the librarian instance
     */
    public void LibrarianIO(Librarian librarian) {
        System.out.println("\nWelcome " + librarian.userName + "!");
        boolean done = false;
        do {
            System.out.print(adminTemplate);
            int choice = Integer.parseInt(sc.nextLine());
            try {
                switch (choice) {
                case 1:
                    // to add a Student
                    librarian.addUser(CONSTANTS.STUDENT);
                    break;
                case 2:
                    // to add a staff
                    librarian.addUser(CONSTANTS.STAFF);
                    break;
                case 3:
                    // to remove a user
                    librarian.removeUser();
                    break;
                case 4:
                    // to add books
                    librarian.addBook();
                    break;
                case 5:
                    librarian.changeStocks();
                    break;
                case 6:
                    librarian.viewBookStat();
                    break;
                case 7:
                    librarian.viewReport();
                    break;
                case 8: {
                    System.out.println("Do you really want to logout? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("logging out...!");
                        done = true;
                    } else {
                        done = false;
                    }
                }
                    break;
                case 9: {
                    System.out.println("Do you really want to exit? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("----------------------------------");
                        System.out.println("------Thankyou for using LMS------");
                        System.out.println("----------------------------------");
                        System.exit(0);
                    } else {
                        done = false;
                    }
                    break;
                }
                default:
                    System.out.println("Please enter a valid input!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong please try again!");
            }
        } while (!done);
    }

    /**
     * This method is the Staff object from where he/she can access the library
     * after authenication
     * 
     * @param staff
     */
    public void StaffIO(Staff staff) {
        System.out.println("\nWelcome " + staff.userName + "!");
        boolean done = false;
        do {
            System.out.println();
            System.out.println("-------------Staff Interface-------------");
            System.out.print(sTemplate);
            System.out.println("-----------------------------------------");
            int choice = Integer.parseInt(sc.nextLine());
            try {
                switch (choice) {
                case 1:
                    staff.payFine(staff);
                    break;
                case 2:
                    staff.borrowBook(staff, library);
                    break;
                case 3:
                    staff.returnBook(staff, library);
                    break;
                case 4:
                    staff.renewBook(staff);
                    break;
                case 5:
                    staff.viewReport();
                    break;
                case 6: {
                    System.out.println("Do you really want to logout? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("logging out...!");
                        done = true;
                    } else {
                        done = false;
                    }
                }
                    break;
                case 7: {
                    System.out.println("Do you really want to exit? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("----------------------------------");
                        System.out.println("------Thankyou for using LMS------");
                        System.out.println("----------------------------------");
                        System.exit(0);
                    } else {
                        done = false;
                    }
                }
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid input!");
            }
        } while (!done);
    }

    /**
     * This method is the Student object from where he/she can access the library
     * after authenication
     * 
     * @param student
     */
    public void StudentIO(Student student) {
        System.out.println("\nWelcome " + student.userName + "!");
        boolean done = false;
        while (!done) {
            System.out.println();
            System.out.println("------------Student Interface------------");
            System.out.print(sTemplate);
            System.out.println("-----------------------------------------");
            int choice = Integer.parseInt(sc.nextLine());
            try {
                switch (choice) {
                case 1:
                    student.payFine(student);
                    break;
                case 2:
                    student.borrowBook(student, library);
                    break;
                case 3:
                    student.returnBook(student, library);
                    break;
                case 4:
                    student.renewBook(student);
                    break;
                case 5:
                    student.viewReport();
                    break;

                case 6: {
                    System.out.println("Do you really want to logout? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("logging out...!");
                        done = true;
                    } else {
                        done = false;
                    }
                }
                    break;
                case 7: {
                    System.out.println("Do you really want to exit? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("----------------------------------");
                        System.out.println("------Thankyou for using LMS------");
                        System.out.println("----------------------------------");
                        System.exit(0);
                    } else {
                        done = false;
                    }
                }
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    public static void main(String[] args) {
        Library Library = new Library();
        boolean done = false;
        do {
            System.out.println();
            System.out.println("-------Welcome to Library Management System-------");
            System.out.println();
            System.out.println("Select from the following options");
            System.out.println("1.Librarian login");
            System.out.println("2.Staff login");
            System.out.println("3.Studnet login");
            System.out.println("4.Exit");
            System.out.println();
            System.out.println("--------------------------------------------------");

            try {
                int input = Integer.parseInt(sc.nextLine());

                switch (input) {
                case 1:
                    Library.userLogin(CONSTANTS.LIBRARIAN);
                    break;
                case 2:
                    Library.userLogin(CONSTANTS.STAFF);
                    break;
                case 3:
                    Library.userLogin(CONSTANTS.STUDENT);
                    break;
                case 4:
                    System.out.println("Do you really want to exit? y/n");
                    String userInput = sc.nextLine();
                    if ((userInput.equals("y") || userInput.equals("Y"))) {
                        System.out.println("----------------------------------");
                        System.out.println("------Thankyou for using LMS------");
                        System.out.println("----------------------------------");
                        done = true;
                    } else {
                        done = false;
                    }
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
                }

            } catch (Exception e) {
                System.out.println("Something went wrong please try again!");
            }

        } while (!done);

    }
}
