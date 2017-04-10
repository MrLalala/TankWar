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
	//��ʼ��һ��̹��
	Tank myTank = new Tank(50, 50,this);
	//��Ϸ�����С
	public static final int Game_w = 700,Game_h = 800;
	
	// �½�һ���ӵ�����
	Bullet bullet = null;
	// �½��ӵ��б�
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	//ʵ��˫���壺
	//�½�һ�����ⱳ��ͼƬ����
	
	
	Image offScreen = null;

	//��ʼ������
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

	//��д�Ļ�ͼ�¼�
	public void paint(Graphics g) {
		//ʹ��̹���Լ��Ļ�ͼ�¼�
		g.drawString("Bullets Count:"+bullets.size(), 10, 50);
		for(int i = 0; i< bullets.size();i++){
			bullet = bullets.get(i);
			//�ж��ӵ��������
			/*if (!bullet.isLive())
				bullets.remove(i);
			else*/
			bullet.paint(g);
		}
		myTank.draw(g);
	}
	
	//��д������� update ����
	public void update(Graphics g) {
		//�½�ͼƬ����ͼƬ��������һ��
		if(offScreen == null){
			offScreen = this.createImage(Game_w,Game_h);
		}
		//�½�����
		Graphics goff = offScreen.getGraphics();
		//��ȡ��ɫ�����ֳ����õĸ��ֲ���Ӧ��������һ��
		Color color = goff.getColor();
		goff.setColor(Color.blue);
		goff.fillRect(0, 0,Game_w, Game_h); 
		goff.setColor(color);
		//���û�ͼ�¼�������Ҫͼ�����Ƶ���ͼƬ�ϡ�
		paint(goff);
		//ˢ��������
		g.drawImage(offScreen, 0, 0, null);
	}
	
	//��ͼ�����߳�
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
	
	//�Զ�����̼����ࣺ
	private class MyKeyEvent extends KeyAdapter{
		
		//����İ����ͷŲ���
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		//����İ�����ѹ����
		public void keyPressed(KeyEvent e) {
			//����Tank�Լ��İ�������
			myTank.keyPressed(e);
		}
	}
	
	//��������
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}

}
