package postgres.msc.semester2.assignment1.createtables;

import java.sql.*;
import java.io.*;

public class CreateFilmDatabase {
    
    public static void main (String args [])
            throws SQLException, IOException {

          try {
              // Load the PostgreSQL JDBC driver
              Class.forName ("org.postgresql.Driver");
          } catch (ClassNotFoundException e) {
              System.out.println ("Could not load the driver");
          }
          
          System.out.println("PostgreSQL JDBC Driver Registered!");

          Connection connection = null;

          try {
              connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
              
              
              // Create database
              Statement statement = connection.createStatement();
              
              String drop_db_sql = "DROP DATABASE IF EXISTS FILMS";
              statement.executeUpdate(drop_db_sql);
              
              String create_db_sql = "CREATE DATABASE FILMS"; 
              statement.executeUpdate(create_db_sql);
              System.out.println("Database created successfully...");
              
              connection.close();  // close the connection
              System.out.println("Connection closed successfully...");
              
              // use FILMS db
              connection = DriverManager.getConnection(
                      "jdbc:postgresql://127.0.0.1:5432/films", "postgres",
                      "postgres");
              
              System.out.println("Reconnected and Using Films Database...");
              
           // Create Tables
              create_tables(connection);
              System.out.println("Tables created: Actors, Reviewers, Film");
              
           // Populate Tables
              populate_tables(connection);
              System.out.println("Tables (sparsely) populated...");

          } catch (SQLException e) {

              System.out.println("Connection Failed! Check output console");
              e.printStackTrace();
              return;
          } finally { // ensure resultSet, statement and connection are closed
              try {
                  //resultSet.close();
                  connection.close();
                  System.out.println("Connection closed...");
              } catch ( Exception exception ) {
                  exception.printStackTrace();
              } // end try - catch
              
          } // end finally                 
    } // end main
    
    public static void populate_tables(Connection conn)
            throws SQLException {
         Statement s = conn.createStatement();

         
         // Add data to table Actors. Example of adding data to a user defined type
         String query1 = "insert into Actors values( " +
                 "1000, " +
                 "'George Clooney', " +
//         "person_type('George Clooney', 'Male', 'American', Date'1965-12-04'), " +
//                      "address_type('20 Shore Road', 'Newtownabbey', 'BT37 0QB'), " +
//                      "phones_type('11111111111', '22222222222', '33333333333') " +
//                            "), " +
         " 'Casualty' )";
         s.executeUpdate(query1);
         
      // Add data to table Film
         String query2 = "INSERT INTO Film VALUES('Love Actually', " +
             "Date'2003-10-21', " +
             "116, " +
             "'romantic comedy', " +
//             " earnings_type( 1000.00, 2000.00, 3000.00, 4000.00, 5000.00) ) ";
             "1000.00) ";
         s.executeUpdate(query2);
         
         String query3 = "INSERT INTO Film VALUES('HAHK', " +
                 "Date'1995-10-21', " +
                 "185, 'bollywood romance',  " +
//                 " earnings_type(6000.01, 7000.02, 8000.03, 9000.04, 10000.05) )";
                 "6000.00) ";
             s.executeUpdate(query3);
             
          // Add data to table Reviewers. An example of populating a VARRAY
             String query6 = "insert into Reviewers values( " +
                     "1002, " +
                     "'Gavin Conran', " +
//             "person_type('Gavin Conran', 'Male', 'European', Date'1985-12-04', " +
//                          "address_type('20 Shore Road', 'Newtownabbey', 'BT37 0QB'), " +
//                          "phones_type('11111111111', '22222222222', '33333333333') " +
//                                "), " +
//             "emails_varray_type('gavin@gavinconran.com', 'gc@yahoo.com',' gc@uu.co.uk') )";
                     "'gavin@gavinconran.com' )";

             s.executeUpdate(query6);  
             
             s.close();
    }      
    
    public static void create_tables(Connection conn)
            throws SQLException {

         Statement statement = conn.createStatement();
         
         // CREATE NEW TYPES
         // Create type address_type. Example of user defined object
         String query0 = "create type address_type as ( " +
                      "street        varchar(30), " +
                      "city          varchar(20), " +
                      "postcode      varchar(10) )";
         statement.executeUpdate(query0);
         
         // create type phones_type. Example of user defined object
         String query1 = "create type phones_type as (" +
                     "home_phone     char(11), " +
                     "work_phone     char(11), " +
                     "mobile         char(11) )";
         statement.executeUpdate(query1);
         
         // create type person_type. Example of user defined object
         String query3 = "create type person_type as (" +
                     "p_name         varchar(30), " +
                     "p_sex          varchar(6), " +
                     "p_nationality  varchar(20), " +
                     "p_dob          date )";
//                     "p_address      address_type, " +
//                     "p_phones       phones_type )" ;
         statement.executeUpdate(query3);
         
         // create VARRY type emails_varray_type. Example of VARRY collection type
         String query4 = "create type emails_varray_type as (" +
                 "email1     varchar(30), " +
                 "email2     varchar(30), " +
                 "email3     varchar(30) )";
         statement.executeUpdate(query4);
         
         // create type earnings_type. Example of user defined methods
         String query5 = "create type earnings_type as ( " +
                     "asian_earnings        numeric(10,2), \n " +
                     "australian_earnings   numeric(10,2), \n " +
                     "european_earnings      numeric(10,2), \n" +
                     "namerican_earnings    numeric(10,2), \n" +
                     "samerican_earnings     numeric(10,2) \n )" ;
         // specify type earning_type
         statement.executeUpdate(query5);
         
         // create user defined function
         String query5AND6 = "CREATE OR REPLACE FUNCTION gross_total_earnings() RETURNS int AS ' "
                 + " DECLARE "
                 + "    asian_earnings             numeric(10,2); "
                 + "    australian_earnings        numeric(10,2); "
                 + "    european_earnings          numeric(10,2); "
                 + "    namerican_earnings         numeric(10,2); "
                 + "    samerican_earnings         numeric(10,2); "
                 + " BEGIN "
                 + "    return (asian_earnings + australian_earnings + european_earnings + namerican_earnings + samerican_earnings ); "
                 + " END;' language plpgsql";
         statement.executeUpdate(query5AND6);
         
       // CREATE TABLES  
       //create table Actors
         String query20 = "create table Actors( " +
                     "Anum numeric(4) CONSTRAINT ACTOR_PK PRIMARY KEY, " +
//                     "person person_type, " +
                     "name text, " +
                     "DebutFilmTitle VARCHAR(30))" ;
         statement.executeUpdate(query20);
         
      // create table Reviewers
         String query21 = "create table Reviewers( " +
                         "Rnum numeric(4) CONSTRAINT O_REVIEWER_PK PRIMARY KEY,  " +
//                         "person person_type, " +
                         "name text, " +
//                         "emails emails_varray_type)";
                         "emails text)";
         statement.executeUpdate(query21);
         
      // create table Film
         String query22 = "CREATE  TABLE Film( " +
                         "FilmTitle VARCHAR(30) CONSTRAINT O_FILM_PK PRIMARY KEY, " +
                         "ReleaseDate DATE,  " +
                         "RunningTime SMALLINT, " +
                         "Genre VARCHAR(25), " +
                         "earnings numeric(10,2) )";  //earnings_type )";
         statement.executeUpdate(query22);
         
         // Tables Film2, Cinema, and Cinema2 NOT included
         statement.close();

         
    }     
          

}
