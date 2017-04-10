import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class TankClient extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//初始化一个坦克
	Tank myTank = new Tank(50, 50, true, this);
	Tank enemyTank = new Tank(100, 100, false, this);
	//游戏框体大小
	public static final int Game_w = 700,Game_h = 800;
	
	//新建爆炸列表
	ArrayList<Explode>eList = new ArrayList<Explode>();
	// 新建子弹列表
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	//实现双缓冲：
	//新建一个虚拟背景图片对象
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
		g.drawString("Bullets Count:"+bullets.size(), 10, 50);
		g.drawString("Explodes Count:"+eList.size(), 10, 70);
		for(int i = 0; i< bullets.size();i++){
			Bullet bullet = bullets.get(i);
			if(bullet.hitTank(enemyTank))
				eList.add(new Explode(bullet.x, bullet.y,this));
			//判断子弹死亡标记
			/*if (!bullet.isLive())
				bullets.remove(i);
			else*/
			bullet.paint(g);
		}
		
		for(int i = 0;i< eList.size();i++){
			Explode e = eList.get(i);
			e.draw(g);
		}
		myTank.draw(g);
		enemyTank.draw(g);
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
					Thread.sleep(50);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}
		}
	}
	
	//自定义键盘监听类：
	private class MyKeyEvent extends KeyAdapter{
		
		//窗体的按键释放操作
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
 		}
		//窗体的按键按压操作
		public void keyPressed(KeyEvent e) {
			//这是Tank自己的按键操作
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
