import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {

	// 新建一个子弹对象
	Bullet bullet = null;
	// 坦克起始位置
	private int x, y;
	// 方向判断
	private boolean kL = false, kR = false, kU = false, kD = false;

	// 方向枚举
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, stop
	};
	
	private Direction t;
	
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

	// 重绘事件
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
		if (bullet != null)
			bullet.paint(g);
		move();
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
		//为空格时发射子弹
		case KeyEvent.VK_SPACE:
			this.bullet = fire();
		}
		locateDirection();
	}

	// 开火事件
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
		}
		locateDirection();
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
			t = dir;
	}

}
