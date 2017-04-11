import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	
	//�ڲ���Ա����
	int x,y,w,h;
	//��������������
	TankClient tc;
	//���캯��
	public Wall(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public Wall(int x, int y, int w, int h,TankClient tc) {
		// TODO �Զ����ɵĹ��캯�����
		this(x, y, w, h);
		this.tc = tc;
	}
	//���ƺ���
	public void draw(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRect(x, y, w, h);
		g.setColor(color);
	}
	//��Χ������
	public Rectangle wallRectangle(){
		return new Rectangle(x,y,w,h);
	}

	
}
