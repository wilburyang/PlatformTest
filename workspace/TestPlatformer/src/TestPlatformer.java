import org.newdawn.slick.*;

public class TestPlatformer extends BasicGame {

	World world = null;
	PlatformLevel lvl1 = null;
	Character player1 = null;
	
	public TestPlatformer()
	{
		super("Our Cool Platformer");
	}
	
	@Override
	public void init (GameContainer gc) throws SlickException
	{
		world = new World(); //creates new set of world parameters
		lvl1 =  new PlatformLevel(); //create new level object
		lvl1.loadBackground("data/testbackground.png", "data/testfloor.png");
		player1 = new Character();
		player1.loadCharacterImage("data/testplayer.png");
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_D)) //normal movement on ground
		{
			player1.x += player1.speed*delta;
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			player1.x -= player1.speed*delta;
		}
		
		if(input.isKeyDown(Input.KEY_W)) //jump command
		{
			player1.jump();
		}
		
		//will be array of all objects
		world.floor(player1);
		world.wall(player1);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//g.drawString("Hello World", 100, 100);
		lvl1.drawLevel(); //draws Background
		player1.drawCharacter();
	}
	
	public static void main (String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new TestPlatformer());
		app.setDisplayMode(800, 600, false);
		
		app.start();
	}
}