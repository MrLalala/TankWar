import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	//位置和大小标记
	private int x,y;
	private int blood_R = 20;
	TankClient tc;
	//状态统计
	private int step = 0;
	//延时计数
	int flag = 0;
	//新血块出现延时
	private  static final int delay = 100;
	private int count = 100;
	//存在标记
	private boolean live = true;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	//位置数组
	private int [][] location={
			{233,166},{450,666},{100,669},{20,589},{344,422},{499,283},{333,624}
	};
	
	public Blood(){
		x = location[0][0];
		y = location[0][1];
	}
	
	//绘图函数
	public void draw(Graphics g){
		if(!live){
			if(count-- == 0){
				this.setLive(true);
				step = 0;
				count = delay;
			}
				
			return;	
		}
		Color color = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillOval(x, y, blood_R, blood_R);
		g.setColor(color);
		if(flag == 0)
			move();
		if(flag ++ == 100)
			flag = 0;
	}
	//移动函数
	public void move(){
		step ++;
		if(step == location.length)
			step = 0;
		x = location[step][0];
		y = location[step][1];
	}
	//范围返回方法
	public Rectangle bloodRect(){
		return new Rectangle(x, y, blood_R, blood_R);
	}
	
}
