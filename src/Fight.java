import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
//Download by http://www.codefans.net
public class Fight extends GameCanvas
    implements Runnable
{
	RPGGameCanvas rpgc = null;
    public static Thread FightThread = null;
    public static boolean isOver = true;
    private boolean isAttack;
    private boolean isMagicAttack;
    private boolean isMagicAttackState;
    private boolean isMagicState;
    private boolean isProp;
    private boolean isRecovery;
    private boolean isBuy;
    private boolean bGongFu[] = new boolean[4]; 
    private boolean isSequece;
    private boolean isMDie[] = new boolean[3];
    private boolean isMDied[] = new boolean[3];
    private boolean PlayMDiedOver[] = new boolean[3];	//死亡效果播放完毕
    private boolean isFightOver;
    private boolean FightOver;	//人物打完一个回合
    private boolean MFightOver[] = new boolean[3];	//怪物打完一个回合
    private boolean MFighting[] = new boolean[3];
    private boolean FightCanvasOver;
    public Graphics g;
    private short STATE;	//攻击选择（游戏主线程switch选择）
    private final short ATTACK = 1;
    private final short MAGICATTACK = 2;
    private final short PROP = 3;
    private final short RECOVERY = 4;
    private final short BUY = 5;
    private final short FIGHT_OVER = 6;
    private final short MONSTER_FIGHT = 7;
    private short isState;		//游戏战斗选择如状态
    private short MagicState = 1;	//游戏魔法选择
    short i = 0;
    short i2 = 0;
    short i3 = 0;
    short j = 0;
    short step = 0;
    private short Monster_State = 0;
    private final short Monster1 = 1;
    private final short Monster2 = 2;
    private final short Monster3 = 3;
    
    private String GongFu[] = new String[4];
    private LayerManager lm = new LayerManager();
    protected Fight(boolean arg0,RPGGameCanvas rpgc)
    {
        super(arg0);
    	RPGGameCanvas.mhp[0] = 100;
    	RPGGameCanvas.mhp[1] = 100;
    	RPGGameCanvas.mhp[2] = 100;
        this.rpgc = rpgc;
        GongFu[0] = "黯然销魂";
        GongFu[1] = "万剑归宗";
        GongFu[2] = "如来神掌";
        GongFu[3] = "鸿哥群秒";
        g = getGraphics();
        STATE = 0;
        isState = 1;
        setFullScreenMode(true);
        FightThread = new Thread(this);
        FightThread.start();
    }
    
    public void run()
    {
        while(isOver) 
        {
            switch(STATE)
            {
            case ATTACK: // '\001'
            	Hero_Attack();
            	Select();
            	lm.remove(LoadGame.loadimagesprite.spfHero);
            	isAttack = false;	//打完还原
            	STATE = 0;
            	isState = 1;
                break;
            case MAGICATTACK: // '\002'
            	Hero_MagicAttack();
            	Select();
            	MagicState = 1;
            	isMagicState = false;
            	isMagicAttackState = false;	//选择怪物攻击
            	STATE = 0;
            	isState = 1;
            	isMagicAttack = false;
                break;
            case PROP: // '\003'
            	isProp = true;
                break;
            case RECOVERY: // '\004'
            	isRecovery = true;
                break;
            case BUY: // '\005'
            	isBuy = true;
                break;
            case FIGHT_OVER:
            	FightOver();
            	if(LoadGame.isMusic){
            		if(LoadGame.loadmusic.play.getState() == 300){
            		LoadGame.loadmusic.play.close();
            		LoadGame.loadmusic.PlayBg_1();
            		}
            	}
            	break;
            case MONSTER_FIGHT:
            	MonsterAttack();
            	break;
            default:
			initFight();
            Select();
            if(isMDied[0]&&isMDied[1]&&isMDied[2]&&LoadGame.loadimagesprite.sMonsterDied[0].getFrame()==0){
            	STATE = FIGHT_OVER;
            	if(LoadGame.isMusic){
            	LoadGame.loadmusic.play.close();
            	LoadGame.loadmusic.PlayShengLi();
            	}
            }
            if(FightOver){
            	STATE = MONSTER_FIGHT;
            }
        	if(isMDied[0]&&!PlayMDiedOver[0]){
               	LoadGame.loadimagesprite.sMonsterDied[0].setPosition(26, 88);
            	lm.append(LoadGame.loadimagesprite.sMonsterDied[0]);
            	LoadGame.loadimagesprite.sMonsterDied[0].nextFrame();	
                if(LoadGame.loadimagesprite.sMonsterDied[0].getFrame()==2)
                {
                	lm.remove(LoadGame.loadimagesprite.sMonsterDied[0]);
                	LoadGame.loadimagesprite.sMonsterDied[0].setFrame(0);
            		PlayMDiedOver[0] = true; 
                }
        	}
        	if(isMDied[1]&&!PlayMDiedOver[1]){
            	LoadGame.loadimagesprite.sMonsterDied[1].setPosition(44, 120);
            	lm.append(LoadGame.loadimagesprite.sMonsterDied[1]);
            	LoadGame.loadimagesprite.sMonsterDied[1].nextFrame();	
                if(LoadGame.loadimagesprite.sMonsterDied[1].getFrame()==2)
                {
                	lm.remove(LoadGame.loadimagesprite.sMonsterDied[1]);
                	LoadGame.loadimagesprite.sMonsterDied[0].setFrame(0);
                	PlayMDiedOver[1] = true;
                }
        	}
        	if(isMDied[2]&&!PlayMDiedOver[2]){
                LoadGame.loadimagesprite.sMonsterDied[2].setPosition(26, 160);
            	lm.append(LoadGame.loadimagesprite.sMonsterDied[2]);
            	LoadGame.loadimagesprite.sMonsterDied[2].nextFrame();	
                if(LoadGame.loadimagesprite.sMonsterDied[2].getFrame()==2)
                {
                	lm.remove(LoadGame.loadimagesprite.sMonsterDied[2]);
                	LoadGame.loadimagesprite.sMonsterDied[0].setFrame(0);
                	PlayMDiedOver[2] = true;
                }
        	}
            if(isMDie[0]&&isMDie[1]&&isMDie[2]){
        		isMDie[0] = false;
        		isMDie[1] = false;
        		isMDie[2] = false;
            	if(isFightOver){
            		if(bGongFu[0])
                	bGongFu[0] = false;
            		if(bGongFu[1])
                	bGongFu[1] = false;
            		if(bGongFu[2])
                	bGongFu[2] = false;
            		if(bGongFu[3]) 
                	bGongFu[3] = false;
            	}
            }else if(isMDie[0]){
            	isMDie[0] = false;
            	FightOver = true;
            	if(isFightOver){
            		if(bGongFu[0])
                	bGongFu[0] = false;
            		else if(bGongFu[1])
                	bGongFu[1] = false;
            		else if(bGongFu[2])
                	bGongFu[2] = false;
            		else if(bGongFu[3]) 
                	bGongFu[3] = false;
            	}
            }else if(isMDie[1]){
            	isMDie[1] = false;
            	FightOver = true;
            	if(isFightOver){
            		if(bGongFu[0])
                	bGongFu[0] = false;
            		else if(bGongFu[1])
                	bGongFu[1] = false;
            		else if(bGongFu[2])
                	bGongFu[2] = false;
            		else if(bGongFu[3]) 
                	bGongFu[3] = false;
            	}
            }else if(isMDie[2]){
            	isMDie[2] = false;
            	FightOver = true;
            	if(isFightOver){
            		if(bGongFu[0])
                	bGongFu[0] = false;
            		else if(bGongFu[1])
                	bGongFu[1] = false;
            		else if(bGongFu[2])
                	bGongFu[2] = false;
            		else if(bGongFu[3]) 
                	bGongFu[3] = false;
            	}
            }
            
            if(isAttack||isMagicAttackState)
            {
            	if(j==3)
            		j = 0;
            	j++;
            }
            
            if(isFightOver)
            {
            	if(bGongFu[3]){
            		i = 3;
            		i2 = 3;
            		i3 = 3;
            		RPGGameCanvas.mhp[0] -= RPGGameCanvas.hattack;
            		RPGGameCanvas.mhp[1] -= RPGGameCanvas.hattack;
            		RPGGameCanvas.mhp[2] -= RPGGameCanvas.hattack;
            		if(RPGGameCanvas.mhp[0]<=0){
            			isMDied[0] = true;
            		}
                		if(RPGGameCanvas.mhp[1]<=0){
                			isMDied[1] = true;
                		}
                    		if(RPGGameCanvas.mhp[2]<=0){
                    			isMDied[2] = true;
                    		}
                    Monster_HP((short)26,(short)88, (short) RPGGameCanvas.hattack);
                    isMDie[0] = true;
            		Monster_HP((short)44,(short)120, (short) RPGGameCanvas.hattack);
            		isMDie[1] = true;
            		Monster_HP((short)26,(short)160, (short) RPGGameCanvas.hattack);
            		isMDie[2] = true;
            		MFightOver[0] = false;//人物攻击时怪物攻击是在假的状态 还原怪物打斗
            		MFightOver[1] = false;
            		MFightOver[2] = false;
            	}else if(Monster_State == Monster1){
            		i = 3;
            		RPGGameCanvas.mhp[0] -= RPGGameCanvas.hattack;
            		Monster_HP((short)26,(short)88, (short) RPGGameCanvas.hattack);
            		isMDie[0] = true;
            		MFightOver[0] = false;//人物攻击时怪物攻击是在假的状态 还原怪物打斗
            		MFightOver[1] = false;
            		MFightOver[2] = false;
            		if(RPGGameCanvas.mhp[0]<=0){
            			isMDied[0] = true;
            		}
            	}else if(Monster_State == Monster2){
            		i2 = 3;
            		RPGGameCanvas.mhp[1] -= RPGGameCanvas.hattack;
            		Monster_HP((short)44,(short)120, (short) RPGGameCanvas.hattack);
            		isMDie[1] = true;
            		MFightOver[0] = false;//人物攻击时怪物攻击是在假的状态 还原怪物打斗
            		MFightOver[1] = false;
            		MFightOver[2] = false;
            		if(RPGGameCanvas.mhp[1]<=0){
            			isMDied[1] = true;
            		}
            	}else if(Monster_State == Monster3){
            		i3 = 3;
            		RPGGameCanvas.mhp[2] -= RPGGameCanvas.hattack;
            		Monster_HP((short)26,(short)160, (short) RPGGameCanvas.hattack);
            		isMDie[2] = true;
            		MFightOver[0] = false;//人物攻击时怪物攻击是在假的状态 还原怪物打斗
            		MFightOver[1] = false;
            		MFightOver[2] = false;
            		RPGGameCanvas.hmp -= 10;
            		if(RPGGameCanvas.mhp[2]<=0){
            			isMDied[2] = true;
            		}
            	}
            	repaint();
            	try {
        			Thread.sleep(240);
        		} catch (InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
            repaint();
            lm.paint(g, 0, 0);
            
            LoadGame.loadimagesprite.spfHero.nextFrame();
            if(isSequece)
            {
            	if(LoadGame.loadimagesprite.spattackHero.getFrame()==4)
            	{
            		lm.remove(LoadGame.loadimagesprite.spattackHero);
            		LoadGame.loadimagesprite.spattackHero.setFrame(0);
            		isSequece = false;
            		isFightOver = true;
            	}
            	LoadGame.loadimagesprite.spattackHero.nextFrame();
            	if(LoadGame.loadimagesprite.spMagic[0].getFrame()==4)
            	{
            		if(bGongFu[0])
            			lm.remove(LoadGame.loadimagesprite.spMagic[0]);
            		else if(bGongFu[1])
            			lm.remove(LoadGame.loadimagesprite.spMagic[1]);
            		else if(bGongFu[2])
            			lm.remove(LoadGame.loadimagesprite.spMagic[2]);
            		else if(bGongFu[3]){
            			lm.remove(LoadGame.loadimagesprite.spMagic[3]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[4]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[5]);
            		}
            		isFightOver = true;
            	}else if(LoadGame.loadimagesprite.spMagic[1].getFrame()==3){
            		if(bGongFu[0])
            			lm.remove(LoadGame.loadimagesprite.spMagic[0]);
            		else if(bGongFu[1])
            			lm.remove(LoadGame.loadimagesprite.spMagic[1]);
            		else if(bGongFu[2])
            			lm.remove(LoadGame.loadimagesprite.spMagic[2]);
            		else if(bGongFu[3]){
            			lm.remove(LoadGame.loadimagesprite.spMagic[3]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[4]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[5]);
            		}
            		isFightOver = true;
            	}else if(LoadGame.loadimagesprite.spMagic[2].getFrame()==3){
            		if(bGongFu[0])
            			lm.remove(LoadGame.loadimagesprite.spMagic[0]);
            		else if(bGongFu[1])
            			lm.remove(LoadGame.loadimagesprite.spMagic[1]);
            		else if(bGongFu[2])
            			lm.remove(LoadGame.loadimagesprite.spMagic[2]);
            		else if(bGongFu[3]){
            			lm.remove(LoadGame.loadimagesprite.spMagic[3]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[4]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[5]);
            		}
            		isFightOver = true;
            	}else if(LoadGame.loadimagesprite.spMagic[3].getFrame()==3){
            		if(bGongFu[0])
            			lm.remove(LoadGame.loadimagesprite.spMagic[0]);
            		else if(bGongFu[1])
            			lm.remove(LoadGame.loadimagesprite.spMagic[1]);
            		else if(bGongFu[2])
            			lm.remove(LoadGame.loadimagesprite.spMagic[2]);
            		else if(bGongFu[3]){
            			lm.remove(LoadGame.loadimagesprite.spMagic[3]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[4]);
            			lm.remove(LoadGame.loadimagesprite.spMagic[5]);
            		}
            		isFightOver = true;
            	}
            	
            	if(bGongFu[0])
            	LoadGame.loadimagesprite.spMagic[0].nextFrame();
            	if(bGongFu[1])
            	LoadGame.loadimagesprite.spMagic[1].nextFrame();
            	if(bGongFu[2])
            	LoadGame.loadimagesprite.spMagic[2].nextFrame();
            	if(bGongFu[3]){
            		LoadGame.loadimagesprite.spMagic[3].nextFrame();
            		LoadGame.loadimagesprite.spMagic[4].nextFrame();
            		LoadGame.loadimagesprite.spMagic[5].nextFrame();
            	}
            }
                break;
            }
//            System.out.println("1:"+isMDied[0]);
//            System.out.println("2:"+isMDied[1]);
//            System.out.println("3:"+isMDied[2]);
            serviceRepaints();
            try
            {
                Thread.sleep(60L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void FightOver() {
		// TODO Auto-generated method stub
		g.drawImage(LoadGame.loadimagesprite.imgShengLiBg, 0, 0, 0);
		g.drawImage(LoadGame.loadimagesprite.imgtouxiang, (LoadGame.width-LoadGame.loadimagesprite.imgtouxiang.getWidth())/2, (LoadGame.height-3*LoadGame.loadimagesprite.imgtouxiang.getHeight())/2, 0);
		g.setColor(0xff0000);
		g.setFont(LoadGame.font);
		g.drawString("战斗胜利", (LoadGame.width-LoadGame.font.stringWidth("战斗胜利"))/2,
				(LoadGame.height-LoadGame.font.getHeight())/2, 0);
		g.setFont(LoadGame.font);
		g.setColor(0xffffff);
		g.drawString("获得经验100", (LoadGame.width-LoadGame.font.stringWidth("获得经验100"))/2, (LoadGame.height-LoadGame.font.getHeight())/2+LoadGame.font.getHeight()+2, 0);
		g.drawString("获得金钱100", (LoadGame.width-LoadGame.font.stringWidth("获得金钱100"))/2, (LoadGame.height+LoadGame.font.getHeight()+LoadGame.font.getHeight())/2+12, 0);
		flushGraphics();
		FightCanvasOver = true;
	}

	private void Select()
    {
        if(isState != 1)
            g.drawImage(LoadGame.loadimagesprite.imgAttack, 51, 224, 0);
        else
            g.drawImage(LoadGame.loadimagesprite.imgAttack_y, 51, 224, 0);
        if(isState != 2)
            g.drawImage(LoadGame.loadimagesprite.imgMagicAttack, 79, 224, 0);
        else
            g.drawImage(LoadGame.loadimagesprite.imgMagicAttack_y, 79, 224, 0);
        if(isState != 3)
            g.drawImage(LoadGame.loadimagesprite.imgProp, 107, 224, 0);
        else
            g.drawImage(LoadGame.loadimagesprite.imgProp_y, 107, 224, 0);
        if(isState != 4)
            g.drawImage(LoadGame.loadimagesprite.imgRecovery, 135, 224, 0);
        else
            g.drawImage(LoadGame.loadimagesprite.imgRecovery_y, 135, 224, 0);
        if(isState != 5)
            g.drawImage(LoadGame.loadimagesprite.imgBuy, 163, 224, 0);
        else
            g.drawImage(LoadGame.loadimagesprite.imgBuy_y, 163, 224, 0);
    }

    public void initFight()//初始化打斗场面
    {
    	i = 0;
    	i2 = 0;
    	i3 = 0;
        g.drawImage(LoadGame.loadimagesprite.imgBackgroup, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgLeft, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgRight, 120, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDiaLogBg, 0, 248, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDi, 204, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgQiu, 209, 3, 0);
        
        g.drawImage(LoadGame.loadimagesprite.imgBorderLeft, 35, 221, 0);
        for(short i= 51;i<188;i+=2)
        g.drawImage(LoadGame.loadimagesprite.imgBorderBg, i, 222, 0);
        g.drawImage(LoadGame.loadimagesprite.imgBorderRight, 188, 221, 0);
        if(!isSequece)
        	lm.append(LoadGame.loadimagesprite.spfHero);
            
        if(!isFightOver){//怪物攻击时不能显示和死亡不能显示
        	if(!isMDie[0]&&!isMDied[0]&&!MFighting[0]){
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[0], 20, 100, 0);
        	}
        	if(!isMDie[1]&&!isMDied[1]&&!MFighting[1]){
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[1], 30, 135, 0);
        	}
        	if(!isMDie[2]&&!isMDied[2]&&!MFighting[2]){
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[2], 20,175, 0);
        	}
        }

        if(!isMagicState){
        g.drawImage(LoadGame.loadimagesprite.imgHeroFace, 20, 255, 0);
        g.setColor(0x000000);
        g.fillRect(20, 291, 40, 5);//生命条
        g.fillRect(20, 306, 40, 5);//魔法条
        g.setColor(0x0000ff);
        g.fillRect(21, 307, RPGGameCanvas.hmp*38/100, 3);
        g.setColor(0xff0000);
        g.fillRect(21, 292, RPGGameCanvas.hhp*38/100, 3);
        g.setColor(0xffffff);
        g.setFont(LoadGame.font);
        g.drawString(RPGGameCanvas.hhp+"/100", 20, 279, 0);
        g.drawString(RPGGameCanvas.hmp+"/100", 20, 294, 0);
        }

        if(isAttack||isMagicAttackState)	//普通攻击选择和魔法攻击选择
        {
        	g.setColor(0xffffff);
        	if(bGongFu[3]&&!isMDied[0]&&!isMDied[1]&&!isMDied[2])
        	{
        		g.fillTriangle(30,96-j,34,96-j,32,98-j);
        		g.fillTriangle(46,131-j,50,131-j,48,133-j);
        		g.fillTriangle(30,171-j,34,171-j,32,173-j);
        	}else if(Monster_State==1&&!isMDied[0])
        		g.fillTriangle(30,96-j,34,96-j,32,98-j);
        	else if(Monster_State==2&&!isMDied[1])
                g.fillTriangle(46,131-j,50,131-j,48,133-j);
        	else if(Monster_State==3&&!isMDied[2])
        		g.fillTriangle(30,171-j,34,171-j,32,173-j);
        }
        if(isMagicState)	//魔法选择
        {
        	g.setFont(LoadGame.font);
        	if(MagicState==1)
        		g.setColor(0xff0000);
        	else
        		g.setColor(0xffffff);
        	g.drawString(GongFu[0], 30, 260, 0);
        	if(MagicState==2)
        		g.setColor(0xff0000);
        	else
        		g.setColor(0xffffff);
        	g.drawString(GongFu[1], LoadGame.width-LoadGame.font.stringWidth(GongFu[1])-30, 260, 0);
        	if(MagicState==3)
        		g.setColor(0xff0000);
        	else
        		g.setColor(0xffffff);
        	g.drawString(GongFu[2], 30, 260+LoadGame.font.getHeight()*2, 0);
        	if(MagicState==4)
        		g.setColor(0xff0000);
        	else
        		g.setColor(0xffffff);
        	g.drawString(GongFu[3], LoadGame.width-LoadGame.font.stringWidth(GongFu[3])-30, 260+LoadGame.font.getHeight()*2, 0);
        }
        try {
			Thread.sleep(180);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	if(isProp){
    		g.setColor(0x0000ff);
    		g.fillRect(20, 40, 100, 120);
    		g.setColor(0xffffff);
    		g.setFont(LoadGame.font);
    		g.drawString("正在开发中...", (LoadGame.width-LoadGame.font.stringWidth("正在开发中..."))/2, (LoadGame.height-LoadGame.font.getHeight())/2, 0);
    		repaint();
    	}else if(isRecovery){
    		g.setColor(0x0000ff);
    		g.fillRect(20, 40, 100, 120);
    		g.setColor(0xffffff);
    		g.setFont(LoadGame.font);
    		g.drawString("正在开发中...", (LoadGame.width-LoadGame.font.stringWidth("正在开发中..."))/2, (LoadGame.height-LoadGame.font.getHeight())/2, 0);
    		repaint();
    	}else if(isBuy){
    		g.setColor(0x0000ff);
    		g.fillRect(20, 40, 100, 120);
    		g.setColor(0xffffff);
    		g.setFont(LoadGame.font);
    		g.drawString("正在开发中...", (LoadGame.width-LoadGame.font.stringWidth("正在开发中..."))/2, (LoadGame.height-LoadGame.font.getHeight())/2, 0);
    		repaint();
    	}
    }
    
    public void Hero_Attack()
    {
        g.drawImage(LoadGame.loadimagesprite.imgBackgroup, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgLeft, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgRight, 120, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDiaLogBg, 0, 248, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDi, 204, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgQiu, 209, 3, 0);
        
        g.drawImage(LoadGame.loadimagesprite.imgBorderLeft, 35, 221, 0);
        for(short i= 51;i<188;i+=2)
            g.drawImage(LoadGame.loadimagesprite.imgBorderBg, i, 222, 0);
        g.drawImage(LoadGame.loadimagesprite.imgBorderRight, 188, 221, 0);

        g.drawImage(LoadGame.loadimagesprite.imgBorderBg, 51, 222, 0);
        
    	if(Monster_State == Monster1&&!isMDied[0])
    	{
    		lm.append(LoadGame.loadimagesprite.spattackHero);
    		lm.remove(LoadGame.loadimagesprite.spfHero);
    		LoadGame.loadimagesprite.spattackHero.setPosition(40, 80);
    		isSequece = true;
    	}else if(Monster_State == Monster2&&!isMDied[1])
    	{
    		lm.append(LoadGame.loadimagesprite.spattackHero);
    		lm.remove(LoadGame.loadimagesprite.spfHero);
    		LoadGame.loadimagesprite.spattackHero.setPosition(60, 120);
    		isSequece = true;
    	}else if(Monster_State == Monster3&&!isMDied[2]){
    		lm.append(LoadGame.loadimagesprite.spattackHero);
    		lm.remove(LoadGame.loadimagesprite.spfHero);
    		LoadGame.loadimagesprite.spattackHero.setPosition(40, 156);
    		isSequece = true;
    	}
    }
    
    public void Hero_MagicAttack()
    {
        g.drawImage(LoadGame.loadimagesprite.imgBackgroup, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgLeft, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgRight, 120, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDiaLogBg, 0, 248, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDi, 204, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgQiu, 209, 3, 0);
        
        g.drawImage(LoadGame.loadimagesprite.imgBorderLeft, 35, 221, 0);
        for(short i= 51;i<188;i+=2)
            g.drawImage(LoadGame.loadimagesprite.imgBorderBg, i, 222, 0);
        g.drawImage(LoadGame.loadimagesprite.imgBorderRight, 188, 221, 0);

        g.drawImage(LoadGame.loadimagesprite.imgBorderBg, 51, 222, 0);

    	if(Monster_State == Monster1&&!isMDied[0])
    	{
    		if(bGongFu[0]){
    			LoadGame.loadimagesprite.spMagic[0].setPosition(10, 80);
    			lm.append(LoadGame.loadimagesprite.spMagic[0]);
    			RPGGameCanvas.hmp -= 10;
    		}
    		else if(bGongFu[1]){
    			LoadGame.loadimagesprite.spMagic[1].setPosition(10, 80);
    			lm.append(LoadGame.loadimagesprite.spMagic[1]);
    			RPGGameCanvas.hmp -= 10;
    		}
    		else if(bGongFu[2]){
    			LoadGame.loadimagesprite.spMagic[2].setPosition(10, 80);
    			lm.append(LoadGame.loadimagesprite.spMagic[2]);
    			RPGGameCanvas.hmp -= 10;
    		}
    		else if(bGongFu[3]){
    			LoadGame.loadimagesprite.spMagic[3].setPosition(10, 80);
    			lm.append(LoadGame.loadimagesprite.spMagic[3]);
    			LoadGame.loadimagesprite.spMagic[4].setPosition(20, 120);
    			lm.append(LoadGame.loadimagesprite.spMagic[4]);
    			LoadGame.loadimagesprite.spMagic[5].setPosition(10, 156);
    			lm.append(LoadGame.loadimagesprite.spMagic[5]);
    			RPGGameCanvas.hmp -= 20;
    		}
    		isSequece = true;
    	}else if(Monster_State == Monster2&&!isMDied[1])
    	{
    		if(bGongFu[0]){
    			LoadGame.loadimagesprite.spMagic[0].setPosition(20, 120);
    			lm.append(LoadGame.loadimagesprite.spMagic[0]);
    			RPGGameCanvas.hmp -= 10;
    		}
    			
    		else if(bGongFu[1]){
    			LoadGame.loadimagesprite.spMagic[1].setPosition(20, 120);
    			lm.append(LoadGame.loadimagesprite.spMagic[1]);
    			RPGGameCanvas.hmp -= 10;
    		}
    			
    		else if(bGongFu[2]){
    			LoadGame.loadimagesprite.spMagic[2].setPosition(20, 120);
    			lm.append(LoadGame.loadimagesprite.spMagic[2]);
    			RPGGameCanvas.hmp -= 10;
    		}
    			
    		else if(bGongFu[3]){
    			LoadGame.loadimagesprite.spMagic[3].setPosition(10, 80);
    			lm.append(LoadGame.loadimagesprite.spMagic[3]);
    			LoadGame.loadimagesprite.spMagic[4].setPosition(20, 120);
    			lm.append(LoadGame.loadimagesprite.spMagic[4]);
    			LoadGame.loadimagesprite.spMagic[5].setPosition(10, 156);
    			lm.append(LoadGame.loadimagesprite.spMagic[5]);
    			RPGGameCanvas.hmp -= 20;
    		}
    		
    		isSequece = true;
    	}else if(Monster_State == Monster3&&!isMDied[2]){
    		if(bGongFu[0]){
    			LoadGame.loadimagesprite.spMagic[0].setPosition(10, 156);
    			lm.append(LoadGame.loadimagesprite.spMagic[0]);
    			RPGGameCanvas.hmp -= 10;
    		}
    			
    		else if(bGongFu[1]){
    			LoadGame.loadimagesprite.spMagic[1].setPosition(10, 156);
    			lm.append(LoadGame.loadimagesprite.spMagic[1]);
    			RPGGameCanvas.hmp -= 10;
    		}
    			
    		else if(bGongFu[2]){
    			LoadGame.loadimagesprite.spMagic[2].setPosition(10, 156);
    			lm.append(LoadGame.loadimagesprite.spMagic[2]);
    			RPGGameCanvas.hmp -= 10;
    		}
    			
    		else if(bGongFu[3]){
    			LoadGame.loadimagesprite.spMagic[3].setPosition(10, 80);
    			lm.append(LoadGame.loadimagesprite.spMagic[3]);
    			LoadGame.loadimagesprite.spMagic[4].setPosition(20, 120);
    			lm.append(LoadGame.loadimagesprite.spMagic[4]);
    			LoadGame.loadimagesprite.spMagic[5].setPosition(10, 156);
    			lm.append(LoadGame.loadimagesprite.spMagic[5]);
    			RPGGameCanvas.hmp -= 20;
    		}
    			
    		isSequece = true;
    	}
    }

    private void Monster_HP(short x,short y,short HP) {
		// TODO Auto-generated method stub
        	g.setColor(0xffffff);
        	g.setFont(LoadGame.font);
        	g.drawString(""+RPGGameCanvas.hattack, x, y, 0);
        	g.setColor(0x000000);
        	g.drawString("敌人消耗"+RPGGameCanvas.hattack+"点生命", 10, 10, 0);
        	Select();
        	repaint();
        	isFightOver = false;
        	if(!isMDied[0])
            g.drawImage(LoadGame.loadimagesprite.imgcateran[0], 20-i, 100, 0);
        	if(!isMDied[1])
            g.drawImage(LoadGame.loadimagesprite.imgcateran[1], 30-i2, 135, 0);
        	if(!isMDied[2])
            g.drawImage(LoadGame.loadimagesprite.imgcateran[2], 20-i3,175, 0);
	}
    
    
    public void MonsterAttack(){
    	int i = 0;
    	switch(i){
    	case 0:
        	if(!isMDied[0]&&!MFightOver[0]){
        		step = 6;
        		MFighting[0] = true;
        		MonsterAttackEffect();
        		Select();
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[0], 170, 140, 0);
           	 	repaint();
           	 	MFightOver[0] = true;
           	 	MFighting[0] = false;
           	 	step = 0;
           	 	RPGGameCanvas.hhp -= RPGGameCanvas.mattack;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
				MonsterAttackEffect();
				Select();
				repaint();
				if(!isMDied[0])
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	case 1:
            	if(!isMDied[1]&&!MFightOver[1]){
            		step = 6;
            		MFighting[1] = true;
            		MonsterAttackEffect();
            		Select();
            		g.drawImage(LoadGame.loadimagesprite.imgcateran[1], 170, 140, 0);
               	 	repaint();
               	 	MFightOver[1] = true;
               	 	MFighting[1] = false;
               	 	step = 0;
               	 RPGGameCanvas.hhp -= RPGGameCanvas.mattack;
        			try {
    					Thread.sleep(200);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}
				MonsterAttackEffect();
				Select();
				repaint();
				if(!isMDied[1])
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	case 2:
    			if(!isMDied[2]&&!MFightOver[2]){
    				step = 6;
    				MFighting[2] = true;
    				MonsterAttackEffect();
    				Select();
            		g.drawImage(LoadGame.loadimagesprite.imgcateran[2], 170, 140, 0);
        			repaint();
    				MFightOver[2] = true;
    				MFighting[2] = false;
    				step = 0;
    				RPGGameCanvas.hhp -= RPGGameCanvas.mattack;
    				try {
    					Thread.sleep(200);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    	}
		MonsterAttackEffect();
		Select();
		repaint();
		if(!isMDied[2])
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	FightOver = false;
    	STATE = 0;
    }
    
    public void MonsterAttackEffect(){
        g.drawImage(LoadGame.loadimagesprite.imgBackgroup, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgLeft, 0, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgSelectBgRight, 120, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDiaLogBg, 0, 248, 0);
        g.drawImage(LoadGame.loadimagesprite.imgDi, 204, 0, 0);
        g.drawImage(LoadGame.loadimagesprite.imgQiu, 209, 3, 0);
        
        g.drawImage(LoadGame.loadimagesprite.imgBorderLeft, 35, 221, 0);
        for(short i= 51;i<188;i+=2)
        g.drawImage(LoadGame.loadimagesprite.imgBorderBg, i, 222, 0);
        g.drawImage(LoadGame.loadimagesprite.imgBorderRight, 188, 221, 0);
        
        LoadGame.loadimagesprite.spfHero.setPosition(180+step, 120);
        lm.append(LoadGame.loadimagesprite.spfHero);	//加个变量控制人物后退动作
        lm.paint(g, 0, 0);
            
        	if(!isMDie[0]&&!isMDied[0]&&!MFighting[0]){
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[0], 20, 100, 0);
        	}
        	if(!isMDie[1]&&!isMDied[1]&&!MFighting[1]){
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[1], 30, 135, 0);
        	}
        	if(!isMDie[2]&&!isMDied[2]&&!MFighting[2]){
        		g.drawImage(LoadGame.loadimagesprite.imgcateran[2], 20,175, 0);
        	}
        	
        if(!isMagicState){
            g.drawImage(LoadGame.loadimagesprite.imgHeroFace, 20, 255, 0);
            g.setColor(0x000000);
            g.fillRect(20, 291, 40, 5);
            g.fillRect(20, 306, 40, 5);
            g.setColor(0x0000ff);
            g.fillRect(21, 307, RPGGameCanvas.hmp*38/100, 3);
            g.setColor(0xff0000);
            g.fillRect(21, 292, RPGGameCanvas.hhp*38/100, 3);
            g.setColor(0xffffff);
            g.setFont(LoadGame.font);
            g.drawString(RPGGameCanvas.hhp+"/100", 20, 279, 0);
            g.drawString(RPGGameCanvas.hmp+"/100", 20, 294, 0);
            }
    }

	protected void keyPressed(int KeyCode)
    {
		if(FightCanvasOver){
			if(getKeyName(KeyCode).equals("SOFT1")||getGameAction(KeyCode) == 8)
            {
			RPGGameCanvas.isOver = true;
				RPGGameCanvas.GAME_STATE = RPGGameCanvas.GAME_NEW;
				LoadGame.LayerM.remove(LoadGame.loadimagesprite.sFeng);
				Midlet.display.setCurrent(rpgc);
				isOver = false;
            }
        else
        if(getKeyName(KeyCode).equals("SOFT2")){
        	System.out.println("ren wu zhuang tai");
        }
		}
		if(!isSequece)//打完收功 为避免重复按而重复打斗
		{ 
			if(getGameAction(KeyCode) == 8)
				isProp = false;
			else if(getGameAction(KeyCode) == 8)
				isRecovery = false;
			else if(getGameAction(KeyCode) == 8)
				isBuy = false;
				
	    	if(isMagicState&&!isMagicAttackState)	//魔法的选择  并且进入怪物选择是无效
			{
				if(getGameAction(KeyCode) == 2)
				{
					if(MagicState<=1)
						MagicState = 4;
					else
						MagicState -= 1;
				}else if(getGameAction(KeyCode) == 5)
				{
					if(MagicState>=4)
						MagicState = 1;
					else
						MagicState += 1;
				}
				if(getGameAction(KeyCode) == 8)
				{
					if(MagicState == 1){
						bGongFu[0] = true;
					}
					else if(MagicState == 2){
						bGongFu[1] = true;
					}
					else if(MagicState == 3){
						bGongFu[2] = true;
					}
					else if(MagicState == 4){
						bGongFu[3] = true;
					}
					isMagicAttackState = true;
				}
		}
	        
	        if(STATE == 0)	//在default中进行判断
	        {
	        	
	        	if(isAttack&&Monster_State==1&&getGameAction(KeyCode) == 8)
	        		STATE = ATTACK;
	        	else if(isAttack&&Monster_State==2&&getGameAction(KeyCode) == 8)
	        		STATE = ATTACK;
	        	else if(isAttack&&Monster_State==3&&getGameAction(KeyCode) == 8)
	        		STATE = ATTACK;
	        	
	        	if(isMagicAttack&&isMagicState&&isMagicAttackState&&Monster_State==1&&getGameAction(KeyCode) == 8)
	        		STATE = MAGICATTACK;
	        	else if(isMagicAttack&&isMagicState&&isMagicAttackState&&Monster_State==2&&getGameAction(KeyCode) == 8)
	        		STATE = MAGICATTACK;
	        	else if(isMagicAttack&&isMagicState&&isMagicAttackState&&Monster_State==3&&getGameAction(KeyCode) == 8)
	        		STATE = MAGICATTACK;
	        	
	        	if(getGameAction(KeyCode) == 2&&!isAttack&&!isMagicState)//进入攻击对象选择时按确定时进行菜单判断将无效
	            {
	                if(isState == 1)
	                    isState = 5;
	                else
	                    isState--;
	            } else if(getGameAction(KeyCode) == 5&&!isAttack&&!isMagicState)
	            {
	                if(isState == 5)
	                    isState = 1;
	                else
	                    isState++;
	            }else if(getGameAction(KeyCode) == 8&&!isAttack&&!isMagicState)	//按确定时进行菜单判断且在没有选择攻击时才有效
	            {
	            	if(isState == 1)
	            	{
	            		isAttack = true;
	            		Monster_State = 1;
	            	}
	            	else if(isState == 2)
	            	{
	            		isMagicState = true;
	            		Monster_State = 1;
	            	}
	            	else if(isState == 3)
	            		STATE = PROP;
	            	else if(isState == 4)
	            		STATE = RECOVERY;
	            	else if(isState == 5)
	            		STATE = BUY;
	            }
	        	if(isAttack||isMagicAttackState){
	        		if(getGameAction(KeyCode) == 1)//怪物的选择 1代表上
	        		{
	        			if(Monster_State<=1)
	        				Monster_State = 3;
	        			else 
	        				Monster_State -= 1;
	        		}else if(getGameAction(KeyCode) == 6)
	        		{
	        			if(Monster_State>=3)
	        				Monster_State = 1;
	        			else
	        				Monster_State += 1;
	        		}else {
	        			if(getGameAction(KeyCode) == 8)
	        				isMagicAttack = true;
	        		}
	        	}
	        }
		}
    }
}