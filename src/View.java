
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

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
	/*
    private int [][] maze = 
        { {1,1,1,1,1,1,1,1,1,1,1,1,1},
          {1,0,1,0,1,0,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,1,0,1,1,1,0,1},
          {1,0,0,0,1,1,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,0,0,1},
          {1,0,1,0,1,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,1,0,1},
          {1,0,0,0,0,0,0,0,0,0,1,9,1},
          {1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
     */
	private Integer[][] maze;
    
    private final List<Integer> path = new ArrayList<Integer>();
    private int pathIndex;
    private int cellSize;
    private int currentxPos = 1;
	private int currentyPos = 1;
	
    public View() {
    	JPanel buttonPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
    	JTextField mazeSizeText = new JTextField(4);
    	JTextField cellSizeText = new JTextField(4);
    	JButton applyMazeSize = new JButton("Apply");
    	JButton showPath = new JButton("Solve");
    	mazeSizeText.setText("25");
    	cellSizeText.setText("10");
  
    	applyMazeSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int mazeSize = 0;
				try{ 
					mazeSize = Integer.parseInt(mazeSizeText.getText());
					cellSize = Integer.parseInt(cellSizeText.getText());
					
					if (mazeSize >= 2 && cellSize >= 2) {
						MazeGenerator generatedMaze = new MazeGenerator(mazeSize);
					    maze = generatedMaze.getMazeArray();
					  
					    path.removeAll(path);
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
					Solver.findPath(maze, 1, 1, path);
				    pathIndex = path.size() - 2;
	
				    repaint();
				}
				 
			}
		});
    	
    	buttonPanel.add(mazeSizeText);
    	buttonPanel.add(cellSizeText);
    	buttonPanel.add(applyMazeSize);
    	buttonPanel.add(showPath);
    	add(buttonPanel);
    	 
        setTitle("Simple Maze Solver");
        setSize(640, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

  /*      
  
        MazeGenerator generatedMaze = new MazeGenerator(5);
        maze = generatedMaze.getMazeArray();
        maze[9][9] = 9;
        
        Solver.findPath(maze, 1, 1, path);
        pathIndex = path.size() - 2; */
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.translate(25, 75);
        
        // draw the maze
        if (maze != null) {
        /*	for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze.length; j++) {
					System.out.println("i" + i + " j"+ j);
				}
				System.out.println();
			} */
        	
        	 for (int row = 0; row < maze.length; row++) {
                 for (int col = 0; col < maze[0].length; col++) {
                     Color color;
               //  	System.out.println("cols: " + col + "rows: " + row);
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
        }
        
        
       

    /*    
        // draw the ball on path
        int pathX = path.get(pathIndex);
        int pathY = path.get(pathIndex + 1);
        g.setColor(Color.RED);
        g.fillOval(pathX * cellSize, pathY * cellSize, cellSize, cellSize);*/
        
    }
    
    @Override
    protected void processKeyEvent(KeyEvent ke) {
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
        	if (maze[currentxPos][currentyPos - 1] != 1) {
        		currentyPos = -1;
        		System.out.println("moved up");
        	} 
        		
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
        	if (maze[currentxPos + 1][currentyPos] != 1) {
        		currentxPos += 1;
        		System.out.println("moved right");
        	}
        
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
        	if (maze[currentxPos][currentyPos + 1] != 1) {
        		currentyPos += 1;
        		System.out.println("moved down");
        	}
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
        	if (maze[currentxPos - 1][currentyPos] != 1) {
        		currentxPos -= 1;
        		System.out.println("moved left");
        	}
           
        }
        System.out.println(currentxPos + " " + currentyPos);
        repaint(); 
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                view.setVisible(true);
            }
        });
    }
    
}