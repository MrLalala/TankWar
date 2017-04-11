import java.awt.Color;
import java.awt.Font;
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
	// 初始化一个坦克
	Tank myTank = new Tank(335,745, true, this);
	// 敌方坦克容器
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	ArrayList<Tank> allTanks = new ArrayList<Tank>();
	// 游戏框体大小
	public static final int Game_w = 700, Game_h = 800;
	// 新建爆炸列表
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	// 新建我方子弹列表
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	// 新建敌方子弹列表
	// ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	// 新建墙
	Wall wall = new Wall(100, 300, 20, 275);
	Wall wall2 = new Wall(500, 200, 40, 300);
	// 实现双缓冲：
	// 新建一个虚拟背景图片对象
	Image offScreen = null;
	
	Blood blood = new Blood();

	// 初始化方法
	public void launchFrame() {
		for (int i = 1; i <= 10; i++) {
			tanks.add(new Tank(50 + i * 50, 50, false, Tank.Direction.D, this));
		}
		allTanks.addAll(tanks);
		allTanks.add(myTank);
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

	// 重写的绘图事件
	public void paint(Graphics g) {
		// 使用坦克自己的绘图事件
		g.drawString("Bullets Count:" + bullets.size(), 10, 70);
		g.drawString("AllTanks Count:" + allTanks.size(), 10, 50);
		g.drawString("Explodes Count:" + explodes.size(), 10, 90);
		g.drawString("Tanks Count:" + tanks.size(), 10, 110);
		if(!myTank.isLive()){
			Font font = new Font("myFont", WIDTH,20);
			g.setFont(font);
			g.drawString("按F2复活", 340, 390);
			allTanks.remove(myTank);
		}
		wall.draw(g);
		wall2.draw(g);
		blood.draw(g);
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			// wall.hitWall(bullet);
			bullet.hitWall(wall);
			bullet.hitWall(wall2);
			bullet.hitTanks(tanks);
			bullet.hitTank(myTank);
			// 判断子弹死亡标记
			/*
			 * if (!bullet.isLive()) bullets.remove(i); else
			 */
			bullet.paint(g);
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			tank.tankImpact(allTanks);
			tank.draw(g);
		}
		// 加入坦克重生系统
		if (tanks.size() == 0) {
			for (int i = 0; i < 3; i++) {
				Tank temp = new Tank(50 + i * 200, 50, false, Tank.Direction.D, this);
				tanks.add(temp);
				allTanks.add(temp);
			}
		}
		// for (int i =0; i < enemyBullets.size(); i++){
		// Bullet bullet = enemyBullets.get(i);
		// bullet.hitTank(myTank);
		// bullet.paint(g);
		// }
		myTank.draw(g);
		myTank.eatBlood(blood);
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
	}

	// 重写主窗体的 update 方法
	public void update(Graphics g) {
		// 新建图片，该图片和主窗体一致
		if (offScreen == null) {
			offScreen = this.createImage(Game_w, Game_h);
		}
		// 新建画笔
		Graphics goff = offScreen.getGraphics();
		// 获取颜色保护现场，该的各种参数应和主窗体一致
		Color color = goff.getColor();
		goff.setColor(Color.blue);
		goff.fillRect(0, 0, Game_w, Game_h);
		goff.setColor(color);
		// 调用绘图事件，将想要图案绘制到该图片上。
		paint(goff);
		// 刷新主窗体
		g.drawImage(offScreen, 0, 0, null);
	}

	// 绘图重置线程
	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}
		}
	}

	// 自定义键盘监听类：
	private class MyKeyEvent extends KeyAdapter {

		// 窗体的按键释放操作
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
			if( !myTank.isLive() && e.getKeyCode() == KeyEvent.VK_F2){
				 myTank.setLive(true);
				back();
			}
		}

		// 窗体的按键按压操作
		public void keyPressed(KeyEvent e) {
			// 这是Tank自己的按键操作
			myTank.keyPressed(e);
		}
	}
	//重生方法
	private void back(){
		myTank = new Tank(335,745, true, this);
		allTanks.add(myTank);
	}
	// 主函数：
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TankClient tc = new TankClient();
		tc.launchFrame();

	}

}
