package client;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import panels.ConnectToServerPanel;
import panels.DetectiveNotePanel;
import panels.GameBoardPanel;
import panels.UserDecisionPanel;


public class MainApplication 
	extends JFrame
{
	protected JPanel ctsPane;
	protected JPanel dnp;
	protected JPanel gbp;
	
	public MainApplication()
	{
		super( "Main Application" );
	}
	
	private void createAndShowGUI()
	{
		JPanel mainPane = new JPanel( new BorderLayout() );
//		ctsPane = new ConnectToServerPanel();
		ctsPane = new UserDecisionPanel();
//		ctsPane.createComponents();
//		ctsPane.layoutComponents();
		ctsPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		dnp = new DetectiveNotePanel();
		dnp.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		gbp = new GameBoardPanel();
		gbp.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		mainPane.add( gbp, BorderLayout.PAGE_START );
		mainPane.add( ctsPane, BorderLayout.LINE_START );
		mainPane.add( dnp, BorderLayout.LINE_END );
		
		add(mainPane);
		
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
