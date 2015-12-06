package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import panels.ConnectToServerPanel;
import panels.DetectiveNotePanel;
import panels.GameBoardPanel;
import panels.UserDecisionPanel;


public class MainApplication 
	extends JFrame
{
	protected JPanel ctsPane;
	protected JPanel udPane;
	protected JPanel dnp;
	protected JPanel gbp;
	
	public MainApplication()
	{
		super( "Main Application" );
	}
	
	private void createAndShowGUI()
	{
		JPanel mainPane = new JPanel( new BorderLayout() );
		ctsPane = new ConnectToServerPanel();		
		ctsPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		( (ConnectToServerPanel) ctsPane ).okay.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				String ipAddress = ((ConnectToServerPanel) ctsPane).input.getText();
				// TODO use ipAddress to connect to Server
			}
		} );
		
		udPane = new UserDecisionPanel();
		udPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		( (UserDecisionPanel) udPane ).suggestionAccusationPanel.okay.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO send suggestion/accusation decision to Server
						
					}	
				} );
		
		dnp = new DetectiveNotePanel();
		dnp.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		gbp = new GameBoardPanel();
		gbp.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
//		JPanel bottomPane = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
//		bottomPane.add( udPane );
//		bottomPane.add( dnp );
//		
//		mainPane.add( gbp, BorderLayout.PAGE_START );
//		mainPane.add( bottomPane, BorderLayout.PAGE_END );
		
		mainPane.add( gbp, BorderLayout.PAGE_START );
		mainPane.add( udPane, BorderLayout.LINE_START );
		mainPane.add( dnp, BorderLayout.LINE_END );
		
		JScrollPane scroll = new JScrollPane( mainPane );
		add( scroll );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		pack();
		setLocationRelativeTo( null );
		setVisible( true );
	}
	
	public static void main( String[] a )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run() {
				MainApplication app = new MainApplication();
				app.createAndShowGUI();
			}
		});
	}
	
}
