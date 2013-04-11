import org.newdawn.slick.*;

public class Character {

	Image image[] = null;
	int totalFrames; //may not be needed
	protected int currentAnimation;
	private int fDuration; //amount of time frames remain
	Animation animation[] = null;
	
	protected final int NORMAL =		0;
	protected final int LEFT =		1;
	protected final int RIGHT =		2;
	protected final int JUMP =		3;
	
	protected int life;
	int height;
	int width;
	float x;
	float y;
	
	int PLAYER =	0; //id number for characters
	int NPC =		1;
	
	int ID; //id player or npc
	
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
		
		ID = PLAYER; //default, versus NPC
		
		//isOnGround = true;
		
		scale = 1.0f;
		xSpeed = 0.2f;
		ySpeed = 0.4f; //adjusts for jumps
		
		x = 0; //safe guard, changed immediately
		y = 0;
		
		currentAnimation = 0;
		fDuration = 200; //ms?
		//isOnTile = true;
		
		fallen = false;
	}
	
	public void loadAnimation(String fileName, String extension, int total) throws SlickException
	{
		loadImage(fileName, extension, total);
		
		width = (int) (image[0].getWidth()*scale);
		height = (int) (image[0].getHeight()*scale); //must occur at start
		
		animation = new Animation[3];
		
		for(int i = 1; i < 3; i++)
		{
			animation[i] = new Animation(image, fDuration); //will be different sub sections of image array
		}
	}
	
	private void loadImage(String fileName, String extension, int total) throws SlickException
	{
		totalFrames = total;
		image = new Image[totalFrames];
		
		for(int frame = 0; frame < totalFrames; frame++) //load each frame to image array
		{
			try
			{
				image[frame] = new Image(fileName + frame + extension);
			} catch (SlickException e) {
				System.err.println("Image file not found: " + fileName+frame+extension);
				return;
			}
		}
	}
	/*public void loadCharacterImage(String file, int total) throws SlickException
	{	
		image = new Image(file);
		width = (int) (image.getWidth()*scale);
		height = (int) (image.getHeight()*scale); //must occur at start
	}*/
	
	public void draw(int xShift)
	{
		if(currentAnimation != NORMAL) //if still, draw still instead of zeroeth animation
		{
			animation[currentAnimation].draw(x-xShift, y);
		}
		else
		{
			image[0].draw(x-xShift, y, scale);
		}
		currentAnimation = NORMAL; //reset to check again
	}
	
	public void animateLeft() //changes current frame based on duration
	{
		currentAnimation = LEFT;
	}
	
	public void animateRight() //changes current frame based on duration
	{
		currentAnimation = RIGHT;
	}
	
	//jump method, make smooth
	public void jump(int change)
	{
		System.out.println("jumping");
		if(ySpeed >= -1) //if falling, cannot jump
		{
			ySpeed = 7; //initial velocity
		}
	}
	
	/*public void reverse(String direction) //push back
	{
		if(direction.equals("left"))
		{
			y += xSpeed;
		}
		else if(direction.equals("right"))
		{
			y -= xSpeed;
		}
	}*/
	
	public void checkHurtBox() //kills player if hit
	{
		
	}
	
	//TODO switch to using "TOP_LEFT", etc...
	public boolean checkCollision(PlatformLevel level, GameContainer gc, String direction)
	{
		if(direction.equals("right")) //check right collision
		{
			int xPos = (int)(x+width-1)/30; //divided by tile size
			int yPosA = (int)(y)/30; //top
			int yPosB = (int)(y+height/2)/30; //middle
			int yPosC = (int)(y+height-1)/30; //bottom, but not floor
			
			//System.out.println(xPos + " , " + yPos);
			
			//simplified checker
			if(level.barrier[xPos][yPosA]
					|| level.barrier[xPos][yPosB]
					|| level.barrier[xPos][yPosC]) //checks with boxes tile dimensions
			{
				x--;
				checkCollision(level, gc, "right");
			}
		}
		else if(direction.equals("left")) //check left collision
		{
			int xPos = (int)(x+1)/30; //divided by tile size
			int yPosA = (int)(y+1)/30;
			int yPosB = (int)(y+height/2)/30;
			int yPosC = (int)(y+height-1)/30;
			
			//System.out.println(xPos + " , " + yPos);
			
			//simplified checker
			if(level.barrier[xPos][yPosA]
					|| level.barrier[xPos][yPosB]
					|| level.barrier[xPos][yPosC]) //checks with boxes tile dimensions
			{
				x++;
				checkCollision(level, gc, "left");
			}
		}
		else if(direction.equals("up")) //check top collision
		{
			int xPosA = (int)(x+1)/30; //divided by tile size
			int xPosB = (int)(x+width/2)/30; //checks middle of ch, left, and right
			int xPosC = (int)(x+width-1)/30;
			int yPos = (int)y/30;
			
			//System.out.println(xPos + " , " + yPos);
			
			//simplified checker for top collision
			if(level.barrier[xPosA][yPos]
					|| level.barrier[xPosB][yPos]
					|| level.barrier[xPosC][yPos]) //checks with boxes tile dimensions
			{
				y++;
				checkCollision(level, gc, "up");
				return true;
			}
		}
		return false; //only returns true if collision from above
	}

	
	public boolean isOnFloor(PlatformLevel level, GameContainer gc)
	{
		//Input input = gc.getInput();
		
		//temp floor , will initiate death method:
		if(y+height>=600) //falls to for
		{
			die();
			return true; //false floor to prevent collision check
		}
		
		//check if any of character is touching barrier
		int xPosA = (int)(x+1)/30; //divided by tile size
		int xPosB = (int)(x+width/2)/30; //check middle
		int xPosC = (int)(x+width-1)/30; //check right
		int yPos = (int)(y+height)/30;
		
		//System.out.println(xPos + " , " + yPos);
		
		//simplified checker
		if(level.barrier[xPosA][yPos]
				|| level.barrier[xPosB][yPos]
				|| level.barrier[xPosC][yPos]) //checks with boxes tile dimensions
		{
			//System.out.println("is on floor");
			return true;
		}
		return false;
	}
	
	public boolean alive() //return true if player has 1 or more lives
	{
		if(life > 0)
		{
			return true;
		}
		return false;
	}
	public void die() //when character loses life, reset position and state
	{
		lifeDown();
		
		if(life > 0)
		{
			x = 0;
			y = 400 - height;
		}
		else //full death, will initiate death animation
		{
			x = 0;
			y = 400 - height;
			lifeUp(3); //reset to full life (temp)
		}
	}
	public int getLife()
	{
		return life;
	}
	
	public void lifeUp(int amount)
	{
		life += amount;
	}
	public void lifeUp() //increment life by one
	{
		life++;
		System.out.println("life = " + life);
	}
	
	public void lifeDown(int amount)
	{
		life -= amount;
	}
	public void lifeDown()
	{
		life--;
		System.out.println("life = " + life);
	}
}
