/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs323.jdbc;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 *              Matthew Kim 011846417
 */
public class CECS323JDBC {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            /*
                Queries to create
                    1. List all writing groups
                    2. List all the data for a group specified by the user
                    3. List all publishers
                    4. List all the data for a publisher specified by the user
                    5. List all book titles
                    6. List all the data for a book specified by the user
                    7. Insert a new book
                    8. Insert a new publisher and update all book published by one publisher to be
                       published by the new publisher
                    9. Remove a book specified by the user
            */
            
            int choice = 10;
            
            while (choice != 0) {
                
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
                System.out.println("Select an option: ");
                choice = in.nextInt();
                
                // List all writing groups.
                if (choice == 1) {
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM WritingGroups";
                    ResultSet rs = stmt.executeQuery(sql);
                    
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
                }
                
                // List all the data for a group specified by the user.
                if (choice == 2) {
                    System.out.println("Input a group name: ");
                    String groupName = in.nextLine();
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM WritingGroups "
                            + "WHERE groupName = " + groupName + ";";
                    ResultSet rs = stmt.executeQuery(sql);
                    
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
                }
                
                // List all publishers.
                if (choice == 3) {
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM Publishers";
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    System.out.printf(displayFormat, "Publisher Name", "Publisher Address", "Publisher Phone", "Publisher Email");
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
                }
                
                // List all the data for a publisher specified by the user.
                if (choice == 4) {
                    System.out.println("Input a publisher name: ");
                    String publisherName = in.nextLine();
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM Publishers "
                            + "WHERE publisherName = " + publisherName + ";";
                    ResultSet rs = stmt.executeQuery(sql);
                    
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
                }
                
                // List all book titles.
                if (choice == 5) {
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM Books";
                    ResultSet rs = stmt.executeQuery(sql);
                    
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
                }
                
                // List all the data for a book specified by the user.
                if (choice == 6) {
                    System.out.println("Input a book title: ");
                    String bookTitle = in.nextLine();
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM Books "
                            + "WHERE bookTitle = " + bookTitle + ";";
                    ResultSet rs = stmt.executeQuery(sql);
                    
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
                }
                
                // Insert a new book.
                if (choice == 7) { 
                    System.out.println("Input book title: ");
                    String title = in.nextLine();
                    System.out.println("Input year published: ");
                    int year = in.nextInt();
                    System.out.println("Input number of pages: ");
                    int pages = in.nextInt();
                    System.out.println("Input group name: ");
                    String group = in.nextLine();
                    System.out.println("Input publisher name: ");
                    String publisher = in.nextLine();
                    
                    stmt = conn.createStatement();
                    String sql;
                    sql = "INSERT INTO Books (bookTitle, yearPublished, numberPages, groupName, publisherName) "
                            + "VALUES (" + title + ", " + year + ", " + pages + ", " + group + ", " + publisher + ")";
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    System.out.println("Book successfully added...");
                }
                
                // Insert a new publisher and update all book published by one publisher to be published by the new publisher.
                if (choice == 8) {
                    // do some stupid stuff
                }
                
                // Remove a book specified by the user.
                if (choice == 9) {
                    System.out.println("Input book title to delete: ");
                    String title = in.nextLine();
                    
                    stmt = conn.createStatement();
                    String sql;
                    sql = "DELETE FROM Books WHERE bookTitle = " + title + ";";
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    System.out.println("Book successfully deleted...");
                }
            }
            
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Books";
            ResultSet rs = stmt.executeQuery(sql);

            
            //STEP 5: Extract data from result set
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
                    
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
    
    public static int menu(Scanner in) {
        boolean done = false;
        int choice = 0;
        
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
        System.out.println("Select an option: ");
        
        while (!done) {
            try {
                choice = in.nextInt();
                if (!(choice >= 1 && choice <= 10)) {
                    throw new NumberFormatException();
                }
                done = true;
            } catch (InputMismatchException ime) {
                in.next();
                System.out.print("Invalid input. Re-enter option: ");
            } catch (NumberFormatException nfe) {
                System.out.print("Invalid input. Re-enter option: ");
            }
        }
        return choice;
    }
    
    public static void option1() {
        
    }
    
}//end FirstExample}
