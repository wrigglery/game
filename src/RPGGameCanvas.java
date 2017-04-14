import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.media.MediaException;
import javax.microedition.midlet.MIDletStateChangeException;
//Download by http://www.codefans.net

public class RPGGameCanvas extends GameCanvas implements Runnable {

	public Midlet midlet = null;
	public LoadGame loadgame = null;
	public LoadShop loadshop = null;
	public Fight fight = null;
	public Graphics g = getGraphics();
	public static Thread RPGThread = null;
	public static boolean isOver = false;
	public static int GAME_STATE = 0;
	private static int MENU_STATE = 1;
	public static final int GAME_MENU = 0;
	public static final int GAME_NEW = 1;
	public static final int GAME_LOAD = 2;
	public static final int GAME_SET = 3;
	public static final int GAME_HELP = 4;
	public static final int GAME_EXIT = 5;
	public static final int GAME_SHOP = 6;
	public static final int GAME_DUIHUA = 7;
	public static final int GAME_FIGHT = 8;
	
	public static boolean isUp = true;
	public static boolean isDown = true;
	public static boolean isLeft = true;
	public static boolean isRight = true;
	public static boolean isDiaLog = true;
	public static boolean isDiaLogOver = true;
	
	private boolean isUse;	//�Ƿ������һ��
	private boolean isRun;
	
	public static boolean isVino;
	public static boolean isShop;
	public static boolean isHospital;
	//********************************//NPC����ײ���
	private boolean isNPC;		//��������NPC�Ƿ���ײ����ͼ��� �ж�
	private boolean NPC[] = new boolean[10];
	public short HeroX;
	public short HeroY;
	public short x;
	public short y;//�����ƶ�����
	public short fy = 192; //�ֳ�ʼ����
	public short mapx = 24;
	public short mapy = 32;
	
	public static short hhp = 100;
	public static short hmp = 100;
	public static short mhp[] = new short[3];
	public static short hattack = 39;
	public static short mattack = 3;
	private int vwX = 0;
	private int vwY = 0;
	private int BORDER = 100;
	
	private int helpy;//��Ϸ�����ƶ�
	
	private short Gauge = 1;
	private short ProgressInterval = 1;
	
	private  String str = LoadGame.str1;
	
	public short sequence = 1;	//�������
	public short step = 3;
	protected RPGGameCanvas(boolean arg0,Midlet midlet,LoadGame loadgame) {
		super(arg0);
		this.midlet = midlet;
		this.loadgame = loadgame;
		this.setFullScreenMode(true);
		mhp[0] = 80;
		mhp[1] = 80;
		mhp[2] = 80;
		isOver = true;
		HeroX = 100;
		HeroY = 180;//����ĳ�ʼλ��
		RPGThread = new Thread(this);
		RPGThread.start();
		// TODO Auto-generated constructor stub
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			if(isOver)
			{
				if(!isDiaLog)
				input();
				switch(GAME_STATE)
				{
				case GAME_MENU:
					if(!Fight.isOver)
					{
						fight = null;
						Fight.isOver = true;
						System.out.println("fight is null");
					}
					DrawMenu();
					break;
				case GAME_NEW:
					if(!LoadShop.isRun)//�̵�������ص���Ϸ������
					{
						loadshop = null;
						LoadShop.isRun = true;
						this.isShop = false;
						this.isVino = false;
						this.isHospital = false;
						System.out.println("loadshop is null");
					}
					if(!Fight.isOver)
					{
						fight = null;
						Fight.isOver = true;
						System.out.println("fight is null");
					}
					EnterGame();
					if(isNPC)
					{
						sequence ++;
						if(sequence==5)
						sequence = 1;
					if(NPC[0])	//��Ϸ�������
						DuiHuaGIF0(LoadGame.loadimagesprite.spNPC[1].getX()-vwX,LoadGame.loadimagesprite.spNPC[1].getY()-vwY-15);
					else if(NPC[1])
						DuiHuaGIF0(LoadGame.loadimagesprite.spNPC[2].getX()-vwX,LoadGame.loadimagesprite.spNPC[2].getY()-vwY-15);
					}
					fy += step;
					if(fy>=200){
						fy = 200;
						step = (short) -step;
					}
					if(fy<=100){
						fy = 100;
						step = (short) -step;
					}
					LoadGame.loadimagesprite.sFeng.setPosition(310, fy);
					flushGraphics();
					break;
				case GAME_LOAD:
					LoadGame();
					break;
				case GAME_SET:
					SetGame();
					flushGraphics();
					break;
				case GAME_HELP:
					HelpGame();
					break;
				case GAME_EXIT:
					try {
							midlet.destroyApp(true);
						} catch (MIDletStateChangeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					break;
				case GAME_SHOP:
					LoadShop();
					flushGraphics();
					break;
				case GAME_DUIHUA:
					if(NPC[0])
						ShowDialog(LoadGame.NPC[0]);
					else if(NPC[1])
						ShowDialog(LoadGame.NPC[2]);
					flushGraphics();
					break;
				case GAME_FIGHT:
					LoadFight();
					flushGraphics();
					break;
				default:
					ErrorGame();
					flushGraphics();
					break;
				}
				LoadGame.loadimagesprite.spSanJianTou1.nextFrame();
				LoadGame.loadimagesprite.spSanJianTou2.nextFrame();
				LoadGame.loadimagesprite.spSanJianTou3.nextFrame();
				LoadGame.loadimagesprite.sFeng.nextFrame();
				
				if(LoadGame.isMusic){
									if(LoadGame.isPlayingMusic){
					if(LoadGame.loadmusic.play.getState()==300){
						LoadGame.loadmusic.play.close();
						LoadGame.loadmusic.PlayBg_1();
					}
				}

				}
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
//			System.out.println(LoadGame.loadimagesprite.spHeroBody.getX()+"  "+LoadGame.loadimagesprite.spHeroBody.getY());
//			System.out.println("Run");
		}
	}
	
	private void DuiHuaGIF0(int x,int y) {
		// TODO Auto-generated method stub
		if(sequence==1)
		g.drawImage(LoadGame.loadimagesprite.imgBQPoint[0], x, y, 0);
		else if(sequence==2)
			g.drawImage(LoadGame.loadimagesprite.imgBQPoint[1], x, y, 0);
		else if(sequence==3)
			g.drawImage(LoadGame.loadimagesprite.imgBQPoint[2], x, y, 0);
		else
			g.drawImage(LoadGame.loadimagesprite.imgBQPoint[3], x, y, 0);
//		System.out.println(LoadGame.loadimagesprite.spNPC[1].getX()+"   "+LoadGame.loadimagesprite.spNPC[1].getY());
	}
	
	private void LoadShop() {
		// TODO Auto-generated method stub
		g.drawImage(LoadGame.loadimagesprite.imgDragon, 0, 0, 0);
		g.drawString("��Ϸװ����...("+Gauge+"%"+")", (LoadGame.width-LoadGame.font.stringWidth("��Ϸװ����...("+Gauge+"%"+")"))/2,
				(LoadGame.height-14)-LoadGame.font.getHeight()-6, 0);
		g.setColor(0x92CBF5);
		g.drawRect(10, LoadGame.height-14, 220, 10);
		g.setColor(0xffffff);
		g.fillRect(11, LoadGame.height-13, Gauge*(LoadGame.width-22)/100, 9);
		if(Gauge==100)
		{
			Midlet.display.setCurrent(loadshop);
			Gauge = 1;
			isOver = false;
			try {
				LoadShop.ThisThread.join();//����������̣߳����߳̽�������״̬
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(Gauge<100)
			Gauge += ProgressInterval;
			if(Gauge>100)
			Gauge = 100;
			if(loadshop!=null)
				ProgressInterval+=1;
	}

	private void LoadFight() {
		// TODO Auto-generated method stub
		g.drawImage(LoadGame.loadimagesprite.imgDragon, 0, 0, 0);
		g.drawString("��Ϸװ����...("+Gauge+"%"+")", (LoadGame.width-LoadGame.font.stringWidth("��Ϸװ����...("+Gauge+"%"+")"))/2,
				(LoadGame.height-14)-LoadGame.font.getHeight()-6, 0);
		g.setColor(0x92CBF5);
		g.drawRect(10, LoadGame.height-14, 220, 10);
		g.setColor(0xffffff);
		g.fillRect(11, LoadGame.height-13, Gauge*(LoadGame.width-22)/100, 9);
		if(Gauge==100)
		{
			Midlet.display.setCurrent(fight);
			Gauge = 1;
			isOver = false;
			try {
				Fight.FightThread.join();//����������̣߳����߳̽�������״̬
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(Gauge<100)
			Gauge += ProgressInterval;
			if(Gauge>100)
			Gauge = 100;
			if(fight!=null)
				ProgressInterval+=1;
	}
	
	private void DrawMenu()
	{
		g.setColor(0xffffff);
		g.fillRect(0, 0, LoadGame.width, LoadGame.height);
		g.drawImage(LoadGame.loadimagesprite.imgMenu, LoadGame.width-LoadGame.loadimagesprite.imgMenu.getWidth(),
				LoadGame.height-LoadGame.loadimagesprite.imgMenu.getHeight(), 0);
		if(MENU_STATE==GAME_NEW)
			g.drawImage(LoadGame.loadimagesprite.imgMenuB1, (LoadGame.width-LoadGame.loadimagesprite.imgMenuB1.getWidth())/2+1,
					(LoadGame.height-LoadGame.loadimagesprite.imgMenuB1.getHeight())/3+8, 0);
		else if(MENU_STATE==GAME_LOAD)
		g.drawImage(LoadGame.loadimagesprite.imgMenuB2, (LoadGame.width-LoadGame.loadimagesprite.imgMenuB2.getWidth())/2+1,
				(LoadGame.height-LoadGame.loadimagesprite.imgMenuB2.getHeight())/3+LoadGame.loadimagesprite.imgMenuB2.getHeight()*2+0, 0);
		else if(MENU_STATE==GAME_SET)
			g.drawImage(LoadGame.loadimagesprite.imgMenuB3, (LoadGame.width-LoadGame.loadimagesprite.imgMenuB3.getWidth())/2+1,
					(LoadGame.height-LoadGame.loadimagesprite.imgMenuB2.getHeight())/3+LoadGame.loadimagesprite.imgMenuB2.getHeight()*4-8, 0);
		else if(MENU_STATE==GAME_HELP)
			g.drawImage(LoadGame.loadimagesprite.imgMenuB4, (LoadGame.width-LoadGame.loadimagesprite.imgMenuB4.getWidth())/2+1,
					(LoadGame.height-LoadGame.loadimagesprite.imgMenuB2.getHeight())/3+LoadGame.loadimagesprite.imgMenuB2.getHeight()*6-16, 0);
		else if(MENU_STATE==GAME_EXIT)
			g.drawImage(LoadGame.loadimagesprite.imgMenuB5, (LoadGame.width-LoadGame.loadimagesprite.imgMenuB5.getWidth())/2+1,
					(LoadGame.height-LoadGame.loadimagesprite.imgMenuB2.getHeight())/3+LoadGame.loadimagesprite.imgMenuB2.getHeight()*8-24, 0);
		flushGraphics();
	}
	
	private void EnterGame()
	{
		LoadGame.LayerM.paint(g, 0, 0);
		g.setColor(255,255,255);
		g.setFont(LoadGame.font);
		g.drawString("x:"+mapx+" y:"+mapy, (this.getWidth()-LoadGame.font.stringWidth("X:,Y:000,000")),this.getHeight()-LoadGame.font.stringWidth("��"), 0);
		if(isDiaLog)
		ShowDialog(str);
		if(!isRun)
		if(LoadGame.loadimagesprite.sFeng.collidesWith(LoadGame.loadimagesprite.spHeroBody, true)){
			GAME_STATE = GAME_FIGHT;
			fight = new Fight(false,this);
			isRun = true;
		}
//		System.out.println(LoadGame.loadimagesprite.spHeroBody.getX()+"  "+LoadGame.loadimagesprite.spHeroBody.getY());
	}
	
	private void LoadGame()
	{
		
	}
	
	private void SetGame()
	{
		
	}
	
	private void HelpGame()
	{
		g.drawImage(LoadGame.loadimagesprite.imgHelpDiaLog, 0, 0, 0);
		if(!isUse)
		flushGraphics();
		g.setFont(LoadGame.font);
		g.setColor(240,226,0);
		//14����Ϊ��
		g.drawString("��������", 10, 50+helpy, 0);
		g.drawString("�ܾ���ǰ��һ���콣�ͣ���", 30, 70+helpy, 0);
		g.drawString("���ϵ��˳���Ϊ����Ӱ����֮��", 10, 90+helpy, 0);
		g.drawString("��������������Ϊ���Ľ��ǳ�֮", 10, 110+helpy, 0);
		g.drawString("�죬���׿쵽���̶ֳȣ�û����", 10, 130+helpy, 0);
		g.drawString("֪��������ֻ�й��֪������Ϊ", 10, 150+helpy, 0);
		g.drawString("�������е��˻����϶���һ����", 10, 170+helpy, 0);
		g.drawString("������������Ļ���רΪ����", 10, 190+helpy, 0);
		g.drawString("���£��ͽ�¹ٶ�������������", 10, 210+helpy, 0);
		g.drawString("�ָ�ƶ����ա���", 10, 230+helpy, 0);
		g.drawString("��һ����Ǳ���˵���͢ة", 30, 250+helpy, 0);
		g.drawString("��ļ����������֮�£���С��", 10, 270+helpy, 0);
		g.drawString("������ة��׼������֮�£���Ϸ", 10, 290+helpy, 0);
		g.drawString("�Ӵ�չ��...", 10, 310+helpy, 0);
		g.drawString("��Ϸ���ߣ����", 90, 330+helpy, 0);
		if(!isUse)
			repaint(10,30,220,270);
		isUse = true;
	}
	
	private void ErrorGame()
	{
		g.setColor(0x000000);
		g.fillRect(0, 0, LoadGame.width, LoadGame.height);
		g.setColor(0xffffff);
		g.setFont(LoadGame.font);
		g.drawString("��Ϸ����", (LoadGame.width-LoadGame.font.stringWidth("��Ϸ����"))/2,
				(LoadGame.height-LoadGame.font.getHeight())/2, 0);
	}
	
	private void Move(int dir) {
		// TODO Auto-generated method stub
		int direction = dir;
		short mapX = mapx;
		short mapY = mapy;
		x = HeroX;
		y = HeroY;
		switch(direction)
		{
		case LEFT:
			if(isLeft != false)
				HeroLeft();
			LoadGame.loadimagesprite.spHero.nextFrame();
			x -= 8;
			mapX -= 1;
			break;
		case RIGHT:
			if(isRight != false)
				HeroRight();
			LoadGame.loadimagesprite.spHero.nextFrame();
			x += 8;
			mapX += 1;
			break;
		case UP:
			if(isUp != false)
				HeroUp();
			LoadGame.loadimagesprite.spHero.nextFrame();
			y -= 8;
			mapY -= 1;
			break;
		case DOWN:
			if(isDown != false)
				HeroDown();
			LoadGame.loadimagesprite.spHero.nextFrame();
			y += 8;
			mapY += 1;
			break;
		}
		Move(x,y,mapX,mapY);
	}

	private void Move(short x, short y, short mapX2, short mapY2) {
		// TODO Auto-generated method stub
			LoadGame.loadimagesprite.spHero.setPosition(x, y);
			LoadGame.loadimagesprite.spHeroBody.setPosition(x, y+23);
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.TreeDown, true)||LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.GrassDown1, true)||
					LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.GrassDown2, true)||LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.GrassDown3, true)||
					LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.HouseDown1, true)|LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.HouseDown3, true)||
					LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.HouseDown4, true)||LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.River, true))
			{
				LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
				LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY+23);
				mapx += 0;					//��ͼ����,���������Ͳ���!
				mapy += 0;
				return;
			}
			if(x <= -5||y <= -5||x >= LoadGame.mapWidth-24 ||y >= LoadGame.mapHeight-24)//��ͼ�߽粻���ߵ��㷨
			{
				LoadGame.loadimagesprite.spHero.setPosition(HeroX, HeroY);
				LoadGame.loadimagesprite.spHeroBody.setPosition(HeroX, HeroY+23);
				mapx += 0;
				mapy += 0;
				return;
			}
			if(LoadGame.loadimagesprite.spHeroBody.getX()>=100&&LoadGame.loadimagesprite.spHeroBody.getX()<=116&&
					LoadGame.loadimagesprite.spHeroBody.getY()>=171&&LoadGame.loadimagesprite.spHeroBody.getY()<=180)
			{
				isShop = true;
				GAME_STATE = GAME_SHOP;
				loadshop = new LoadShop(false,this);
			}	
				else if(LoadGame.loadimagesprite.spHeroBody.getX()>=556&&LoadGame.loadimagesprite.spHeroBody.getX()<=580&&
						LoadGame.loadimagesprite.spHeroBody.getY()>=147&&LoadGame.loadimagesprite.spHeroBody.getY()<=150)
						{
							isHospital = true;
							GAME_STATE = GAME_SHOP;
							loadshop = new LoadShop(false,this);
						}else if(LoadGame.loadimagesprite.spHeroBody.getX()>=700&&LoadGame.loadimagesprite.spHeroBody.getX()<=724&&
						LoadGame.loadimagesprite.spHeroBody.getY()>=176&&LoadGame.loadimagesprite.spHeroBody.getY()<=180)
						{
							isVino = true;
							GAME_STATE = GAME_SHOP;
							loadshop = new LoadShop(false,this);
						}
			if(!isRun)
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.loadimagesprite.sFeng, true))
			{
				GAME_STATE = GAME_FIGHT;
				fight = new Fight(false,this);
			}
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.loadimagesprite.spNPC[1], true))
			{
				isNPC = true;
				NPC[0] = true;
			}else		//�����뿪����ײ�ͷ���ԭ��ֵ
			{
				isNPC = false;
				NPC[0] = false;
			if(LoadGame.loadimagesprite.spHeroBody.collidesWith(LoadGame.loadimagesprite.spNPC[2], true))
			{
				isNPC = true;
				NPC[1] = true;
			}else		//�����뿪����ײ�ͷ���ԭ��ֵ
			{
				isNPC = false;
				NPC[1] = false;
			}
		}
			int cx = HeroX;
	    	int cy = HeroY;
	    	if (cx < this.vwX + BORDER&&cx>=BORDER) {
	    		vwX -= 8;  //���ֵ��ú��ƶ���ֵһ��,��Ȼ����!    ��
	    		LoadGame.LayerM.setViewWindow(vwX, vwY, LoadGame.width, LoadGame.height);
	    	} else if (cx > this.vwX + LoadGame.width - BORDER 
	    			&& vwX + LoadGame.width<=LoadGame.TBg.getWidth()-2) {
	    		vwX += 8;  //��Ϊ����̫��      ��
	    		LoadGame.LayerM.setViewWindow(vwX, vwY, LoadGame.width, LoadGame.height);
	    	}

	    	if (cy < this.vwY + BORDER && vwY>=5) {
	    		vwY -= 8;   //��
	    		LoadGame.LayerM.setViewWindow(vwX, vwY, LoadGame.width, LoadGame.height);
	    	} else if (cy > this.vwY + LoadGame.height - BORDER
	    			&& vwY + LoadGame.height+6<=LoadGame.TBg.getHeight()) {
	    		vwY += 8;   //��
	    		LoadGame.LayerM.setViewWindow(vwX, vwY, LoadGame.width, LoadGame.height);
	    	}  
			HeroX = x;
			HeroY = y;
			mapx = mapX2;
			mapy = mapY2;
	}

	public void HeroDown() {
		// TODO Auto-generated method stub
		LoadGame.loadimagesprite.spHero.setFrameSequence(LoadGame.loadimagesprite.SequenceDown);
		isDown = false;
		isUp = true;
		isLeft = true;
		isRight = true;
	}

	public void HeroUp() {
		// TODO Auto-generated method stub
		LoadGame.loadimagesprite.spHero.setFrameSequence(LoadGame.loadimagesprite.SequenceUp);
		isUp = false;
		isDown = true;
		isLeft = true;
		isRight = true;
	}

	public void HeroRight() {
		// TODO Auto-generated method stub
		LoadGame.loadimagesprite.spHero.setFrameSequence(LoadGame.loadimagesprite.SequenceRight);
		isRight = false;
		isDown = true;
		isUp = true;
		isLeft = true;
	}

	public void HeroLeft() {
		// TODO Auto-generated method stub
		LoadGame.loadimagesprite.spHero.setFrameSequence(LoadGame.loadimagesprite.SequenceLeft);
		isLeft = false;
		isRight = true;
		isDown = true;
		isUp = true;
	}
	
	protected void keyPressed(int KeyCode)
	{
		if(GAME_STATE == GAME_DUIHUA)	//����NPC�Ի�ѡ��ȡ��
		{
			if(getGameAction(KeyCode)==8||getKeyName(KeyCode).equals("SOFT2"))
			{
				GAME_STATE = GAME_NEW;
				NPC[0] = false;
			}
		}else if(GAME_STATE == GAME_MENU)  //������Ϸ�˵�ʱ��Ч
		{
			if((getKeyStates() & UP_PRESSED) != 0)
			{
				if(MENU_STATE==GAME_NEW)
					MENU_STATE = GAME_EXIT;
				else
					MENU_STATE -= 1;
			}else{
				if((getKeyStates() & DOWN_PRESSED) != 0)
				{
					if(MENU_STATE==GAME_EXIT)
						MENU_STATE = GAME_NEW;
					else
						MENU_STATE += 1;
				}else if((getKeyStates() & FIRE_PRESSED) != 0)
				{
					if(MENU_STATE==GAME_NEW)
						GAME_STATE = GAME_NEW;
					else
						if(MENU_STATE==GAME_LOAD)
							GAME_STATE = GAME_LOAD;
						else
							if(MENU_STATE==GAME_SET)
								GAME_STATE = GAME_SET;
							else
								if(MENU_STATE==GAME_HELP)
									GAME_STATE = GAME_HELP;
								else
										GAME_STATE = GAME_EXIT;
				}
			}
		}else if(GAME_STATE==GAME_NEW)		//������Ϸʱ����Ч
		{
			if(getGameAction(KeyCode)==8&&isNPC)
			{
				GAME_STATE = GAME_DUIHUA;
				isNPC = false;
			}
			
			if(getKeyName(KeyCode).equals("SOFT1"))
				GAME_STATE = GAME_MENU;
			else if(getKeyName(KeyCode).equals("SOFT2"))
				GAME_STATE = GAME_MENU;
			else if(getGameAction(KeyCode)==8&&isDiaLogOver)
			{
				str = LoadGame.str2;
				isDiaLogOver = false;
			}else if(getGameAction(KeyCode)==8&&!isDiaLogOver)
				isDiaLog = false;
		}else if(GAME_STATE==GAME_SET)
		{
			if(getKeyName(KeyCode).equals("SOFT1"))
				GAME_STATE = GAME_MENU;
		}else if(GAME_STATE==GAME_HELP)
		{
			if(getKeyName(KeyCode).equals("SOFT1"))
				GAME_STATE = GAME_MENU;
			if(getKeyName(KeyCode).equals("Up")||getGameAction(KeyCode)==1)
				helpy+=6;
			if(getKeyName(KeyCode).equals("Down")||getGameAction(KeyCode)==6)
				helpy-=6;
			repaint(10,30,220,270);
		}
	}
	public void input()
	{
		if(GAME_STATE==GAME_NEW)		//������Ϸʱ��Ч
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
	}
	
	public void ShowDialog(String str)
	{
		g.drawImage(LoadGame.loadimagesprite.imgDiaLogBg, 0, 248, 0);
		g.setColor(0xffffff);
		g.setFont(LoadGame.font);
		//0 ��ʼλ�ã�20������峤��
//		if(LoadGame.font.stringWidth(str)>=220&&LoadGame.font.stringWidth(str)<=440)//E2�������14��,�ֻ�ģ������18��Ϊ��
//		{
//			g.drawSubstring(str, 0, 14, 10, 256, 0);
//			g.drawSubstring(str, 14, str.length()-14, 10, 276, 0);
//		}else
//		{
//			g.drawSubstring(str, 0, 14, 10, 256, 0);
//			g.drawSubstring(str, 14,14, 10, 276, 0);
//			g.drawSubstring(str, 28, str.length()-28, 10, 296, 0);
//		}
		if(LoadGame.font.stringWidth(str)>=220&&LoadGame.font.stringWidth(str)<=440)//�ʺ��ֻ���ͯ������
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
}
