package panels;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Card;

public class MoveMakingPanel extends JPanel 
{
	boolean init;
	JButton okayButton;
	JCheckBox loc0;
	JCheckBox loc1;
	JCheckBox loc2;
	
	JLabel noMove = new JLabel( "No Move Available");
	
	public MoveMakingPanel()
	{
		super();
		noMove.setEnabled( false );
		init = true;
	}
	
	public void createComponents( int[] moves )
	{
		if( init )
			removeAll();
		
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		
		if( moves.length == 0 || moves == null )
		{
			noMove.setEnabled( true );
			this.add( noMove );
		}
		else if( moves.length == 1 )
		{
			loc0 = new JCheckBox( (Card.getCard( moves[0] )).getName() );
			this.add( loc0 );
		}
		else if( moves.length ==2 )
		{
			loc1 = new JCheckBox( (Card.getCard( moves[1] )).getName() );
			this.add( loc1 );
		}
		else if( moves.length == 3 )
		{
			loc2 = new JCheckBox( (Card.getCard( moves[2] )).getName() );
			this.add( loc2 );
		}
		this.add( okayButton );
	}
}
