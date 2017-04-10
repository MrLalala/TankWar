import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	//λ��
	int x,y;
	//����
	private boolean live = true;
	//��ը�뾶
	int[] dia = {4,7,18,26,35,49,35,26,18,7,4};
	//ִ�в���
	int step = 0;
	//��ը��ɫ
	private Color bloom = Color.orange;
	//�õ�����������
	private TankClient tc;
	//���췽��
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	//��д��ͼ�¼�
	public void draw(Graphics g){
		if(!live) return;
		if(step == dia.length){
			live =false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(bloom);
		g.fillOval(x, y, dia[step], dia[step]);
		g.setColor(c);
		step++;
	}
}
