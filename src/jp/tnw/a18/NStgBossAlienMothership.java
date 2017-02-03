package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
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

	// 座標修正
	int turretOffsetX[] = { 112 - 28, 233 - 28, 134 - 28, 210 - 28 };
	int turretOffsetY[] = { 365 - 28, 365 - 28, 428 - 28, 428 - 28 };

	// 被弾フラッシュ
	boolean bossDamage = false;
	int bossDamageTimer = 0;

	boolean turretDamage[] = { false, false, false, false };
	int turretDamageTimer[] = { 0, 0, 0, 0 };

	int bossMoveOffset = 0;
	boolean bossMoveOffsetFlag = false;

	int bossGameOverCount = 0;

	Font f = new Font("Default", Font.BOLD, 13);

	NStgBossAlienMothership() {

		super(100); // 数の上限
		dX[0] = -9999;
		dY[0] = -9999;
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

		//start();
	}

	public void update() {

		switch (flag[0]) {
		case 10000:
			dY[0] += ((-230) - dY[0]) / 100;
			if (dY[0] >= -250) {
				spdY[0] = 70;
				accY[0] = -30;
				spdX[0] = 400;
				accX[0] = -200;
				flag[0]++;
			}
			break;
		case 10001:
			if (dX[0] > SYS.WINDOW_SIZE_X / 2 - 173) {
				accX[0] = -200;
			} else {
				accX[0] = 200;
			}

			if (dY[0] > -250) {
				accY[0] = -30;
			} else {
				accY[0] = 30;
			}
			move(0);
			dY[0] -= bossMoveOffset;
			if (hp[0] <= 0) {
				isHitable[0] = false;
				flag[0] = 20000;
			}
			break;
		case 20000:
			dY[0] += ((SYS.WINDOW_SIZE_Y) - dY[0]) / 300;
			VFX.request(dX[0] + 173 + (Math.random() * 400 - 200), dY[0] + 256 + (Math.random() * 600 - 300),
					(int) (Math.random() * 9 - 1));
			if (SYS.TIMERSTAGE % 3 == 0) {
				bossDamage = true;
				flag[0] = 20001;
			}
			bossGameOverCount++;
			break;
		case 20001:
			dY[0] += ((SYS.WINDOW_SIZE_Y) - dY[0]) / 300;
			VFX.request(dX[0] + 173 + (Math.random() * 400 - 200), dY[0] + 256 + (Math.random() * 600 - 300),
					(int) (Math.random() * 9 - 1));

			if (SYS.TIMERSTAGE % 3 == 0) {
				bossDamage = false;
				flag[0] = 20000;
			}
			bossGameOverCount++;
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

		imageIndex[0] = !bossDamage ? 1 : 2;
		imageIndex[1] = !turretDamage[0] ? 1 : 4;
		imageIndex[2] = !turretDamage[1] ? 2 : 5;
		imageIndex[3] = !turretDamage[2] ? 3 : 6;
		imageIndex[4] = !turretDamage[3] ? 3 : 6;

		if (bossDamageTimer == 5) {
			bossDamage = false;
		}
		bossDamageTimer = bossDamageTimer == 0 ? 0 : bossDamageTimer - 1;

		for (int i = 0; i < 4; i++) {

			if (turretDamageTimer[i] == 5) {
				turretDamage[i] = false;
			}
			turretDamageTimer[i] = turretDamageTimer[i] == 0 ? 0 : turretDamageTimer[i] - 1;

			if (hp[i + 1] <= 0) {
				isVisible[i + 1] = false;
				isHitable[i + 1] = false;
				flag[i + 1] = 0;
			}
		}

		if (!bossMoveOffsetFlag && SYS.TIMERSTAGE % 9 == 0) {
			bossMoveOffset++;
			if (bossMoveOffset == 1) {
				bossMoveOffsetFlag = true;
			}
		} else if (bossMoveOffsetFlag && SYS.TIMERSTAGE % 5 == 0) {
			bossMoveOffset--;
			if (bossMoveOffset == 0) {
				bossMoveOffsetFlag = false;
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
		hp[0] = 5000;
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
			hp[i] = 180;
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
				g.drawRect((int) dX[i], (int) dY[i], 64, 500);
				g.drawRect((int) dX[i] + 218 + 64, (int) dY[i], 64, 500);

			}
			if (isVisible[i] && flag[i] >= 1000 && flag[i] < 10000) {
				drawKoma(g, wind, turret, imageIndex[i], dX[i], dY[i], opacity[i]);
				g.setColor(new Color(255, 0, 255));
				g.drawRect(flag[1] == 0 ? 9999 : (int) dX[1], (int) dY[1], 56, 56);
				g.drawRect(flag[2] == 0 ? 9999 : (int) dX[2], (int) dY[2], 56, 56);
				g.drawRect(flag[3] == 0 ? 9999 : (int) dX[3], (int) dY[3], 56, 56);
				g.drawRect(flag[4] == 0 ? 9999 : (int) dX[4], (int) dY[4], 56, 56);
			}
			if (isVisible[i] && flag[i] >= 100 && flag[i] < 1000) {
				drawKoma(g, wind, drone, imageIndex[i], dX[i], dY[i], opacity[i]);
			}
		}
		if (flag[0] >= 10000) {

			double bossNowHp = (((double) hp[0] / 5000) / 1);
			double turretNowHpA = (((double) hp[1] / 500) / 1);
			double turretNowHpB = (((double) hp[2] / 500) / 1);
			double turretNowHpC = (((double) hp[3] / 500) / 1);
			double turretNowHpD = (((double) hp[4] / 500) / 1);
			System.out.println(bossNowHp);
			if (bossDamageTimer > 0) {
				g.setColor(new Color(255, 128, 128));
			} else {
				g.setColor(new Color(255, 255, 255));
			}
			g.fillRect(5, SYS.WINDOW_SIZE_Y - 10, (int) ((SYS.WINDOW_SIZE_X - 10) * bossNowHp), 5);
			g.setColor(new Color(0, 255, 255));
			g.fillRect(5, SYS.WINDOW_SIZE_Y - 13, (int) ((SYS.WINDOW_SIZE_X - 10) * turretNowHpA), 2);
			g.fillRect(5, SYS.WINDOW_SIZE_Y - 16, (int) ((SYS.WINDOW_SIZE_X - 10) * turretNowHpB), 2);
			g.fillRect(5, SYS.WINDOW_SIZE_Y - 19, (int) ((SYS.WINDOW_SIZE_X - 10) * turretNowHpC), 2);
			g.fillRect(5, SYS.WINDOW_SIZE_Y - 22, (int) ((SYS.WINDOW_SIZE_X - 10) * turretNowHpD), 2);
		}

		if (bossGameOverCount > 200){
			g.setColor(new Color(255,255,255,bossGameOverCount > 455 ? 255 : bossGameOverCount - 200));
			g.fillRect(0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y);
		}

		if (bossGameOverCount > 500){
			g.setColor(Color.BLACK);// 色指定
			g.setFont(f);
			g.drawString("YOU WIN!", (int) (SYS.WINDOW_SIZE_X / 2 - 20), (int) (SYS.WINDOW_SIZE_Y / 2));
		}

		if (bossGameOverCount > 1000){
			Input.K_ESC_R = true;
		}

	}

}
