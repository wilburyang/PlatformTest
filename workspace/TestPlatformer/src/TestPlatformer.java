import org.newdawn.slick.*;

public class TestPlatformer extends BasicGame {

	PlatformLevel lvl1 = null;
	Player player1 = null;
	
	public TestPlatformer()
	{
		super("Our Cool Platformer");
	}
	
	@Override
	public void init (GameContainer gc) throws SlickException
	{
		lvl1 =  new PlatformLevel(); //create new level object
		lvl1.loadBackground("data/testbackground.png", "data/testfloor.png");
		player1 = new Player();
		player1.loadPlayerImage("data/testplayer.png");
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_D) && player1.isOnGround) //normal movement on ground
		{
			player1.x += player1.speed*delta;
			player1.direction = player1.forward;
		}
		if(input.isKeyDown(Input.KEY_A) && player1.isOnGround)
		{
			player1.x -= player1.speed*delta;
			player1.direction = player1.backward;
		}
		
		if(input.isKeyDown(Input.KEY_W)) //jump command
		{
			player1.y -= player1.speed*delta;
			player1.isOnGround = false;
			
			player1.direction = player1.neutral; //default dir in jump
			if(input.isKeyDown(Input.KEY_D)) //normal movement on ground
			{
				player1.direction = player1.forward;
			}
			if(input.isKeyDown(Input.KEY_A))
			{
				player1.direction = player1.backward;
			}
		}
		
		if(!player1.isOnGround)
		{
			player1.fall();
			//shifts player based on last direction while in air
			if(player1.direction == player1.forward)
			{
				player1.x += player1.speed*delta;
			}
			if(player1.direction == player1.backward)
			{
				player1.x -= player1.speed*delta;
			}
		}
		
		if(player1.y > (400 - player1.height)) //prevents falling through ground
		{
			player1.y = (400 - 50);
			player1.isOnGround = true; //checks that player is on ground
			player1.fallCounter = 0; //resets fallCounter - gravity
		}
		
		if(player1.x < 0)
		{
			player1.x = 0;
		}
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//g.drawString("Hello World", 100, 100);
		lvl1.drawLevel(); //draws Background
		player1.drawPlayer();
	}
	
	public static void main (String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new TestPlatformer());
		app.setDisplayMode(800, 600, false);
		
		app.start();
	}
}