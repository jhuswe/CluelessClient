package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

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
	/*
	 *  Set time out to 5s
	 */
	protected final static int TIME_OUT = 5000;
	
	protected JPanel mainPane;
	
	/*
	 * Connect-to-Server panel
	 */
	protected JPanel ctsPane;
	
	/*
	 * User-decision panel
	 */
	protected JPanel udPane;
	
	/*
	 * Detective note panel
	 */
	protected JPanel dnPane;
	
	/*
	 * Gameboard panel
	 */
	protected JPanel gbPane;
	
	protected Socket socket;
	
	protected boolean connected;
	
	
	public MainApplication()
	{
		super( "Main Application" );
		mainPane = new JPanel( new BorderLayout() );
		connected = false;
	}
	
	/**
	 * Open up Connect-to-Server panel to allow user to connect
	 * to server by inputing an IP address. If connection is established,
	 * Main Panel would be opened up that allows user to play game on.
	 */
	protected void makeConnection()
	{
		ctsPane = new ConnectToServerPanel();		
		( (ConnectToServerPanel) ctsPane ).okay.addActionListener( new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				String ipAddress = ((ConnectToServerPanel) ctsPane).input.getText();
				try
				{
					socket = new Socket();
					socket.connect( new InetSocketAddress( ipAddress, 8889 ), TIME_OUT );
					mainPane.remove( ctsPane );
					openGameGUI();
				} 
				catch( IOException e ) 
				{ 
					System.out.println( "ERROR:: " + e );
					( (ConnectToServerPanel) ctsPane ).label2.setVisible( true );
				}
			}
		} );
		
		mainPane.add( ctsPane );
		add( mainPane );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		pack();
		setLocationRelativeTo( null );
		setVisible( true );
	}
	
	/**
	 * Open up Main Panel, the container for 
	 * Gameboard panel, User decision panel, Detective note panel,
	 * and Status panel.
	 */
	protected void openGameGUI()
	{	
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
		
		dnPane = new DetectiveNotePanel();
		dnPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		gbPane = new GameBoardPanel();
		gbPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		JPanel bottomPane = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
		bottomPane.add( udPane );
		bottomPane.add( dnPane );
		
		mainPane.add( gbPane, BorderLayout.PAGE_START );
		mainPane.add( bottomPane, BorderLayout.PAGE_END );
		
		mainPane.add( gbPane, BorderLayout.PAGE_START );
		mainPane.add( udPane, BorderLayout.LINE_START );
		mainPane.add( dnPane, BorderLayout.LINE_END );
		
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
				app.makeConnection();
			}
		});
	}
	
}
