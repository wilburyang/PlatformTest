import org.newdawn.slick.*;

public class Player {

	Image playerImage = null;
	int life;
	int height;
	int width;
	float x;
	float y;
	
	boolean isOnGround;
	
	float speed;
	float scale;
	
	public Player()
	{
		life = 3;
		x = 0; //initial position
		y = 400 - 50; //to be minus height
		
		isOnGround = true;
		
		scale = 1.0f;
		speed = 0.2f;
	}
	
	public void loadPlayerImage(String file) throws SlickException
	{
		playerImage = new Image(file);
	}
	
	public void drawPlayer()
	{
		playerImage.draw(x, y, scale);
	}
}
