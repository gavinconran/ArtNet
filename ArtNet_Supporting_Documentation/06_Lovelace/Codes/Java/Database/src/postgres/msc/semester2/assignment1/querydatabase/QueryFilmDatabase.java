package postgres.msc.semester2.assignment1.querydatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryFilmDatabase {
    
    public static void result_tables(Connection conn)
            throws SQLException {
         Statement s = conn.createStatement();

         System.out.println("Table Results");

         // Retrieve rows from Actors table
         ResultSet result=s.executeQuery("Select a.anum, a.name, a.DebutFilmTitle From Actors a");
         System.out.println("Actor Results: ");
         while(result.next()) {
             System.out.println(result.getString(1) +"  "+ result.getString(2) +"  "+result.getString(3) );
         }
         
         // Retrieve an attribute of a user-defined object
         result=s.executeQuery("Select f.filmtitle, f.earnings from film f");
         System.out.println("Film Results: ");
         while(result.next()) {
             System.out.println(result.getString(1) +"  "+ result.getString(2) );
         }
    }
    
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
         
              // use FILMS db
              connection = DriverManager.getConnection(
                      "jdbc:postgresql://127.0.0.1:5432/films", "postgres",
                      "postgres");
              
              System.out.println("Connected and Using Films Database...");
        
           // Print Results
              result_tables(connection);

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
              }
              
          } // end finally// end try - catch                 
    } // end main
    
    

}
