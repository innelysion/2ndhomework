package jp.tnw.a18;

public class NStgOptions extends NStgUnit {

	boolean[] isActive;
	double optionsAngle[] = { 0, 45, 90, 135, 180, 225, 270, 315 };

	NStgOptions() {
		super(8);
		isActive = new boolean[MAX];
		for (int i = 0; i < MAX; i++) {
			isActive[i] = false;
		}
		
		//dbug
		isActive[0] = isActive[4] = true;
		
	}

	public void update() {
		
		int shift = Input.K_SHIFT ? 8 : 26;

		for (int i = 0; i < MAX; i++) {
			dX[i] = shift * Math.cos(Math.toRadians(optionsAngle[i])) - shift * Math.sin(Math.toRadians(optionsAngle[i]))
					+ (int) NStgPlayer.dX + 48 - 8;
			dY[i] = shift * Math.sin(Math.toRadians(optionsAngle[i])) + shift * Math.cos(Math.toRadians(optionsAngle[i]))
					+ (int) NStgPlayer.dY + 48 - 8;
			optionsAngle[i] = optionsAngle[i] > 359 ? 0 : optionsAngle[i] + 1.5;
		}

	}

}
