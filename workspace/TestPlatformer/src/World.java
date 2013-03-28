//import org.newdawn.slick.*;

public class World {
	//make gravity method
	//int floorpos;
	double gCount; //acceleration with time
	
	World()
	{
		//floorpos = 400;
		gCount = 0.0;
	}
	
	/*public void gravity(Character ch, int change) //consider moving to main gameplay class
	{
		
		if(!isOnGround(ch)) //if not on ground, then jumping
		{
			ch.ySpeed += ch.speed*3;
			gCount += 0.035; //increments gravity counter when in air
			
			ch.ySpeed -= gCount; //negative acceleration
			ch.y -= ch.ySpeed;
		}
		else
		{
			gCount = 0;
			ch.ySpeed = ch.speed;
		}
	}
	
	public boolean isOnGround(Character ch)
	{
		if(ch.isOnFloor(lvl1, gc)) //checks neutral collision
		{
			if(ch.ySpeed < 0)
			{
				ch.y += ch.speed;
			}
			return true;
			
		}
		else
		{
			return false;
		}
	}*/
	
	/*public void floor (Character ch) //floor barrier
	{
		if(isOnGround(ch))
		{
			if(ch.y >= (floorpos - ch.height))
			{
				ch.y = (floorpos - ch.height);
			}
			ch.ySpeed = ch.speed; //reset acceleration
		}
	}*/
	
	public void wall(Character ch) //wall barrier
	{
		if(ch.x < 0)
		{
			ch.x = 0;
		}
	}
}
