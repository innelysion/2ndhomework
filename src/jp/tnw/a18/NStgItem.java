package jp.tnw.a18;

public class NStgItem extends NStgUnit {

	int type[];
	int flag[];
	double scrollSpd;

	NStgItem() {
		super(100);
		komaImage = new KomaImage("Image/Item.png", 4, 10);
		flag = new int[MAX];
		type = new int[MAX];
		for (int i = 0; i < MAX; i++) {
			flag[i] = 0;
			type[i] = 0;
		}
	}

	public void request(String itemType, NStgUnit fromUnit, int index, double offsetX, double offsetY) {

		int i = findIdleItem();
		dX[i] = fromUnit.dX[index] + offsetX;
		dY[i] = fromUnit.dY[index] + offsetY;
		spdY[i] = scrollSpd * 60;

		switch (itemType) {
		case "LIFE":
			type[i] = 1;
			break;
		case "BOMB":
			type[i] = 2;
			break;
		case "THEWORLD":
			type[i] = 3;
			break;
		case "OPTION":
			type[i] = 4;
			break;
		case "ROSE":
			type[i] = 5;
			break;
		case "MUSHROOM":
			type[i] = 6;
			break;
		}
		
		isVisible[i] = true;
		imageIndex[i] = type[i] * 4;

		isHitable[i] = true;
		hitCir[i] = 50;
		hitBoxW[i] = 30;
		hitBoxH[i] = 30;
		
		flag[i] = 1;
		
	}

	public void update() {

		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				continue;
			}
			if (timerAni[i] % 10 == 0) {
				imageIndex[i] = (imageIndex[i] > type[i] * 4 - 1) ? type[i] * 4 - 3 : imageIndex[i] + 1;
			}
			move(i);
			resetAuto(i);
			timerAni[i]++;
		}
		
	}
	
	// リセット
	public void reset(int index) {
		super.reset(index);
		flag[index] = 0;
		type[index] = 0;
	}

	// 画面外に行くと自動リセット
	public void resetAuto(int index) {
		if (isOutBorder(this, index)) {
			reset(index);
		}
	}

	// アイテム配列の中に待機しているものを探す
	private int findIdleItem() {
		for (int i = 0; i < MAX; i++) {
			if (type[i] == 0 || flag[i] == 0) {
				return i;
			}
		}
		System.out.println("STGWarning: <ITEM> out of limit");
		return MAX;
	}

}
