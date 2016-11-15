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

	//final static double GAMEVERSON = 0.14112;
	SYS setting = new SYS("PC");
	JFrame wind = new JFrame("Shooooooooooooooooting!!!");// NEW JFrame
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Font f = new Font("Default", Font.BOLD, 13);// 使用するフォントクラス宣言

	Input input = new Input();
	VFX vfx = new VFX();

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
					// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
					g.translate(sz.left, sz.top);
					g.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);
					gg.translate(sz.left, sz.top);
					gg.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);

					// Main Graphics Update
					ui.screenEffect(g, wind);
					drawMain(g);
					ui.draw(gg, wind);
					msgbox.draw(gg);

					// Dispose last frame graphics
					offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
					g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄
					gg.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

					// Stop timertask when restart the game

				}
				
		        
			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

		private void drawMain(Graphics2D g) {

			// 描画
			mp.drawImage(g, wind);
			en.drawKoma(g, wind);
			ps.drawKoma(g, wind);
			pr.draw(g, wind);
			dm.drawKoma(g, wind);
			it.drawKoma(g, wind);
			vfx.draw(g, wind);
			manager.drawHit(g, wind);

		}

		private void mainUpdate() {

			// データのやり取り
			manager.map = mp;
			manager.enemy = en;
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

}
