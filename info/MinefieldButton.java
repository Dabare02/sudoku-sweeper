package info;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class MinefieldButton extends JButton{
	private Contents holds;
	private boolean[] mark = {false, false, false, false};
	private BufferedImage[] img = {null, null, null, null};
	private ImagePanel[] imgP = {null, null, null, null};
	private boolean hasBeenPressed;
	public static final int NONE = -1;
	public static final int MINE = 0;
	public static final int ONE_POINT = 1;
	public static final int TWO_POINTS = 2;
	public static final int THREE_POINTS = 3;
	
	public MinefieldButton() {
		this(Contents.ONE_POINT, false, false, false, false);
	}
	
	public MinefieldButton(String t) {
		this(t, Contents.ONE_POINT, false, false, false, false);
	}
	public MinefieldButton(Contents c, boolean mine, boolean one, boolean two, boolean three) {
		super();
		setFont(new Font("Arial", Font.BOLD, 40));
		setLayout(new GridLayout(2, 2));
		for (int i = 0; i < img.length; i++) {
			img[i] = openImg("src/images/" + i + ".png");
			imgP[i] = new ImagePanel(img[i]);
			imgP[i].setVisible(false);
			add(imgP[i]);
		}
		holds = c;
		hasBeenPressed = false;
		mark[0] = mine; mark[1] = one; mark[2] = two; mark[3] = three;
	}
	
	public MinefieldButton(String t, Contents c, boolean mine, boolean one, boolean two, boolean three) {
		super(t);
		holds = c;
		hasBeenPressed = false;
		mark[0] = mine; mark[1] = one; mark[2] = two; mark[3] = three;
	}

	public Contents getHolds() {
		return holds;
	}

	public void setHolds(Contents contains) {
		this.holds = contains;
	}
	
	public boolean getMark(int m) {
		try {
			return mark[m];	
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Error: Don't use NONE (-1) constant for the method \"getMark()\", or any." +
								"number outside the range [0, 3].");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean getHasBeenPressed() {
		return hasBeenPressed;
	}
	
	public void setHasBeenPressed(boolean b) {
		this.hasBeenPressed = b;
	}
	
	private BufferedImage openImg(String name) {
		try {
			BufferedImage img = ImageIO.read(new File(name));
			return img;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void toggleMark(Contents c) {
		switch(c) {
			case MINE:
				mark[c.ordinal()] = !mark[c.ordinal()];
				imgP[MINE].setVisible(!imgP[MINE].isVisible());
				break;
			case ONE_POINT:
				mark[c.ordinal()] = !mark[c.ordinal()];
				imgP[ONE_POINT].setVisible(!imgP[ONE_POINT].isVisible());
				break;
			case TWO_POINTS:
				mark[c.ordinal()] = !mark[c.ordinal()];
				imgP[TWO_POINTS].setVisible(!imgP[TWO_POINTS].isVisible());
				break;
			case THREE_POINTS:
				mark[c.ordinal()] = !mark[c.ordinal()];
				imgP[THREE_POINTS].setVisible(!imgP[THREE_POINTS].isVisible());
				break;
			case NONE:	// Removes all marks
				mark[MINE] = false; mark[ONE_POINT] = false; mark[TWO_POINTS] = false; mark[THREE_POINTS] = false;
				imgP[MINE].setVisible(false); imgP[ONE_POINT].setVisible(false);
				imgP[TWO_POINTS].setVisible(false); imgP[THREE_POINTS].setVisible(false);
				System.out.println("Marks removed");	//-ONLY FOR TESTING
				break;
			default:
				System.err.println("Warning: Only use a number in the range [-1, 3] for the method \"toggleMark()\"");
		}
	}
	
	public void setRandomHolds() {
		int contInt = ThreadLocalRandom.current().nextInt(0, 3);
		this.holds = Contents.values()[contInt];
	}
}

class ImagePanel extends JPanel {
	private BufferedImage img;
	
	public ImagePanel(BufferedImage img) {
		super();
		this.img = img;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}