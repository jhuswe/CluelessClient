package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
	public JLabel udpLabel = new JLabel( "User Decision Options" );
	
	public SuggestionAccusationPanel suggestionAccusationPanel;
	
	public UserDecisionPanel() 
	{
		super();
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		suggestionAccusationPanel = new SuggestionAccusationPanel();
		
		JPanel labelPane = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
		udpLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		labelPane.add( udpLabel );
		
		add( labelPane );
		add( suggestionAccusationPanel );
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
