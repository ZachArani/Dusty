import java.awt.Color;
import java.util.ArrayList;

public class dot //This is what the game is centered around - dot entities. This is the contents of each one. Each frame they will all think their next move based on what kind they are.
{
	/*------------------------MOVEMENT------------------------*/
	int x;  //X-Coordinate
	int y;  //Y-Coordinate
	int a,b,c,d;  //Used in Rush
	int lean;  //Natural lean a dot has in either direction.
	int xVel = 0;  //X velocity in dots/frame.
	int yVel = 0;  //Y velocity in dots/frame.
	boolean moved;  //Whether or not this dot has moved this frame.
	Color color;  //Color of the dot.
	ArrayList<dot> Dissolved = new ArrayList<dot>();  //Dots currently inside this dot.
	
	/*------------------PHYSICAL PROPERTIES-------------------*/
	int id;  //What element this dot is
	int density; //Current density, changed by temperature. TO-DO: Add some form of pressure
	int dissolveCap; //How many dots this one can absorb.
	boolean soluable = false; //Whether or not this dot can be dissolved into other dots.
	int state;  //What physical state is this dot in? 0 = solid, 1 = liquid, 2 = gas. TO-DO: Use state
	int weight; //How fast this dot falls.
	
	/*-------------------------VISUAL-------------------------*/
	//int style = draw.random.nextInt(2);
	
	
	
	/*------------------------INTERNAL------------------------*/
	public void moveData(int xx, int yy)
	{
		draw.Dot[xx][yy].id = this.id;
		draw.Dot[xx][yy].state = this.state;
		//draw.Dot[xx][yy].style = this.style;
		//draw.Dot[xx][yy].moved = this.moved;
		draw.Dot[xx][yy].density = this.density;
	}
	
	public void storeData()
	{
		draw.Store.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
		draw.Store.id = this.id;
		draw.Store.state = this.state;
		//draw.Store.style = this.style;
		//draw.Store.moved = this.moved;
		draw.Store.density = this.density;
	}

	/*---------------------------SET----------------------------*/
	public void update() //Empty on PORPOISE! Meant to ensure the game doesn't crash in the event this method is called on a blank tile.
	{
		System.out.print("Empty tile called to update. Work on your efficiency, boi! ");
	}
	
	public void rush(int xx, int yy) //Move dot as close to [x][y] as possible, stopping when it hits anything that isn't air.
	{
		a = x;
		c = x;
		b = y;
		d = y;
		
		while(true) //X = a, Y = b
		{
			move(x,y + 1);
			break;
		}
	}
	
	public void move(int xx, int yy) //Swaps this dot with target dot.
	{
		draw.Dot[xx][yy].moved = true;
		draw.Dot[x][y].moved = true;
		draw.Store.moved = true;
		
		draw.Store = draw.Dot[xx][yy];
		draw.Dot[xx][yy].storeData();
		
		draw.Dot[xx][yy] = draw.Dot[x][y];
		this.moveData(xx, yy);
		
		draw.Dot[x][y] = draw.Store;
		draw.Store.moveData(x, y);

		draw.Dot[xx][yy].moved = true;
	}
	
	public void flow()
	{
		
	}
	
	public void oldFlow(/*int viscosity*/) //Try once to switch places with a neighboring dot of equal density
	{
		draw.coin = draw.random.nextInt(4/*viscosity*/);
		
		if(draw.coin == 1)
		{
			if(isInBounds(x, y - 1) && draw.Dot[x][y - 1].state == this.state && this.density == draw.Dot[x][y - 1].density)
			move(x, y - 1);
		}
		else if(draw.coin == 2)
		{
			if(isInBounds(x - 1, y) && draw.Dot[x - 1][y].state == this.state && this.density == draw.Dot[x - 1][y].density)
			{
			move(x - 1, y);
			}
		}
		else if(draw.coin == 3)
		{
			if(isInBounds(x + 1, y) && draw.Dot[x + 1][y].state == this.state && this.density == draw.Dot[x + 1][y].density)
			move(x + 1, y);
		}
		else if(draw.coin == 4)
		{
			if(isInBounds(x, y + 1) && draw.Dot[x][y + 1].state == this.state && this.density == draw.Dot[x][y + 1].density)
			move(x, y + 1);
		}
	}
	
	public void absorb()
	{
		
	}
	
	public void oldAbsorb() //Looks for a nearby dot capable of being absorbed
	{
		//draw.coin = draw.random.nextInt(6);
		
		//TO DO: Make absorb depend on Coin AND this dot's internal int
		
		if(draw.coin == 1)
		{
			if(isInBounds(x, y - 1) && id != draw.Dot[x][y - 1].id && draw.Dot[x][y - 1].soluable == true && this.density == draw.Dot[x][y - 1].density)
			take(x, y - 1);
		}
		else if(draw.coin == 2)
		{
			if(isInBounds(x - 1, y) && id != draw.Dot[x - 1][y].id && draw.Dot[x - 1][y].soluable && this.density == draw.Dot[x - 1][y].density)
			take(x - 1, y);
		}
		else if(draw.coin == 3)
		{
			if(isInBounds(x + 1, y) && id != draw.Dot[x + 1][y].id && draw.Dot[x + 1][y].soluable == true && this.density == draw.Dot[x + 1][y].density)
			take(x + 1, y);
		}
		else if(draw.coin == 4)
		{
			if(isInBounds(x, y + 1) && id != draw.Dot[x][y + 1].id && draw.Dot[x][y + 1].soluable == true && this.density == draw.Dot[x][y + 1].density)
			take(x, y + 1);
		}
	}
	
	public void eject()
	{
		
	}
	
	public void oldEject() //Try once to drop a dot from inside this dot to an adjacent air tile.
	{
		draw.coin = draw.random.nextInt(5);
		
		if(draw.coin == 1)
		{
			if(isInBounds(x, y - 1) && draw.Dot[x][y - 1].id == 0)
			drop(x, y - 1);
		}
		else if(draw.coin == 2)
		{
			if(isInBounds(x - 1, y) && draw.Dot[x - 1][y].id == 0)
			drop(x - 1, y);
		}
		else if(draw.coin == 3)
		{
			if(isInBounds(x + 1, y) && draw.Dot[x + 1][y].id == 0)
			drop(x + 1, y);
		}
		else if(draw.coin == 4)
		{
			if(isInBounds(x, y + 1) && draw.Dot[x][y + 1].id == 0)
			drop(x, y + 1);
		}
	}
	
	public void drop(int xx, int yy) //Replaces target dot with the first dot inside this one.
	{
		draw.Dot[xx][yy] = draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1);
		draw.Dot[xx][yy].id = draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).id;
		draw.Dot[xx][yy].state = draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).state;
		draw.Dot[xx][yy].density = draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).density;
		draw.Dot[xx][yy].color = new Color(draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).color.getRed(), draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).color.getGreen(), draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).color.getBlue());
		draw.Dot[x][y].Dissolved.remove(Dissolved.size() - 1);
		
		
		draw.Dot[x][y].moved = true;
	}
	
	public void take(int xx, int yy) //Make new dot of the same kind as target in this Dissolved, then populate it with all of target dot's info.
	{
		draw.Dot[x][y].Dissolved.add(draw.Dot[xx][yy]);
		draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).id = draw.Dot[xx][yy].id;
		draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).state = draw.Dot[xx][yy].state;
		draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).density = draw.Dot[xx][yy].density;
		draw.Dot[x][y].Dissolved.get(Dissolved.size() - 1).color = new Color(draw.Dot[xx][yy].color.getRed(), draw.Dot[xx][yy].color.getGreen(), draw.Dot[xx][yy].color.getBlue());
		
		draw.Dot[xx][yy] = new air();
		
		draw.Dot[x][y].moved = true;
	}
	

	/*---------------------------GET----------------------------*/
	
	public boolean isInBounds(int x, int y)
	{
		if(x < draw.screenWidth && x >= 0 && y < draw.screenLength && y >= 0)
			return true;
			else return false;
	}
	
	public boolean canMove(int x, int y) //Checks if the target coordinates are safe to go to.
	{
		if(isInBounds(x, y) == true && draw.Dot[x][y].moved == false && draw.Dot[x][y].id == 0)
		return true;
		else
			return false;
	}
	
	public boolean canMove(int x, int y, int id) //Checks if the target coordinates are safe to go to, AND if the id is the one described.
	{
		if(isInBounds(x, y) == true && draw.Dot[x][y].moved == false && draw.Dot[x][y].id == id)
		return true;
		else
			return false;
	}
}