
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class holds all the GUI elements and a few controllers.
 * 
 * @author Mattias Melchior, Sanna Lundqvist
 *
 */
@SuppressWarnings("serial")
public class View extends JFrame {

	private Integer[][] mazeArray; // The maze represented by Integers(0 for path, 1 for wall, 2 for visited path, 9 for endpoint)
	private MazeGenerator generatedMaze; // The MazeGenerator object
	private final List<Integer> path = new ArrayList<Integer>(); // The path to the exit
	private boolean isPathShowing = false; // Used as a toggle to show the correct path or not
	private CustomKeyListener keylistener; // The CustomKeyListener object
	private JTextField mazeSizeText; // A JTextField that sets the size of the maze
	private JTextField cellSizeText; // A JTextField that sets the size of a cellblock
	private int cellSize = 10; // Determines the graphical representaion of a cellblock
	
	/**
	 * A constructor that creates all the GUI elements and sets listeners to components that need one.
	 */
	public View() {
		JPanel buttonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
		
		JTextField mazeDesc = new JTextField(6);
		JTextField cellDesc = new JTextField(5);
		mazeDesc.setText("Maze size:");
		mazeDesc.setEditable(false);
		mazeDesc.setBorder(null);
		mazeDesc.setFocusable(false);
		cellDesc.setText("Cell size:");
		cellDesc.setEditable(false);
		cellDesc.setBorder(null);
		cellDesc.setFocusable(false);
		
    	mazeSizeText = new JTextField(4);
    	cellSizeText = new JTextField(4);
    	mazeSizeText.setText("25");
    	cellSizeText.setText("10");
    	JButton applyMazeSize = new JButton("Apply");
    	JButton showPath = new JButton("Solve");
   
    	keylistener = new CustomKeyListener(this);
   
    	mazeSizeText.addKeyListener(keylistener);
    	cellSizeText.addKeyListener(keylistener); 
    	applyMazeSize.addKeyListener(keylistener);
   		showPath.addKeyListener(keylistener);
   		buttonPanel.addKeyListener(keylistener);

    	applyMazeSize.addActionListener(new ApplyButtonActionListener());
    	showPath.addActionListener(new SolveButtonActionListener());
    	
    	buttonPanel.add(mazeDesc);
    	buttonPanel.add(mazeSizeText);
    	buttonPanel.add(cellDesc);
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
	
	/**
	 * Getter for the MazeGenerator object.
	 * 
	 * @return the generatedMaze
	 */
	public MazeGenerator getGeneratedMaze() {
		return generatedMaze;
		
	}
	
	/**
	 * A inner class that implements ActionListener used for the applyButton. 
	 * It confirms that there is numerical values in the JTextFields and reapplies them to the maze and GUI.
	 * Also resets a bunch of controls for player movement
	 *  
	 * @author Mattias Melchior, Sanna Lundqvist
	 *
	 */
	class ApplyButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int mazeSize = 0;
			try{ 
				mazeSize = Integer.parseInt(mazeSizeText.getText());
				cellSize = Integer.parseInt(cellSizeText.getText());
				
				if (mazeSize >= 2 && cellSize >= 2) {
					generatedMaze = new MazeGenerator(mazeSize);
				    mazeArray = generatedMaze.getMazeArray();
				    keylistener.resetCurrentPos();
				    keylistener.resetIfWon();
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
	
	/**
	 * A inner class that implements ActionListener used for the findPathButton. 
	 * It toggles between showing the path to the exit and hiding it.
	 * 
	 * @author Mattias Melchior, Sanna Lundqvist
	 *
	 */
	class SolveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (mazeArray != null) {
				if (isPathShowing) {
					path.removeAll(path);
					isPathShowing = false;
				} else {
					mazeArray = generatedMaze.getMazeArray();
					Solver.findPath(mazeArray, keylistener.getCurrentyPos(), keylistener.getCurrentxPos(), path);
					isPathShowing = true;
				}
			    repaint();  
			}
		}
	}
	
	/**
	 * A inner class that extends JPanel and is used to draw the maze.
	 * 
	 * @author Mattias Melchior, Sanna Lundqvist
	 *
	 */
	class CustomPanel extends JPanel {
		
		/**
		 * Sets the JPanel size to cover the JFrame.
		 * 
		 * @return the Dimension
		 */
	    public Dimension getPreferredSize() { 	
	        return new Dimension(getParent().getWidth(), getParent().getWidth());
	    }
	    
	    /**
	     * Paints the maze and player.
	     * 
	     * @param g - the Graphics
	     */
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);       

	        if (mazeArray != null) {
	        	for (int row = 0; row < mazeArray.length; row++) {
	        		for (int col = 0; col < mazeArray[0].length; col++) {
	        			Color color;
	        			switch (mazeArray[row][col]) {
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
				
				for (int i = 0; i < path.size(); i += 2) {
					if (i == 0) {
						
					} else {
						int pathY = path.get(i);
						int pathX = path.get(i + 1);
						g.setColor(Color.GREEN);
						g.fillRect(pathY * cellSize + 1, pathX * cellSize + 1, cellSize - 1, cellSize - 1);
					}
					
				}
			
			g.setColor(Color.BLUE);
			g.fillOval(keylistener.getCurrentxPos() * cellSize, keylistener.getCurrentyPos() * cellSize, cellSize, cellSize);
	    	}
	    }  
	}
}
