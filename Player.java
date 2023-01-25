//import java.util.*;
public class Player {
	
	private int playerNumber;
	private char playerGuess;
	private int total;
	
	public Player(int number)
	{
		playerNumber = number;
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	public void setPlayerGuess(char guess)
	{
		playerGuess = guess;
	}
	
	public char getPlayerGuess()
	{
		return playerGuess;
	}
	
	public void setTotal(int b)
	{
		if(b == -100)
		{
			total = 0;
		} else 
		{
			total += b;
		}
	}

	public void revertTotal(int a)
	{
		total = total - a;
	}
	public int getTotal()
	{
		return total;
	}

}
