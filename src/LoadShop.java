import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;
//Download by http://www.codefans.net

public class LoadShop extends GameCanvas implements Runnable  {
	
	public RPGGameCanvas rpggamecanvas = null;
	public static Thread ThisThread = null;
	public static boolean isFinish;
	public static boolean isRun = true;
	private boolean isNPC = false;
	private boolean NPC[] = new boolean[10];
	public static TiledLayer TBg = null;
	public static TiledLayer TBDT = null;
	public static TiledLayer Desk = null;
	public static TiledLayer DeskUp = null;
	public static TiledLayer Pail = null;
	public static TiledLayer Obstacle = null;
	public static LayerManager LM = null;
	public Graphics g = getGraphics();
	public short HeroX = 120;
	public short HeroY = 272;
	public short sequence = 1;
	
	
	private static short SHOP_STATE = 0;
	private static final short SHOP_DUIHUA = 1;
	
	
	protected LoadShop(boolean b,RPGGameCanvas rpggamecanvas) {
		super(b);
		setFullScreenMode(true);
		this.rpggamecanvas = rpggamecanvas;
		LM = new LayerManager();
		isFinish = false;
		if(RPGGameCanvas.isVino)
			Vino();
		else if(RPGGameCanvas.isShop)
			Shop();
		else if(RPGGameCanvas.isHospital)
			Hospital();
		ThisThread = new Thread(this);
		ThisThread.start();
		// TODO Auto-generated constructor stub
	}
	private void Hospital() {
		// TODO Auto-generated method stub
		System.out.println("Hospital");
		LM.paint(getGraphics(), 0, 0);
	}

	private void Shop() {
		// TODO Auto-generated method stub
		TBg = new TiledLayer(Map.shop[0].length, Map.shop.length, LoadImageSprite.imgShop, 16, 16);
		Desk = new TiledLayer(Map.shop[0].length, Map.shop.length, LoadImageSprite.imgShop, 16, 16);
		DeskUp = new TiledLayer(Map.shop[0].length, Map.shop.length, LoadImageSprite.imgShop, 16, 16);
		Pail = new TiledLayer(Map.shop[0].length, Map.shop.length, LoadImageSprite.imgShop, 16, 16);
		Obstacle = new TiledLayer(Map.shop[0].length, Map.shop.length, LoadImageSprite.imgShop, 16, 16);
		TBDT = new TiledLayer(Map.shop[0].length, Map.shop.length, LoadImageSprite.imgShop, 16, 16);
		for(int x=0;x<Map.shop.length;x++)
			for(int y=0;y<Map.shop[0].length;y++)
			{
				TBg.setCell(y, x, 97);
				if(Map.shop[x][y]==169||Map.shop[x][y]==170||Map.shop[x][y]==173||Map.shop[x][y]==174||Map.shop[x][y]==175||Map.shop[x][y]==176)
				Desk.setCell(y, x, Map.shop[x][y]);
				if(Map.shop[x][y]==163||Map.shop[x][y]==164||Map.shop[x][y]==165||Map.shop[x][y]==166||Map.shop[x][y]==51||Map.shop[x][y]==52||Map.shop[x][y]==53)
					DeskUp.setCell(y, x, Map.shop[x][y]);
				if(Map.shop[x][y]==10||Map.shop[x][y]==45||Map.shop[x][y]==69||Map.shop[x][y]==41)
					Pail.setCell(y, x, Map.shop[x][y]);
				if(Map.shop[x][y]==61||Map.shop[x][y]==62||Map.shop[x][y]==63||Map.shop[x][y]==114||Map.shop[x][y]==84||Map.shop[x][y]==94||
						Map.shop[x][y]==67||Map.shop[x][y]==68||Map.shop[x][y]==78||Map.shop[x][y]==79||Map.shop[x][y]==76||Map.shop[x][y]==73||
						Map.shop[x][y]==83||Map.shop[x][y]==74||Map.shop[x][y]==103||Map.shop[x][y]==124||Map.shop[x][y]==104)
					Obstacle.setCell(y, x, Map.shop[x][y]);
				if(Map.shop[x][y]==117||Map.shop[x][y]==127||Map.shop[x][y]==118||Map.shop[x][y]==119||Map.shop[x][y]==128||Map.shop[x][y]==129)
					TBDT.setCell(y, x, Map.shop[x][y]);
			}
		LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
		LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY);
		LM.append(DeskUp);
		LM.append(LoadGame.loadimagesprite.spHero);
		LM.append(LoadGame.loadimagesprite.spHeroBody);
		LM.append(LoadGame.loadimagesprite.spNPC[0]);
		LM.append(TBDT);
		LM.append(Obstacle);
		LM.append(Pail);
		LM.append(Desk);
		LM.append(TBg);
		LM.paint(getGraphics(), 0, 0);
	}

	private void Vino() {
		// TODO Auto-generated method stub
		TBg = new TiledLayer(Map.JiuDian[0].length, Map.JiuDian.length, LoadImageSprite.imgShop, 16, 16);
		Desk = new TiledLayer(Map.JiuDian[0].length, Map.JiuDian.length, LoadImageSprite.imgShop, 16, 16);
		DeskUp = new TiledLayer(Map.JiuDian[0].length, Map.JiuDian.length, LoadImageSprite.imgShop, 16, 16);
		Pail = new TiledLayer(Map.JiuDian[0].length, Map.JiuDian.length, LoadImageSprite.imgShop, 16, 16);
		Obstacle = new TiledLayer(Map.JiuDian[0].length, Map.JiuDian.length, LoadImageSprite.imgShop, 16, 16);
		TBDT = new TiledLayer(Map.JiuDian[0].length, Map.JiuDian.length, LoadImageSprite.imgShop, 16, 16);
		for(int x=0;x<Map.JiuDian.length;x++)
			for(int y=0;y<Map.JiuDian[0].length;y++)
			{
				TBg.setCell(y, x, 97);
				if(Map.JiuDian[x][y]==5||Map.JiuDian[x][y]==6||Map.JiuDian[x][y]==15||Map.JiuDian[x][y]==16||Map.JiuDian[x][y]==7||Map.JiuDian[x][y]==17)
					Desk.setCell(y, x, Map.JiuDian[x][y]);
					if(Map.JiuDian[x][y]==73||Map.JiuDian[x][y]==74||Map.JiuDian[x][y]==51||Map.JiuDian[x][y]==52||Map.JiuDian[x][y]==53)
						DeskUp.setCell(y, x, Map.JiuDian[x][y]);
					if(Map.JiuDian[x][y]==41||Map.JiuDian[x][y]==44||Map.JiuDian[x][y]==46)
						Pail.setCell(y, x, Map.JiuDian[x][y]);
					if(Map.JiuDian[x][y]==61||Map.JiuDian[x][y]==62||Map.JiuDian[x][y]==63||Map.JiuDian[x][y]==114||Map.JiuDian[x][y]==84||Map.JiuDian[x][y]==94||
							Map.JiuDian[x][y]==67||Map.JiuDian[x][y]==68||Map.JiuDian[x][y]==78||Map.JiuDian[x][y]==79||Map.JiuDian[x][y]==76||
							Map.JiuDian[x][y]==83||Map.JiuDian[x][y]==103||Map.JiuDian[x][y]==124||Map.JiuDian[x][y]==104||Map.JiuDian[x][y]==75||Map.JiuDian[x][y]==123||Map.JiuDian[x][y]==113)
						Obstacle.setCell(y, x, Map.JiuDian[x][y]);
					if(Map.JiuDian[x][y]==117||Map.JiuDian[x][y]==127||Map.JiuDian[x][y]==118||Map.JiuDian[x][y]==119||Map.JiuDian[x][y]==128||Map.JiuDian[x][y]==129)
						TBDT.setCell(y, x, Map.JiuDian[x][y]);
			}
		LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
		LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY);
		LM.append(DeskUp);
		LM.append(LoadGame.loadimagesprite.spHero);
		LM.append(LoadGame.loadimagesprite.spHeroBody);
		LM.append(TBDT);
		LM.append(Obstacle);
		LM.append(Pail);
		LM.append(Desk);
		LM.append(TBg);
		LM.paint(getGraphics(), 0, 0);
	}

	public void run() {
		// TODO Auto-generated method stub
		while(isRun)
		{
			if(SHOP_STATE!=SHOP_DUIHUA)
			input();
			switch(SHOP_STATE)
			{
			case SHOP_DUIHUA:
				if(NPC[0])
					ShowDialog(LoadGame.NPC[1]);
				flushGraphics();
				break;
			default :
				LM.paint(g, 0, 0);		//先刷图层
				if(NPC[0])
					DuiHuaGIF0();
			sequence ++;
			if(sequence==5)
				sequence = 1;
			flushGraphics();
			}
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("is run");
//			System.out.println(LoadGame.loadimagesprite.spHeroBody.getX()+"  "+LoadGame.loadimagesprite.spHeroBody.getY());
		}
	}
	private void DuiHuaGIF0() {
		// TODO Auto-generated method stub
		if(sequence==1)
		g.drawImage(LoadGame.loadimagesprite.imgBQPoint[0], 144, 86, 0);
		else if(sequence==2)
			g.drawImage(LoadGame.loadimagesprite.imgBQPoint[1], 144, 86, 0);
		else if(sequence==3)
			g.drawImage(LoadGame.loadimagesprite.imgBQPoint[2], 144, 86, 0);
		else
			g.drawImage(LoadGame.loadimagesprite.imgBQPoint[3], 144, 86, 0);
	}
	public void input()
	{
			if((getKeyStates() & LEFT_PRESSED) != 0)
				Move(LEFT);
			else if((getKeyStates() & RIGHT_PRESSED) != 0)
				Move(RIGHT);
			else if((getKeyStates() & UP_PRESSED) != 0)
				Move(UP);
			else if((getKeyStates() & DOWN_PRESSED) != 0)
				Move(DOWN);
	}
	private void Move(int dir) {
		// TODO Auto-generated method stub
		int direction = dir;
		int x = HeroX;
		int y = HeroY;
		switch(direction)
		{
		case LEFT:
			if(RPGGameCanvas.isLeft != false)
				rpggamecanvas.HeroLeft();	//外部对象调用本类的方法时必须是公用的
			LoadGame.loadimagesprite.spHero.nextFrame();
			x -= 8;
			break;
		case RIGHT:
			if(RPGGameCanvas.isRight != false)
				rpggamecanvas.HeroRight();
			LoadGame.loadimagesprite.spHero.nextFrame();
			x += 8;
			break;
		case UP:
			if(RPGGameCanvas.isUp != false)
				rpggamecanvas.HeroUp();
			LoadGame.loadimagesprite.spHero.nextFrame();
			y -= 8;
			break;
		case DOWN:
			if(RPGGameCanvas.isDown != false)
				rpggamecanvas.HeroDown();
			LoadGame.loadimagesprite.spHero.nextFrame();
			y += 8;
			break;
		}
		Move(x,y);
	}

	private void Move(int x, int y) {
		// TODO Auto-generated method stub
		if(RPGGameCanvas.isShop)	//洒店碰撞检测
		{
			LoadGame.loadimagesprite.spHero.setPosition(x, y);
			LoadGame.loadimagesprite.spHeroBody.setPosition(x, y+23);
//			System.out.println(LoadGame.loadimagesprite.spHeroBody.getX()+"  "+LoadGame.loadimagesprite.spHeroBody.getY());
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.loadimagesprite.spNPC[0], true))
			{
				NPC[0] = true;
				isNPC = true;
			}else{
				isNPC = false;
				NPC[0] = false;
			}
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(Desk, true)||LoadGame.loadimagesprite.spHeroBody.collidesWith(Pail, true)||
					LoadGame.loadimagesprite.spHeroBody.collidesWith(Obstacle, true))
			{
				LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
				LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY+23);
				return;
			}
			if(LoadGame.loadimagesprite.spHeroBody.getX()>=64&&LoadGame.loadimagesprite.spHeroBody.getX()<=128&&
					LoadGame.loadimagesprite.spHeroBody.getY()>=292)
			{
				RPGGameCanvas.GAME_STATE = RPGGameCanvas.GAME_NEW; 
				rpggamecanvas.isOver = true;
				LM.remove(LoadGame.loadimagesprite.spHero);
				LM.remove(LoadGame.loadimagesprite.spHeroBody);
				LoadGame.loadimagesprite.spHero.setPosition(100, 172);
				LoadGame.loadimagesprite.spHeroBody.setPosition(100, 184);
				Midlet.display.setCurrent(rpggamecanvas);
				isFinish = true;
				isRun = false;
//				try {
//					new Thread
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			HeroX = (short) x;
			HeroY = (short) y;
		}else if(RPGGameCanvas.isVino)
		{
			LoadGame.loadimagesprite.spHero.setPosition(x, y);
			LoadGame.loadimagesprite.spHeroBody.setPosition(x, y+23);
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(Desk, true)||LoadGame.loadimagesprite.spHeroBody.collidesWith(Pail, true)||
					LoadGame.loadimagesprite.spHeroBody.collidesWith(Obstacle, true))
			{
				LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
				LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY+23);
				return;
			}
			if(LoadGame.loadimagesprite.spHeroBody.getX()>=64&&LoadGame.loadimagesprite.spHeroBody.getX()<=128&&
					LoadGame.loadimagesprite.spHeroBody.getY()>=292)
			{
				RPGGameCanvas.GAME_STATE = RPGGameCanvas.GAME_NEW; 
				rpggamecanvas.isOver = true;
				LM.remove(LoadGame.loadimagesprite.spHero);
				LM.remove(LoadGame.loadimagesprite.spHeroBody);
				LoadGame.loadimagesprite.spHero.setPosition(708, 172);
				LoadGame.loadimagesprite.spHeroBody.setPosition(708, 184);
				Midlet.display.setCurrent(rpggamecanvas);
				isFinish = true;
				isRun = false;
			}
			if(x <= -5||y <= -5||x >= Map.JiuDian[0].length * 16-24 ||y >= Map.JiuDian.length * 16-24)//地图边界不能走的算法
			{
				LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
				LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY+23);
				return;
			}
			HeroX = (short) x;
			HeroY = (short) y;
		}
		LM.paint(getGraphics(), 0, 0);
	}
	
	
	public void ShowDialog(String str)
	{
		g.drawImage(LoadGame.loadimagesprite.imgDiaLogBg, 0, 248, 0);
		g.setColor(0xffffff);
		g.setFont(LoadGame.font);
		//0 开始位置，20输出字体长度
//		if(LoadGame.font.stringWidth(str)>=220&&LoadGame.font.stringWidth(str)<=440)//E2字体放置14个,手机模拟器用18个为满
//		{
//			g.drawSubstring(str, 0, 14, 10, 256, 0);
//			g.drawSubstring(str, 14, str.length()-14, 10, 276, 0);
//		}else
//		{
//			g.drawSubstring(str, 0, 14, 10, 256, 0);
//			g.drawSubstring(str, 14,14, 10, 276, 0);
//			g.drawSubstring(str, 28, str.length()-28, 10, 296, 0);
//		}
		if(LoadGame.font.stringWidth(str)>=220&&LoadGame.font.stringWidth(str)<=440)//适合手机顽童的字体
		{
			g.drawSubstring(str, 0, 18, 10, 256, 0);
			g.drawSubstring(str, 18, str.length()-18, 10, 276, 0);
		}else
		{
			g.drawSubstring(str, 0, 18, 10, 256, 0);
			g.drawSubstring(str, 18,18, 10, 276, 0);
			g.drawSubstring(str, 36, str.length()-36, 10, 296, 0);
		}
	}
	
	protected void keyPressed(int KeyCode)
	{
		if(SHOP_STATE == SHOP_DUIHUA)
			if(getGameAction(KeyCode)==8)
			{
				isNPC = false;
				NPC[0] = false;
				SHOP_STATE = 0;
			}
			if(getGameAction(KeyCode)==8&&isNPC)
				SHOP_STATE = SHOP_DUIHUA;
	}
}
