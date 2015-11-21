package panels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MoveMakingPanel extends JPanel 
{
	public static final String[] ROOMS = { 
		"Hall", "Lounge", "Dinning Room", "Ball Room",
		"Conservatory", "Billiard Room", "Library", "Study" };
	
	public MoveMakingPanel()
	{
		super();
		createComponents();
	}
	
	public void createComponents()
	{
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		
		
	}
}
