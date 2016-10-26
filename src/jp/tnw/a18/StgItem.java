package jp.tnw.a18;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class StgItem extends StgUnitBase {

	int widthBlock = 1;
	int heightBlock = 12;
	int UNIT_MAX = 10;
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

	StgItem() {

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
			hitCir[i] = 16;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

		}

	}

	public void request(double x, double y) {

		timerReq[0] = timerReq[0] - SYS.FRAME_TIME;
		if (timerReq[0] < 0) {
			timerReq[0] = 0.01;


			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0) { // ONLY FOR WATING OBJECT

					imageIndex[i] = 1;
					dX[i] = x;
					dY[i] = y;
					spdX[i] = 0;
					spdY[i] = 100;
					accX[i] = 0;
					accY[i] = 50;
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;


						break;

				} // if end

			} // for end

		}

	}

	public void update() {

		//request(500,500);

		for (int i = 0; i < UNIT_MAX; i++) {

			spdX[i] += SYS.FRAME_TIME * accX[i];
			spdY[i] += SYS.FRAME_TIME * accY[i];
			dX[i] += SYS.FRAME_TIME * spdX[i];
			dY[i] += SYS.FRAME_TIME * spdY[i];

			if (isHitable[i] && isHit(dX[i], dY[i], StgPlayer.dX, StgPlayer.dY, 20, 48)) {
				StgPlayer.energy = StgPlayer.energy >= 7 ? 7 : StgPlayer.energy + 1;
				StgPlayer.damage(-10);
				isVisible[i] = false;
				isHitable[i] = false;
				flag[i] = 0;
			}

		}

	}

	public boolean isHit(double x1, double y1, double x2, double y2, double rad1, double rad2) {

		double dblSaX = Math.pow(((x1 + rad1) - (x2 + rad2)), 2);
		double dblSaY = Math.pow(((y1 + rad1) - (y2 + rad2)), 2);
		return Math.sqrt(dblSaX + dblSaY) <= rad1 + rad2;

	}

	public void drawImage(Graphics2D g, JFrame wind){
		this.drawImage(g, wind, widthBlock, heightBlock, UNIT_MAX, imageIndex, isVisible, opacity, dX, dY, 0);
	}

}
