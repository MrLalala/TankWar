import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {

	

	// ̹����ʼλ��
	private int x, y;
	// �����ж�
	private boolean kL = false, kR = false, kU = false, kD = false;
	
	//����TankClient��һ������
	private TankClient tc = null;
	// ����ö��
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, stop
	};

	private Direction pt_Direct = Direction.D;

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
	//��TankClient�Ĺ��캯��
	public Tank(int x,int y ,TankClient tc) {
		this(x, y);
		this.tc = tc;
	}
	// �ػ��¼�
	public void draw(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
		draw_pt(g);
		move();
	}

	// ������Ͳ
	void draw_pt(Graphics g) {
		switch (pt_Direct) {
		case L:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x, y+Tank_r/2);
			break;
		case R:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x + Tank_r, y+Tank_r/2);
			break;
		case U:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x + Tank_r/2, y);
			break;
		case D:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x + Tank_r/2, y+Tank_r);
			break;
		case LU:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x, y);
			break;
		case LD:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x, y+Tank_r);
			break;
		case RU:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x + Tank_r, y);
			break;
		case RD:
			g.drawLine(x+Tank_r/2, y+Tank_r/2, x + Tank_r, y+Tank_r);
			break;
		default:
			break;
		}
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
		// Ϊ�ո�ʱ�����ӵ�
		}
		locateDirection();
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
		case KeyEvent.VK_SPACE:
			tc.bullets.add(fire());
			break;
		}
		locateDirection();
	}

	// �����¼�
	public Bullet fire() {
		Direction temp = null;
		if (dir == Direction.stop) {
			temp = pt_Direct;
		} else
			temp = dir;
		Bullet B = new Bullet(x + (Tank_r - Bullet.bullet_r) / 2, y + (Tank_r - Bullet.bullet_r) / 2, temp);
		return B;

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
			pt_Direct = dir;
	}

}
