import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.io.*;

public class OptionsMenuState extends BasicGameState {
 
    int stateID = -1;
 
    OptionsMenuState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
 
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
    	Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_ESCAPE)) //normal movement on ground
		{
			sbg.enterState(TestPlatformer.MAINMENUSTATE);
		}
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	String text1 = "Press ESC to return to main menu";
    	g.drawString(text1, 280, 300);

    }
 
}