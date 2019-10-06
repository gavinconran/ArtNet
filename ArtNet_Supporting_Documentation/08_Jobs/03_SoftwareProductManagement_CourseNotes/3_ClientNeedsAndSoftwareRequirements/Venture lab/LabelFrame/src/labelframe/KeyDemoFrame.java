
package labelframe;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextArea;


public class KeyDemoFrame extends JFrame implements KeyListener {

    private String line1 = ""; // first line of textarea
    private String line2 = ""; // second line of textarea
    private String line3 = ""; // third line of textarea
    private JTextArea textArea; // textarea to display output

    // KeyDemoFrame constructor
    public KeyDemoFrame() {

        super( "Demonstrating Keystroke Events" );

        textArea = new JTextArea( 10, 15 ); // set up JTextArea
        textArea.setText( "Press any key on the keyboard..." );
        textArea.setEnabled( false );
        textArea.setDisabledTextColor( Color.BLACK );
        add( textArea );

        addKeyListener( this );
    } // end KeyDemoFrame constructor

    // handle press of any key
    public void keyPressed( KeyEvent event ) {

        line1 = String.format( "Key pressed: %s",
                KeyEvent.getKeyText( event.getKeyCode() ) );
        setLines2and3( event );
    }

    // handle release of any key
    public void keyReleased( KeyEvent event ) {

        line1 = String.format( "Key released: %s",
                KeyEvent.getKeyText( event.getKeyCode() ) );
        setLines2and3( event );
    } // end method keyReleased// handle release of any key

    public void keyTyped( KeyEvent event ) {

        line1 = String.format( "Key released: %s",
                KeyEvent.getKeyText( event.getKeyCode() ) );
        setLines2and3( event );
    } // end method keyTyped

    public void setLines2and3( KeyEvent event ) {

        line2 = String.format( "This key is %san action key",
                ( event.isActionKey() ? "none" : "not " ) );

        String temp = KeyEvent.getKeyModifiersText( event.getModifiers() );

        line3 = String.format( "Modifier keys pressed: %s",
                ( temp.equals( "" ) ? "none" : temp ) ); // output modifiers

        textArea.setText( String.format( "%s\n%s\n%s\n",
                line1, line2, line3 ) ); // output three lines of text

    } // end method setLines2and3

} // end class KeyDemoFrame
