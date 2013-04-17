import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public class Sheep extends NPC {
	
	Sheep(float xInit, float yInit)
	{
		super(xInit, yInit);
		life = 1; //default npc health
		
		//temporary:
		x = xInit;
		y = yInit;
		xSpeed = 1.0f;
		
		pRange = 100;
		
		totalFrames = 1;
		
		soundFile = "data/testnpcsound.wav";
		
		try {
			loadWeapon("data/weapon/cow_fire", 5);
		} catch (SlickException e) {
			return;
		}
	}
	
	public void triggerNPC()
	{
		
	}
	
	public void attack() //use weapon
	{
		if(!isFlipped)
		{
			npcWeapon.setPosition((int)x+width, (int)(y + height/2-npcWeapon.getHeight()/2), false); //overriden by subclasses
		}
		else
		{
			npcWeapon.setPosition((int)x-npcWeapon.getWidth(), (int)(y + height/2-npcWeapon.getHeight()/2), true); //overriden by subclasses
		}
		
		//weapon.setHitBox();
		
		isAttacking = true; //will loop attack indefinitely for now
		System.out.println("attacking!");
	}
	
	public void draw(int xShift)
	{
		if(currentAnimation != NORMAL) //if still, draw still instead of zeroeth animation
		{
			animation[currentAnimation].draw(x-xShift, y);
		}
		else
		{
			image[0].draw(x-xShift, y, scale);
		}
		currentAnimation = NORMAL; //reset to check again
		
		if(isAttacking)
		{
			npcWeapon.setPosition((int)x+width,
				(int)(y + height/2-npcWeapon.getHeight()/2), false);
			
			npcWeapon.drawActive(xShift);
		}
		else
		{
			npcWeapon.drawInactive(xShift);
		}
	}
}
