package panels;
 
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics; 
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame; 
import javax.swing.JPanel;

import objects.Card;
import objects.Hallway;
import objects.Location;
import objects.Character;
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
  final static int DINING_X = LOUNGE_X; 
  final static int DINING_Y = LOUNGE_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
   
  final static int CONSERVATORY_X = LIBRARY_X; 
  final static int CONSERVATORY_Y = LIBRARY_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int BALL_X = BILLIARD_X; 
  final static int BALL_Y = BILLIARD_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
  final static int KITCHEN_X = DINING_X; 
  final static int KITCHEN_Y = DINING_Y + ROOM_WIDTH + HALLWAY_LENGTH; 
   
  final static int STUDY_HALL_HALLWAY_X = STUDY_X + ROOM_WIDTH;
  final static int STUDY_HALL_HALLWAY_Y = STUDY_Y + ROOM_WIDTH * 2/5;
  
  final static int HALL_LOUNGE_HALLWAY_X = HALL_X + ROOM_WIDTH;
  final static int HALL_LOUNGE_HALLWAY_Y = HALL_Y + ROOM_WIDTH * 2/5;
  
  final static int STUDY_LIBRARY_HALLWAY_X = STUDY_X + ROOM_WIDTH * 2/5;
  final static int STUDY_LIBRARY_HALLWAY_Y = STUDY_Y + ROOM_WIDTH;
  
  final static int HALL_BILLIARD_HALLWAY_X = HALL_X + ROOM_WIDTH *2/5;
  final static int HALL_BILLIARD_HALLWAY_Y = HALL_Y + ROOM_WIDTH;
  
  final static int LOUNGE_DINING_HALLWAY_X = LOUNGE_X + ROOM_WIDTH * 2/5;
  final static int LOUNGE_DINING_HALLWAY_Y = LOUNGE_Y + ROOM_WIDTH;
  
  final static int LIBRARY_BILLIARD_HALLWAY_X = LIBRARY_X + ROOM_WIDTH;
  final static int LIBRARY_BILLIARD_HALLWAY_Y = LIBRARY_Y + ROOM_WIDTH * 2/5;
  
  final static int BILLIARD_DINING_HALLWAY_X = BILLIARD_X + ROOM_WIDTH;
  final static int BILLIARD_DINING_HALLWAY_Y = BILLIARD_Y + ROOM_WIDTH * 2/5;
  
  final static int LIBRARY_CONSERVATORY_HALLWAY_X = LIBRARY_X + ROOM_WIDTH * 2/5;
  final static int LIBRARY_CONSERVATORY_HALLWAY_Y = LIBRARY_Y + ROOM_WIDTH;
  
  final static int BILLIARD_BALL_HALLWAY_X = BILLIARD_X + ROOM_WIDTH *2/5;
  final static int BILLIARD_BALL_HALLWAY_Y = BILLIARD_Y + ROOM_WIDTH;
  
  final static int DINING_KITCHEN_HALLWAY_X = DINING_X + ROOM_WIDTH * 2/5;
  final static int DINING_KITCHEN_HALLWAY_Y = DINING_Y + ROOM_WIDTH;
  
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

  List<Location> loc;
  
  
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
    drawOccupants( g );
  } 

  public void drawStaticGraphics( Graphics g )
  {
	  drawRooms( g );
	  drawHallways( g );
  }
  
  /**
   * Receive message from Server to update the current state of game
   * re-rendering to update client's game graphics
   */
  public void updateGameBoard( List<Location> loc )
  {
  	  this.loc = loc;
  	  this.repaint();
  	  this.loc = null;
  }
  
  public void drawOccupants(Graphics g) 
  {
	  if( loc != null )
	  {
		  for( Location l : loc )
		  {
			  int[] xy;
			  
			  if( l.getOccupants() != null )
			  {
			       if( l instanceof Hallway )
			       {
			    	   xy = getDrawPoint( ((Hallway) l).id );
			    	   Color color = g.getColor();
			    	   g.setColor( l.getOccupants().get(0).color);
			    	   g.fillOval(xy[0], xy[1], HALLWAY_WIDTH, HALLWAY_WIDTH);
			    	   g.setColor( color );
			       }
			       else if( l instanceof Room )
			       {
			    	   xy = getDrawPoint( ((Room) l).getId() );
			 		  List<Character> charList = l.getOccupants();
			    	   // TODO: draw Ovals for number of occupants
			       }
			  }
		  }
	  }
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
		
		g.drawRect( DINING_X, DINING_Y, ROOM_WIDTH, ROOM_WIDTH ); 
		g.drawString("Dining", DINING_X + FONT_SIZE, DINING_Y + FONT_SIZE);
		 
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
		g.drawRect( LOUNGE_DINING_HALLWAY_X, LOUNGE_DINING_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH ); 
		
		g.drawRect( LIBRARY_BILLIARD_HALLWAY_X, LIBRARY_BILLIARD_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH ); 
		g.drawRect( BILLIARD_DINING_HALLWAY_X, BILLIARD_DINING_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH );
		g.drawRect( LIBRARY_CONSERVATORY_HALLWAY_X, LIBRARY_CONSERVATORY_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH ); 
		g.drawRect( BILLIARD_BALL_HALLWAY_X, BILLIARD_BALL_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH );
		g.drawRect( DINING_KITCHEN_HALLWAY_X, DINING_KITCHEN_HALLWAY_Y, HALLWAY_WIDTH, HALLWAY_LENGTH );
		
		g.drawRect( CONSERVATORY_BALL_HALLWAY_X, CONSERVATORY_BALL_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH ); 
		g.drawRect( BALL_KITCHEN_HALLWAY_X, BALL_KITCHEN_HALLWAY_Y, HALLWAY_LENGTH, HALLWAY_WIDTH );
  }
  
  public void drawSecretPath( Graphics g, int x, int y )
  {
	  g.drawRect( x, y, SECRETPATH_LENGTH, SECRETPATH_LENGTH );
  }
  
  public int[] getDrawPoint( int id )
  {
	  int[] xy = { -1, -1 };
	  
	  // HALLWAY Coordinates
	  if( id == Card.BALL_KITCHEN.value() )
		  xy = new int[] { BALL_KITCHEN_HALLWAY_X + HALLWAY_LENGTH / 2, BALL_KITCHEN_HALLWAY_Y };
	  else if( id == Card.BILLIARD_BALL.value() )
		  xy = new int[] { BILLIARD_BALL_HALLWAY_X, BILLIARD_BALL_HALLWAY_Y + HALLWAY_LENGTH / 2 };
	  else if( id == Card.BILLIARD_DINING.value() )
		  xy = new int[] { BILLIARD_DINING_HALLWAY_X + HALLWAY_LENGTH / 2, BILLIARD_DINING_HALLWAY_Y };
	  else if( id == Card.CONSERVATORY_BALL.value() )
		  xy = new int[] { CONSERVATORY_BALL_HALLWAY_X + HALLWAY_LENGTH / 2, CONSERVATORY_BALL_HALLWAY_Y };
	  else if( id == Card.DINING_KITCHEN.value() )
		  xy = new int[] { DINING_KITCHEN_HALLWAY_X, DINING_KITCHEN_HALLWAY_Y + HALLWAY_LENGTH / 2 };
	  else if( id == Card.HALL_BILLIARD.value() )
		  xy = new int[] { HALL_BILLIARD_HALLWAY_X, HALL_BILLIARD_HALLWAY_Y + HALLWAY_LENGTH / 2 };
	  else if( id == Card.HALL_LOUNGE.value() )
		  xy = new int[]{ HALL_LOUNGE_HALLWAY_X + HALLWAY_LENGTH / 2, HALL_LOUNGE_HALLWAY_Y };
	  else if( id == Card.LIBRARY_BILLIARD.value() )
		  xy = new int[]{ LIBRARY_BILLIARD_HALLWAY_X + HALLWAY_LENGTH / 2, LIBRARY_BILLIARD_HALLWAY_Y };
	  else if( id == Card.LIBRARY_CONSERVATORY.value() )
		  xy = new int[]{ LIBRARY_CONSERVATORY_HALLWAY_X, LIBRARY_CONSERVATORY_HALLWAY_Y + HALLWAY_LENGTH / 2 };
	  else if( id == Card.LOUNGE_DINING.value() )
		  xy = new int[] { LOUNGE_DINING_HALLWAY_X, LOUNGE_DINING_HALLWAY_Y + HALLWAY_LENGTH / 2 };
	  else if( id == Card.STUDY_HALL.value() )
		  xy = new int[] { STUDY_HALL_HALLWAY_X + HALLWAY_LENGTH / 2, STUDY_HALL_HALLWAY_Y };
	  else if( id == Card.STUDY_LIBRARY.value() )
		  xy = new int[] { STUDY_LIBRARY_HALLWAY_X, STUDY_LIBRARY_HALLWAY_Y + HALLWAY_LENGTH / 2 };
	  
	  // ROOM Coordinates
	  if( id == Card.BALL.value() )
		  xy = new int[] { BALL_X, BALL_Y };
	  else if( id == Card.BILLIARD.value() )
		  xy = new int[] { BILLIARD_X, BILLIARD_Y };
	  else if( id == Card.DINING.value() )
		  xy = new int[] { DINING_X, DINING_Y };
	  else if( id == Card.HALL.value() )
		  xy = new int[] { HALL_X, HALL_Y };
	  else if( id == Card.KITCHEN.value() )
		  xy = new int[] { KITCHEN_X, KITCHEN_Y };
	  else if( id == Card.LIBRARY.value() )
		  xy = new int[] { LIBRARY_X, LIBRARY_Y };
	  else if( id == Card.LOUNGE.value() )
		  xy = new int[] { LOUNGE_X, LOUNGE_Y };
	  else if( id == Card.STUDY.value() )
		  xy = new int[] { STUDY_X, STUDY_Y };
	  else if( id == Card.CONSERVATORY.value() )
		  xy = new int[] { CONSERVATORY_X, CONSERVATORY_Y };
	  
	  return xy;
  }
  
  public static void main (String[] args)  
  { 
    JFrame frame = new JFrame( "FrameView" ); 
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
    frame.setSize( 750, 700 );
    frame.setVisible( true ); 
     
    GameBoardPanel gbp = new GameBoardPanel(); 
    
    // TEST
    gbp.loc = new ArrayList<Location>();
    Hallway hw1 = new Hallway( Card.STUDY_HALL.value() ) ;
    hw1.addOccupant( new Character( Card.COL_MUSTARD.value() ) );
    Hallway hw2 = new Hallway( Card.HALL_LOUNGE.value() ) ;
    hw2.addOccupant( new Character( Card.MISS_SCARLET.value() ) );
    gbp.loc.add( hw1 );
    gbp.loc.add( hw2 );
    
    frame.add( gbp ); 
  } 
} 
 
 
 