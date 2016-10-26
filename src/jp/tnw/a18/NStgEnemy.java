package jp.tnw.a18;

public class NStgEnemy extends NStgUnit{
	
	// 動くパタン処理用フラグ、タイプとアクション
	int flag[];
	int type[];
	int action[][];
	
	// ステータス
	int hp[];
	
	// 生成からのタイマー
	int timerLife[];
	
	// リクェスト用
	double timerReq;
	int counterReq;
	
	NStgEnemy(){
		
		super(500); //　敵数の上限
		flag = new int[MAX];
		type = new int[MAX];
		action = new int[MAX][50]; //アクションは50個まで
		hp = new int[MAX];
		
		for (int i = 0; i < MAX; i++) {
			
			flag[i] = 0;
			type[i] = 0;
			hp[i] = 1;
			timerLife[i] = 0;
			timerReq = 0;
			counterReq = 0;
			// アクション初期化 0 = 無効
			for (int j = 0; j < 50; j++){
				action[i][j] = 0;
			}
			
		}
		
	}
	
	public void request(String enemyType){
		
		
	}
	
	public void update(){
		
	}
	
	public void reset(int i){
		
		super.reset(i);
		flag[i] = 0;
		type[i] = 0;
		hp[i] = 1;
		timerLife[i] = 0;
		for (int j = 0; j < 50; j++){
			action[i][j] = 0;
		}
		
	}

}
