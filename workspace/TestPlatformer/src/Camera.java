
public class Camera {

	//int cameraX;
	//int cameraY;
	
	int xShift;
	
	Camera()
	{
		//cameraX = 0;
		//cameraY = 0;
		xShift = 0;
	}
	
	//TODO change to variable values!
	
	public void xCamShift(PlatformLevel map, Character player, int xLine)
	{
		if(player.x <= xLine)
		{
			xShift = 0;
		}
		
		if(player.x > xLine)
		{
			if(player.x-xShift > xLine || player.x-xShift < 800-xLine)
			{
				xShift = (int)player.x - xLine;
			}
		}
		
		if(player.x+xLine >= map.floorLength) //if at end of map
		{
			xShift = map.floorLength - 800;
		}
	}
}
