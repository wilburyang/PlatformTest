import java.io.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class LevelEditor extends BasicGameState {
	
	EditorObject bg = null;
	EditorObject button = null;
	float scale = 1.0f;
	
	int stateID = -1;
	 
    LevelEditor( int stateID ) //level editor state id
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
		bg = new EditorObject("data/leveleditor_bg.png", 0, 0, 1.0);
		button = new EditorObject("data/testbutton.png", 50, 400, 0.5);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_A))
		{
			
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
		bg.drawObject();
		button.drawObject();
	}
}

