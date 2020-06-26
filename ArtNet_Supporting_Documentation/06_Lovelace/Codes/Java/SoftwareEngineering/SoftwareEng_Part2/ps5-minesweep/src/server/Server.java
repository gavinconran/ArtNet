package server;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class Server extends JFrame {

    /**
     * Mine sweeper Server
     * 
     * Thread-safe strategy
     * 1. Thread-safe Communication with the GUI uses Containment via the displayMessage method
     * 2. The Thread-safe approach for the Board data-type takes advantage of immutable data types within the board and protects mutable
     * data by using the keyword 'synchronized' in the method signature of those methods that may mutate data 
     * 3. Each client communicates with a server thread which is spawned as soon as the client initially connects to the server. 
     * This is the essence of a multi-threaded server allowing a number of clients to concurrently communicate with the server.
     */
    private static final long serialVersionUID = 1L;
    
    private static final int PORT = 4444;
    private static final int MAX_PLAYERS = 10;
    
    private Board board; // (Mine Sweeper) board
    private JTextArea outputArea; // for outputting moves
    private ServerSocket server; // server socket to connect with clients
    private ExecutorService runGame; // will run players
    private boolean debugServer;   // debug mode?
    
    // set up server and GUI that displays messages
    public Server(boolean debug, List<Integer> params) {
        
        super( "MineSweeper Server" ); // set title of window        
        debugServer = debug;       // set debug mode  
        board = Board.makeBoard( params, debugServer );    // Create a new Board              
        runGame = Executors.newCachedThreadPool();     // create ExecutorService for Players
        
        try {
            server = new ServerSocket( PORT, MAX_PLAYERS );
        } catch (IOException ioException ) {
            ioException.printStackTrace();
            System.exit(1);
        } // end try-catch
        
        outputArea = new JTextArea(); // create JTextArea for output
        add( outputArea, BorderLayout.CENTER );
        outputArea.setText( "Server awaiting connections\n" );
        
        setSize( 300, 300 ); // set size of window
        setVisible( true ); // show window
        
    } // end constructor  
    
    // wait for two connections so game can be played
    public void execute() {
        // connect clients
        for ( int i = 0; i < MAX_PLAYERS; i++ ) {
            try { // wait for connection, create Player, start runnable
                runGame.execute( new Player( server.accept(), i) ); // execute player runnable
            } catch ( IOException ioException ) {
                ioException.printStackTrace();
                System.exit( 1 );
            } // end catch
        } // end for
        displayMessage( "Max players connected\n" );
    }
    
    // private inner class Player manages each Player as a runnable
    private class Player implements Runnable {        
        private Handler commsHandler; // manages all communication between server and client
        private InputValidator validator = new InputValidator(); // create an input validator
        
     // set up Player thread
        public Player( Socket socket, int number ) {
            // initiate commsHandler
            commsHandler = new CommsHandler( socket );
        } // end Player constructor
        
        @Override
        public void run() {               
            try {
                String playerNumber = board.addPlayer(); // add player to board
                displayMessage( "Player " + playerNumber + " connected to " + commsHandler.getClientPortNumber() + " number\n" );
                // send "Welcome" message to client
                commsHandler.handleResponse("Welcome to Minesweeper. Players: " + playerNumber + " including you."); 
                commsHandler.handleResponse("Board: " + board.getCols() + " columns by " + board.getRows() + " rows. Type 'help' for help."); 
                
                boolean active = true;  // variable for active player
                while ( active ) { // while client is active
                    String messageFromClient = commsHandler.handleRequest(); // receive message from client
                    displayMessage( "Server received " + messageFromClient + " from Player " + playerNumber + "\n" ); // display on server
                    active = dealWithInputFromClient( messageFromClient, playerNumber );      
                } // end while loop
            } finally {
                commsHandler.handleResponse( "Game Over." );
                commsHandler.closeConnection();
            } // end finally  
        } // end run
        
        /**
         *  Deals with command from client
         * 
         *  @param messageFromClient: input String which originated from a Client
         *  @param playerNumber: playerNumber
         *  @return : boolean true if player still active and false for inactive player
         */
        private boolean dealWithInputFromClient( String messageFromClient, String playerNumber ) {
                        
            if (!validator.validate(messageFromClient)) { // Invalid input
                commsHandler.handleResponse( "Invalid Input" );
            } else if (messageFromClient.equals("bye")) { // valid input: bye
                try {
                    displayMessage( "Server Terminating Player " + playerNumber + "\n" );
                    terminatePlayer();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit( 1 );
                } 
            } else if (messageFromClient.equals("help")) { // valid input: help
                commsHandler.handleResponse( "Commands: look, dig, flag, deflag, bye" );
            }
            else { // valid input: board related commands
                // return server generated message back to client 
                String[] fromServer = handleClientToBoardCommand(messageFromClient);
                // send output back to client
                for (String line: fromServer) {
                    commsHandler.handleResponse( line ); // send input back to Client
                    if (line.equals("BOOM!")) { // client has triggered an explosion
                        try {                                        
                            if (!debugServer) {
                                displayMessage( "Server Terminating Player " + playerNumber + " because of BOOM\n" );
                                terminatePlayer();
                                return false; // stop while loop
                            } else 
                                displayMessage( "In Debug Mode: " + playerNumber + " Still Alive\n" );
                        } catch (IOException e) {
                            e.printStackTrace();
                        } // close connection
                    } // end BOOM if    
                } // end for loop  
            } // end message handling if
            return true; // player still active
        }
        
        /**
         * Helper method: terminates connection and ties up loose ends
         */
        private void terminatePlayer() throws IOException {
            
            board.removePlayer(); // remove player from the board
            commsHandler.handleResponse( "Terminating Connection" );
            commsHandler.closeConnection();
        } // end method
        
        /**
         * Handler to deal with the valid command from client for board interaction
         * make requested mutations on game state it applicable, then return
         * appropriate message to the user
         * 
         *  @param input: input String which originated from a Client
         *  @return : String which is an instruction from the server in response to the input
         */
        private String[] handleClientToBoardCommand(String input) {
            
            String[] tokens = input.split(" ");
            if (tokens[0].equals("look")) {                
                return board.look();
            } else {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                if (tokens[0].equals("dig")) {
                    // 'dig x y' request
                    return board.dig(x, y); 
                } else if (tokens[0].equals("flag")) {
                    // 'flag x y' request
                    return board.flag(x, y); 
                } else if (tokens[0].equals("deflag")) {
                    // 'de-flag x y' request
                    return board.deflag(x, y); 
                }
            } // end if
            //should never get here
            return new String[0]; 
        } // end method
    }  //end Player class 
    
    
    /**
     * Helper method: manipulates displayArea in the event-dispatch thread
     * 
     * @param messageToDisplay String
     */
//    private void displayMessage( final String messageToDisplay ) {
//        SwingUtilities.invokeLater(
//                new Runnable() {
//                    public void run() { // updates displayArea
//                        outputArea.append( messageToDisplay ); // display message
//                    } // end method run
//                } // end anonymous inner class
//            ); // end call to SwingUtilities.invokeLater
//    } // end method displayMessage
    
    /**
     * Helper method: manipulates displayArea in the event-dispatch thread
     * Uses a LAMBDA function rather than void run()
     * 
     * @param messageToDisplay String
     */
    private void displayMessage( final String messageToDisplay ) {
        SwingUtilities.invokeLater(() -> {
                    outputArea.append( messageToDisplay ); // display message
                } // lambda function
            ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage
        
    /**
     * Start a MinesweeperServer running on the specified port, with either a random new board or a
     * board loaded from a file. Either the file or the size argument must be null, but not both.
     * 
     * @param debug The server should disconnect a client after a BOOM message if and only if this
     *        argument is false.
     * @param size If this argument is not null, start with a random board of size size * size.
     * @param file If this argument is not null, start with a board loaded from the specified file,
     *        according to the input file format defined in the JavaDoc for main().
     * @param port The network port on which the server should listen.
     */
    public static void runMinesweeperServer(boolean debug, File file, Integer size, int port)
            throws IOException
    {
        final int DEFAULT_SIZE = 10;
        // Declare list of parameters to hold dimensions and bomb placements (if present) 
        List<Integer> params;
        
        int mySize = 0;
        if (size == null && file == null) { // SIZE or File NOT specified
            params = new ArrayList<Integer>();
            params.add( DEFAULT_SIZE );
            params.add( DEFAULT_SIZE );
            params.addAll(Util.generateBombs( DEFAULT_SIZE , debug));
        } else if (size == null && file != null) { // File specified 
            size = mySize;
            params = Util.readFile(file);
        } else {  // Size specified
            params = new ArrayList<Integer>();
            params.add(size);
            params.add(size);
            params.addAll(Util.generateBombs( size , debug));
        }
        
        // start server
        Server server = new Server(debug, params);
        server.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        server.execute();
        
    }
    
    /**
     * Start a MinesweeperServer running on the default port (4444).
     * 
     * Usage: MinesweeperServer [DEBUG [(-s SIZE | -f FILE)]]
     * 
     * The DEBUG argument should be either 'true' or 'false'. The server should disconnect a client
     * after a BOOM message if and only if the DEBUG flag is set to 'false'.
     * 
     * SIZE is an optional integer argument specifying that a random board of size SIZE*SIZE should
     * be generated. E.g. "MinesweeperServer false -s 15" starts the server initialized with a
     * random board of size 15*15.
     * 
     * FILE is an optional argument specifying a file pathname where a board has been stored. If
     * this argument is given, the stored board should be loaded as the starting board. E.g.
     * "MinesweeperServer false -f boardfile.txt" starts the server initialized with the board
     * stored in boardfile.txt, however large it happens to be (but the board may be assumed to be
     * square).
     * 
     * The board file format, for use by the "-f" option, is specified by the following grammar:
     * 
     * FILE :== LINE+
     * LINE :== (VAL SPACE)* VAL NEWLINE
     * VAL :== 0 | 1
     * SPACE :== " "
     * NEWLINE :== "\n" 
     * 
     * If neither FILE nor SIZE is given, generate a random board of size 10x10. If no arguments are
     * specified, do the same and additionally assume DEBUG is 'false'. FILE and SIZE may not be
     * specified simultaneously, and if one is specified, DEBUG must also be specified.
     * 
     * The system property minesweeper.customport may be used to specify a listening port other than
     * the default (used by the autograder only).
     */
    public static void main(String[] args) {
        // We parse the command-line arguments for you. Do not change this method.
        boolean debug = false;
        File file = null;
        Integer size = 10; // Default size.
        try {
            if (args.length != 0 && args.length != 1 && args.length != 3)
              throw new IllegalArgumentException();
            if (args.length >= 1) {
                if (args[0].equals("true")) {
                    debug = true;
                } else if (args[0].equals("false")) {
                    debug = false;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            if (args.length == 3) {
                if (args[1].equals("-s")) {
                    try {
                        size = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }
                    if (size < 0)
                        throw new IllegalArgumentException();
                } else if (args[1].equals("-f")) {
                    file = new File(args[2]);
                    if (!file.isFile()) {
                        System.err.println("file not found: \"" + file + "\"");
                        return;
                    }
                    size = null;
                } else {
                    throw new IllegalArgumentException();
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println("usage: MinesweeperServer DEBUG [(-s SIZE | -f FILE)]");
            return;
        }
        // Allocate Port number
        final int port = PORT;
        
        // start server
        try {
            runMinesweeperServer(debug, file, size, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} // end class
