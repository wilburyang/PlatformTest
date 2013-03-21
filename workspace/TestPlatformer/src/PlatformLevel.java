import org.newdawn.slick.*;

public class PlatformLevel {

	Image background = null;
	Image floor = null; //temporary floor image
	
	PlatformLevel()
	{
		
	}
	
	public void loadBackground(String file1, String file2) throws SlickException
	{
		background = new Image(file1); //loads background image
		floor = new Image(file2); // temporary floor image load
	}
	
	public void drawLevel()
	{
		background.draw(0, 0);
		floor.draw(0, 400);
	}
}
