import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int x = 50,y = 50;
	
	public void launchFrame(){
		setBounds(200,200,600,800);
		setLayout(null);
		//setBackground(Color.blue);
		this.setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, 40, 40);
		g.setColor(c);
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
					Thread.sleep(50);
					x +=flag;
					y +=flag;
				} catch (Exception e) {
					// TODO: handle exceptio
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
