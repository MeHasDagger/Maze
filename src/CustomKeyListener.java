import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	
	/**
	 * Constructor that recieves the View.
	 * 
	 * @param view the View
	 */
	public CustomKeyListener(View view) {
		this.view = view;
	}
	
	/**
	 * Changes the player positon accordingly to what button is pressed.
	 * 
	 * @param event the KeyEvent
	 */
	private void movePlayer(KeyEvent event) {
		Integer[][] mazeArray = view.getGeneratedMaze().getMazeArray();
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
	 * Implemented method that triggers when a key is pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		movePlayer(e);
		view.repaint(); 
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
