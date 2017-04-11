import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Tank {

	// 坦克起始位置
	private int x, y;
	//子弹起始位置
	private int bx,by;
	//记录起始生命值
	private int life = 50;
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	// 坦克上次移动位置
	private int oldX,oldY;
	// 方向判断
	private boolean kL = false, kR = false, kU = false, kD = false;
	//敌方坦克转向标志
	private  int flag = 0;
	// 拿到窗体引用
	private TankClient tc = null;
	
	//基于坦克的行动原理，使用随机数产生器使其随机移动
	private static Random r = new Random();

	// 方向枚举
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, stop
	};
	
	// 初始方向
	private Direction pt_Direct = Direction.D;
	
	// 敌我标记
	private boolean bGood;
	
	//生死标记
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	// 方向变量
	private Direction dir = Direction.stop;
	// 坦克直径
	public static final int Tank_r = 50;
	// 移动速度
	public static final int x_speed = 10, y_speed = 10;

	// 构造函数
	public Tank(int x, int y,boolean bGood) {
		this.x = x;
		this.y = y;
		this.setbGood(bGood);
		this.live = true;
	}

	// 带TankClient的构造函数
	public Tank(int x, int y,boolean bGood, TankClient tc) {
		this(x, y, bGood);
		this.tc = tc;
	}
	
	public Tank(int x, int y,boolean bGood, Direction dif,TankClient tc) {
		this(x, y, bGood, tc);
		this.dir = dif;
	}

	// 重绘事件
	public void draw(Graphics g) {
		//死了就不画了
		if(!live){
			if(!isbGood())
				tc.tanks.remove(this);
			tc.allTanks.remove(this);
			return;
		}
		Color c = g.getColor();
		if(isbGood()) g.setColor(Color.yellow);
		else g.setColor(Color.red);
		g.fillOval(x, y, Tank_r, Tank_r);
		if(isbGood()){
			g.drawRect(x, y-15, Tank_r, 10);
			g.fillRect(x, y-15, life, 10);
		}
		g.setColor(c);
		draw_pt(g);
		move();
	}

	// 绘制炮筒
	void draw_pt(Graphics g) {
		switch (pt_Direct) {
		case L:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x, y + Tank_r / 2);
			break;
		case R:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x + Tank_r, y + Tank_r / 2);
			break;
		case U:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x + Tank_r / 2, y);
			break;
		case D:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x + Tank_r / 2, y + Tank_r);
			break;
		case LU:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x, y);
			break;
		case LD:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x, y + Tank_r);
			break;
		case RU:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x + Tank_r, y);
			break;
		case RD:
			g.drawLine(x + Tank_r / 2, y + Tank_r / 2, x + Tank_r, y + Tank_r);
			break;
		default:
			break;
		}
	}

	// 移动事件
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
		//指定敌方坦克的随机移动方向
		if(!isbGood()){
			//该方法可以将枚举类型转为相应的数组
			Direction[] dirs = Direction.values();
			int rn = r.nextInt(dirs.length);
			if(flag == 0){
				this.dir = dirs[rn];
				if(dirs[rn]!=Direction.stop){
					this.pt_Direct = dir;
				}
			}
			flag ++;
			//借用随机数
			if(flag >r.nextInt(20)+3)
				flag = 0;
			if(r.nextInt(40)>38)this.fire();
		}
	}

	// Tank的按键按压操作:修改方向
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
		// 为空格时发射子弹
		}
		locateDirection();
	}

	// 坦克的按键释放操作：修正方向
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

	// 开火事件
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
	//带方向的开火事件
	public Bullet fire(Direction d){
		Bullet b = new Bullet(bx,by, d, this.tc,isbGood());
//		if(!bGood)
//			tc.enemyBullets.add(b);
		tc.bullets.add(b);
		return b;
	}

	// 判断方向
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
	//击中范围方法
	public Rectangle getRect() {
		// TODO 自动生成的方法存根
		return new Rectangle(this.x, this.y, Tank_r, Tank_r);
	}
	
	//属性设置
	public boolean isbGood() {
		return bGood;
	}

	public void setbGood(boolean bGood) {
		this.bGood = bGood;
	}
	
	// 坦克撞墙处理函数
	public boolean tankStay(Wall wall) {
		if (wall.wallRectangle().intersects(this.getRect())){
			this.stay();
			return true;
		}
			
		return false;
	}
	//坦克移动撞击处理函数
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
	//停止事件
	public void stay(){
		this.x = oldX;
		this.y = oldY;
	}
	
	//超级炮弹事件
	public void superBullet(){
		Direction[] dirs = Direction.values();
		for(int i = 0;i<dirs.length;i++){
			if(dirs[i] != Direction.stop)
				fire(dirs[i]);
		}
	}
	
	//吃血事件
	public void eatBlood(Blood b){
		if(this.isbGood())
			if(b.isLive()&&this.getRect().intersects(b.bloodRect())){
				if(this.life != 50)
					life += 10;
				b.setLive(false);
			}
	}
}
