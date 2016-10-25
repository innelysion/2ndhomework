package jp.tnw.a18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GameObject implements GameTools {

	// オブジェクトの数
	int MAX;
	// コマ画像の容器
	public KomaImage komaImage;
	// 配列画像の容器
	public BufferedImage arrayImage[];
	// コマ画像の切り替え用の変数
	int imageIndex[];
	// 可視度
	boolean isVisible[];
	float opacity[];
	// 座標
	double dX[];
	double dY[];

	GameObject() {

		this(1);

	}

	GameObject(int qty) {

		MAX = qty;
		imageIndex = new int[MAX];
		isVisible = new boolean[MAX];
		opacity = new float[MAX];
		dX = new double[MAX];
		dY = new double[MAX];

	}


	public void drawKomaImage(Graphics2D g, JFrame wind) {

		for (int i = 0; i < MAX; i++) {
			if (isVisible[i]) {
				this.drawImage(g, wind, komaImage, imageIndex[i], dX[i], dY[i], opacity[i]);
			}
		}

	}



}

class KomaImage implements GameTools {

	public BufferedImage file;
	public int widthBlock;
	public int heightBlock;

	KomaImage(String fileName, int wBlock, int hBlock) {
		file = loadImage(fileName);
		widthBlock = wBlock;
		heightBlock = hBlock;
	}

}




