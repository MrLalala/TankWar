import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bullet {
	//子弹的起始位置
	int x,y;
	//子弹的方向
	Tank.Direction dir;
	//子弹的速度 
	private static final int x_speed = 20,y_speed = 20;
	//拿到窗口引用
	private TankClient tc;
	//设置子弹颜色
	private Color bColor = Color.black;
	//生死标记
	private boolean live = true;
	//敌我子弹标记
	private boolean bGood;
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean isLive) {
		this.live = isLive;
	}
	//子弹的大小
	static final int bullet_r = 10; 	
	//构造函数：子弹的起始位置
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
	//子弹的重绘事件
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
	//子弹的移动事件
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
		
		//死亡标记条件
		if(x <= 0 || y <= 0 || x >= TankClient.Game_w || y >= TankClient.Game_h){
			this.live = false;
		}
			
	}
	
	//判断位置关系
	public Rectangle getRect(){
		return new Rectangle(x, y, bullet_r, bullet_r);
	}
//	//击墙专用位置
//	public Rectangle getRect2(){
//		return new Rectangle(x-bullet_r, y-bullet_r, 2*bullet_r, 2*bullet_r);
//	}
	
	//击中方法
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

	// 子弹击中强处理函数
	public boolean hitWall(Wall wall) {
		if (wall.wallRectangle().intersects(this.getRect())) {
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	//对敌方坦克做击中处理
	public boolean hitTanks(ArrayList<Tank> tanks){
		for(int i =0 ;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
}
