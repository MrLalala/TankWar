import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void launchFrame(){
		setBounds(200,200,600,800);
		setLayout(null);
		//setBackground(Color.blue);
		this.setResizable(false);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TankClient tc = new TankClient();
		tc.launchFrame();
	}

}
