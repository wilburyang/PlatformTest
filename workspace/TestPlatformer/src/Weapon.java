import org.newdawn.slick.*;

public class Weapon {
	
	Image images[]; //series of frames for weapon
	
	Animation animation;
	Animation flippedAnimation; //faces left instead of right
	
	int totalFrames;
	
	int duration; //how long weapon attack lasts
	
	int delay; //minimum time between each attack
	
	private int x; //position of animation on x axis
	private int y; //position on y axis
	
	private boolean direction; //default = false = facing right
	
	//boolean[][] hitBox;
	
	Weapon(int frameCount)
	{
		totalFrames = frameCount;
		
		//defaults:
		direction = false; //to right
		duration = 400; //ms
		delay = 2000; //ms
	}
	
	//creates animation based on loaded image array, defaults png
	public void loadAnimation(String filePrefix) throws SlickException
	{
		loadImage(filePrefix); //TODO create second animation that is flipped
		
		animation = new Animation(images, duration);
		
		/*initialize hitBox for collision detection
		hitBox = new boolean
				[animation.getWidth()]
				[animation.getHeight()];*/
		//clearHitBox(); //set initial values for hitBox
	}
	//creates animation based on loaded image array
	public void loadAnimation(String filePrefix, String extension) throws SlickException
	{
		loadImage(filePrefix, extension);
		
		animation = new Animation();
		
		setPosition(0, 0, false); //initialized position
		
		/*initialize hitBox for collision detection
		hitBox = new boolean
				[animation.getWidth()]
				[animation.getHeight()];*/
		//clearHitBox(); //set initial values for hitBox
	}
	
	//loads image array from file source, defaults png extension
	private void loadImage(String filePrefix) throws SlickException
	{
		images = new Image[totalFrames];
		
		for(int i = 0; i < totalFrames; i++)
		{
			//adds image to array based on index
			images[i] = new Image(filePrefix + i + ".png");
		}
	}
	//loads image array from file source
	private void loadImage(String filePrefix, String extension) throws SlickException
	{
		images = new Image[totalFrames]; //creates image array
		
		for(int i = 0; i < totalFrames; i++)
		{
			//adds image to array based on index
			images[i] = new Image(filePrefix + i + "." + extension);
		}
	}
	
	public float getX()
	{
		return (float)x;
	}
	public float getY()
	{
		return (float)y;
	}
	public int getWidth()
	{
		return animation.getWidth();
	}
	public int getHeight()
	{
		return animation.getHeight();
	}
	
	public void setPosition(int xPosition, int yPosition, boolean flip)
	{
		x = xPosition;
		y = yPosition;
		direction = flip;
	}
	
	/*public void setHitBox() //edits hitBox to match current frame
	{
		corners[0][0] = 
	}*/
	
	/*public void clearHitBox()
	{
		for(int xAxis = x; xAxis < x+animation.getWidth(); xAxis++)
		{
			for(int yAxis = y; xAxis < y+animation.getHeight(); yAxis++)
			{
				hitBox[xAxis][yAxis] = false;
			}
		}
	}*/
	
	public void drawInactive(int shift) //not used for npcs
	{
		
	}
	
	public void drawActive(int shift)
	{
		if(!direction)
		{
			animation.draw(x-shift, y);
		}
		else
		{
			//TODO add flipped animation object
			animation.draw(x-shift, y);
		}
	}
}
