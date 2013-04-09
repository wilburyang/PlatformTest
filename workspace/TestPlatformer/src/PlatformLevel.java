//import java.io.*;
import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class PlatformLevel {

	int floorLayerIndex = 0;
	int npcLayerIndex = 1;
	
	Image background = null;
	NPC testNPC = null; //to be all images needed (in arrays for animations)
	ArrayList<NPC> allNPC;
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
	
	public void loadBackground(String file) throws SlickException
	{
		background = new Image(file); //loads background image
		
		//temp:
		//floor = new Image(file2); // temporary
		
		
		//floor image load
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
				int tileID = tilefloor.getTileId(xAxis, yAxis, floorLayerIndex);
				System.out.println(tileID);
		        //String value = tilefloor.getTileProperty(tileID, "barrier", "false");
		        if (tileID > 0) //brute force check for layer
		        {
		        	barrier[xAxis][yAxis] = true;
		            System.out.println(xAxis + ", " + yAxis + "is a barrier");
		        }
		    }
		}
	}
	
	public void loadNPC() throws SlickException //will load all npcs based on tmx
	{
		//temporary:
		testNPC = new NPC(600, 300);
		testNPC.loadImage("data/testnpc", ".png", 3);
		
		//counts number of npcs for level's npc array size
		int amount = 0;
		for (int xAxis=0;xAxis<tilefloor.getWidth(); xAxis++)
		{
			for (int yAxis=0;yAxis<tilefloor.getHeight(); yAxis++)
	    	{
				int tileID = tilefloor.getTileId(xAxis, yAxis, npcLayerIndex); //layer index for second layer == 1
				
				if(tileID > 0)
				{
					System.out.println("-------------" + tileID);
					amount++;
				}
	    	}
		}
		//System.out.println("-------------" + amount);
		
		//*****NOTE: change to list format!!!
		//creates npc array based on tmx npc layer
		int npcIndex = 0;
		allNPC = new ArrayList<NPC>();
		
		for (int xAxis=0;xAxis<tilefloor.getWidth(); xAxis++)
		{
			for (int yAxis=0;yAxis<tilefloor.getHeight(); yAxis++)
	    	{
				int tileID = tilefloor.getTileId(xAxis, yAxis, npcLayerIndex);
				
				if(tileID > 0) //temp force draw cow
				{
					allNPC.add(new NPC(xAxis*30, yAxis*30));
					
					//temporary load, to be copying
					allNPC.get(npcIndex).loadImage("data/testnpc", ".png", 3);
					
					System.out.println(xAxis*30 + " , " + yAxis*30);
					
					if(tileID == 860) //temporary brute force method
					{
						allNPC.get(npcIndex).setID("testcow");
					}
					npcIndex++;
				}
	    	}
		}
	}
	
	public void updateNPC(Character ch) //will move all npcs
	{
		//testNPC.moveNPC(barrier, ch);
		
		for(int i = 0; i < allNPC.size(); i++) //moves all npcs in array individually
		{
			allNPC.get(i).moveNPC(barrier,  ch, tilefloor.getWidth()*30);
		}
		
		//death test:
		/*if(ch.x+(ch.height/2) > testNPC.x && ch.x+(ch.height/2) < testNPC.x+testNPC.width
				&& ch.y+ch.height < testNPC.y+testNPC.height+5 && ch.y+ch.height > testNPC.y+testNPC.height-5)
		{
			System.out.println("---------------life lost!");
			ch.lifeDown();
		}*/
	}
	
	public void drawLevel(int xShift)
	{
		background.draw(0, 0);
		
		tilefloor.render(x-xShift, y, floorLayerIndex); //only render static layer (layer 0)
		
		//testNPC.drawCharacter(xShift);
		
		//***NOTE: Optimize to only render npcs onscreen
		for(int i = 0; i < allNPC.size(); i++) //draws all npcs in array
		{
			allNPC.get(i).draw(xShift);
		}
	}
	public void playTrack(String name){
		String filename = "data/" + name + ".wav";
		Thread track = new Thread(new PlayTrack(filename));
		track.start();
	}
}
