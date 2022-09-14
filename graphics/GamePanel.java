package graphics;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
	public GamePanel() {
		GridLayout gl = new GridLayout(6, 6);
		gl.setHgap(1); gl.setVgap(1);
		setLayout(gl);
	}
}
