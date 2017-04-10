import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	int x,y;
	public static final int Tank_r = 30;
	public static final int speed = 10;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval(x, y, Tank_r, Tank_r);
		g.setColor(c);
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
				x +=speed;
			break;
		case KeyEvent.VK_LEFT:
				x -=speed;
			break;
		case KeyEvent.VK_DOWN:
				y +=speed;
			break;
		case KeyEvent.VK_UP:
				y -=speed;
			break;
		default:
			break;
		}
	}
}
