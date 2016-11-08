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

//◆メーン◆//
public class GameMain {

	static double GAMEVERSON = 0.11111;
	SYS setting = new SYS("PC");
	JFrame wind = new JFrame("Shooooooooooooooooting!!!");// NEW JFrame
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Font f = new Font("Default", Font.BOLD, 13);// 使用するフォントクラス宣言

	Input input = new Input();
	VFX vfx = new VFX();

	NStgManager manager = new NStgManager();
	NStgPlayerShoot ps = new NStgPlayerShoot();
	NStgDanmaku dm = new NStgDanmaku();
	NStgPlayer pr = new NStgPlayer();
	NStgEnemy en = new NStgEnemy();
	NStgMap mp = new NStgMap();

	NStgUI ui = new NStgUI();
	GameMessage msgbox = new GameMessage();
//	StgItem item = new StgItem();

	// -----------------------------
	// 初期化用の関数
	// ・window生成
	// ・window大きさの指定
	// ・windowの場所
	// -----------------------------

	GameMain() {

		// Setup javaframe window & graphics2D buffer
		wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		wind.setBackground(new Color(0, 0, 0));// 色指定
		wind.setResizable(false);// ｻｲｽﾞ変更不可
		wind.setVisible(true);// 表示or非表示
		sz = wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		wind.setSize(SYS.WINDOW_SIZE_X + sz.left + sz.right, SYS.WINDOW_SIZE_Y + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽﾞ
		wind.setLocationRelativeTo(null);// 中央に表示
		wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		offimage = wind.getBufferStrategy();

		// For input class
		wind.addMouseListener(input);
		wind.addMouseMotionListener(input);
		wind.addKeyListener(input);
		wind.addMouseWheelListener(input);

		// Load game data and resources
//		item.loadImage("Image/Item", 1);

		// Setup timer task
		Timer TM = new Timer();
		TM.schedule(new timer_TSK(), 17, 17);
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

	}// GameMain end

	// ◆メーン処理クラス◆//
	class timer_TSK extends TimerTask {

		public void run() {
			///////////////////////////////////////////////////////////////////////////////////////

			// Game data update
			input.update(wind);
			mainUpdate();

			///////////////////////////////////////////////////////////////////////////////////////
			// Garphics update
			
			Graphics g2 = offimage.getDrawGraphics();// ｸﾞﾗﾌｨｯｸ初期化
			Graphics2D g = (Graphics2D) g2;
			Graphics gg2 = offimage.getDrawGraphics();
			Graphics2D gg = (Graphics2D) gg2;
			
			

			if (offimage.contentsLost() == false) {//

				// Clear the graphic for next frame
				g.translate(sz.left, sz.top); // ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
				gg.translate(sz.left, sz.top);


				g.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);
				gg.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);
				///////////////////////////////////////////////////////////////////////////////////

				// Main Update
				ui.screenEffect(g, wind);
				drawMain(g);
				

				ui.draw(gg, wind);
				msgbox.draw(gg);
				
//				gg.drawString(String.valueOf(ui.gRotate), SYS.WINDOW_SIZE_X - 170 - 15, SYS.WINDOW_SIZE_Y - 50);

				///////////////////////////////////////////////////////////////////////////////////
				//	Show gameover screen


				offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
				g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄
				gg.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

		private void drawMain(Graphics2D g) {

			mp.drawImage(g, wind);
			en.drawKoma(g, wind);
			ps.drawKoma(g, wind);
//			item.drawImage(g, wind);
			pr.draw(g, wind);
			dm.drawKoma(g, wind);

			vfx.draw(g, wind);



		}

		private void mainUpdate() {

			// データのやり取り
			manager.map = mp;
			manager.enemy = en;
			manager.danmaku = dm;
			manager.shoot = ps;
			manager.ui = ui;
			manager.msgbox = msgbox;

			// stage main (4200frame made)
			manager.update();

			// All Update Here
			mp.update();
			vfx.update();
			ps.update();
			dm.update();
			en.update();
			pr.update();
//			item.update();
			ui.update();
			msgbox.update();


		}

	}// timer task class end

	// ◆ここからプログラムが動く◆//
	public static void main(String[] args) {

		@SuppressWarnings("unused")
		GameMain Game = new GameMain();

	}

}
