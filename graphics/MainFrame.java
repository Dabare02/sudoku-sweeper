package graphics;

import javax.swing.*;

public class MainFrame extends JFrame {
	public static void main(String[] args) {
		MainFrame f = new MainFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new MainPanel());
		f.setVisible(true);
	}
	
	public MainFrame() {
		this("Window", 0, 0, 700, 600);
	}
	
	public MainFrame(String n, int x, int y, int w, int h) {
		super(n);
		setBounds(x, y, w, h);
	}
}