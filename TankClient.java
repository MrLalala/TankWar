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
	// ��ʼ��һ��̹��
	Tank myTank = new Tank(335,745, true, this);
	// �з�̹������
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	ArrayList<Tank> allTanks = new ArrayList<Tank>();
	// ��Ϸ�����С
	public static final int Game_w = 700, Game_h = 800;
	// �½���ը�б�
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	// �½��ҷ��ӵ��б�
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	// �½��з��ӵ��б�
	// ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	// �½�ǽ
	Wall wall = new Wall(100, 300, 20, 275);
	Wall wall2 = new Wall(500, 200, 40, 300);
	// ʵ��˫���壺
	// �½�һ�����ⱳ��ͼƬ����
	Image offScreen = null;
	
	Blood blood = new Blood();

	// ��ʼ������
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

	// ��д�Ļ�ͼ�¼�
	public void paint(Graphics g) {
		// ʹ��̹���Լ��Ļ�ͼ�¼�
		g.drawString("Bullets Count:" + bullets.size(), 10, 70);
		g.drawString("AllTanks Count:" + allTanks.size(), 10, 50);
		g.drawString("Explodes Count:" + explodes.size(), 10, 90);
		g.drawString("Tanks Count:" + tanks.size(), 10, 110);
		if(!myTank.isLive()){
			Font font = new Font("myFont", WIDTH,20);
			g.setFont(font);
			g.drawString("��F2����", 340, 390);
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
			// �ж��ӵ��������
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
		// ����̹������ϵͳ
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

	// ��д������� update ����
	public void update(Graphics g) {
		// �½�ͼƬ����ͼƬ��������һ��
		if (offScreen == null) {
			offScreen = this.createImage(Game_w, Game_h);
		}
		// �½�����
		Graphics goff = offScreen.getGraphics();
		// ��ȡ��ɫ�����ֳ����õĸ��ֲ���Ӧ��������һ��
		Color color = goff.getColor();
		goff.setColor(Color.blue);
		goff.fillRect(0, 0, Game_w, Game_h);
		goff.setColor(color);
		// ���û�ͼ�¼�������Ҫͼ�����Ƶ���ͼƬ�ϡ�
		paint(goff);
		// ˢ��������
		g.drawImage(offScreen, 0, 0, null);
	}

	// ��ͼ�����߳�
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

	// �Զ�����̼����ࣺ
	private class MyKeyEvent extends KeyAdapter {

		// ����İ����ͷŲ���
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
			if( !myTank.isLive() && e.getKeyCode() == KeyEvent.VK_F2){
				 myTank.setLive(true);
				back();
			}
		}

		// ����İ�����ѹ����
		public void keyPressed(KeyEvent e) {
			// ����Tank�Լ��İ�������
			myTank.keyPressed(e);
		}
	}
	//��������
	private void back(){
		myTank = new Tank(335,745, true, this);
		allTanks.add(myTank);
	}
	// ��������
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TankClient tc = new TankClient();
		tc.launchFrame();

	}

}
