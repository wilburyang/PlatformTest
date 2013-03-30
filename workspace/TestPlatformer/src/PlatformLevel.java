//import java.io.*;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class PlatformLevel {

	Image background = null;
	NPC testNPC = null; //to be all images needed (in arrays for animations)
	//Image floor = null; //temporary floor image
	
	//Image block = null;
	//Tile tileMap[] = null; //needs to be array
	
	
	TiledMap tilefloor;
	boolean[][] barrier; //array for barrier grid
	
	int x;
	int y;
	
	PlatformLevel()
	{
		x = 0;
		y = 0;
	}
	
	public void loadBackground(String file1, String file2, String file3) throws SlickException
	{
		background = new Image(file1); //loads background image
		
		//temp:
		//floor = new Image(file2); // temporary floor image load
		/*tileMap = new Tile[100];
		for(int i=0; i<tileMap.length; i++)
		{
			tileMap[i] = new Tile();
		}*/
	}
	
	public void loadTiles(String file) throws SlickException
	{
		//to be input from gamplay for level selection:
		tilefloor = new TiledMap(file); //lvl1 test
		
		//new stuff:
		final int SIZE = 30;   
		// build a collision map based on tile properties in the TileD map 
		barrier = new boolean[tilefloor.getWidth()][tilefloor.getHeight()];
		
		for (int xAxis=0;xAxis<tilefloor.getWidth(); xAxis++)
		{
			for (int yAxis=0;yAxis<tilefloor.getHeight(); yAxis++)
		    {
				int tileID = tilefloor.getTileId(xAxis, yAxis, 0);
				System.out.println(tileID);
		        //String value = tilefloor.getTileProperty(tileID, "barrier", "false");
		        if (tileID != 0) //"true".equals(value)
		        {
		        	barrier[xAxis][yAxis] = true;
		            System.out.println(xAxis + ", " + yAxis + "is a barrier");
		        }
		    }
		}
	}
	
	public void loadNPC() throws SlickException //will load all npcs based on tmx
	{
		testNPC = new NPC(600, 300);
		testNPC.loadCharacterImage("data/testnpc.png");
	}
	
	public void updateNPC() //will move all npcs
	{
		testNPC.moveNPC(barrier);
	}
	
	public void drawLevel(int xShift)
	{
		background.draw(0, 0);
		
		tilefloor.render(x-xShift, y, 0, 0, 800, 600);
		
		testNPC.drawCharacter(xShift); //to be array or list of all npcs needed
	}
}
