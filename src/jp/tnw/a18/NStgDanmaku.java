package jp.tnw.a18;

//◆弾幕のクラス◆//
public class NStgDanmaku extends NStgUnit {

	// 誰から撃つ◆true:自機◆false:敵
	boolean belongPlayer[];

	// 動くパタン処理用フラグ、タイプとアクション
	int flag[];
	int type[];
	int action[][];

	// 弾幕のステータス
	int damage[];

	// 生成からのタイマー
	int timerLife[];

	// リクェスト用
	double timerReq;
	int counterReq;

	NStgEnemy enemy;

	// 初期化
	NStgDanmaku() {

		super(5000); // 弾数の上限
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

	}

	public void request(String danmakuType, int danmakuPattern, NStgUnit fromUnit, int i, double offsetX,
			double offsetY) {

		switch (danmakuType) {
		case "自機狙い弾いA":
			danmaku_JKN_A(fromUnit, i, offsetX, offsetY);
			break;
		case "花火A":
			danmaku_NOR_A(danmakuPattern, fromUnit, i, offsetX, offsetY);
			break;
		case "ビームA":
			danmaku_BEAM_A(danmakuPattern, fromUnit, i, offsetX, offsetY);

		}

	}

	public void update() {



		for (int i = 0; i < enemy.MAX; i++) {

			if (enemy.flag[i] == 3 && enemy.type[i] == 2 && timerReq % 5 == 0){
				request("花火A", counterReq % 4, enemy, i, 18, 24);
			}

		}

		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}

			// 様々な弾幕のアクション
			switch (type[i]) {
			case 1://花火A
				danmaku_ACTION_A(i);
				break;
			}

		}

		timerReq++;
		counterReq++;

	}

	//リセット
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

	//AUTOリセット
	public void resetAuto(int index) {
		if (isOutBorder(this, index)) {
			reset(index);
		}
	}

	private void danmaku_JKN_A(NStgUnit fromUnit, int index, double offsetX, double offsetY) {

	}

	private void danmaku_BEAM_A(int danmakuPattern, NStgUnit fromUnit, int i, double offsetX, double offsetY) {

	}

	private void danmaku_NOR_A(int dmPattern, NStgUnit fromUnit, int index, double offsetX, double offsetY) {
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

			dX[i] = fromUnit.dX[index] + offsetX;
			dY[i] = fromUnit.dY[index] + offsetY;
			spdX[i] = -80;
			spdY[i] = -80;
			accX[i] = 50;
			accY[i] = 50;
			angle[i] = motionAngle[dmPattern][dmIndex] + timerReq;

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
		
		VFX.request(fromUnit.dX[index] + offsetX + 8, fromUnit.dY[index] + offsetY + 8, 4);
	}// funtion end

	private void danmaku_ACTION_A(int index){
		if (timerReq % 4 == 0) {
			imageIndex[index] = imageIndex[index] < 37 || imageIndex[index] >= 40 ? 37 : imageIndex[index] + 1;
		}
		moveCir(index, -0.5);
		resetAuto(index);
	}

}
