import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/*
---------------------------------- IMPORTANT NOTES ----------------------------------
- In another vain attempt to fix everything, I broke everything.
- I've replaced the dirt and water update code with an empty method and renamed the original to "updateOld".
- I've also worked to phase out the dice rolling thingy because 40000 random numbers every frame is apparently "horrible and inneficient". Pssh.
- Now every frame "Choice" rolls a random number from -10 to 10 and water is created with a positive or negative "lean".
- When a dot of water needs to know which direction to move, it looks at "Choice" and multiplies it by its "lean". Positive moves it one way, negative the other.
- This was a terrible idea. Now every dot of water moves perfectly choreographed with every other dot, either doing the same or the opposite of its neighbors.
- The result is two jittering masses when you pour a straight line of water down.
- Feel free to gut this program anywhere you feel like it, most of this code is probably gonna be replaced by better stuff before this thing ever sees the light of day anyway.
- Good luck!
*/

public class draw extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
    Thread runner;
    PointerInfo a = MouseInfo.getPointerInfo();
    
	static Random random = new Random();
	
	static int direction;
	static int choice;
	
	static int thisX,thisY; //Just used to tell Dot[][] what the currently selected X and Y coorinates are.
	static int coin; //Flipped when a random int is needed. May have more than two sides. No, it's not a die, shut up.
	static int parts, dirts, waters, others; //Used to show how many of each element there are on-screen.
	static int size = 3; //Scale of the screen. Doesn't change the number of tiles, just the size of them.
	static int gravity = 1;
	static int temp = 100; //Room Temperature. This is the number temp tiles will slowly fade to over time.
	static int screenLength = 200; //Number of rows in dot matrix.
	static int screenWidth = 200; //Number of columns in dot matrix.
	static int mouseX2 = 10, mouseY2 = 10, mouseX = 10, mouseY = 10;
	static int selected = 1;
	static Point menu = new Point(0, screenLength * size);
	static Boolean mouseHeld = false;
	static Boolean rightMouseHeld = false;
	static Boolean mouseClicked = false;
	static Color fill;
	
    static dot[][] Dot; //The grid itself.
    static int[][] Temp; //The heat grid.
    static ArrayList<button> Buttons = new ArrayList<button>(); //The list of buttons for ease of drawing.
    static dot Store; //A single dot used by the others when they need to move from one spot to another in the Dot array.
    
    public draw()
    {
    	addButtons();
    	Store = new dot();
    	Dot = new dot[screenWidth][screenLength];
    	for(int y = screenLength - 1; y >= 0; y--)
		{
			for(int x = screenWidth - 1; x >= 0; x--)
			{   
    	    		Dot[x][y] = new air();

    	    }
    	}
    	
    	Temp = new int[screenWidth][screenLength];
    	for(int y = screenLength - 1; y >= 0; y--)
		{
			for(int x = screenWidth - 1; x >= 0; x--)
			{
    	    	Temp[x][y] = temp;
    	    }
    	}
    	
 
    }
    
    
    
    public void updateMouse()
    {
    	a = MouseInfo.getPointerInfo();
    	mouseX2 = mouseX;
    	mouseY2 = mouseY;
    	mouseX = a.getLocation().x - this.getLocationOnScreen().x;
    	mouseY = a.getLocation().y - this.getLocationOnScreen().y;
    }
    
    public void start()
    {
    	if(runner == null)
    	{
    	runner = new Thread(this);
    	runner.start();
    	}
    }
    
    public void stop()
    {
    	if(runner != null)
    	{
    		runner = null;
    	}
    }

    public void run()
    {
    	while(true)
    	{
    		choice = random.nextInt(20) - 10;
    		direction = random.nextInt(4);
    		updateMouse();
    	    repaint();
    	    updateDots();
    	    updateButtons();
    	    mouseClicked = false;
    	    
    		try {
				Thread.sleep(20); //Milliseconds between frames.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void addButtons() //Text, id, r, g, b
    {
    	Buttons.add(new button("Dirt", 1));
    	Buttons.add(new button("Water", 2));
    	Buttons.add(new button("Dye", 3));
    	Buttons.add(new button("Metal", 4));
    	//Buttons.add(new button("Test", 5));
    }
    
    public void updateButtons()
    {
    	for(int that = 0; that <= Buttons.size() - 1; that++)                         //MENU
		{
    		if(mouseX >= menu.x + 1 + (that * 51) && mouseX <= menu.x + 51 + (that * 51) && mouseY > menu.y/* + 2*/ && mouseY < menu.y + 13)
    		{
    			Buttons.get(that).selected = true;
    			if(mouseClicked == true)
    				Buttons.get(that).press();
    		}
    		else
    			Buttons.get(that).selected = false;
			//(menu.x + 1 + (that * 51), menu.y + 2, 50, 13);
		}
    }
    
    public void updateDotCount() //Recount all tiles.
    {
    	parts = 0;
    	dirts = 0;
    	waters = 0;
    	others = 0;
    	for(thisY = screenLength - 1; thisY >= 0; thisY--)
		{
			for(thisX = screenWidth - 1; thisX >= 0; thisX--)
			{
				if(Dot[thisX][thisY].id != 0)
					parts++;
				if(Dot[thisX][thisY].id == 1)
					dirts++;
				if(Dot[thisX][thisY].id == 2)
					waters++;
				if(Dot[thisX][thisY].id >= 3)
					others++;
			}
		}
    }
    
    public void updateDots()
    {
    	for(thisY = screenLength - 1; thisY >= 0; thisY--)
		{
			for(thisX = screenWidth - 1; thisX >= 0; thisX--)
			{
				if(thisX >= 0)
				{
					Dot[thisX][thisY].update();
				}
			}
		}
    	
    	for(int y = 0; y <= screenLength - 1; y++)
		{
			for(int x = 0; x <= screenWidth - 1; x++)
			{
				Dot[x][y].moved = false;
			}
		}
    	
    	if(mouseHeld == true)
    	{
    		if(Store.isInBounds(mouseX / size + 1, mouseY / size) == true && Store.canMove(mouseX / size + 1, mouseY / size) == true)
    		{
    			if(selected == 1)
    				Dot[mouseX / size + 1][mouseY / size] = new dirt();
    			else if(selected == 2)
    				Dot[mouseX / size + 1][mouseY / size] = new water();
    			else if(selected == 3)
    				Dot[mouseX / size + 1][mouseY / size] = new dye(random.nextInt(200), random.nextInt(200), random.nextInt(200));
    			else if(selected == 4)
    				Dot[mouseX / size + 1][mouseY / size] = new metal();
    		}
    	}
    	else if(rightMouseHeld == true)
    	{
    		if(Store.isInBounds(mouseX / size + 1, mouseY / size) == true)
    		{
    			Dot[mouseX / size + 1][mouseY / size] = new air();
    		}
    	}
    	
    	Store.moved = false;
		updateDotCount();
    }
    
	public void paint(Graphics g)
	{
	super.paint(g);
	
	//g.setColor(Color.BLACK);
	//g.fillRect(0, 0, screenWidth, screenLength);
	
		for(int y = screenLength - 1; y >= 0; y--)
		{
			for(int x = screenWidth - 1; x >= 0; x--)
			{
				g.setColor(Dot[x][y].color);
				g.fillRect(x * size, y * size, size, size);
				if(Dot[x][y].Dissolved.size() > 0)
				{
					for(int b = 0; b < Dot[x][y].Dissolved.size(); b++)
					{
						//System.out.println("Dots: " + (b + 1));
						fill = new Color(Dot[x][y].Dissolved.get(b).color.getRed(), Dot[x][y].Dissolved.get(b).color.getGreen(), Dot[x][y].Dissolved.get(b).color.getBlue(), 50);
						g.setColor(fill);
						//g.fillRect(x * 2, y * 2, 2, 2);
						g.fillRect(x * size, y * size, size, size);
					}
				}
			}
		}
		
		/*---------------------------------------------------------------------------*/
		
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 4, 4);                           //CURSOR
		
		g.setColor(Color.GRAY);                                     //DIVIDER
		g.drawLine(0, screenLength * size + 1, screenWidth, screenLength * size + 1);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, screenLength * size, screenWidth * size, Buttons.size() * 12);
		
		for(int that = 0; that <= Buttons.size() - 1; that++)       //MENU
		{
			if(Buttons.get(that).selected == true)
			g.setColor(Color.DARK_GRAY);
			else
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(menu.x + 1 + (that * 51), menu.y + 2, 50, 13);
			g.setColor(Color.BLACK);
			g.drawString(Buttons.get(that).text, menu.x + 1 + (that * 52), menu.y + 13);
		}
		
		g.setColor(Color.WHITE);                                    //TEXT
		g.drawString("Water: " + draw.waters, 2, 10);
		g.drawString("Dirt: " + draw.dirts, 2, 20);
		g.drawString("Other: " + draw.others, 2, 30);
		g.drawString("Total: " + draw.parts, 2, 40);
		g.drawString("Choice: " + draw.choice, 2, 50);
		g.drawString("Mouse X: " + shapes.getX() , 2, 60);
		g.drawString("Mouse Y: " + shapes.getY(), 2, 70);
		
	}
}




/*
if(Dot[x][y].id == 0)            //AIR
{
	g.setColor(Color.BLACK);
	g.fillRect(x * size, y * size, size, size);
}
if(Dot[x][y].id == 1)               //DIRT
{
	if(Dot[x][y].style == 0)
	{
		g.setColor(Color.getHSBColor(0.07f, 1f, 0.5f));
		g.fillRect(x * size, y * size, size, size);
	}
	else
	{
		g.setColor(Color.getHSBColor(0.07f, 0.9f, 0.6f));
		g.fillRect(x * size, y * size, size, size);
	}
}
if(Dot[x][y].id == 2)               //WATER
{
	if(Dot[x][y].style == 0)
	{
		g.setColor(Color.getHSBColor(0.7f, 0.5f, 0.6f));
		g.fillRect(x * size, y * size, size, size);
	}
	else
	{
		g.setColor(Color.getHSBColor(0.7f, 0.5f, 0.7f));
		g.fillRect(x * size, y * size, size, size);
	}
}
*/