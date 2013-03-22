import java.io.*;
import org.newdawn.slick.*;

public class PlatformLevel {

	Image background = null;
	Image floor = null; //temporary floor image
	
	//Image block = null;
	Tile tileMap[] = null; //needs to be array
	
	PlatformLevel()
	{
	}
	
	public void loadBackground(String file1, String file2, String file3) throws SlickException
	{
		background = new Image(file1); //loads background image
		floor = new Image(file2); // temporary floor image load
		
		tileMap = new Tile[100];
		for(int i=0; i<tileMap.length; i++)
		{
			tileMap[i] = new Tile();
		}
		
		loadTiles(file3);
	}
	
	public void loadTiles(String file) throws SlickException
	{
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
	}
	
	public void drawLevel()
	{
		background.draw(0, 0);
		floor.draw(0, 400);
		
		for(int i=0; i<21; i++)
		{
			tileMap[i].draw(); //temp draw one tile
		}
	}
}
