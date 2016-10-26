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
		fadeHP = StgPlayer.MAXHP;
		dX = (int) StgPlayer.dX - 24;
		dY = (int) StgPlayer.dY + 80;
		image = loadImage(image, "Image/dmg.png");
		gameoverImage = loadImage(image, "Image/g_over.png");
	}

	public void drawImage(Graphics2D g, JFrame wind) {

		g.drawImage(image, //
				dX, dY, dX + 144, dY + 8, //
				0, 0, 144, 0 + 8, //
				wind); //

		g.drawImage(image, //
				dX, dY, dX + 144 * fadeHP / StgPlayer.MAXHP, dY + 8, //
				0, 16, 144 * fadeHP / StgPlayer.MAXHP, 16 + 8, //
				wind);

		g.drawImage(image, //
				dX, dY, dX + 144 * StgPlayer.HP / StgPlayer.MAXHP, dY + 8, //
				0, 8, 144 * StgPlayer.HP / StgPlayer.MAXHP, 8 + 8, //
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
			if (fadeHP < StgPlayer.HP) {
				fadeHP++;
			} else if (fadeHP > StgPlayer.HP) {
				fadeHP--;
			}
		}

		dX = (int) StgPlayer.dX - 24;
		dY = (int) StgPlayer.dY + 80;

		timer++;

	}

}
