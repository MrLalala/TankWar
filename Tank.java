import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {

	

	// 坦克起始位置
	private int x, y;
	// 方向判断
	private boolean kL = false, kR = false, kU = false, kD = false;
	
	//引用TankClient的一个对象
	private TankClient tc = null;
	// 方向枚举
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, stop
	};

	private Direction pt_Direct = Direction.D;

	// 方向变量
	private Direction dir = Direction.stop;
	// 坦克直径
	public static final int Tank_r = 40;
	// 移动速度
	public static final int x_speed = 10, y_speed = 10;

	// 构造函数
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	//带TankClient的构造函数
	public Tank(int x,int y ,TankClient tc) {
		this(x, y);
		this.tc = tc;
	}
	// 重绘事件
	public void draw(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
		draw_pt(g);
		move();
	}

	// 绘制炮筒
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

	// 移动事件
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

	// Tank的按键按压操作:修改方向
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
		// 为空格时发射子弹
		}
		locateDirection();
	}

	// 坦克的按键释放操作：修正方向
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

	// 开火事件
	public Bullet fire() {
		Direction temp = null;
		if (dir == Direction.stop) {
			temp = pt_Direct;
		} else
			temp = dir;
		Bullet B = new Bullet(x + (Tank_r - Bullet.bullet_r) / 2, y + (Tank_r - Bullet.bullet_r) / 2, temp);
		return B;

	}

	// 判断方向
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
