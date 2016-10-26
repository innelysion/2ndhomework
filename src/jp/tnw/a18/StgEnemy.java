package jp.tnw.a18;

public class StgEnemy extends GameObject {

//	BufferedImage image;
//	int widthBlock = 10;
//	int heightBlock = 10;
	int UNIT_MAX = 500;
//	// 画像の切り替え用の変数
//	int imageIndex[] = new int[UNIT_MAX];
//	// 可視スイッチ
//	boolean isVisible[] = new boolean[UNIT_MAX];
//	float opacity[] = new float[UNIT_MAX];
//	// 座標
//	double dX[] = new double[UNIT_MAX];
//	double dY[] = new double[UNIT_MAX];
	// アニメとリクエストのカウンター
	int timerAni[] = new int[UNIT_MAX];
	double timerReq[] = new double[UNIT_MAX];
	double timerShoot[] = new double[UNIT_MAX];
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
	int itemCount = 0;
	boolean itemFlag[] = new boolean[UNIT_MAX];

	StgEnemy() {

		super(500);

		for (int i = 0; i < UNIT_MAX; i++) {

			isVisible[i] = false;
			opacity[i] = 1.0f;

			dX[i] = 0;
			dY[i] = 0;

			timerAni[i] = 0;
			timerReq[i] = 0;
			timerShoot[i] = 1;

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
		for (int i = 50; i < 80; i++) {
			flag[i] = -1;
		}
		for (int i = 80; i < 130; i++) {
			flag[i] = -2;
		}

	}

	public void request() {

		timerReq[0] = timerReq[0] - SYS.FRAME_TIME;
		if (timerReq[0] < 0) {
			timerReq[0] = 0.3;

			int cnt = 2;

			for (int i = 0; i < 50; i++) {
				if (flag[i] == 0) { // ONLY FOR WATING OBJECT

					if (itemCount % 23 == 0) {
						imageIndex[i] = 11;
						itemFlag[i] = true;
					} else {
						imageIndex[i] = 1;
					}

					if (cnt == 2) {
						dX[i] = 0;
						dY[i] = 48;
						spdX[i] = 150;
						spdY[i] = 0;
					} else if (cnt == 1) {
						dX[i] = SYS.WINDOW_SIZE_X - 48;
						dY[i] = 128;
						spdX[i] = -150;
						spdY[i] = 0;
					}

					accX[i] = 0;
					accY[i] = 0;
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 1;
					itemCount++;

					cnt--;

					if (cnt == 0) {
						break;
					}
				} // if end

			} // for end


			for (int i = 80; i < 130; i++) {
				if (flag[i] == -2) { // ONLY FOR WATING OBJECT
					imageIndex[i] = 21;
					dX[i] = -20;
					dY[i] = SYS.WINDOW_SIZE_Y - 20;
					spdX[i] = 240;
					spdY[i] = -500;
					accX[i] = 0;
					accY[i] = 250;
					isVisible[i] = true;
					isHitable[i] = true;
					flag[i] = 4;
					itemCount++;
					break;
				}
			}
		} // if end

	}// method end

	public void update(StgBullet bullet, StgDanmaku danmaku, VFX bom, StgItem item) {

		request();

		for (int i = 0; i < UNIT_MAX; i++) {

			if (timerAni[i] % 8 == 0) {

				if (flag[i] == 2 || flag[i] == 3) {
					imageIndex[i] = (imageIndex[i] > 59) ? 51 : imageIndex[i] + 1;
				} else if (flag[i] == 4) {
					imageIndex[i] = (imageIndex[i] > 29) ? 21 : imageIndex[i] + 1;
				} else if (itemFlag[i]) {
					imageIndex[i] = (imageIndex[i] > 19) ? 11 : imageIndex[i] + 1;
				} else {
					imageIndex[i] = (imageIndex[i] > 9) ? 1 : imageIndex[i] + 1;
				}

			}
			timerAni[i]++;

			switch (flag[i]) {
			case 1:
				spdX[i] += SYS.FRAME_TIME * accX[i];
				spdY[i] += SYS.FRAME_TIME * accY[i];
				dX[i] += SYS.FRAME_TIME * spdX[i];
				dY[i] += SYS.FRAME_TIME * spdY[i];
				angle[i]++;
				if (isOutBorder((int) dX[i], (int) dY[i], 40, 40)) {
					dX[i] = 9999;
					isVisible[i] = false;
					isHitable[i] = false;
					flag[i] = 0;
				}
				break;
			case 2:
				spdX[i] += SYS.FRAME_TIME * accX[i];
				spdY[i] += SYS.FRAME_TIME * accY[i];
				dX[i] += SYS.FRAME_TIME * spdX[i];
				dY[i] += SYS.FRAME_TIME * spdY[i];
				if (dY[i] > SYS.WINDOW_SIZE_Y / 2) {
					spdX[i] = 0;
					spdY[i] = 0;
					accX[i] = 35;
					accY[i] = 35;
					flag[i] = 3;
				}
				break;
			case 3:
				spdX[i] += SYS.FRAME_TIME * accX[i];
				spdY[i] += SYS.FRAME_TIME * accY[i];
				dX[i] += SYS.FRAME_TIME * spdX[i] * Math.cos(Math.toRadians(angle[i]));
				dY[i] += SYS.FRAME_TIME * spdY[i] * Math.sin(Math.toRadians(angle[i]));
				angle[i] -= 2.2;
				if (isOutBorder((int) dX[i], (int) dY[i], 40, 40)) {
					dX[i] = 9999;
					isVisible[i] = false;
					isHitable[i] = false;
					flag[i] = 0;
				}
				break;
			case 4:
				spdX[i] += SYS.FRAME_TIME * accX[i];
				spdY[i] += SYS.FRAME_TIME * accY[i];
				dX[i] += SYS.FRAME_TIME * spdX[i];
				dY[i] += SYS.FRAME_TIME * spdY[i];
				if (isOutBorder((int) dX[i], (int) dY[i], 40, 40)) {
					dX[i] = 9999;
					isVisible[i] = false;
					isHitable[i] = false;
					flag[i] = 0;
				}
				break;

			}

			//弾リクエスト
			if (flag[i] > 0){
				timerShoot[i] -= SYS.FRAME_TIME;
				if (timerShoot[i] < 0){
					timerShoot[i] = 0.1;
					danmaku.requestSniper(dX[i] + 24, dY[i] + 24, 1);
				}

			}


			for (int j = 0; j < 1000; j++) {

				if ((isHitable[i] && isHit(dX[i], dY[i], bullet.dX[j], bullet.dY[j], 24, 4)
						|| isHit(dX[i], dY[i], StgPlayer.dX + 24, StgPlayer.dY + 24, 24, StgPlayer.hitCir))) {
					if (isHit(dX[i], dY[i], StgPlayer.dX + 24, StgPlayer.dY + 24, 24, StgPlayer.hitCir)) {
						if (StgPlayer.timerFlash == 0) {
							StgPlayer.damage(50);
						}
					}
					bom.request(dX[i] + 24, dY[i] + 24, 0);
					if (itemFlag[i]) {
						item.request(dX[i], dY[i]);
					}

					dX[i] = 0;
					dY[i] = 9998;
					spdX[i] = 0;
					spdY[i] = 0;
					accX[i] = 0;
					accY[i] = 0;
					itemFlag[i] = false;
					isVisible[i] = false;
					isHitable[i] = false;
					if (flag[i] == 1) {
						flag[i] = 0;
					} else if (flag[i] == 2 || flag[i] == 3) {
						angle[i] = 0;
						flag[i] = -1;
					} else if (flag[i] == 4) {
						flag[i] = -2;
					}

					bullet.isVisible[j] = false;
					bullet.isHitable[j] = false;
					bullet.imageIndex[j] = 1;
					bullet.dX[j] = 5000;
					bullet.spdX[j] = 0;
					bullet.spdY[j] = 0;
					bullet.accX[j] = 0;
					bullet.accY[j] = 0;
					bullet.bulletType[j] = 0;
					bullet.flag[j] = 0;

				}

			}

		}

	}

	public boolean isHit(double x1, double y1, double x2, double y2, double rad1, double rad2) {

		double dblSaX = Math.pow(((x1 + rad1) - (x2 + rad2)), 2);
		double dblSaY = Math.pow(((y1 + rad1) - (y2 + rad2)), 2);
		return Math.sqrt(dblSaX + dblSaY) <= rad1 + rad2;

	}

//	public void drawImage(Graphics2D g, JFrame wind) {
//
//		for (int i = 0; i < UNIT_MAX; i++) {
//			if (isVisible[i]) {
//				this.drawImage(g, wind, image, widthBlock, heightBlock, imageIndex[i], opacity[i], dX[i], dY[i]);
//			}
//		}
//
//	}

	public void loadImage() {

		//image = loadImage(image, "Image/zako.png");
		komaImage = new KomaImage("Image/zako.png", 10, 10);

	}

	public boolean isOutBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 - xSize || x > SYS.WINDOW_SIZE_X || y < 0 - ySize || y > SYS.WINDOW_SIZE_Y);

	}

	public boolean isTouchBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 || x > SYS.WINDOW_SIZE_X - xSize || y < 0 || y > SYS.WINDOW_SIZE_Y - ySize);

	}

}
