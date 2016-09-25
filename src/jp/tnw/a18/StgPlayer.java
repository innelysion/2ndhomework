package jp.tnw.a18;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class StgPlayer extends StgObject {

	int widthBlock = 2;
	int heightBlock = 5;
	int UNIT_MAX = 1;

	// 画像の切り替え用の変数
	int imageIndex[] = new int[1];
	// 可視スイッチ
	boolean isVisible[] = new boolean[1];
	float opacity[] = new float[1];

	// 座標
	static double dX[] = new double[1];
	static double dY[] = new double[1];

	// 当たり判定
	static boolean isHitable[] = new boolean[1];
	static double hitCir[] = new double[1];
	static double hitBoxW[] = new double[1];
	static double hitBoxH[] = new double[1];

	// 自機のやつら
	static int life, bomb, energy;
	int timerInput;
	int timerFacing;
	double angle;
	double faceX[] = new double[1];
	double faceY[] = new double[1];

	StgPlayer() {

		life = bomb = energy = 0;
		dX[0] = 512;
		dY[0] = 400;
		faceX[0] = dX[0];
		faceY[0] = dY[0];
		imageIndex[0] = 1;
		isHitable[0] = false;
		opacity[0] = 1.0f;
		isVisible[0] = true;
		hitCir[0] = 24;
		hitBoxW[0] = 96;
		hitBoxH[0] = 96;
		timerInput = 0;

	}

	public void update() {

		double SPD = Input.K_SHIFT ? 3.2 : 7.2;
		double SPM = 0.9;
		int DELAY = 2;

		if (!Sys.isPlayerMouseControl) {
			switch (Input.DIR8) {
			case 1:
				if (timerInput > 0) {
					dX[0] += -SPD;
				} else {
					dX[0] += -SPD / 4;
				}
				timerInput = DELAY;
				break;
			case 3:
				if (timerInput > 0) {
					dY[0] += -SPD;
				} else {
					dY[0] += -SPD / 4;
				}
				timerInput = DELAY;
				break;
			case 5:
				if (timerInput > 0) {
					dX[0] += SPD;
				} else {
					dX[0] += SPD / 4;
				}
				timerInput = DELAY;
				break;
			case 7:
				dY[0] += SPD;
				timerInput = DELAY;
				break;
			case 2:
				dX[0] += -(SPD * SPM);
				dY[0] += -(SPD * SPM);
				timerInput = DELAY;
				break;
			case 4:
				dX[0] += (SPD * SPM);
				dY[0] += -(SPD * SPM);
				timerInput = DELAY;
				break;
			case 6:
				dX[0] += (SPD * SPM);
				dY[0] += (SPD * SPM);
				timerInput = DELAY;
				break;
			case 8:
				dX[0] += -(SPD * SPM);
				dY[0] += (SPD * SPM);
				timerInput = DELAY;
				break;
			}

		} else {
			dX[0] = Input.M_X - 48;
			dY[0] = Input.M_Y - 48;
		}

		if (dX[0] < 0) {
			dX[0] = 0;
		}
		if (dX[0] > Sys.windowSizeX - 96) {
			dX[0] = Sys.windowSizeX - 96;
		}
		if (dY[0] < 0) {
			dY[0] = 0;
		}
		if (dY[0] > Sys.windowSizeY - 96) {
			dY[0] = Sys.windowSizeY - 96;
		}

		// Check face direction
		if (timerFacing == 0 && (faceX[0] != dX[0] || faceY[0] != dY[0])) {

			double temp = Math.atan2(faceY[0] - dY[0], faceX[0] - dX[0]);
			angle = temp / Math.PI * 180;
			if (angle <= 60 && angle >= -60) {
				imageIndex[0] = 5;
			} else if (angle > 60 && angle < 120) {
				imageIndex[0] = 1;
			} else if (angle >= 120 || angle <= -120) {
				imageIndex[0] = 3;
			} else if (angle < -60 && angle > -120) {
				imageIndex[0] = 1;
			}

			if (Sys.isPlayerMouseControl){
				timerFacing = 8;
			}else{
				timerFacing = 3;
			}

			faceX[0] = dX[0];
			faceY[0] = dY[0];

		} else {

			if (timerFacing == 0){
				imageIndex[0] = 1;
				timerFacing = 3;
			}
			angle = 0;
		}
		timerFacing = timerFacing == 0 ? 0 : timerFacing - 1;
		timerInput = timerInput <= 0 ? 0 : timerInput - 1;

	}

	public void drawImage(Graphics2D g, JFrame wind) {
		this.drawImage(g, wind, widthBlock, heightBlock, UNIT_MAX, imageIndex, isVisible, opacity, dX, dY, 0);
	}
}
