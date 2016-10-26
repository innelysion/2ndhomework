package jp.tnw.a18;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class StgUI extends StgImage {
	// TEST
	BufferedImage image;
	BufferedImage gameoverImage;
	float opacity;
	int dX, dY;
	int fadeHP; 
	int timer;

	StgUI() {

		opacity = 0.0f;
		fadeHP = NStgPlayer.MAXHP;
		dX = (int) NStgPlayer.dX - 24;
		dY = (int) NStgPlayer.dY + 80;
		image = loadImage(image, "Image/dmg.png");
		gameoverImage = loadImage(image, "Image/g_over.png");
	}

	public void drawImage(Graphics2D g, JFrame wind) {

		g.drawImage(image, //
				dX, dY, dX + 144, dY + 8, //
				0, 0, 144, 0 + 8, //
				wind); //

		g.drawImage(image, //
				dX, dY, dX + 144 * fadeHP / NStgPlayer.MAXHP, dY + 8, //
				0, 16, 144 * fadeHP / NStgPlayer.MAXHP, 16 + 8, //
				wind);

		g.drawImage(image, //
				dX, dY, dX + 144 * NStgPlayer.HP / NStgPlayer.MAXHP, dY + 8, //
				0, 8, 144 * NStgPlayer.HP / NStgPlayer.MAXHP, 8 + 8, //
				wind); //

	}

	public void drawGameover(Graphics2D g, JFrame wind){
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));
		g.drawImage(gameoverImage, 0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y, 0, 0, 800, 600, wind);
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));
	}

	public void update() {

		if (SYS.GAMEOVERING){
			opacity = opacity >= 0.998f ? 1.0f : opacity + 0.002f;
		}

		if (timer % 5 == 0) {
			if (fadeHP < NStgPlayer.HP) {
				fadeHP++;
			} else if (fadeHP > NStgPlayer.HP) {
				fadeHP--;
			}
		}

		dX = (int) NStgPlayer.dX - 24;
		dY = (int) NStgPlayer.dY + 80;

		timer++;

	}

}
