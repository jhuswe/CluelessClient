package panels;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserDecisionPanel
	extends JPanel 
{	
	public JLabel udpLabel = new JLabel( "Have a Clue ?!" );
	
	public SuggestionAccusationPanel suggestionAccusationPanel;
	public MoveMakingPanel moveMakingPanel;
	
	public UserDecisionPanel() 
	{
		super();
		createComponents();
	}
	
	public void createComponents()
	{
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		suggestionAccusationPanel = new SuggestionAccusationPanel();
		moveMakingPanel = new MoveMakingPanel();
		
		JPanel labelPane = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
		udpLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		labelPane.add( udpLabel );
		
		add( labelPane );
		add( suggestionAccusationPanel );
	}
	
	public void switchToMoveMakingPanel()
	{
		this.remove( suggestionAccusationPanel );
		moveMakingPanel = new MoveMakingPanel();
		this.add( moveMakingPanel );
	}
	
	public void switchToSuggestionAccusationPanel()
	{
		this.remove( moveMakingPanel );
		suggestionAccusationPanel = new SuggestionAccusationPanel();
		this.add( suggestionAccusationPanel );
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
