package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import objects.Action;
import objects.Card;
import objects.InOut;
import objects.Message;
import objects.MessageBuilder;
import objects.Player;
import objects.Character;
import panels.ConnectToServerPanel;
import panels.DetectiveNotePanel;
import panels.GameBoardPanel;
import panels.MoveMakingPanel;
import panels.StatusPanel;
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
	
	/**
	 * Status panel
	 */
	protected StatusPanel stPane;
	
	protected Socket socket;
	
	protected InOut IOport;
	
	protected boolean endGame;
	
	/** 
	 * should be same as Character ID, i.e.
	 * int value of Card.MR_GREEN
	 */
	protected int playerId; 
	
	public MainApplication()
	{
		super( "Main Application" );
		mainPane = new JPanel( new BorderLayout() );
		endGame = false;
	}
	
	public void runGame()
	{
		while( !endGame )
		{
			Message msg = this.recvMsg();
			
			if( msg == null )
				return;
			
			if( msg.action == Action.INITIATE_CHARACTER )
			{
				playerId = msg.player.getId();
				this.logMessage( "Assigned Character: " + Card.getCard( playerId ).getName() );
				this.stPane.add( new JLabel( "Game Starts !!!" ) );
				this.stPane.add( new JLabel( "Assigned Character: " + Card.getCard( playerId ).getName() ) );
			}
			
			if( msg.action == Action.MOVE )
			{
				this.logMessage( "Your turn (" + Card.getCard( this.playerId ) + ") to MOVE" );
				udPane.switchToMoveMakingPanel();
				final MoveMakingPanel mmPane = udPane.moveMakingPanel;
				mmPane.createComponents( msg.availableMoves );
				mmPane.okayButton.addActionListener( 
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) {
							for( int i = 0; i < mmPane.checkBox.size(); i++ )
							{
								if( mmPane.checkBox.get( i ).isSelected() )
								{
									// TODO: create Message and send back
								}
							}
						}
					});
			}
			
			if( msg.action == Action.MAKE_SUGGESTION )
			{
				this.logMessage( "Your turn (" + Card.getCard( this.playerId ) + ") to " + msg.action );
				udPane.suggestionAccusationPanel.suggestionButton.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) {
							Message rplMsg = new Message();
							rplMsg.action = Action.MAKE_SUGGESTION;
							// TODO: build up suggestion/accusation info
							
							sendMsg( rplMsg );
							
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
							
							sendMsg( rplMsg );
						}	
					} );
			}
			
			if( msg.action == Action.DISPROVE )
			{
				// TODO: add action listener to Disprove Panel's
				// okay button to get selected Card
				
				Message rplMsg = new Message();
				// TODO: create and send back Message
				
				sendMsg( rplMsg );
			}
			
			if( msg.action == Action.RECEIVE_DISPROVE_CARD )
			{
				
			}
			
			if( msg.action == Action.SHOW_SUGGESTION )
			{
				
			}
			
			if( msg.action == Action.WIN || msg.action == Action.LOSE )
			{
				
			}
			
		}
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
		
		dnPane = new DetectiveNotePanel();
		dnPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		gbPane = new GameBoardPanel();
		
		stPane = new StatusPanel();
		stPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		JPanel topPane = new JPanel( new BorderLayout() );
		topPane.add( gbPane, BorderLayout.LINE_START );
		topPane.add( stPane, BorderLayout.LINE_END );
		
		mainPane.add( topPane, BorderLayout.PAGE_START );
		mainPane.add( dnPane, BorderLayout.LINE_START );
		mainPane.add( udPane, BorderLayout.LINE_END );
		
		JScrollPane scroll = new JScrollPane( mainPane );
		add( scroll );
		
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		pack();
		setLocationRelativeTo( null );
		setVisible( true );
		
		( new Thread() {
			public void run() {
				runGame();
			}
		}).start();
		
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
					// Secret way to quickly connect to Server at localhost
					if( ipAddress.equals( "v" ) )
						ipAddress = "127.0.0.1";
					
					socket = new Socket();
					socket.connect( new InetSocketAddress( ipAddress, 8889 ), TIME_OUT );
					
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		            IOport = new InOut(in, out);
					
		            logMessage("[ MainApplication:makeConnection() ] Connection Established");
		            
					mainPane.remove( ctsPane );
					openGameGUI();
				} 
				catch( IOException e ) 
				{ 
					System.out.println( "[ this.makeConnection() ] ERROR:: " + e );
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
	
	public void updateComponent( JPanel panel )
	{
		this.remove( panel );
		this.add( panel );
	}
	
    //send a message to a single client
    public void sendMsg( Message message ) {
    	String jsonText = MessageBuilder.SerializeMsg(message);
    	
    	IOport.out.println(jsonText);
    }
    
    //convert jsonText to Message object
    public Message recvMsg() {
    	Message message = null;

    	try {
				String jsonText = IOport.in.readLine();
				message = (Message) MessageBuilder.DeserializeMsg(jsonText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return message;
    }
    
    //wrapper for printing messages to the console
    public void logMessage(String message) {
    	System.out.println(message);
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
