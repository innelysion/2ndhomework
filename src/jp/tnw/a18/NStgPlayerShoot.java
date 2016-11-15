package jp.tnw.a18;

//◆自機弾のクラス◆//
public class NStgPlayerShoot extends NStgDanmaku {

	// 自機弾に必要なもの

	private double timerJikiReq = 0;

	// オプション
	private double optionX[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private double optionY[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private double optionAngle[] = { 0, 45, 90, 135, 180, 225, 270, 315 };
	private boolean optionActive[] = {false, false, false, false, false, false, false, false};

	public void update() {
		

		if (((SYS.MOUSE_CONTROLING) || (!SYS.MOUSE_CONTROLING)) && (!NStgPlayer.STOPSHOOT)) {
			playerShoot();
		}
		
		if (NStgPlayer.BOMBING){
			playerShoot();
		}
		

		for (int i = 0; i < 8; i++) {
			if (Input.K_SHIFT) {
				optionX[i] = 30 * Math.cos(Math.toRadians(optionAngle[i]))
						- 30 * Math.sin(Math.toRadians(optionAngle[i])) + (int) NStgPlayer.dX + 48 - 8;
				optionY[i] = 30 * Math.sin(Math.toRadians(optionAngle[i]))
						+ 30 * Math.cos(Math.toRadians(optionAngle[i])) + (int) NStgPlayer.dY + 48 - 8;
				optionAngle[i] += 2.5;
			} else {
				optionX[i] = 70 * Math.cos(Math.toRadians(optionAngle[i]))
						- 70 * Math.sin(Math.toRadians(optionAngle[i])) + (int) NStgPlayer.dX + 48 - 8;
				optionY[i] = 70 * Math.sin(Math.toRadians(optionAngle[i]))
						+ 70 * Math.cos(Math.toRadians(optionAngle[i])) + (int) NStgPlayer.dY + 48 - 8;
				optionAngle[i] += 2.5;
			}
		}
		

		for (int i = 0; i < MAX; i++) {

			spdX[i] += SYS.FRAME_TIME * accX[i];
			spdY[i] += SYS.FRAME_TIME * accY[i];
			dX[i] += SYS.FRAME_TIME * spdX[i];
			dY[i] += SYS.FRAME_TIME * spdY[i];
			angle[i]++;
			
			if (type[i] == 1000 && SYS.TIMERSTAGE % 2 == 0){
				imageIndex[i] = imageIndex[i] > 123 ? 121 : imageIndex[i] + 1;
			} 

			if (type[i] == 4 && (dX[i] < SYS.WINDOW_SIZE_X - 16)) {

				VFX.request(dX[i] + 8, dY[i] + 8, 7);

			} else if (type[i] == 5 && (dX[i] < SYS.WINDOW_SIZE_X - 16)) {

				VFX.request(dX[i] + 8, dY[i] + 8, 6);

			} else if (type[i] == 6) {

				VFX.request(dX[i] + 8, dY[i] + 8, 5);

			} else if (type[i] == 7) {

				VFX.request(dX[i] + 8, dY[i] + 8, 4);
			}

			if (flag[i] == 2) {
				dX[i] += Math.random() * 20;
			} else if (flag[i] == 3) {
				dX[i] -= Math.random() * 20;
			}

			if (timerJikiReq < 0) {
				timerJikiReq = 8;
				if (flag[i] == 2) {
					flag[i] = 3;
				} else if (flag[i] == 3) {
					flag[i] = 2;
				}
			}
			timerJikiReq--;

			if (type[i] != 0 && isOutBorder((int) dX[i], (int) dY[i], 16, 16)) {

				isVisible[i] = false;
				isHitable[i] = false;
				imageIndex[i] = 1;
				dX[i] = 9999;
				spdX[i] = 0;
				spdY[i] = 0;
				accX[i] = 0;
				accY[i] = 0;
				type[i] = 0;
				flag[i] = 0;

			}

		}
	}
	
	public void optionShoot(double gX, double gY) {
		for (int i = 0; i < MAX; i++) {
			if (flag[i] == 0 && type[i] == 0) {
				dX[i] = gX;
				dY[i] = gY;
				spdY[i] = -300;
				type[i] = 1000;
				flag[i] = 1000;
				imageIndex[i] = 121;
				isVisible[i] = true;
				isHitable[i] = true;
				hitCir[i] = 16;
				belongPlayer[i] = true;
				break;
			}
		}
	}

	private void playerShoot() {
		timerReq = timerReq - SYS.FRAME_TIME;
		if (timerReq < 0) {
			timerReq = NStgPlayer.POWER >= 3 ? 0.1 : 0.05;

			if (!NStgPlayer.BOMBING) {
				if (NStgPlayer.POWER == 0) {

					for (int i = 0; i < MAX; i++) {
						if (flag[i] == 0 && type[i] == 0) {

							type[i] = 1;
							imageIndex[i] = 1;
							dX[i] = NStgPlayer.dX + 48 - 8;
							dY[i] = NStgPlayer.dY + 48 - 8;
							spdX[i] = -100 + (Input.K_SHIFT ? Math.random() * 200 : 100);
							spdY[i] = -1000;
							opacity[i] = 0.5f;
							isVisible[i] = true;
							isHitable[i] = true;
							hitCir[i] = 16;
							belongPlayer[i] = true;
							flag[i] = 1;

							break;
						}
					}

				} else if (NStgPlayer.POWER == 1) {

					int cnt = 2;

					for (int i = 0; i < MAX; i++) {
						if (flag[i] == 0 && type[i] == 0) {

							type[i] = 2;
							imageIndex[i] = 1;
							dX[i] = NStgPlayer.dX + 48 - 8 - 45 + (30 * cnt);
							dY[i] = NStgPlayer.dY + 48 - 8;
							spdX[i] = -100 + (Input.K_SHIFT ? Math.random() * 200 : 100);
							spdY[i] = -1000;
							opacity[i] = 0.5f;
							isVisible[i] = true;
							isHitable[i] = true;
							hitCir[i] = 16;
							belongPlayer[i] = true;
							flag[i] = 1;
							cnt--;
							if (cnt == 0) {
								break;
							}

						}
					}

				} else if (NStgPlayer.POWER == 2) {

					int cnt = 3;

					for (int i = 0; i < MAX; i++) {
						if (flag[i] == 0 && type[i] == 0) {

							type[i] = 2;
							imageIndex[i] = 1;
							dX[i] = NStgPlayer.dX + 48 - 8 - 40 + (20 * cnt);
							dY[i] = NStgPlayer.dY + 48 - 8;
							spdX[i] = -100 + (Input.K_SHIFT ? Math.random() * 200 : 100);
							spdY[i] = -1000;
							opacity[i] = 0.5f;
							isVisible[i] = true;
							isHitable[i] = true;
							hitCir[i] = 16;
							belongPlayer[i] = true;
							flag[i] = 1;
							cnt--;
							if (cnt == 0) {
								break;
							}

						}
					}

				} else if (NStgPlayer.POWER == 3) {

					int cnt = 3;

					for (int i = 0; i < MAX; i++) {
						if (flag[i] == 0 && type[i] == 0) {

							type[i] = 2;
							imageIndex[i] = 49;
							dX[i] = NStgPlayer.dX + 48 - 8 - 40 + (20 * cnt);
							dY[i] = NStgPlayer.dY + 48 - 8;
							spdX[i] = -300 + (150 * cnt);
							spdY[i] = -1000;
							opacity[i] = 0.5f;
							isVisible[i] = true;
							isHitable[i] = true;
							hitCir[i] = 16;
							damage[i] = 2;
							belongPlayer[i] = true;
							flag[i] = 1;
							cnt--;
							if (cnt == 0) {
								break;
							}

						}
					}

				} else if (NStgPlayer.POWER == 4) {

					int cnt = 5;

					for (int i = 0; i < MAX; i++) {
						if (flag[i] == 0 && type[i] == 0) {

							type[i] = 2;
							imageIndex[i] = 49;
							dX[i] = NStgPlayer.dX + 48 - 8 - 60 + (20 * cnt);
							dY[i] = NStgPlayer.dY + 48 - 8;
							spdX[i] = -1200 + (400 * cnt);
							spdY[i] = -1000;
							opacity[i] = 0.5f;
							isVisible[i] = true;
							isHitable[i] = true;
							hitCir[i] = 16;
							damage[i] = 2;
							belongPlayer[i] = true;
							flag[i] = 1;
							cnt--;
							if (cnt == 0) {
								break;
							}

						}
					}

				} else if (NStgPlayer.POWER == 5) {

					int cnt = 6;

					for (int i = 0; i < MAX; i++) {
						if (flag[i] == 0 && type[i] == 0) {
							opacity[i] = 0.5f;
							type[i] = 3;
							imageIndex[i] = 49;

							dX[i] = NStgPlayer.dX + (48 - 14) + (3 * cnt);
							dY[i] = NStgPlayer.dY + 48 + 5;
							if (Input.K_SHIFT) {
								spdX[i] = 50 * (cnt - 2) * Math.sin(angle[i]);
							} else {
								spdX[i] = 200 * (cnt - 2) * Math.cos(angle[i]);
							}
							spdY[i] = -SYS.WINDOW_SIZE_Y * 2;
							isVisible[i] = true;
							isHitable[i] = true;
							hitCir[i] = 16;
							damage[i] = 2;
							belongPlayer[i] = true;
							flag[i] = 1;
							cnt--;
							if (cnt == 0) {
								break;
							}

						}
					}

				} else if (NStgPlayer.POWER == 6) {

//					int cnt = 4;
//
//					for (int i = 0; i < MAX; i++) {
//						if (flag[i] == 0 && type[i] == 0) {
//
//							type[i] = Input.K_SHIFT ? 5 : 4;
//							imageIndex[i] = 21;
//
//							spdX[i] = 0;
//							spdY[i] = 0;
//							accY[i] = -2000;
//
//							dX[i] = optionX[Math.abs(cnt - 4)];
//							dY[i] = optionY[Math.abs(cnt - 4)];
//							if (Input.K_SHIFT) {
//								VFX.request(dX[i] + 8, dY[i] + 8, 4);
//							} else {
//								VFX.request(dX[i] + 8, dY[i] + 8, 5);
//							}
//							if (dX[i] < SYS.WINDOW_SIZE_X - 16) {
//								isVisible[i] = true;
//								isHitable[i] = true;
//								hitCir[i] = 16;
//								belongPlayer[i] = true;
//								flag[i] = 1;
//							}
//							cnt--;
//							if (cnt == 0) {
//								break;
//							}
//						}
//					}

				} else if (NStgPlayer.POWER == 7) {

//					int cnt = 4;
//
//					for (int i = 0; i < MAX; i++) {
//						if (flag[i] == 0 && type[i] == 0) {
//
//							type[i] = Input.K_SHIFT ? 7 : 6;
//							imageIndex[i] = 163;
//							dX[i] = NStgPlayer.dX + 48 - 8;
//							dY[i] = NStgPlayer.dY + 48 - 8;
//							spdX[i] = 0;
//							spdY[i] = Input.K_SHIFT ? 100 + Math.random() * 200 : -500;
//							accX[i] = Input.K_SHIFT ? -1200 + Math.random() * 2400 : -800 + Math.random() * 1600;
//							accY[i] = Input.K_SHIFT ? -500 : 0;
//							isVisible[i] = true;
//							isHitable[i] = true;
//							hitCir[i] = 32;
//							damage[i] = 4;
//							belongPlayer[i] = true;
//							flag[i] = 2;
//							cnt--;
//							if (cnt == 0) {
//								break;
//							}
//						}
//					}
					
				}
			}

			if (NStgPlayer.BOMBING) {

				int cnt = 8;

				for (int i = 0; i < MAX; i++) {
					if (flag[i] == 0 && type[i] == 0) {

						type[i] = cnt % 2 == 0 ? 7 : 6;
						imageIndex[i] = 163;
						dX[i] = NStgPlayer.dX + 48 - 8;
						dY[i] = NStgPlayer.dY + 48 - 8;
						spdX[i] = 0;
						spdY[i] = cnt % 2 == 0 ? 0 + Math.random() * 200 : -500;
						accX[i] = cnt % 2 == 0 ? -1200 + Math.random() * 2400 : -800 + Math.random() * 1600;
						accY[i] = cnt % 2 == 0 ? -500 : 0;
						isVisible[i] = true;
						isHitable[i] = true;
						belongPlayer[i] = true;
						flag[i] = 2;
						cnt--;
						if (cnt == 0) {
							break;
						}

					}
				}
			}
		}
	}

}
