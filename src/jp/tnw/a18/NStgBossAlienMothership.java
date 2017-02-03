package jp.tnw.a18;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class NStgBossAlienMothership extends NStgUnit {

	// 動くパタン処理用フラグ、タイプとアクション
	int flag[];

	// ステータス
	int hp[];

	// タイマー
	int timerMain;
	int timerLife[];

	// ボス関係の画像
	public KomaImage boss;
	public KomaImage turret;
	public KomaImage drone;

	// リクェスト用
	private double timerReq;
	private int counterReq;

	// 複数あたり判定
	String multiHit[][];

	// 砲塔座標修正
	int turretOffsetX[] = { 112 - 28, 233 - 28, 134 - 28, 210 - 28 };
	int turretOffsetY[] = { 365 - 28, 365 - 28, 428 - 28, 428 - 28 };

	NStgBossAlienMothership() {

		super(100); // 数の上限
		boss = new KomaImage("Image/mothership.png", 2, 1);
		turret = new KomaImage("Image/turret.png", 3, 2);
		flag = new int[MAX];
		hp = new int[MAX];
		timerLife = new int[MAX];

		timerMain = 0;

		// アクションとマルチあたり判定は10個まで
		multiHit = new String[MAX][10];

		for (int i = 0; i < MAX; i++) {

			flag[i] = 0;
			hp[i] = 1;
			timerLife[i] = 0;
			timerReq = 0;
			counterReq = 0;
		}

		start();
	}

	public void update() {

		switch (flag[0]) {
		case 10000:
			dY[0] += ((-180) - dY[0]) / 100;
			if (dY[0] >= -200) {
				flag[0]++;
			}
			break;
		}

		dX[1] = dX[0] + turretOffsetX[0];
		dY[1] = dY[0] + turretOffsetY[0];
		dX[2] = dX[0] + turretOffsetX[1];
		dY[2] = dY[0] + turretOffsetY[1];
		dX[3] = dX[0] + turretOffsetX[2];
		dY[3] = dY[0] + turretOffsetY[2];
		dX[4] = dX[0] + turretOffsetX[3];
		dY[4] = dY[0] + turretOffsetY[3];

		for (int i = 0; i < MAX; i++) {
			if (flag[i] == 0) {
				continue;
			}
			if (flag[i] >= 100 && flag[i] < 1000) {

			}
		}

	}

	public void start() {

		dX[0] = SYS.WINDOW_SIZE_X / 2 - 173;
		dY[0] = -560;
		spdX[0] = 0;
		spdY[0] = 50;
		imageIndex[0] = 1;
		hitBoxW[0] = 346;
		hitBoxH[0] = 512;
		multiHit[0][0] = "R,0,0,48,48";
		multiHit[0][1] = "R,-40,10,128,28";
		multiHit[0][2] = "R,10,-40,28,128";
		hp[0] = 10000;
		flag[0] = 10000; // ボス扱い

		dX[1] = dX[0] + turretOffsetX[0];
		dY[1] = dY[0] + turretOffsetY[0];
		imageIndex[1] = 1;
		dX[2] = dX[0] + turretOffsetX[1];
		dY[2] = dY[0] + turretOffsetY[1];
		imageIndex[2] = 2;
		dX[3] = dX[0] + turretOffsetX[2];
		dY[3] = dY[0] + turretOffsetY[2];
		imageIndex[3] = 3;
		dX[4] = dX[0] + turretOffsetX[3];
		dY[4] = dY[0] + turretOffsetY[3];
		imageIndex[4] = 3;

		for (int i = 1; i < 5; i++) {
			hp[i] = 1000;
			flag[i] = 1000; // 砲塔扱い
			hitBoxW[i] = 56;
			hitBoxH[i] = 56;
		}

		for (int i = 0; i < 5; i++) {
			isVisible[i] = true;
			isHitable[i] = true;
		}
	}

	private int findIdleEnemy() {
		for (int i = 0; i < MAX; i++) {
			if (flag[i] == 0) {
				return i;
			}
		}
		System.out.println("STGWarning: <BOSS> out of limit");
		return MAX;
	}

	public void drawKoma(Graphics2D g, JFrame wind) {

		for (int i = 0; i < MAX; i++) {
			if (isVisible[i] && flag[i] >= 10000) {
				drawKoma(g, wind, boss, imageIndex[i], dX[i], dY[i], opacity[i]);
				g.setColor(new Color(255, 0, 0));
				g.drawRect((int) dX[i] + 65, (int) dY[i], 218, 350);
				g.setColor(new Color(255, 255, 0));
				g.drawRect((int) dX[i], (int) dY[i], 64, 512);
				g.drawRect((int) dX[i] + 218 + 64, (int) dY[i], 64, 512);

			}
			if (isVisible[i] && flag[i] >= 1000 && flag[i] < 10000) {
				drawKoma(g, wind, turret, imageIndex[i], dX[i], dY[i], opacity[i]);
				g.setColor(new Color(255, 0, 255));
				g.drawRect((int) dX[1], (int) dY[1], 56, 56);
				g.drawRect((int) dX[2], (int) dY[2], 56, 56);
				g.drawRect((int) dX[3], (int) dY[3], 56, 56);
				g.drawRect((int) dX[4], (int) dY[4], 56, 56);
			}
			if (isVisible[i] && flag[i] >= 100 && flag[i] < 1000) {
				drawKoma(g, wind, drone, imageIndex[i], dX[i], dY[i], opacity[i]);
			}
		}

	}

}
