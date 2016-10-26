package jp.tnw.a18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class StgDanmaku extends StgImage{

	BufferedImage image;
	int widthBlock = 20;
	int heightBlock = 10;
	int UNIT_MAX = 60000;
	// 画像の切り替え用の変数
	int imageIndex[] = new int[UNIT_MAX];
	// 可視スイッチ
	boolean isVisible[] = new boolean[UNIT_MAX];
	float opacity[] = new float[UNIT_MAX];
	// 座標
	double dX[] = new double[UNIT_MAX];
	double dY[] = new double[UNIT_MAX];
	// アニメとリクエストのカウンター
	int timerAni[] = new int[UNIT_MAX];
	double timerReq[] = new double[UNIT_MAX];
	// 速度
	double spdX[] = new double[UNIT_MAX];
	double spdY[] = new double[UNIT_MAX];
	double accX[] = new double[UNIT_MAX];
	double accY[] = new double[UNIT_MAX];
	// 角度
	double angle[] = new double[UNIT_MAX];
	// 円心と軸
	double centerX[] = new double[UNIT_MAX];
	double centerY[] = new double[UNIT_MAX];
	double axisX[] = new double[UNIT_MAX];
	double axisY[] = new double[UNIT_MAX];
	// 当たり判定
	boolean isHitable[] = new boolean[UNIT_MAX];
	double hitCir[] = new double[UNIT_MAX];
	double hitBoxW[] = new double[UNIT_MAX];
	double hitBoxH[] = new double[UNIT_MAX];

	int flag[] = new int[UNIT_MAX];
	int bulletType[] = new int[UNIT_MAX];
	int bulletAction[] = new int[UNIT_MAX];

	double a = 0;
	int b = 0;
	int timerA = 0;
	int timerB[] = new int[UNIT_MAX];
	int bulletCount = 0;

	StgMap map;

	StgDanmaku() {

		image = loadImage(image, "Image/tama.png");

		for (int i = 0; i < UNIT_MAX; i++) {

			isVisible[i] = false;
			opacity[i] = 1.0f;

			dX[i] = 0;
			dY[i] = 0;

			timerAni[i] = 0;
			timerReq[i] = 0;

			spdX[i] = 0;
			spdY[i] = 0;
			accX[i] = 0;
			accY[i] = 0;

			angle[i] = 0;

			centerX[i] = 0;
			centerY[i] = 0;
			axisX[i] = 0;
			axisY[i] = 0;

			isHitable[i] = false;
			hitCir[i] = 8;
			hitBoxW[i] = 8;
			hitBoxH[i] = 8;

			flag[i] = 0;
			bulletType[i] = 0;
			bulletAction[i] = 0;

			timerB[i] = 120;


		}

	}

	public void requestSniper(double x, double y, int type) {


		int[][] KD = { //
				//
				{ 0, 999 }, //
				{ 0, 0 + 30, 0 - 30, 999 }, //
				{ 0, 0 + 15, 0 - 15, 0 + 30, 0 - 30, 0 + 45, 0 - 45, 999 } //
		};//
		// 自機狙い角度
		double jikinerai;
		jikinerai = Math.atan2((StgPlayer.dY + 24 - (y - 16)) , (StgPlayer.dX + 24 - (x - 16))) / Math.PI * 180 ;
		int cnt = 0;

		timerReq[0] = timerReq[0] - SYS.FRAME_TIME;
		if (timerReq[0] < 0 && bulletCount < 10) {
			timerReq[0] = 0.5;
			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0 && bulletAction[i] == 0) {
					imageIndex[i] = 235;
					bulletType[i] = 2;
					bulletAction[i] = 1;
					dX[i] = x;
					dY[i] = y;
					spdX[i] = 0;
					spdY[i] = 0;

					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;

					angle[i] = KD[type][cnt] + jikinerai;
					cnt++;
					if (KD[type][cnt] == 999) {
						break;
					}


				}
			}
			bulletCount++;
		}
	}

	public void request(double x, double y, int type) {

		int[][] KD = { //
				//
				{ 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360, 999 }, //
				{ 0, 90, 180, 270, 999 }, //
				{ 90, 90 + 30, 90 - 30, 999 }, //
				{ 0, 180, 999 } //
		}//
		;//
		int cnt;
		cnt = 0;
		a += 7;
		timerReq[0] = timerReq[0] - SYS.FRAME_TIME;
		if (timerReq[0] < 0) {
			timerReq[0] = 0.02;

			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0 && bulletAction[i] == 0) {
					imageIndex[i] = 235;
					bulletType[i] = 2;
					bulletAction[i] = 1;
					dX[i] = x;
					dY[i] = y;
					spdX[i] = 250;
					spdY[i] = 250;

					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;

					angle[i] = KD[type][cnt] + a;
					cnt++;
					if (KD[type][cnt] == 999) {
						break;
					}
				}
			}

		}

	}

	public void update() {

//		requestSniper(480, 150, b);
//
//		if (timerA % 7 == 0) {
//			b++;
//		}
//
//		timerA++;
//		if (b > 2) {
//			b = 0;
//		}

		// Animation
		for (int i = 0; i < UNIT_MAX; i++) {

			switch (bulletType[i]) {
			case 0:
				imageIndex[i] = 57;
			case 1:

				imageIndex[i] = imageIndex[i] < 21 || imageIndex[i] >= 36 ? 21 : imageIndex[i] + 1;

				break;
			case 2:
				if (timerAni[i] % 5 == 0) {
					imageIndex[i] = imageIndex[i] < 37 || imageIndex[i] >= 40 ? 37 : imageIndex[i] + 1;
				}
				break;
			case 3:
				if (timerAni[i] % 5 == 0) {
					imageIndex[i] = imageIndex[i] < 161 || imageIndex[i] >= 164 ? 161 : imageIndex[i] + 1;
				}
				break;
			}
			timerAni[i]++;
		}
		// Animation

		// Position
		for (int i = 0; i < UNIT_MAX; i++) {

			if (flag[i] == 1){
				timerB[i] -= 1;
				if (timerB[i] <= 0){
					spdX[i] = 250;
					spdY[i] = 250;
					flag[i] = 2;
				}
			}

			if (flag[i] == 2){

			spdX[i] += SYS.FRAME_TIME * accX[i];
			spdY[i] += SYS.FRAME_TIME * accY[i];
			dX[i] += SYS.FRAME_TIME * spdX[i] * Math.cos(Math.toRadians(angle[i]));
			dY[i] += SYS.FRAME_TIME * spdY[i] * Math.sin(Math.toRadians(angle[i]));

			}

			if (bulletType[i] != 0 && isOutBorder((int) dX[i], (int) dY[i], 16, 16)) {

				isVisible[i] = false;
				isHitable[i] = false;
				imageIndex[i] = 1;
				dX[i] = 9999;
				spdX[i] = 0;
				spdY[i] = 0;
				accX[i] = 0;
				accY[i] = 0;
				bulletType[i] = 0;
				bulletAction[i] = 0;
				flag[i] = 0;

			}

			if (isHit(dX[i] - 16, dY[i] - 16, StgPlayer.dX + 24, StgPlayer.dY + 24, 4, 4)) {
				if (StgPlayer.timerFlash == 0) {
					StgPlayer.damage(10);
				}

			}

		}
		// Position

	}

	public void drawImage(Graphics2D g, JFrame wind) {
		for (int i = 0; i < UNIT_MAX; i++) {
			if (isVisible[i]) {
				this.drawImage(g, wind, image, widthBlock, heightBlock, imageIndex[i], opacity[i], dX[i], dY[i]);
			}
		}
	}

	public boolean isHit(double x1, double y1, double x2, double y2, double rad1, double rad2) {

		double dblSaX = Math.pow(((x1 + rad1) - (x2 + rad2)), 2);
		double dblSaY = Math.pow(((y1 + rad1) - (y2 + rad2)), 2);
		return Math.sqrt(dblSaX + dblSaY) <= rad1 + rad2;

	}

	public boolean isOutBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 - xSize || x > SYS.WINDOW_SIZE_X || y < 0 - ySize || y > SYS.WINDOW_SIZE_Y);

	}

	public boolean isTouchBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 || x > SYS.WINDOW_SIZE_X - xSize || y < 0 || y > SYS.WINDOW_SIZE_Y - ySize);

	}


}


class StgDanmakuLaser extends StgDanmaku {

	StgDanmakuLaser(){

		image = loadImage(image, "Image/lazer.png");

	}

}
