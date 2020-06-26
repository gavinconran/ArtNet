// http://www.vogella.com/tutorials/MySQLJava/article.html
// https://marksman.wordpress.com/2009/03/01/setting-up-mysqljdbc-driver-on-ubuntu/
// https://cs.fit.edu/~mmahoney/cis5100/examples/

package mysql.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

 
class JDBCTest {
    
    // database URL
    static final String DATABASE_URL = "jdbc:mysql://localhost/books?";
    
    private static final String user_deitel = "deitel";
    private static final String password_deitel = "deitel";
     
//    private static final String user = "root";
//    private static final String password = "gavint";
    
    private static void writeResultSet(ResultSet resultSet) throws SQLException {
                
        // It is possible to get the columns via name
        // also possible to get the columns via the column number
        // which starts at 1
        // e.g. resultSet.getSTring(2);
            
        // process query results
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        System.out.println( "Authors Table of Books Database:\n" );
            
        for ( int i = 1; i <= numberOfColumns; i++ )
            System.out.printf( "%-8s\t", metaData.getColumnName( i ) );
        System.out.println();
            
        while ( resultSet.next()) {
            for ( int i = 1; i <= numberOfColumns; i++ )
                System.out.printf( "%-8s\t", resultSet.getObject( i ) );
            System.out.println();
        } // end while
    } // end writeResultSet
    
 
    public static void main(String args[]) {
        
        Connection connection = null;  // manages connection
        Statement statement = null;    // query statement
        ResultSet resultSet = null;    // manages results
        
        // connect to database books and query database
        try {
            // establish connection  to database
            connection = DriverManager.getConnection(
                    DATABASE_URL, user_deitel, password_deitel);
            
            // create Statement for querying database
            statement = connection.createStatement();
            
            // query database
            resultSet = statement.executeQuery(
                    "SELECT AuthorID, FirstName, LastName FROM authors");
            
            writeResultSet(resultSet);
            
        } catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        } finally { // ensure resultSet, statement and connection are closed
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch ( Exception exception ) {
                exception.printStackTrace();
            }
            
        } // end finally
    }
}
