package panels;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Card;
import objects.Hallway;
import objects.Location;
import objects.Room;

public class MoveMakingPanel extends JPanel 
{
	boolean init;
	public JLabel title = new JLabel( "Make a Move" );
	public JButton okayButton;
	public ArrayList<JCheckBox> checkBox;
	int chosenLocId = -1;
	
	JLabel noMove = new JLabel( "No Move Available");
	
	public MoveMakingPanel()
	{
		super();
		createComponents( null );
		noMove.setEnabled( false );
		init = true;
	}
	
	public void createComponents( List<Location> moves )
	{
		if( init )
			removeAll();
		
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		
		title.setFont(new Font("Arial", Font.PLAIN, 20));
		JPanel labelPane = new JPanel( new FlowLayout( FlowLayout.CENTER ) ); 
		labelPane.add( title );
		this.add( labelPane );
		
		checkBox = new ArrayList<JCheckBox>();
		
		if( moves == null || moves.size() == 0 )
		{
			noMove.setEnabled( true );
			add( noMove );
		}
		else
		{
			for( int i = 0; i < moves.size(); i++ )
			{
				JCheckBox box =  new JCheckBox( moves.get(i).getName() );
				checkBox.add( box );
				this.add( box );
			
			}
		}
		
		okayButton = new JButton( "Okay" );
		this.add( okayButton );
	}
	
	public static void main( String a[] ) 
	{
		JFrame frame = new JFrame();
		MoveMakingPanel mmPane = new MoveMakingPanel();
		frame.add( mmPane );
		
		ArrayList<Location> loc = new ArrayList<Location>();
		loc.add( new Room(Card.BALL.value()) );
		loc.add( new Hallway(Card.BALL_KITCHEN.value()));
		loc.add( new Hallway(Card.BILLIARD_BALL.value()));
		
		mmPane.createComponents( new ArrayList<Location>() );
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}
}
