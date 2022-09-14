package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

import info.Contents;
import info.MinefieldButton;

public class MainPanel extends JPanel implements ActionListener {
	private JPanel[] panels;
	/*
	 * [0][?][?]: Rows.
	 * [1][?][?]: Columns.
	 * [x][y][?]: Specific row(x==0)/column(x==1).
	 * [x][y][0]: Number of points in a specific row/column.
	 * [x][y][1]: Number of  mines in a specific row/column.
	 */
	private int[][][] rowPoints = {{{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}, {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}}};
	private boolean isFinished = false;
	/*
	 * First array: Rows.
	 * Second array: Columns.
	 */
	protected static JComponent[][] gameC;
	protected static JLabel[] infoC = {new JLabel(), new JLabel()};
	protected static int tPoints;
	protected static int mode;	// 0->Mark mine; 1->Mark one; 2->Mark two; 3->Mark three; 4->Interact mode.
	
	public MainPanel() {
		mode = 4;
		tPoints = 0;
		setLayout(new BorderLayout());
		setFocusable(true);
		gameC = new JComponent[6][6];
		panels = new JPanel[2];
		panels[0] = new InfoPanel();
		panels[1] = new GamePanel();
		
		for(int i = 0; i < gameC.length; i++) {
			for (int j = 0; j < gameC[i].length; j++) {
				if (i==5||j==5) {
					JLabel jl = new JLabel("");
					//jl.setText("Label " + i + "." + j);
					jl.setHorizontalAlignment(JLabel.CENTER);
					jl.setOpaque(true);
					jl.setBackground(Color.BLACK);
					jl.setForeground(Color.WHITE);
					gameC[i][j] = jl;
				} else {
					MinefieldButton mb = new MinefieldButton();
					mb.setRandomHolds();
					mb.setBackground(Color.YELLOW);
					mb.setForeground(Color.YELLOW);
					mb.setText(/*mb.getHolds() + */"");	//-ONLY FOR TESTING
					if (mb.getHolds().ordinal()==0) {
						rowPoints[0][i][1]++; rowPoints[1][j][1]++;
					}
					rowPoints[0][i][0] += mb.getHolds().ordinal();	// Rows
					rowPoints[1][j][0] += mb.getHolds().ordinal();	// Columns
					mb.addActionListener(this);
					gameC[i][j] = mb;
					
				}
				panels[1].add(gameC[i][j]);
			}
		}
		for (int i = 0; i < 5; i++) {
			/* REMINDER:
			 * rowPoints[0][i][0]: Number of points in row number i.
			 * rowPoints[0][i][1]: Number of mines in row number i.
			 * rowPoints[1][i][0]: Number of points in column number i.
			 * rowPoints[1][i][0]: Number of mines in column number i.
			 */
			((JLabel) gameC[i][5]).setText("<html><p style=\"width:50px\">" + "Pts: " + rowPoints[0][i][0]
					+ "\nMines: " + rowPoints[0][i][1] + "</p></html>");
			((JLabel) gameC[5][i]).setText("<html><p style=\"width:50px\">" + "Pts: " + rowPoints[1][i][0]
					+ "\nMines: " + rowPoints[1][i][1] + "</p></html>");
		}
		
		infoC[0].setOpaque(true); infoC[0].setBackground(Color.DARK_GRAY); infoC[0].setForeground(Color.WHITE);
		infoC[0].setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		infoC[1].setOpaque(true); infoC[1].setBackground(Color.GRAY); infoC[1].setForeground(Color.WHITE);
		infoC[1].setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		panels[0].add(infoC[0]); panels[0].add(infoC[1]);
		
		add(panels[0], BorderLayout.NORTH); add(panels[1], BorderLayout.CENTER);
		
		addKeyListener(new KeyAdapter() {	// Changes mode in a cyclic way.
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'z') {
					mode = (mode + 1) % 5;
					InfoPanel.updateL();
					//-(start)ONLY FOR TESTING
					String s;
					switch (mode) {
						case 0:
							s = "Mark mine";
							break;
						case 1:
							s = "Mark one";
							break;
						case 2:
							s = "Mark two";
							break;
						case 3:
							s = "Mark three";
							break;
						case 4:
							s = "Interact mode";
							break;
						default:
							s = "Incorrect mode";
					}
					System.out.println("Switched to mode " + mode + ". " + s);
					//-(end)ONLY FOR TESTING
				}
			}
		});
		requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isFinished && e.getSource() instanceof MinefieldButton) {
			MinefieldButton b = (MinefieldButton) e.getSource();
			if (!b.getHasBeenPressed()) {
				switch (mode) {
					case 0:
						b.toggleMark(Contents.MINE);
						break;
					case 1:
						b.toggleMark(Contents.ONE_POINT);
						break;
					case 2:
						b.toggleMark(Contents.TWO_POINTS);
						break;
					case 3:
						b.toggleMark(Contents.THREE_POINTS);
						break;
					case 4:
						buttonInteracted(b);
				}
			}
		}
		requestFocus();
	}
	
	private void buttonInteracted(MinefieldButton b) {
		switch (b.getHolds()) {
			case MINE:
				System.out.println("This button has a mine.");	//-ONLY FOR TESTING
				b.toggleMark(Contents.NONE);
				b.setBackground(Color.RED);
				stop();
				break;
			case ONE_POINT:
				System.out.println("This button has 1 point.");	//-ONLY FOR TESTING
				tPoints += 1;
				b.toggleMark(Contents.NONE);
				b.setText(b.getHolds().ordinal() + "");
				b.setBackground(Color.BLUE);
				InfoPanel.updateL();
				b.setHasBeenPressed(true);
				break;
			case TWO_POINTS:
				System.out.println("This button has 2 points.");//-ONLY FOR TESTING
				tPoints += 2;
				b.toggleMark(Contents.NONE);
				b.setText(b.getHolds().ordinal() + "");
				b.setBackground(Color.BLUE);
				InfoPanel.updateL();
				b.setHasBeenPressed(true);
				break;
			case THREE_POINTS:
				System.out.println("This button has 3 points.");//-ONLY FOR TESTING
				tPoints += 3;
				b.toggleMark(Contents.NONE);
				b.setText(b.getHolds().ordinal() + "");
				b.setBackground(Color.BLUE);
				InfoPanel.updateL();
				b.setHasBeenPressed(true);
				break;
			default:
				System.out.println("This button is empty.");	//-ONLY FOR TESTING
		}
	}
	
	private void stop() {
		isFinished = true;
		infoC[0].setText("YOU LOST");
	}
}
