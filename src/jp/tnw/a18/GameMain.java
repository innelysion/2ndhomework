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

	JFrame wind = new JFrame("Shooooooooooooooooting!!!");// NEW JFrame
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Font f = new Font("Default", Font.BOLD, 13);// 使用するフォントクラス宣言

	Input input = new Input();
	VFX vfx = new VFX();

	NStgDanmaku dm = new NStgDanmaku();
	NStgPlayer pr = new NStgPlayer();
	NStgPlayerShoot ps = new NStgPlayerShoot();
	NStgEnemy en = new NStgEnemy();
	NStgMap mp = new NStgMap();

	StgUI ui = new StgUI();
	StgItem item = new StgItem();
	// StgMap map = new StgMap();

	// -----------------------------
	// 初期化用の関数
	// ・window生成
	// ・window大きさの指定
	// ・windowの場所
	// -----------------------------

	GameMain() {

		// System setting
		SYS.setupPC();

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
		item.loadImage("Image/Item", 1);

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
				if (SYS.GAMEOVERING) {
					g.translate(Math.random() * 10 - 5, Math.random() * 10 - 5);
				}
				g.clearRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);

				///////////////////////////////////////////////////////////////////////////////////

				// Main Update

				drawMain(g);

				ui.drawImage(g, wind);

				g.drawString("A18張瀚夫20161004", SYS.WINDOW_SIZE_X - 170 - 15, SYS.WINDOW_SIZE_Y - 10);

				///////////////////////////////////////////////////////////////////////////////////
				if (SYS.GAMEOVERING) {
					gg.translate(sz.left, sz.top);
					ui.drawGameover(gg, wind);
					offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
					gg.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄
				}

				offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
				g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

		private void drawMain(Graphics2D g) {

			// map.drawImage(g, wind);
			mp.drawImage(g, wind);
			vfx.draw(g, wind);
			en.drawKoma(g, wind);
			item.drawImage(g, wind);
			pr.draw(g, wind);
			ps.drawKoma(g, wind);
			dm.drawKoma(g, wind);
			g.setColor(Color.MAGENTA);// 色指定
			g.setFont(f);
		}

		private void mainUpdate() {

			// データのやり取り
			ps.map = mp;
			dm.enemy = en;

			// All Update Here
			mp.update();
			vfx.update();
			ps.update();
			dm.update();
			en.update();
			pr.update();
			item.update();
			ui.update();
		}

	}// timer task class end

	// ◆ここからプログラムが動く◆//
	public static void main(String[] args) {

		@SuppressWarnings("unused")
		GameMain Game = new GameMain();

	}

}
