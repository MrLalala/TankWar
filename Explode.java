import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
	//λ��
	int x,y;
	//�������
	private boolean live = true;
	//�ж��Ƿ�Ϊ��ʼ��
	private boolean init = false;
	//ͬ�� ToolKit��ȡӲ���ļ�
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//�½�ͼƬ����
	private static Image [] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	//ִ�в���
	int step = 0;
	//��ը��ɫ
	//private Color bloom = Color.orange;
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
		if(!init){
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		if(!live){
			tc.explodes.remove(this);
			return;
		}
		if(step == imgs.length){
			live =false;
			step = 0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
