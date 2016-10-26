package jp.tnw.a18;

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

	public void request(String danmakuType,int danmakuPattern, NStgUnit fromUnit, int i, double offsetX, double offsetY) {

		switch (danmakuType) {
		case "自機狙い弾いA":
			danmaku_JKN_A(fromUnit, i, offsetX, offsetY);
			break;
		case "花火A":
			danmaku_NOR_A(danmakuPattern, fromUnit, i, offsetX, offsetY);
			break;
		}

	}

	public void update() {

	}

	public void reset(int i) {

		super.reset(i);
		belongPlayer[i] = false;
		flag[i] = 0;
		type[i] = 0;
		damage[i] = 1;
		timerLife[i] = 0;
		for (int j = 0; j < 10; j++) {
			action[i][j] = 0;
		}

	}

	private void danmaku_JKN_A(NStgUnit fromUnit, int index, double offsetX, double offsetY) {

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
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}

			dX[i] = fromUnit.dX[i] + offsetX;
			dY[i] = fromUnit.dY[i] + offsetY;
			spdX[i] = 250;
			spdY[i] = 250;
			angle[i] = motionAngle[dmPattern][dmIndex];

			isVisible[i] = true;
			imageIndex[i] = 37;

			isHitable[i] = true;
			hitCir[i] = 16;
			hitBoxW[i] = 16;
			hitBoxH[i] = 16;

			type[i] = 1;
			flag[i] = 1;

			dmIndex++;
			if (motionAngle[dmPattern][dmIndex] == 999) {
				break;
			}
		} // for(i) end
	}// funtion end

}
