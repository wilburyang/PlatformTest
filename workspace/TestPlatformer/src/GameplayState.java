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
		
		if(input.isKeyDown(Input.KEY_D)) //normal movement on ground
		{
			//add horizontal accel in air
			player1.x += player1.xSpeed*delta;
			player1.checkCollision(lvl1, gc, "right"); //check for each input
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			player1.x -= player1.xSpeed*delta;
			player1.checkCollision(lvl1, gc, "left");
		}
		
		if(input.isKeyDown(Input.KEY_W)) //jump command
		{
			player1.jump(delta);
			player1.checkCollision(lvl1, gc, "up");
		}
		
		if(input.isKeyDown(Input.KEY_O)) //option menu
		{
			sbg.enterState(TestPlatformer.LEVELEDITORSTATE);
		}
		if(input.isKeyDown(Input.KEY_P)) //pause menu
		{
			sbg.enterState(TestPlatformer.PAUSESTATE);
		}
		
		//will be array of all objects
		gravity(player1, delta, gc);
		
		//world.floor(player1); //makes sure player is on ground, not needed
		world.wall(player1); //keeps from moving to left of screen
    }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	//g.drawString("Hello World", 100, 100);
    	
    	//just draw when needed?
    	lvl1.drawLevel(); //draws Background
    	//map.render(0, 0, 0, 0, 800, 600); //test render of tilemap
    	player1.drawCharacter();
    }
    
    public void gravity(Character ch, int change, GameContainer gc) //consider moving to main gameplay class
    {
    	ch.y -= ch.ySpeed; //move character based on previous speed adjustments
    	
    	if(!ch.isOnFloor(lvl1, gc)) //if not on ground, then jumping
    	{
    		System.out.println("gravity is applied");
    		//ch.ySpeed += ch.ySpeed*3;
    		ch.gCount -= 0.2; //increments gravity counter when in air
    		
    		/*if(ch.gCount < -10) //terminal velocity emulation
    		{
    			ch.gCount = -10;
    		}*/
    		
    		ch.y -= ch.gCount; //negative acceleration
    		//allows that character falls smoothly when w is not held:
    		ch.ySpeed -= 0.5; //decreases velocity up unless jump key is held
    	}
    	/*else //if on the ground
    	{	
    		
    		ch.gCount = 0; //reset gravity
    		ch.ySpeed = 0; //reset jump
    	}*/
    	
    	//***consider adjusting one time to top of block instead
		//checks if on or below the floor after gravity, and adjusts:
    	if(ch.isOnFloor(lvl1, gc))
    	{
    		System.out.println("is on floor");
    		ch.gCount = 0; //reset gravity counter
    		ch.ySpeed = 0; //reset speed counter
    		while(ch.isOnFloor(lvl1,  gc)) //if below floor, move up until not
    		{
    			ch.y--; //checks by pixel
    			//System.out.println("is below floor");
    		}
    		ch.y++;
    	}
    	
		System.out.println("y velocity = " + (ch.ySpeed+ch.gCount));
    }
}