import java.util.ArrayList;

import org.newdawn.slick.*;

public class Character {

	//Image image[] = null;
	int totalFrames; //may not be needed
	protected int currentAnimation;
	private int fDuration; //amount of time in frames remain
	
	Animation neutralAnimation, leftAnimation, rightAnimation, jumpAnimation, fallAnimation, attackAnimation;
	
	int attackCounter = 0; //counts attack duration in frames
	boolean isAttacking = false;
	
	protected final int NORMAL =		0;
	protected final int LEFT =		1;
	protected final int RIGHT =		2;
	protected final int JUMP =		3;
	protected final int FALL =		4;
	//protected final int ATTACK =		5;
	
	int state = NORMAL; //default normal, how the character is moving
	int direction = RIGHT; //where the character is facing
	
	protected int life;
	int height;
	int width;
	float x;
	float y;
	
	int hurtBoxX;
	int hurtBoxY;
	int hurtSize;
	
	ArrayList<Weapon> weapons; //private for player only, not npc
	int currentWeapon;
	
	int PLAYER =	0; //id number for characters
	int NPC =		1;
	
	int ID; //id player or npc
	
	/*int direction; //testing for directional jump
	int forward = 1;
	int backward = -1; //workaround for direction names
	int neutral = 0;
	
	boolean isOnGround;*/
	
	float xSpeed;
	float ySpeed;
	float scale;
	double gCount;
	
	boolean fallen; //checks if fallen off map
	
	public Character()
	{
		life = 3;
		
		ID = PLAYER; //default, versus NPC
		
		//isOnGround = true;
		
		scale = 1.0f;
		xSpeed = 0.2f;
		ySpeed = 0.4f; //adjusts for jumps
		
		x = 0; //safe guard, changed immediately
		y = 0;
		
		hurtBoxX = 0;
		hurtBoxY = 0;
		hurtSize = 0;
		
		totalFrames = 5; //temp
		currentWeapon = 0; //default character weapon first
		
		currentAnimation = 0;
		fDuration = 200; //ms?
		//isOnTile = true;
		
		fallen = false;
		
		//consider moving out of constructor
		try {
			loadWeapon("data/weapon/cow_fire", 5);
		} catch (SlickException e) {
			return;
		}
	}
	
	public void loadAnimation(String fileName, String extension, int total) throws SlickException
	{	
		neutralAnimation = new Animation();
		leftAnimation = new Animation();
		rightAnimation = new Animation();
		jumpAnimation = new Animation(); //number of frames temporary
		attackAnimation = new Animation(); //number of frames temporary
		
		//temp loads all the same, temp start at image0.png
		neutralAnimation = new Animation(
				loadImage(fileName, extension, 1, 0), fDuration); //will be different sub sections of image array
		leftAnimation = new Animation(
				loadImage(fileName, extension, total, 0), fDuration);
		rightAnimation = new Animation(
				loadImage(fileName, extension, total, 0), fDuration);
		jumpAnimation = new Animation(
				loadImage(fileName, extension, total, 0), fDuration);
		fallAnimation = new Animation(
				loadImage(fileName, extension, total, 0), fDuration);
		attackAnimation = new Animation(
				loadImage(fileName, extension, total, 0), fDuration);
		
		//set dimensions of character image
		width = (int) (neutralAnimation.getCurrentFrame().getWidth()*scale);
		height = (int) (neutralAnimation.getCurrentFrame().getHeight()*scale); //must occur at start
		hurtSize = (int) width; //set hurtbox dimensions (square)
	}
	
	private Image[] loadImage(String fileName, String extension, int total, int start) throws SlickException
	{
		totalFrames = total;
		Image image[] = new Image[totalFrames];
		
		for(int frame = start; frame < totalFrames; frame++) //load each frame to image array
		{
			try
			{
				image[frame] = new Image(fileName + frame + extension);
			} catch (SlickException e) {
				System.err.println("Image file not found: " + fileName+frame+extension);
				return null;
			}
		}
		
		return image;
	}
	//creates weapon object, run in specific subclasses
	public void loadWeapon(String filePrefix, int totalFrames) throws SlickException
	{
		weapons = new ArrayList<Weapon>();
		
		weapons.add(new Weapon(totalFrames));
		
		weapons.get(weapons.size()-1).loadAnimation(filePrefix); //load animation for most recent object
	}
	
	public void updateWeapon()
	{
		//TODO this is a temporary update, final will update hitbox
		//based on some currentWeapon parameter
		//should consider update move to weapon class
		
		if(direction == RIGHT)
		{
			//positions weapon to right of character
			weapons.get(currentWeapon).setPosition((int)x+width,
				(int)(y + height/2-weapons.get(currentWeapon).getHeight()/2), RIGHT);
		}
		else if(direction == LEFT)
		{
			//position weapon to left of character
			weapons.get(currentWeapon).setPosition((int)x-weapons.get(currentWeapon).getWidth(),
					(int)(y + height/2-weapons.get(currentWeapon).getHeight()/2), LEFT);
		}
		else //temp default to the right
		{
			weapons.get(currentWeapon).setPosition((int)x+width,
					(int)(y + height/2-weapons.get(currentWeapon).getHeight()/2), RIGHT);
		}
		
		//update weapon position
		/*if(!isFlipped)
		{*/
			//weapons.get(currentWeapon).setPosition((int)x+width, (int)y, false); //overriden by subclasses
		/*}
		else
		{
			npcWeapon.setPosition((int)x-npcWeapon.getWidth(), (int)y, true); //overriden by subclasses
		}*/
	}
	
	public void attack()
	{	
		isAttacking = true; //will loop attack indefinitely for now
		
		updateWeapon(); //updates weapon position
		
		if(attackCounter > 0)
		{
			attackCounter++;
		}
		System.out.println("attacking!");
	}
	
	public void draw(int xShift)
	{
		if (isAlive()) 
		{
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
				weapons.get(currentWeapon).drawActive(xShift);
			}
			else
			{
				weapons.get(currentWeapon).drawInactive(xShift);
			}
			
			state = NORMAL; //reset to check again TODO consider moving out of render
		}
	}
	
	public void animateLeft() //changes current frame based on duration
	{
		state = LEFT; //walking left
		direction = LEFT; //facing left
	}
	
	public void animateRight() //changes current frame based on duration
	{
		state = RIGHT; //walking right
		direction = RIGHT; //facing right
	}
	
	//jump method, make smooth
	public void jump(int change)
	{
		System.out.println("jumping");
		if(ySpeed >= -1) //if falling, cannot jump
		{
			ySpeed = 7; //initial velocity
		}
	}
	
	/*public void reverse(String direction) //push back
	{
		if(direction.equals("left"))
		{
			y += xSpeed;
		}
		else if(direction.equals("right"))
		{
			y -= xSpeed;
		}
	}*/
	
	public boolean checkHurtBox(NPC enemy) //kills player if hit by npc
	{
		//System.out.println(hurtBoxX + " , " + box.getX() + " , " + hurtBoxY + " , " + box.getY());
		
		if (enemy.isAttacking) {
			hurtBoxX = (int) (x + width / 2 - hurtSize / 2);
			hurtBoxY = (int) (y + height / 2 - hurtSize / 2);
			float xCenter = x + width / 2;
			float yCenter = y + height / 2;
			if (yCenter >= enemy.npcWeapon.getY()
					&& yCenter <= (enemy.npcWeapon.getY() + enemy.npcWeapon.getHeight())) {
				if (xCenter >= enemy.npcWeapon.getX()
						&& xCenter <= (enemy.npcWeapon.getX() + enemy.npcWeapon.getWidth())) {
					return true;
				}
			}
			for (int j = hurtBoxY; j < hurtBoxY + hurtSize; j++) {
				for (int k = (int) enemy.npcWeapon.getY(); k < enemy.npcWeapon.getY() + enemy.npcWeapon.getHeight(); k++) {
					if (j == k) {
						for (int l = (int) hurtBoxX; l < hurtBoxX + hurtSize; l++) {
							for (int m = (int) enemy.npcWeapon.getX(); m < enemy.npcWeapon.getX()
									+ enemy.npcWeapon.getWidth(); m++) {
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
	
	//TODO switch to using "TOP_LEFT", etc...
	public boolean checkCollision(PlatformLevel level, GameContainer gc, String direction)
	{
		try {
			if(direction.equals("right")) //check right collision
			{
				int xPos = (int)(x+width-1)/30; //divided by tile size
				int yPosA = (int)(y)/30; //top
				int yPosB = (int)(y+height/2)/30; //middle
				int yPosC = (int)(y+height-1)/30; //bottom, but not floor
				
				//System.out.println(xPos + " , " + yPos);
				
				//simplified checker
				if(level.barrier[xPos][yPosA]
						|| level.barrier[xPos][yPosB]
						|| level.barrier[xPos][yPosC]) //checks with boxes tile dimensions
				{
					x--;
					checkCollision(level, gc, "right");
				}
			}
			else if(direction.equals("left")) //check left collision
			{
				int xPos = (int)(x+1)/30; //divided by tile size
				int yPosA = (int)(y+1)/30;
				int yPosB = (int)(y+height/2)/30;
				int yPosC = (int)(y+height-1)/30;
				
				//System.out.println(xPos + " , " + yPos);
				
				//simplified checker
				if(level.barrier[xPos][yPosA]
						|| level.barrier[xPos][yPosB]
						|| level.barrier[xPos][yPosC]) //checks with boxes tile dimensions
				{
					x++;
					checkCollision(level, gc, "left");
				}
			}
			else if(direction.equals("up")) //check top collision
			{
				int xPosA = (int)(x+1)/30; //divided by tile size
				int xPosB = (int)(x+width/2)/30; //checks middle of ch, left, and right
				int xPosC = (int)(x+width-1)/30;
				int yPos = (int)y/30;
				
				//System.out.println(xPos + " , " + yPos);
				
				//simplified checker for top collision
				if(level.barrier[xPosA][yPos]
						|| level.barrier[xPosB][yPos]
						|| level.barrier[xPosC][yPos]) //checks with boxes tile dimensions
				{
					y++;
					checkCollision(level, gc, "up");
					return true;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return false; //only returns true if collision from above
	}

	
	public boolean isOnFloor(PlatformLevel level, GameContainer gc)
	{
		try {
			//Input input = gc.getInput();
			
			//temp floor , will initiate death method:
			if(y+height>=600) //falls to for
			{
				die();
				return true; //false floor to prevent collision check
			}
			
			//check if any of character is touching barrier
			int xPosA = (int)(x+1)/30; //divided by tile size
			int xPosB = (int)(x+width/2)/30; //check middle
			int xPosC = (int)(x+width-1)/30; //check right
			int yPos = (int)(y+height)/30;
			
			//System.out.println(xPos + " , " + yPos);
			
			//simplified checker
			if(level.barrier[xPosA][yPos]
					|| level.barrier[xPosB][yPos]
					|| level.barrier[xPosC][yPos]) //checks with boxes tile dimensions
			{
				//System.out.println("is on floor");
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		//if not on floor
		if(ySpeed > gCount)
		{
			state = JUMP;
		}
		if(ySpeed < gCount)
		{
			state = FALL;
		}
		return false;
	}
	
	public boolean isAlive() //return true if player has 1 or more lives
	{
		if(life > 0)
		{
			return true;
		}
		return false;
	}
	public void die() //when character loses life, reset position and state
	{
		lifeDown();
		
		if(life > 0)
		{
			x = 0;
			y = 400 - height;
		}
		else //full death, will initiate death animation
		{
			x = 0;
			y = 400 - height;
			lifeUp(3); //reset to full life (temp)
		}
	}
	public int getLife()
	{
		return life;
	}
	
	public void lifeUp(int amount)
	{
		life += amount;
	}
	public void lifeUp() //increment life by one
	{
		life++;
		System.out.println("life = " + life);
	}
	
	public void lifeDown(int amount)
	{
		life -= amount;
	}
	public void lifeDown()
	{
		life--;
		System.out.println("life = " + life);
	}
}
