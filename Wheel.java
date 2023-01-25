import java.util.*;
public class Wheel 
{
	private Random rando;
	private int wheelSpot;
	private int wheelValue;
	
	public void spin()
	{
		rando = new Random();
		wheelSpot = rando.nextInt(9)+1;
		wheelValue = wheelValue(wheelSpot);
	}
	
	private int wheelValue(int spot)
	{
		int [] wheelValues = {100, 200, 300, 250, 350,
									150, 50, 50, -100, 0};
		
		return wheelValues[spot];
	}
	
	public int getWheelValue()
	{
		return wheelValue;
	}
	
}
