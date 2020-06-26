package minesweeper.server;

import java.net.Socket;

public interface Handler {

    // Factory method
    public static  CommsHandler makeHandler( Socket socket ) {
        return new CommsHandler( socket );
    }
    
    // Client to Server
    public String handleRequest();
    
    // Server to Client
    public void handleResponse( String message );
    
    public void closeConnection();
    
    public int getClientPortNumber();
    
}
