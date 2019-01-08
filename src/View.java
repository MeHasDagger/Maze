
import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class View extends JFrame {

	private Integer[][] maze;
	private MazeGenerator generatedMaze;
	private final List<Integer> path = new ArrayList<Integer>();
	private boolean isPathShowing = false;
	private int cellSize = 10;
	private int currentxPos = 1;
	private int currentyPos = 1;
	
	public View() {
		JPanel buttonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
    	JTextField mazeSizeText = new JTextField(4);
    	JButton applyMazeSize = new JButton("Apply");
    	JTextField cellSizeText = new JTextField(4);
    	JButton showPath = new JButton("Solve");
    	mazeSizeText.setText("25");
    	cellSizeText.setText("10");
    	
    	CustomKeyListener keylis = new CustomKeyListener();
   
      	buttonPanel.setFocusable(true);
    	buttonPanel.addKeyListener(keylis);
    	mazeSizeText.addKeyListener(keylis);
    	applyMazeSize.addKeyListener(keylis);
   		showPath.addKeyListener(keylis);
    	mazeSizeText.addKeyListener(keylis); 

    	applyMazeSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int mazeSize = 0;
				try{ 
					mazeSize = Integer.parseInt(mazeSizeText.getText());
					cellSize = Integer.parseInt(cellSizeText.getText());
					
					if (mazeSize >= 2 && cellSize >= 2) {
						generatedMaze = new MazeGenerator(mazeSize);
					    maze = generatedMaze.getMazeArray();
					    currentxPos = 1;
					    currentyPos = 1;
					    path.removeAll(path);
					    isPathShowing = false;
						repaint();
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Values need to be equals or larger than 2.", "Number too small", JOptionPane.WARNING_MESSAGE);
					}
				} catch(NumberFormatException err) {
					JOptionPane.showMessageDialog(new JFrame(), "Cannot parse, please only use numbers.", "NumberFormatException", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

    	showPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (maze != null) {
					if (isPathShowing) {
						path.removeAll(path);
						isPathShowing = false;
					} else {
						maze = generatedMaze.getMazeArray();
						Solver.findPath(maze, 1, 1, path);
						isPathShowing = true;
					}
				    repaint();  
				}
			}
		});
    	buttonPanel.setPreferredSize(new Dimension(40, 0));
    	buttonPanel.add(mazeSizeText);
    	buttonPanel.add(cellSizeText);
    	buttonPanel.add(applyMazeSize);
    	buttonPanel.add(showPath);
    
    	buttonPanel.add(new CustomPanel());
    
    	add(buttonPanel);
    	
		setTitle("Maze generator and solver");
		setSize(640, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}	
	
	private void movePlayer(KeyEvent ke) {
		
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
	
	class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			movePlayer(e);
			repaint(); 
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
		
		}
	}
	
	class CustomPanel extends JPanel {
		
	    public Dimension getPreferredSize() { 	
	        return new Dimension(getParent().getWidth(), getParent().getWidth());
	    }
	    
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);       
	  
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
						g.fillRect(cellSize * col, cellSize * row, cellSize, cellSize);
						g.setColor(Color.BLACK);
						g.drawRect(cellSize * col, cellSize * row, cellSize, cellSize);
					}
				}

				// draw the path list
				for (int p = 0; p < path.size(); p += 2) {
					int pathX = path.get(p);
					int pathY = path.get(p + 1);
					g.setColor(Color.GREEN);
					g.fillRect(pathX * cellSize, pathY * cellSize, cellSize, cellSize);
				}
			
			//player
			g.setColor(Color.BLUE);
			g.fillOval(currentxPos * cellSize, currentyPos * cellSize, cellSize, cellSize);
	    	}
	    }  
	}

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	View view = new View();
	        		view.setVisible(true);
	            }
	        });
	}
}