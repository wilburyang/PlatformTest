import java.io.IOException;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class NPC extends Character {

	//can put npc place holder tiles in tmx with tiled editor,
	//then check tile ids and load npcs at those locations
	
	float pRange; //range of proximity checker 
	
	String soundFile = null;
	Audio soundEffect;
	
	String ID; //selects which methods to apply based on ID of npc
	
	NPC(float xInit, float yInit)
	{
		life = 1; //default npc health
		
		//temporary:
		x = xInit;
		y = yInit;
		xSpeed = 0.4f;
		
		pRange = 50;
	}
	
	public void setID(String id)
	{
		ID = id;
	}
	
	public void moveNPC(boolean[][] barriers, Character ch, int end)
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
		
		if(xSpeed >= 0 && checkCollision(barriers, "right") == "right") //if it bumps into right, go left
		{
			System.out.println("right bump");
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
	}
	
	private String proximityCheck(Character ch) //checks if player is near npc and returns which side
	{
		if((ch.y+(ch.height/2)) > y-pRange && (ch.y+(ch.height/2)) < y+height) //checks y axis proximity
		{
			if((ch.x+(ch.width/2)) < x && (ch.x+(ch.width/2)) > x-pRange) //if player is close and to the left
			{
				System.out.println("------close left------");
				//playSoundEffect(ch.x); //temp sound test
				return "left";
			}
			if((ch.x+(ch.width/2)) > x+width && (ch.x+(ch.width/2)) < x+width+pRange) //if player is close and to the right
			{
				System.out.println("------close right------");
				//playSoundEffect(ch.x); //temp sound test
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
	public void loadSoundEffect()
	{
		try {
			soundEffect = AudioLoader.getAudio("WAV",
				ResourceLoader.getResourceAsStream(soundFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void playSoundEffect(float playerPos)
	{
		//TODO adjust to play on interval with volume proportional to distance from player
		double volumeScale = (1/(playerPos-x)); //large when closer to cow
		
		if(volumeScale < 0)
		{
			volumeScale *= -1;
		}
		if(volumeScale > 1.0)
		{
			volumeScale = 1.0;
		}
		
		if(!soundEffect.isPlaying())
		{
			//play in position and at volume relative to player position:
			soundEffect.playAsSoundEffect(1.0f, (float) volumeScale*10.0f, false);
		}
	}
	
	//overrides regular character check method
	public String checkCollision(boolean[][] barriers, String direction)
	{
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
		return "none"; //default direction
	}
}
