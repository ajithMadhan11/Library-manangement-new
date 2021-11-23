import java.util.Scanner;

public class Index {

    // Librarian,Staff and Student menu template
    static String adminTemplate = "\n\nchoose from the following options\n\n" + "1:Add Student\n" + "2:Add Staff\n"
            + "3:Remove user\n" + "4:Add Book\n" + "5:view Report\n" + "6:logout\n" + "7:exit\n";
    static String sTemplate = "\n\nchoose from the following options\n\n" + "1:Pay fine\n" + "2:Borrow book\n"
            + "3:Return Book\n" + "4:view my report\n" + "5:logout\n" + "6:Exit\n";
    static Scanner sc = new Scanner(System.in);

    LibraryDatabase library;

    Index() {
        library = LibraryDatabase.getInstance();
    }

    /**
     * This module is used to logging in user
     * 
     * @param type -Type of user trying to lgging in
     */

    public void userLogin(int type) {
        String userID, password;
        // User is asked for the userID and password!
        System.out.println("Enter the userID:");
        userID = sc.nextLine();
        System.out.println("Enter the password:");
        password = sc.nextLine();

        // getting the user Object
        Users user = library.getUser(userID);

        // Authentiating user
        if (user == null || !user.login(userID, password)) {
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
        case Users.Constants.LIBRARIAN: {
            Librarian librarian = (Librarian) user;
            LibrarianIO(librarian);
            break;
        }
        case Users.Constants.STAFF: {
            Staff staff = (Staff) user;
            StaffIO(staff);
            break;
        }
        case Users.Constants.STUDENT: {
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
     * This method is the Librarian Home interface
     * 
     * @param librarian -Librarian object
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
                    librarian.addUser(Users.Constants.STUDENT);
                    break;
                case 2:
                    // to add a staff
                    librarian.addUser(Users.Constants.STAFF);
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
                    // to view report
                    librarian.viewReport();
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
     * This method is the staff Home interface
     * 
     * @param staff -staff object
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
                    staff.borrowBooks(staff);
                    break;
                case 3:
                    staff.returnBook(staff);
                    break;
                case 4:
                    staff.viewReport();
                    break;
                case 5: {
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
                case 6: {
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
     * This method is the student Home interface
     * 
     * @param student -student object
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
                    student.borrowBooks(student);
                    break;
                case 3:
                    student.returnBook(student);
                    break;
                case 4:
                    student.viewReport();
                    break;
                case 5: {
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
                case 6: {
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
        Index index = new Index();
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
                    index.userLogin(Users.Constants.LIBRARIAN);
                    break;
                case 2:
                    index.userLogin(Users.Constants.STAFF);
                    break;
                case 3:
                    index.userLogin(Users.Constants.STUDENT);
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
