package jp.tnw.a18;

public class NStgEnemy extends NStgUnit {

	// 動くパタン処理用フラグ、タイプとアクション
	int flag[];
	int type[]; // 0=待機 1=雑魚A 2=
	int action[][];

	// ステータス
	int hp[];

	// 生成からのタイマー
	int timerLife[];

	// リクェスト用
	double timerReq;
	int counterReq;

	NStgEnemy() {

		super(200); // 敵数の上限
		komaImage = new KomaImage("Image/zako.png", 10, 10);
		flag = new int[MAX];
		type = new int[MAX];
		action = new int[MAX][50]; // アクションは50個まで
		hp = new int[MAX];
		timerLife = new int[MAX];

		for (int i = 0; i < MAX; i++) {

			flag[i] = 0;
			type[i] = 0;
			hp[i] = 1;
			timerLife[i] = 0;
			timerReq = 0;
			counterReq = 0;
			// アクション初期化 0 = 無効
			for (int j = 0; j < 50; j++) {
				action[i][j] = 0;
			}

		}

	}

	public void request(String enemyType) {

		switch (enemyType) {
		case "雑魚A":
			enemy_ZAKO_A();
			break;

		case "雑魚B":
			enemy_ZAKO_B();
			break;
		}

	}

	public void update() {

		if (timerReq % 15 == 0) {

			request("雑魚A");

		}

		if (timerReq % 1000 == 0) {

			request("雑魚B");

		}

		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}

			// 様々な敵のアクション
			switch (type[i]) {
			case 1:// 雑魚A
				if (timerAni[i] % 10 == 0) {
					imageIndex[i] = (imageIndex[i] > 69) ? 61 : imageIndex[i] + 1;
				}
				move(i);
				resetAuto(i);
				break;

			case 2:// 雑魚B
				if (timerAni[i] % 10 == 0) {
					imageIndex[i] = (imageIndex[i] > 49) ? 41 : imageIndex[i] + 1;
				}
				if (flag[i] == 1) {
					move(i);
					if (timerLife[i] > 50) {
						flag[i]++;
					}
				} else if (flag[i] == 2) {
					if (timerLife[i] > 80) {
						flag[i]++;
					}
				} else if (flag[i] == 3) {
					if (timerLife[i] > 300) {
						flag[i]++;
					}
				} else if (flag[i] == 4) {
					if (timerLife[i] > 700){
						spdY[i] = 150;
						flag[i]++;
					}
				}else if (flag[i] == 5){
					move(i);
					resetAuto(i);
				}
				break;
			}

			timerLife[i]++;
			timerAni[i]++;

		}

		timerReq++;

	}

	public void reset(int index) {

		super.reset(index);
		flag[index] = 0;
		type[index] = 0;
		hp[index] = 1;
		timerLife[index] = 0;
		for (int j = 0; j < 50; j++) {
			action[index][j] = 0;
		}

	}

	public void resetAuto(int index) {
		if (isOutBorder(this, index)) {
			reset(index);
		}
	}

	private void enemy_ZAKO_A() {

		int qtycount = 2; // 一回出す敵の数

		for (int i = 0; i < MAX; i++) {
			if (type[i] != 0 || flag[i] != 0) {
				continue;
			}
			// 主処理/////////////////////////
			if (qtycount == 2) {
				dX[i] = 0;
				dY[i] = 48;
				spdX[i] = 150;
				spdY[i] = 0;
			} else if (qtycount == 1) {
				dX[i] = SYS.WINDOW_SIZE_X - 48;
				dY[i] = 128;
				spdX[i] = -150;
				spdY[i] = 0;
			}
			// 主処理END///////////////////////
			isVisible[i] = true;
			imageIndex[i] = 61;

			isHitable[i] = true;
			hitCir[i] = 24;
			hitBoxW[i] = 48;
			hitBoxH[i] = 48;

			type[i] = 1;
			flag[i] = 1;

			qtycount--;
			if (qtycount == 0) {
				break;
			}
		}
	}

	private void enemy_ZAKO_B() {

		int qtycount = 3; // 一回出す敵の数

		for (int i = 0; i < MAX; i++) {
			if (type[i] != 0 || flag[i] != 0) {
				continue;
			}
			// 主処理/////////////////////////
			if (qtycount == 3) {
				dX[i] = 0 + 150;
				spdY[i] = 150;
			} else if (qtycount == 2) {
				dX[i] = SYS.WINDOW_SIZE_X / 2 - 48;
				spdY[i] = 100;
			} else if (qtycount == 1) {
				dX[i] = SYS.WINDOW_SIZE_X - 48 - 150;
				spdY[i] = 150;
			}
			dY[i] = -48;
			spdX[i] = 0;

			// 主処理END///////////////////////
			isVisible[i] = true;
			imageIndex[i] = 41;

			isHitable[i] = true;
			hitCir[i] = 16;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

			type[i] = 2;
			flag[i] = 1;

			qtycount--;
			if (qtycount == 0) {
				break;
			}
		}
	}

}
