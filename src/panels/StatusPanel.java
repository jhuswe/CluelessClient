package panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import objects.*;

public class StatusPanel extends JPanel
{
	JLabel title = new JLabel( "Game Status" );
	JLabel waitForPlayerConnect = new JLabel( "Waiting for all Players to connect" );

	public StatusPanel()
	{
		super();
		createComponents();
	}
	
	public void updateStatus( Action action, String playerName )
	{	
		if( action == Action.MAKE_SUGGESTION )
		{
			this.add( new JLabel( "------------------------------") );
			this.add( new JLabel( action.getName() + " turn: " + playerName ) );
		}
		else if( action == Action.DISPROVE )
			this.add( new JLabel( action.getName() + " turn: " + playerName ) );
		else if( action == Action.NO_DISPROVE_MADE )
			this.add( new JLabel( action.name() ) );
	}
	
	public void createComponents()
	{
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS) );
//		setPreferredSize( new Dimension(305, 100) );
		title.setFont( new Font( "Arial", Font.PLAIN, 20 ) );
		JPanel titlePane = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
		titlePane.add( title );
		add( titlePane );
		add( waitForPlayerConnect );
	}
	
	/**
	 * Test !!!
	 */
	public static void main( String a[] ) 
	{
		JFrame frame = new JFrame();
		StatusPanel st = new StatusPanel();
		JScrollPane scroll = new JScrollPane();
		scroll.add( st );
		frame.add( scroll );
		
		st.updateStatus( Action.MAKE_SUGGESTION, Card.MISS_SCARLET.getName() );
		frame.remove( st );
		frame.add(st);
		
		st.updateStatus( Action.DISPROVE, Card.MRS_PEACOCK.getName() );
		frame.remove( st );
		frame.add(st);
		
		st.updateStatus( Action.DISPROVE, Card.MRS_WHITE.getName() );
		frame.remove( st );
		frame.add(st);
		
		st.updateStatus( Action.DISPROVE, Card.COL_MUSTARD.getName() );
		frame.remove( st );
		frame.add(st);
		
		st.updateStatus( Action.DISPROVE, Card.MRS_WHITE.getName() );
		frame.remove( st );
		frame.add(st);
		
		st.updateStatus( Action.NO_DISPROVE_MADE, null );
		frame.remove( st );
		frame.add(st);
		
		st.updateStatus( Action.MAKE_SUGGESTION, Card.COL_MUSTARD.getName() );
		frame.remove( st );
		frame.add(st);
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}
}
