import java.awt.Color;



public class water extends dot{
	
	public water()
	{
		if(draw.random.nextInt(2) == 1)
			lean = -1;
		else
			lean = 1;
			
		id = 2;
		density = 1;
		state = 2;
		/*draw.coin = draw.random.nextInt(2);
		if(draw.coin == 1)
		color = new Color(0, 0, 200);
		else*/
		color = new Color(0, 0, 200);
	}

	@Override
	public void  update()
	{

		x = draw.thisX;
		y = draw.thisY;
		dissolveCap = 2;
		
		//if(Dissolved.size() < dissolveCap) // Number of dots this dot can absorb
		//absorb();
		//else 
		if(Dissolved.size() > dissolveCap)
		{
			eject();
		}
		if(moved == false)
		if(canMove(x, y + 1) == true)
		{
			move(x, y + 1);
		}
		else if(Dissolved.size() < dissolveCap && isInBounds(x, y + 1) == true && draw.Dot[x][y + 1].soluable == true)
			{
				take(x, y + 1);
			}
		else
		{
			//draw.coin = draw.random.nextInt(2);
			if(draw.choice * lean < 0)
			{
				if(canMove(x - 1, y))
				{
					move(x - 1, y);
				}
				else if(Dissolved.size() < dissolveCap && isInBounds(x - 1, y) == true && draw.Dot[x - 1][y].soluable == true)
				{
					take(x - 1, y);
				}
			}
			if(draw.choice * lean > 0)
			{
				if(canMove(x + 1, y))
				{
					move(x + 1, y);
				}
				else if(Dissolved.size() < dissolveCap && isInBounds(x + 1, y) == true && draw.Dot[x + 1][y].soluable == true)
				{
					take(x + 1, y);
				}
			}
		}
	}
	
	public void oldUpdate()
	{
		x = draw.thisX;
		y = draw.thisY;
		dissolveCap = 2;
		
		//if(Dissolved.size() < dissolveCap) // Number of dots this dot can absorb
		//absorb();
		//else 
		if(Dissolved.size() > dissolveCap)
		{
			eject();
		}
		if(moved == false)
		if(canMove(x, y + 1) == true)
		{
			move(x, y + 1);
		}
		else if(Dissolved.size() < dissolveCap && isInBounds(x, y + 1) == true && draw.Dot[x][y + 1].soluable == true)
			{
				take(x, y + 1);
			}

		else
		{
			//draw.coin = draw.random.nextInt(2);
			if(draw.coin + lean <= 1)
			{
				if(canMove(x - 1, y))
				{
					move(x - 1, y);
				}
				else if(Dissolved.size() < dissolveCap && isInBounds(x - 1, y) == true && draw.Dot[x - 1][y].soluable == true)
				{
					take(x - 1, y);
				}
			}
			if(draw.coin + lean > 1)
			{
				if(canMove(x + 1, y))
				{
					move(x + 1, y);
				}
				else if(Dissolved.size() < dissolveCap && isInBounds(x + 1, y) == true && draw.Dot[x + 1][y].soluable == true)
				{
					take(x + 1, y);
				}
			}
		}
		
		if(moved == false)
		flow(/*20*/);
	}
}