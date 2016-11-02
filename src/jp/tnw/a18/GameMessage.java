package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GameMessage {

	Font f = new Font("Meiryo", Font.BOLD, 20);

	boolean pause;
	boolean fastPlay;
	boolean pauseWhenFastplay;

	int M_Max = 40;
	int dataCharCnt = 0;
	int dataLineCnt = 0;
	int msgboxCharCnt = 0;
	int msgboxLineCnt = 0; // 0-3
	
	int textTimer = 0;

	char mj[][] = new char[4][40];
	char msgboxTop[][] = new char[2][40];
	char msgboxBot[][] = new char[2][40];

	GameMessage() {

		pause = false;
		clear();
		

	}

	// 表示エリア初期化
	public void clear() {
		for (int i = 0; i < M_Max; i++) {
			mj[0][i] = ' ';
			mj[1][i] = ' ';
			mj[2][i] = ' ';
			mj[3][i] = ' ';
		}
		clearTop();
		clearBot();
	}
	
	public void clearTop(){
		for (int i = 0; i < M_Max; i++) {
			msgboxTop[0][i] = ' ';
			msgboxTop[1][i] = ' ';
		}
	}
	
	public void clearBot(){
		for (int i = 0; i < M_Max; i++) {
			msgboxBot[0][i] = ' ';
			msgboxBot[1][i] = ' ';
		}
	}

	// public void loadImage(Resources res) {
	//
	// //ﾌﾗｸﾞを新規で作成
	// BitmapFactory.Options opt = new BitmapFactory.Options();
	// //ﾉｰﾏﾙで読み込むために
	// opt.inScaled = false;
	// //実際のファイル読み込み
	// frameBG = BitmapFactory.decodeResource(res, R.drawable.waku, opt);
	// frameDark = BitmapFactory.decodeResource(res, R.drawable.waku2, opt);
	// pauseArrow = BitmapFactory.decodeResource(res, R.drawable.enter, opt);
	// bgPNG[0] = BitmapFactory.decodeResource(res, R.drawable.bg01, opt);
	// bgPNG[1] = BitmapFactory.decodeResource(res, R.drawable.bg02, opt);
	// bgPNG[2] = BitmapFactory.decodeResource(res, R.drawable.bg03, opt);
	// bgPNG[3] = BitmapFactory.decodeResource(res, R.drawable.bg04, opt);
	// bgPNG[4] = BitmapFactory.decodeResource(res, R.drawable.bg05, opt);
	// bgPNG[5] = BitmapFactory.decodeResource(res, R.drawable.bg06, opt);
	// bgPNG[6] = BitmapFactory.decodeResource(res, R.drawable.bg07, opt);
	// bgPNG[7] = BitmapFactory.decodeResource(res, R.drawable.bg08, opt);
	// bgPNG[8] = BitmapFactory.decodeResource(res, R.drawable.bg09, opt);
	// bgPNG[9] = BitmapFactory.decodeResource(res, R.drawable.bg10, opt);
	// bgPNG[10] = BitmapFactory.decodeResource(res, R.drawable.bg11, opt);
	// bgPNG[11] = BitmapFactory.decodeResource(res, R.drawable.bg12, opt);
	//
	// bgChara[1] = BitmapFactory.decodeResource(res, R.drawable.ch01, opt);
	// bgChara[2] = BitmapFactory.decodeResource(res, R.drawable.ch02, opt);
	// bgChara[3] = BitmapFactory.decodeResource(res, R.drawable.ch03, opt);
	// bgChara[4] = BitmapFactory.decodeResource(res, R.drawable.ch04, opt);
	// bgChara[5] = BitmapFactory.decodeResource(res, R.drawable.ch05, opt);
	// bgChara[6] = BitmapFactory.decodeResource(res, R.drawable.ch06, opt);
	// bgChara[7] = BitmapFactory.decodeResource(res, R.drawable.ch07, opt);
	// bgChara[8] = BitmapFactory.decodeResource(res, R.drawable.ch08, opt);
	// bgChara[9] = BitmapFactory.decodeResource(res, R.drawable.ch09, opt);
	// bgChara[10] = BitmapFactory.decodeResource(res, R.drawable.ch10, opt);
	//
	// }

	public void update() {

		textTimer++;

		if (Input.M_LCR == true) {
			if (pause) {
				clear();
				pause = false;
			}
		}
		
		Input.M_LCR = false;

		if (pause) {
			return;
		}

		if (textTimer % (fastPlay ? 1 : 3) == 0) {

			char spCode = text01[dataLineCnt].charAt(dataCharCnt);
			char spCode2 = text01[dataLineCnt].charAt(dataCharCnt + 1);

			if (spCode == '@') {

				specialCode(spCode2);

			} else {

				mj[msgboxLineCnt][msgboxCharCnt] = text01[dataLineCnt].charAt(dataCharCnt); // 文字列の何文字目をGET;
				dataCharCnt++;
				msgboxCharCnt++;

			}
		}

	}

	public void showBG() {

	}

	public void specialCode(char chr) {

		switch (chr) {
		case 'E':
			dataLineCnt++;
			dataCharCnt = 0;
			msgboxCharCnt = 0;
			msgboxLineCnt++;
			if (msgboxLineCnt == 4) {
				msgboxLineCnt = 0;
				if (fastPlay) {
					pauseWhenFastplay = true;
				}
				pause = true;
				// clear();
			}
			break;

		case 'N':
			dataCharCnt += 2;
			msgboxCharCnt = 0;
			msgboxLineCnt++;
			if (msgboxLineCnt == 4) {
				msgboxLineCnt = 0;
				if (fastPlay) {
					pauseWhenFastplay = true;
				}
				pause = true;
				// clear();
			}
			break;

		}

	}

	public void draw(Graphics2D g) {
		g.setFont(f);
		g.setColor(new Color(255, 255, 255));
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawString(String.valueOf(mj[0]), (int) (30), (int) (420));
		g.drawString(String.valueOf(mj[1]), (int) (30), (int) (450));
		g.drawString(String.valueOf(mj[2]), (int) (30), (int) (480));
		g.drawString(String.valueOf(mj[3]), (int) (30), (int) (510));
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	// public void drawImagePC(Canvas c, double Xx, double Yy,
	// int widthBlock, int heightBlock, int UNIT_MAX, int imageIndex,
	// float opacity, double dX, double dY) {
	//
	// Paint p = new Paint();
	//
	//
	// if (Xx != 0 && Yy != 0 && OK_flag == 0) {//拡大縮小は1回だけ
	//
	// OK_flag = 1;
	// for (int i = 0; i < 12; i++){
	// bgPNG[i] = Bitmap.createScaledBitmap(bgPNG[i], (int) (Xx * 960), (int)
	// (Yy * 540), true);
	// }
	// for (int i = 1; i < 6; i++){
	// bgChara[i] = Bitmap.createScaledBitmap(bgChara[i], (int) (Xx * 500),
	// (int) (Yy * 450), true);
	// }
	// bgChara[6] = Bitmap.createScaledBitmap(bgChara[6], (int) (Xx * 750),
	// (int) (Yy * 450), true);
	// bgChara[7] = Bitmap.createScaledBitmap(bgChara[7], (int) (Xx * 500),
	// (int) (Yy * 450), true);
	// bgChara[8] = Bitmap.createScaledBitmap(bgChara[8], (int) (Xx * 500),
	// (int) (Yy * 450), true);
	// bgChara[9] = Bitmap.createScaledBitmap(bgChara[9], (int) (Xx * 500),
	// (int) (Yy * 450), true);
	// bgChara[10] = Bitmap.createScaledBitmap(bgChara[10], (int) (Xx * 750),
	// (int) (Yy * 450), true);
	//
	// pauseArrow = Bitmap.createScaledBitmap(pauseArrow, (int) (Xx * 128),
	// (int) (Yy * 32), true);
	// frameBG = Bitmap.createScaledBitmap(frameBG, (int) (Xx * 960), (int) (Yy
	// * 175), true);//BG
	// frameDark = Bitmap.createScaledBitmap(frameDark, (int) (Xx * 960), (int)
	// (Yy * 175), true);//BG
	//
	// }
	// // まとめ画像を描画
	// if (OK_flag == 1) {
	//
	// c.drawBitmap(bgPNG[bgIndex], 0, 0, p);
	//
	// drawLeftChara(c, p, Xx, Yy);
	// drawMidChara(c, p, Xx, Yy);
	// drawRightChara(c, p, Xx, Yy);
	//
	// p.setAlpha(100);
	// c.drawBitmap(frameDark, 0, (int) ((540 - 175) * Yy), p);
	// p.setAlpha(255);
	// c.drawBitmap(frameBG, 0, (int) ((540 - 175) * Yy), p);
	// if (pause) {
	// // 一コマの幅をゲット
	// int blockw = 128 / widthBlock;
	// // 一コマの高さをゲット
	// int blockh = 32 / heightBlock;
	// // 描画したいコマの左上端座標をゲット
	// int indexw = (imageIndex % widthBlock == 0) ? blockw * (widthBlock - 1)
	// : blockw * ((imageIndex % widthBlock) - 1);
	// int indexh = (imageIndex % widthBlock == 0) ? blockh * (imageIndex /
	// widthBlock - 1)
	// : blockh * (imageIndex / widthBlock);
	//
	// // 描画
	//
	// c.drawBitmap(pauseArrow,
	// // 画像のどこを使う
	// new Rect( //
	// (int)(indexw * Xx), //
	// (int)(indexh * Yy),//
	// (int)((indexw + blockw) * Xx),//
	// (int)((indexh + blockh) * Yy)//
	// ),//
	// // 画面に出したいところ
	// new Rect(//
	// (int) (dX * Xx),//
	// (int) (dY * Yy),//
	// (int)(((int) dX + blockw) * Xx),//
	// (int)(((int) dY + blockh) * Yy) //
	// ), p);//
	// }
	// }
	//
	// p.setTextSize(26 * (float) Xx);
	//
	//
	//
	// p.setColor(Color.WHITE);
	// p.setAntiAlias(true);
	// c.drawText(String.valueOf(mj[0]), (int) (30 * Xx), (int) (420 * Yy), p);
	// c.drawText(String.valueOf(mj[1]), (int) (30 * Xx), (int) (450 * Yy), p);
	// c.drawText(String.valueOf(mj[2]), (int) (30 * Xx), (int) (480 * Yy), p);
	// c.drawText(String.valueOf(mj[3]), (int) (30 * Xx), (int) (510 * Yy), p);
	// p.setAntiAlias(false);
	// }
	//
	// private void drawRightChara(Canvas c, Paint p, double Xx, double Yy) {
	// if (charaFaceIndexR == 0 || charaIndexR == 0){
	// charaFaceIndexR = 0;
	// charaIndexR = 0;
	// return;
	// }
	//
	// // 一コマの幅をゲット
	// int widthBlock = bgChara[charaIndexR].getWidth() > (600 * Xx) ? 3 : 2;
	// int blockw = bgChara[charaIndexR].getWidth() > (600 * Xx) ? 750 : 500 /
	// widthBlock;
	// // 一コマの高さをゲット
	// int blockh = 450;
	// // 描画したいコマの左上端座標をゲット
	// int indexw = (charaFaceIndexR % widthBlock == 0) ? blockw * (widthBlock -
	// 1)
	// : blockw * ((charaFaceIndexR % widthBlock) - 1);
	// int indexh = (charaFaceIndexR % widthBlock == 0) ? blockh *
	// (charaFaceIndexR / widthBlock - 1)
	// : blockh * (charaFaceIndexR / widthBlock);
	// c.drawBitmap(bgChara[charaIndexR],
	// // 画像のどこを使う
	// new Rect( //
	// (int)(indexw * Xx), //
	// (int)(indexh * Yy),//
	// (int)((indexw + blockw) * Xx),//
	// (int)((indexh + blockh) * Yy)//
	// ),//
	// // 画面に出したいところ
	// new Rect(//
	// (int) (680 * Xx),//
	// (int) (170 * Yy),//
	// (int)(((int) 680 + blockw) * Xx),//
	// (int)(((int) 170 + blockh) * Yy) //
	// ), p);//
	//
	// }
	//
	// private void drawMidChara(Canvas c, Paint p, double Xx, double Yy) {
	// if (charaFaceIndexM == 0 || charaIndexM == 0){
	// charaFaceIndexM = 0;
	// charaIndexM = 0;
	// return;
	// }
	//
	// // 一コマの幅をゲット
	// int widthBlock = bgChara[charaIndexM].getWidth() > (600 * Xx) ? 3 : 2;
	// int blockw = bgChara[charaIndexR].getWidth() > (600 * Xx) ? 750 : 500 /
	// widthBlock;
	// // 一コマの高さをゲット
	// int blockh = 450;
	// // 描画したいコマの左上端座標をゲット
	// int indexw = (charaFaceIndexM % widthBlock == 0) ? blockw * (widthBlock -
	// 1)
	// : blockw * ((charaFaceIndexM % widthBlock) - 1);
	// int indexh = (charaFaceIndexM % widthBlock == 0) ? blockh *
	// (charaFaceIndexM / widthBlock - 1)
	// : blockh * (charaFaceIndexM / widthBlock);
	// c.drawBitmap(bgChara[charaIndexM],
	// // 画像のどこを使う
	// new Rect( //
	// (int)(indexw * Xx), //
	// (int)(indexh * Yy),//
	// (int)((indexw + blockw) * Xx),//
	// (int)((indexh + blockh) * Yy)//
	// ),//
	// // 画面に出したいところ
	// new Rect(//
	// (int) (360 * Xx),//
	// (int) (170 * Yy),//
	// (int)(((int) 360 + blockw) * Xx),//
	// (int)(((int) 170 + blockh) * Yy) //
	// ), p);//
	//
	// }
	//
	// private void drawLeftChara(Canvas c, Paint p, double Xx, double Yy) {
	// if (charaFaceIndexL == 0 || charaIndexL == 0){
	// charaFaceIndexL = 0;
	// charaIndexL = 0;
	// return;
	// }
	//
	// // 一コマの幅をゲット
	// int widthBlock = bgChara[charaIndexL].getWidth() > (600 * Xx) ? 3 : 2;
	// int blockw = bgChara[charaIndexR].getWidth() > (600 * Xx) ? 750 : 500 /
	// widthBlock;
	// // 一コマの高さをゲット
	// int blockh = 450;
	// // 描画したいコマの左上端座標をゲット
	// int indexw = (charaFaceIndexL % widthBlock == 0) ? blockw * (widthBlock -
	// 1)
	// : blockw * ((charaFaceIndexL % widthBlock) - 1);
	// int indexh = (charaFaceIndexL % widthBlock == 0) ? blockh *
	// (charaFaceIndexL / widthBlock - 1)
	// : blockh * (charaFaceIndexL / widthBlock);
	// c.drawBitmap(bgChara[charaIndexL],
	// // 画像のどこを使う
	// new Rect( //
	// (int)(indexw * Xx), //
	// (int)(indexh * Yy),//
	// (int)((indexw + blockw) * Xx),//
	// (int)((indexh + blockh) * Yy)//
	// ),//
	// // 画面に出したいところ
	// new Rect(//
	// (int) (40 * Xx),//
	// (int) (170 * Yy),//
	// (int)(((int) 40 + blockw) * Xx),//
	// (int)(((int) 170 + blockh) * Yy) //
	// ), p);//
	//
	//
	// }
	//
	//
	// public void drawImageTEST(Canvas c, double screenWidth, double
	// screenHeight, double Xx, double Yy) {
	// drawImagePC(c, Xx, Yy, 4, 1, 1, indexPauseArrow, 1.0f, 900, 500);
	// }

	public String text01[] = {

			"ふん。@Nアタシの@Nマネなんて@N十年早い@Nわよ！@E", // takeo勝ち
			"あらあら、随分弱いわねぇ～偽者さん。@E", "このブス！　アタシはもっとプリチ～だわ！@E", "なかなかやるじゃない……。@E", // 負け
			"同じキャラだもん、どっちかは負けるでしょうが！@E", "……アタシが偽者なの？@E", "坊や、おうちで遊んでなさい。@E", // Kachan勝ち
			"弱いわねぇ～。仕方ないか、アタシが相手だもんね。@E", "ふん。このダンゴ虫め！@E", "な、なんでこんなダンゴみたいなヤツに……。@E", // 負け
			"可愛い顔して強いわね……。@E", "今度はアタシが勝つ番よ！@E", "このガラクタめ！　アタシを誰だと思っているの？@E", // Kutara勝ち
			"困るのよねぇ～時間の無駄だわ。@E", "こんなロボット、アタシの敵じゃないわ。@E", "……イタタタ……卑怯よ、あんた鉄製でしょ！@E", // 負け
			"アタシにも遠距離攻撃が欲しいわぁ～！@E", "こんなジジィに負けるとは……アタシって……。@E", "誰あんた？　木みたいなヤツがアタシに勝てると思う？@E", // Ton-Gari勝ち

	};

}
