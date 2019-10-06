
package labelframe;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class ButtonFrame extends JFrame {

    private JButton plainJButton; // button with just text
    private JButton fancyJButton; // button with icons

    // ButtonFrame adds Jbuttons to JFrame
    public ButtonFrame() {
        super( "Testing Buttons" );
        setLayout( new FlowLayout() ); // set frame layout

        plainJButton = new JButton( "Plain Button" ); // button with text
        add( plainJButton ); // add plainJButton to JFrame

        Icon bug1 = new ImageIcon( getClass().getResource( "man.jpg" ) );
        Icon bug2 = new ImageIcon( getClass().getResource( "man.jpg" ) );
        fancyJButton = new JButton( "Fancy Button", bug1 ); // set image
        fancyJButton.setRolloverIcon( bug2 ); // set rollover image
        add( fancyJButton ); // add fancyJButton to JFrame

        // create new Buttonhandler for button event handling
        ButtonHandler handler = new ButtonHandler();
        fancyJButton.addActionListener( handler );
        plainJButton.addActionListener( handler );
        
    } // end ButtonFrame constructor

        // create class for button event handling
    private class ButtonHandler implements ActionListener {

        // handle button event
        public void actionPerformed( ActionEvent event ) {

            JOptionPane.showMessageDialog( ButtonFrame.this, String.format(
                    "You pressed: %s", event.getActionCommand() ) );
        } // end method actionPerformed
    } // end private inner class ButtonHandler
} // end class ButtonFrame
