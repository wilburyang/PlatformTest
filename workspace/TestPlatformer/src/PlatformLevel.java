//import java.io.*;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class PlatformLevel {

	Image background = null;
	//Image floor = null; //temporary floor image
	
	//Image block = null;
	Tile tileMap[] = null; //needs to be array
	
	
	TiledMap tilefloor;
	boolean[][] barrier; //array for barrier grid
	
	PlatformLevel()
	{
	}
	
	public void loadBackground(String file1, String file2, String file3) throws SlickException
	{
		background = new Image(file1); //loads background image
		
		//temp:
		//floor = new Image(file2); // temporary floor image load
		tileMap = new Tile[100];
		for(int i=0; i<tileMap.length; i++)
		{
			tileMap[i] = new Tile();
		}
	}
	
	public void loadTiles(String file) throws SlickException
	{
		
		/*temp:
		tileMap[0].loadImage(file);
		int i;
		for(i=1; i<(800/(tileMap[0].width)); i++) //copy same tile instead of reloading
		{
			//tileMap[i].copyImage(tileMap[i]);
			tileMap[i].loadImage(file);
			tileMap[i].x = tileMap[i-1].x + tileMap[i-1].width;
		}
		//collision test tile
		tileMap[20].loadImage(file);
		tileMap[20].x = 200;
		tileMap[20].y = 300;
		*/
		
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
	
	public void drawLevel()
	{
		background.draw(0, 0);
		//floor.draw(0, 400);
		
		tilefloor.render(0,0,0,0,800,600);
		
		/*for(int i=0; i<21; i++)
		{
			tileMap[i].draw(); //temp draw one tile
		}*/
	}
}
