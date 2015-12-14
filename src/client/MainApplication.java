package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import objects.Action;
import objects.Card;
import objects.InOut;
import objects.Location;
import objects.Message;
import objects.MessageBuilder;
import objects.Player;
import objects.Character;
import objects.Room;
import panels.ConnectToServerPanel;
import panels.DetectiveNotePanel;
import panels.DisprovePanel;
import panels.GameBoardPanel;
import panels.MoveMakingPanel;
import panels.StatusPanel;
import panels.SuggestionAccusationPanel;
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
//	protected UserDecisionPanel udPane;
	
	protected SuggestionAccusationPanel saPanel;
	
	protected MoveMakingPanel mmPanel;
	
	
	/**
	 * Disprove panel
	 */
	protected DisprovePanel disprovePane;
	
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
	protected boolean suggestionAccusationButtonListenerAdded = false;
	protected boolean moveMakingButtonListenerAdded = false;
	protected boolean disproveButtonListenerAdded = false;
	
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
			final Message msg = this.recvMsg();
			
//			final Message msg = new Message();
//			msg.player = new Player( new Character( Card.MISS_SCARLET.value() ) );
//			msg.action = Action.MAKE_SUGGESTION;
			
			if( msg == null )
				return;
			
			if (msg.playerLocations != null && msg.action != Action.INITIATE_CHARACTER) {
				gbPane.updateGameBoard( msg.playerLocations );
				this.revalidate();
				this.repaint();
			}
			
			this.logMessage( "[ Message ] " + msg.action + " for Player " + msg.player.getName() );
			
			if( msg.action == Action.INITIATE_CHARACTER )
			{
				playerId = msg.player.getId();
				
				this.logMessage( "Assigned Character: " + Card.getCard( playerId ).getName() );
				
				this.stPane.add( new JLabel( "Game Starts !!!" ) );
				this.stPane.add( new JLabel( "Assigned Character: " + Card.getCard( playerId ).getName() ) );
				
				gbPane.updateGameBoard( msg.playerLocations );
				disprovePane.playerCards = msg.player.cards;
				disprovePane.createComponents();
				
				this.setName( Card.getCard( playerId ).name() );
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.MOVE )
			{
				if(  msg.player.getId() == this.playerId )
				{
					mmPanel.createComponents( msg.availableMoves );
					
					//Ky I commented this out because it seems to break the move system on the 2nd round
//					if( !moveMakingButtonListenerAdded )
//					{
						mmPanel.okayButton.addActionListener( 
							new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent e) {
									System.out.println("Move making panel Okay button click");
									
									Message rplMsg = new Message();
									rplMsg.action = Action.MOVE;
									rplMsg.player = new Player( new Character( playerId) );
									
									for( int i = 0; i < mmPanel.checkBox.size(); i++ )
									{
										if( mmPanel.checkBox.get( i ).isSelected() )
										{
											Location selectedLoc = msg.availableMoves.get( i );
											rplMsg.player.location = selectedLoc;
											break;
										}
									}
									sendMsg( rplMsg );
									
		//							mmPane.okayButton.setEnabled( false );
								}
							} );
						moveMakingButtonListenerAdded = true;
					}
//				}
			
				stPane.add( new JLabel(msg.player.getName() + "'s turn to move" ) );
				
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.MAKE_SUGGESTION )
			{
				gbPane.updateGameBoard( msg.playerLocations );
				stPane.add( new JLabel( "------------------------------") );
				stPane.add( new JLabel(msg.player.getName() + "'s turn to make a suggestion" ) );
		
				if(  msg.player.getId() == this.playerId )
				{
					saPanel.suggestionButton.setEnabled( true );
					saPanel.accusationButton.setEnabled( true );
					
					if( !suggestionAccusationButtonListenerAdded )
					{	
						saPanel.suggestionButton.addActionListener(
							new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent e) {
									
									System.out.println( "[ SuggestionAccusationPanel ] SUGGESTION button is clicked" );
									
									Message rplMsg = new Message();
									rplMsg.action = Action.MAKE_SUGGESTION;
									rplMsg.player = msg.player;
									
									rplMsg.SDAInfo = new ArrayList<Integer>();
									rplMsg.SDAInfo.add( msg.player.location.getId() );
									
									for( int i = 0; i < saPanel.weaponBox.size() ; i++ )
									{
										if( saPanel.weaponBox.get( i ).isSelected() )
										{
											rplMsg.SDAInfo.add( saPanel.WEAPONS[i].value() );
											break;
										}
									}
									
									for( int i = 0; i < saPanel.suspectBox.size() ; i++ )
									{
										if( saPanel.suspectBox.get( i ).isSelected() )
										{
											rplMsg.SDAInfo.add( saPanel.SUSPECTS[i].value() );
											break;
										}
									}
									
									sendMsg( rplMsg );
								}	
							} );
							
						saPanel.accusationButton.addActionListener(
							new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent e) {
									
									System.out.println( "[ SuggestionAccusationPanel ] ACCUSATION button is clicked" );
									
									Message rplMsg = new Message();
									rplMsg.action = Action.ACCUSATION;
									rplMsg.player = msg.player;
									
									rplMsg.SDAInfo = new ArrayList<Integer>();
									rplMsg.SDAInfo.add( msg.player.location.getId() );
									
									for( int i = 0; i < saPanel.weaponBox.size() ; i++ )
									{
										if( saPanel.weaponBox.get( i ).isSelected() )
										{
											rplMsg.SDAInfo.add( saPanel.WEAPONS[i].value() );
											break;
										}
									}
									
									for( int i = 0; i < saPanel.suspectBox.size() ; i++ )
									{
										if( saPanel.suspectBox.get( i ).isSelected() )
										{
											rplMsg.SDAInfo.add( saPanel.SUSPECTS[i].value() );
											break;
										}
									}
									
									sendMsg( rplMsg );
								}	
							} );
						suggestionAccusationButtonListenerAdded = true;
					}
				}
				
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.UPDATE_PLAYER_LOCATION )
			{
				gbPane.updateGameBoard( msg.playerLocations );
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.DISPROVE )
			{
				String cardNames = this.getSuggestionString(msg.SDAInfo);
				
				stPane.add( new JLabel( msg.player.getName() + "'s turn to disprove"));
				stPane.add( new JLabel( "Suggestion: " + cardNames));
				
				if( msg.player.getId() == this.playerId )
				{
					disprovePane.disproveButton.setEnabled( true );
					
					if( !disproveButtonListenerAdded )
					{
						disprovePane.disproveButton.addActionListener( 
							new ActionListener()
							{
								@Override
								public void actionPerformed(ActionEvent e) {
									
									System.out.println( "[ Disprove Panel ] DISPROVE button is clicked" );
									
									Message rplMsg = new Message();
									rplMsg.action = Action.DISPROVE;								
									rplMsg.player = new Player( new Character( msg.player.getId() ) );
									rplMsg.SDAInfo = new ArrayList<Integer>();
									
									for( int i = 0; i < disprovePane.checkBox.size(); i++ )
									{
										if( disprovePane.checkBox.get( i ).isSelected() )
										{
											rplMsg.SDAInfo.add( 
												disprovePane.playerCards.get( i ).value() );
											break;
										}
									}
										
									sendMsg( rplMsg );
								}
							} );
						disproveButtonListenerAdded = true;
					}
				}
				
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.RECEIVE_DISPROVE_CARD )
			{
				if( msg.player.getId() == this.playerId )
				{
					stPane.add( new JLabel( msg.action.getName() + "'s disprove: " 
							+ Card.getCard( msg.SDAInfo.get( 0 ) ).getName() ) );
					this.revalidate();
					this.repaint();
				}
				else {
					stPane.add( new JLabel( "A disproval card was shown" ));
					this.revalidate();
					this.repaint();
				}
			}
			
			if( msg.action == Action.SHOW_SUGGESTION )
			{
				stPane.add( new JLabel( msg.player.getName() + " makes a suggestion:" ) );
				String str = "[";
				for( Integer i : msg.SDAInfo) 
				{
					str = str + Card.getCard( i ).getName() + ",";
				}
				str += "]";
				stPane.add( new JLabel( str ) );
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.LOSE )
			{
				mmPanel.okayButton.setEnabled( false );
				saPanel.accusationButton.setEnabled( false );
				saPanel.suggestionButton.setEnabled( false );
				
				stPane.add( new JLabel( msg.player.getName() + "made a wrong accusation" ) );
				
				String str = "[";
				for( Integer i : msg.SDAInfo) 
				{
					str = str + Card.getCard( i ).getName() + ",";
				}
				str += "]";
				
				stPane.add( new JLabel( str ) );
				stPane.add( new JLabel( msg.player.getName() + " loses" ) );
				
				this.revalidate();
				this.repaint();
			}
			
			if( msg.action == Action.WIN )
			{
				endGame = true;
				mmPanel.okayButton.setEnabled( false );
				saPanel.accusationButton.setEnabled( false );
				saPanel.suggestionButton.setEnabled( false );
				
				disprovePane.disproveButton.setEnabled( false );
				
				stPane.add( new JLabel( msg.player.getName() + "made a correct accusation" ) );
				
				String str = "[";
				for( Integer i : msg.SDAInfo) 
				{
					str = str + Card.getCard( i ).getName() + ",";
				}
				str += "]";
				
				stPane.add( new JLabel( str ) );
				
				this.revalidate();
				this.repaint();
			}
			
			if (msg.action == Action.NO_DISPROVE_MADE) {
				stPane.add( new JLabel( msg.player.getName() + " did not disprove the suggestion" ) );
			}
			
			//always scroll status panel to the bottom
			int height = (int)stPane.getPreferredSize().getHeight();
            Rectangle rect = new Rectangle(0,height,10,10);
            stPane.scrollRectToVisible(rect);
			
		}
	}
	
	/**
	 * Open up Main Panel, the container for 
	 * Gameboard panel, User decision panel, Detective note panel,
	 * and Status panel.
	 */
	protected void openGameGUI()
	{	
//		udPane = new UserDecisionPanel();
//		udPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		mmPanel = new MoveMakingPanel();
		
		saPanel = new SuggestionAccusationPanel();
		
		dnPane = new DetectiveNotePanel();
		dnPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		gbPane = new GameBoardPanel();
		
		stPane = new StatusPanel();
		stPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		JScrollPane scrollStatusPane = new JScrollPane( stPane);
		scrollStatusPane.setPreferredSize( new Dimension( 300, 150));
		
		disprovePane = new DisprovePanel();
		disprovePane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		JPanel topPane = new JPanel( new BorderLayout() );
		JPanel rightPane = new JPanel();
		rightPane.setLayout( new BoxLayout( rightPane, BoxLayout.Y_AXIS ) );
		
		rightPane.add( scrollStatusPane );
		rightPane.add( disprovePane );
		
		topPane.add( gbPane, BorderLayout.LINE_START );
		topPane.add( rightPane, BorderLayout.LINE_END );
		
		JPanel userDecisionPanel = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
		userDecisionPanel.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		userDecisionPanel.add( mmPanel );
		userDecisionPanel.add( saPanel );
		
		mainPane.add( topPane, BorderLayout.PAGE_START );
		mainPane.add( dnPane, BorderLayout.LINE_START );
		mainPane.add( userDecisionPanel, BorderLayout.LINE_END );
		
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
					socket.connect( new InetSocketAddress( ipAddress, 8889 ), 0 );
					socket.setKeepAlive(true);
					socket.setSoTimeout(0);
					
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		            IOport = new InOut(in, out, socket);
					
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
    	if( IOport.out.checkError() )
    		this.logMessage( "[ sendMsg ] sendMsg failed");
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
	
	//returns a comma seperated string of card names
	private String getSuggestionString(List<Integer> guess) {
		List<String> cardNames = new ArrayList<String>();;
		
		for (Integer cardId : guess) {
			cardNames.add( Card.getCard(cardId).getName() );
		}
		
		return String.join(", ", cardNames);
	}
}