import org.newdawn.slick.*;

public class EditorObject {

	Image image = null;
	int x;
	int y;
	float scale;
	
	EditorObject(String file, int xinit, int yinit, double size) throws SlickException
	{
		image = new Image(file);
		x = xinit;
		y = yinit;
		scale = (float)size;
	}
	
	public void drawObject()
	{
		image.draw(x, y, scale);
	}
}
