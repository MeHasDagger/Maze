
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.scene.control.Cell;

@SuppressWarnings("serial")
public class View extends JFrame {
	/**
	 * Conventions:
	 * 
	 * maze[row][col]
	 * 
	 * Values: 0 = not-visited node
	 *         1 = wall (blocked)
	 *         2 = visited node
	 *         9 = target node
	 *
	 * borders must be filled with "1" to void ArrayIndexOutOfBounds exception.
	 */

	private Integer[][] maze;

	private final List<Integer> path = new ArrayList<Integer>();
	private int currentxPos = 1;
	private int currentyPos = 1;

	public View() {

		JPanel buttonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
		JTextField mazeSizeText = new JTextField(4);
		JButton applyMazeSize = new JButton("Apply");
		JButton showPath = new JButton("Solve");
		mazeSizeText.setText("25");

		KeyListener keylis = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//hjÃ¤lper ignent


			}

			@Override
			public void keyReleased(KeyEvent e) {
				//seg


			}

			@Override
			public void keyPressed(KeyEvent e) {
				//snabbast
				movePlayer(e);
				repaint(); 

			}
		};
		buttonPanel.addKeyListener(keylis);
		mazeSizeText.addKeyListener(keylis);
		applyMazeSize.addKeyListener(keylis);
		showPath.addKeyListener(keylis);
		mazeSizeText.addKeyListener(keylis);

		applyMazeSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int mazeSize = Integer.parseInt(mazeSizeText.getText());
				MazeGenerator generatedMaze = new MazeGenerator(mazeSize);
				maze = generatedMaze.getMazeArray();
				currentxPos = 1;
				currentyPos = 1;
				path.removeAll(path);
				repaint();

			}
		});

		showPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (maze != null) {
					System.out.println("maze not null");
					if(path != null)
						System.out.println("path not null");
					Solver.findPath(maze, 1, 1, path);

					repaint();
				}

			}
		});

		buttonPanel.add(mazeSizeText);
		buttonPanel.add(applyMazeSize);
		buttonPanel.add(showPath);
		add(buttonPanel);

		setTitle("Simple Maze Solver");
		setSize(640, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.translate(25, 75);

		// draw the maze
		if (maze != null) {
			
				for (int row = 0; row < maze.length; row++) {
					for (int col = 0; col < maze[0].length; col++) {
						Color color;
						switch (maze[row][col]) {
						case 1 : color = Color.BLACK; break;
						case 9 : color = Color.RED; break;
						default : color = Color.WHITE;
						}
						g.setColor(color);
						g.fillRect(10 * col, 10 * row, 10, 10);
						g.setColor(Color.BLACK);
						g.drawRect(10 * col, 10 * row, 10, 10);
					}
				}

				// draw the path list
				for (int p = 0; p < path.size(); p += 2) {
					int pathX = path.get(p);
					int pathY = path.get(p + 1);
					g.setColor(Color.GREEN);
					g.fillRect(pathX * 10, pathY * 10, 10, 10);
				}

				//player
				int pathX = currentxPos;
				int pathY = currentyPos;
				g.setColor(Color.BLUE);
				g.fillOval(pathX * 10, pathY * 10, 10, 10);
				
				if(maze[currentyPos][currentxPos] == 9) 
					JOptionPane.showMessageDialog(getParent(), "You finished the maze!");
			}
		
	}


	private void movePlayer(KeyEvent ke) {

		if (ke.getID() != KeyEvent.KEY_PRESSED) {
			return;
		}
		if (ke.getKeyCode() == KeyEvent.VK_UP) {
			if (maze[currentyPos - 1][currentxPos] != 1) {
				currentyPos += -1;
			} 

		} else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (maze[currentyPos][currentxPos + 1] != 1) {
				currentxPos += 1;
			}

		} else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
			if (maze[currentyPos + 1][currentxPos] != 1) {
				currentyPos += 1;
			}
		} else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			if (maze[currentyPos][currentxPos - 1] != 1) {
				currentxPos += -1;
			}

		}

	}

	public static void main(String[] args) {

		View view = new View();
		view.setVisible(true);
	}

}