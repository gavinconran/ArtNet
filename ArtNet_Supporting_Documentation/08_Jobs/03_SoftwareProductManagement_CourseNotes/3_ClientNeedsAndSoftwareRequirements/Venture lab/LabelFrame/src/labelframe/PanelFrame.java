
package labelframe;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class PanelFrame extends JFrame {

    private JPanel buttonJPanel;
    private JButton[] buttons;

    // no argument constructor
    public PanelFrame() {

        super( "Panel Demo" );
        buttons = new JButton[ 4 ];
        buttonJPanel = new JPanel();
        buttonJPanel.setLayout( new GridLayout( 1, buttons.length ) );

        // create and add buttons
        for ( int count = 0; count < buttons.length; count++ ) {
            buttons[ count ] = new JButton( "Button " + ( count + 1 ) );
            buttonJPanel.add( buttons[ count ] );
        } // end for

        add( buttonJPanel, BorderLayout.SOUTH ); // add button to JFrame

    } // end panelFrame constructor
} // end class PanelFrame
