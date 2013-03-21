import org.newdawn.slick.*;

public class World {
	//make gravity method
	int floorpos;
	
	World()
	{
		floorpos = 400;
	}
	
	public void gravity(Character ch) //adjust movement with gravity
	{
		
	}
	
	public void floor(Character ch)
	{
		if(ch.y > (floorpos - ch.height)) //prevents falling through ground
		{
			ch.y = (floorpos - ch.height);
			//ch.isOnGround = true; //checks that character is on ground
			//ch.fallCounter = 0; //resets fallCounter - gravity
		}
	}
	
	public void wall(Character ch)
	{
		if(ch.x < 0)
		{
			ch.x = 0;
		}
	}
}
