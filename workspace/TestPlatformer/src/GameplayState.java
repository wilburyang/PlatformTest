import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameplayState extends BasicGameState {

	World world = null;
	PlatformLevel lvl1 = null;
	Character player1 = null;
	
	int stateID = -1;
	 
    GameplayState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
    	world = new World(); //creates new set of world parameters
		lvl1 =  new PlatformLevel(); //create new level object
		lvl1.loadBackground("data/testbackground.png",
				"data/testfloor.png", "data/floor_tile.png");
		
		player1 = new Character();
		player1.loadCharacterImage("data/testplayer.png");
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
    	//gc.setMinimumLogicUpdateInterval(10);
    	Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_D)) //normal movement on ground
		{
			//add horizontal accel in air
			player1.x += player1.speed*delta;
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			player1.x -= player1.speed*delta;
		}
		
		if(input.isKeyDown(Input.KEY_W)) //jump command
		{
			if(world.isOnGround(player1))
			{
				player1.jump();
			}
		}
		else
		{
			player1.ySpeed -= player1.speed*3; //negate jump if w is released
		}
		
		if(input.isKeyDown(Input.KEY_O)) //option menu
		{
			sbg.enterState(TestPlatformer.LEVELEDITORSTATE);
		}
		
		//will be array of all objects
		world.gravity(player1, delta);
		//world.floor(player1); //makes sure player is on ground, not needed
		world.wall(player1);
		
		int n = 0;
		boolean collision = false;
		while(!collision && n < 21) //tile collision check loop
		{
			player1.checkCollision(lvl1.tileMap[n], gc);
			if(player1.isOnTile)
			{
				collision = true;
			}
			n++;
		}
		n = 0;
    }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	//g.drawString("Hello World", 100, 100);
    	lvl1.drawLevel(); //draws Background
    	player1.drawCharacter();
    }
	
	/*@Override
	public void init (GameContainer gc) throws SlickException
	{
		
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
	}*/
}