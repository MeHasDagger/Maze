import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * This class implements KeyListener and keeps track of the player movement.
 * 
 * @author Mattias Melchior, Sanna Lundqvist
 *
 */
public class CustomKeyListener implements KeyListener{
	private int currentxPos = 1; // The current player x-coordinate
	private int currentyPos = 1; // The current player y-coordinate
	private View view; // The View class that generated the maze
	private Integer[][] mazeArray; // The maze represented by Integers
	private boolean isWon = false; // If player has won by reaching the exit
	
	/**
	 * Constructor that recieves the View.
	 * 
	 * @param view the View
	 */
	public CustomKeyListener(View view) {
		this.view = view;
	}
	
	/**
	 * Changes the player position accordingly to what button is pressed.
	 * Checks if the block in the direction player wants to move in is not a wall.
	 * 
	 * @param event the KeyEvent
	 */
	private void movePlayer(KeyEvent event) {
		try{mazeArray = view.getGeneratedMaze().getMazeArray();
		}catch(NullPointerException e) {
			
		}
		
		if (event.getKeyCode() == KeyEvent.VK_UP) {
			if (mazeArray[currentyPos - 1][currentxPos] != 1) {
				currentyPos += -1;
			} 

		} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (mazeArray[currentyPos][currentxPos + 1] != 1) {
				currentxPos += 1;
			}

		} else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			if (mazeArray[currentyPos + 1][currentxPos] != 1) {
				currentyPos += 1;
			}
		} else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			if (mazeArray[currentyPos][currentxPos - 1] != 1) {
				currentxPos += -1;
			}
		}
	}
	
	/**
	 * Getter for CurrentxPos.
	 * 
	 * @return the currentxPos
	 */
	public int getCurrentxPos() {
		return currentxPos;
	}
	
	/**
	 * Getter for CurrentyPos.
	 * 
	 * @return the currentyPos
	 */
	public int getCurrentyPos() {
		return currentyPos;
	}
	
	/**
	 * Resets the player position to the starting position.
	 */
	public void resetCurrentPos() {
		currentxPos = 1;
		currentyPos = 1;
	}
	/**
	 * Resets the check if player won or not
	 */
	public void resetIfWon() {
		isWon = false;
	}

	/**
	 * Implemented method that triggers when a key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		movePlayer(e);
		view.repaint();
		checkIfWon();
	}
	
	/**
	 * Checks if the player have won by getting to the exit.
	 */
	public void checkIfWon() {
		try {
			if (mazeArray[currentyPos][currentxPos] == 9 && !isWon) {
				isWon = true;
				JOptionPane.showMessageDialog(new JFrame(), "Yay, you made it out!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
				
			}
		} catch(NullPointerException error) {
			
		}
	}

	/**
	 * Implemented method that triggers when a key is released.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/**
	 * Implemented method that triggers when a key is typed.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
