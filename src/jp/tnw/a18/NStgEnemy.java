package jp.tnw.a18;

//◆敵のクラス◆//
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
	private double timerReq;
	private int counterReq;

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

		// 雑魚らの動くパターン
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
				enemy_ACTION_ZAKOB(i);
				break;
			}

			// HP = 0なら消滅
			if (hp[i] <= 0){
				VFX.request(dX[i] + 24, dY[i] + 24, 0);
				reset(i);
			}

			timerLife[i]++;
			timerAni[i]++;

		}

		timerReq++;

	}

	//リセット
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

	//AUTOリセット
	public void resetAuto(int index) {
		if (isOutBorder(this, index)) {
			reset(index);
		}
	}
	
	public void killAllEnemy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}
			hp[i] = 0;
		}
	}
	
	private int findVoidEnemy(){
		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}
			return i;
		}
		System.out.println("STGWarning: <ENEMY> out of limit");
		return MAX;
	}

	//TEST雑魚A
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
			hitCir[i] = 8;
			hitBoxW[i] = 48;
			hitBoxH[i] = 48;

			hp[i] = 3;
			type[i] = 1;
			flag[i] = 1;

			qtycount--;
			if (qtycount == 0) {
				break;
			}
		}
	}

	//TEST雑魚B
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
			hitCir[i] = 32;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

			hp[i] = 150;
			type[i] = 2;
			flag[i] = 1;

			qtycount--;
			if (qtycount == 0) {
				break;
			}
		}
	}

	//TEST雑魚Bの行動
	private void enemy_ACTION_ZAKOB(int index){

		if (timerAni[index] % 10 == 0) {
			imageIndex[index] = (imageIndex[index] > 49) ? 41 : imageIndex[index] + 1;
		}
		if (flag[index] == 1) {
			move(index);
			if (timerLife[index] > 50) {
				flag[index]++;
			}
		} else if (flag[index] == 2) {
			if (timerLife[index] > 80) {
				flag[index]++;
			}
		} else if (flag[index] == 3) {
			if (timerLife[index] > 300) {
				flag[index]++;
			}
		} else if (flag[index] == 4) {
			if (timerLife[index] > 700){
				spdY[index] = 150;
				flag[index]++;
			}
		}else if (flag[index] == 5){
			move(index);
			resetAuto(index);
		}

	}



}
