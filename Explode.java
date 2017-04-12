import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
	//位置
	int x,y;
	//生死标记
	private boolean live = true;
	//判断是否为初始化
	private boolean init = false;
	//同过 ToolKit勾取硬盘文件
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//新建图片数组
	private static Image [] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	//执行步骤
	int step = 0;
	//爆炸颜色
	//private Color bloom = Color.orange;
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
		if(!init){
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		if(!live){
			tc.explodes.remove(this);
			return;
		}
		if(step == imgs.length){
			live =false;
			step = 0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
