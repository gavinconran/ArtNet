
package labelframe;

import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;

public class GridLayoutFrame extends JFrame implements ActionListener
{
    private JButton[] buttons; // array of buttons
    private static final String[] names =
 { "one", "two", "three", "four", "five", "six" };

    private boolean toggle = true; // toggle between two layouts
    private Container container; // frame container
    private GridLayout gridLayout1; // first gridlayout
    private GridLayout gridLayout2; // second gridlayout

    // no argument constructor
    public GridLayoutFrame()
    {
        super( "GridLayout Demo" );
        gridLayout1 = new GridLayout( 2, 3, 5, 5 ); // 2 by 3; gaps of 5
        gridLayout2 = new GridLayout( 3, 2 ); // 3 by 2; no gaps
        container = getContentPane();
        setLayout( gridLayout1 );
        buttons = new JButton[ names.length ]; // create array of JButtons

        for ( int count = 0; count < names.length; count++ ) {
            buttons[ count ] = new JButton( names[ count ] );
            buttons[ count ].addActionListener( this );
            add( buttons[ count ] );
        } // end for
    } // end GridLayoutFrame constructor

    // handle button events by toggling between layouts
    public void actionPerformed( ActionEvent event ) {

        if ( toggle )
            container.setLayout( gridLayout2 );
        else
            container.setLayout( gridLayout1 );

        toggle = !toggle;
        container.validate();
    }


}
