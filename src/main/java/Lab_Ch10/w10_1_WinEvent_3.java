package Lab_Ch10;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


class w10_1_WinEvent_3 extends JFrame implements MouseListener
{
    private JPanel		contentpane;
    private java.util.Random	random;

    private String   path  = "src/main/Java/Lab_Ch10/";
    private String[] birds = {"black.png", "blue.png", "green.png", 
		              "red.png", "white.png", "yellow.png"};

    public w10_1_WinEvent_3()
    {
	setTitle("This is a Frame");
        setSize(700, 400);
        setLocationRelativeTo(null);        
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

	contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new FlowLayout() );
	contentpane.setBackground( Color.GRAY );

	random = new java.util.Random();
	
	// ----- (2) add listeners : notice the difference between (2.1), (2.2), (2.3)

	// ----- (2.1) click anywhere in the frame to add a random bird
	addMouseListener( this );

	// ----- (2.2) for every 5 birds added, pop up a dialog
	contentpane.addContainerListener( new MyContainerListener3() );

	// ----- (2.3) count total no. of birds when closing the frame
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++        
	// addWindowListener( new WindowAdapter() {
	addWindowListener( new WindowListener() {
            @Override
            public void windowOpened( WindowEvent e )		
            { 
		QuickDialog3.show("Click anywhere to add birds");
            }
            @Override
            public void windowClosing( WindowEvent e )		
            { 
		//JFrame frame = (JFrame)e.getWindow();
		//JPanel contentpane = (JPanel)frame.getContentPane();
		int count = contentpane.getComponentCount();
		QuickDialog.show("Total birds = " + count);
            }

            public void windowClosed( WindowEvent e )		{ }
            public void windowActivated( WindowEvent e )	{ }
            public void windowDeactivated( WindowEvent e )	{ }
            public void windowIconified( WindowEvent e )        { }
            public void windowDeiconified( WindowEvent e )      { }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }

    // ----- (1) handlers for MouseListener
    public void mousePressed( MouseEvent e )	{ }
    public void mouseReleased( MouseEvent e )	{ }
    public void mouseEntered( MouseEvent e )	{ }	
    public void mouseExited( MouseEvent e )	{ }

    @Override
    public void mouseClicked( MouseEvent e )	
    {
	int r = random.nextInt(6);
	JLabel label = new JLabel( new ImageIcon(path + birds[r]) );
	contentpane.add( label );
	validate();
    }

    public static void main(String[] args) 
    {
	new w10_1_WinEvent_3();
    }
};

////////////////////////////////////////////////////////////////////////////////////
class QuickDialog3
{
    public static void show(String message)
    {
        JOptionPane.showMessageDialog(new JFrame(), message, "Quick Dialog",
			              JOptionPane.INFORMATION_MESSAGE );
    }
};

////////////////////////////////////////////////////////////////////////////////////
class MyContainerListener3 extends ContainerAdapter
{
    @Override
    public void componentAdded( ContainerEvent e )
    {
	int count = e.getContainer().getComponentCount();
	if (count % 5 == 0) 
	{
            QuickDialog3.show( count + " birds have been added" );

            JLabel label = (JLabel)e.getChild();
            label.setOpaque(true);

            // ----- (3) last argument is transparency degree 
            //           0 = transparent, 255 = opaque
            //label.setBackground( new Color(0, 0, 100, 50) );
	}
    }
};


////////////////////////////////////////////////////////////////////////////////////
