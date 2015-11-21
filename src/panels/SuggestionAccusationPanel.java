package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SuggestionAccusationPanel extends JPanel
{
	public static final String[] WEAPONS = { 
		"Knife", "Candle Stick", "Rope",
		"Lead Pipe", "Wrench", "Revolver" };
	
	public static final String[] SUSPECTS = { 
		"Miss Scarlet", "Col. Mustard", "Mrs. White",
		"Mr. Green", "Prof. Plum", "Mrs. Peacock" };
	
	protected JLabel SuggestionLabel = new JLabel( "SUGGESTION" );
	protected JLabel AccusationLabel = new JLabel( "ACCUSATION" );
	public JButton accusationButton = new JButton( "Make Accusation" );
	public JButton okay = new JButton( "Okay" );
	
	public SuggestionAccusationPanel()
	{
		setLayout( new BorderLayout() );
		createComponents();
	}
	
	public void createComponents()
	{
//		accusationButton.addActionListener( new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{
//				
//			}
//			
//		});
		
		JPanel mainPane = new JPanel( new BorderLayout() );
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHEAST;
		
		JPanel SuspectPane = new JPanel( new GridBagLayout() );
		JPanel WeaponPane = new JPanel( new GridBagLayout() );
		
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
