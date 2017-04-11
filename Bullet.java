import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bullet {
	//�ӵ�����ʼλ��
	int x,y;
	//�ӵ��ķ���
	Tank.Direction dir;
	//�ӵ����ٶ� 
	private static final int x_speed = 20,y_speed = 20;
	//�õ���������
	private TankClient tc;
	//�����ӵ���ɫ
	private Color bColor = Color.black;
	//�������
	private boolean live = true;
	//�����ӵ����
	private boolean bGood;
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean isLive) {
		this.live = isLive;
	}
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
	public Bullet(int x, int y,Tank.Direction dir,TankClient tc,boolean bGood) {
		this(x, y, dir, tc);
		this.bGood = bGood;
	}
	//�ӵ����ػ��¼�
	public void paint(Graphics g){
		if(!live){
			//if(bGood)
			tc.bullets.remove(this);
//			else
//				tc.enemyBullets.remove(this);
			return;
		}
		Color color = g.getColor();
		g.setColor(bColor);
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
			this.live = false;
		}
			
	}
	
	//�ж�λ�ù�ϵ
	public Rectangle getRect(){
		return new Rectangle(x, y, bullet_r, bullet_r);
	}
//	//��ǽר��λ��
//	public Rectangle getRect2(){
//		return new Rectangle(x-bullet_r, y-bullet_r, 2*bullet_r, 2*bullet_r);
//	}
	
	//���з���
	public boolean hitTank(Tank t){
		if(this.isLive() && this.getRect().intersects(t.getRect()) && t.isLive() && t.isbGood() != bGood){
			this.live = false;
			if(t.isbGood()){
				 t.setLife(t.getLife()-10);
				if(t.getLife() == 0)
					t.setLive(false);
			}
			else 
				t.setLive(false);
			tc.explodes.add(new Explode(x, y, tc));
			return true;
		}
		return false;
	}

	// �ӵ�����ǿ������
	public boolean hitWall(Wall wall) {
		if (wall.wallRectangle().intersects(this.getRect())) {
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	//�Եз�̹�������д���
	public boolean hitTanks(ArrayList<Tank> tanks){
		for(int i =0 ;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
}
