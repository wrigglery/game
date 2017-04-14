import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;
//Download by http://www.codefans.net
public class LoadGame extends Canvas implements Runnable {
	public Midlet midlet = null;
	public static Map map = null;
	public static LoadImageSprite loadimagesprite = null;
	public static RPGGameCanvas rpggamecanvas = null;
	public static LoadMusic loadmusic = null;
	public static int width = 0;
	public static int height = 0;
	public static int mapWidth = 0;
	public static int mapHeight = 0;
	private short Gauge = 1;
	private short ProgressInterval = 1;
	public static Font font = null;
	public boolean isRun;
	public static boolean isMusic;
	public static boolean isPlayingMusic;
	public static boolean isLoadFinish;
	public long starttime,endtime;
	
	public static TiledLayer TBg;
	public static TiledLayer TreeUp,TreeDown;
	public static TiledLayer GrassUp1,GrassDown1;
	public static TiledLayer GrassUp2,GrassDown2;
	public static TiledLayer GrassUp3,GrassDown3;
	public static TiledLayer HouseUp1,HouseDown1;
	public static TiledLayer HouseUp2,HouseDown2;
	public static TiledLayer HouseUp3,HouseDown3;
	public static TiledLayer HouseUp4,HouseDown4;
	public static TiledLayer Road;
	public static TiledLayer River;
	public static LayerManager LayerM;
	
	public static String str1 = "欢迎来到古代传说游戏中来，目前该游戏正在测试中，" +
			"你只要按着提示完成游戏任务就OK了！";
	public static String str2 = "那么接下来就看商店里面逛逛,看看有什么发现没有?" +
			"仔细点哦,不然很可能就会错过的喽!";
	public static String NPC[] = new String[10];
	public LoadGame(Midlet midlet)
	{
		this.setFullScreenMode(true);
		this.loadString();
		this.midlet = midlet;
		map = new Map();
		loadimagesprite = new LoadImageSprite();
		loadmusic = new LoadMusic();
		width = this.getWidth();
		height = this.getHeight();
		font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		this.initMap();
	}

	protected void paint(Graphics g) {
		// TODO Auto-generated method stub
		if(!isRun)
		{
			g.setColor(0x000000);
			g.fillRect(0, 0, width, height);
			g.setColor(0xffffff);
			g.drawString("是否打开音乐？", (width-font.stringWidth("是否打开音乐？"))/2, 
					(height-font.getHeight())/2, 0);
			g.drawString("建议关闭背景音乐", (width-font.stringWidth("建议关闭背景音乐"))/2, 
					(height-(font.getHeight()))/2+2*font.getHeight(), 0);
			g.drawImage(LoadGame.loadimagesprite.confirmno, 0, height-LoadGame.loadimagesprite.confirmno.getHeight(), 0);
			g.drawImage(LoadGame.loadimagesprite.confirmyes, width-LoadGame.loadimagesprite.confirmno.getWidth(),
					height-LoadGame.loadimagesprite.confirmno.getHeight(), 0);
		}else{
			g.drawImage(loadimagesprite.imgDragon, 0, 0, 0);
			g.drawString("游戏装载中...("+Gauge+"%"+")", (width-font.stringWidth("游戏装载中...("+Gauge+"%"+")"))/2, (height-14)-font.getHeight()-6, 0);
			g.setColor(0x92CBF5);
			g.drawRect(10, height-14, 220, 10);
			g.setColor(0xffffff);
			g.fillRect(11, height-13, Gauge*(width-22)/100, 9);
			if(Gauge==100)
			{
				if(isMusic){
					loadmusic.PlayBg_1();
					isPlayingMusic = true;
				}
				Midlet.display.setCurrent(rpggamecanvas);
				isLoadFinish = true;
			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while(isRun)
		{
			starttime = System.currentTimeMillis();
			if(Gauge<100)
			Gauge += ProgressInterval;
			if(Gauge>100)
			Gauge = 100;
			if(rpggamecanvas!=null)
				ProgressInterval+=1;
			repaint();
			serviceRepaints();
			endtime = System.currentTimeMillis();
			try {
				Thread.sleep((endtime-starttime)*6);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initMap()
	{
		TBg = new TiledLayer(map.cells[0].length,map.cells.length,LoadGame.loadimagesprite.imgBg,16,16);
		TreeUp = new TiledLayer(map.cells[0].length,map.cells.length,LoadGame.loadimagesprite.imgBg,16,16);
		TreeDown = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		
		GrassUp1 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		GrassUp2 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		GrassUp3 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		
		GrassDown1 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		GrassDown2 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		GrassDown3 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		
		HouseUp1 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		HouseUp2 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		HouseUp3 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		HouseUp4 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		
		HouseDown1 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		HouseDown2 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		HouseDown3 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		HouseDown4 = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		
		Road = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		River = new TiledLayer(map.cells[0].length,map.cells.length,loadimagesprite.imgBg,16,16);
		mapWidth = map.cells[0].length * 16;	//地图总宽    减去人的宽 21
		mapHeight = map.cells.length * 16;	//地图总高  减去人的高 60  
		for(int y=0;y<map.cells.length;y++) {
		for(int x=0;x<map.cells[0].length;x++) {
        	TBg.setCell(x, y, 6);
        	if(map.cells[y][x]==83||map.cells[y][x]==84)
        		TreeDown.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==72||map.cells[y][x]==73)
        		TreeUp.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==122||map.cells[y][x]==123||map.cells[y][x]==124||map.cells[y][x]==125||map.cells[y][x]==126)
        		HouseUp1.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==127||map.cells[y][x]==128||map.cells[y][x]==129||map.cells[y][x]==130||map.cells[y][x]==131||map.cells[y][x]==89||map.cells[y][x]==90||map.cells[y][x]==91||map.cells[y][x]==92||map.cells[y][x]==93)
        		HouseDown1.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==67||map.cells[y][x]==68||map.cells[y][x]==69||map.cells[y][x]==70||map.cells[y][x]==71||map.cells[y][x]==78||map.cells[y][x]==79||map.cells[y][x]==80||map.cells[y][x]==81||map.cells[y][x]==82)
        		HouseUp2.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==41||map.cells[y][x]==42||map.cells[y][x]==43||map.cells[y][x]==44||map.cells[y][x]==52||map.cells[y][x]==53||map.cells[y][x]==54||map.cells[y][x]==55||map.cells[y][x]==94||map.cells[y][x]==95||map.cells[y][x]==64||map.cells[y][x]==65||map.cells[y][x]==96||map.cells[y][x]==97)
        		HouseUp3.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==105||map.cells[y][x]==106||map.cells[y][x]==75||map.cells[y][x]==76||map.cells[y][x]==11||map.cells[y][x]==108||map.cells[y][x]==116||map.cells[y][x]==117||map.cells[y][x]==86||map.cells[y][x]==87||map.cells[y][x]==118||map.cells[y][x]==119)
        		HouseDown3.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==41||map.cells[y][x]==42||map.cells[y][x]==43||map.cells[y][x]==44||map.cells[y][x]==52||map.cells[y][x]==53||map.cells[y][x]==54||map.cells[y][x]==55||map.cells[y][x]==63||map.cells[y][x]==64||map.cells[y][x]==65||map.cells[y][x]==66||map.cells[y][x]==74||map.cells[y][x]==75||map.cells[y][x]==76||map.cells[y][x]==33)
        		HouseUp4.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==85||map.cells[y][x]==86||map.cells[y][x]==87||map.cells[y][x]==88)
        		HouseDown4.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==22)
        		HouseDown4.setCell(x, y, 22);
        	if(map.cells[y][x]==37)
        		GrassUp1.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==48)
        		GrassDown1.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==100||map.cells[y][x]==101||map.cells[y][x]==102||map.cells[y][x]==103)
        		GrassUp2.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==113||map.cells[y][x]==114)
        		GrassDown2.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==98||map.cells[y][x]==99||map.cells[y][x]==109||map.cells[y][x]==110)
        		GrassUp3.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==120||map.cells[y][x]==121)
        		GrassDown3.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==2||map.cells[y][x]==3||map.cells[y][x]==4||map.cells[y][x]==13||map.cells[y][x]==14||map.cells[y][x]==15||map.cells[y][x]==24||map.cells[y][x]==25||map.cells[y][x]==26)
        		Road.setCell(x, y, map.cells[y][x]);
        	if(map.cells[y][x]==34||map.cells[y][x]==35||map.cells[y][x]==36||map.cells[y][x]==45||map.cells[y][x]==46||map.cells[y][x]==47||map.cells[y][x]==56||map.cells[y][x]==57||map.cells[y][x]==58)
        		River.setCell(x, y, map.cells[y][x]);
			}
		}
		LayerM = new LayerManager();
		
		LayerM.append(HouseUp1);
		LayerM.append(HouseUp2);
		LayerM.append(HouseUp3);
		LayerM.append(HouseUp4);
		
		LayerM.append(GrassUp1);
		LayerM.append(GrassUp2);
		LayerM.append(GrassUp3);
		
		LayerM.append(TreeUp);
		
		LayerM.append(loadimagesprite.spHero);
		LayerM.append(loadimagesprite.spHeroBody);
		LayerM.append(loadimagesprite.sFeng);
		LayerM.append(loadimagesprite.spNPC[1]);
		LayerM.append(loadimagesprite.spNPC[2]);
		
		LayerM.append(loadimagesprite.spSanJianTou1);
		LayerM.append(loadimagesprite.spSanJianTou2);
		LayerM.append(loadimagesprite.spSanJianTou3);
		
		LayerM.append(HouseDown1);
		LayerM.append(HouseDown3);
		LayerM.append(HouseDown4);
		
		LayerM.append(GrassDown1);
		LayerM.append(GrassDown2);
		LayerM.append(GrassDown3);
		
		LayerM.append(TreeDown);
		LayerM.append(Road);
		LayerM.append(River);
		LayerM.append(TBg);
	}
	
	protected void keyPressed(int KeyCode)
	{
		if(getKeyName(KeyCode).equals("SOFT2")||getKeyName(KeyCode).equals("5"))
		{
			isRun = true;
			isMusic = true;
			rpggamecanvas = new RPGGameCanvas(false,midlet,this);	//点是之后就开始加载游戏
			new Thread(this).start();
		}else{
			if(getKeyName(KeyCode).equals("SOFT1"))
			{
				isRun = true;
				isMusic = false;
				rpggamecanvas = new RPGGameCanvas(false,midlet,this);	//点是之后就开始加载游戏
				new Thread(this).start();
			}
		}
	}

	public void loadString(){
		NPC[0] = "小翠：大爷，我们的有空来我们飘香院来坐坐哦，我" +
		"们里面的美女真是天下绝版的，开通？";
		NPC[1] = "店老板：客官，你想要些什么东西？我们这可是什么" +
				"东西都有哦！你先随便看看！";
		NPC[2] = "小书生：这位兄台，我就是传说江湖，笑面书生，我" +
				"收藏了好多江湖上失传已久的秘籍！";
	}
}
