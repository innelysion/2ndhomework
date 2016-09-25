package jp.tnw.a18;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class StgObject {

	// 画像の容器
	BufferedImage image[] = new BufferedImage[99];
	// // 表示するユニットの最大の数
	// int UNIT_MAX = 1000;
	// // 画像の切り替え用の変数
	// int imageIndex[] = new int[UNIT_MAX];
	// // 可視スイッチ
	// boolean isVisible[] = new boolean[UNIT_MAX];
	// float opacity[] = new float[UNIT_MAX];
	// // 座標
	// double dX[] = new double[UNIT_MAX];
	// double dY[] = new double[UNIT_MAX];
	// // アニメとリクエストのカウンター
	// int timerAni[] = new int[UNIT_MAX];
	// double timerReq[] = new double[UNIT_MAX];

	// // 速度と加速度
	// double spdX[] = new double[UNIT_MAX];
	// double spdY[] = new double[UNIT_MAX];
	// double spdX[] = new double[UNIT_MAX];
	// double spdY[] = new double[UNIT_MAX];
	// // 角度
	// double angle[] = new double[UNIT_MAX];
	// // 円心と軸
	// double centerX[] = new double[UNIT_MAX];
	// double centerY[] = new double[UNIT_MAX];
	// double axisX[] = new double[UNIT_MAX];
	// double axisY[] = new double[UNIT_MAX];
	// //当たり判定
	// boolean isHitable[] = new boolean[UNIT_MAX];
	// double hitCir[] = new double[UNIT_MAX];
	// double hitBoxW[] = new double[UNIT_MAX];
	// double hitBoxH[] = new double[UNIT_MAX];

	// ◆コンストラクター Constructor◆
	StgObject() {

		// for (int i = 0; i < UNIT_MAX; i++) {
		//
		// isVisible[i] = false;
		// opacity[i] = 1.0f;
		//
		// dX[i] = 0;
		// dY[i] = 0;
		// timerAni[i] = 0;
		// timerReq[i] = 0;
		//
		// spdX[i] = 0;
		// spdY[i] = 0;
		// accX[i] = 0;
		// accY[i] = 0;
		//
		// angle[i] = 0;
		//
		// centerX[i] = 0;
		// centerY[i] = 0;
		// axisX[i] = 0;
		// axisY[i] = 0;
		//
		// isHitable[i] = false;
		// hitCir[i] = 0;
		// hitBoxW[i] = 0;
		// hitBoxH[i] = 0;
		//
		// }

	}

	// ◆画像の読み込む処理◆
	public void loadImage(String filename, int fileqty) {

		try {
			if (fileqty > 0 || fileqty <= 10000) {

				// ファイルが一つしかいない場合
				if (fileqty == 1) {

					image[0] = ImageIO.read(getClass().getResource(filename + ".png"));

				} else {
					String num = "";
					for (int i = 0; i < fileqty; i++) {

						// ファイル名のゼロの処理
						// ファイル名例：gazou_0000.png gazou_0032.png gazou_9999.png
						if (i < 10) {
							num = "000" + i;
						} else if (i < 100) {
							num = "00" + i;
						} else if (i < 1000) {
							num = "0" + i;
						}

						image[i] = ImageIO.read(getClass().getResource(filename + "_" + num + ".png"));

					}

				}

			}

		} catch (IOException e) {

			// ファイルエラーの場合
			e.printStackTrace();

		}
	}

	// ◆画像の描画処理◆
	// public void drawImage(Graphics2D g, JFrame wind) {
	//
	// // 配列画像を描画
	// for (int i = 0; i < UNIT_MAX; i++) {
	// if (isVisible[i]) {
	//
	// g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	// opacity[i])));
	// g.drawImage(image[imageIndex[i]], (int) dX[i], (int) dY[i], wind);
	// g.setComposite(AlphaComposite.Clear);
	// }
	// }
	//
	// }

	// public void drawImage(Graphics2D g, JFrame wind, int widthBlock, int
	// heightBlock) {
	//
	// // 画像が一つしかいない場合の略
	// this.drawImage(g, wind, widthBlock, heightBlock, 0);
	//
	// }

	public void drawImage(Graphics2D g, JFrame wind, int widthBlock, int heightBlock, int UNIT_MAX, int imageIndex[],
			boolean isVisible[], float opacity[], double dX[], double dY[], int arrImage) {

		// まとめ画像を描画

		for (int i = 0; i < UNIT_MAX; i++) {
			// 一コマの幅をゲット
			int blockw = image[arrImage].getWidth() / widthBlock;
			// 一コマの高さをゲット
			int blockh = image[arrImage].getHeight() / heightBlock;
			// 描画したいコマの左上端座標をゲット
			int indexw = (imageIndex[i] % widthBlock == 0) ? blockw * (widthBlock - 1)
					: blockw * ((imageIndex[i] % widthBlock) - 1);
			int indexh = (imageIndex[i] % widthBlock == 0) ? blockh * (imageIndex[i] / widthBlock - 1)
					: blockh * (imageIndex[i] / widthBlock);

			// 描画
			if (isVisible[i]) {
				g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[i])));
				g.drawImage(image[arrImage],

						// 画面に出したいところ
						(int) dX[i], // 左上端X座標
						(int) dY[i], // 左上端Y座標
						(int) dX[i] + blockw, // 右下端X座標
						(int) dY[i] + blockh, // 右下端Y座標
						// 画像のどこを使う
						indexw, // 左上端X座標
						indexh, // 左上端Y座標
						indexw + blockw, // 右下端X座標
						indexh + blockh, // 右下端Y座標

						wind);
				g.setComposite(AlphaComposite.Clear);
			}
		}
	}

	abstract void drawImage(Graphics2D g, JFrame wind);

	public boolean isOutBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 - xSize || x > Sys.windowSizeX || y < 0 - ySize || y > Sys.windowSizeY);

	}

	public boolean isTouchBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 || x > Sys.windowSizeX - xSize || y < 0 || y > Sys.windowSizeY - ySize);

	}

}
