package panels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MoveMakingPanel extends JPanel 
{
	public static final String[] ROOMS = { 
		"Hall", "Lounge", "Dinning Room", "Ball Room",
		"Conservatory", "Billiard Room", "Library", "Study" };
	
	public static final String[] HALLWAYS = {
		"Study-Hall", "Study-Library", "Hall-Billiard",
		"Hall-Lounge", "Lounge-Dining", "Library-Billiard",
		"Billiard-Dining", "Library-Conservatory", "Billiard-Ball", 
		"Dining-Kitchen", "Conservatory-Ball", "Ball-Kitchen" };
	
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
