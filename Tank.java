import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tank {

	// ̹����ʼλ��
	private int x, y;
	//�ӵ���ʼλ��
	private int bx,by;
	//��¼��ʼ����ֵ
	private int life = 50;
	//��Դ����
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = null;
	//ʹ���ֵ�
	private static Map<String,Image>map = new HashMap<>();
	static {
		imgs = new Image[] { tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif"))
		};
		map.put("L", imgs[0]);map.put("R", imgs[1]);map.put("U", imgs[2]);map.put("D", imgs[3]);
		map.put("LU", imgs[4]);map.put("LD", imgs[5]);map.put("RU", imgs[6]);map.put("RD", imgs[7]);
	}
	
	//λ�ñ��

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	// ̹���ϴ��ƶ�λ��
	private int oldX,oldY;
	// �����ж�
	private boolean kL = false, kR = false, kU = false, kD = false;
	//�з�̹��ת���־
	private  int flag = 0;
	// �õ���������
	private TankClient tc = null;
	
	//����̹�˵��ж�ԭ��ʹ�������������ʹ������ƶ�
	private static Random r = new Random();

	// ����ö��
	
	
	// ��ʼ����
	private Direction pt_Direct = Direction.D;
	
	// ���ұ��
	private boolean bGood;
	
	//�������
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	// �������
	private Direction dir = Direction.stop;
	// ̹��ֱ��
	public static final int Tank_r = 50;
	// �ƶ��ٶ�
	public static final int x_speed = 10, y_speed = 10;

	// ���캯��
	public Tank(int x, int y,boolean bGood) {
		this.x = x;
		this.y = y;
		this.setbGood(bGood);
		this.live = true;
	}

	// ��TankClient�Ĺ��캯��
	public Tank(int x, int y,boolean bGood, TankClient tc) {
		this(x, y, bGood);
		this.tc = tc;
	}
	
	public Tank(int x, int y,boolean bGood, Direction dif,TankClient tc) {
		this(x, y, bGood, tc);
		this.dir = dif;
	}

	// �ػ��¼�
	public void draw(Graphics g) {
		//���˾Ͳ�����
		if(!live){
			if(!isbGood())
				tc.tanks.remove(this);
			tc.allTanks.remove(this);
			return;
		}
		Color c = g.getColor();
		if(isbGood()) g.setColor(Color.yellow);
		else g.setColor(Color.red);
		if(isbGood()){
			g.drawRect(x, y-15, Tank_r, 10);
			g.fillRect(x, y-15, life, 10);
		}
		g.setColor(c);
		draw_pt(g);
		move();
	}

	// ������Ͳ
	void draw_pt(Graphics g) {
		switch (pt_Direct) {
		case L:
			g.drawImage(map.get("L"), x, y, null);
			break;
		case R:
			g.drawImage(map.get("R"), x, y, null);
			break;
		case U:
			g.drawImage(map.get("U"), x, y, null);
			break;
		case D:
			g.drawImage(map.get("D"), x, y, null);
			break;
		case LU:
			g.drawImage(map.get("LU"), x, y, null);
			break;
		case LD:
			g.drawImage(map.get("LD"), x, y, null);
			break;
		case RU:
			g.drawImage(map.get("RU"), x, y, null);
			break;
		case RD:
			g.drawImage(map.get("RD"), x, y, null);
			break;
		default:
			break;
		}
	}

	// �ƶ��¼�
	void move() {
		oldX = this.x;
		oldY = this.y;
		switch (dir) {
		case L:
			x -= x_speed;
			bx = x;
			by = y+(Tank_r - Bullet.bullet_r)/2;
			break;
		case R:
			x += x_speed;
			bx = x+Tank_r;
			by = y+(Tank_r - Bullet.bullet_r)/2;
			break;
		case U:
			y -= y_speed;
			bx = x+(Tank_r - Bullet.bullet_r)/2;
			by = y;
			break;
		case D:
			y += y_speed;
			bx = x+(Tank_r - Bullet.bullet_r)/2;
			by = y+Tank_r;
			break;
		case LU:
			x -= x_speed;
			y -= y_speed;
			bx = x;
			by = y;
			break;
		case LD:
			x -= x_speed;
			y += y_speed;
			bx = x;
			by = y+(Tank_r - Bullet.bullet_r);
			break;
		case RU:
			x += x_speed;
			y -= y_speed;
			bx = x+(Tank_r - Bullet.bullet_r);
			by = y;
			break;
		case RD:
			x += x_speed;
			y += y_speed;
			bx = x+Tank_r;
			by = y+Tank_r;
			break;
		case stop:
			break;
		}
		if(x < 7) x = 7;
		if(y < 30) y = 30;
		if(x > TankClient.Game_w-Tank_r-5) x = TankClient.Game_w-Tank_r-5;
		if(y > TankClient.Game_h-Tank_r-5) y = TankClient.Game_h-Tank_r-5;
		if(this.tankStay(tc.wall) || this.tankStay(tc.wall2)){
			this.x = oldX;
			this.y = oldY;
		}
		//ָ���з�̹�˵�����ƶ�����
		if(!isbGood()){
			//�÷������Խ�ö������תΪ��Ӧ������
			Direction[] dirs = Direction.values();
			int rn = r.nextInt(dirs.length);
			if(flag == 0){
				this.dir = dirs[rn];
				if(dirs[rn]!=Direction.stop){
					this.pt_Direct = dir;
				}
			}
			flag ++;
			//���������
			if(flag >r.nextInt(20)+3)
				flag = 0;
			if(r.nextInt(40)>38)this.fire();
		}
	}

	// Tank�İ�����ѹ����:�޸ķ���
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			kR = true;
			break;
		case KeyEvent.VK_LEFT:
			kL = true;
			break;
		case KeyEvent.VK_DOWN:
			kD = true;
			break;
		case KeyEvent.VK_UP:
			kU = true;
			break;
		// Ϊ�ո�ʱ�����ӵ�
		}
		locateDirection();
	}

	// ̹�˵İ����ͷŲ�������������
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT:
			kR = false;
			break;
		case KeyEvent.VK_LEFT:
			kL = false;
			break;
		case KeyEvent.VK_DOWN:
			kD = false;
			break;
		case KeyEvent.VK_UP:
			kU = false;
			break;
		case KeyEvent.VK_SPACE:
			if(this.isLive())
				fire();   
			break;
		case KeyEvent.VK_A:
			if(this.isLive())
				this.superBullet();
			break;
		}
		locateDirection();
	}

	// �����¼�
	public Bullet fire() {
		Direction temp = null;
		if (dir == Direction.stop) {
			temp = pt_Direct;
		} else
			temp = dir;	
		Bullet b = new Bullet(bx,by, temp, this.tc,isbGood());
//		if(!bGood)
//			tc.enemyBullets.add(b);
		tc.bullets.add(b);
		return b;

	}
	//������Ŀ����¼�
	public Bullet fire(Direction d){
		Bullet b = new Bullet(bx,by, d, this.tc,isbGood());
//		if(!bGood)
//			tc.enemyBullets.add(b);
		tc.bullets.add(b);
		return b;
	}

	// �жϷ���
	void locateDirection() {
		if (kL && !kR && !kU && !kD)
			dir = Direction.L;
		else if (!kL && kR && !kU && !kD)
			dir = Direction.R;
		else if (!kL && !kR && kU && !kD)
			dir = Direction.U;
		else if (!kL && !kR && !kU && kD)
			dir = Direction.D;
		else if (kL && !kR && kU && !kD)
			dir = Direction.LU;
		else if (kL && !kR && !kU && kD)
			dir = Direction.LD;
		else if (!kL && kR && kU && !kD)
			dir = Direction.RU;
		else if (!kL && kR && !kU && kD)
			dir = Direction.RD;
		else
			dir = Direction.stop;
		if (dir != Direction.stop)
			pt_Direct = dir;
		//if(!bGood)
			
	}
	//���з�Χ����
	public Rectangle getRect() {
		// TODO �Զ����ɵķ������
		return new Rectangle(this.x, this.y, Tank_r, Tank_r);
	}
	
	//��������
	public boolean isbGood() {
		return bGood;
	}

	public void setbGood(boolean bGood) {
		this.bGood = bGood;
	}
	
	// ̹��ײǽ������
	public boolean tankStay(Wall wall) {
		if (wall.wallRectangle().intersects(this.getRect())){
			this.stay();
			return true;
		}
			
		return false;
	}
	//̹���ƶ�ײ��������
	public boolean tankImpact(ArrayList<Tank>tanks){
		for (int i = 0;i < tanks.size();i++){
			Tank tank = tanks.get(i);
			if(this.isLive() && tank.isLive() && this != tank && this.getRect().intersects(tank.getRect())){
				this.stay();
				tank.stay();
			}
		}
		return false;
	}
	//ֹͣ�¼�
	public void stay(){
		this.x = oldX;
		this.y = oldY;
	}
	
	//�����ڵ��¼�
	public void superBullet(){
		Direction[] dirs = Direction.values();
		for(int i = 0;i<dirs.length;i++){
			if(dirs[i] != Direction.stop)
				fire(dirs[i]);
		}
	}
	
	//��Ѫ�¼�
	public void eatBlood(Blood b){
		if(this.isbGood())
			if(b.isLive()&&this.getRect().intersects(b.bloodRect())){
				if(this.life != 50)
					life += 10;
				b.setLive(false);
			}
	}
}
