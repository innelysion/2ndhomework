package jp.tnw.a18;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class StgImage {
	// // 画像の容器
	// BufferedImage image;
	// BufferedImage image[] = new BufferedImage[XX];
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
	StgImage() {
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

	public BufferedImage loadImage(BufferedImage image, String filename) {

		try {
			image = ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			// ファイルエラーの場合
			e.printStackTrace();
		}
		return image;
	}

	public void drawImage(Graphics2D g, JFrame window, BufferedImage image, int widthBlock, int heightBlock,
			int imageIndex, float opacity, double dX, double dY) {

		// 一コマの幅をゲット
		int blockW = image.getWidth() / widthBlock;
		// 一コマの高さをゲット
		int blockH = image.getHeight() / heightBlock;
		// 描画したいコマの左上端座標をゲット
		int indexX = (imageIndex % widthBlock == 0) ? blockW * (widthBlock - 1)
				: blockW * ((imageIndex % widthBlock) - 1);
		int indexY = (imageIndex % widthBlock == 0) ? blockH * (imageIndex / widthBlock - 1)
				: blockH * (imageIndex / widthBlock);
		// 不透明度
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));
		// 描画
		g.drawImage(image,
				// 画面に出したいところ
				(int) dX, // 左上端X座標
				(int) dY, // 左上端Y座標
				(int) dX + blockW, // 右下端X座標
				(int) dY + blockH, // 右下端Y座標
				// 画像のどこを使う
				indexX, // 左上端X座標
				indexY, // 左上端Y座標
				indexX + blockW, // 右下端X座標
				indexY + blockH, // 右下端Y座標
				window);
		// 不透明度リセット
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));

	}

}



