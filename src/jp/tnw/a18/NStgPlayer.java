package jp.tnw.a18;

import java.awt.Graphics2D;

import javax.swing.JFrame;

//◆自機クラス◆//
public class NStgPlayer extends NStgUnit {

	// 便利のため一応たくさんstatic変数を作ったぞ
	static double dX, dY;
	// ダメージフラッシュ
	static boolean IMMORTAL; // 無敵
	static boolean CONTROLLABLE; // 操作可能
	static int FLASHTIME; // フラッシュ中

	int flagFlash; // フラッシュするときコマ画像の切り替え
	double shiftY; // 左右移動するとき画像座標の調整

	// 手ごたえ調整
	private int timerInput;
	private int timerPowerUp;
	private int timerBombing;

	// 自機の向き(主にマウス操作向け)
	private int timerFacing;
	private double facingAngle;
	private double facingX;
	private double facingY;

	// 自機のステータス
	static int HP, MAXHP, BOMB, MAXBOMB, POWER, MAXPOWER;
	static double hitCir;
	static boolean STOPSHOOT;
	static boolean BOMBING;
	static boolean CONFUSING;

	// 初期化
	NStgPlayer() {

		super();
		komaImage = new KomaImage("Image/jiki2.png", 2, 5);
		imageIndex[0] = 1;
		isVisible[0] = true;
		opacity[0] = 1.0f;
		dX = SYS.WINDOW_SIZE_X / 2 - 48;
		dY = SYS.WINDOW_SIZE_Y;

		isHitable[0] = true;
		hitCir = 2;
		hitBoxW[0] = 96;
		hitBoxH[0] = 96;

		flagFlash = 0;
		shiftY = 0;
		facingAngle = 0;
		facingX = dX;
		facingY = dY;

		HP = 5;
		MAXHP = 5;
		BOMB = 2;
		MAXBOMB = 10;
		POWER = 2;
		MAXPOWER = 10;
		IMMORTAL = false;
		FLASHTIME = 0;
		STOPSHOOT = true;

		CONTROLLABLE = false;

		timerInput = 0;
		timerFacing = 0;
		timerBombing = 0;
		BOMBING = false;

		CONFUSING = false;

		// デバッグ用////////////////
		timerPowerUp = 0;
		// デバッグ用////////////////

	}

	public void update() {

		// 制御
		HP = HP > MAXHP ? MAXHP : HP;
		HP = HP < 0 ? 0 : HP;
		BOMB = BOMB > MAXBOMB ? MAXBOMB : BOMB;
		BOMB = BOMB < 0 ? 0 : BOMB;
		POWER = POWER > MAXPOWER ? MAXPOWER : POWER;
		POWER = POWER < 0 ? 0 : POWER;

		if (CONTROLLABLE) {
			playerControl();
		} else {
			imageIndex[0] = 1;
		}
		playerEffects();

		// デバッグ用////////////////

		if (timerBombing >= 0) {

			if (timerBombing == 0) {

				BOMBING = false;

			}

		} else if ((Input.M_RC || Input.M_LC) && timerPowerUp < 0) {

			NStgPlayer.POWER = NStgPlayer.POWER >= 5 ? 0 : NStgPlayer.POWER + 1;
			timerPowerUp = 30;
			// SYS.SCREEN_FREEZING = !SYS.SCREEN_FREEZING;
		}
		// デバッグ用///////////////

		if (Input.K_X && timerBombing < -60 && CONTROLLABLE) {
			timerBombing = 240;
			BOMBING = true;
		}

		timerBombing = timerBombing >= -100 ? timerBombing - 1 : -100;
		timerPowerUp = timerPowerUp >= -100 ? timerPowerUp - 1 : -100;

	}

	public void draw(Graphics2D g, JFrame wind) {
		drawKoma(g, wind, komaImage, imageIndex[0] + flagFlash, dX, dY + shiftY, opacity[0]);
	}

	public static void damage(int qty) {

		if (!IMMORTAL && !SYS.GAMEOVERING) {

			boolean isHeal;
			isHeal = (HP < (HP - qty)) ? true : false;

			HP -= qty;
			HP = HP > MAXHP ? MAXHP : HP;
			HP = HP < 0 ? 0 : HP;

			if (!isHeal) {
				FLASHTIME = 150;
				IMMORTAL = true;
			}

		}

		if (HP == 0) {
			SYS.GAMEOVERING = true;
			FLASHTIME = 5000;
			IMMORTAL = true;
		}

	}

	private void playerControl() {

		double spd = Input.K_SHIFT ? 3.2 : 7.2;// SHIFTを押すとスピード落とす
		int delay = 4;// 操作のディレイ
		int radian = 0;// 向きの角度

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
			if (CONFUSING){
				radian += 180;
			}

			if (Input.DIR8 > 0) {
				// キーボード
				if (timerInput < delay) {
					dX += spd / 2 * Math.cos(radian * 1.0 / 180.0 * Math.PI);
					dY += spd / 2 * Math.sin(radian * 1.0 / 180.0 * Math.PI);
					timerInput += 1;
				} else {
					dX += spd * Math.cos(radian * 1.0 / 180.0 * Math.PI);
					dY += spd * Math.sin(radian * 1.0 / 180.0 * Math.PI);
				}
			}
		} else {
			// マウスの操作と手ごたえ調整
			if (Math.abs(dX - (Input.M_X - 48)) < 30) {
				dX = Input.M_X - 48;
			} else {
				dX += ((Input.M_X - 48) - dX) / 3;
			}
			if (Math.abs(dY - (Input.M_Y - 48)) < 30) {
				dY = Input.M_Y - 48;
			} else {
				dY += ((Input.M_Y - 48) - dY) / 3;
			}
		}

		// 画面外に行かないように
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
				facingX = dX;
				facingY = dY;
			}
		}

		// 向きをチェック
		if (timerFacing == 0 && (facingX != dX || facingY != dY)) {

			double temp = Math.atan2(facingY - dY, facingX - dX);
			facingAngle = temp / Math.PI * 180;
			if (facingAngle <= 60 && facingAngle >= -60) {
				imageIndex[0] = 5;
				shiftY = 14;
			} else if (facingAngle > 60 && facingAngle < 120) {
				imageIndex[0] = 1;
				shiftY = 0;
			} else if (facingAngle >= 120 || facingAngle <= -120) {
				imageIndex[0] = 3;
				shiftY = 14;
			} else if (facingAngle < -60 && facingAngle > -120) {
				imageIndex[0] = 1;
				shiftY = 0;
			}

			if (SYS.MOUSE_CONTROLING) {
				timerFacing = 8;
			} else {
				timerFacing = 0;
			}

			facingX = dX;
			facingY = dY;

		} else {

			if (timerFacing == 0) {
				imageIndex[0] = 1;
				shiftY = 0;
				timerFacing = 3;
			}
			facingAngle = 0;
		}

		// タイマー処理
		if (Input.DIR8 == 0) {
			timerInput = timerInput <= 0 ? 0 : timerInput - 1;
		}
		timerFacing = timerFacing == 0 ? 0 : timerFacing - 1;

	}

	private void playerEffects() {

		// 自機爆発
		if (SYS.GAMEOVERING) {

			STOPSHOOT = true;
			CONTROLLABLE = false;
			VFX.request(dX + 48 + (Math.random() * 200 - 100), dY + 60 + (Math.random() * 200 - 100),
					(int) (Math.random() * 9));

		}

		// 自機フラッシュ
		if (FLASHTIME > 0) {
			flagFlash = 0;
			if (FLASHTIME > 50) {
				if (FLASHTIME % 8 == 0) {
					if (flagFlash == 1) {
						flagFlash = 0;
					} else {
						flagFlash = 1;
					}
				}
			} else {
				if (FLASHTIME % 3 == 0) {
					if (flagFlash == 1) {
						flagFlash = 0;
					} else {
						flagFlash = 1;
					}
				}
			}
			FLASHTIME--;
		} else {

			IMMORTAL = false;

		}

	}

}
