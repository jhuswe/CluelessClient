package panels;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.*;

public class StatusPanel extends JPanel
{
	JLabel title = new JLabel( "Game Status" );
	public JLabel playerConnect = new JLabel( "Waiting for all Players to connect" );
	public JLabel inSuggestion = new JLabel( "In Suggestion" );
	public JLabel playerMakingSuggestion;
	public JLabel inDisprove = new JLabel( "In Disprove" );
	public JLabel playerMakingDisprove;
	
	public StatusPanel()
	{
		super();
		createComponents();
	}
	
	public void createComponents()
	{
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS) );
		add(title);
	}
}
