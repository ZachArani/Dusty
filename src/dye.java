import java.awt.Color;


public class dye extends dot
{
	public dye(int r, int g, int b)
	{
		id = 3;
		density = 1;
		state = 2;
		color = new Color(r, g, b);
		soluable = true;
	}
	
	public void update()
	{

		x = draw.thisX;
		y = draw.thisY;
		

		
		if(moved == false)
		if(canMove(x + xVel, y + yVel + draw.gravity) == true || canMove(x + xVel, y + yVel + draw.gravity, 2) == true) //If dot can move down
		{
			this.isFalling = true;
			move(x + xVel, y + yVel + draw.gravity);
		}
		else
		{
			this.isFalling = false;
			xVel = 0;
			yVel = 0;
		}
		//xVel = xVel / 2;
		//yVel++;
	
	}
}
