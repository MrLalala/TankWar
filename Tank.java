import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {

	// 坦克起始位置
	private int x, y;
	// 方向判断
	private boolean kL = false, kR = false, kU = false, kD = false;

	// 方向枚举
	private enum Direction {
		L, R, U, D, LU, LD, RU, RD,stop
	};
	
	//方向变量
	private Direction dir = Direction.stop;
	// 坦克直径
	public static final int Tank_r = 30;
	// 移动速度
	public static final int x_speed = 10,y_speed = 10;
	
	//构造函数
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//重绘事件
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
		move();
		this.kL = false;
		this.kR = false;
		this.kU = false;
		this.kD = false;
	}
	
	//移动事件
	void move(){
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
	
	//Tank的按键操作:修改方向
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
		}
		locateDirection();
	}
	
	//判断方向
	void locateDirection(){
		if(kL && !kR && !kU && ! kD)
			dir = Direction.L;
		else if(!kL && kR && !kU && ! kD)
			dir = Direction.R;
		else if(!kL && !kR && kU && ! kD)
			dir = Direction.U;
		else if(!kL && !kR && !kU &&  kD)
			dir = Direction.D;
		else if(kL && !kR && kU && ! kD)
			dir = Direction.LU;
		else if(kL && !kR && !kU &&  kD)
			dir = Direction.LD;
		else if(!kL && kR && kU && ! kD)
			dir = Direction.RU;
		else if(!kL && kR && !kU &&  kD)
			dir = Direction.RD;
		else 
			dir = Direction.stop;
	}
	
	
}
