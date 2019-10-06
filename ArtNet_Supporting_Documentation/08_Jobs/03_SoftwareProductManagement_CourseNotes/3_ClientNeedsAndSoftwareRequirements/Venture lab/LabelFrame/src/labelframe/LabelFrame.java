
package labelframe;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class LabelFrame extends JFrame {

    private JLabel label1; // Jlabel with just text
    private JLabel label2; // JLabel constructed with text and icon
    private JLabel label3; // JLabel with added text and icon

    // LabelFrame constructor adds JLabels to JFrame
    public LabelFrame() {

        super( "Testing JLabel" );
        setLayout( new FlowLayout() ); // set frame layout

        // JLabel constructor with a string argument
        label1 = new JLabel( "Label with text" );
        label1.setToolTipText( "This is label1");
        add( label1 ); // add label1 to JFrame

        // JLabel constructor vwith string,Icon and alignment arguments
        Icon bug = new ImageIcon( getClass().getResource( "man.jpg" ));
        label2 = new JLabel( "Label with text and icon", bug, SwingConstants.LEFT );
        label2.setToolTipText( "This is label2");
        add( label2 ); // add label2 to JFrame

        label3 = new JLabel();
        label3.setText( "Label with icon and text at bottom" );
        label3.setIcon( bug );
        label3.setHorizontalTextPosition(  SwingConstants.CENTER );
        label3.setVerticalTextPosition( SwingConstants.BOTTOM );
        label3.setToolTipText( "This is label3 " );
        add( label3 );
    } // end LabelFrame constructor

} // end class labelFrame
