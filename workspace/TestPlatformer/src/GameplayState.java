import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
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
    	Input input = gc.getInput();
    	//gc.setMinimumLogicUpdateInterval(10);
		
		if(input.isKeyDown(Input.KEY_D)) //normal movement on ground
		{
			//add horizontal accel in air
			player1.x += player1.xSpeed*delta;
			if(player1.isCollision(lvl1, gc)) //check for each input
			{
				//change speed or position based on input dir
			}
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			player1.x -= player1.xSpeed*delta;
			if(player1.isCollision(lvl1, gc))
			{
				
			}
		}
		
		if(input.isKeyDown(Input.KEY_W)) //jump command
		{
			if(player1.isOnFloor(lvl1, gc))
			{
				player1.jump();
				if(player1.isCollision(lvl1, gc))
				{
					
				}
			}
		}
		/*else
		{
			player1.ySpeed -= player1.speed*3; //negate jump if w is released
		}*/
		
		if(input.isKeyDown(Input.KEY_O)) //option menu
		{
			sbg.enterState(TestPlatformer.LEVELEDITORSTATE);
		}
		
		//will be array of all objects
		gravity(player1, delta, gc);
		//collision check after gravity is floor check since only y decreases
		/*if(player1.isOnFloor(lvl1, gc)) //checks neutral collision
		{
			if(player1.ySpeed < 0)
			{
				player1.y += player1.ySpeed;
			}
			player1.isOnTile = true;
			
		}
		else
		{
			player1.isOnTile = false;
		}*/
		
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
    
    public void gravity(Character ch, int change, GameContainer gc) //consider moving to main gameplay class
    {
    	
    	if(!ch.isOnFloor(lvl1, gc)) //if not on ground, then jumping
    	{
    		//ch.ySpeed += ch.ySpeed*3;
    		ch.gCount += 0.035; //increments gravity counter when in air
    		
    		ch.ySpeed -= ch.gCount; //negative acceleration
    		//ch.y -= ch.ySpeed;
    	}
    	else //if on the ground
    	{
    		ch.gCount = 0;
    		ch.ySpeed = 0;
    	}
    }

    /*public boolean isOnGround(Character ch, GameContainer gc)
    {
    	if(ch.isOnFloor(lvl1, gc)) //checks neutral collision
    	{
    		return true;
    		
    	}
    	else
    	{
    		return false;
    	}
    }*/
}