import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;



public class water extends dot{
	
	ActionListener flow = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
	    	  flow();
	      }
	  };
	  Timer flowTimer = new Timer(60, flow);
	  
	  ActionListener rush = new ActionListener(){
		  public void actionPreformed(ActionEvent event){
			  if(draw.choice * lean < 0)
				{
					if(rushOngoing == false){
						//System.out.println(draw.screenWidth + " " + x + " " + y);
						rush(draw.screenWidth, y);
					}
				}
			  if(draw.choice * lean > 0)
				{
					if(rushOngoing == false)
						rushDirection("right");
				}	
	  }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	  };
	  Timer rushTimer = new Timer(30, flow);


		

	
	boolean isTopLayer;
	public water()
	{
		lean = setLean();
		id = 2;
		density = 1;
		state = 2;
		isTopLayer = false;
		/*draw.coin = draw.random.nextInt(2);
		if(draw.coin == 1)
		color = new Color(0, 0, 200);
		else*/
		color = new Color(draw.random.nextInt(25), 0, draw.random.nextInt(75) + 175);
	}


	
	public void update()
	{

		x = draw.thisX;
		y = draw.thisY;
		dissolveCap = 2;
		
		//if(Dissolved.size() < dissolveCap) // Number of dots this dot can absorb
		//absorb();
		//else 
		if(Dissolved.size() > dissolveCap)
		{
			System.out.println("This is happening1");
			eject();
		}
		if(moved == false)
		if(canMove(x, y + 1) == true)
		{
			move(x, y + 1);
			this.isFalling = true;
		}
		else if(Dissolved.size() < dissolveCap && isInBounds(x, y + 1) == true && draw.Dot[x][y + 1].soluable == true)
			{
				this.isFalling = false;
				take(x, y + 1);
			}
		else if(isInBounds(x,y+1) == true && canMove(x,y+1) == false && draw.Dot[x][y+1].isFalling == false && draw.Dot[x][y+1].id == 2 && draw.Dot[x][y-1].id == 0)
		{
			this.isFalling = false;
			//isTopLayer = true;
			flowTimer.start();
		}
		else if(isInBounds(x,y+1) == true && canMove(x,y+1) == false && draw.Dot[x][y+1].isFalling == false){
			this.isFalling = false;
			flowTimer.start();
		}
		else if(isInBounds(x,y+1) == false && canMove (x,y+1) == false)
			this.isFalling = false;
	}

	public void flow(){
		if(!canMove(x,y+1) && isInBounds(x,y+1) && isFalling == false && draw.Dot[x][y+1].isFalling == false && isTopLayer == true){
			if(draw.choice * lean < 0)
			{
				if(rushOngoing == false){
					//System.out.println(draw.screenWidth + " " + x + " " + y);
					rush(draw.screenWidth, y);
				}
				if(Dissolved.size() < dissolveCap && isInBounds(x - 1, y) == true && draw.Dot[x - 1][y].soluable == true)
				{
					take(x - 1, y);
				}
			}
			if(draw.choice * lean > 0)
			{
				if(rushOngoing == false)
					rushDirection("right");
				if(Dissolved.size() < dissolveCap && isInBounds(x + 1, y) == true && draw.Dot[x + 1][y].soluable == true)
				{
					take(x + 1, y);
				}
			}	
		}
		
		else if(!canMove(x, y+1) && isInBounds(x, y+1) && isFalling == false && draw.Dot[x][y+1].isFalling == false /*&& isTopLayer == false*/){
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
		else
			flowTimer.stop();
	}
		  
	public void dissipate()
	{
		if(!canMove(x, y+1) && isInBounds(x, y+1) && isFalling != true)
		{
			
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
	
	public int setLean(){
		if(draw.random.nextInt(2) == 1)
			return -1;
		else
			return 1;
		
	}
	
	public int setOppositeLean(){
		if(setLean() == 1)
			return -1;
		else
			return 1;
	}
}