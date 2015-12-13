package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.*;

public class DisprovePanel extends JPanel
{
	
	protected JLabel dpLabel = new JLabel( "Disprove" );	
	public JButton disproveButton = new JButton( "Make Disprove" );

	public ArrayList<JCheckBox> checkBox;
	
	public List<Card> playerCards = new ArrayList<Card>();
	
	boolean init;
	
	public DisprovePanel()
	{
		super();
		setLayout(new BorderLayout() );
		createComponents();
		init = true;
	}
	
	public void createComponents()
	{
		if( init )
			removeAll();
		
		dpLabel.setFont( new Font( "Arial", Font.PLAIN, 20 ) );
		JPanel titlePane = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
		titlePane.add( dpLabel );
		
		this.add( titlePane, BorderLayout.PAGE_START );
		
		disproveButton.setEnabled (false);
		checkBox = new ArrayList<JCheckBox>();
		
		JPanel centerPane = new JPanel();
		centerPane.setLayout( new BoxLayout( centerPane, BoxLayout.Y_AXIS ) );
		for(Card e: playerCards )
		{
			JCheckBox box = new JCheckBox (e.getName());
			checkBox.add( box );
			centerPane.add( box );
		}
		
		this.add( centerPane, BorderLayout.CENTER );
		this.add(disproveButton, BorderLayout.PAGE_END );

	}
	
	public static void main( String a[] ) 
	{
		JFrame frame = new JFrame();
		DisprovePanel dPanel = new DisprovePanel();
			
		
		dPanel.playerCards = new ArrayList<Card>();
		Card weapon = Card.KNIFE;
		Card character = Card.PROF_PLUM;
		Card room = Card.LIBRARY;
		
		dPanel.playerCards.add(weapon);
		dPanel.playerCards.add(character);
		dPanel.playerCards.add(room);
		dPanel.createComponents();
		
		frame.add( dPanel );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}
	
}
