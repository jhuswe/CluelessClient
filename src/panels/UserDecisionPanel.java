package panels;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserDecisionPanel
	extends JPanel 
{	
	public JLabel udpLabel = new JLabel( "Suggestion | Accusation" );
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
	
	public void setActive( boolean set )
	{
		this.suggestionAccusationPanel.accusationButton.setEnabled( set );
		this.suggestionAccusationPanel.suggestionButton.setEnabled( set );
		this.moveMakingPanel.okayButton.setEnabled( set );
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
