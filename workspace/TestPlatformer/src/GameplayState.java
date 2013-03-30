import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

public class GameplayState extends BasicGameState {

	//*****NOTE: change all "30" to variable = tile width
	
	World world = null;
	PlatformLevel lvl1 = null;
	Character player1 = null;
	Camera cam = null;
	int camLine;
	
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
    	cam = new Camera();
    	camLine = 400; //where camera begins to move
    	
		lvl1 =  new PlatformLevel(); //create new level object
		lvl1.loadBackground("data/testbackground.png",
				"data/testfloor.png", "data/floor_tile.png");
		lvl1.loadTiles("data/testfloormap.tmx");
		lvl1.loadNPC();
		
		player1 = new Character();
		player1.loadCharacterImage("data/testplayer.png");
		player1.x = 0;
		player1.y = 400 - player1.height;
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
    	Input input = gc.getInput();
    	
    	gc.setMaximumLogicUpdateInterval(17); //consistent logic rate
		
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
		}
		
		if(input.isKeyDown(Input.KEY_P)) //pause menu
		{
			sbg.enterState(TestPlatformer.PAUSESTATE);
		}
		
		lvl1.updateNPC();
		//will be array of all objects
		gravity(player1, gc);
		gravity(lvl1.testNPC, gc); //to check whole array
		
		//world.floor(player1); //makes sure player is on ground, not needed
		world.wall(player1); //keeps from moving off left of screen
		world.wall(lvl1.testNPC);
		
		cam.xCamShift(lvl1, player1, camLine);
    }
	
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	//g.drawString("Hello World", 100, 100);
    	
    	//just draw when needed?
    	lvl1.drawLevel(cam.xShift); //draws Background
    	//map.render(0, 0, 0, 0, 800, 600); //test render of tilemap
    	player1.drawCharacter(cam.xShift);
    }
    
    public void gravity(Character ch, GameContainer gc) //can re-add delta if needed
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
    		
    		//check for head collision a second time:
    		
    		if(player1.checkCollision(lvl1, gc, "up")) //push player down if head bumps
			{
				player1.gCount--;
			}
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