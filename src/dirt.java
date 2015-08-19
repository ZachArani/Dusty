import java.awt.Color;

public class dirt extends dot{
	
	public dirt()
	{
		id = 1;
		density = 1;
		state = 1;
		/*draw.coin = draw.random.nextInt(2);
		if(draw.coin == 1)
		color = new Color(150, 50, 0);
		else*/
		color = new Color(150, 50, 0);
		soluable = false;
	}

	@Override //chicken breast
	public void  update()
	{
		
	}
	
	public void oldUpdate()
	{
		x = draw.thisX;
		y = draw.thisY;
		

		
		if(moved == false)
		if(canMove(x + xVel, y + yVel + draw.gravity) == true || canMove(x + xVel, y + yVel + draw.gravity, 2) == true) //If dot can move down
		{
			move(x + xVel, y + yVel + draw.gravity);
		}
		else
		{
			xVel = 0;
			yVel = 0;
		}
		//xVel = xVel / 2;
		//yVel++;
	}
}
