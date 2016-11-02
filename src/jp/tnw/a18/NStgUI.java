package jp.tnw.a18;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class NStgUI extends GameObject {

	KomaImage bar_hp;
	BufferedImage gameover;
	Font f = new Font("Default", Font.BOLD, 13);

	float opacity;
	int dX, dY;
	int fadeHP;

	// ストーリーシーン用
	boolean playStory;
	int storyBackHeight;
	boolean screenRotation;
	double gRotate;
	int timerStoryAni;

	NStgUI() {

		super(2);
		bar_hp = new KomaImage("Image/dmg.png", 1, 4);
		gameover = loadImage("Image/g_over.png");
		opacity = 0.0f;

		fadeHP = NStgPlayer.MAXHP;
		dX = (int) NStgPlayer.dX - 24;
		dY = (int) NStgPlayer.dY + 80;

		playStory = false;
		screenRotation = false;
		storyBackHeight = 0;
		gRotate = 0;
		timerStoryAni = 0;

	}

	public void draw(Graphics2D g, JFrame wind) {


		g.setColor(Color.BLACK);// 色指定
		g.setFont(f);

		drawGameover(g, wind);
		

		if (playStory) {

			NStgPlayer.CONTROLLABLE = false;
			NStgPlayer.STOPSHOOT = true;

			if (storyBackHeight < 100) {
				g.fillRect(0, 0, SYS.WINDOW_SIZE_X, storyBackHeight);
				g.fillRect(0, SYS.WINDOW_SIZE_Y - storyBackHeight, SYS.WINDOW_SIZE_X, storyBackHeight);
				storyBackHeight += 1;
			} else {
				g.fillRect(0, 0, SYS.WINDOW_SIZE_X, storyBackHeight);
				g.fillRect(0, SYS.WINDOW_SIZE_Y - storyBackHeight, SYS.WINDOW_SIZE_X, storyBackHeight);
				screenRotation = true;
			}

		} else {

			if (storyBackHeight > 0) {
				g.fillRect(0, 0, SYS.WINDOW_SIZE_X, storyBackHeight);
				g.fillRect(0, SYS.WINDOW_SIZE_Y - storyBackHeight, SYS.WINDOW_SIZE_X, storyBackHeight);
				storyBackHeight -= 2;
				screenRotation = false;
			}

			if (gRotate == 0 && !SYS.GAMEOVERING) {

				g.drawImage(bar_hp.file, //
						dX, dY, dX + 144, dY + 8, //
						0, 0, 144, 0 + 8, //
						wind); //

				g.drawImage(bar_hp.file, //
						dX, dY, dX + 144 * fadeHP / NStgPlayer.MAXHP, dY + 8, //
						0, 16, 144 * fadeHP / NStgPlayer.MAXHP, 16 + 8, //
						wind);

				g.drawImage(bar_hp.file, //
						dX, dY, dX + 144 * NStgPlayer.HP / NStgPlayer.MAXHP, dY + 8, //
						0, 8, 144 * NStgPlayer.HP / NStgPlayer.MAXHP, 8 + 8, //
						wind); //
			}

		}
		//DB
		
//		g.setColor(Color.WHITE);// 色指定
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//                RenderingHints.VALUE_ANTIALIAS_ON);
//		g.fillArc(100 - 64, 460 - 64, 128, 128, 0, 180);
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
//                RenderingHints.VALUE_ANTIALIAS_OFF);

		g.setColor(Color.MAGENTA);

		g.drawString("A17張瀚夫 Ver" + GameMain.GAMEVERSON, SYS.WINDOW_SIZE_X - 170 - 15, SYS.WINDOW_SIZE_Y - 10);

		//DB END
	}
	
	public void update() {

		// story mode
		if (Input.M_RC && timerStoryAni == 0) {
			playStory = !playStory;
			timerStoryAni = 150;
		}

		// opacito of gameover image
		if (SYS.GAMEOVERING) {
			opacity = opacity >= 0.99f ? 1.0f : opacity + 0.004f;
		}

		// red hpbar update
		if (SYS.TIMERSTAGE % 5 == 0) {
			if (fadeHP < NStgPlayer.HP) {
				fadeHP++;
			} else if (fadeHP > NStgPlayer.HP) {
				fadeHP--;
			}
		}

		//hp bar position
		dX = (int) NStgPlayer.dX - 24;
		dY = (int) NStgPlayer.dY + 80;

		//story switch timer--
		timerStoryAni = timerStoryAni < 0 ? 0 : timerStoryAni - 1;

	}

	public void effect(Graphics2D g, JFrame wind) {

		if (SYS.GAMEOVERING) {
			g.translate(Math.random() * 10 - 5, Math.random() * 10 - 5);
		}

		if (screenRotation) {

			if (gRotate < 90) {
				g.rotate(Math.toRadians(gRotate), SYS.WINDOW_SIZE_X / 2, SYS.WINDOW_SIZE_Y / 2);
				gRotate += (90 - gRotate) / 15;
				if (gRotate > 89.9) {
					gRotate = 90;
				}
			} else {
				g.rotate(Math.toRadians(90), SYS.WINDOW_SIZE_X / 2, SYS.WINDOW_SIZE_Y / 2);
			}

		} else {

			if (gRotate > 0) {
				g.rotate(Math.toRadians(gRotate), SYS.WINDOW_SIZE_X / 2, SYS.WINDOW_SIZE_Y / 2);
				gRotate += (0 - gRotate) / 15;
				if (gRotate < 0.1) {
					gRotate = 0;
					NStgPlayer.CONTROLLABLE = true;
					NStgPlayer.STOPSHOOT = false;
				}
			}

		}

	}

	public void drawGameover(Graphics2D g, JFrame wind) {
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));
		g.drawImage(gameover, 0, 0, SYS.WINDOW_SIZE_X, SYS.WINDOW_SIZE_Y, 0, 0, 800, 600, wind);
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));
	}
	
	public void drawRandomLine(Graphics2D g){
		//find a random angle :
		double randomAngle = Math.random()*Math.PI*2;
		//find the diameter of the circle around your ship that contains all the screen
		double maxX = Math.max( dX, SYS.WINDOW_SIZE_X - dX );
		double maxY = Math.max( dY, SYS.WINDOW_SIZE_Y - dY );
		int diam = (int) Math.sqrt( maxX * maxX + maxY * maxY );

		//Then take the point of this circle at randomAngle : 
		int x2 = (int)(dX + (int) diam*Math.cos( randomAngle ));
		int y2 = (int)(dY + (int) diam*Math.sin( randomAngle ));
		g.setColor(new Color(0,255,255));
		g.drawLine( (int)(dX - 15), (int)(dY - 5), x2, y2 );
	}



}
