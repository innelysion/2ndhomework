package jp.tnw.a18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class StgPlayer extends StgImage {

	BufferedImage image;
	int widthBlock = 2;
	int heightBlock = 5;
	int UNIT_MAX = 1;

	// 画像の切り替え用の変数
	int imageIndex;
	// 可視スイッチ
	boolean isVisible;
	float opacity;

	// 座標
	static double dX;
	static double dY;

	// 当たり判定
	static boolean isHitable;
	static double hitCir;
	static double hitBoxW;
	static double hitBoxH;

	// 自機のやつら
	static int HP, MAXHP, BOMB, energy;
	int timerInput;
	int timerFacing;
	static int timerFlash;
	int flagFlash;
	double angle;
	double faceX;
	double faceY;

	StgPlayer() {

		image = loadImage(image, "Image/jiki2.png");
		HP = 100;
		MAXHP = 100;
		BOMB = 0;
		energy = 0;
		dX = 512;
		dY = 400;
		faceX = dX;
		faceY = dY;
		imageIndex = 1;
		isHitable = false;
		opacity = 1.0f;
		isVisible = false;
		hitCir = 24;
		hitBoxW = 96;
		hitBoxH = 96;
		timerInput = 0;

	}

	public void update(VFX bom) {

		HP = HP > MAXHP ? MAXHP : HP;
		HP = HP < 0 ? 0 : HP;

		if (SYS.GAMEOVERING) {

			bom.request(dX + 48 + (Math.random() * 100 - 50), dY + 60 + (Math.random() * 100 - 50),
					(int) (Math.random() * 9));

		}

		double SPD = Input.K_SHIFT ? 3.2 : 7.2;
		double SPDM = 0.75; // 斜方向スピード緩め
		int DELAY = 4;
		int radian = 0;

		if (!SYS.MOUSE_CONTROLING) {
			switch (Input.DIR8) {

			case 0:
				radian = 400;
				break;
			case 1:
				radian = 180;
				break;
			case 3:
				radian = 270;
				break;
			case 5:
				radian = 0;
				break;
			case 7:
				radian = 90;
				break;
			case 2:
				radian = 180 + 45;
				break;
			case 4:
				radian = 270 + 45;
				break;
			case 6:
				radian = 0 + 45;
				break;
			case 8:
				radian = 90 + 45;
				break;
			}

			// case 1:
			// if (timerInput > 0) {
			// dX += -SPD;
			// }
			// timerInput = DELAY;
			// break;
			// case 3:
			// if (timerInput > 0) {
			// dY += -SPD;
			// }
			// timerInput = DELAY;
			// break;
			// case 5:
			// if (timerInput > 0) {
			// dX += SPD;
			// }
			// timerInput = DELAY;
			// break;
			// case 7:
			// if (timerInput > 0) {
			// dY += SPD;
			// }
			// timerInput = DELAY;
			// break;
			// case 2:
			// dX += -(SPD * SPDM);
			// dY += -(SPD * SPDM);
			// timerInput = DELAY;
			// break;
			// case 4:
			// dX += (SPD * SPDM);
			// dY += -(SPD * SPDM);
			// timerInput = DELAY;
			// break;
			// case 6:
			// dX += (SPD * SPDM);
			// dY += (SPD * SPDM);
			// timerInput = DELAY;
			// break;
			// case 8:
			// dX += -(SPD * SPDM);
			// dY += (SPD * SPDM);
			// timerInput = DELAY;
			// break;
			// }
			if (Input.DIR8 > 0) {
				if (timerInput < DELAY) {
					dX += SPD / 2 * Math.cos(radian * 1.0 / 180.0 * Math.PI);
					dY += SPD / 2 * Math.sin(radian * 1.0 / 180.0 * Math.PI);
					timerInput += 1;
				} else {
					dX += SPD * Math.cos(radian * 1.0 / 180.0 * Math.PI);
					dY += SPD * Math.sin(radian * 1.0 / 180.0 * Math.PI);
				}
			}
		} else {
			dX = Input.M_X - 48;
			dY = Input.M_Y - 48;
		}

		if (dX < 0) {
			dX = 0;
		}
		if (dX > SYS.WINDOW_SIZE_X - 96) {
			dX = SYS.WINDOW_SIZE_X - 96;
		}
		if (dY < 0) {
			dY = 0;
		}
		if (dY > SYS.WINDOW_SIZE_Y - 96) {
			dY = SYS.WINDOW_SIZE_Y - 96;
			if (dX < 1) {
				faceX = dX;
				faceY = dY;
			}
		}

		// Check face direction
		if (timerFacing == 0 && (faceX != dX || faceY != dY)) {

			double temp = Math.atan2(faceY - dY, faceX - dX);
			angle = temp / Math.PI * 180;
			if (angle <= 60 && angle >= -60) {
				imageIndex = 5;
			} else if (angle > 60 && angle < 120) {
				imageIndex = 1;
			} else if (angle >= 120 || angle <= -120) {
				imageIndex = 3;
			} else if (angle < -60 && angle > -120) {
				imageIndex = 1;
			}

			if (SYS.MOUSE_CONTROLING) {
				timerFacing = 8;
			} else {
				timerFacing = 0;
			}

			faceX = dX;
			faceY = dY;

		} else {

			if (timerFacing == 0) {
				imageIndex = 1;
				timerFacing = 3;
			}
			angle = 0;
		}

		timerFacing = timerFacing == 0 ? 0 : timerFacing - 1;
		if (Input.DIR8 == 0) {
			timerInput = timerInput <= 0 ? 0 : timerInput - 1;
		}

		if (timerFlash > 0) {
			if (timerFlash > 50) {
				if (timerFlash % 8 == 0) {
					if (flagFlash == 1) {
						flagFlash = 0;
					} else {
						flagFlash = 1;
					}
				}
			} else {
				if (timerFlash % 3 == 0) {
					if (flagFlash == 1) {
						flagFlash = 0;
					} else {
						flagFlash = 1;
					}
				}
			}
			timerFlash--;
		}

	}

	public static void damage(int qty) {
		if (!SYS.GAMEOVERING) {
			boolean isHeal;
			isHeal = (HP < (HP - qty)) ? true : false;

			HP -= qty;

			HP = HP > MAXHP ? MAXHP : HP;
			HP = HP < 0 ? 0 : HP;
			if (!isHeal) {
				timerFlash = 150;
			}
		}

		if (HP == 0) {
			SYS.GAMEOVERING = true;
			timerFlash = 5000;
		}

	}

	public void drawImage(Graphics2D g, JFrame wind) {
		if (isVisible) {
			this.drawImage(g, wind, image, widthBlock, heightBlock, imageIndex + flagFlash, opacity, dX, dY);

		}
	}
}
