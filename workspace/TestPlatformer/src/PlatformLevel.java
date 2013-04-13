import java.io.*;
import java.util.*;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

//TODO http://lwjgl.org/wiki/index.php?title=Slick-Util_Library_-_Part_2_-_Loading_Sounds_for_LWJGL

public class PlatformLevel {

	int floorLayerIndex = 0;
	int npcLayerIndex = 1;
	
	Image background = null;
	NPC testNPC = null; //to be all images needed (in arrays for animations)
	ArrayList<Cow> allNPC; //temporarily cow class specifically
	
	TiledMap tileFloor;
	boolean[][] barrier; //array for barrier grid
	int floorLength = 0;
	
	private Audio levelMusic; //wav file for level bg music stream
	float musicVolume;
	
	int x;
	int y;
	
	int level;
	
	PlatformLevel(int levelNum) //level number to be used for what is selected
	{
		x = 0;
		y = 0;
		level = levelNum;
		
		musicVolume = 0.1f; //set default music volume
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
	//eventually will not take a file string
	public void loadSound(String file) throws SlickException //loads level effects and level music
	{
		try {
			levelMusic = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTiles(String file) throws SlickException
	{
		//to be input from gamplay for level selection:
		tileFloor = new TiledMap(file); //lvl1 test
		
		final int SIZE = 30; //TODO get tile size
		
		floorLength = tileFloor.getWidth()*30; //sets floor length in tiles
		
		// build a collision map based on tile properties in the TileD map 
		barrier = new boolean[tileFloor.getWidth()][tileFloor.getHeight()];
		
		for (int xAxis=0;xAxis<tileFloor.getWidth(); xAxis++)
		{
			for (int yAxis=0;yAxis<tileFloor.getHeight(); yAxis++)
		    {
				int tileID = tileFloor.getTileId(xAxis, yAxis, floorLayerIndex);
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
		testNPC.loadAnimation("data/testnpc", ".png", 3);
		
		/*counts number of npcs for level's npc array size
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
		}*/
		
		//creates npc array based on tmx npc layer
		int npcIndex = 0;
		allNPC = new ArrayList<Cow>(); //temporarily cow class specifically
		
		for (int xAxis=0;xAxis<tileFloor.getWidth(); xAxis++)
		{
			for (int yAxis=0;yAxis<tileFloor.getHeight(); yAxis++)
	    	{
				int tileID = tileFloor.getTileId(xAxis, yAxis, npcLayerIndex);
				
				if(tileID > 0) //temp force draw cow
				{
					allNPC.add(new Cow(xAxis*30, yAxis*30)); //temporarily cow class specifically
					
					//temporary load, to be copying
					allNPC.get(npcIndex).loadAnimation("data/testnpc", ".png", 3);
					
					System.out.println(xAxis*30 + " , " + yAxis*30);
					
					if(tileID == 860) //temporary brute force method
					{
						allNPC.get(npcIndex).setType("cow");
					}
					npcIndex++;
				}
	    	}
		}
		
		//loads audio fx for npcs:
		for (int i = 0; i < allNPC.size(); i++) {
			allNPC.get(i).loadSoundEffect();
		}
	}
	
	public void playMusic()
	{
		//TODO change to playasmusic and fix to play, move from init
		//levelMusic.playAsSoundEffect(1.0f, 1.0f, true);
		
		if(!levelMusic.isPlaying()) //checks if already playing music
    	{
			levelMusic.playAsMusic(1.0f, musicVolume, true);
    	}
		// polling is required to allow streaming to get a chance to
		// queue buffers.
		//SoundStore.get().poll(0);
	}
	
	public void updateNPC(Character ch) //will move all npcs
	{
		//testNPC.moveNPC(barrier, ch);
		
		for(int i = 0; i < allNPC.size(); i++) //moves all npcs in array individually
		{
			allNPC.get(i).update(barrier,  ch, tileFloor.getWidth()*30);
			
			if(!allNPC.get(i).isAlive()) //if npc is dead, remove from list
			{
				allNPC.remove(i);
			}
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
		
		tileFloor.render(x-xShift, y, floorLayerIndex); //only render static layer (layer 0)
		
		//testNPC.drawCharacter(xShift);
		
		//***NOTE: Optimize to only render npcs onscreen
		for(int i = 0; i < allNPC.size(); i++) //draws all npcs in array
		{
			allNPC.get(i).draw(xShift);
		}
	}
}
