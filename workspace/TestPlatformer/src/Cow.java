import org.newdawn.slick.SlickException;

//flamethrowing cow class
//describes unique trigger behavior for flamethrowing cow

//TODO Will contain unique image, behavior, and sound effects

public class Cow extends NPC {

	Cow(float xInit, float yInit) throws SlickException
	{
		super(xInit, yInit);
		life = 1; //default npc health
		
		//temporary:
		x = xInit;
		y = yInit;
		xSpeed = 0.4f;
		
		pRange = 50;
		
		soundFile = "data/testnpcsound.wav";
		
		loadWeapon("data/weapon/cow_fire", 3);
	}
	
	public void triggerNPC()
	{
		
	}
	
	public void attack() //use weapon
	{
		if(!isFlipped)
		{
			weapon.setPosition((int)x+width, (int)(y + height/2-weapon.getHeight()/2), false); //overriden by subclasses
		}
		else
		{
			weapon.setPosition((int)x-weapon.getWidth(), (int)(y + height/2-weapon.getHeight()/2), true); //overriden by subclasses
		}
		
		//weapon.setHitBox();
		
		isAttacking = true; //will loop attack indefinitely for now
		System.out.println("attacking!");
	}
}
