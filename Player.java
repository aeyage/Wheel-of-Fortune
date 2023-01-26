
public class Player 
{
	
	private int total;
	
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
