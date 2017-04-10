import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//坦克的位置（初始值为默认位置）
	Tank myTank = new Tank(50, 50);
	//坦克直径
	public static final int Tank_r = 50;
	//游戏框体大小
	public static final int Game_w = 700,Game_h = 800;
	//定义移动速度
	public static final int speed = 10;
	//实现双缓冲：
	//新建一个图片对象
	Image offScreen = null;

	//初始化方法
	public void launchFrame() {
		setBounds(200, 200, Game_w, Game_h);
		setLayout(null);
		setBackground(Color.blue);
		this.setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new MyKeyEvent());
		setVisible(true);
		new Thread(new PaintThread()).start();
	}

	//重写的绘图事件
	public void paint(Graphics g) {
		//使用坦克自己的绘图事件
		myTank.draw(g);
	}
	
	//重写主窗体的 update 方法
	public void update(Graphics g) {
		//新建图片，该图片和主窗体一致
		if(offScreen == null){
			offScreen = this.createImage(Game_w,Game_h);
		}
		//新建画笔
		Graphics goff = offScreen.getGraphics();
		//获取颜色保护现场，该的各种参数应和主窗体一致
		Color color = goff.getColor();
		goff.setColor(Color.blue);
		goff.fillRect(0, 0,Game_w, Game_h); 
		goff.setColor(color);
		//调用绘图事件，将想要图案绘制到该图片上。
		paint(goff);
		//刷新主窗体
		g.drawImage(offScreen, 0, 0, null);
	}
	
	//绘图重置线程
	private class PaintThread implements Runnable {
		
		public void run() {
			while(true){
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}
		}
	}
	
	//自定义键盘监听类：
	private class MyKeyEvent extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			//System.out.println("OK");
			myTank.keyPressed(e);
		}
	}
	
	//主函数：
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}

}
