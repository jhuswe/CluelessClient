package panels;
 
 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; 

import javax.swing.JFrame; 
import javax.swing.JPanel;

import objects.Hallway;
import objects.Room;
 
 
public class GameBoardPanel extends JPanel 
{ 
  final static int MARGIN = 50; 
  final static int FONT_SIZE = 11;
  final static int ROOM_WIDTH = 100; 
  final static int HALLWAY_LENGTH = ROOM_WIDTH; 
  final static int HALLWAY_WIDTH = 20;
  final static int SECRETPATH_LENGTH = HALLWAY_WIDTH;
  
  final static int PANEL_SIZE = MARGIN * 2 + ROOM_WIDTH * 3 + HALLWAY_LENGTH * 2;
  
  final static int STUDY_X = MARGIN; 
  final static int STUDY_Y = MARGIN; 
  final static int HALL_X = STUDY_X + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int HALL_Y = STUDY_Y; 
  final static int LOUNGE_X = HALL_X + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int LOUNGE_Y = STUDY_Y; 
   
  final static int LIBRARY_X = STUDY_X; 
  final static int LIBRARY_Y = STUDY_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int BILLIARD_X = HALL_X; 
  final static int BILLIARD_Y = HALL_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int DINNING_X = LOUNGE_X; 
  final static int DINNING_Y = LOUNGE_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
   
  final static int CONSERVATORY_X = LIBRARY_X; 
  final static int CONSERVATORY_Y = LIBRARY_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int BALL_X = BILLIARD_X; 
  final static int BALL_Y = BILLIARD_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int KITCHEN_X = DINNING_X; 
  final static int KITCHEN_Y = DINNING_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
   
  final static int STUDY_HALL_HALLWAY_X = STUDY_X + ROOM_WIDTH;
  final static int STUDY_HALL_HALLWAY_Y = STUDY_Y + ROOM_WIDTH * 2/5;
  
  final static int HALL_LOUNGE_HALLWAY_X = HALL_X + ROOM_WIDTH;
  final static int HALL_LOUNGE_HALLWAY_Y = HALL_Y + ROOM_WIDTH * 2/5;
  
  final static int STUDY_LIBRARY_HALLWAY_X = STUDY_X + ROOM_WIDTH * 2/5;
  final static int STUDY_LIBRARY_HALLWAY_Y = STUDY_Y + ROOM_WIDTH;
  
  final static int HALL_BILLIARD_HALLWAY_X = HALL_X + ROOM_WIDTH *2/5;
  final static int HALL_BILLIARD_HALLWAY_Y = HALL_Y + ROOM_WIDTH;
  
  final static int LOUNGE_DINNING_HALLWAY_X = LOUNGE_X + ROOM_WIDTH * 2/5;
  final static int LOUNGE_DINNING_HALLWAY_Y = LOUNGE_Y + ROOM_WIDTH;
  
  final static int LIBRARY_BILLIARD_HALLWAY_X = LIBRARY_X + ROOM_WIDTH;
  final static int LIBRARY_BILLIARD_HALLWAY_Y = LIBRARY_Y + ROOM_WIDTH * 2/5;
  
  final static int BILLIARD_DINNING_HALLWAY_X = BILLIARD_X + ROOM_WIDTH;
  final static int BILLIARD_DINNING_HALLWAY_Y = BILLIARD_Y + ROOM_WIDTH * 2/5;
  
  final static int LIBRARY_CONSERVATORY_HALLWAY_X = LIBRARY_X + ROOM_WIDTH * 2/5;
  final static int LIBRARY_CONSERVATORY_HALLWAY_Y = LIBRARY_Y + ROOM_WIDTH;
  
  final static int BILLIARD_BALL_HALLWAY_X = BILLIARD_X + ROOM_WIDTH *2/5;
  final static int BILLIARD_BALL_HALLWAY_Y = BILLIARD_Y + ROOM_WIDTH;
  
  final static int DINNING_KITCHEN_HALLWAY_X = DINNING_X + ROOM_WIDTH * 2/5;
  final static int DINNING_KITCHEN_HALLWAY_Y = DINNING_Y + ROOM_WIDTH;
  
  final static int CONSERVATORY_BALL_HALLWAY_X = CONSERVATORY_X + ROOM_WIDTH;
  final static int CONSERVATORY_BALL_HALLWAY_Y = CONSERVATORY_Y + ROOM_WIDTH * 2/5;
  
  final static int BALL_KITCHEN_HALLWAY_X = BALL_X + ROOM_WIDTH;
  final static int BALL_KITCHEN_HALLWAY_Y = BALL_Y + ROOM_WIDTH * 2/5;
  
  final static int STUDY_SECRETPATH_X = STUDY_X + ROOM_WIDTH * 4/5;
  final static int STUDY_SECRETPATH_Y = STUDY_Y + ROOM_WIDTH * 4/5;
  final static int LOUNGE_SECRETPATH_X = LOUNGE_X;
  final static int LOUNGE_SECRETPATH_Y = LOUNGE_Y + ROOM_WIDTH * 4/5;
  final static int CONSERVATORY_SECRETPATH_X = CONSERVATORY_X + ROOM_WIDTH * 4/5;
  final static int CONSERVATORY_SECRETPATH_Y = CONSERVATORY_Y;
  final static int KITCHEN_SECRETPATH_X = KITCHEN_X;
  final static int KITCHEN_SECRETPATH_Y = KITCHEN_Y;
  
  Room hall;
  Room study; 
  Room lounge;  
  Room lib; 
  Room billiard; 
  Room dinning; 
  Room conservatory; 
  Room ball; 
  Room kitchen; 
   
  Hallway study_Hallway;
  Hallway hall_lounge;
  Hallway study_lib;
  Hallway hall_billiard;
  Hallway lounge_dinning;
  Hallway lib_billiard;
  Hallway billiard_dinning;
  Hallway lib_conservatory;
  Hallway billiard_ball;
  Hallway dinning_kitchen;
  Hallway conservatory_ball;
  Hallway ball_kitchen;
   
  public GameBoardPanel() 
  { 
  	super();
    setPreferredSize( new Dimension( PANEL_SIZE, PANEL_SIZE ) );
  } 
   
  @Override
  public void paintComponent( Graphics g ) 
  { 
    super.paintComponent( g ); 
    drawStaticGraphics( g );
  } 
   
  public void drawStaticGraphics( Graphics g )
  {
	  drawRooms( g );
	  drawHallways( g );
  }
  
  public void drawRooms( Graphics g )
  {
	  Font font = new Font( "Arial", Font.PLAIN, FONT_SIZE );
	  g.setFont( font );
	  
		g.drawRect( STUDY_X, STUDY_Y, ROOM_WIDTH, ROOM_WIDTH );
		g.drawString("Study", STUDY_X + FONT_SIZE, STUDY_Y + FONT_SIZE);
		drawSecretPath( g, STUDY_SECRETPATH_X, STUDY_SECRETPATH_Y );
		
		g.drawRect( HALL_X, HALL_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Hall", HALL_X + FONT_SIZE, HALL_Y + FONT_SIZE);
		
		g.drawRect( LOUNGE_X, LOUNGE_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Lounge", LOUNGE_X + FONT_SIZE, HALL_Y + FONT_SIZE);
		drawSecretPath( g, LOUNGE_SECRETPATH_X, LOUNGE_SECRETPATH_Y );
		
		g.drawRect( LIBRARY_X, LIBRARY_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Library", LIBRARY_X + FONT_SIZE, LIBRARY_Y + FONT_SIZE);
		
		g.drawRect( BILLIARD_X, BILLIARD_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Billiard", BILLIARD_X + FONT_SIZE, BILLIARD_Y + FONT_SIZE);
		
		g.drawRect( DINNING_X, DINNING_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Dinning", DINNING_X + FONT_SIZE, DINNING_Y + FONT_SIZE);
		 
		g.drawRect( CONSERVATORY_X, CONSERVATORY_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Conservatory", CONSERVATORY_X + FONT_SIZE, CONSERVATORY_Y + FONT_SIZE);
		drawSecretPath( g, CONSERVATORY_SECRETPATH_X, CONSERVATORY_SECRETPATH_Y );
		
		g.drawRect( BALL_X, BALL_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Ball", BALL_X + FONT_SIZE, BALL_Y + FONT_SIZE);
		
		g.drawRect( KITCHEN_X, KITCHEN_Y, ROOM_WIDTH, ROOM_WIDTH );
		g.drawString("Kitchen", KITCHEN_X + FONT_SIZE + SECRETPATH_LENGTH, KITCHEN_Y + FONT_SIZE);
		drawSecretPath( g, KITCHEN_SECRETPATH_X, KITCHEN_SECRETPATH_Y );
		
  }
  
  public void drawHallways( Graphics g )
  {
		g.drawRect( STUDY_HALL_HALLWAY_X, STUDY_HALL_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH ); 
		g.drawRect( HALL_LOUNGE_HALLWAY_X, HALL_LOUNGE_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH );
		g.drawRect( STUDY_LIBRARY_HALLWAY_X, STUDY_LIBRARY_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH ); 
		g.drawRect( HALL_BILLIARD_HALLWAY_X, HALL_BILLIARD_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH );
		g.drawRect( LOUNGE_DINNING_HALLWAY_X, LOUNGE_DINNING_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH ); 
		
		g.drawRect( LIBRARY_BILLIARD_HALLWAY_X, LIBRARY_BILLIARD_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH ); 
		g.drawRect( BILLIARD_DINNING_HALLWAY_X, BILLIARD_DINNING_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH );
		g.drawRect( LIBRARY_CONSERVATORY_HALLWAY_X, LIBRARY_CONSERVATORY_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH ); 
		g.drawRect( BILLIARD_BALL_HALLWAY_X, BILLIARD_BALL_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH );
		g.drawRect( DINNING_KITCHEN_HALLWAY_X, DINNING_KITCHEN_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH );
		
		g.drawRect( CONSERVATORY_BALL_HALLWAY_X, CONSERVATORY_BALL_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH ); 
		g.drawRect( BALL_KITCHEN_HALLWAY_X, BALL_KITCHEN_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH );
  }
  
  public void drawSecretPath( Graphics g, int x, int y )
  {
	  g.drawRect( x, y, SECRETPATH_LENGTH, SECRETPATH_LENGTH );
  }
  
  public void updateState( String string )
  {
  	// receive message from Server to update the current state of game
  	// re-rendering to update client's game graphics
  }
  
  public static void main (String[] args)  
  { 
    JFrame frame = new JFrame( "FrameView" ); 
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
    frame.setSize( 750, 700 );
    frame.setVisible( true ); 
     
    GameBoardPanel view = new GameBoardPanel(); 
    frame.add( view ); 
  } 
} 
 
 
 