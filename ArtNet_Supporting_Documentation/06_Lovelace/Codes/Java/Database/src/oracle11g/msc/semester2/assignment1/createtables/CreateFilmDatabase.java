package oracle11g.msc.semester2.assignment1.createtables;

import java.sql.*;
import java.io.*;


public class CreateFilmDatabase {
    
    public static void main (String args [])
            throws SQLException, IOException {

            // the following statement loads the Oracle jdbc driver

          try {
              // Load the Oracle JDBC driver
              Class.forName ("oracle.jdbc.driver.OracleDriver");
          } catch (ClassNotFoundException e) {
              System.out.println ("Could not load the driver");
          }
          
          System.out.println("Oracle JDBC Driver Registered!");

          Connection conn = null;

          try {
              String user, pass, host,servicename;
              user = readEntry("userid  : ");
              pass = readEntry("password: ");
              host = readEntry("hostname or ip address: ");
              /*  userid, password and hostname are obtained from the console
              *  It is assumed that the service name is XE and the databse server
              *  listens on the default port 1521.
              */

              servicename="xe";
              // Connect to the database
              conn = DriverManager.getConnection
                          //("jdbc:oracle:oci8:"+user+"/"+pass+"@"+host+":1521:xe");
                            ("jdbc:oracle:thin:"+user+"/"+pass+"@"+host+":1521/"+servicename);
              
              System.out.println(conn.getAutoCommit());
              conn.setAutoCommit(false);
              System.out.println(conn.getAutoCommit());

              // Creating and populating Actor table
              // Creating a statement lets us issue commands against the connection.

              // Create Tables
              create_tables(conn);

              // Create stored procedures
              create_procedures(conn);

              // Populate Tables
              populate_tables(conn);
              
          } catch (SQLException e) {

              System.out.println("Connection Failed! Check output console\n");
              e.printStackTrace();
              return;
          } finally { // ensure resultSet, statement and connection are closed
              try {
                  //resultSet.close();
                  conn.close();
                  System.out.println("Connection closed...");
              } catch ( Exception exception ) {
                  exception.printStackTrace();
              }
              
          } // end finally// end try - catch     
    } // end main
    
    public static void create_procedures(Connection conn)
            throws SQLException {
         Statement s = conn.createStatement();

         // create update_email stored procedure
         //s.executeUpdate("drop procedure update_email");
         s.execute(" create or replace procedure update_email( \n " +
                "rnum2 in reviewers.rnum%type, \n " +
                "i in integer, \n " +
                "email in varchar )  as \n " +
                "emails2 reviewers.emails%type; \n " +

                "begin \n " +

                "select r.emails \n " +
                "into emails2 \n " +
                "from reviewers r \n " +
                "where r.rnum = rnum2; \n " +

                "emails2(i) := email; \n " +

                "update reviewers r \n " +
                "set r.emails = emails2 \n " +
                "where r.rnum = rnum2; \n " +

                "end; " );

         // create get_email stored procedure
         //s.executeUpdate("drop procedure get_email");
         s.execute(" create or replace procedure get_email( \n " +
                "rnum2 in reviewers.rnum%type, \n " +
                "i in integer, \n " +
                "email out varchar )  as \n " +
                "emails2 reviewers.emails%type; \n " +

                "begin \n " +
                "select r.emails \n " +
                "into emails2 \n " +
                "from reviewers r \n " +
                "where r.rnum = rnum2; \n " +

                "email := emails2(i); \n " +
                "end; " );

         System.out.println("Stored Procedures created: update_email, get_email");

 }

    
    public static void populate_tables(Connection conn)
            throws SQLException {
         Statement s = conn.createStatement();

         System.out.println("Populating Tables");
         // Add data to table Actors. Example of adding data to a user defined type
         String query1 = "insert into Actors values( " +
                 "1000, " +
         "person_type('George Clooney', 'Male', 'American', Date'1965-12-04', " +
                      "address_type('20 Shore Road', 'Newtownabbey', 'BT37 0QB'), " +
                      "phones_type('11111111111', '22222222222', '33333333333') " +
                            "), " +
         " 'Casualty' )";
         s.executeUpdate(query1);
         conn.commit();

         // Add data to table Film
         String query2 = "INSERT INTO Film VALUES('Love Actually', " +
             "Date'2003-10-21', " +
             "116, " +
             "'romantic comedy', " +
             " earnings_type( 1000.00, 2000.00, 3000.00, 4000.00, 5000.00) ) ";
         s.executeUpdate(query2);
         conn.commit();

         String query3 = "INSERT INTO Film VALUES('HAHK', " +
             "Date'1995-10-21', " +
             "185, 'bollywood romance',  " +
             " earnings_type(6000.01, 7000.02, 8000.03, 9000.04, 10000.05) )";
         s.executeUpdate(query3);
         conn.commit();

         //System.out.println("Inserted Love Actually and HAHK into O_Film");

         // Add data to table Film2
         String query4 = "INSERT INTO Film2 VALUES('Love Actually', " +
             "Date'2003-10-21', " +
             "116, " +
             "'romantic comedy', " +
             " earnings2_type( 1000.00, 2000.00, 3000.00, 4000.00, 5000.00) ) ";
         s.executeUpdate(query4);
         conn.commit();

         String query5 = "INSERT INTO Film2 VALUES('HAHK', " +
             "Date'1995-10-21', " +
             "185, " +
             "'bollywood romance',  " +
             " earnings2_type(6000.01, 7000.02, 8000.03, 9000.04, 10000.05) )";
         s.executeUpdate(query5);
         conn.commit();

         // Add data to table Reviewers. An example of populating a VARRAY
         String query6 = "insert into Reviewers values( " +
                 "1002, " +
         "person_type('Gavin Conran', 'Male', 'European', Date'1985-12-04', " +
                      "address_type('20 Shore Road', 'Newtownabbey', 'BT37 0QB'), " +
                      "phones_type('11111111111', '22222222222', '33333333333') " +
                            "), " +
         "emails_varray_type('gavin@gavinconran.com', 'gc@yahoo.com',' gc@uu.co.uk') )";

         s.executeUpdate(query6);
         conn.commit();

         // Add data to table Cinema. An example of populating a Nested table
         String query7 = "insert into Cinema values(1000, \n" +
                " screen_revenue_table (  \n" +
                                        "screen_revenue('may01', 10000), \n " +
                                        "screen_revenue('may02', 5000), \n " +
                                        "screen_revenue('may03', 2000) \n " +
                                    ") \n " +
        ") ";
         s.executeUpdate(query7);
         conn.commit();

         // Add data to table Cinema. An example of populating a Nested table
         String query8 = "insert into Cinema values(2000, \n" +
                " screen_revenue_table (  \n" +
                                        "screen_revenue('soho01', 20000), \n " +
                                        "screen_revenue('soho02', 9000), \n " +
                                        "screen_revenue('soho03', 3000) \n " +
                                    ") \n " +
        ") ";
         s.executeUpdate(query8);
         conn.commit();

         // Add data to table Cinema2. An example of populating an Object table
         String query9 = "insert into Cinema2 values(cinema2_type (3000,  "+
                         "screen_revenue_table ( screen_revenue('rich01', 50000), "+
                                                 "screen_revenue('rich02', 30000), "+
                                                 "screen_revenue('rich03', 10000) ) ) )";
         s.executeUpdate(query9);
         conn.commit();

         System.out.println("Tables populated with data");

    }
    
    public static void create_tables(Connection conn)
            throws SQLException {

         Statement s = conn.createStatement();
         s.executeUpdate("drop table Actors cascade constraints");
         s.executeUpdate("drop table Reviewers cascade constraints");
         s.executeUpdate("drop table Film cascade constraints");
         s.executeUpdate("drop table Film2 cascade constraints");
         s.executeUpdate("drop table Cinema cascade constraints");
         s.executeUpdate("drop table Cinema2 cascade constraints");
         s.executeUpdate("drop type person_type");
         s.executeUpdate("drop type address_type");
         s.executeUpdate("drop type phones_type");
         s.executeUpdate("drop type emails_varray_type");
         s.executeUpdate("drop type earnings_type");
         s.executeUpdate("drop type earnings2_type");
         s.executeUpdate("drop type cinema2_type");
         s.executeUpdate("drop type screen_revenue_table");
         s.executeUpdate("drop type screen_revenue");


         // Create type address_type. Example of user defined object
         String query0 = "create or replace type address_type as object ( " +
                      "street        varchar(30), " +
                      "city          varchar(20), " +
                      "postcode      varchar(10) )";
         s.executeUpdate(query0);
         conn.commit();

         // create type phones_type. Example of user defined object
         String query1 = "create or replace type phones_type as object (" +
                     "home_phone     char(11), " +
                     "work_phone     char(11), " +
                     "mobile         char(11) )";
         s.executeUpdate(query1);
         conn.commit();

         // create type person_type. Example of user defined object
         String query3 = "create or replace type person_type as object (" +
                     "p_name         varchar(30), " +
                     "p_sex          varchar(6), " +
                     "p_nationality  varchar(20), " +
                     "p_dob          date, " +
                     "p_address      address_type, " +
                     "p_phones       phones_type )" ;
         s.executeUpdate(query3);
         conn.commit();

         // create VARRY type emails_varray_type. Example of VARRY collection type
         String query4 = "create type emails_varray_type as varray(3) of varchar(30) ;";
         s.executeUpdate(query4);
         conn.commit();


         // create type earnings_type. Example of user defined methods
         String query5 = "create type earnings_type as object ( " +
                     "asian_earnings        number(10,2), \n " +
                     "australian_earnings   number(10,2), \n " +
                     "european_earnings      number(10,2), \n" +
                     "namerican_earnings    number(10,2), \n" +
                     "samerican_earnings     number(10,2), \n" +
                     "member function gross_total_earnings return number)" ;
         String query6 = "create type body earnings_type as \n" +
                     "member function gross_total_earnings return number is \n" +
                     "begin \n" +
                     "return (asian_earnings + australian_earnings + european_earnings + namerican_earnings + samerican_earnings ); \n" +
                     "end; \n" +
                     "end; \n" ;

         // specify type earning_type
         s.executeUpdate(query5);

         // implement method gross_total_earnings
         s.executeUpdate(query6);
         conn.commit();

         // create type earnings2
         String query7 = "create or replace type earnings2_type as object ( " +
         "asian_earnings        number(10,2), \n " +
         "australian_earnings   number(10,2), \n " +
         "european_earnings      number(10,2), \n" +
         "namerican_earnings    number(10,2), \n" +
         "samerican_earnings     number(10,2), \n" +
         "member function gross_total_earnings return number, \n" +
         "member function net_earnings(deductions  number) return number)";
         String query8 = "create or replace type body earnings2_type as \n" +
        "member function gross_total_earnings return number is \n" +
                "begin \n" +
          "return (asian_earnings + australian_earnings + european_earnings \n" +
                        "+ namerican_earnings + samerican_earnings ); \n" +
        "end; \n" +
        "member function net_earnings(deductions in number) return number is \n" +
                  "begin \n" +
                    "return ( (1.0-deductions)*self.gross_total_earnings() ); \n" +
                  "end; \n" +
        "end; \n" ;
         // specify type earning_type2
         s.executeUpdate(query7);

         // implement method gross_total_earnings and net_earnings
         s.executeUpdate(query8);
         conn.commit();

         // create type screen_revenue_table type. Example of a Nested Table
         String query9 = "create or replace type screen_revenue as object (screencode varchar(8), revenue  int) ";
         String query10 = "create or replace type screen_revenue_table as table of screen_revenue ";
         s.executeUpdate(query9);
         s.executeUpdate(query10);
         conn.commit();

         // create type cinema2_type. Example of an Object table
         String query11 = "create or replace type cinema2_type  as object (cnum2 int, result2 screen_revenue_table) ";
         s.executeUpdate(query11);
         conn.commit();

         //create table Actors
         String query20 = "create table Actors( " +
                     "Anum number(4) CONSTRAINT ACTOR_PK PRIMARY KEY, " +
                     "person person_type, " +
                     "DebutFilmTitle VARCHAR(30))" ;
         s.executeUpdate(query20);
         conn.commit();

         // create table Reviewers
         String query21 = "create table Reviewers( " +
                         "Rnum number(4) CONSTRAINT O_REVIEWER_PK PRIMARY KEY,  " +
                         "person person_type, " +
                         "emails emails_varray_type)";
         s.executeUpdate(query21);
         conn.commit();

         // create table Film
         String query22 = "CREATE  TABLE Film( " +
                         "FilmTitle VARCHAR(30) CONSTRAINT O_FILM_PK PRIMARY KEY, " +
                         "ReleaseDate DATE,  " +
                         "RunningTime SMALLINT, " +
                         "Genre VARCHAR(25), " +
                         "earnings earnings_type )";

         s.executeUpdate(query22);
         conn.commit();

         // create table Film2
         String query23 = "CREATE  TABLE Film2( FilmTitle VARCHAR(30) CONSTRAINT O_FILM2_PK PRIMARY KEY, " +
                         "ReleaseDate DATE, " +
                         "RunningTime SMALLINT, " +
                         "Genre VARCHAR(25), " +
                         "earnings2 earnings2_type )";
         s.executeUpdate(query23);

         // create nested table Cinema
         String query24 = "create table Cinema(cnum int constraint cinema_pk primary key, " +
                         "revenue screen_revenue_table) nested table revenue store as revenue_tab ";
         s.executeUpdate(query24);
         conn.commit();

         // create object table Cinema2
         String query25 = "create  table Cinema2 of cinema2_type  (primary key (cnum2) ) "+
                          "nested table result2 store as revenue2_tab ";
         s.executeUpdate(query25);
         conn.commit();


         conn.commit(); // commit after all data has been inserted
     // inb.close();   // close the buffered reader
     // inf.close();   // close the file
        System.out.println("Tables created: Actors, Reviewers, Film1, Film2, Cinema, Cinema2");

 }

    
  //readEntry function -- to read input string
    static String readEntry(String prompt) {
       try {
         StringBuffer buffer = new StringBuffer();
         System.out.print(prompt);
         System.out.flush();
         int c = System.in.read();
         while(c != '\n' && c != -1) {
           buffer.append((char)c);
           c = System.in.read();
         }
         return buffer.toString().trim();
       } catch (IOException e) {
         return "";
         }
     }

}
