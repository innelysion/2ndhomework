package jp.tnw.a18;

public class NStgManager {

	NStgDanmaku danmaku;
	NStgEnemy enemy;
	NStgPlayerShoot shoot;
	NStgMap map;
	int stage;
	// NStgItem item;

	NStgManager() {

	}

	public void update() {

		requestDanmaku();
		requestEnemy();
		hitManage();

	}

	private void requestEnemy() {
		// TODO Auto-generated method stub
		// ここから敵
//		if (SYS.TIMERSTAGE % 15 == 0) {
//			enemy.request("雑魚A");
//		}

		if (SYS.TIMERSTAGE % 1000 == 0) {
			enemy.request("雑魚B");
		}

	}

	private void requestDanmaku() {
		// TODO Auto-generated method stub
		// ここから弾幕
		for (int i = 0; i < enemy.MAX; i++) {
			if (enemy.flag[i] == 3 && enemy.type[i] == 2 && SYS.TIMERSTAGE % 1 == 0) {
				danmaku.request("花火A", SYS.TIMERSTAGE % 4, enemy, i, 18, 24);
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

	private void hitManage() {

		playerHitDanmaku();
		playerShootHitMap();
		playerShootHitEnemy();

		// TODO Auto-generated method stub
		// ここからあたり判定

	}

	private void playerShootHitEnemy() {
		// TODO Auto-generated method stub
		for (int i = 0; i < shoot.MAX; i++) {
			if (shoot.type[i] != 0) {

				for (int j = 0; j < enemy.MAX; j++) {
//					if (enemy.type[i] == 0 || enemy.flag[i] == 0) {
//						continue;
//					}

					if (isCircleHit(shoot.dX[i], shoot.dY[i], enemy.dX[j], enemy.dY[j], shoot.hitCir[i],
							enemy.hitCir[j])) {
						enemy.hp[j] -= shoot.damage[i];
						VFX.request(shoot.dX[i] + 8, shoot.dY[i] + 8, 7);
						shoot.reset(i);
						break;
					}

				}
			}
		}

	}

	private void playerHitDanmaku() {
		// TODO Auto-generated method stub
		for (int i = 0; i < danmaku.MAX; i++){
			if (danmaku.flag[i] != 0){
				if (isCircleHit(danmaku.dX[i], danmaku.dY[i], NStgPlayer.dX + 48, NStgPlayer.dY + 48, danmaku.hitCir[i], NStgPlayer.hitCir)){
					NStgPlayer.damage(1);
					danmaku.reset(i);
				}
			}
		}

	}

	private void playerShootHitMap() {
		// TODO Auto-generated method stub
		for (int i = 0; i < shoot.MAX; i++) {
			if (shoot.flag[i] > 0 && map.isMapHit(shoot.dX[i] + 8, shoot.dY[i] + 8)) {
				VFX.request(shoot.dX[i] + 8, shoot.dY[i] + 8, 7);
				shoot.reset(i);
				break;
			}
		}
	}

	// 四角形同士あたり判定
	public boolean isRectHit(double rx1, double ry1, double rw1, double rh1, double rx2, double ry2, double rw2,
			double rh2) {
		return !(rx1 >= rx2 && rx1 >= rx2 + rw2) || //
				!(rx1 <= rx2 && rx1 + rw1 <= rx2) || //
				!(ry1 >= ry2 && ry1 >= ry2 + rh2) || //
				!(ry1 <= ry2 && ry1 + rh1 <= ry2);
	}

	// 円同士あたり判定
	public boolean isCircleHit(double cx1, double cy1, double cx2, double cy2, double cr1, double cr2) {
		return //
		(Math.sqrt(//
				Math.pow((cx1 + cr1) - (cx2 + cr2), 2) + //
						Math.pow((cy1 + cr1) - (cy2 + cr2), 2)) //
		<= cr1 + cr2);

	}

	// 四角形と円あたり判定
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
