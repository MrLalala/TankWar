import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	//�ӵ�����ʼλ��
	int x,y;
	//�ӵ��ķ���
	Tank.Direction dir;
	//�ӵ����ٶ� 
	private static final int x_speed = 20,y_speed = 20;
	private TankClient tc;
	//�������
	/*private boolean live = true;
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean isLive) {
		this.live = isLive;
	}*/
	//�ӵ��Ĵ�С
	static final int bullet_r = 10; 	
	//���캯�����ӵ�����ʼλ��
	public Bullet(int x, int y,Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Bullet(int x, int y,Tank.Direction dir,TankClient tc) {
		this(x, y, dir);
		this.tc = tc;
	}
	//�ӵ����ػ��¼�
	public void paint(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, bullet_r, bullet_r);
		g.setColor(color);
		move();
	}
	//�ӵ����ƶ��¼�
	void move(){
		switch (dir) {
		case L:
			x -= x_speed;
			break;
		case R:
			x += x_speed;
			break;
		case U:
			y -= y_speed;
			break;
		case D:
			y += y_speed;
			break;
		case LU:
			x -= x_speed;
			y -= y_speed;
			break;
		case LD:
			x -= x_speed;
			y += y_speed;
			break;
		case RU:
			x += x_speed;
			y -= y_speed;
			break;
		case RD:
			x += x_speed;
			y += y_speed;
			break;
		default:
			break;
		}
		
		//�����������
		if(x <= 0 || y <= 0 || x >= TankClient.Game_w || y >= TankClient.Game_h){
			tc.bullets.remove(this);
		}
			
	}
}
