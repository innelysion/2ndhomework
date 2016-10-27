package jp.tnw.a18;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//◆System Control◆//
public class SYS {

	static int WINDOW_SIZE_X, WINDOW_SIZE_Y;
	static double FRAME_TIME;
	static boolean MOUSE_CONTROLING;
	static boolean SCREEN_FREEZING;
	static boolean GAMEOVERING;

	// for mobile screen
	static double Xx, Yy;

	public static void setupPC(){
		FRAME_TIME = 0.017;
		MOUSE_CONTROLING = false;
		WINDOW_SIZE_X = 960;
		WINDOW_SIZE_Y = 540;
		GAMEOVERING = false;
	}

}

interface GameTools {

	//画像ファイルを読み込む
	public default BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	//コマ画像を描画
	public default void drawKoma(Graphics2D g, JFrame window, KomaImage image, int imageIndex, double dX, double dY,
			float opacity) {

		// 一コマの幅をゲット
		int blockW = image.file.getWidth() / image.widthBlock;
		// 一コマの高さをゲット
		int blockH = image.file.getHeight() / image.heightBlock;
		// 描画したいコマの左上端座標をゲット
		int indexX = (imageIndex % image.widthBlock == 0)// if
				? blockW * (image.widthBlock - 1)// do
				: blockW * ((imageIndex % image.widthBlock) - 1);// else do
		int indexY = (imageIndex % image.widthBlock == 0)// if
				? blockH * (imageIndex / image.widthBlock - 1)// do
				: blockH * (imageIndex / image.widthBlock);// else do
		// 不透明度
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));
		// 描画

		g.drawImage(image.file,
				// 画面に出したいところ
				(int) (dX), // 左上端X座標
				(int) (dY), // 左上端Y座標
				(int) (dX + blockW), // 右下端X座標
				(int) (dY + blockH), // 右下端Y座標
				// 画像のどこを使う
				(int) (indexX), // 左上端X座標
				(int) (indexY), // 左上端Y座標
				(int) (indexX + blockW), // 右下端X座標
				(int) (indexY + blockH), // 右下端Y座標
				window);
		// 不透明度リセット

		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));

	}

	//画面外判断
	public default boolean isOutBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 - xSize || x > SYS.WINDOW_SIZE_X || y < 0 - ySize || y > SYS.WINDOW_SIZE_Y);

	}

	//画面端との接触判断
	public default boolean isTouchBorder(int x, int y, int xSize, int ySize) {

		return (x < 0 || x > SYS.WINDOW_SIZE_X - xSize || y < 0 || y > SYS.WINDOW_SIZE_Y - ySize);

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