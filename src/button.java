
public class button
{
	String text;
	int id;
	boolean selected = false;
	
	public button(String Text, int Id)
	{
		text = Text;
		id = Id;
	}

	public void press()
	{
		draw.selected = id;
	}
}
