import org.newdawn.slick.*;

public class Character {

	Image characterImage = null;
	int life;
	int height;
	int width;
	float x;
	float y;
	/*int direction; //testing for directional jump
	int forward = 1;
	int backward = -1; //workaround for direction names
	int neutral = 0;
	
	boolean isOnGround;*/
	
	float speed;
	float ySpeed;
	float scale;
	float fallCounter;
	
	public Character()
	{
		life = 3;
		
		//isOnGround = true;
		
		scale = 1.0f;
		speed = 0.2f;
		ySpeed = speed; //adjusts for jumps
		fallCounter = 0;
	}
	
	public void loadCharacterImage(String file) throws SlickException
	{
		characterImage = new Image(file);
		width = (int) (characterImage.getWidth()*scale);
		height = (int) (characterImage.getHeight()*scale); //must occur at start
		x = 0; //initial position
		y = 400 - height;
	}
	
	public void drawCharacter()
	{
		characterImage.draw(x, y, scale);
	}
	
	//jump method, make smooth
	public void jump()
	{
		y -= speed; //gives initial movement
	}
	
	public void checkCollision(Tile object)
	{
		if(x > object.x && x < object.x+object.width) //check x axis collision
		{
			if(y > object.y && x <= object.y+object.height) //check y axis collision
			{
				
			}
			if(y > object.y && x <= object.y+object.height) //check y axis collision
			{
				
			}
		}
		if(x+width < object.x && x+width > object.width)
		{
			if(y > object.y && x <= object.y+object.height) //check y axis collision
			{
				
			}
			if(y > object.y && x <= object.y+object.height) //check y axis collision
			{
				
			}
		}
	}
	
	/*public void fall() //gravity effect
	{
		y += fallCounter; //falls faster than moves up
		fallCounter += 0.1;
	}*/
}
