package graphics;

import javax.swing.*;

public class InfoPanel extends JPanel {
	
	public InfoPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		updateL();
	}
	
	public static void updateL() {
		String s = "";
		switch(MainPanel.mode) {
			case -1:
				s = "Remove all marks";
				break;
			case 0:
				s = "Mark mines";
				break;
			case 1:
				s = "Mark 1 point";
				break;
			case 2:
				s = "Mark 2 points";
				break;
			case 3:
				s = "Mark 3 points";
				break;
			case 4:
				s = "Interact";
		}
		MainPanel.infoC[0].setText("Mode: " + s);
		MainPanel.infoC[1].setText("Total Points: " + MainPanel.tPoints);
	}
}