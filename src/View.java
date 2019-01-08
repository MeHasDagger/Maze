
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
	CustomKeyListener keylis;
	JTextField mazeSizeText;
	JTextField cellSizeText;
//	private int currentxPos = 1;
//	private int currentyPos = 1;
	
	public View() {
		JPanel buttonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
    	mazeSizeText = new JTextField(4);
    	JButton applyMazeSize = new JButton("Apply");
    	cellSizeText = new JTextField(4);
    	JButton showPath = new JButton("Solve");
    	mazeSizeText.setText("25");
    	cellSizeText.setText("10");
    	
    	keylis = new CustomKeyListener(this);
   
    	mazeSizeText.addKeyListener(keylis);
    	mazeSizeText.addKeyListener(keylis); 
    	applyMazeSize.addKeyListener(keylis);
   		showPath.addKeyListener(keylis);
   		buttonPanel.addKeyListener(keylis);

    	applyMazeSize.addActionListener(new ApplyButtonActionListener());
    	showPath.addActionListener(new SolveButtonActionListener());
    	
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
	
	public MazeGenerator getGeneratedMaze() {
		return generatedMaze;
		
	}
	
	class ApplyButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int mazeSize = 0;
			try{ 
				mazeSize = Integer.parseInt(mazeSizeText.getText());
				cellSize = Integer.parseInt(cellSizeText.getText());
				
				if (mazeSize >= 2 && cellSize >= 2) {
					generatedMaze = new MazeGenerator(mazeSize);
				    maze = generatedMaze.getMazeArray();
				    keylis.resetCurrentPos();
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
	}
	
	class SolveButtonActionListener implements ActionListener {

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
				for (int i = 0; i < path.size(); i += 2) {
					int pathX = path.get(i);
					int pathY = path.get(i + 1);
					g.setColor(Color.GREEN);
					g.fillRect(pathX * cellSize, pathY * cellSize, cellSize, cellSize);
				}
			
			//player
			g.setColor(Color.BLUE);
			g.fillOval(keylis.getCurrentxPos() * cellSize, keylis.getCurrentyPos() * cellSize, cellSize, cellSize);
	    	}
	    }  
	}


}