import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
//Download by http://www.codefans.net

public class LoadImageSprite {
	//**************************开始游戏选择
	public Image confirm = null;
	public Image confirmyes = null;
	public Image confirmno = null;
	
	//**************************游戏选择菜单
	public Image imgMenu = null;
	public Image imgMenuB1 = null;
	public Image imgMenuB2 = null;
	public Image imgMenuB3 = null;
	public Image imgMenuB4 = null;
	public Image imgMenuB5 = null;
	//************************游戏怪
	public Image imgFeng = null;
	public Image imgcateran[] = new Image[10];
	public Image imgMonsterDied = null;
	
	//***********************游戏战斗界面
	public Image imgUi = null;
	public Image imgDi = null;
	public Image imgQiu = null;
	public Image imgBorderLeft = null;
	public Image imgBorderRight = null;
	public Image imgBorderBg = null;
	public Image imgbattleface = null;
	public Image imgHeroFace = null;
	public Image imgtouxiang = null;
	//******************************
	public Image imgUiwenzi = null;			//没有选中的文字
	public Image imgAttack = null;
	public Image imgMagicAttack = null;
	public Image imgProp = null;
	public Image imgRecovery = null;
	public Image imgBuy = null;
	//*****************************
	public Image imgUiwenzi_y = null;			//选中的文字
	public Image imgAttack_y = null;
	public Image imgMagicAttack_y = null;
	public Image imgProp_y = null;
	public Image imgRecovery_y = null;
	public Image imgBuy_y = null;
	
	//****************************//战斗界面上的人物
	public Image imgFightHero = null;
	public Image imgattackHero = null;
	public Image imgFightMagicAttack = null;	
	public Image imgMagic[] = new Image[3];
	public Image imgShengLiBg = null;
	//****************************//人物选择背景
	public Image imgDiaLogBg = null;
	public Image imgSelectBgLeft = null;	//人物选择背景
	public Image imgSelectBgRight = null;	//人物选择背景
	public Image imgBackgroup = null;
	
	public Image imgBg = null;
	public static Image imgShop = null;
	public Image imgDragon = null;
	public Image imgSanJianTou = null;
	public Image imgHelpDiaLog = null;
	//***************************//地图上小人
	public Image imgHero = null;
	public Image imgHeroHead = null;
	public Image imgHeroBody = null;
	
	//***************************//地图上的NPC  Image
	public Image imgNPC[] = new Image[10];	//整张图片NPC
	public Image imgNPC2[] = new Image[10];	//从整张图片取到的NPC
	//***************************//地图上的NPC  Sprite
	//**********************************//表情图片
	public Image imgBiaoQing = null; 
	public Image imgBQHeart = null;
	public Image imgBQAsk = null;
	public Image imgBQNo = null;
	public Image imgBQPoint[] = new Image[4];
	public Image imgBQSmile = null;
	
	
	public Sprite spNPC[] = new Sprite[10];
	//*******************************//战斗的界面的人物
	public Sprite spfHero = null;
	public Sprite spattackHero = null;
	public Sprite spMagic[] = new Sprite[6];
	//***************************//
	public Sprite spHero = null;
	public Sprite spHeroBody = null;
	public Sprite spSanJianTou1 = null;
	public Sprite spSanJianTou2 = null;
	public Sprite spSanJianTou3 = null;
	public Sprite sFeng = null;
	public Sprite sMonsterDied[] = new Sprite[3];
	
	public final int[] SequenceUp = {6,7,8};
	public final int[] SequenceDown = {0,1,2};
	public final int[] SequenceLeft = {3,4,5};
	public final int[] SequenceRight = {9,10,11};
	
	public final int[] Sequence1 = {0,1,2};
	public final int[] Sequence2 = {3,4,5};
	
	public final int[] Sequence = {0,1,2,3};
	public final int[] Sequence_ = {0,1,2,3,4};
	public final int[] SequenceMag = {0,1,2,3,4,5};
	public LoadImageSprite()
	{
		LoadImage();
		LoadSprite();
	}
	
	public void LoadImage()
	{
		try {
			confirm = Image.createImage("/confirm.png");
			confirmyes = Image.createImage(confirm, 0, 0, 17, 14, 0);
			confirmno = Image.createImage(confirm, 17, 0, 17, 14, 0);
			imgMenu = Image.createImage("/Menu.png");
			imgMenuB1 = Image.createImage("/menuB1.png");
			imgMenuB2 = Image.createImage("/menuB2.png");
			imgMenuB3 = Image.createImage("/menuB3.png");
			imgMenuB4 = Image.createImage("/menuB4.png");
			imgMenuB5 = Image.createImage("/menuB5.png");
			imgBg = Image.createImage("/imgBg.png");
			imgBackgroup = Image.createImage("/backgroup3.png");
			imgHero = Image.createImage("/imgHero.png");
			imgHeroBody = Image.createImage(imgHero, 0, 23, 39, 4, 0);
			imgSanJianTou = Image.createImage("/sanjiantou.png");
			imgDragon = Image.createImage("/dragon.png");
			imgHelpDiaLog = Image.createImage("/HelpDiaLog.png");
			imgShop = Image.createImage("/Shop.png");
			imgUi = Image.createImage("/ui.png");
			imgDiaLogBg = Image.createImage(imgUi,0,36,240,72, 0);
			imgSelectBgLeft = Image.createImage(imgUi, 0, 0, 120, 36, 0);
			imgSelectBgRight = Image.createImage(imgUi, 0, 0, 120, 36, Sprite.TRANS_MIRROR);
			imgBorderLeft = Image.createImage(imgUi, 120, 5, 17, 18, 0);
			imgBorderRight = Image.createImage(imgUi, 120, 5, 17, 18, Sprite.TRANS_MIRROR);
			imgBorderBg = Image.createImage(imgUi, 134, 6, 2, 17, Sprite.TRANS_MIRROR);
			
			//**************************************************选择状态
			imgDi = Image.createImage("/di.png");
			imgQiu = Image.createImage("/qiu.png");
			imgUiwenzi = Image.createImage("/uiwenzi1.png");
			imgAttack = Image.createImage(imgUiwenzi, 0, 0, 25, 13, 0);
			imgMagicAttack = Image.createImage(imgUiwenzi, 25, 0, 25, 13, 0);
			imgProp = Image.createImage(imgUiwenzi, 50, 0, 25, 13, 0);
			imgRecovery = Image.createImage(imgUiwenzi, 75, 0, 25, 13, 0);
			imgBuy = Image.createImage(imgUiwenzi, 100, 0, 25, 13, 0);
			
			
			imgAttack_y = Image.createImage(imgUiwenzi, 0, 13, 25, 13, 0);
			imgMagicAttack_y = Image.createImage(imgUiwenzi, 25, 13, 25, 13, 0);
			imgProp_y = Image.createImage(imgUiwenzi, 50, 13, 25, 13, 0);
			imgRecovery_y = Image.createImage(imgUiwenzi, 75, 13, 25, 13, 0);
			imgBuy_y = Image.createImage(imgUiwenzi, 100, 13, 25, 13, 0);
			//******************************************战斗状态
			imgFightHero = Image.createImage("/fight02001.png");
			imgattackHero = Image.createImage("/fight02002.png");
			imgbattleface = Image.createImage("/battleface.png");
			imgHeroFace = Image.createImage(imgbattleface, 0, 0, 40, 25, 0);
			imgtouxiang = Image.createImage("/touxiang.png");
			
			imgShengLiBg = Image.createImage("/shenglibg.png");
			//**************************************************怪物
			imgFeng = Image.createImage("/feng.png");
			imgMonsterDied = Image.createImage("/MonsterDied.png");
			
			
			imgcateran[0] = Image.createImage("/cateran1.png");
			imgcateran[1] = Image.createImage("/cateran2.png");
			imgcateran[2] = Image.createImage("/cateran3.png");
			imgcateran[3] = Image.createImage("/cateran4.png");
			imgcateran[4] = Image.createImage("/cateran5.png");
			imgcateran[5] = Image.createImage("/cateran6.png");
			imgcateran[6] = Image.createImage("/cateran7.png");
			imgcateran[7] = Image.createImage("/cateran8.png");
			imgcateran[8] = Image.createImage("/cateran9.png");
			imgcateran[9] = Image.createImage("/cateran10.png");
			
			//*************************************************//NPC
			imgNPC[0] = Image.createImage("/guyan.png");
			imgNPC[1] = Image.createImage("/liang.png");
			imgNPC[2] = Image.createImage("/smallsr.png");	//整张
			
			
			imgNPC2[0] = Image.createImage(imgNPC[1], 0, 0, 11, 29, 0);
			imgNPC2[1] = Image.createImage(imgNPC[2],0,0,16,32,0);
			//*************************************************//NPC表情
			imgBiaoQing = Image.createImage("/biaoqing.png");
			imgBQPoint[0] = Image.createImage(imgBiaoQing, 0, 49, 12, 14, 0);
			imgBQPoint[1] = Image.createImage(imgBiaoQing, 14, 47, 15, 15, 0);
			imgBQPoint[2] = Image.createImage(imgBiaoQing, 30, 50, 14, 14, 0);
			imgBQPoint[3] = Image.createImage(imgBiaoQing, 46, 49, 14, 15, 0);
			imgMagic[0] = Image.createImage("/Animation09001.png");
			imgMagic[1] = Image.createImage("/Animation05003.png");
			imgMagic[2] = Image.createImage("/Animation05001.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LoadSprite()
	{
		spHero = new Sprite(imgHero,13,27);
		spHero.setFrameSequence(SequenceDown);
		spHeroBody = new Sprite(imgHeroBody,13,4);
		spHeroBody.setFrameSequence(Sequence1);
		spHero.setPosition(100, 180);
		spHeroBody.setPosition(100, 203);
		spSanJianTou1 = new Sprite(imgSanJianTou,11,11);
		spSanJianTou2 = new Sprite(imgSanJianTou,11,11);
		spSanJianTou3 = new Sprite(imgSanJianTou,11,11);
		spSanJianTou1.setFrameSequence(Sequence1);
		spSanJianTou2.setFrameSequence(Sequence1);
		spSanJianTou3.setFrameSequence(Sequence1);
		spSanJianTou1.setPosition(108, 180);
		spSanJianTou2.setPosition(108, 186);
		spSanJianTou3.setPosition(108, 192);
		
		//***********************************//
		spfHero = new Sprite(imgFightHero,57,59);
		spfHero.setFrameSequence(Sequence);
		spfHero.setPosition(180, 120);
		spattackHero = new Sprite(imgattackHero,57,59);
		spattackHero.setFrameSequence(Sequence_);
		spMagic[0] = new Sprite(imgMagic[0],80,61);
		spMagic[0].setFrameSequence(SequenceMag);
		spMagic[1] = new Sprite(imgMagic[1],39,55);
		spMagic[1].setFrameSequence(Sequence);
		spMagic[2] = new Sprite(imgMagic[2],38,37);
		spMagic[2].setFrameSequence(Sequence);
		spMagic[3] = new Sprite(imgMagic[2],38,37);
		spMagic[3].setFrameSequence(Sequence);
		spMagic[4] = new Sprite(imgMagic[2],38,37);
		spMagic[4].setFrameSequence(Sequence);
		spMagic[5] = new Sprite(imgMagic[2],38,37);
		spMagic[5].setFrameSequence(Sequence);
		
		sFeng = new Sprite(imgFeng,18,28);
		sFeng.setFrameSequence(Sequence);
		sFeng.setPosition(310, 192);
		sMonsterDied[0] = new Sprite(imgMonsterDied,16,42);
		sMonsterDied[0].setFrameSequence(Sequence);
		sMonsterDied[1] = new Sprite(imgMonsterDied,16,42);
		sMonsterDied[1].setFrameSequence(Sequence);
		sMonsterDied[2] = new Sprite(imgMonsterDied,16,42);
		sMonsterDied[2].setFrameSequence(Sequence);
		
		spNPC[0] = new Sprite(imgNPC[0],11,26);//酒店老板
		spNPC[0].setPosition(144, 100);
		spNPC[1] = new Sprite(imgNPC2[0],11,29);//酒店外的小翠
		spNPC[1].setPosition(140, 176);
		spNPC[2] = new Sprite(imgNPC2[1],16,32);
		spNPC[2].setPosition(600, 176);	//书生
//		spSanJianTou1.setTransform(Sprite.TRANS_ROT90);
	}
	
}
