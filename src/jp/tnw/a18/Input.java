package jp.tnw.a18;

import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

//◆入力管理のクラス◆//
public class Input implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	static boolean K_SHIFT, K_ESC, K_Z, K_X, LEFT, UP, RIGHT, DOWN, UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT; // 各キーの状態
	static boolean M_LC, M_MC, M_RC; // マウスボタン
	static int DIR4, DIR8; // 十字と8方向入力
	static int M_X, M_Y, M_W; // カーソル座標とマウスホイール
	Insets sz; // ウィンドウの枠
	boolean left_first = false;
	boolean right_first = false;
	
	// for message system
	static boolean M_LCR;
	static boolean K_ZR;

	//

	// FOR TEST
	// static int TEST[];
	// static boolean TEST2[];
	// boolean check[] = { false, false, false, false };
	// int index[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	// int KEY, KEYR, MB;

	Input() {

	}

	public void update(JFrame wind) {

		sz = wind.getInsets();
		M_X = MouseInfo.getPointerInfo().getLocation().x - wind.getLocationOnScreen().x - sz.left;
		M_Y = MouseInfo.getPointerInfo().getLocation().y - wind.getLocationOnScreen().y - sz.top;

	}

	public void dirCheck() {

		if (LEFT && UP && RIGHT && DOWN) {
			DIR8 = 0;
			DIR4 = 0;
		} else if (LEFT && UP && RIGHT) {
			DIR8 = 3;
			DIR4 = 3;
		} else if (UP && RIGHT && DOWN) {
			DIR8 = 5;
			DIR4 = 5;
		} else if (RIGHT && DOWN && LEFT) {
			DIR8 = 7;
			DIR4 = 7;
		} else if (DOWN && LEFT && UP) {
			DIR8 = 1;
			DIR4 = 1;
		} else if (LEFT && UP) {
			DIR8 = 2;
			DIR4 = 1;
		} else if (UP && RIGHT) {
			DIR8 = 4;
			DIR4 = 3;
		} else if (RIGHT && DOWN) {
			DIR8 = 6;
			DIR4 = 3;
		} else if (DOWN && LEFT) {
			DIR8 = 8;
			DIR4 = 1;
		} else if (UP && DOWN) {
			DIR8 = 0;
			DIR4 = 0;
		} else if (LEFT) {
			DIR8 = 1;
			DIR4 = 1;
			if (!RIGHT) {
				right_first = false;
			}
			if (left_first != true && right_first != true) {
				left_first = true;
			}
			if (RIGHT && left_first) {
				DIR8 = 5;
				DIR4 = 3;
			}
		} else if (UP) {
			DIR8 = 3;
			DIR4 = 2;
		} else if (RIGHT) {
			DIR8 = 5;
			DIR4 = 3;
			if (!LEFT) {
				left_first = false;
			}
			if (right_first != true && left_first != true) {
				right_first = true;
			}
			if (LEFT && right_first) {
				DIR8 = 1;
				DIR4 = 1;
			}
		} else if (DOWN) {
			DIR8 = 7;
			DIR4 = 4;
		} else {
			left_first = false;
			right_first = false;
			DIR8 = 0;
			DIR4 = 0;
		}

		// TEST = Arrays.copyOf(index, 8);
		// TEST2 = Arrays.copyOf(check, 4);

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

//		KEY = arg0.getKeyCode();
		switch (arg0.getKeyCode()) {
		case 16://
			K_SHIFT = true;
			break;
		case 27:
			K_ESC = true;
			break;
		case KeyEvent.VK_LEFT:
			LEFT = true;
			break;
		case KeyEvent.VK_UP:
			UP = true;
			break;
		case KeyEvent.VK_RIGHT:
			RIGHT = true;
			break;
		case KeyEvent.VK_DOWN:
			DOWN = true;
			break;
		case KeyEvent.VK_NUMPAD4:
			LEFT = true;
			break;
		case KeyEvent.VK_NUMPAD8:
			UP = true;
			break;
		case KeyEvent.VK_NUMPAD6:
			RIGHT = true;
			break;
		case KeyEvent.VK_NUMPAD2:
			DOWN = true;
			break;
		case 88:
			K_X = true;
			break;
		case 90:
			K_Z = true;
			K_ZR = false;
			break;
		}

		dirCheck();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

//		KEYR = arg0.getKeyCode();
		switch (arg0.getKeyCode()) {
		case 16:
			K_SHIFT = false;
			break;
		case 27:
			K_ESC = false;
			break;
		case KeyEvent.VK_LEFT:
			LEFT = false;
			break;
		case KeyEvent.VK_UP:
			UP = false;
			break;
		case KeyEvent.VK_RIGHT:
			RIGHT = false;
			break;
		case KeyEvent.VK_DOWN:
			DOWN = false;
			break;
		case KeyEvent.VK_NUMPAD4:
			LEFT = false;
			break;
		case KeyEvent.VK_NUMPAD8:
			UP = false;
			break;
		case KeyEvent.VK_NUMPAD6:
			RIGHT = false;
			break;
		case KeyEvent.VK_NUMPAD2:
			DOWN = false;
			break;
		case 88:
			K_X = false;
			break;
		case 90:
			K_Z = false;
			K_ZR = true;
			break;
		}

		dirCheck();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		M_X = arg0.getX() - sz.left;
		M_Y = arg0.getY() - sz.top;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

//		MB = arg0.getButton();
		switch (arg0.getButton()) {
		case 1:
			M_LC = true;
			M_LCR = false;
			break;
		case 2:
			M_MC = true;
			break;
		case 3:
			M_RC = true;
			break;
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

//		MB = arg0.getButton();
		switch (arg0.getButton()) {
		case 1:
			M_LC = false;
			M_LCR = true;
			break;
		case 2:
			M_MC = false;
			break;
		case 3:
			M_RC = false;
			break;
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub

		M_W -= arg0.getWheelRotation();

	}

}
