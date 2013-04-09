//flamethrowing cow class
//describes unique trigger behavior for flamethrowing cow

//TODO Will contain unique image, behavior, and sound effects

public class Cow extends NPC {

	Cow(float xInit, float yInit)
	{
		super(xInit, yInit);
		life = 1; //default npc health
		
		//temporary:
		x = xInit;
		y = yInit;
		xSpeed = 0.4f;
		
		pRange = 50;
	}

	public void triggerNPC()
	{
		
	}
}
