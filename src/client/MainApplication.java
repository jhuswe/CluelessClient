package client;
import java.awt.BorderLayout;
import java.awt.Color;
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

import objects.Action;
import objects.Card;
import objects.Message;
import objects.Player;
import objects.Character;
import panels.ConnectToServerPanel;
import panels.DetectiveNotePanel;
import panels.GameBoardPanel;
import panels.UserDecisionPanel;


public class MainApplication 
	extends JFrame
{
	/**
	 *  Set time out to 5s
	 */
	protected final static int TIME_OUT = 5000;
	
	protected JPanel mainPane;
	
	/**
	 * Connect-to-Server panel
	 */
	protected ConnectToServerPanel ctsPane;
	
	/**
	 * User-decision panel
	 */
	protected UserDecisionPanel udPane;
	
	/**
	 * Detective note panel
	 */
	protected DetectiveNotePanel dnPane;
	
	/**
	 * Gameboard panel
	 */
	protected GameBoardPanel gbPane;
	
	protected Socket socket;
	
	protected boolean connected;
	
	/** 
	 * should be same as Character ID, i.e.
	 * int value of Card.MR_GREEN
	 */
	protected int playerId; 
	
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
					// Secret way to go to Game Panel without connecting to Server
					if( !ipAddress.equals( "a" ) )
					{
						socket = new Socket();
						socket.connect( new InetSocketAddress( ipAddress, 8889 ), TIME_OUT );
					}
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
		udPane.suggestionAccusationPanel.suggestionButton.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					Message rplMsg = new Message();
					rplMsg.action = Action.MAKE_SUGGESTION;
					// TODO: build up suggestion/accusation info
					
				}	
			} );
		
		udPane.suggestionAccusationPanel.accusationButton.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					Message rplMsg = new Message();
					rplMsg.action = Action.ACCUSATION;
					// TODO: build up suggestion/accusation info
				}	
			} );
		
		dnPane = new DetectiveNotePanel();
		dnPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		gbPane = new GameBoardPanel();
		gbPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
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
	
	public void updateComponent( JPanel panel )
	{
		this.remove( panel );
		this.add( panel );
	}
	
	public static void main( String[] a )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run() {
				MainApplication app = new MainApplication();
				app.makeConnection();
				
				// Message example for Controller to Client 
				// for a particular Player to make suggestion
				Message msg1 = new Message();
				msg1.action = Action.MAKE_SUGGESTION;
				msg1.player = new Player( new Character( Card.MR_GREEN.value() ) );
				
			}
		});
	}
	
}
