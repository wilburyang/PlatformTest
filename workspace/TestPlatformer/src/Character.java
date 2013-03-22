import org.newdawn.slick.*;

public class Character {

	Image characterImage = null;
	int life;
	int height;
	int width;
	float x;
	float y;
	
	boolean isOnTile; //floating tile floor
	
	/*int direction; //testing for directional jump
	int forward = 1;
	int backward = -1; //workaround for direction names
	int neutral = 0;
	
	boolean isOnGround;*/
	
	float speed;
	float ySpeed;
	float scale;
	
	public Character()
	{
		life = 3;
		
		//isOnGround = true;
		
		scale = 1.0f;
		speed = 0.2f;
		ySpeed = speed; //adjusts for jumps
		
		//isOnTile = true;
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
	
	public void checkCollision(Tile object, GameContainer gc)
	{
		Input input = gc.getInput();
		
		if(x+width > object.x && x < object.x+object.width
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
		}
	}
}
