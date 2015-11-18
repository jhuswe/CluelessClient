package objects;
import java.util.Set;


public class Player 
{
	String name;
	Set<Card> cards;
	
	public Player()
	{
		this( null, null );
	}
	
	public Player( String name, Set<Card> cards )
	{
		this.name = name;
		this.cards = cards;
	}
}
