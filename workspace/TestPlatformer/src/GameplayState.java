import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.tiled.*;

public class GameplayState extends BasicGameState {

	World world = null;
	PlatformLevel lvl1 = null;
	Character player1 = null;
	
	//TiledMap map;
	
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
    	//map = new TiledMap("data/TestTileMap.tmx","data");
    	
    	world = new World(); //creates new set of world parameters
		lvl1 =  new PlatformLevel(); //create new level object
		lvl1.loadBackground("data/testbackground.png",
				"data/testfloor.png", "data/floor_tile.png");
		lvl1.loadTiles("data/testfloormap.tmx");
		
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
			if(player1.isCollision(lvl1, gc)) //check for each input
			{
				//change speed or position based on input dir
			}
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			player1.x -= player1.speed*delta;
			if(player1.isCollision(lvl1, gc))
			{
				
			}
		}
		
		if(input.isKeyDown(Input.KEY_W)) //jump command
		{
			if(world.isOnGround(player1))
			{
				player1.jump();
				if(player1.isCollision(lvl1, gc))
				{
					
				}
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
		//collision check after gravity is floor check since only y decreases
		if(player1.isCollision(lvl1, gc)) //checks neutral collision
		{
			player1.y -= 30;
			player1.isOnTile = true;
			
		}
		
		//world.floor(player1); //makes sure player is on ground, not needed
		world.wall(player1);
		
		//player1.isCollision(lvl1.tilefloor, lvl1.barrier, gc);
		
		//int n = 0;
		//boolean collision = false;
		//while(!collision && n < 21) //tile collision check loop
		//{
			//player1.checkCollision(lvl1.tilefloor, lvl1.barrier, gc);
			//if(player1.isOnTile)
			//{
			//	collision = true;
			//}
			//n++;
		//}
		//n = 0;
    }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	//g.drawString("Hello World", 100, 100);
    	
    	//just draw when needed?
    	lvl1.drawLevel(); //draws Background
    	//map.render(0, 0, 0, 0, 800, 600); //test render of tilemap
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