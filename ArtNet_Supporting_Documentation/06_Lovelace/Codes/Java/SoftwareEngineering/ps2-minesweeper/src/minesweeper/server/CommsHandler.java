package minesweeper.server;

/**
 * Client Server Communication Process:
 * 
 * 1.Open a socket (carried out by server and passed to Class constructor as param)
 * 2.Open an input stream and output stream to the socket. (carried out by Class Constructor)
 * 3.Read from and write to the stream according to the server's protocol. (handleRequeat, handleResponse)
 * 4.Close the streams. (closeConnection)
 * 5.Close the socket. (closeConnection)
 * 
 * Only step 3 differs from client to client, depending on the server. The other steps remain largely the same.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommsHandler implements Handler {
    
    // Rep
    private Socket connection; // connection to client
    private BufferedReader input; // input from client
    private PrintWriter output;   // output to client
    
    // Constructor
    public CommsHandler( Socket socket ) {
        connection = socket; // store socket for client
        
        try { // obtain streams from Socket
            input = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            output = new PrintWriter(connection.getOutputStream(), true);
        } catch ( IOException ioException ){ 
            ioException.printStackTrace();
            System.exit( 1 );
        } // end catch    
    }
    
    /**
     * Handler for server input from client
     * 
     *  @return message String
     */
    @Override
    public String handleRequest() {
        String inputString = null;
        try {
            inputString = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } // receive message from client
        return inputString;
    }

    /**
     * Handler for server output to client
     * 
     *  @param message: message String which originated from Server
     */
    @Override
    public void handleResponse( String message ) {
        output.println( message ); // notify client
    }
    
    /**
     * Terminates client server connection
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get Client Port Number
     * 
     * @return port Number
     */
    public int getClientPortNumber() {
        return connection.getPort();
    }

}

