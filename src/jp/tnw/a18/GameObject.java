package jp.tnw.a18;

import java.awt.Graphics2D;

import javax.swing.JFrame;

//画面に表示が必要な全てのスーパークラス
public class GameObject implements GameTools {

	// オブジェクトの数
	int MAX;
	// コマ画像の容器
	public KomaImage komaImage;
	// コマ画像の切り替え用の変数
	int imageIndex[];
	// 可視度
	boolean isVisible[];
	float opacity[];
	// 座標
	double dX[];
	double dY[];

	// 初期値で初期化
	GameObject() {

		this(1);

	}

	// 初期化
	GameObject(int qty) {

		MAX = qty;
		imageIndex = new int[MAX];
		isVisible = new boolean[MAX];
		opacity = new float[MAX];
		dX = new double[MAX];
		dY = new double[MAX];

		for (int i = 0; i < MAX; i++) {
			imageIndex[i] = 1;
			isVisible[i] = true;
			opacity[i] = 1.0f;
			dX[i] = 0;
			dY[i] = 0;
		}

	}

	public void drawKoma(Graphics2D g, JFrame wind) {

		for (int i = 0; i < MAX; i++) {
			if (isVisible[i]) {
				drawKoma(g, wind, komaImage, imageIndex[i], dX[i], dY[i], opacity[i]);
			}
		}

	}

}
