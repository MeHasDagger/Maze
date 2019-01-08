import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener implements KeyListener{
	private static int currentxPos = 1;
	private static int currentyPos = 1;
	private View view;
	
	public CustomKeyListener(View view) {
		this.view = view;
	
	}
	
	public int getCurrentxPos() {
		return currentxPos;
	}
	
	public int getCurrentyPos() {
		return currentyPos;
	}
	
	public void resetCurrentPos() {
		currentxPos = 1;
		currentyPos = 1;
	}
	
	private void movePlayer(KeyEvent ke) {
		Integer[][] maze = view.getGeneratedMaze().getMazeArray();
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

	@Override
	public void keyPressed(KeyEvent e) {
		movePlayer(e);
		view.repaint(); 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
}
