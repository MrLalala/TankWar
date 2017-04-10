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
	// ��ʼ��һ��̹��
	Tank myTank = new Tank(50, 50, true, this);
	// �з�̹������
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	// ��Ϸ�����С
	public static final int Game_w = 700, Game_h = 800;

	// �½���ը�б�
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	// �½��ӵ��б�
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	// ʵ��˫���壺
	// �½�һ�����ⱳ��ͼƬ����
	Image offScreen = null;

	// ��ʼ������
	public void launchFrame() {
		for (int i = 1;i<=10;i++){
			tanks.add(new Tank(50+i*50,50,false,Tank.Direction.D,this));
		}
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
		g.drawString("Bullets Count:" + bullets.size(), 10, 50);
		g.drawString("Explodes Count:" + explodes.size(), 10, 70);
		g.drawString("Tanks Count:" + tanks.size(), 10, 90);

		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			bullet.hitTanks(tanks);
			// �ж��ӵ��������
			/*
			 * if (!bullet.isLive()) bullets.remove(i); else
			 */
			bullet.paint(g);
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			tank.draw(g);
		}
		myTank.draw(g);
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
		}

		// ����İ�����ѹ����
		public void keyPressed(KeyEvent e) {
			// ����Tank�Լ��İ�������
			myTank.keyPressed(e);
		}
	}

	// ��������
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TankClient tc = new TankClient();
		tc.launchFrame();

	}

}
