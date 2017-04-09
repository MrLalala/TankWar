import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static int x = 50, y = 50;

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
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				if (k.getKeyCode() == KeyEvent.VK_LEFT)
					if (x != 0)
						x -= 5;
				if (k.getKeyCode() == KeyEvent.VK_RIGHT)
					if (x != 560)
						x += 5;
				if (k.getKeyCode() == KeyEvent.VK_UP)
					if (y != 20)
						y -= 5;
				if (k.getKeyCode() == KeyEvent.VK_DOWN)
					if (y != 750)
						y += 5;
				repaint();
		}});
	}


	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, 40, 40);
		g.setColor(c);
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

}
