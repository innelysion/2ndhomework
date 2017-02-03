package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

//◆MAIN◆//
public class GameMain {



	//final static double GAMEVERSON = 0.14112333;
	SYS setting = new SYS("PC");
	JFrame wind = new JFrame("Shooooooooooooooooting!!!");// NEW JFrame
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Font f = new Font("Default", Font.BOLD, 13);// 使用するフォントクラス宣言

	Input input = new Input();
	VFX vfx = new VFX();

	NStgBossAlienMothership boss = new NStgBossAlienMothership();
	NStgPlayerShoot ps = new NStgPlayerShoot();
	NStgManager manager = new NStgManager();
	NStgDanmaku dm = new NStgDanmaku();
	NStgOptions op = new NStgOptions();
	NStgPlayer pr = new NStgPlayer();
	NStgEnemy en = new NStgEnemy();
	NStgItem it = new NStgItem();
	NStgMap mp = new NStgMap();
	NStgUI ui = new NStgUI();

	GameMessage msgbox = new GameMessage();

	Timer TM = new Timer();
	timer_TSK MAINLOOP = new timer_TSK();

	// -----------------------------
	// 初期化用の関数
	// ・window生成
	// ・window大きさの指定
	// ・windowの場所
	// -----------------------------

	GameMain() {

		// Setup javaframe window & graphics2D buffer
		wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		wind.setBackground(new Color(0, 0, 0));// 色指定
		wind.setResizable(false);// ｻｲｽﾞ変更不可
		wind.setVisible(true);// 表示or非表示
		sz = wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		wind.setSize(SYS.WINDOW_SIZE_X + sz.left + sz.right, SYS.WINDOW_SIZE_Y + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽ
		wind.setLocationRelativeTo(null);// 中央に表示


		try {
			Thread.sleep(10);
			wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		} catch (Exception e) {
			Input.K_ESC_R = true;// もしﾀﾞﾌﾞﾙﾊﾞｯﾌｧ生成失敗すると強制再起動
		}
		offimage = wind.getBufferStrategy();

		// For input class
		wind.addMouseListener(input);
		wind.addMouseMotionListener(input);
		wind.addKeyListener(input);
		wind.addMouseWheelListener(input);

		// Setup timer task
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

		TM.schedule(MAINLOOP, 17, 17);

	}// GameMain end

	// ◆メーン処理繰り返しタスク◆//
	class timer_TSK extends TimerTask {

		public void run() {
			if (Input.K_ESC_R) {

				this.cancel();

			} else {

				// Game data update
				input.update(wind);
				mainUpdate();

				// Graphics for new frame
				Graphics g2 = offimage.getDrawGraphics();
				Graphics2D g = (Graphics2D) g2;
				Graphics gg2 = offimage.getDrawGraphics();
				Graphics2D gg = (Graphics2D) gg2;

				if (offimage.contentsLost() == false) {

					// Clear the prev frame
					// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
					g.translate(sz.left, sz.top);
					gg.translate(sz.left, sz.top);
					g.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);
					gg.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);

					// Main Graphics Update
					ui.screenEffect(g, wind);
					drawMain(g);
					manager.drawHit(g, wind);
					ui.draw(gg, wind);
					msgbox.draw(gg);
					manager.drawDebugMsg(gg, wind);

					// Dispose last frame graphics
					offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
					g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄
					gg.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

				}


			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

		private void drawMain(Graphics2D g) {

			// 描画
			mp.drawImage(g, wind);
			boss.drawKoma(g, wind);
			en.drawKoma(g, wind);
			ps.drawKoma(g, wind);
			pr.draw(g, wind);
			dm.drawKoma(g, wind);
			it.drawKoma(g, wind);
			vfx.draw(g, wind);


		}

		private void mainUpdate() {

			// データのやり取り
			manager.map = mp;
			manager.enemy = en;
			manager.boss = boss;
			manager.danmaku = dm;
			manager.options = op;
			manager.shoot = ps;
			manager.ui = ui;
			manager.item = it;
			manager.msgbox = msgbox;

			// stage main (4200frame made)
			manager.update();

			// All Update Here
			mp.update();
			en.update();
			ps.update();
			op.update();
			pr.update();
			dm.update();
			it.update();
			vfx.update();
			ui.update();
			boss.update();
			msgbox.update();

		}

	}// timer task class end

	// ◆ここからプログラムが動く◆//
	public static void main(String[] args) throws Throwable {

		GameMain Game = new GameMain();
		do {
			Thread.sleep(250);
			if (Input.K_ESC_R) {
				Game.setting = null;
				Game.f = null;
				Game.input = null;
				Game.vfx = null;
				Game.manager = null;
				Game.ps = null;
				Game.dm = null;
				Game.pr = null;
				Game.en = null;
				Game.mp = null;
				Game.ui = null;
				Game.msgbox = null;

				Game.sz = null;
				if (Game.offimage != null) {
					Game.offimage.dispose();
				}
				Game.offimage = null;
				Game.wind.dispose();
				Game.wind = null;
				Game.MAINLOOP.cancel();
				Game.MAINLOOP = null;
				Game.TM.cancel();
				Game.TM.purge();
				Game.TM = null;

				Game.finalize();
				Game = null;

				Game = new GameMain();
			}
			Thread.sleep(50);
		} while (Game != null);

	}

	/*
	 * for (String target : timeTable){
	 *   System.out.println(target);
	 * }
	 * 就職3年目まで:第一線のプログラマーとして、いろんなプロジェクトに参加し、各種エンジンとツール、言語を身に着ける。
	 * 就職5年目まで:プロジェクトの中核として活躍する。
	 * 就職10年目まで:作りたい夢のゲームを作り出す!形式は商業でもインディーズでも構わない!
	 *
	 * 個人的に推すゲーム、いずれいずれも熱中した/している:
	 * ACT:朧村正、Unchartedシリーズ
	 * FPS:Battlefieldシリーズ、CounterStrike
	 * RPG:二ノ国、軒轅剣シリーズ、DragonAge:Origins、The Elder Scrollsシリーズ、Undertale
	 * RTS/MOBA:RedAlertシリーズ、WarCraft/StarCraftシリーズ、League of Legends
	 * SLG:Civilizationシリーズ、Heroes of Might and Magicシリーズ、
	 * STG:東方シリーズ、虫姫さまふたり、ディアドラエンプティ
	 * パズル:Portalシリーズ、Fez、Child of Light、Ori and the Blind Forest
	 * ギャルゲー:うたわれるものシリーズ、Ever17、ランスシリーズ、クドわふたー
	 * ソーシャル:グラブル、かくりよの門、陰陽師
	 * カード:遊戯王、MTG、HearthStone、シャドウバース
	 * テーブル:クトゥルフTRPG、D&D
	 *
	 * 自分の脳内スキルツリー
	 *
	 * RPGツクール(XP,VXace,MV),Unity,Unreal
	 * Eclipase, VS2015, NOTEPAD++, AndroidStudio, Dreamweaver
	 *
	 * 3dsmax, Maya, SketchUp
	 * AfterEffects, Premiere, Vegas
	 * Photoshop, PaintToolSAI,
	 *
	 * GoogleDocs, Github, SVN
	 * Word, Excel, PowerPoint, Visio
	 *
	 * Java, C++, C#
	 * JS, Ruby, LUA
	 *
	 *
	 */

}
