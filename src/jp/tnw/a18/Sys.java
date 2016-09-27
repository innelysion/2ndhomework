package jp.tnw.a18;

//◆System Control◆//
public class Sys {

	static int windowSizeX, windowSizeY;
	static double frameTime;
	static boolean isPlayerMouseControl;
	static boolean pc, android, ios;

	// for mobile screen
	static double Xx, Yy;

	public static void setupPC(){
		frameTime = 0.017;
		isPlayerMouseControl = false;
		windowSizeX = 960;
		windowSizeY = 540;
		pc = true;
	}

	public static void setupANDROID(){
		frameTime = 0.017;
		isPlayerMouseControl = true;
		windowSizeX = 960;
		windowSizeY = 540;
		android = true;
	}

}
