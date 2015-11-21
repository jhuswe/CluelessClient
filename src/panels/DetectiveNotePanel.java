package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DetectiveNotePanel 
	extends JPanel
{
	private static final String[] SUSPECTS = { 
		"Miss Scarlet", "Col. Mustard", "Mrs. White",
		"Mr. Green", "Prof. Plum", "Mrs. Peacock" };
	
	private static final String[] ROOMS = { 
		"Hall", "Lounge", "Dinning Room", "Ball Room",
		"Conservatory", "Billiard Room", "Library", "Study" };
	
	private static final String[] WEAPONS = { 
		"Knife", "Candle Stick", "Rope",
		"Lead Pipe", "Wrench", "Revolver" };
	
	public DetectiveNotePanel()
	{
		super();
		createComponents();
	}
	
	public void createComponents()
	{
		setLayout( new BorderLayout() );
		
		JPanel labelPane = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
		
		JLabel label = new JLabel( "Detective Note" );
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		
		labelPane.add( label );
		
		add( labelPane, BorderLayout.PAGE_START );
		
		add( new NotePanel( "ROOMS" ), BorderLayout.LINE_START );
		add( new NotePanel( "SUSPECTS" ), BorderLayout.CENTER );
		add( new NotePanel( "WEAPONS" ), BorderLayout.LINE_END );
	}

	class NotePanel extends JPanel
	{
		
		public NotePanel( String notePanelName )
		{
			super();
			setLayout( new GridBagLayout() );
			GridBagConstraints gbc = new GridBagConstraints();
			
			JLabel label = new JLabel( notePanelName );
			
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			add( label, gbc );
			
			String[] rowNames = {};
			if( notePanelName.equals("SUSPECTS") )
				rowNames = SUSPECTS;
			if( notePanelName.equals("ROOMS") )
				rowNames = ROOMS;
			if( notePanelName.equals("WEAPONS") )
				rowNames = WEAPONS;
			
			gbc.anchor = GridBagConstraints.NORTHEAST;
			for( int i = 0; i < rowNames.length; i++ )
			{
				CheckBoxRow box = new CheckBoxRow( rowNames[i] );
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				add( box, gbc );
			}
		}
		
	}
	
	class CheckBoxRow extends JPanel
	{
		public CheckBoxRow( String labelName )
		{
			super();
			JLabel label = new JLabel( labelName );
			
			JCheckBox box1 = new JCheckBox();
			JCheckBox box2 = new JCheckBox();
			JCheckBox box3 = new JCheckBox();
			JCheckBox box4 = new JCheckBox();
			JCheckBox box5 = new JCheckBox();
			
			add( label );
			add( box1 );
			add( box2 );
			add( box3 );
			add( box4 );
			add( box5 );
		}	
	}
	
	public static void main( String a[] ) 
	{
		JFrame frame = new JFrame();
		frame.add( new DetectiveNotePanel() );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible( true );
	}
}

