package jp.tnw.a18;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JFrame;

//◆ゲームの流れを制御するクラス◆//
public class NStgManager {

	public NStgDanmaku danmaku;
	public NStgEnemy enemy;
	public NStgOptions options;
	public NStgPlayerShoot shoot;
	public NStgMap map;
	public NStgUI ui;
	public NStgItem item;
	public GameMessage msgbox;

	private int stage;
	private int stageFlag;
	private int playerHitMapDelay;

	// items effect timer
	private int THEWORLD, KISSOFANGEL, CONFUSING;

	// for debug
	double fps = 0;
	private Font f = new Font("Default", Font.BOLD, 13);
	String lastItem = "";

	NStgManager() {

		stage = 1;
		stageFlag = 0;
		playerHitMapDelay = 0;

		THEWORLD = 0;
		KISSOFANGEL = 0;
		CONFUSING = 0;

	}

	public void update() {

		// requestStory();
		requestDanmaku();
		requestEnemy();
		hitManage();
		playerHitMapDelay = playerHitMapDelay == 0 ? 0 : playerHitMapDelay - 1;
		item.scrollSpd = map.scrollSpd;
		SYS.TIMERSTAGE++;

		// option shoot
		for (int i = 0; i < options.MAX; i++) {
			if (options.isActive[i] && ((options.optionsAngle[i] % 60 == 0) || (options.optionsAngle[i] % 300 == 0))) {
				shoot.optionShoot(options.dX[i], options.dY[i]);
			}
		}
		// dbug
		for (int i = 0; i < options.MAX; i++) {
			if (SYS.TIMERSTAGE % 120 == 0 && !options.isActive[i]) {
				options.isActive[i] = true;
				break;
			}
		}

		// items effect timer
		THEWORLD = THEWORLD <= 0 ? 0 : THEWORLD - 1;
		KISSOFANGEL = KISSOFANGEL <= 0 ? 0 : KISSOFANGEL - 1;
		CONFUSING = CONFUSING <= 0 ? 0 : CONFUSING - 1;
		danmaku.freezing = THEWORLD > 0 ? true : false;
		enemy.freezing = KISSOFANGEL > 0 ? true : false;
		NStgPlayer.CONFUSING = CONFUSING > 0 ? true : false;

	}

	// 当たり判定
	private void hitManage() {

		playerEatItem();
		playerHitDanmaku();
		optionHitDanmaku();
		playerHitEnemy();
		optionHitEnemy();
		playerHitMap();
		playerShootHitMap();
		playerShootHitEnemy();

	}

	public void drawHit(Graphics2D g, JFrame wind) {

		g.setColor(Color.WHITE);
		for (int i = 0; i < item.MAX; i++) {
			if (item.flag[i] != 0) {
				g.drawArc((int) item.dX[i], (int) item.dY[i], 60, 60, 0 + SYS.TIMERSTAGE % 360, 60);
				g.drawArc((int) item.dX[i], (int) item.dY[i], 60, 60, 0 + 120 + SYS.TIMERSTAGE % 360, 60);
				g.drawArc((int) item.dX[i], (int) item.dY[i], 60, 60, 0 + 240 + SYS.TIMERSTAGE % 360, 60);
			}
		}
		g.setColor(Color.WHITE);
		for (int i = 0; i < options.MAX; i++) {
			if (options.isActive[i]) {
				g.drawArc((int) options.dX[i], (int) options.dY[i], 16, 16, 0 + SYS.TIMERSTAGE % 180 * 2, 45);
				g.drawArc((int) options.dX[i], (int) options.dY[i], 16, 16, 90 + SYS.TIMERSTAGE % 180 * 2, 45);
				g.drawArc((int) options.dX[i], (int) options.dY[i], 16, 16, 180 + SYS.TIMERSTAGE % 180 * 2, 45);
				g.drawArc((int) options.dX[i], (int) options.dY[i], 16, 16, 270 + SYS.TIMERSTAGE % 180 * 2, 45);
			}
		}
		g.drawArc((int) NStgPlayer.dX, (int) NStgPlayer.dY, 96, 96, -90 + SYS.TIMERSTAGE % 90 * 4, 180);
		g.setColor(NStgPlayer.IMMORTAL ? Color.YELLOW : Color.RED);
		g.drawArc((int) NStgPlayer.dX + 41, (int) NStgPlayer.dY + 41, 14, 14, -SYS.TIMERSTAGE % 180 * 2, 270);
		g.drawArc((int) NStgPlayer.dX + 40, (int) NStgPlayer.dY + 40, 16, 16, -SYS.TIMERSTAGE % 180 * 2, 270);
		g.setColor(Color.RED);
		for (int i = 0; i < enemy.MAX; i++) {
			if (enemy.flag[i] != 0) {
				g.drawArc((int) enemy.dX[i], (int) enemy.dY[i], 48, 48, 0 + SYS.TIMERSTAGE % 90 * 4, 180);
			}
		}

		g.setFont(f);
		g.setColor(Color.MAGENTA);
		// g.drawString("", SYS.WINDOW_SIZE_X - 170 - 15, SYS.WINDOW_SIZE_Y -
		// 110);
		// g.drawString("", SYS.WINDOW_SIZE_X - 170 - 15, SYS.WINDOW_SIZE_Y -
		// 90);
		if (NStgPlayer.CONFUSING) {
			g.drawString("反転中", (int) NStgPlayer.dX + 28, (int) NStgPlayer.dY + 30);
		}
		g.drawString("先とったアイテム: " + lastItem, 100, SYS.WINDOW_SIZE_Y - 70);
		g.drawString("活動敵弾数: " + String.valueOf(danmaku.activing), 100, SYS.WINDOW_SIZE_Y - 50);
		g.drawString("ステージタイマー: " + String.valueOf(SYS.TIMERSTAGE), 100, SYS.WINDOW_SIZE_Y - 30);
		g.drawString("A17張瀚夫", 100, SYS.WINDOW_SIZE_Y - 10);

	}

	// 会話と特殊演出
	private void requestStory() {
		// TODO Auto-generated method stub
		switch (SYS.TIMERSTAGE) {
		case 280:
			for (int i = 0; i < enemy.MAX; i++) {
				enemy.killAllEnemy();
				danmaku.resetAllDanmaku();
			}
			break;
		case 300:
			ui.requestStoryModeWithRotation();
			break;
		}

		switch (stageFlag) {
		case 0:
			if (SYS.TIMERSTAGE > 300 && ui.isReadyForPlay) {
				msgbox.request(0);
				stageFlag++;
			}
			break;
		case 1:
			if (msgbox.requesting == null) {
				ui.stopStoryMode();
				stageFlag++;
			}
			break;
		}

	}

	// 敵を呼び出す
	private void requestEnemy() {
		// TODO Auto-generated method stub
		// ここから敵
		// if (!enemy.freezing) {
		switch (SYS.TIMERSTAGE) {
		case 1900:
			enemy.request("雑魚B");
			break;
		}

		 if (SYS.TIMERSTAGE % 15 == 0 && !enemy.freezing && SYS.TIMERSTAGE > 2800) {
		 enemy.request("雑魚A");
		 }

		// 弾幕バリア攻撃の雑魚
		if (SYS.TIMERSTAGE > 150 && SYS.TIMERSTAGE % 60 == 0 && SYS.TIMERSTAGE < 1500) {
			enemy.request("demo01");
			if (SYS.TIMERSTAGE >= 900) {
				danmaku.other1direction = true;
			}
		}
		// }

	}

	// 弾幕を撒け
	private void requestDanmaku() {
		// TODO Auto-generated method stub
		// ここから弾幕
		if (!danmaku.freezing && !enemy.freezing) {
			for (int i = 0; i < enemy.MAX; i++) {
				if (enemy.flag[i] == 3 && enemy.type[i] == 2 && SYS.TIMERSTAGE % 9 == 0) {
					danmaku.request("花火A", SYS.TIMERSTAGE % 4, enemy, i, 18, 24);
				}
				if (enemy.type[i] == 10 && enemy.timerLife[i] < 300 && SYS.TIMERSTAGE % 2 == 0) {
					danmaku.request("自機狙いバリア弾", 0, enemy, i, 18, 24);
					if (SYS.TIMERSTAGE % 6 == 0) {
						VFX.request(enemy.dX[i] + 24, enemy.dY[i] + 35, 4);
					}
				}
			}
		}

		for (int i = 0; i < options.MAX; i++) {
			if (options.isActive[i] && SYS.TIMERSTAGE % 3 == 0) {
				danmaku.request("オプションしっぽ", 0, options, i, 0, 0);
			}
		}
		// ここから演出用弾幕
		for (int i = 0; i < danmaku.MAX; i++) {
			if (danmaku.type[i] == 1 && danmaku.flag[i] == 1 && danmaku.timerLife[i] > 100
					&& danmaku.timerLife[i] % 1 == 0) {
				danmaku.request("花火しっぽ", 0, danmaku, i, 0, 0);
			}
		}
	}

	private void playerEatItem() {
		// TODO Auto-generated method stub
		for (int i = 0; i < item.MAX; i++) {
			if (item.flag[i] != 0) {
				if (isCircleHit(item.dX[i], item.dY[i], NStgPlayer.dX, NStgPlayer.dY, 30, 48)) {
					switch (item.type[i]) {
					case 1:
						NStgPlayer.HP++;
						lastItem = "回復+1";
						break;
					case 3:
						THEWORLD = 60;
						lastItem = "敵弾フリーズ";
						break;
					case 4:
						for (int j = 0; j < options.MAX; j++) {
							options.isActive[j] = true;
						}
						lastItem = "オプション全充填";
						break;
					case 5:
						KISSOFANGEL = 60;
						lastItem = "敵止め";
						break;
					case 6:
						CONFUSING = 600;
						lastItem = "入力反転!!";
						break;
					}
					item.reset(i);
				}
			}
		}
	}

	// 自機＆敵弾との当たり判定
	private void playerHitDanmaku() {
		// TODO Auto-generated method stub
		for (int i = 0; i < danmaku.MAX; i++) {
			if (danmaku.flag[i] != 0 && !danmaku.belongPlayer[i]) {
				if (isCircleHit(danmaku.dX[i] + 4, danmaku.dY[i] + 4, NStgPlayer.dX + 43, NStgPlayer.dY + 43, 4, 5)) {
					NStgPlayer.damage(1);
					VFX.request(danmaku.dX[i], danmaku.dY[i], 5);
					danmaku.reset(i);
					break;
				}
			}
		}

	}

	private void optionHitDanmaku() {
		// TODO Auto-generated method stub
		for (int i = 0; i < danmaku.MAX; i++) {
			if (danmaku.flag[i] != 0 && !danmaku.belongPlayer[i]) {
				for (int j = 0; j < options.MAX; j++) {
					if (options.isActive[j]
							&& isCircleHit(danmaku.dX[i], danmaku.dY[i], options.dX[j], options.dY[j], 8, 8)) {
						VFX.request(danmaku.dX[i], danmaku.dY[i], 5);
						VFX.request(options.dX[j], options.dY[j], 3);
						danmaku.reset(i);
						options.isActive[j] = false;
					}
				}
			}
		}
	}

	// 自機＆敵との当たり判定
	private void playerHitEnemy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemy.MAX; i++) {
			if (enemy.flag[i] != 0) {
				if (isCircleHit(enemy.dX[i], enemy.dY[i], NStgPlayer.dX + 43, NStgPlayer.dY + 43, 24, 5)) {
					NStgPlayer.damage(2);
					enemy.hp[i] -= 100;
					break;
				}
			}
		}
	}

	private void optionHitEnemy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemy.MAX; i++) {
			if (enemy.flag[i] != 0) {
				for (int j = 0; j < options.MAX; j++) {
					if (options.isActive[j]
							&& isCircleHit(enemy.dX[i], enemy.dY[i], options.dX[j], options.dY[j], 24, 8)) {
						enemy.hp[i] -= 10;
						VFX.request(options.dX[j], options.dY[j], 3);
						options.isActive[j] = false;
					}
				}
			}
		}
	}

	// 自機＆背景との当たり判定
	private void playerHitMap() {
		// TODO Auto-generated method stub
		if (map.isMapHit(NStgPlayer.dX + 48, NStgPlayer.dY + 48)) {
			if (!NStgPlayer.IMMORTAL) {
				NStgPlayer.damage(1);
			}
			if (playerHitMapDelay == 0) {
				VFX.request(NStgPlayer.dX + 48, NStgPlayer.dY + 48, 0);
				playerHitMapDelay = 15;
			}
		}

	}

	// 自機弾＆敵との当たり判定
	private void playerShootHitEnemy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < shoot.MAX; i++) {
			if (shoot.type[i] != 0) {

				for (int j = 0; j < enemy.MAX; j++) {
					// 待機中と当たり判定なしのものを除く
					if (enemy.type[j] == 0 || enemy.flag[j] == 0 || !enemy.isHitable[j]) {
						continue;
					}

					if (isCircleHit(shoot.dX[i], shoot.dY[i], enemy.dX[j], enemy.dY[j], 8, 24)) {
						enemy.hp[j] -= shoot.damage[i];

						// アイテムドロップ
						if (enemy.hp[j] <= 0 && enemy.itemDrop[j] > 0) {
							String drop = null;
							switch (enemy.itemDrop[j]) {
							case 1:
								drop = "LIFE";
								break;
							case 2:
								drop = "BOMB";
								break;
							case 3:
								drop = "THEWORLD";
								break;
							case 4:
								drop = "OPTION";
								break;
							case 5:
								drop = "ROSE";
								break;
							case 6:
								drop = "MUSHROOM";
								break;
							}
							item.request(drop, enemy, j, -3, -3);
							enemy.itemDrop[j] = 0;
						}
						VFX.request(shoot.dX[i] + 8, shoot.dY[i] + 8, 7);
						shoot.reset(i);
					}
				}
			}
		}

	}

	// 自機弾＆背景との当たり判定
	private void playerShootHitMap() {
		// TODO Auto-generated method stub
		for (int i = 0; i < shoot.MAX; i++) {
			if (shoot.flag[i] > 0 && map.isMapHit(shoot.dX[i] + 8, shoot.dY[i] + 8)) {
				VFX.request(shoot.dX[i] + 8, shoot.dY[i] + 8, 7);
				shoot.reset(i);
			}
		}
	}

	// 四角形同士あたり判定 rect(lefttop_x, lefttop_y, width, height)
	public boolean isRectHit(double rx1, double ry1, double rw1, double rh1, double rx2, double ry2, double rw2,
			double rh2) {
		return !(rx1 >= rx2 && rx1 >= rx2 + rw2) || //
				!(rx1 <= rx2 && rx1 + rw1 <= rx2) || //
				!(ry1 >= ry2 && ry1 >= ry2 + rh2) || //
				!(ry1 <= ry2 && ry1 + rh1 <= ry2);
	}

	// 円同士あたり判定 circle(center_x, center_y, radius)
	public boolean isCircleHit(double cx1, double cy1, double cx2, double cy2, double cr1, double cr2) {
		return //
		(Math.sqrt(//
				Math.pow((cx1 + cr1) - (cx2 + cr2), 2) + //
						Math.pow((cy1 + cr1) - (cy2 + cr2), 2)) //
		<= cr1 + cr2);

	}

	// 四角形と円あたり判定 circle(center_x, center_y, radius) rect(lefttop_x, lefttop_y,
	// width, height)
	public boolean isCricleHitRect(double rx, double ry, double rw, double rh, double cx, double cy, double cr) {
		double rcx = cx - (rx + rw * 0.5);
		double rcy = cy - (ry + rh * 0.5);
		double dx1 = Math.min(rcx, rw * 0.5);
		double dx2 = Math.max(dx1, -rw * 0.5);
		double dy1 = Math.min(rcy, rh * 0.5);
		double dy2 = Math.max(dy1, -rh * 0.5);
		return Math.pow((dx2 - rcx), 2) + Math.pow((dy2 - rcy), 2) <= Math.pow(cr, 2);
	}
}
