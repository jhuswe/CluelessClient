package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserDecisionPanel
	extends JPanel 
{
	public static final String[] SUSPECTS = { "Miss Scarlet", "Col. Mustard", "Mrs. White",
																							"Mr. Green", "Prof. Plum", "Mrs. Peacock" };
	
	public static final String[] ROOMS = { "Hall", "Lounge", "Dinning Room", "Ball Room",
																					"Conservatory", "Billiard Room", "Library", "Study" };
	
	public static final String[] WEAPONS = { "Knife", "Candle Stick", "Rope",
																						"Lead Pipe", "Wrench", "Revolver" };

	public JLabel udpLabel = new JLabel( "User Decision Options" );
	
	public UserDecisionPanel() 
	{
		super( new BorderLayout() );
		add( udpLabel, BorderLayout.PAGE_START );
		add( new SuggestionAccusationPane(), BorderLayout.PAGE_END );
	}
	
	class SuggestionAccusationPane extends JPanel
	{
		protected JLabel SuggestionLabel = new JLabel( "SUGGESTION" );
		protected JLabel AccusationLabel = new JLabel( "ACCUSATION" );
		protected JButton accusationButton = new JButton( "Make Accusation" );
		protected JButton okay = new JButton( "Okay" );
		
		public SuggestionAccusationPane()
		{
			setLayout( new BorderLayout() );
			
			accusationButton.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					
				}
				
			});
			
			JPanel mainPane = new JPanel( new BorderLayout() );
		
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.NORTHEAST;
			
			JPanel SuspectPane = new JPanel( new GridBagLayout() );
			SuspectPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
			
			JPanel WeaponPane = new JPanel( new GridBagLayout() );
			WeaponPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
			
			for( int i = 0; i < SUSPECTS.length; i++ )
			{
				JPanel row = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
				JLabel lb = new JLabel( SUSPECTS[i] );
				JCheckBox box = new JCheckBox();
				row.add( lb );
				row.add( box );
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				SuspectPane.add( row, gbc );
			}
			
			for( int i = 0; i < WEAPONS.length; i++ )
			{
				JPanel row = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
				JLabel lb = new JLabel( WEAPONS[i] );
				JCheckBox box = new JCheckBox();
				row.add( lb );
				row.add( box );
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				WeaponPane.add( row, gbc );
			}
			
			mainPane.add( SuspectPane, BorderLayout.LINE_START );
			mainPane.add( WeaponPane, BorderLayout.LINE_END );
			
			JPanel buttonPane = new JPanel( new BorderLayout() );
			buttonPane.add( okay, BorderLayout.LINE_END );
			buttonPane.add( accusationButton, BorderLayout.LINE_START );
			
			add( mainPane, BorderLayout.CENTER );
			add( buttonPane, BorderLayout.PAGE_END );
		}
		
	}
	
	public static void main( String a[] ) 
	{
		JFrame frame = new JFrame();
		frame.add( new UserDecisionPanel( ) );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}
}
