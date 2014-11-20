import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.Random;

public class NPC extends Character {

	//can put npc place holder tiles in tmx with tiled editor,
	//then check tile ids and load npcs at those locations
	
	float pRange; //range of proximity checker 
	
	String soundFile = null;
	Audio soundEffect;
	Random random = new Random(); //create new random set for sound delay
	int r = random.nextInt(9)*500; //random number taken from random
	long startTime = System.currentTimeMillis(); //gets start time upon load
	
	boolean isFlipped = false; //if facing right
	
	Weapon npcWeapon;
	
	String type = null; //selects which methods to apply based on type of npc
	
	NPC(float xInit, float yInit)
	{
		life = 1; //default npc health
		
		ID = NPC;
		
		//temporary:
		x = xInit;
		y = yInit;
		xSpeed = 0.4f;
		
		pRange = 50;
	}
	
	public void setType(String id)
	{
		type = new String(id);
	}
	
	public void update(boolean[][] barriers, Character ch, int end)
	{
		playSoundEffect(ch.x); //temp sound test
		
		if(proximityCheck(ch).equals("left"))
		{
			if(xSpeed > 0) //if moving right turn left
			{
				xSpeed *= -1;
			}
		}
		else if(proximityCheck(ch).equals("right"))
		{
			if(xSpeed < 0) //if moving left turn right
			{
				xSpeed *= -1;
			}
		}
		
		x += xSpeed;
		
		if(xSpeed < 0)
		{
			animateLeft(); //if moving left, animate left
		}
		else if (xSpeed > 0)
		{
			animateRight();
		}
		
		if(x+width > end-30) //makes sure npc does not go off edge
		{
			x = end-width-30; //temp, will be wall at end
			xSpeed *= -1;
		}
		
		if(xSpeed >= 0 && checkCollision(barriers, "right").equals("right")) //if it bumps into right, go left
		{
			//System.out.println("right bump");
			xSpeed *= -1;
		}
		if(xSpeed < 0 && checkCollision(barriers, "left").equals("left")) //if it bumps into left, go right
		{
			xSpeed *= -1;
		}
		if(x < 0)
		{
			xSpeed *= -1;
		}
		
		/*update weapon position
		if(!isFlipped)
		{
			npcWeapon.setPosition((int)x+width, (int)y, false); //overriden by subclasses
		}
		else
		{
			npcWeapon.setPosition((int)x-npcWeapon.getWidth(), (int)y, true); //overriden by subclasses
		}*/
		
		//checks if attacked
		if(checkHurtBox(ch))
		{
			die();
		}
	}
	
	public boolean checkHurtBox(Character enemy) //kills player if hit
	{
		//System.out.println(hurtBoxX + " , " + box.getX() + " , " + hurtBoxY + " , " + box.getY());
		
		if (enemy.isAttacking) {
			hurtBoxX = (int) (x + width / 2 - hurtSize / 2);
			hurtBoxY = (int) (y + height / 2 - hurtSize / 2);
			float xCenter = x + width / 2;
			float yCenter = y + height / 2;
			if (yCenter >= enemy.weapons.get(enemy.currentWeapon).getY()
					&& yCenter <= (enemy.weapons.get(enemy.currentWeapon).getY()
							+ enemy.weapons.get(enemy.currentWeapon).getHeight())) {
				if (xCenter >= enemy.weapons.get(enemy.currentWeapon).getX()
						&& xCenter <= (enemy.weapons.get(enemy.currentWeapon).getX()
								+ enemy.weapons.get(enemy.currentWeapon).getWidth())) {
					return true;
				}
			}
			for (int j = hurtBoxY; j < hurtBoxY + hurtSize; j++) {
				for (int k = (int) enemy.weapons.get(enemy.currentWeapon).getY();
						k < enemy.weapons.get(enemy.currentWeapon).getY() + enemy.weapons.get(enemy.currentWeapon).getHeight(); k++) {
					if (j == k) {
						for (int l = (int) hurtBoxX; l < hurtBoxX + hurtSize; l++) {
							for (int m = (int) enemy.weapons.get(enemy.currentWeapon).getX();
									m < enemy.weapons.get(enemy.currentWeapon).getX()
									+ enemy.weapons.get(enemy.currentWeapon).getWidth(); m++) {
								if (l == m) {
									System.out.println("hit!");
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private String proximityCheck(Character ch) //checks if player is near npc and returns which side
	{
		if((ch.y+(ch.height/2)) > y-pRange && (ch.y+(ch.height/2)) < y+height) //checks y axis proximity
		{
			if((ch.x+(ch.width/2)) < x && (ch.x+(ch.width/2)) > x-pRange) //if player is close and to the left
			{
				//System.out.println("------close left------");
				//playSoundEffect(ch.x); //temp sound test
				
				return "left";
			}
			if((ch.x+(ch.width/2)) > x+width && (ch.x+(ch.width/2)) < x+width+pRange) //if player is close and to the right
			{
				//System.out.println("------close right------");
				//playSoundEffect(ch.x); //temp sound test
				
				
				attack(); //temporary test attack
				
				
				return "right";
			}
		}
		return "none";
	}
	
	public void triggerNPC()
	{
		/*AePlayWave moo = new AePlayWave("data/testnpcsound.wav");
		moo.run();
		Thread moo = new Thread(new PlaySound("data/testnpcsound.wav"));
		moo.start();*/
	}
	public void attack()
	{	
		isAttacking = true; //will loop attack indefinitely for now
		System.out.println("attacking!");
	}
	
	//creates weapon object, run in specific subclasses
	public void loadWeapon(String filePrefix, int totalFrames) throws SlickException
	{
		npcWeapon = new Weapon(totalFrames);
		
		npcWeapon.loadAnimation(filePrefix);
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
	
	public void loadSoundEffect()
	{
		try {
			soundEffect = AudioLoader.getAudio("WAV",
				ResourceLoader.getResourceAsStream(soundFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean delay(long startTime, int random){
		//System.out.println("Random Value: "+random);
		//System.out.println((System.currentTimeMillis()-startTime)%10006);
		long p = (System.currentTimeMillis()-startTime)%10006 - (long) random;
		if(p <= 50 && p >=0){
			startTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	public void playSoundEffect(float playerPos)
	{
		//TODO adjust to play on interval with volume proportional to distance from player
		double volumeScale = (1/(playerPos-x)); //large when closer to cow
		
		if(volumeScale < 0)
		{
			volumeScale *= -1;
		}
		if(volumeScale > 1)
		{
			volumeScale = 1;
		}
		
		if(!soundEffect.isPlaying())
		{
			if(delay(startTime, r)){
				r = (random.nextInt(10)+8)*500;
				soundEffect.playAsSoundEffect(1.0f, (float) volumeScale*0.75f, false);	
				startTime = System.currentTimeMillis() + 2000;
			}

		}
	}
	
	public void die() //when character loses life, reset position and state
	{
		lifeDown(); //if they are dead, the npc list will remove
	}
	
	//overrides regular character check method
	public String checkCollision(boolean[][] barriers, String direction)
	{
		try {
			if(direction.equals("right")) //check right collision
			{
				int xPos = (int)(x+width-1)/30; //divided by tile size
				int yPosA = (int)(y)/30; //top
				int yPosB = (int)(y+height/2)/30; //middle
				int yPosC = (int)(y+height-1)/30;
				
				//simplified checker
				if(barriers[xPos][yPosA]
						|| barriers[xPos][yPosB]
						|| barriers[xPos][yPosC]) //checks with boxes tile dimensions
				{
					x--;
					checkCollision(barriers, "right");
					return "right";
				}
				
				if(!barriers[xPos+1][yPosA+1]) //if there is no block on floor directly to right (edge)
				{
					return "right"; //turn left
				}
			}
			if(direction.equals("left")) //check left collision
			{
				int xPos = (int)(x+1)/30; //divided by tile size
				int yPosA = (int)(y+1)/30;
				int yPosB = (int)(y+height/2)/30;
				int yPosC = (int)(y+height-1)/30;
				
				//System.out.println(xPos + " , " + yPos);
				
				//simplified checker
				if(barriers[xPos][yPosA]
						|| barriers[xPos][yPosB]
						|| barriers[xPos][yPosC]) //checks with boxes tile dimensions
				{
					x++;
					checkCollision(barriers, "left");
					return "left";
				}
				
				if(!barriers[xPos][yPosA+1]) //if there is no block on floor directly to left (edge)
				{
					return "left"; //turn right
				}
			}
			if(direction.equals("up")) //check top collision
			{
				int xPosA = (int)(x+1)/30; //divided by tile size
				int xPosB = (int)(x+width/2)/30; //checks middle of ch, left, and right
				int xPosC = (int)(x+width-1)/30;
				int yPos = (int)y/30;
				
				//System.out.println(xPos + " , " + yPos);
				
				//simplified checker for top collision
				if(barriers[xPosA][yPos]
						|| barriers[xPosB][yPos]
						|| barriers[xPosC][yPos]) //checks with boxes tile dimensions
				{
					y++;
					checkCollision(barriers, "up");
					return "up";
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return "out of bounds error";
		}
		return "none"; //default direction
	}
}
