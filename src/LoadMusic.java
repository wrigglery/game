import java.io.IOException;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
//Download by http://www.codefans.net
public class LoadMusic {
	
	public Player play;
	
	public void PlayBg_1(){
		InputStream is = this.getClass().getResourceAsStream("/017.mid");
		try {
			play = Manager.createPlayer(is,"audio/midi");
			play.realize();
			play.prefetch();
			play.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc();
	}
	
	public void PlayShengLi(){
		InputStream is = this.getClass().getResourceAsStream("/002.mid");
		try {
			play = Manager.createPlayer(is,"audio/midi");
			play.realize();
			play.prefetch();
			play.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc();
	}
}