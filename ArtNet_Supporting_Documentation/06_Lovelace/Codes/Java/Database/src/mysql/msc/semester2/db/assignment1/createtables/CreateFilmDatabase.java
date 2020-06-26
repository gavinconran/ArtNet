package mysql.msc.semester2.db.assignment1.createtables;

/*
 * An implementation that illustrates the following JDBC interfaces:
 * Statement, PreparedStatement, CallableStatement, ResultSet, ResultSetMetaData
 * Triggers and the following object-relational features of Oracle:
 * user-defined objects and methods, VARRAY collection type, nested and object tables
 */

import java.sql.*;

public class CreateFilmDatabase {
    
 // database URL
    static final String DATABASE_URL = "jdbc:mysql://localhost/books?";
    
//    private static final String user_deitel = "deitel";
//    private static final String password_deitel = "deitel";
     
    private static final String user = "root";
    private static final String password = "gavint";
    
    public static void create_tables(Connection connection, ResultSet resultSet)
            throws SQLException {

         
         
         // create database
         System.out.println("Creating database...");
         Statement statement = connection.createStatement();
         
         String sql = "CREATE DATABASE IF NOT EXISTS FILMS";
         statement.executeUpdate(sql);
         System.out.println("Database created successfully...");
         
         // use FILMS db
         statement.executeQuery("use FILMS"); 
         System.out.println("using FILMS database...");
         
         // Drop tables
         statement.executeUpdate("DROP TABLE IF EXISTS Actors"); 
         statement.executeUpdate("DROP TABLE IF EXISTS Reviewers"); 
         statement.executeUpdate("DROP TABLE IF EXISTS Film1"); 
         statement.executeUpdate("DROP TABLE IF EXISTS Film2");
         statement.executeUpdate("DROP TABLE IF EXISTS Cinema");
         statement.executeUpdate("DROP TABLE IF EXISTS Cinema2");
         System.out.println("dropped existing tables within FILMS database...");
         
         // Create type address_type. Example of user defined object
//         String query0 = "create or replace type address_type as object ( " +
//                      "street        varchar(30), " +
//                      "city          varchar(20), " +
//                      "postcode      varchar(10) )";
//         statement.executeUpdate(query0);
         
         
         
    }     
    
    public static void main(String args[]) {
        
        Connection connection = null;  // manages connection
        Statement statement = null;    // query statement
        ResultSet resultSet = null;    // manages results
        
        // connect to database books and query database
        try {
            // establish connection  to database
            connection = DriverManager.getConnection(
                    DATABASE_URL, user, password);
            
            // create Statement for querying database
            statement = connection.createStatement();
            
            // Creating and populating Actor table
            // Creating a statement lets us issue commands against the connection.

            // Create Tables
            create_tables(connection, resultSet);

            // Create stored procedures
            //create_procedures(conn);

            // Populate Tables
            //populate_tables(conn);            
            
        } catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        } finally { // ensure resultSet, statement and connection are closed
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch ( Exception exception ) {
                exception.printStackTrace();
            }
            
        } // end finally
    } // end main
} // end CreateFilmDatabase
