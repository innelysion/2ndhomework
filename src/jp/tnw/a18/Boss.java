package jp.tnw.a18;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Boss extends NStgUnit{
	
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
	
	//複数あたり判定
	String multiHit[][];

	Boss() {

		super(100); // 数の上限
		boss = new KomaImage("Image/mothership.png", 2, 1);
		turret = new KomaImage("Image/turret.png", 3, 2);
		flag = new int[MAX];
		hp = new int[MAX];
		timerLife = new int[MAX];
		
		timerMain = 0;
		
		//アクションとマルチあたり判定は10個まで
		multiHit = new String[MAX][10];
		
		for (int i = 0; i < MAX; i++) {

			flag[i] = 0;
			hp[i] = 1;
			timerLife[i] = 0;
			timerReq = 0;
			counterReq = 0;
		}
	}
	
	public void start(){
		
	}
	
	public void drawKoma(Graphics2D g, JFrame wind) {

		drawKoma(g, wind, boss);
		drawKoma(g, wind, turret);
		//drawKoma(g, wind, drone);

	}

}
