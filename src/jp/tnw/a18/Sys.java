package jp.tnw.a18;

//◆System Control◆//
public class Sys {
	
	static double frameTime;
	static boolean isPlayerMouseControl;
	static int windSizeX, windSizeY;
	static int score;
	
	public static void setup(){
		frameTime = 0.017;
		isPlayerMouseControl = true;
		windSizeX = 960;
		windSizeY = 640;
		score = 0;
	}
	
}
