package jp.tnw.a18;

//◆動けるもののスーパークラス◆//
public class NStgUnit extends GameObject {

	// 速度と加速度
	double spdX[];
	double spdY[];
	double accX[];
	double accY[];
	// 角度
	double angle[];
	// 円心と軸(特殊移動に使う)
	double cirCenterX[];
	double cirCenterY[];
	double axisX[];
	double axisY[];
	double rotation[];
	// 当たり判定
	boolean isHitable[];
	double hitCir[];
	double hitBoxW[];
	double hitBoxH[];
	// 画像の切り替えアニメのカウンター
	int timerAni[];
	// 使ているユニット
	int activing;

	// 初期値で初期化
	NStgUnit() {

		this(1);

	}

	// 初期化
	NStgUnit(int qty) {

		super(qty);
		spdX = new double[MAX];
		spdY = new double[MAX];
		accX = new double[MAX];
		accY = new double[MAX];
		angle = new double[MAX];
		cirCenterX = new double[MAX];
		cirCenterY = new double[MAX];
		axisX = new double[MAX];
		axisY = new double[MAX];
		rotation = new double[MAX];
		isHitable = new boolean[MAX];
		hitCir = new double[MAX];
		hitBoxW = new double[MAX];
		hitBoxH = new double[MAX];
		timerAni = new int[MAX];
		activing = 0;

		for (int i = 0; i < MAX; i++) {

			spdX[i] = 0;
			spdY[i] = 0;
			accX[i] = 0;
			accY[i] = 0;
			angle[i] = 0;
			cirCenterX[i] = 0;
			cirCenterY[i] = 0;
			axisX[i] = 0;
			axisY[i] = 0;
			rotation[i] = 0;
			isHitable[i] = false;
			hitCir[i] = 0;
			hitBoxW[i] = 0;
			hitBoxH[i] = 0;

		}

	}
	
	public void reset(int index) {

		imageIndex[index] = 1;
		isVisible[index] = false;
		opacity[index] = 1.0f;
		dX[index] = 0;
		dY[index] = 0;
		spdX[index] = 0;
		spdY[index] = 0;
		accX[index] = 0;
		accY[index] = 0;
		angle[index] = 0;
		cirCenterX[index] = 0;
		cirCenterY[index] = 0;
		axisX[index] = 0;
		axisY[index] = 0;
		rotation[index] = 0;
		isHitable[index] = false;
		hitCir[index] = 0;
		hitBoxW[index] = 0;
		hitBoxH[index] = 0;

	}

	// 直线速度控制
	public void move(int index) {

		spdX[index] += SYS.FRAME_TIME * accX[index];
		spdY[index] += SYS.FRAME_TIME * accY[index];
		dX[index] += SYS.FRAME_TIME * spdX[index];
		dY[index] += SYS.FRAME_TIME * spdY[index];

	}

	// 指定出发点、速度和方向进行圆周、椭圆或螺旋运动
	// （控制初速度、初始加速度、初始角度及控制其变化）
	public void moveCir(int index, double rotateSpd) {

		spdX[index] += SYS.FRAME_TIME * accX[index];
		spdY[index] += SYS.FRAME_TIME * accY[index];
		dX[index] += SYS.FRAME_TIME * spdX[index] * Math.cos(Math.toRadians(angle[index]));
		dY[index] += SYS.FRAME_TIME * spdY[index] * Math.sin(Math.toRadians(angle[index]));
		angle[index] += rotateSpd;

	}

	// 指定圆心、半径的圆周运动或复杂曲线运动
	// （直接指定位置、速度＆加速度无效化）
	// （角度控制单位移动速度、控制角度倍率、M1~8）
	// （M1M5&M4M8和M2M6&M3M7的基础值相等）
	public void moveSp(int index, double rotateSpd, //
			double M1, double M2, double M3, double M4, //
			double M5, double M6, double M7, double M8) {//

		dX[index] = axisX[index] * Math.cos(Math.toRadians(angle[index] * M1)) * M5//
				- axisY[index] * Math.sin(Math.toRadians(angle[index] * M2)) * M6 + cirCenterX[index];//
		dY[index] = axisY[index] * Math.sin(Math.toRadians(angle[index] * M3)) * M7//
				+ axisX[index] * Math.cos(Math.toRadians(angle[index] * M4)) * M8 + cirCenterY[index];//
		angle[index] += rotateSpd;

	}

	// 円衝突判定
	public boolean isHit(NStgUnit unit1, int index1, NStgUnit unit2, int index2) {

		// 衝突判定のない場合
		if (!unit1.isHitable[index1] || !unit2.isHitable[index2]) {
			return false;
		}

		double dblSaX = Math.pow(((unit1.dX[index1] + unit1.hitCir[index1])//
				- (unit2.dX[index2] + unit2.hitCir[index2])), 2);
		double dblSaY = Math.pow(((unit1.dY[index1] + unit1.hitCir[index1])//
				- (unit2.dY[index2] + unit2.hitCir[index2])), 2);
		return Math.sqrt(dblSaX + dblSaY) <= unit1.hitCir[index1] + unit2.hitCir[index2];
	}

	// 画面外判断
	public boolean isOutBorder(NStgUnit unit, int index) {

		return isOutBorder((int) unit.dX[index], (int) unit.dY[index], (int) unit.hitBoxW[index],
				(int) unit.hitBoxH[index]);

	}

	// 画面端との接触判断
	public boolean isTouchBorder(NStgUnit unit, int index) {

		return isTouchBorder((int) unit.dX[index], (int) unit.dY[index], (int) unit.hitBoxW[index],
				(int) unit.hitBoxH[index]);

	}

}
