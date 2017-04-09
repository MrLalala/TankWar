import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int x = 50,y = 50;

	//实现双缓冲：
	//新建一个图片对象
	Image offScreen = null;

	public void launchFrame() {
		setBounds(200, 200, 600, 800);
		setLayout(null);
		setBackground(Color.blue);
		this.setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new Thread(new PaintThread()).start();
	}



	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, 40, 40);
		g.setColor(c);
	}
	//重写主窗体的 update 方法
	public void update(Graphics g) {
		//新建图片，该图片和主窗体一致
		if(offScreen == null){
			offScreen = this.createImage(800,600);
		}
		//新建画笔
		Graphics goff = offScreen.getGraphics();
		//获取颜色保护现场，该的各种参数应和主窗体一致
		Color color = goff.getColor();
		goff.setColor(Color.blue);
		goff.fillRect(0, 0, 800, 600); 
		goff.setColor(color);
		//调用绘图事件，将想要图案绘制到该图片上。
		paint(goff);
		//刷新主窗体
		g.drawImage(offScreen, 0, 0, null);
	}
	
	private class PaintThread implements Runnable {
		
		public void run() {
			int flag = 5;
			while(true){
				if(x >745 || y >545)
					flag = -5;
				if(x <20 || y < 10)
					flag = 5 ;
				try {
					//Thread.sleep(5);
					x +=flag;
					y +=flag;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				repaint();
			}
		}
	}
	

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}

}
