package jp.tnw.a18;

//◆ゲームの流れを制御するクラス◆//
public class NStgManager {

	public NStgDanmaku danmaku;
	public NStgEnemy enemy;
	public NStgPlayerShoot shoot;
	public NStgMap map;
	public NStgUI ui;
	public GameMessage msgbox;

	// NStgItem item;

	private int stage;
	private int stageFlag;
	private int playerHitMapDelay;

	NStgManager() {

		stage = 1;
		stageFlag = 0;
		playerHitMapDelay = 0;

	}

	public void update() {

		requestGaming();
		requestDanmaku();
		requestEnemy();
		hitManage();
		playerHitMapDelay = playerHitMapDelay == 0 ? 0 : playerHitMapDelay - 1;
		SYS.TIMERSTAGE++;

	}

	// 当たり判定
	private void hitManage() {

		playerHitDanmaku();
		playerHitEnemy();
		playerHitMap();
		playerShootHitMap();
		playerShootHitEnemy();

	}

	private void requestGaming() {
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

		switch (SYS.TIMERSTAGE) {
		case 200:
			// enemy.request("雑魚B");
			break;
		}

		// if (SYS.TIMERSTAGE % 15 == 0) {
		// enemy.request("雑魚A");
		// }

		if (SYS.TIMERSTAGE > 150 && SYS.TIMERSTAGE % 60 == 0 && SYS.TIMERSTAGE < 1150) {
			enemy.request("demo01");
			if (SYS.TIMERSTAGE >= 650){
				danmaku.other1direction = true;
			}
		}

	}

	// 弾幕を撒け
	private void requestDanmaku() {
		// TODO Auto-generated method stub
		// ここから弾幕
		for (int i = 0; i < enemy.MAX; i++) {
			if (enemy.flag[i] == 3 && enemy.type[i] == 2 && SYS.TIMERSTAGE % 9 == 0) {
				danmaku.request("花火A", SYS.TIMERSTAGE % 4, enemy, i, 18, 24);
			}

			if (enemy.flag[i] == 1 && enemy.type[i] == 10 && SYS.TIMERSTAGE % 2 == 0) {
				danmaku.request("自機狙いバリア弾", 0, enemy, i, 18, 24);
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

	// 自機＆敵弾との当たり判定
	private void playerHitDanmaku() {
		// TODO Auto-generated method stub
		for (int i = 0; i < danmaku.MAX; i++) {
			if (danmaku.flag[i] != 0) {
				if (isCircleHit(danmaku.dX[i], danmaku.dY[i], NStgPlayer.dX + 48, NStgPlayer.dY + 48, danmaku.hitCir[i],
						NStgPlayer.hitCir)) {
					NStgPlayer.damage(1);
					VFX.request(danmaku.dX[i], danmaku.dY[i], 5);
					danmaku.reset(i);
					break;
				}
			}
		}

	}

	// 自機＆敵との当たり判定
	private void playerHitEnemy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemy.MAX; i++) {
			if (!NStgPlayer.IMMORTAL && enemy.flag[i] != 0) {
				if (isCircleHit(enemy.dX[i], enemy.dY[i], NStgPlayer.dX + 48, NStgPlayer.dY + 48, enemy.hitCir[i],
						NStgPlayer.hitCir)) {
					NStgPlayer.damage(2);
					enemy.hp[i] -= 5000;
					break;
				}
			}
		}
	}

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

					if (isCircleHit(shoot.dX[i], shoot.dY[i], enemy.dX[j] + 24, enemy.dY[j] + 24, shoot.hitCir[i],
							enemy.hitCir[j])) {
						enemy.hp[j] -= shoot.damage[i];
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
