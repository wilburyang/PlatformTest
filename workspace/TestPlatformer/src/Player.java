//Redundnat
import org.newdawn.slick.*;

public class Player {

	Image playerImage = null;
	int life;
	int height;
	int width;
	float x;
	float y;
	int direction; //testing for directional jump
	int forward = 1;
	int backward = -1; //workaround for direction names
	int neutral = 0;
	
	boolean isOnGround;
	
	float speed;
	float scale;
	float fallCounter;
	
	public Player()
	{
		life = 3;
		
		isOnGround = true;
		
		scale = 1.0f;
		speed = 0.2f;
		fallCounter = 0;
	}
	
	public void loadPlayerImage(String file) throws SlickException
	{
		playerImage = new Image(file);
		height = playerImage.getHeight(); //must occur at start
		x = 0; //initial position
		y = 400 - height;
	}
	
	public void drawPlayer()
	{
		playerImage.draw(x, y, scale);
	}
	
	//jump method, make smooth says Josh
	
	public void fall() //gravity effect
	{
		y += fallCounter; //falls faster than moves up
		fallCounter += 0.1;
	}
}
