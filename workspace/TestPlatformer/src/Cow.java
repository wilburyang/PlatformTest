import org.newdawn.slick.SlickException;

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
		xSpeed = 1.0f;
		
		fDuration = 60;
		
		pRange = 100;
		
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
	
	public void updateWeapon()
	{
		//TODO this is a temporary update, final will update hitbox
		//based on some currentWeapon parameter
		//should consider update move to weapon class
		if(attackCounter > 0)
		{
			attackCounter++;
		}
		if(attackCounter >= fDuration)
		{
			attackCounter = 0;
		}
		
		if(direction == RIGHT)
		{
			//positions weapon to right of character
			npcWeapon.setPosition((int)x+width,
				(int)(y + height/2-npcWeapon.getHeight()/2), RIGHT);
		}
		else if(direction == LEFT)
		{
			//position weapon to left of character
			npcWeapon.setPosition((int)x-npcWeapon.getWidth(),
					(int)(y + height/2-npcWeapon.getHeight()/2), LEFT);
		}
		else //temp default to the right
		{
			weapons.get(currentWeapon).setPosition((int)x+width,
					(int)(y + height/2-weapons.get(currentWeapon).getHeight()/2), RIGHT);
		}
	}
	
	public void attack() //use weapon
	{
		updateWeapon();
		
		isAttacking = false; //reset for check
		
		if(!isFlipped)
		{
			npcWeapon.setPosition((int)x+width, (int)(y + height/2-npcWeapon.getHeight()/2), direction); //overriden by subclasses
		}
		else
		{
			npcWeapon.setPosition((int)x-npcWeapon.getWidth(), (int)(y + height/2-npcWeapon.getHeight()/2), direction); //overriden by subclasses
		}
		
		//weapon.setHitBox();
		if(!isAttacking && attackCounter < fDuration/2) //leave half of time for delay
		{
			isAttacking = true;
		}
		if(attackCounter == 0)
		{
			attackCounter = 1; //start count
		}
		//System.out.println("attacking!");
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
		
			if(isAttacking)
			{
				npcWeapon.setPosition((int)x+width,
						(int)(y + height/2-npcWeapon.getHeight()/2), RIGHT);
				
				npcWeapon.drawActive(xShift);
			}
			else
			{
				npcWeapon.drawInactive(xShift);
			}
			
			state = NORMAL; //reset to check again TODO consider moving out of render
		}
	}
}
