package jp.tnw.a18;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


//◆ドカーーーンのクラス◆//
public class VFX {

	BufferedImage image;// 背景の読み込むメモリ宣言
	final static int max = 5000;// 最大数

	static int flag[] = new int[max];// 0:出現していない
	static double a_wait[] = new double[max];// ｱﾆﾒwait time
	static double a_wait_cp[] = new double[max];// ｱﾆﾒwait time copy

	static int no[] = new int[max];// ｱﾆﾒﾅﾝﾊﾞｰ
	static int nox1[] = new int[max];// ﾘｸｴｽﾄﾅﾝﾊﾞｰ
	static int nox2[] = new int[max];// ｱﾆﾒｻｲｽﾞx
	static int noy[] = new int[max];// ｱﾆﾒｻｲｽﾞy
	static int nom[] = new int[max];// ｱﾆﾒｻｲｽﾞmax

	static double zx[] = new double[max];// 表示座標 x
	static double zy[] = new double[max];// 表示座標 y
	double timer;// ﾘｸｴｽﾄﾀｲﾏｰ

	// ----------------
	// ｺﾝｽﾄﾗｸﾀ
	// ----------------
	public VFX() {

		try {
			image = ImageIO.read(getClass().getResource("Image/bomb.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int cnt = 0; cnt < max; cnt++) {
			flag[cnt] = 0;
		}

	}

	// ---------------------------
	// ﾘｸｴｽﾄ関数
	// entry;座標x,y 爆発ﾅﾝﾊﾞｰ,
	// ---------------------------
	public static void request(double xx, double yy, int b_no) {

		double ani_wait[] = { 0.02, 0.02, 0.02, 0.02, 0.02, 0.01, 0.01, 0.01, 0.01 };// wait
		int ani_size[] = { 48, 48, 32, 32, 32, 32, 16, 16, 16 };// size
		int ani_pt[] = { 10, 10, 10, 10, 5, 5, 5, 5, 4 };// ﾊﾟﾀｰﾝ数
		int ani_xx[] = { 0, 16 * 3, 16 * 6, 16 * 8, 16 * 10, 16 * 12, 16 * 14, 16 * 15, 16 * 16 };// 先頭

		for (int cnt = 0; cnt < max; cnt++) {
			if (flag[cnt] == 0) {

				flag[cnt] = 1;//
				no[cnt] = 0;

				a_wait[cnt] = ani_wait[b_no];// timr
				a_wait_cp[cnt] = ani_wait[b_no];// time copy
				nox1[cnt] = ani_xx[b_no];// ヨコ先頭
				nox2[cnt] = ani_size[b_no];// 幅
				noy[cnt] = ani_size[b_no];// 幅
				nom[cnt] = ani_pt[b_no];// max

				zx[cnt] = xx - ani_size[b_no] / 2;// 表示座標 x
				zy[cnt] = yy - ani_size[b_no] / 2;// 表示座標 y

				break;
			} // if end
		} // for end

	}

	// ---------------------------
	// 処理
	// ---------------------------
	public void update() {

		for (int cnt = 0; cnt < max; cnt++) {
			if (flag[cnt] != 0) {

				a_wait[cnt] -= SYS.FRAME_TIME;
				if (a_wait[cnt] < 0) {
					a_wait[cnt] = a_wait_cp[cnt];// timer reset

					no[cnt]++;
					if (no[cnt] > nom[cnt])
						flag[cnt] = 0;// 爆発end
				}

			} // flag end
		} // for end

	}

	// ---------------
	// ○敵読み込み
	// ----------------
//	public void Load() {
//		try {
//			image = ImageIO.read(getClass().getResource("Image/bomb.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}// Load end
	// -----------------------
	// 表示関数
	// -----------------------
	public void draw(Graphics g, JFrame w) {

		for (int cnt = 0; cnt < max; cnt++) {
			if (flag[cnt] != 0)
				g.drawImage(image,
						(int) zx[cnt],
						(int) zy[cnt],
						(int) zx[cnt] + nox2[cnt],
						(int) zy[cnt] + noy[cnt],
						nox1[cnt],
						noy[cnt] * no[cnt],
						nox1[cnt] + nox2[cnt],
						noy[cnt] * no[cnt] + noy[cnt],
						w);
		}

	}

}
