package jp.tnw.a18;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class StgBullet extends StgObject {

	int widthBlock = 20;
	int heightBlock = 10;
	int UNIT_MAX = 1000;
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

	double optionX[] = { 0, 0, 0, 0 };
	double optionY[] = { 0, 0, 0, 0 };
	double optionAngle[] = { 0, 90, 180, 270 };

	StgMap map;

	StgBullet() {

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

	void request(VFX bom) {

		timerReq[0] = timerReq[0] - Sys.frameTime;
		if (timerReq[0] < 0) {
			timerReq[0] = StgPlayer.energy == 7 ? 0.05 : 0.1;

			if (StgPlayer.energy == 0) {

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = 1;
						imageIndex[i] = 1;
						dX[i] = StgPlayer.dX + 48 - 8;
						dY[i] = StgPlayer.dY + 48 - 8;
						spdX[i] = -100 + (Input.K_SHIFT ? Math.random() * 200 : 100);
						spdY[i] = -550;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;

						break;
					}
				}

			} else if (StgPlayer.energy == 1) {

				int cnt = 2;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = 2;
						imageIndex[i] = 1;
						dX[i] = StgPlayer.dX + 48 - 8 - 45 + (30 * cnt);
						dY[i] = StgPlayer.dY + 48 - 8;
						spdX[i] = -100 + (Input.K_SHIFT ? Math.random() * 200 : 100);
						spdY[i] = -570;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}

			} else if (StgPlayer.energy == 2) {

				int cnt = 3;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = 2;
						imageIndex[i] = 1;
						dX[i] = StgPlayer.dX + 48 - 8 - 40 + (20 * cnt);
						dY[i] = StgPlayer.dY + 48 - 8;
						spdX[i] = -100 + (Input.K_SHIFT ? Math.random() * 200 : 100);
						spdY[i] = -570;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}

			} else if (StgPlayer.energy == 3) {

				int cnt = 3;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = 2;
						imageIndex[i] = 49;
						dX[i] = StgPlayer.dX + 48 - 8 - 40 + (20 * cnt);
						dY[i] = StgPlayer.dY + 48 - 8;
						spdX[i] = -300 + (150 * cnt);
						spdY[i] = -570;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}

			} else if (StgPlayer.energy == 4) {

				int cnt = 4;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = 2;
						imageIndex[i] = 49;
						dX[i] = StgPlayer.dX + 48 - 8 - 50 + (20 * cnt);
						dY[i] = StgPlayer.dY + 48 - 8;
						spdX[i] = -500 + (200 * cnt);
						spdY[i] = -570;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}

			} else if (StgPlayer.energy == 5) {

				int cnt = 4;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = 3;
						imageIndex[i] = 49;

						dX[i] = StgPlayer.dX + (48 - 14) + (3 * cnt);
						dY[i] = StgPlayer.dY + 48 + 5;
						if (Input.K_SHIFT) {
							spdX[i] = 50 * (cnt - 2) * Math.sin(angle[i]);
						} else {
							spdX[i] = 200 * (cnt - 2) * Math.cos(angle[i]);
						}
						spdY[i] = -600;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}

			} else if (StgPlayer.energy == 6) {

				int cnt = 4;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = Input.K_SHIFT ? 5 : 4;
						imageIndex[i] = 21;

						spdX[i] = 0;
						spdY[i] = 0;
						accY[i] = -2000;

						dX[i] = optionX[Math.abs(cnt - 4)];
						dY[i] = optionY[Math.abs(cnt - 4)];
						if (Input.K_SHIFT) {
							bom.bomb_req(dX[i] + 8, dY[i] + 8, 4);
						} else {
							bom.bomb_req(dX[i] + 8, dY[i] + 8, 5);
						}
						if (dX[i] < Sys.windowSizeX - 16) {
							isVisible[i] = true;
							isHitable[i] = true;
							flag[i] = 1;
						}
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}

			} else if (StgPlayer.energy == 7) {

				int cnt = 4;

				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0 && bulletType[i] == 0) {

						bulletType[i] = Input.K_SHIFT ? 7 : 6;
						imageIndex[i] = 163;
						dX[i] = StgPlayer.dX + 48 - 8;
						dY[i] = StgPlayer.dY + 48 - 8;
						spdX[i] = 0;
						spdY[i] = Input.K_SHIFT ? 100 + Math.random() * 200 : -500;
						accX[i] = Input.K_SHIFT ? -1200 + Math.random() * 2400 : -800 + Math.random() * 1600;
						accY[i] = Input.K_SHIFT ? -500 : 0;
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 2;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}
			}
		}
	}

	void update(VFX bom) {

		timerReq[1]--;

		if ((Sys.isPlayerMouseControl) || (!Sys.isPlayerMouseControl && Input.K_Z)) {
			request(bom);
		}

		if ((Input.K_X || Input.M_RC || Input.M_LC) && timerReq[1] < 0) {

			StgPlayer.energy = StgPlayer.energy >= 7 ? 0 : StgPlayer.energy + 1;
			timerReq[1] = 30;
			//Sys.isScreenFreeze = !Sys.isScreenFreeze;
		}

		for (int i = 0; i < 4; i++) {
			if (Input.K_SHIFT) {
				optionX[i] = 30 * Math.cos(Math.toRadians(optionAngle[i]))
						- 30 * Math.sin(Math.toRadians(optionAngle[i])) + (int) StgPlayer.dX + 48 - 8;
				optionY[i] = 30 * Math.sin(Math.toRadians(optionAngle[i]))
						+ 30 * Math.cos(Math.toRadians(optionAngle[i])) + (int) StgPlayer.dY + 48 - 8;
				optionAngle[i] += 2.5;
			} else {
				optionX[i] = 70 * Math.cos(Math.toRadians(optionAngle[i]))
						- 70 * Math.sin(Math.toRadians(optionAngle[i])) + (int) StgPlayer.dX + 48 - 8;
				optionY[i] = 70 * Math.sin(Math.toRadians(optionAngle[i]))
						+ 70 * Math.cos(Math.toRadians(optionAngle[i])) + (int) StgPlayer.dY + 48 - 8;
				optionAngle[i] += 2.5;
			}
		}

		for (int i = 0; i < UNIT_MAX; i++) {

			spdX[i] += Sys.frameTime * accX[i];
			spdY[i] += Sys.frameTime * accY[i];
			dX[i] += Sys.frameTime * spdX[i];
			dY[i] += Sys.frameTime * spdY[i];
			angle[i]++;

			if (bulletType[i] == 4 && (dX[i] < Sys.windowSizeX - 16)) {

				bom.bomb_req(dX[i] + 8, dY[i] + 8, 7);

			} else if (bulletType[i] == 5 && (dX[i] < Sys.windowSizeX - 16)) {

				bom.bomb_req(dX[i] + 8, dY[i] + 8, 6);

			} else if (bulletType[i] == 6) {

				bom.bomb_req(dX[i] + 8, dY[i] + 8, 5);

			} else if (bulletType[i] == 7) {

				bom.bomb_req(dX[i] + 8, dY[i] + 8, 4);
			}

			if (flag[i] == 2) {
				dX[i] += Math.random() * 20;
			} else if (flag[i] == 3) {
				dX[i] -= Math.random() * 20;
			}

			if (timerReq[3] < 0) {
				timerReq[3] = 8;
				if (flag[i] == 2) {
					flag[i] = 3;
				} else if (flag[i] == 3) {
					flag[i] = 2;
				}
			}
			timerReq[3]--;

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
				flag[i] = 0;

			}
			if (flag[i] > 0 && isVisible[i] && map.isMapHit(dX[i] + 8, dY[i] + 8)) {
				isVisible[i] = false;
				isHitable[i] = false;
				imageIndex[i] = 1;
				dX[i] = 9999;
				spdX[i] = 0;
				spdY[i] = 0;
				accX[i] = 0;
				accY[i] = 0;
				bulletType[i] = 0;
				flag[i] = 0;
			}

		}

	}

	public void drawImage(Graphics2D g, JFrame wind) {
		this.drawImage(g, wind, widthBlock, heightBlock, UNIT_MAX, imageIndex, isVisible, opacity, dX, dY, 0);
	}

}
