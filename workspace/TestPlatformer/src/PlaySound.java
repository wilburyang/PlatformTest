import java.util.Random;
public class PlaySound implements Runnable{
	String name;
	int time;
	public PlaySound(String filename){
		name = filename;
		Random r = new Random();
		int t = r.nextInt(999);
		if(t>=666){
			time = 5000;
		}else if(t>=333){
			time = 4000;
		}else if(t>=0){
			time = 3000;
		}
	}
	@Override
	public void run() {

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		AePlayWave sound = new AePlayWave(name);
		sound.run();
	}

}