import org.newdawn.slick.*;

public class NPC extends Character {

	//can put npc place holder tiles in tmx with tiled editor,
	//then check tile ids and load npcs at those locations
	NPC(float xInit, float yInit)
	{
		life = 1;
		
		//temporary:
		x = xInit;
		y = yInit;
		xSpeed = 0.4f;
	}
	
	public void moveNPC(boolean[][] barriers)
	{
		x += xSpeed;
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
	
	public void triggerNPC()
	{
		
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
