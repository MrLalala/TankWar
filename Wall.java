import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	
	//内部成员变量
	int x,y,w,h;
	//持有主窗体引用
	TankClient tc;
	//构造函数
	public Wall(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public Wall(int x, int y, int w, int h,TankClient tc) {
		// TODO 自动生成的构造函数存根
		this(x, y, w, h);
		this.tc = tc;
	}
	//绘制函数
	public void draw(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRect(x, y, w, h);
		g.setColor(color);
	}
	//范围处理函数
	public Rectangle wallRectangle(){
		return new Rectangle(x,y,w,h);
	}

	
}
