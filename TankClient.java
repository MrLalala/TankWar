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
	//̹�˵�λ�ã���ʼֵΪĬ��λ�ã�
	static int x = 0,y = 0;
	//̹��ֱ��
	public static final int Tank_r = 50;
	//��Ϸ�����С
	public static final int Game_w = 700,Game_h = 800;
	//�����ƶ��ٶ�
	public static final int speed = 10;
	//ʵ��˫���壺
	//�½�һ��ͼƬ����
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
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
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
					Thread.sleep(5);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}
		}
	}
	
	//�Զ�����̼����ࣺ
	private class MyKeyEvent extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			//System.out.println("OK");
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_RIGHT:
				if (x <= Game_w-Tank_r)
					x +=speed;
				break;
			case KeyEvent.VK_LEFT:
				if (x >=0)
					x -=speed;
				break;
			case KeyEvent.VK_DOWN:
				if (y <= Game_h-Tank_r)
					y +=speed;
				break;
			case KeyEvent.VK_UP:
				if (y >=0)
					y -=speed;
				break;
	
			default:
				break;
			}

		}
		
	}
	
	//��������
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}

}
