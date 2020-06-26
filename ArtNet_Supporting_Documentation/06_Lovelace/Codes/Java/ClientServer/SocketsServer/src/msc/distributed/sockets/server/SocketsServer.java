package msc.distributed.sockets.server;

import java.net.*;
import java.io.*;

public class SocketsServer {

    public static void main (String args[]) throws IOException {
        // Create ServerSocket object ssock with a port number
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("EchoBufferServer has created ServerSocket." +
                           " Port is 4444");

        while (true) {
            // ServerSocket ssock accepts connection requests from client
            // Result of connection request is a Socket object client
            Socket clientSocket = serverSocket.accept();

            // Input string
            String[] receiveArray = new String[288];

            try {
                // Declare inReader to receive the contents of the file
                BufferedReader inReader   = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream ()));

                //Declare outWriter to Send the contents of the file
                PrintWriter outWriter = new PrintWriter
                        (clientSocket.getOutputStream(), true);

                // Begin test loop
                for (int loop = 0; loop < 1; loop++){
                    // Receive array from Client
                    System.out.println("Preparing to receive data");
                    for  (int i = 0; i < 288; i++){
                        receiveArray[i] = inReader.readLine();
                    // System.out.println("receiveArray: " + receiveArray[i]);
                    }
                    System.out.println("Preparing to send data");
                    // Send array to server
                    for  (int i = 0; i < 288; i++){
                        outWriter.println(receiveArray[i]);
                    }
                    outWriter.flush();
                    System.out.println("Data Sent");
                } // End Test loop

            } catch (IOException e) {}

            clientSocket.close();
        }
    }

}
