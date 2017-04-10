import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	//子弹的起始位置
	int x,y;
	// 子弹的方向
	Tank.Direction dir;
	//子弹的速度 
	private static final int x_speed = 20,y_speed = 20;
	//子弹的大小
	static final int bullet_r = 10; 	
	//构造函数：子弹的起始位置
	public Bullet(int x, int y,Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	//子弹的重绘事件
	public void paint(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, bullet_r, bullet_r);
		g.setColor(color);
		move();
	}
	//子弹的移动事件
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
		default:
			break;
		}
	}
}
