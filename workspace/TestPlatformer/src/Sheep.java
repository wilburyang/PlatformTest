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
			npcWeapon.setPosition((int)x+width, (int)(y + height/2-npcWeapon.getHeight()/2), direction); //overriden by subclasses
		}
		else
		{
			npcWeapon.setPosition((int)x-npcWeapon.getWidth(), (int)(y + height/2-npcWeapon.getHeight()/2), direction); //overriden by subclasses
		}
		
		//weapon.setHitBox();
		
		isAttacking = true; //will loop attack indefinitely for now
		//System.out.println("attacking!");
		weapons.get(currentWeapon).setPosition((int)x+width,
				(int)(y + height/2-weapons.get(currentWeapon).getHeight()/2), direction);
	}
	
	public void draw(int xShift)
	{
		if (isAlive()) {
			switch (state)
			{
			case LEFT: leftAnimation.draw(x - xShift, y);
			break;
			case RIGHT: rightAnimation.draw(x - xShift, y);
			break;
			case JUMP: jumpAnimation.draw(x - xShift, y);
			break;
			case FALL: fallAnimation.draw(x - xShift, y);
			break;
			default: neutralAnimation.draw(x - xShift, y);
			}
			
			state = NORMAL; //reset to check again TODO consider moving out of render
		
			if(isAttacking)
			{
				npcWeapon.setPosition((int)x+width,
						(int)(y + height/2-npcWeapon.getHeight()/2), direction);
				
				npcWeapon.drawActive(xShift);
			}
			else
			{
				npcWeapon.drawInactive(xShift);
			}
		}
	}
}
