package jp.tnw.a18;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;

import javax.swing.JFrame;

public class Input implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

	static boolean K_SHIFT, K_ESC, K_Z, K_X, LEFT, UP, RIGHT, DOWN; // 保存键盘按键状态
	static boolean M_LC, M_MC, M_RC; // 保存鼠标左中右键状态
	static int DIR4, DIR8; // 保存键盘四八方向键状态
	static int M_X, M_Y, M_W; // 保存鼠标指针坐标及滚轮状态
	//左右移动手感特化处理
	boolean left_first = false;
	boolean right_first = false;
	//测试用
	static int TEST[];
	static boolean TEST2[];
	static int KEY, KEYR, MB;
	boolean check[] = { false, false, false, false };
	int index[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	Insets sz;
	
	Input() {
		
		// 为窗口添加监听
		
	}
	
	public void update(JFrame wind){
		
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

		TEST = Arrays.copyOf(index, 8);
		TEST2 = Arrays.copyOf(check, 4);

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

		KEY = arg0.getKeyCode();
		switch (KEY) {
		case 16:
			K_SHIFT = true;
			break;
		case 27:
			K_ESC = true;
			break;
		case 37:
			LEFT = true;
			break;
		case 38:
			UP = true;
			break;
		case 39:
			RIGHT = true;
			break;
		case 40:
			DOWN = true;
			break;
		case 88:
			K_X = true;
			break;
		case 90:
			K_Z = true;
			break;
		}

		dirCheck();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

		KEYR = arg0.getKeyCode();
		switch (KEYR) {
		case 16:
			K_SHIFT = false;
			break;
		case 27:
			K_ESC = false;
			break;
		case 37:
			LEFT = false;
			break;
		case 38:
			UP = false;
			break;
		case 39:
			RIGHT = false;
			break;
		case 40:
			DOWN = false;
			break;
		case 88:
			K_X = false;
			break;
		case 90:
			K_Z = false;
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

		MB = arg0.getButton();
		switch (MB){
		case 1:
			M_LC = true;
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

		MB = arg0.getButton();
		switch (MB){
		case 1:
			GameMain.in = true;
			GameMain.in1 = true;
			GameMain.in2 = true;
			GameMain.in3 = true;
			GameMain.in4 = true;
			GameMain.in5 = true;
			M_LC = false;
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
