package jp.tnw.a18;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class Graphics {
	// // 画像の容器
	//BufferedImage image;
	//BufferedImage image[] = new BufferedImage[XX];
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
	Graphics(){
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
	
	public void loadImage(String filename, BufferedImage image[] , int fileqty) {

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
	
	
	public void loadImage(String filename, BufferedImage image){
		
		try {

			image = ImageIO.read(getClass().getResource(filename + ".png"));

		} catch (IOException e) {

			// ファイルエラーの場合
			e.printStackTrace();

		}
		
	}
	

	
}
