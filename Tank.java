import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {

	// �½�һ���ӵ�����
	Bullet bullet = null;
	// ̹����ʼλ��
	private int x, y;
	// �����ж�
	private boolean kL = false, kR = false, kU = false, kD = false;

	// ����ö��
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, stop
	};
	
	private Direction t;
	
	// �������
	private Direction dir = Direction.stop;
	// ̹��ֱ��
	public static final int Tank_r = 40;
	// �ƶ��ٶ�
	public static final int x_speed = 10, y_speed = 10;

	// ���캯��
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// �ػ��¼�
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
		if (bullet != null)
			bullet.paint(g);
		move();
	}

	// �ƶ��¼�
	void move() {
		switch (dir) {
		case L:
			x -= x_speed;
			break;
		case R:
			x += x_speed;
			break;
		case U:
			y -= y_speed;
			break;
		case D:
			y += y_speed;
			break;
		case LU:
			x -= x_speed;
			y -= y_speed;
			break;
		case LD:
			x -= x_speed;
			y += y_speed;
			break;
		case RU:
			x += x_speed;
			y -= y_speed;
			break;
		case RD:
			x += x_speed;
			y += y_speed;
			break;
		case stop:
			break;
		}
	}

	// Tank�İ�����ѹ����:�޸ķ���
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			kR = true;
			break;
		case KeyEvent.VK_LEFT:
			kL = true;
			break;
		case KeyEvent.VK_DOWN:
			kD = true;
			break;
		case KeyEvent.VK_UP:
			kU = true;
			break;
		//Ϊ�ո�ʱ�����ӵ�
		case KeyEvent.VK_SPACE:
			this.bullet = fire();
		}
		locateDirection();
	}

	// �����¼�
	public Bullet fire() {
		Direction temp = null;
		if(dir == Direction.stop){
			if( t == null)
				temp = Direction.R;
			else
				temp = t;
		}else
			temp = dir;
		Bullet B = new Bullet(x+(Tank_r-Bullet.bullet_r)/2, y+(Tank_r-Bullet.bullet_r)/2, temp);
		return B;

	}

	// ̹�˵İ����ͷŲ�������������
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			kR = false;
			break;
		case KeyEvent.VK_LEFT:
			kL = false;
			break;
		case KeyEvent.VK_DOWN:
			kD = false;
			break;
		case KeyEvent.VK_UP:
			kU = false;
			break;
		}
		locateDirection();
	}

	// �жϷ���
	void locateDirection() {
		if (kL && !kR && !kU && !kD)
			dir = Direction.L;
		else if (!kL && kR && !kU && !kD)
			dir = Direction.R;
		else if (!kL && !kR && kU && !kD)
			dir = Direction.U;
		else if (!kL && !kR && !kU && kD)
			dir = Direction.D;
		else if (kL && !kR && kU && !kD)
			dir = Direction.LU;
		else if (kL && !kR && !kU && kD)
			dir = Direction.LD;
		else if (!kL && kR && kU && !kD)
			dir = Direction.RU;
		else if (!kL && kR && !kU && kD)
			dir = Direction.RD;
		else
			dir = Direction.stop;
		if (dir != Direction.stop)
			t = dir;
	}

}
