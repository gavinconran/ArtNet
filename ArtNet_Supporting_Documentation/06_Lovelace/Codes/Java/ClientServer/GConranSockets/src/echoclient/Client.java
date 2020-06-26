package echoclient;

import java.io.*;
import java.net.*;
import java.io.File;
import java.util.Scanner;
import java.util.Formatter;

public class Client {

    public static void main(String[] args) throws IOException {

        System.out.printf( "Hello World1!\n" );

    if (args.length < 2) {
       System.out.println("EchoBufferClient usage:\n" +
             "java EchoBufferClient host port messageLength\n" +
             "Example:\n" +
             "java EchoBufferClient 127.0.0.1 4444");
       System.exit(1);
   }
    // Declare input parameters
    String host  = args[0];    // Host IP address
    // Port number
    int port = Integer.parseInt(args[1]);

    // Socket abstration with output from server & input server streams
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
    System.out.printf( "The Filename is: dds-asst-data-2.txt\n");
    //Open the text file
    // File testFile = new File
             // ( "c:\\users\\the conrans\\gavin\\dds-asst-data-2.txt");
    File testFile = new File( "dds-asst-data-2.txt");
    // File testFile = new File
           //  ( "dds-asst-data-2.txt");
    System.out.printf( "Opened File \n" );

    Scanner output = new Scanner( testFile );
    Formatter results = new Formatter( "results.txt" );
    String[] sendArray = new String[288];
    String[] receiveArray = new String[288];
    double timeStart;
    double timeEnd;
    double timeTaken;
    double timeTotal;

    // Run the test 1 time(s)
    for (int loop = 0; loop < 1; loop++){
        // record the sending start time
        thisTiming.recordTimeStartSending();

        // Send records to server
        System.out.println("Preparing to send data");
        for  (int i = 0; i < 288; i++){
            sendArray[i] = output.nextLine();
            // System.out.println("sendArray: " + sendArray[i]);
            outWriter.println(sendArray[i]);
        }

        // Receive records from Server
        System.out.println("Preparing to receive data");
        for  (int i = 0; i < 288; i++){
            receiveArray[i] = inReader.readLine();
            // System.out.println("receiveArray: " + receiveArray[i]);
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

    // Average time
    // System.out.println("Average time: " +
       //     thisTiming.getTimeTotal()/1 + " milliseconds");

    outWriter.close();  // close outWriter
    inReader.close();   // close inReader
    echoSocket.close(); // close echoSocket
    results.close();    // close results file
}

}
