import java.io.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class PauseState extends BasicGameState {
	
	String pauseText;
	float scale = 1.0f;
	
	int stateID = -1;
	 
    PauseState( int stateID ) //level editor state id
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		pauseText = "paused (press ESC to play)";
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();
		
		System.out.println("paused");
		
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
				sbg.enterState(TestPlatformer.GAMEPLAYSTATE);
		}
		
		if(input.isKeyDown(Input.KEY_D))
		{
			
		}
		
		if(input.isKeyDown(Input.KEY_W))
		{
			
		}
		
		if(input.isKeyDown(Input.KEY_A))
		{
			
		}
		
		if(input.isKeyDown(Input.KEY_2))
		{
			
		}
		if(input.isKeyDown(Input.KEY_1))
		{
			
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawString(pauseText, 300, 290);
	}
}

