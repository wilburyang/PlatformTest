//Redundant
import org.newdawn.slick.*;

public class EditorObject {

	Image image = null;
	int x;
	int y;
	float scale;
	
	Font font;
	
	String buttonText = null;
	boolean isText;
	
	EditorObject(String file, int xinit, int yinit, double size) throws SlickException
	{
		image = new Image(file);
		x = xinit;
		y = yinit;
		scale = (float)size;
		buttonText = "text";
		isText = false;
	}
	
	public void setText(String txt)
	{
		buttonText = txt;
		isText = true;
	}
	
	public void drawObject(Graphics g)
	{
		image.draw(x, y, scale);
		
		//int test = font.getWidth("test");
		font.drawString(100f, 100f, "test hello");
		if(isText)
		{
			//g.drawString(buttonText, image.getWidth()/2-font.getWidth(buttonText),
					//image.getHeight()/2-font.getHeight(buttonText));
		}
	}
}
