package client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import objects.Action;
import objects.Card;
import objects.Hallway;
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


public class MainApplication1
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
	
	/** 
	 * should be same as Character ID, i.e.
	 * int value of Card.MR_GREEN
	 */
	protected int playerId; 
	
	
	public MainApplication1()
	{
		super( "Main Application" );
		mainPane = new JPanel( new BorderLayout() );
		endGame = false;
	}
	
	/**
	 * Open up Main Panel, the container for 
	 * Gameboard panel, User decision panel, Detective note panel,
	 * and Status panel.
	 */
	protected void openGameGUI()
	{	
		saPanel = new SuggestionAccusationPanel();
		mmPanel = new MoveMakingPanel();
		
		// Available location for Move Making Panel
		ArrayList<Location> moves = new ArrayList<Location>();
		
		// Game Board Panel
		gbPane = new GameBoardPanel();
		// Player location for Game Board 
		final ArrayList<Location> loc = new ArrayList<Location>(); 
		final Location l1 = new Hallway( Card.HALL_LOUNGE.value() );
		final Location l2 = new Hallway( Card.LOUNGE_DINING.value() );
		Location l3 = new Hallway( Card.BALL_KITCHEN.value() );
		Location l4 = new Hallway(  Card.CONSERVATORY_BALL.value() );
		final Location l5 = new Hallway( Card.LIBRARY_CONSERVATORY.value() );
		Location l6 = new Hallway( Card.STUDY_LIBRARY.value() );
		final Location hall = new Room( Card.HALL.value() );
		Location lounge = new Room( Card.LOUNGE.value() ); 
		l1.addOccupant( new Character( Card.MISS_SCARLET.value() ));
		l2.addOccupant( new Character( Card.COL_MUSTARD.value() ));
		l3.addOccupant( new Character( Card.MRS_WHITE.value() ));
		l4.addOccupant( new Character( Card.MR_GREEN.value() ));
		l5.addOccupant( new Character( Card.MRS_PEACOCK.value() ));
		l6.addOccupant( new Character( Card.PROF_PLUM.value() ));
		loc.add(l1);
		loc.add(l2);
		loc.add(l3);
		loc.add(l4);
		loc.add(l5);
		loc.add(l6);
		gbPane.updateGameBoard( loc );
		
		moves.add( hall );
		moves.add( lounge );
		mmPanel.createComponents(moves);
		
		// Detective Note
		dnPane = new DetectiveNotePanel();
		dnPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		// Status Panel
		stPane = new StatusPanel();
		stPane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		stPane.add( new JLabel("Game Starts !!!"));
		stPane.add( new JLabel("Assigned Character: Miss Scarlet"));
		stPane.add( new JLabel("Miss Scarlet will make a Move"));
		
		// Disprove Pane - list out a list of Cards a player has
		disprovePane = new DisprovePanel();
		disprovePane.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		disprovePane.playerCards = new ArrayList<Card>();
		disprovePane.playerCards.add( Card.LIBRARY );
		disprovePane.playerCards.add(Card.MRS_PEACOCK);
		disprovePane.playerCards.add(Card.CANDLE_STICK);
		disprovePane.playerCards.add(Card.LEAD_PIPE);
		disprovePane.playerCards.add(Card.BALL);
		disprovePane.createComponents();
		
		mmPanel.okayButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				l1.removeOccupant(new Character( Card.MISS_SCARLET.value() ));
				hall.addOccupant(new Character( Card.MISS_SCARLET.value() ));
				loc.add(hall);
				gbPane.updateGameBoard(loc);
				stPane.add( new JLabel("Miss Scarlet will make a Suggestion"));
				mmPanel.okayButton.setEnabled(false);
				saPanel.accusationButton.setEnabled(true);
				saPanel.suggestionButton.setEnabled(true);
			}
			
		});
		
		saPanel.suggestionButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					l2.removeOccupant(new Character( Card.COL_MUSTARD.value() ));
					hall.addOccupant(new Character( Card.COL_MUSTARD.value() ));
					gbPane.updateGameBoard( loc );
					stPane.add( new JLabel("Suggestion: [ Col. Mustard , Knife , Hall ]"));
					saPanel.accusationButton.setEnabled( false );
					saPanel.suggestionButton.setEnabled( false );
					stPane.add( new JLabel("Col. Mustard will make a Disprove"));
					try{
						TimeUnit.SECONDS.sleep(2);
					} catch( Exception error) {
						// 
					}
					stPane.add( new JLabel("Col.Mustard's Disprove: null"));
			}
			
		});
		
		JPanel topPane = new JPanel( new BorderLayout() );
		JPanel rightPane = new JPanel();
		rightPane.setLayout( new BoxLayout( rightPane, BoxLayout.Y_AXIS ) );
		
		rightPane.add( stPane );
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

					
		            logMessage("[ MainApplication:makeConnection() ] Connection Established");
		            
					mainPane.remove( ctsPane );
					openGameGUI();
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
				MainApplication1 app = new MainApplication1();
				app.openGameGUI();
			}
		});
	}
	
}
