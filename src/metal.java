import java.awt.Color;


public class metal extends dot
{
	public metal()
	{
		id = 4;
		density = 10;
		color = new Color(draw.random.nextInt(40) + 100, draw.random.nextInt(40)+ 100, 100);
		soluable = false;
	}
	
	@Override
	public void update()
	{
		
	}
}
