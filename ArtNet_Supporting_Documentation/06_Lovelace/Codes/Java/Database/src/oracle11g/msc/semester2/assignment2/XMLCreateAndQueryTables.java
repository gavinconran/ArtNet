package oracle11g.msc.semester2.assignment2;

import java.sql.*;
import java.io.*;
import oracle.jdbc.*;
import oracle.xdb.XMLType;
//import oracle.xml.sql.query.OracleXMLQuery;

public class XMLCreateAndQueryTables {
    
    public static void main (String args [])
            throws SQLException, IOException {

            // the following statement loads the Oracle jdbc driver

          try {
              // Load the Oracle JDBC driver
              Class.forName ("oracle.jdbc.driver.OracleDriver");
          } catch (ClassNotFoundException e) {
              System.out.println ("Could not load the driver");
          }
          
          System.out.println("Oracle 11g JDBC Driver Registered!");

          Connection conn = null;

          try {
              String user, pass, host,servicename;
//              user = readEntry("userid  : ");
//              pass = readEntry("password: ");
//              host = readEntry("hostname or ip address: ");
              /*  userid, password and hostname are obtained from the console
              *  It is assumed that the service name is XE and the databse server
              *  listens on the default port 1521.
              */
              user = "system";
              pass = "gavint";
              host = "localhost";
              servicename="xe";
              // Connect to the database
              conn = DriverManager.getConnection
                          //("jdbc:oracle:oci8:"+user+"/"+pass+"@"+host+":1521:xe");
                            ("jdbc:oracle:thin:"+user+"/"+pass+"@"+host+":1521/"+servicename);
              
              /* JDBC default is to commit each SQL statement as it is sent to the database.
              Setting autocommmit=false changes the default
              behaviour so that transactions have to be committed explicity.
              */
              conn.setAutoCommit(false);

              // Creating and populating Actor table
              // Creating a statement lets us issue commands against the connection.

              // Create Tables
              create_tables(conn);
              
              // Query Tables
              query_tables(conn);
              
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
    
    public static void query_tables(Connection conn)
            throws SQLException {

         Statement stmt = conn.createStatement();
         
         /*  The extract function (of the PL/SQL XMLType) takes an XPATH expression as an argument and
         returns an XMLType value.  We must convert it to String by using the getStringVal function.
         */


         // Query 1: Get the title of films  
         ResultSet result = stmt.executeQuery("SELECT a.fnum, " +
                 "a.film.extract('/AFILM/TITLE').getStringval()" +
                 "FROM ASS2_FILM a");
         System.out.println("\n Results from Query1: \n");

         while(result.next())
             System.out.println(result.getString(1) + "  " + result.getString(2) );
         
         // Query 2: Get the titles of all films without the surrounding <title> tag
         result = stmt.executeQuery("SELECT a.fnum, " +
                 "a.film.extract('/AFILM/TITLE/text()').getStringval()" +
                 "FROM ASS2_FILM a");
         System.out.println("\n Results from Query2: \n");

         while(result.next())
            System.out.println(result.getString(1) + "  " + result.getString(2) );
    
         // Query 3: Get the names of all actors who appeared in The Godfather

         /* We can use the existsNode method to retrieve only those names that match the given
           title. The existsNode method takes an XPATH expression as an arguments and
           returns 0 or 1.
         */
         result = stmt.executeQuery("SELECT a.fnum, " +
                "a.film.extract(' /AFILM[TITLE=\"Godfather, The\"]/CAST/PERFORMER/ACTOR').getStringVal() " +
               "FROM ASS2_FILM a ");
         System.out.println("\n Number & Actors of a given Film Title: \n");
         while(result.next())
            System.out.println(result.getString(1) + "  " + result.getString(2) );
    
         // Query 4: Get the title and year of all crime films

         result = stmt.executeQuery("SELECT a.fnum, " +
                  "a.film.extract('/AFILM[GENRES/GENRE=\"Crime\"]/YEAR').getStringVal() " +
                  "FROM ASS2_FILM a " );
         System.out.println("\n Fnum & Year of all Crime Films: \n");
         while(result.next())
            System.out.println(result.getString(1) + "  " + result.getString(2) );
    
         // Query 5: Get the title and year of all films in which Marlon Brando has acted
         result = stmt.executeQuery("SELECT a.fnum, " +
                   "a.film.extract('/AFILM[CAST/PERFORMER/ACTOR=\"Marlon Brando\"]/YEAR').getStringVal() " +
                   "FROM ASS2_FILM a " );
         System.out.println("\n Fnum & Year of all Marlon Brando Films: \n");
         while(result.next())
             System.out.println(result.getString(1) + "  " + result.getString(2) );
         
         // Query 6: Get the title of all films which have only one director
         result = stmt.executeQuery("SELECT a.fnum, " +
                   "a.film.extract('/AFILM/DIRECTORS').getStringVal() " +
                   "FROM ASS2_FILM a " +
                   "WHERE a.film.existsNode('/AFILM/DIRECTORS[2]')=0" );
          System.out.println("\n Fnum & Year of Films WITH ONLY ONE DIRECTOR: \n");
          while(result.next())
             System.out.println(result.getString(1) + "  " + result.getString(2) );


          // Query 7: Get the title and names of directors of all films which have more than one director
          
          result = stmt.executeQuery("SELECT a.fnum, " +
                   "a.film.extract('/AFILM/DIRECTORS').getStringVal() " +
                   "FROM ASS2_FILM a " +
                   "WHERE a.film.existsNode('/AFILM/DIRECTORS[2]')=1" );
          System.out.println("\n Fnum & Year of Films WITH more than ONE DIRECTOR: \n");
          while(result.next())
             System.out.println(result.getString(1) + "  " + result.getString(2) );

          // Query 8: Get the names of persons who have acted in a film and also been the sole director of the same film
//          result = stmt.executeQuery("SELECT a.fnum, " +
//                   "a.film.extract('/AFILM//ACTOR').getStringVal() " +
//                   "FROM ASS2_FILM a " +
//                   "WHERE a.film.extract('/AFILM/DIRECTORS')= a.film.extract('/AFILM/CAST/PERFORMER/ACTOR') " );
//          System.out.println("\n Fnum & people who have acted and directed the same movie: \n");
//          while(result.next())
//             System.out.println(result.getString(1) + "  " + result.getString(2) );
           
    }     
    
    public static void create_tables(Connection conn)
            throws SQLException {

         Statement stmt = conn.createStatement();
         
     //  ----------------   CREATING A TABLE WITH AN XMLTYPE ATTRIBUTE --------

         stmt.executeUpdate("DROP TABLE ASS2_FILM");
         stmt.executeUpdate("CREATE TABLE ASS2_FILM(Fnum VARCHAR(30) " +
                            "CONSTRAINT ASS2_FILM_PK PRIMARY KEY, " +
                            " Film SYS.XMLTYPE," +
                            " creationdate Date) ") ;


         System.out.println ("Database ASS2_FILM created");
         
      // We can insert XMLType data by supplying the corresponding string.
         

         stmt.executeUpdate("INSERT INTO ASS2_FILM VALUES( " +
                        " 'The Godfather', " +
                        "    " +
                        " sys.XMLType.createXML(" +
                         " '<AFILM CREATEDBY=\"Gavin\"> \n " +
                           " <TITLE>Godfather, The</TITLE> \n "  +
                           " <YEAR>1972</YEAR> \n " +
                           " <DIRECTORS>Gavin Conran</DIRECTORS> \n " +
                           " <DIRECTORS>Francis Ford Coppola</DIRECTORS> \n " +
                           " <GENRES> \n " +
                             " <GENRE>Crime</GENRE> \n " +
                             " <GENRE>Drama</GENRE> \n " +
                           " </GENRES> \n " +
                           " <PLOT> Son of a mafia boss takes over when his father is critically wounded in a mob hit. </PLOT> \n " +
                           "<CAST> \n" +
                             "<PERFORMER> \n " +
                               "<ACTOR>Marlon Brando</ACTOR> \n " +
                               "<ROLE>Don Vito Corleone</ROLE> \n " +
                             "</PERFORMER> \n " +
                             "<PERFORMER> \n " +
                               "<ACTOR>Diane Keaton</ACTOR> \n " +
                               "<ROLE>Kay Corleone</ROLE> \n " +
                             "</PERFORMER> \n " +
                             "<PERFORMER> \n " +
                               "<ACTOR>Robert Duvall</ACTOR> \n " +
                               "<ROLE>Tom Hagen</ROLE> \n " +
                             "</PERFORMER> \n " +
                             "<PERFORMER> \n " +
                               "<ACTOR>James Cann</ACTOR> \n " +
                               "<ROLE>Sonny Corleone</ROLE> \n " +
                             "</PERFORMER> \n " +
                           "</CAST> \n " +
                         "</AFILM>'), " +
                         "           " +
                        " DATE'2011-11-05' " +
                                                 " )")  ;
         

         System.out.println ("Inserted XMLtype data: From XML string");
         
     //  --------------  INSERTING XMLType DATA  ----------------------

         // First read the data from file into an instance of XMLType.

//         FileInputStream fis;
        try {
            FileInputStream fis = new FileInputStream("film2_1.xml");
        
         XMLType xmlv = new XMLType(conn, fis);
         System.out.println(xmlv.getStringVal());

         String sqltxt = "INSERT INTO ASS2_FILM VALUES(?, ?, ?)";

         OraclePreparedStatement osqlstmt = (OraclePreparedStatement) conn.prepareStatement(sqltxt);
         osqlstmt.setString(1, "The Godfather I");

         // The 2nd argument  is of type XMLType
         osqlstmt.setObject(2, xmlv);
         new java.sql.Date(0);
         java.sql.Date when = Date.valueOf("2016-11-12");
         osqlstmt.setDate(3, when);
         osqlstmt.execute();

         System.out.println ("Inserted XMLtype data: From File");
         
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

         
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
