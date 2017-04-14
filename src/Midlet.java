import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
//Download by http://www.codefans.net

public class Midlet extends MIDlet {
	
	public static Display display = null;
	public LoadGame loadgame = null;
	public Midlet() {
		// TODO Auto-generated constructor stub
		display = Display.getDisplay(this);
		loadgame = new LoadGame(this);
	
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		notifyDestroyed();
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
//		System.out.println(Math.max(121, 80)); 最大攻击
//		System.out.println(Math.min(121, 80)); 最小攻击
//		Random rand = new Random();
//		System.out.println(rand.nextInt());
		display.setCurrent(loadgame);
		new Thread(loadgame).start();
	}

	
}
