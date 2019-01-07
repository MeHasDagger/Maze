import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMazeGenerator {
	MazeGenerator generatedMaze;
	Integer[][] maze;
	
	@Before
	public void setUp() throws Exception {
		generatedMaze = new MazeGenerator(5);
	}

	@After
	public void tearDown() throws Exception {
		generatedMaze = null;
	}

	@Test
	public void testMazeArray() {
		maze = generatedMaze.getMazeArray();
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				assertNotNull(maze[i][j]);
				
			}
		}
	}
	
	@Test
	public void testToString() {
		String output = generatedMaze.toString();
		
	}

}
