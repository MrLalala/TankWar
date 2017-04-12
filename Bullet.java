import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bullet {
	//�ӵ�����ʼλ��
	int x,y;
	//�ӵ��ķ���
	Direction dir;
	//�ӵ����ٶ� 
	private static final int x_speed = 20,y_speed = 20;
	//�õ���������
	private TankClient tc;
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
	static final int bullet_r = 12; 	
	//��Դ����
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<String, Image>map = new HashMap<>();
	private static Image[] imgs = {
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileL.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileR.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileD.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileU.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileLU.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileLD.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileRU.gif")),
		tk.getImage(Bullet.class.getClassLoader().getResource("images/missileRD.gif"))
	};
	static{
		map.put("L", imgs[0]);map.put("R", imgs[1]);map.put("U", imgs[2]);map.put("D", imgs[3]);
		map.put("LU", imgs[4]);map.put("LD", imgs[5]);map.put("RU", imgs[6]);map.put("RD", imgs[7]);
	}
	/**
	 * ���캯�����ӵ�����ʼλ��
	 * @param x ����ʼ������
	 * @param y ����ʼ������
	 * @param dir ����ʼ����
	 */
	public Bullet(int x, int y,Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Bullet(int x, int y,Direction dir,TankClient tc) {
		this(x, y, dir);
		this.tc = tc;
	}
	public Bullet(int x, int y,Direction dir,TankClient tc,boolean bGood) {
		this(x, y, dir, tc);
		this.bGood = bGood;
	}
	/**
	 * �ӵ����ػ��¼�
	 * @param g ���������Ļ���
	 */
	public void paint(Graphics g){
		if(!live){
			tc.bullets.remove(this);
			return;
		}
		move(g);
	}
	/**
	 * �ӵ����ƶ��¼�
	 * @param g ��������Ļ���
	 */
	void move(Graphics g){
		switch (dir) {
		case L:
			x -= x_speed;
			g.drawImage(map.get("L"), x, y, null);
			break;
		case R:
			x += x_speed;
			g.drawImage(map.get("R"), x, y, null);
			break;
		case U:
			y -= y_speed;
			g.drawImage(map.get("U"), x, y, null);
			break;
		case D:
			y += y_speed;
			g.drawImage(map.get("D"), x, y, null);
			break;
		case LU:
			x -= x_speed;
			y -= y_speed;
			g.drawImage(map.get("LU"), x, y, null);
			break;
		case LD:
			x -= x_speed;
			y += y_speed;
			g.drawImage(map.get("LD"), x, y, null);
			break;
		case RU:
			x += x_speed;
			y -= y_speed;
			g.drawImage(map.get("RU"), x, y, null);
			break;
		case RD:
			x += x_speed;
			y += y_speed;
			g.drawImage(map.get("RD"), x, y, null);
			break;
		default:
			break;
		}
		
		//�����������
		if(x <= 0 || y <= 0 || x >= TankClient.Game_w || y >= TankClient.Game_h){
			this.live = false;
		}
			
	}
	
	/**
	 * �ж�λ�ù�ϵ
	 * @return �����ӵ������÷�Χ
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, bullet_r, bullet_r);
	}
//	//��ǽר��λ��
//	public Rectangle getRect2(){
//		return new Rectangle(x-bullet_r, y-bullet_r, 2*bullet_r, 2*bullet_r);
//	}
	
	/**
	 * ���з���
	 * @param t �������̹�˶���
	 * @return	���Ƿ���б��
	 */
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

	/**
	 *  �ӵ���ǽ�ڴ�����
	 * @param wall ���������е�ǽ
	 * @return �� �Ƿ񱻻���
	 */
	public boolean hitWall(Wall wall) {
		if (wall.wallRectangle().intersects(this.getRect())) {
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * �Եз�̹�������д���
	 * @param tanks ��̹���б�
	 * @return ���Ƿ񱻻���
	 */
	public boolean hitTanks(ArrayList<Tank> tanks){
		for(int i =0 ;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
}
