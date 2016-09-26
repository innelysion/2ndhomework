package jp.tnw.a18;

//使いたいｸﾗｽの取り込みasdasdasdTEST
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameMain  {

	JFrame wind = new JFrame("前期課題まとめA18");// JFrame の初期化(ﾒﾆｭｰﾊﾞｰの表示ﾀｲﾄﾙ
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Font f = new Font("Default", Font.BOLD, 13);// 使用するフォントクラス宣言
	VFX bom = new VFX();
	StgItem item = new StgItem();
	StgBullet bullet = new StgBullet();
	StgEnemy enemy = new StgEnemy();
	StgPlayer player = new StgPlayer();
	Input input = new Input();
	BufferedImage bg;
	boolean[] display = { false, true, false, true, true };
	boolean mainStart = false;
	static boolean in = false, in1 = true, in2 = true, in3 = true, in4 = true, in5 = true;

	// -----------------------------
	// 初期化用の関数
	// ・window生成
	// ・window大きさの指定
	// ・windowの場所
	// -----------------------------

	GameMain() {

		// Load System data
		Sys.setupPC();
		// Setup window & inputs & graphics
		wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		wind.setBackground(new Color(0, 0, 0));// 色指定
		wind.setResizable(false);// ｻｲｽﾞ変更不可
		wind.setVisible(true);// 表示or非表示
		sz = wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		wind.setSize(Sys.windowSizeX + sz.left + sz.right, Sys.windowSizeY + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽﾞ
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
		bom.Load();
		item.loadImage("Image/Item", 1);
		bullet.loadImage("Image/tama", 1);
		enemy.loadImage("Image/zako", 1);
		player.loadImage("Image/jiki2", 1);
		try {
			bg = ImageIO.read(getClass().getResource("Image/bg_02.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup timer task
		Timer TM = new Timer();// ﾀｲﾏｰｸﾗｽの実体化
		TM.schedule(new timer_TSK(), 17, 17);// 17ms後から 17msおきに
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

	}// Main_Game end

	// ---------------------------
	// ﾀｲﾏｰｸﾗｽ 1/60秒で1回動作
	// extends=継承
	// ---------------------------
	class timer_TSK extends TimerTask {

		public void run() {

			// Game data update
			input.update(wind);
			mainUpdate();

			// Garphics update
			Graphics g2 = offimage.getDrawGraphics();// ｸﾞﾗﾌｨｯｸ初期化
			Graphics2D g = (Graphics2D) g2;

			if (offimage.contentsLost() == false) {//
				// Clear the graphic for next frame
				g.translate(sz.left, sz.top); // ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
				g.clearRect(0, 0, Sys.windowSizeX, Sys.windowSizeY); // 画面ｸﾘｱ(左上X、左上Y、右下x、右下y)
				drawMain(g);
				g.drawString("A18 チョウ カンフ 前期課題", Sys.windowSizeX - 170 - 15, Sys.windowSizeY - 10);
				offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
				g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

		private void drawMain(Graphics2D g) {
			// TODO Auto-generated method stub
			g.drawImage(bg, 0, 0, Sys.windowSizeX, Sys.windowSizeY, 0, 0, bg.getWidth(), bg.getHeight(), wind);
			bom.draw(g, wind);


			if (display[4] == true) {
				enemy.drawImage(g, wind);
				item.drawImage(g, wind);
			}
			player.drawImage(g, wind);
			bullet.drawImage(g, wind);
		}

		private void mainUpdate() {
			// TODO Auto-generated method stub
			bom.UpDate();

			bullet.update(bom);

			item.update();

			enemy.update(bullet, bom, item);

			player.update();
		}



	}// timer task class end
		// -----------------------------------
		// Main ここからプログラムが動く
		// -----------------------------------

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		// Main_GameのｸﾗｽをGMという名前で動かします
		// 動かす前に初期化してから動かす！！

		@SuppressWarnings("unused")
		GameMain Game = new GameMain();

	}

}
