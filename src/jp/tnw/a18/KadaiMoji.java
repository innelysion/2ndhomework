package jp.tnw.a18;

public class KadaiMoji extends StgEnemy {

	// パターン、メンバーの順番、リセット用カンター、
	int type, wave, endCount;
	// 追加の容器
	double exPlus[];

	int[][] name = { { 47, 48, 58, 48, 41, 11, 47, 34, 44, 10, 18, 22, 17, 20, 17, 23, 18, 19, 9, 15, 56, 47, 53, 999 },
			{ 34, 52, 34, 58, 34, 11, 34, 40, 34, 41, 10, 17, 22, 17, 20, 17, 23, 18, 19, 9, 15, 56, 47, 53, 999 },
			{ 86, 71, 79, 66, 44, 11, 86, 80, 90, 36, 10, 20, 21, 17, 20, 17, 23, 18, 19, 9, 15, 56, 47, 53, 999 },
			{ 77, 74, 78, 38, 11, 79, 80, 84, 84, 79, 70, 85, 83, 66, 66, 46, 10, 26, 22, 17, 20, 17, 23, 18, 19, 9, 15,
					56, 47, 53, 999 } };

	// for pattern 2
	int[] longname = { 53, 47, 56, 15, 9, 19, 18, 23, 17, 20, 17, 22, 18, 10, 44, 34, 47, 11, 41, 48, 58, 48, 47, 27,
			53, 47, 56, 15, 9, 19, 18, 23, 17, 20, 17, 22, 17, 10, 41, 34, 40, 34, 11, 34, 58, 34, 52, 34, 27, 53, 47,
			56, 15, 9, 19, 18, 23, 17, 20, 17, 21, 20, 10, 36, 90, 80, 86, 11, 44, 66, 79, 71, 86, 27, 53, 47, 56, 15,
			9, 19, 18, 23, 17, 20, 17, 22, 26, 10, 46, 66, 66, 83, 85, 70, 79, 84, 84, 80, 79, 11, 38, 78, 74, 77,
			999 };

	// TNW.(21603051)KAN*HOYON
	// TNW.(21603050)HAGA*AYASA
	// TNW.(21603043)Cyou*Kanfu
	// TNW.(21603059)Maartensson*Emil

	KadaiMoji(int t) {
		UNIT_MAX = 255;
		exPlus = new double[UNIT_MAX];
		type = t;
		wave = 0;
		endCount = 0;
		widthBlock = 16;
		heightBlock = 24;
		for (int i = 0; i < UNIT_MAX; i++) {
			flag[i] = 0;
			dX[i] = 9999;
			hitCir[i] = 10;
			hitBoxW[i] = 20;
			hitBoxH[i] = 20;
			exPlus[i] = 0;
		}
	}

	public void request(int t) {

		switch (t) {

		// ◆パターン1////////////////////////////////////////////////////////

		case 0:
			if (wave < 4) {
				int textIndex = 0;
				for (int i = 0; i < UNIT_MAX; i++) {
					if (flag[i] == 0) {
						if (name[wave][textIndex] == 999) {
							// 次のメンバー名を処理、文字列カンターをリセット
							textIndex -= (name[wave].length - 1);
							wave++;
							if (wave > 3) {
								// 全処理完了
								break;
							}
						}
						imageIndex[i] = (name[wave][textIndex] + 96 * wave);
						dX[i] = 150 + (Sys.windowSizeX - 300) / 3 * wave;
						dY[i] = 150 + (Sys.windowSizeY - 300) / 3 * wave;
						angle[i] = textIndex * 360 / (name[wave].length - 1);
						spdX[i] = 80 * Math.cos(Math.toRadians(angle[i]));
						spdY[i] = 80 * Math.sin(Math.toRadians(angle[i]));
						isVisible[i] = true;
						isHitable[i] = true;
						flag[i] = 1;
						textIndex++;
					}
				}
			}
			break;

		// ◆パターン2////////////////////////////////////////////////////////

		case 1:
			if (wave < 4) {
				timerReq[0] = timerReq[0] - Sys.frameTime;
				if (timerReq[0] < 0) {
					timerReq[0] = 0.09;
					for (int i = 0; i < UNIT_MAX; i++) {
						if (flag[i] == 0) {
							if (longname[i] == 999) {
								break;
							}
							if (longname[i] == 27) {
								wave++;
							}
							imageIndex[i] = longname[i] + wave * 96;
							dX[i] = Sys.windowSizeX;
							dY[i] = Sys.windowSizeY / 2 - 10;
							spdX[i] = -960 / 4;
							angle[i] = 0;
							isVisible[i] = true;
							isHitable[i] = true;
							flag[i] = 1;
							break;
						}
					}
				}
			}
			break;

		// ◆パターン3////////////////////////////////////////////////////////

		case 2:
			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0) {
					if (name[wave][i] == 999) {
						break;
					}
					imageIndex[i] = name[wave][i] + wave * 96;
					dX[i] = Sys.windowSizeX / 2 - 10;
					dY[i] = -10 - (i * 15);
					centerX[i] = dX[name[wave].length / 2];
					centerY[i] = dY[name[wave].length / 2];
					if (i < name[wave].length / 2) {
						// 後半の文字
						angle[i] = 225;
						axisX[i] = 15 * (name[wave].length / 2 + 1 - (i + 1));
						axisY[i] = 15 * (name[wave].length / 2 + 1 - (i + 1));
					} else if (i > name[wave].length / 2) {
						// 前半の文字
						angle[i] = 45;
						axisX[i] = 15 * (i - (name[wave].length / 2));
						axisY[i] = 15 * (i - (name[wave].length / 2));
					} else {
						// 円心の文字
						axisX[i] = 0;
						axisY[i] = 0;
					}
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;
				}
			}
			break;

		// ◆パターン4////////////////////////////////////////////////////////

		case 3:
			for (int i = 0; i < UNIT_MAX; i++) {
				if (flag[i] == 0) {
					if (name[wave][i] == 999) {
						break;
					}
					imageIndex[i] = name[wave][i] + wave * 96;
					dX[i] = -(i * 15);
					dY[i] = 15;
					centerX[i] = dX[i];
					centerY[i] = dY[i];
					if (i < name[wave].length - 2) {
						// 円心以外の文字
						angle[i] = 315;
						axisX[i] = 15 * (name[wave].length - 2 + 1 - (i + 1));
						axisY[i] = 15 * (name[wave].length - 2 + 1 - (i + 1));
					} else {
						// 円心の文字
						axisX[i] = 0;
						axisY[i] = 0;
					}
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;
				}
			}
			break;
		}
	}

	public void update(StgBullet bullet, VFX bom) {

		request(type);
		switch (type) {

		// ◆パターン1////////////////////////////////////////////////////////

		case 0:
			for (int i = 0; i < UNIT_MAX; i++) {
				if (isTouchVertical(i)) {
					if (Math.random() > 0.9 && (spdY[i] < 300 && spdY[i] > -300)) {
						spdY[i] *= -2;
					} else {
						spdY[i] *= -1;
					}
					flag[i] = flag[i] > 2 ? flag[i] : 2;
				}
				if (isTouchHorizontal(i)) {
					if (Math.random() > 0.9 && (spdX[i] < 300 && spdX[i] > -300)) {
						spdX[i] *= -2;
					} else {
						spdX[i] *= -1;
					}
					flag[i] = flag[i] > 2 ? flag[i] : 2;
				}
				switch (flag[i]) {
				case 1:
				case 2:
					dX[i] += spdX[i] * Sys.frameTime;
					dY[i] += spdY[i] * Sys.frameTime;
					break;
				case 3:
					if (imageIndex[i] >= 0 && imageIndex[i] <= 96) {
						endCount++;
						flag[i] = 4;
						dX[i] = 0;
						dY[i] = (Sys.windowSizeY / 2 + (name[0].length - 2) * 10) - 20 * i;
					} else if (imageIndex[i] >= 97 && imageIndex[i] <= 192) {
						endCount++;
						flag[i] = 4;
						dY[i] = Sys.windowSizeY - 20;
						dX[i] = (Sys.windowSizeX / 2 + (name[1].length - 2) * 10) - 20 * (i - (name[0].length - 1));
					} else if (imageIndex[i] >= 193 && imageIndex[i] <= 288) {
						endCount++;
						flag[i] = 4;
						dX[i] = Sys.windowSizeX - 20;
						dY[i] = (Sys.windowSizeY / 2 + (name[2].length - 2) * 10)
								- 20 * (i - (name[0].length - 1) - (name[1].length - 1));
					} else if (imageIndex[i] >= 289 && imageIndex[i] <= 384) {
						endCount++;
						flag[i] = 4;
						dY[i] = 0;
						dX[i] = (Sys.windowSizeX / 2 + (name[3].length - 2) * 10)
								- 20 * (i - (name[0].length - 1) - (name[1].length - 1) - (name[2].length - 1));
					}
					break;
				}
				// バレットの処理
				for (int j = 0; j < 1000; j++) {
					if (isHitable[i] && isHit(dX[i], dY[i], bullet.dX[j], bullet.dY[j], 10, 4)) {
						switch (flag[i]) {
						case 1:
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 2:
							flag[i] = 3;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 3:
						case 4:
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						}
					}
				}
			}
			// 全てのオブジェクトをリセット
			if (endCount >= name[0].length + name[1].length + name[2].length + name[3].length - 4) {
				for (int i = 0; i < UNIT_MAX; i++) {
					flag[i] = 0;
					wave = 0;
					endCount = 0;
				}
			}
			break;

		// ◆パターン2////////////////////////////////////////////////////////

		case 1:
			for (int i = 0; i < UNIT_MAX; i++) {
				dX[i] += spdX[i] * Sys.frameTime;
				dY[i] += spdY[i] * Sys.frameTime;
				switch (flag[i]) {
				case 1:
					if (dX[i] <= 0) {
						spdX[i] = (960 - 32) / 12;
						flag[i] = 2;
					}
					break;
				case 2:
					angle[i] += 2;
					exPlus[i] += 0.5;
					spdY[i] = -Math.cos(Math.toRadians(angle[i])) * (80 + exPlus[i]);
					if (dX[i] > Sys.windowSizeX - 50 && dY[i] <= Sys.windowSizeY / 2 - 10) {
						angle[i] = 0;
						exPlus[i] = 0;
						accY[i] = 0;
						dY[i] = Sys.windowSizeY / 2 - 10;
						spdX[i] = -960 / 4;
						spdY[i] = 0;
						flag[i] = 3;
					}
					break;
				case 3:
					if (dX[i] <= 0) {
						spdX[i] = (960 - 32) / 12;
						flag[i] = 2;
					}
					break;
				case 4:
					dY[i] += spdY[i] * Sys.frameTime;
					spdY[i] += accY[i] * Sys.frameTime;
					if (dY[i] > Sys.windowSizeY) {
						spdX[i] = 0;
						spdY[i] = 0;
						accY[i] = 0;
						angle[i] = 0;
						endCount++;
						flag[i] = 5;
					}
					break;
				}
				// バレットの処理
				for (int j = 0; j < 1000; j++) {
					if (isHitable[i] && isHit(dX[i], dY[i], bullet.dX[j], bullet.dY[j], 10, 4)) {
						switch (flag[i]) {
						case 1:
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 2:
						case 3:
							spdX[i] = 0;
							spdY[i] = -75;
							accY[i] = 50;
							flag[i] = 4;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 4:
							spdY[i] = -75;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						}
					}
				}
			}
			// 全てのオブジェクトをリセット
			if (endCount >= longname.length - 1) {
				wave = 0;
				endCount = 0;
				for (int i = 0; i < UNIT_MAX; i++) {
					flag[i] = 0;
					accY[i] = 0;
					exPlus[i] = 0;
				}
			}
			break;

		// ◆パターン3////////////////////////////////////////////////////////

		case 2:
			for (int i = 0; i < UNIT_MAX; i++) {
				switch (flag[i]) {
				case 1:
					accY[i] += 5;
					dX[i] = axisX[i] * Math.cos(Math.toRadians(angle[i]))
							- axisY[i] * Math.sin(Math.toRadians(angle[i])) + centerX[name[wave].length / 2];
					dY[i] = -(name[wave].length / 2 * 6) + accY[i] + axisY[i] * Math.sin(Math.toRadians(angle[i]))
							+ axisX[i] * Math.cos(Math.toRadians(angle[i])) + centerY[name[wave].length / 2];
					if (dY[name[wave].length / 2] > Sys.windowSizeY / 2 - 10) {
						for (int j = 0; j < UNIT_MAX; j++) {
							if (flag[j] == 1) {
								centerX[j] = dX[name[wave].length / 2];
								centerY[j] = dY[name[wave].length / 2];
								accY[j] = 0;
								flag[j] = 2;
							}
						}
					}
					break;
				case 2:
					angle[i] += 7 * Math.cos(Math.toRadians(exPlus[i] - 90));
					dX[i] = axisX[i] * Math.cos(Math.toRadians(angle[i]))
							- axisY[i] * Math.sin(Math.toRadians(angle[i])) + centerX[i];
					dY[i] = axisY[i] * Math.sin(Math.toRadians(angle[i]))
							+ axisX[i] * Math.cos(Math.toRadians(angle[i])) + centerY[i];
					exPlus[i] += 0.75;
					break;
				case 3:
					dY[i] += spdY[i] * Sys.frameTime;
					spdY[i] += accY[i] * Sys.frameTime;
					if (dY[i] > Sys.windowSizeY) {
						spdY[i] = 0;
						accY[i] = 0;
						angle[i] = 0;
						endCount++;
						flag[i] = 4;
					}
					break;
				}
				// バレットの処理
				for (int j = 0; j < 1000; j++) {
					if (isHitable[i] && isHit(dX[i], dY[i], bullet.dX[j], bullet.dY[j], 10, 4)) {
						switch (flag[i]) {
						case 1:
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 2:
							spdX[i] = 0;
							spdY[i] = -150;
							accX[i] = 0;
							accY[i] = 100;
							angle[i] = 0;
							centerX[i] = 0;
							centerY[i] = 0;
							axisX[i] = 0;
							axisY[i] = 0;
							flag[i] = 3;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 3:
							spdY[i] = -100;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						}
					}
				}
			}
			// 次のメンバーの処理
			if (endCount >= name[wave].length - 1) {
				wave = wave == 3 ? 0 : wave + 1;
				endCount = 0;
				for (int i = 0; i < UNIT_MAX; i++) {
					flag[i] = 0;
					accY[i] = 0;
					exPlus[i] = 0;
				}
			}
			break;

		// ◆パターン4////////////////////////////////////////////////////////

		case 3:
			for (int i = 0; i < UNIT_MAX; i++) {
				switch (flag[i]) {
				case 1:
					accX[i] += 10;
					dX[i] = -(name[wave].length - 2 * 6) + accX[i] + axisX[i] * Math.cos(Math.toRadians(angle[i]))
							- axisY[i] * Math.sin(Math.toRadians(angle[i])) + centerX[name[wave].length - 2];
					dY[i] = axisY[i] * Math.sin(Math.toRadians(angle[i]))
							+ axisX[i] * Math.cos(Math.toRadians(angle[i])) + centerY[name[wave].length - 2];
					if (dX[name[wave].length - 2] > Sys.windowSizeX / 2 - 20) {
						for (int j = 0; j < UNIT_MAX; j++) {
							if (flag[j] == 1) {
								angle[j] = -45;
								centerX[j] = dX[name[wave].length - 2];
								centerY[j] = dY[name[wave].length - 2];
								accY[j] = 0;
								flag[j] = 2;
							}
						}
					}
					break;
				case 2:
					dX[i] = axisX[i] * Math.cos(Math.toRadians(angle[i]))
							- axisY[i] * Math.sin(Math.toRadians(angle[i])) + centerX[i];
					dY[i] = axisY[i] * Math.sin(Math.toRadians(angle[i]))
							+ axisX[i] * Math.cos(Math.toRadians(angle[i])) + centerY[i];
					angle[i] += exPlus[i];
					if (angle[i] < 45) {
						exPlus[i] += 0.04;
					} else if (angle[i] > 45) {
						exPlus[i] -= 0.04;
					}
					if (angle[i] > 90) {
						exPlus[i] -= 0.02;
						if (exPlus[i] <= 0) {
							for (int j = 0; j < UNIT_MAX; j++) {
								if (flag[j] == 2) {
									angle[j] = 120;
									exPlus[j] = 0;
									flag[j] = 3;
								}
							}
							break;
						}
					}
					break;
				case 3:
					if (angle[i] < 45) {
						exPlus[i] += 0.04;
					} else if (angle[i] > 45) {
						exPlus[i] -= 0.04;
					}
					if (flag[1] == 3) {
						exPlus[0] = exPlus[1];
						angle[0] = angle[1];
					}
					dX[i] = axisX[i] * Math.cos(Math.toRadians(angle[i]))
							- axisY[i] * Math.sin(Math.toRadians(angle[i])) + centerX[i];
					dY[i] = axisY[i] * Math.sin(Math.toRadians(angle[i]))
							+ axisX[i] * Math.cos(Math.toRadians(angle[i])) + centerY[i];
					angle[i] += exPlus[i];
				case 4:
					dY[i] += spdY[i] * Sys.frameTime;
					spdY[i] += accY[i] * Sys.frameTime;
					if (dY[i] > Sys.windowSizeY) {
						spdY[i] = 0;
						accY[i] = 0;
						angle[i] = 0;
						endCount++;
						flag[i] = 5;
					}
					break;
				}
				// バレットの処理
				for (int j = 0; j < 1000; j++) {
					if (isHitable[i] && isHit(dX[i], dY[i], bullet.dX[j], bullet.dY[j], 10, 4)) {
						switch (flag[i]) {
						case 1:
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 2:
						case 3:
							spdX[i] = 0;
							spdY[i] = -150;
							accX[i] = 0;
							accY[i] = 100;
							angle[i] = 0;
							centerX[i] = 0;
							centerY[i] = 0;
							axisX[i] = 0;
							axisY[i] = 0;
							flag[i] = 4;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						case 4:
							spdY[i] = -100;
							bom.bomb_req(bullet.dX[j] + 10, bullet.dY[j] + 10, 7);
							bulletHit(bullet, j);
							break;
						}
					}
				}
			}
			// 次のメンバーの処理
			if (endCount >= name[wave].length - 1) {
				wave = wave == 3 ? 0 : wave + 1;
				endCount = 0;
				for (int i = 0; i < UNIT_MAX; i++) {
					flag[i] = 0;
					accY[i] = 0;
					exPlus[i] = 0;
				}
			}
			break;
		}

	}

	private boolean isTouchHorizontal(int i) {
		// TODO Auto-generated method stub
		return (dX[i] < 0 || dX[i] > Sys.windowSizeX - 20);
	}

	private boolean isTouchVertical(int i) {
		// TODO Auto-generated method stub
		return (dY[i] < 0 || dY[i] > Sys.windowSizeY - 20);
	}

	private void bulletHit(StgBullet bullet, int j) {
		bullet.isVisible[j] = false;
		bullet.isHitable[j] = false;
		bullet.dX[j] = 9999;
		bullet.dY[j] = 9999;
		bullet.imageIndex[j] = 1;
		bullet.spdX[j] = 0;
		bullet.spdY[j] = 0;
		bullet.accX[j] = 0;
		bullet.accY[j] = 0;
		bullet.bulletType[j] = 0;
		bullet.flag[j] = 0;
	}
}
