import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TestPlatformer extends StateBasedGame {

	public static final int MAINMENUSTATE		= 0;
	public static final int GAMEPLAYSTATE		= 1;
	public static final int LEVELEDITORSTATE	= 2; //to be options menu
	public static final int PAUSESTATE			= 3;
	
	public static int FPS = 60;
	
	public TestPlatformer()
	{
		super("The Platformer");
	}
	
	public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new TestPlatformer());
 
         app.setDisplayMode(800, 600, false);
         app.setTargetFrameRate(FPS);
         app.start();
    }
	
	@Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new LevelEditor(LEVELEDITORSTATE));
        this.addState(new GameplayState(PAUSESTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
    }
}