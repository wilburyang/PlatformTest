
public class PlayTrack implements Runnable{
	String name;
	public PlayTrack(String filename){
		name = filename;
	}
	public void run(){
		AePlayWave track = new AePlayWave(name);
		while(true){
			track.run();
		}
	}

}