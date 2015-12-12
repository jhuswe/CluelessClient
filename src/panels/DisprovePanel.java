package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.*;

public class DisprovePanel extends JPanel
{
	
	protected JLabel dpLabel = new JLabel( "DISPROVE" );	
	public JButton disproveButton = new JButton( "Make Disprove" );

	public ArrayList<JCheckBox> checkBox;
	
	public List<Card> playerCards;
	
	public DisprovePanel()
	{
		super();
		setLayout( new BorderLayout() );
		createComponents();
	}
	
	public void createComponents()
	{
		JPanel mainPane = new JPanel( new FlowLayout(FlowLayout.CENTER) );
		disproveButton.setEnabled (false);
				
		for( int i = 0; i < playerCards.length; i++ )
		{
			JPanel row = new JPanel( new FlowLayout( FlowLayout.RIGHT ) );
			JLabel lb = new JLabel( SUSPECTS[i] );
			JCheckBox box = new JCheckBox();
			row.add( lb );
			row.add( box );
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			SuspectPane.add( row, gbc );
		}
		
		
		mainPane.add( SuspectPane, BorderLayout.LINE_START );
		
		JPanel buttonPane = new JPanel( new BorderLayout() );
		buttonPane.add( okay, BorderLayout.LINE_END );
		buttonPane.add( accusationButton, BorderLayout.LINE_START );
		
		add( mainPane, BorderLayout.CENTER );
		add( buttonPane, BorderLayout.PAGE_END );
	}
	
	public static void main( String a[] ) 
	{
		JFrame frame = new JFrame();
		frame.add( new DisprovePanel() );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}
	
}
