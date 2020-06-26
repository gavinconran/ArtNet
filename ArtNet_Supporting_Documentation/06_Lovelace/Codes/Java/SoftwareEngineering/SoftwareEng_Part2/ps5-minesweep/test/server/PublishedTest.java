package server;

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Tests basic LOOK and DIG commands and X,Y directions.
 */
public class PublishedTest {

    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 4444;
    
    
    @SuppressWarnings("resource")
    @Test
    public void multiConnectionTest() throws IOException {
        
        // Manually start server
        
        // Connect to server
        Socket socket1 = new Socket(LOCALHOST, PORT);
        Socket socket2 = new Socket(LOCALHOST, PORT);
        
        BufferedReader input1 = new BufferedReader( new InputStreamReader(socket1.getInputStream())); // input from client
        PrintWriter output1 = new PrintWriter(socket1.getOutputStream(), true);   // output to client
        
        
        assertEquals("Welcome to Minesweeper. Players: 1 including you.", input1.readLine());
        assertEquals("Board: 3 columns by 3 rows. Type 'help' for help.", input1.readLine());
        
        BufferedReader input2 = new BufferedReader( new InputStreamReader(socket2.getInputStream())); // input from client
        PrintWriter output2 = new PrintWriter(socket2.getOutputStream(), true);   // output to client
        assertEquals("Welcome to Minesweeper. Players: 2 including you.", input2.readLine());
        assertEquals("Board: 3 columns by 3 rows. Type 'help' for help.", input2.readLine());
        
        String messageToServer = "help";
        String messageFromServer;
        output1.println( messageToServer ); // send message to server
        assertEquals("Commands: look, dig, flag, deflag, bye", input1.readLine());
            
        messageToServer = "look";
        output1.println( messageToServer ); // send message to server

        messageFromServer = input1.readLine(); // receive message from server
        assertEquals("---", messageFromServer);
        messageFromServer = input1.readLine(); // receive message from server
        assertEquals("---", messageFromServer);
        messageFromServer = input1.readLine(); // receive message from server
        assertEquals("---", messageFromServer);
        
        messageToServer = "dig 1 2";
        output2.println( messageToServer ); // send message to server

        messageFromServer = input2.readLine(); // receive message from server
        assertEquals("---", messageFromServer);
        messageFromServer = input2.readLine(); // receive message from server
        assertEquals("---", messageFromServer);
        messageFromServer = input2.readLine(); // receive message from server
        assertEquals("-1-", messageFromServer);
        
        messageToServer = "bye";
        output1.println( messageToServer ); // send message to server
        messageFromServer = input1.readLine(); // receive message from server
        assertEquals("Terminating Connection", messageFromServer);
        
        // Set bomb=true in SquareBoard.java
        messageToServer = "dig 0 1";
        output2.println( messageToServer ); // send message to server
        messageFromServer = input2.readLine(); // receive message from server
        assertEquals("BOOM!", messageFromServer);
        messageFromServer = input2.readLine(); // receive message from server
        assertEquals("Terminating Connection", messageFromServer);


    }
}
