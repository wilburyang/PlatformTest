import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MainMenuState extends BasicGameState {
 
    int stateID = -1;
 
    MainMenuState( int stateID ) 
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
		
		if(input.isKeyDown(Input.KEY_ENTER)) //normal movement on ground
		{
			sbg.enterState(TestPlatformer.GAMEPLAYSTATE);
		}
		if(input.isKeyDown(Input.KEY_O)) //option menu
		{
			sbg.enterState(TestPlatformer.LEVELEDITORSTATE);
		}
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
    	String text1 = "Press Enter to start";
    	String text2 = "or press O for level editor";
    	g.drawString(text1, 280, 300);
    	g.drawString(text2, 280, 320);

    }
 
}