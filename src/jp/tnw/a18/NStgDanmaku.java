package jp.tnw.a18;

//◆弾幕のクラス◆//
public class NStgDanmaku extends NStgUnit {

	// 誰から撃つ◆true:自機◆false:敵
	boolean belongPlayer[];

	// 動くパタン処理用フラグ、タイプとアクション
	int flag[]; // 1000からはエフェクト
	int type[]; // 1000からはエフェクト
	int action[][];
	double other1 = 90;
	boolean other1flag = false;
	boolean other1direction = false;

	// 弾幕のステータス
	int damage[];

	// 生成からのタイマー
	int timerLife[];

	// リクェスト用
	double timerReq;
	int counterReq;

	// THEWORLD
	boolean freezing;

	// 初期化
	NStgDanmaku() {

		super(SYS.DANMAKU_LIMIT); // 弾数の上限
		komaImage = new KomaImage("Image/tama.png", 20, 10);
		belongPlayer = new boolean[MAX];
		flag = new int[MAX];
		type = new int[MAX];
		action = new int[MAX][10]; // アクションは10個まで
		damage = new int[MAX];
		timerLife = new int[MAX];

		for (int i = 0; i < MAX; i++) {

			belongPlayer[i] = false;
			flag[i] = 0;
			type[i] = 0;
			damage[i] = 1;
			timerReq = 0;
			counterReq = 0;
			timerLife[i] = 0;
			// アクション初期化 0 = 無効
			for (int j = 0; j < 10; j++) {
				action[i][j] = 0;
			}

		}

		freezing = false;

	}

	public void request(String danmakuType, int danmakuPattern, NStgUnit fromUnit, int index, double offsetX,
			double offsetY) {

		switch (danmakuType) {
		case "自機狙い弾いA":
			reqJKN01(fromUnit.dX[index] + offsetX, fromUnit.dY[index] + offsetY, 1, 15, 100);
			break;
		case "自機狙いバリア弾":

			reqJKN01(fromUnit.dX[index] + offsetX, fromUnit.dY[index] + offsetY, 2, other1, 200);
			break;
		case "花火A":
			reqNor01(danmakuPattern, fromUnit.dX[index] + offsetX, fromUnit.dY[index] + offsetY);
			break;
		case "ビームA":
			// danmaku_BEAM_A(danmakuPattern, fromUnit, index, offsetX,
			// offsetY);
			break;

		// ここから演出用弾幕
		case "花火しっぽ":
			reqEffect01(fromUnit.dX[index], fromUnit.dY[index]);
			break;
		case "オプションしっぽ":
			reqEffect02(fromUnit.dX[index], fromUnit.dY[index]);
			break;
		}

	}

	public void update() {

		// ここから主処理

		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}

			// 様々な弾幕のアクション
			switch (type[i]) {
			case 1:// 花火A
				actNor01(i);
				break;
			case 1000:// 花火しっぽ
				actEffect01(i);
				break;
			case 1001:
				actEffect02(i);
				break;
			case 10:// JKN01
				if (SYS.TIMERSTAGE % 3 == 0 && imageIndex[i] < 187) {
					imageIndex[i] = (imageIndex[i] > 185) ? 181 : imageIndex[i] + 1;
				} else if (SYS.TIMERSTAGE % 3 == 0 && imageIndex[i] >= 187) {
					imageIndex[i] = (imageIndex[i] > 191) ? 187 : imageIndex[i] + 1;
				}
				//moveCir(i, SYS.TIMERSTAGE > 150 && SYS.TIMERSTAGE < 1650 ? (other1direction ? 0.85 : -0.85) : 0);
				moveCir(i, 0);
				resetAuto(i);
				break;
			}
			if (!freezing) {
				timerLife[i]++;
			}
		}

		// 各種タイム依存変数
		timerReq++;
		counterReq++;
		if (!other1flag) {
			other1 += 2;
			if (other1 >= 150) {
				other1flag = true;
			}
		} else {
			other1 -= 2;
			if (other1 <= 30) {
				other1flag = false;
			}
		}

		countActive();

	}

	/////////////////////////////////////////////////////////////////////////
	// ◆ここから弾幕の初期設定◆ ///////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	private void reqJKN01(double gX, double gY, int qty, double angleBetween, double spd) {

		int i;
		// 自機狙い角度
		double jikinerai = //
				Math.atan2((NStgPlayer.dY + 24 - (gY - 16)), (NStgPlayer.dX + 24 - (gX - 16))) / Math.PI * 180;

		for (int j = 0; j < qty; j++) {

			i = findIdleDanmaku();
			imageIndex[i] = 235;
			dX[i] = gX;
			dY[i] = gY;
			spdX[i] = spd;
			spdY[i] = spd;

			isVisible[i] = true;
			imageIndex[i] = other1direction ? 181 : 187;

			isHitable[i] = true;
			hitCir[i] = 8;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

			type[i] = 10;
			flag[i] = 1;

			//angle[i] = -((angleBetween * (qty - 1)) / 2) + j * angleBetween + jikinerai;
			angle[i] = -((90 * (qty - 1)) / 2) + j * 90 + jikinerai;

		}

	}

	private void reqBeamLazer01(double gX, double gY) {

	}

	private void reqNor01(int dmPattern, double gX, double gY) {
		int[][] motionAngle = { //
				//
				{ 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360, 999 }, //
				{ 0, 90, 180, 270, 999 }, //
				{ 90, 90 + 30, 90 - 30, 999 }, //
				{ 0, 180, 999 } //
		};//
		int dmIndex = 0;

		for (int i = 0; i < MAX; i++) {
			if (type[i] != 0 || flag[i] != 0) {
				continue;
			}

			dX[i] = gX;
			dY[i] = gY;
			spdX[i] = -80;
			spdY[i] = -80;
			accX[i] = 50;
			accY[i] = 50;
			angle[i] = motionAngle[dmPattern][dmIndex] + timerReq * 2;

			isVisible[i] = true;
			imageIndex[i] = 37;

			isHitable[i] = true;
			hitCir[i] = 8;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

			type[i] = 1;
			flag[i] = 1;

			dmIndex++;
			if (motionAngle[dmPattern][dmIndex] == 999) {
				break;
			}
		} // for(i) end

		VFX.request(gX + 8, gY + 8, 4);
	}// funtion end

	/////////////////////////////////////////////////////////////////////////
	// ◆ここから演出用弾幕の初期設定（particleのようなもの）◆ /////////////
	/////////////////////////////////////////////////////////////////////////

	private void reqEffect01(double gX, double gY) {

		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 && flag[i] == 0) {
				dX[i] = gX;
				dY[i] = gY;
				spdX[i] = 0;
				spdY[i] = 0;
				accX[i] = 0;
				accY[i] = 0;

				isVisible[i] = true;
				opacity[i] = 0.5f;
				imageIndex[i] = 39;

				type[i] = 1000;
				flag[i] = 1000;
				break;
			}
		}
	}

	private void reqEffect02(double gX, double gY) {

		int i = findIdleDanmaku();
		dX[i] = gX;
		dY[i] = gY;
		spdX[i] = 0;
		spdY[i] = 0;
		accX[i] = 0;
		accY[i] = 0;

		belongPlayer[i] = true;
		isVisible[i] = true;
		imageIndex[i] = 141;

		type[i] = 1001;
		flag[i] = 1000;

	}

	/////////////////////////////////////////////////////////////////////////
	// ◆ここから動作パターン◆ /////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	private void actNor01(int index) {
		if (timerReq % 6 == 0) {
			imageIndex[index] = imageIndex[index] < 37 || imageIndex[index] >= 40 ? 37 : imageIndex[index] + 1;
		}
		moveCir(index, (Math.random() - 0.5) * 15);
		resetAuto(index);
	}

	/////////////////////////////////////////////////////////////////////////
	// ◆ここから演出用弾幕動作パターン◆ ///////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	private void actEffect01(int index) {
		opacity[index] -= 0.1f;
		if (opacity[index] <= 0.1f) {
			reset(index);
		}
	}

	private void actEffect02(int index) {

			imageIndex[index]++;
			if(imageIndex[index] > 146){
				reset(index);
			}

	}

	/////////////////////////////////////////////////////////////////////////
	// ◆ここから機能的関数◆ ///////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	// THEWORLD
	public void move(int i) {
		if (!freezing) {
			super.move(i);
		}
	}

	public void moveCir(int i, double rotateSpd) {
		if (!freezing) {
			super.moveCir(i, rotateSpd);
		}
	}

	public void countActive() {
		activing = 0;
		for (int i = 0; i < MAX; i++) {
			if (flag[i] != 0 || type[i] != 0) {
				activing++;
			}
		}
	}

	// リセット
	public void reset(int index) {

		super.reset(index);
		belongPlayer[index] = false;
		flag[index] = 0;
		type[index] = 0;
		damage[index] = 1;
		timerLife[index] = 0;
		for (int j = 0; j < 10; j++) {
			action[index][j] = 0;
		}
	}

	// 画面外に行くと自動リセット
	public void resetAuto(int index) {
		if (isOutBorder(this, index)) {
			reset(index);
		}
	}

	// すべての敵弾を強制リセット
	public void resetAllDanmaku() {
		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}
			VFX.request(dX[i], dY[i], 5);
			reset(i);
		}
	}

	// 弾幕配列の中に待機しているものを探す
	private int findIdleDanmaku() {
		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				return i;
			}
		}
		System.out.println("STGWarning: <DANMAKU> out of limit");
		return MAX;
	}

}
