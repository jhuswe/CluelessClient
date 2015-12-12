package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.Card;

public class SuggestionAccusationPanel extends JPanel
{
	public Card[] WEAPONS = {
		Card.KNIFE, Card.CANDLE_STICK, Card.ROPE,
		Card.LEAD_PIPE, Card.WRENCH, Card.REVOLVER };
	
	public Card[] SUSPECTS = { 
		Card.MISS_SCARLET, Card.COL_MUSTARD, Card.MRS_WHITE, 
		Card.MR_GREEN, Card.PROF_PLUM, Card.MRS_PEACOCK };
	
	public JButton accusationButton = new JButton( "Make Accusation" );
	public JButton suggestionButton = new JButton( "Make Suggestion" );
	public ArrayList<JCheckBox> weaponBox = new ArrayList<JCheckBox>();
	public ArrayList<JCheckBox> suspectBox = new ArrayList<JCheckBox>();
	
	public SuggestionAccusationPanel()
	{
		setLayout( new BorderLayout() );
		createComponents();
	}
	
	public void createComponents()
	{	
		JPanel mainPane = new JPanel( new BorderLayout() );
		
		accusationButton.setEnabled( false );
		suggestionButton.setEnabled( false );
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHEAST;
		
		JPanel SuspectPane = new JPanel( new GridBagLayout() );
		JPanel WeaponPane = new JPanel( new GridBagLayout() );
		
		for( int i = 0; i < SUSPECTS.length; i++ )
		{
			JPanel row = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
			JLabel lb = new JLabel( SUSPECTS[i].getName() );
			JCheckBox box = new JCheckBox();
			suspectBox.add( box );
			row.add( lb );
			row.add( box );
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			SuspectPane.add( row, gbc );
		}
		
		for( int i = 0; i < WEAPONS.length; i++ )
		{
			JPanel row = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
			JLabel lb = new JLabel( WEAPONS[i].getName() );
			JCheckBox box = new JCheckBox();
			weaponBox.add( box );
			row.add( lb );
			row.add( box );
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			WeaponPane.add( row, gbc );
		}
		
		mainPane.add( SuspectPane, BorderLayout.LINE_START );
		mainPane.add( WeaponPane, BorderLayout.LINE_END );
		
		JPanel buttonPane = new JPanel( new BorderLayout() );
		buttonPane.add( suggestionButton, BorderLayout.LINE_END );
		buttonPane.add( accusationButton, BorderLayout.LINE_START );
		
		add( mainPane, BorderLayout.CENTER );
		add( buttonPane, BorderLayout.PAGE_END );
	}
	
}
