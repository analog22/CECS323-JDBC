/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs323.jdbc;

import java.sql.*;
import java.util.*;

/**
 * @author Mimi Opkins with some tweaking from Dave Brown Matthew Kim 011846417
 */
public class CECS323JDBC {

    // Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    // This is the specification for the printout that I'm doing:
    // each % denotes the start of a new field.
    // The - denotes left justification.
    // The number indicates how wide to make the field.
    // The "s" denotes that it's a string.  All of our output in this test are 
    // strings, but that won't always be the case.
    static String displayFormat;
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    // + "testdb;user=";
    static Connection conn;
    static Scanner in;
    static PreparedStatement pstmt;

    /**
     * Takes the input string and outputs "N/A" if the string is empty or null.
     *
     * @param input The string to be mapped.
     * @return Either the input string or "N/A" as appropriate.
     */
    public static String dispNull(String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0) {
            return "N/A";
        } else {
            return input;
        }
    }

    public static void main(String[] args) {
        // Prompt the user for the database name, and the credentials.
        // If your database has no credentials, you can update this code to 
        // remove that from the connection string.
        in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        // Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user=" + USER + ";password=" + PASS;
        conn = null; // initialize the connection
        pstmt = null; // initialize the statement that we're using
        try {
            // STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            boolean exit = false;
            while (!exit) {
                
                printMenu();

                int choice = 10;
                try {
                    choice = in.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input, please try again...");
                    in.nextLine();
                    promptEnterKey();
                    printMenu();
                }
                in.nextLine();

                switch (choice) {
                    case 1:
                        listAllGroups();
                        break;
                    case 2:
                        listSpecificGroup();
                        break;
                    case 3:
                        listAllPublishers();
                        break;
                    case 4:
                        listSpecificPublisher();
                        break;
                    case 5:
                        listAllBooks();
                        break;
                    case 6:
                        listSpecificBook();
                        break;
                    case 7:
                        addNewBook();
                        break;
                    case 8:
                        addNewPublisher();
                        break;
                    case 9:
                        removeBook();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid input, please try again...");
                        promptEnterKey();
                }
            }
            
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// end finally try
        }// end try
        System.out.println("Goodbye!");
    }// end main

    // Print the menu
    public static void printMenu() {
        System.out.println("1. List all writing groups.");
        System.out.println("2. List all the data for a group specified by the user.");
        System.out.println("3. List all publishers.");
        System.out.println("4. List all the data for a publisher specified by the user.");
        System.out.println("5. List all book titles.");
        System.out.println("6. List all the data for a book specified by the user.");
        System.out.println("7. Insert a new book.");
        System.out.println("8. Insert a new publisher and update all books published by one publisher to be published by the new publisher");
        System.out.println("9. Remove a book specified by the user.");
        System.out.println("0. Exit the system.");
        System.out.print("Select an option: ");
    }

    // 1
    public static void listAllGroups() {
        try {
            pstmt = conn.prepareStatement(
                    "SELECT groupName FROM WritingGroups"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Group Name");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("groupName");

                //Display values
                System.out.println(name);
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2
    public static void listSpecificGroup() {
        try {
            System.out.println("Input a group name: ");
            String groupName = in.nextLine();
            pstmt = conn.prepareStatement(
                    "SELECT * FROM WritingGroups WHERE groupName = ?"
            );
            pstmt.setString(1, groupName);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("groupName");
                String head = rs.getString("headWriter");
                String year = rs.getString("yearFormed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(name), dispNull(head), dispNull(year), dispNull(subject));
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3
    public static void listAllPublishers() {
        try {
            pstmt = conn.prepareStatement(
                    "SELECT publisherName FROM Publishers"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Publisher Name");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("publisherName");

                //Display values
                System.out.println(name);
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4
    public static void listSpecificPublisher() {
        try {
            System.out.println("Input a publisher name: ");
            String publisherName = in.nextLine();
            pstmt = conn.prepareStatement(
                    "SELECT * FROM Publishers WHERE publisherName = ?"
            );
            pstmt.setString(1, publisherName);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Name", "Address", "Phone", "Email");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("publisherName");
                String address = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5
    public static void listAllBooks() {
        try {
            pstmt = conn.prepareStatement(
                    "SELECT bookTitle FROM Books"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Book Title");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("bookTitle");

                //Display values
                System.out.println(title);
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6
    public static void listSpecificBook() {
        try {
            System.out.println("Input a book title: ");
            String bookTitle = in.nextLine();
            pstmt = conn.prepareStatement(
                    "SELECT * FROM Books WHERE bookTitle = ?"
            );
            pstmt.setString(1, bookTitle);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Title", "Year Published", "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("bookTitle");
                String year = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String group = rs.getString("groupName");
                String publisher = rs.getString("publisherName");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(title), dispNull(year), dispNull(pages), dispNull(group), dispNull(publisher));
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 7
    public static void addNewBook() {
        try {
            System.out.println("Input book title: ");
            String title = in.nextLine();
            System.out.println("Input year published: ");
            int year = in.nextInt();
            in.nextLine();
            System.out.println("Input number of pages: ");
            int pages = in.nextInt();
            in.nextLine();
            System.out.println("Input group name: ");
            String group = in.nextLine();
            System.out.println("Input publisher name: ");
            String publisher = in.nextLine();

            pstmt = conn.prepareStatement(
                    "INSERT INTO Books (bookTitle, yearPublished, numberPages, groupName, publisherName) VALUES (?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, title);
            pstmt.setInt(2, year);
            pstmt.setInt(3, pages);
            pstmt.setString(4, group);
            pstmt.setString(5, publisher);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement(
                    "SELECT * FROM Books WHERE bookTitle = ?"
            );
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Title", "Year Published", "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) {
                //Retrieve by column name
                String btitle = rs.getString("bookTitle");
                String byear = rs.getString("yearPublished");
                String bpages = rs.getString("numberPages");
                String bgroup = rs.getString("groupName");
                String bpublisher = rs.getString("publisherName");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(btitle), dispNull(byear), dispNull(bpages), dispNull(bgroup), dispNull(bpublisher));
            }
            rs.close();

            System.out.println("Book successfully added...");

            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 8
    public static void addNewPublisher() {
        try {
            System.out.println("Input publisher name: ");
            String name = in.nextLine();
            System.out.println("Input publisher address: ");
            String address = in.nextLine();
            System.out.println("Input publisher phone: ");
            String phone = in.nextLine();
            System.out.println("Input publisher email: ");
            String email = in.nextLine();
            System.out.println("Input publisher to replace: ");
            String oldPublisher = in.nextLine();

            pstmt = conn.prepareStatement(
                    "INSERT INTO Publishers(publisherName, publisherAddress, publisherPhone, publisherEmail) VALUES (?, ?, ?, ?)"
            );
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(
                    "UPDATE Books SET publisherName = ? WHERE publisherName = ?"
            );
            pstmt.setString(1, name);
            pstmt.setString(2, oldPublisher);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(
                    "DELETE FROM Publishers WHERE publisherName = ?"
            );
            pstmt.setString(1, oldPublisher);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement(
                    "SELECT * FROM Books WHERE publisherName = ?"
            );
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Title", "Year Published", "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("bookTitle");
                String year = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String group = rs.getString("groupName");
                String publisher = rs.getString("publisherName");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(title), dispNull(year), dispNull(pages), dispNull(group), dispNull(publisher));
            }
            rs.close();
            
            System.out.println("Publisher added, old publisher replaced...");

            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 9
    public static void removeBook() {
        try {
            System.out.println("Input book title to delete: ");
            String title = in.nextLine();

            pstmt = conn.prepareStatement(
                    "DELETE FROM Books WHERE bookTitle = ?"
            );
            pstmt.setString(1, title);
            pstmt.executeUpdate();

            System.out.println("Book successfully removed...");
            
            pstmt = conn.prepareStatement(
                    "SELECT bookTitle FROM Books"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Book Title");
            while (rs.next()) {
                //Retrieve by column name
                String btitle = rs.getString("bookTitle");

                //Display values
                System.out.println(btitle);
            }

            rs.close();
            
            System.out.println("Book successfully removed...");

            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Prompt user for ENTER keypress
    // Used to ensure the user progresses the program
    public static void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        Scanner in = new Scanner(System.in);
        in.nextLine();
    }

}
