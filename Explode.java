import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	//位置
	int x,y;
	//生死
	private boolean live = true;
	//爆炸半径
	int[] dia = {4,7,18,26,35,49,35,26,18,7,4};
	//执行步骤
	int step = 0;
	//爆炸颜色
	private Color bloom = Color.orange;
	//拿到主窗体引用
	private TankClient tc;
	//构造方法
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	//重写绘图事件
	public void draw(Graphics g){
		if(!live) return;
		if(step == dia.length){
			live =false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(bloom);
		g.fillOval(x, y, dia[step], dia[step]);
		g.setColor(c);
		step++;
	}
}
