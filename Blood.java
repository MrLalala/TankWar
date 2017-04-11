import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	//λ�úʹ�С���
	private int x,y;
	private int blood_R = 20;
	TankClient tc;
	//״̬ͳ��
	private int step = 0;
	//��ʱ����
	int flag = 0;
	//��Ѫ�������ʱ
	private  static final int delay = 100;
	private int count = 100;
	//���ڱ��
	private boolean live = true;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	//λ������
	private int [][] location={
			{233,166},{450,666},{100,669},{20,589},{344,422},{499,283},{333,624}
	};
	
	public Blood(){
		x = location[0][0];
		y = location[0][1];
	}
	
	//��ͼ����
	public void draw(Graphics g){
		if(!live){
			if(count-- == 0){
				this.setLive(true);
				step = 0;
				count = delay;
			}
				
			return;	
		}
		Color color = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillOval(x, y, blood_R, blood_R);
		g.setColor(color);
		if(flag == 0)
			move();
		if(flag ++ == 100)
			flag = 0;
	}
	//�ƶ�����
	public void move(){
		step ++;
		if(step == location.length)
			step = 0;
		x = location[step][0];
		y = location[step][1];
	}
	//��Χ���ط���
	public Rectangle bloodRect(){
		return new Rectangle(x, y, blood_R, blood_R);
	}
	
}
