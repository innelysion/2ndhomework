package jp.tnw.a18;

//◆動けるやつら
public class NStgUnit extends GameObject {

	// 速度と加速度
	double spdX[];
	double spdY[];
	double accX[];
	double accY[];
	// 角度
	double angle[];
	// 円心と軸
	double cirCenterX[];
	double cirCenterY[];
	double axisX[];
	double axisY[];
	// 当たり判定
	boolean isHitable[];
	double hitCir[];
	double hitBoxW[];
	double hitBoxH[];

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
		isHitable = new boolean[MAX];
		hitCir = new double[MAX];
		hitBoxW = new double[MAX];
		hitBoxH = new double[MAX];

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
			isHitable[i] = false;
			hitCir[i] = 0;
			hitBoxW[i] = 0;
			hitBoxH[i] = 0;

		}

	}

	// 直线速度控制
	public void move() {

		for (int i = 0; i < MAX; i++) {
			spdX[i] += Sys.frameTime * accX[i];
			spdY[i] += Sys.frameTime * accY[i];
			dX[i] += Sys.frameTime * spdX[i];
			dY[i] += Sys.frameTime * spdY[i];
		}

	}

	// 指定出发点、速度和方向进行圆周、椭圆或螺旋运动
	// （控制初速度、初始加速度、初始角度及控制其变化）
	public void moveCir(double rotateSpd) {

		for (int i = 0; i < MAX; i++) {
			spdX[i] += Sys.frameTime * accX[i];
			spdY[i] += Sys.frameTime * accY[i];
			dX[i] += Sys.frameTime * spdX[i] * Math.cos(Math.toRadians(angle[i]));
			dY[i] += Sys.frameTime * spdY[i] * Math.sin(Math.toRadians(angle[i]));
			angle[i] += rotateSpd;
		}

	}

	// 指定圆心、半径的圆周运动或复杂曲线运动
	// （直接指定位置、速度＆加速度无效化）
	// （角度控制单位移动速度、控制角度倍率、M1~8）
	// （M1M5&M4M8和M2M6&M3M7的基础值相等）
	public void moveSp(double rotateSpd, //
			double M1, double M2, double M3, double M4, //
			double M5, double M6, double M7, double M8) {//

		for (int i = 0; i < MAX; i++) {
			dX[i] = axisX[i] * Math.cos(Math.toRadians(angle[i] * M1)) * M5//
					- axisY[i] * Math.sin(Math.toRadians(angle[i] * M2)) * M6 + cirCenterX[i];//
			dY[i] = axisY[i] * Math.sin(Math.toRadians(angle[i] * M3)) * M7//
					+ axisX[i] * Math.cos(Math.toRadians(angle[i] * M4)) * M8 + cirCenterY[i];//
			angle[i] += rotateSpd;
		}

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

		return isOutBorder((int) unit.hitBoxW[index], (int) unit.dX[index], (int) unit.hitBoxH[index],
				(int) unit.dY[index]);

	}

	// 画面端との接触判断
	public boolean isTouchBorder(NStgUnit unit, int index) {

		return isTouchBorder((int) unit.hitBoxW[index], (int) unit.dX[index], (int) unit.hitBoxH[index],
				(int) unit.dY[index]);

	}

}
