import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bullet {
	//子弹的起始位置
	int x,y;
	//子弹的方向
	Direction dir;
	//子弹的速度 
	private static final int x_speed = 20,y_speed = 20;
	//拿到窗口引用
	private TankClient tc;
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
	static final int bullet_r = 12; 	
	//资源钩子
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
	 * 构造函数：子弹的起始位置
	 * @param x ：起始横坐标
	 * @param y ：起始纵坐标
	 * @param dir ：起始方向
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
	 * 子弹的重绘事件
	 * @param g ：主函数的画笔
	 */
	public void paint(Graphics g){
		if(!live){
			tc.bullets.remove(this);
			return;
		}
		move(g);
	}
	/**
	 * 子弹的移动事件
	 * @param g ：主窗体的画笔
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
		
		//死亡标记条件
		if(x <= 0 || y <= 0 || x >= TankClient.Game_w || y >= TankClient.Game_h){
			this.live = false;
		}
			
	}
	
	/**
	 * 判断位置关系
	 * @return ：该子弹的作用范围
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, bullet_r, bullet_r);
	}
//	//击墙专用位置
//	public Rectangle getRect2(){
//		return new Rectangle(x-bullet_r, y-bullet_r, 2*bullet_r, 2*bullet_r);
//	}
	
	/**
	 * 击中方法
	 * @param t ：传入的坦克对象
	 * @return	：是否击中标记
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
	 *  子弹击墙壁处理函数
	 * @param wall ：主窗体中的墙
	 * @return ： 是否被击中
	 */
	public boolean hitWall(Wall wall) {
		if (wall.wallRectangle().intersects(this.getRect())) {
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * 对敌方坦克做击中处理
	 * @param tanks ：坦克列表
	 * @return ：是否被击中
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
