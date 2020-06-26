package oracle11g.msc.semester2.assignment1.querydatabase;

import java.sql.*;
import java.io.*;

public class QueryFilmDatabase {
    
    public static void main (String args [])
            throws SQLException, IOException {

            // the following statement loads the Oracle jdbc driver

          try {
                  // Load the Oracle JDBC driver
                  Class.forName ("oracle.jdbc.driver.OracleDriver");
              } catch (ClassNotFoundException e) {
                  System.out.println ("Could not load the driver");
          }
          
          System.out.println("Oracle 11g Express JDBC Driver Registered!");
          
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
                            ("jdbc:oracle:thin:"+user+"/"+pass+"@"+host+":1521/"+servicename);

              // Querying and changing the table data
              // Creating a statement lets us issue commands against the connection.


              // Update E-mails
              update_emails(conn);

              // Print Results
              result_tables(conn);
          } catch (SQLException e) {

              System.out.println("Connection Failed! Check output console\n");
              e.printStackTrace();
              return;
          } finally { // ensure resultSet, statement and connection are closed
              try {                  
                  conn.close();
                  System.out.println("Connection closed...");
              } catch ( Exception exception ) {
                  exception.printStackTrace();
              }
              
          } // end finally// end try - catch          
    } // end main



    public static void update_emails(Connection conn)
                     throws SQLException {

        // A PL/SQL stored procedure is required for updating individual e-mails

              CallableStatement cs_ue = conn.prepareCall("{call update_email(?,?,?)}");
              cs_ue.setInt(1, 1002);
              cs_ue.setInt(2, 2);
              cs_ue.setString(3,"gavinconran@yahoo.co.uk");
              cs_ue.execute();

              System.out.println("Email updated");

              //call get_email to check that the change has occurred
              CallableStatement cs_ge = conn.prepareCall("{call get_email(?,?,?)}");
              cs_ge.registerOutParameter(3, java.sql.Types.VARCHAR);
              cs_ge.setInt(1, 1002);
              cs_ge.setInt(2, 2);
              cs_ge.execute();

              System.out.println("Email check: "+ cs_ge.getString(3));
    } // end update_emails

    public static void result_tables(Connection conn)
               throws SQLException {
            Statement s = conn.createStatement();

            System.out.println("Table Results");

            // Retrieve rows from Actors table
            ResultSet result=s.executeQuery(
                    "Select a.anum, a.person.p_name, a.person.p_phones.home_phone From Actors a");
            System.out.println("Actor Results: ");
            while(result.next()) {
                System.out.println(result.getString(1) +"  "+ result.getString(2) +"  "+result.getString(3) );
            }

            // Retrieve an attribute of a user-defined object
            result=s.executeQuery(
                    "Select f.filmtitle, f.earnings.asian_earnings from film f");
            System.out.println("Film Results: ");
            while(result.next()) {
                System.out.println(result.getString(1) +"  "+ result.getString(2) );
            }

            // Using a function from a user-defined object to print out Gross Total Earnings for all films
            result=s.executeQuery(
                    "Select f.filmtitle, f.earnings.gross_total_earnings() from film f");
            System.out.println("Gross Total Earnings Results: ");
            while(result.next()) {
                System.out.println(result.getString(1) +"  "+ result.getString(2) );
            }

            // Retrieving an attribute of a user-defined object to print out Asian earnings for all films
            result=s.executeQuery(
                    "Select f.filmtitle, f.earnings2.asian_earnings from film2 f");
            System.out.println("Asian Earnings: ");
            while(result.next()) {
                System.out.println(result.getString(1) +"  "+ result.getString(2) );
            }

            //Using a function from a user-defined object to print out Gross Total earnings for all films
            result=s.executeQuery(
                    "Select f.filmtitle, f.earnings2.gross_total_earnings() from film2 f");
            System.out.println("Gross Total Earnings: ");
            while(result.next()) {
                System.out.println(result.getString(1) +"  "+ result.getString(2) );
            }

            // Using a function from a user-defined object to print out Net Total earnings for all films
            result=s.executeQuery(
                    "Select f.filmtitle, f.earnings2.net_earnings(0.20) from film2 f");
            System.out.println("Net Total Earnings: ");
            while(result.next()) {
                System.out.println(result.getString(1) +"  "+ result.getString(2) );
            }

            // Print E-mails. Individual elements of a VARRAY can't be accessed in SQL, only in a PL / SQL Block
            CallableStatement cs_ge = conn.prepareCall("{call get_email(?,?,?)}");
            cs_ge.registerOutParameter(3, java.sql.Types.VARCHAR);
            cs_ge.setInt(1, 1002);
            cs_ge.setInt(2, 2);
            cs_ge.execute();

            System.out.println("Result from get_mail: "+ cs_ge.getString(3));
            result.close();

    } // end result_tables



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
    } // end readEntry

} // end class
