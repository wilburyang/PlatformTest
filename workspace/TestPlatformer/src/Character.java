import org.newdawn.slick.*;
import org.newdawn.slick.tiled.*;

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
	
	float xSpeed;
	float ySpeed;
	float scale;
	double gCount;
	
	boolean fallen; //checks if fallen off map
	
	public Character()
	{
		life = 3;
		
		//isOnGround = true;
		
		scale = 1.0f;
		xSpeed = 0.2f;
		ySpeed = 0.4f; //adjusts for jumps
		
		//isOnTile = true;
		
		fallen = false;
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
	public void jump(int change)
	{
		System.out.println("jumping");
		ySpeed = 5f; //initial velocity
		//y -= ySpeed;
		//y -= 100; //gives initial movement
	}
	
	public boolean isCollision(PlatformLevel level, GameContainer gc)
	{
		//Input input = gc.getInput();
		
		//temp floor:
		/*if(y+height>=600)
		{
			y = 600-height;
		}*/
		
		//check if any of character is touching barrier
		int xPos = (int)x/30; //divided by tile size
		int yPos = (int)y/30;
		
		System.out.println(xPos + " , " + yPos);
		
		//simplified checker
		if(level.barrier[xPos][yPos]) //checks with boxes tile dimensions
		{
			System.out.println("barrier!");
			return true;
		}
		return false;
		
		/*for(int xAxis=(int)(x/30); xAxis<Math.floor((x+width)/30);xAxis++)
		{
			for(int yAxis=(int)(y/30); yAxis<(Math.floor(y+height)/30);yAxis++)
			{
				if(barriers[xAxis][yAxis]) //checks with boxes tile dimensions
				{
					System.out.println("barrier!");
					speed = 0;
					ySpeed = 0;
				}
			}
		}*/
		
		
		
		//old:
		/*if(x+width > object.x && x < object.x+object.width
				&& y+height >= object.y && y+height < object.y+object.height)//staying on ground
		{
			y = object.y-height;
			isOnTile = true;
		}
		else
		{
			isOnTile = false;
		}
		if(input.isKeyDown(Input.KEY_D)) //normal movement on ground
		{
			if(x+width > object.x && x < object.x+object.width
					&& y > object.y+object.height && y < object.y) //check x axis collision
			{
				x = object.x-width;
			}
		}
		if(input.isKeyDown(Input.KEY_A)) //normal movement on ground
		{
			if(x+width > object.x && x < object.x+object.width && y+height > object.y && y+height < object.y+object.height) //check x axis collision
			{
				x = object.x+object.width;
			}
		}
		if(input.isKeyDown(Input.KEY_W)) //normal movement on ground
		{
			if(x+width > object.x && x < object.x+object.width && y+height > object.y && y < object.y+object.height) //check x axis collision
			{
				y = object.y+object.height;
			}
		}*/
	}
	
	public boolean isOnFloor(PlatformLevel level, GameContainer gc)
	{
		//Input input = gc.getInput();
		
		//temp floor:
		if(y+height>=600)
		{
			y = 600-height;
		}
		
		//check if any of character is touching barrier
		int xPos = (int)(x+width/2)/30; //divided by tile size
		int yPos = (int)(y+height)/30;
		
		//System.out.println(xPos + " , " + yPos);
		
		//simplified checker
		if(level.barrier[xPos][yPos]) //checks with boxes tile dimensions
		{
			//System.out.println("is on floor");
			return true;
		}
		return false;
	}
}
