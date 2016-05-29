import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
public class shapes 
{
	static PointerInfo a;
	static int ShapesTest;
	static Point test;
	
	public static int getX()
	{
		a = MouseInfo.getPointerInfo();
		return a.getLocation().x - 5;
	}
	
	public static int getY()
	{
		a = MouseInfo.getPointerInfo();
		return a.getLocation().y - 29;
	}

	public static void main(String[] args)
	{
	draw Draw = new draw();
	int screenLength = draw.size * 100;
	int screenWidth = draw.size * 100;

	JFrame frame = new JFrame("Dusty");
	frame.setVisible(true);
	frame.setResizable(false);
	frame.setSize(screenWidth * 2 + 6,screenLength * 2 + 29 + (draw.Buttons.size() * 12)); //(Width * 2 + 6, Height * 2 + 29)
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(Draw);
	Draw.start();
	

	
	MouseAdapter mouseTest = new MouseAdapter() { 
        public void mousePressed(MouseEvent e) {
        	if (e.getButton() == 3)
        	{
        		draw.rightMouseHeld = true;
        	}
        	else 
            draw.mouseHeld = true;
          }
          public void mouseReleased(MouseEvent e)
          {
          	draw.mouseHeld = false;
          	draw.rightMouseHeld = false;
          }
          public void mouseClicked(MouseEvent e)
          {
        	 draw.mouseClicked = true;
          }
          
        };
	
    frame.addMouseListener(mouseTest);
    
   
	}
}