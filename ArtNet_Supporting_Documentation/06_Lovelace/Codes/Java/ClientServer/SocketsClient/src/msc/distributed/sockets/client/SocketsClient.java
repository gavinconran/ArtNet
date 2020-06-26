package msc.distributed.sockets.client;

import java.io.*;
import java.net.*;
import java.io.File;
import java.util.Scanner;
import java.util.Formatter;

public class SocketsClient {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {

        System.out.printf( "Client Starting\n" );

        if (args.length < 2) {
            System.out.println("EchoBufferClient usage:\n" +
                    "java EchoBufferClient host port messageLength\n" +
                    "Example:\n" +
                    "java EchoBufferClient 127.0.0.1 4444");
            System.exit(1);
        }
        // Get input parameters
        String host  = args[0];                         // Host (Server) IP address
        int port = Integer.parseInt(args[1]);           // Server Port number

        // Socket abstraction with output from server & input server streams
        Socket         echoSocket = null;
        PrintWriter outWriter = null;
        BufferedReader inReader = null;

        // create a Timings object and assign it to thisTiming
        Timings thisTiming = new Timings();

        // Record the set up start time
        thisTiming.recordTimeStartSetUp();

        try {
            // Create ClientSocket object echoSocket
            echoSocket = new Socket(host, port);
            //client output stream is connected to Server's input stream
            outWriter = new PrintWriter(echoSocket.getOutputStream(), true);
            //client  input stream is connected to Server's output stream
            inReader  = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " +
                           "the connection to: " + host);
            System.exit(1);
        }

        // record the time taken to set up
        thisTiming.recordSetUpTime();

        // Open Test File
        
        //Open the text file
        File testFile = new File( "dds-asst-data-2.txt");
        System.out.printf( "Opened File: dds-asst-data-2.txt\n");

        Scanner output = new Scanner( testFile );
        Formatter results = new Formatter( "results.txt" );
        String[] sendArray = new String[288];
        String[] receiveArray = new String[288];

        // Run the test 1 time(s)
        for (int loop = 0; loop < 1; loop++){
            // record the sending start time
            thisTiming.recordTimeStartSending();

            // Send records to server
            System.out.println("Preparing to send data");
            for  (int i = 0; i < 288; i++){
                sendArray[i] = output.nextLine();
                outWriter.println(sendArray[i]);
            }

            // Receive records from Server
            System.out.println("Preparing to receive data");
            for  (int i = 0; i < 288; i++){
                receiveArray[i] = inReader.readLine();
            }

            // record the time when all data has been received from server
            thisTiming.recordTimeEnd();
            thisTiming.getTimeTaken();
            thisTiming.recordTimeTotal();
            // display the test start time, end time and the total time taken
            System.out.println("TimeStart:" + thisTiming.getTimeStart() +
                    ",  TimeEnd:" + thisTiming.getTimeEnd() +
                    ", Time Taken:" + thisTiming.getTimeTaken() + " milliseconds");

            results.format("TimeStart:" + thisTiming.getTimeStart() +
                    ",  TimeEnd:" + thisTiming.getTimeEnd() +
                    ", Time Taken:" + thisTiming.getTimeTaken() + " milliseconds\n");


            outWriter.flush();
        
        }// end test loop

        outWriter.close();  // close outWriter
        inReader.close();   // close inReader
        echoSocket.close(); // close echoSocket
        results.close();    // close results file
    }

}
