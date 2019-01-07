import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testSolver {
	 private final List<Integer> testPath = new ArrayList<Integer>();
	 private Integer[][] testMaze = 
	         {{1,1,1,1,1,1,1,1,1,1,1,1,1},
	          {1,0,1,0,1,0,1,0,0,0,0,0,1},
	          {1,0,1,0,0,0,1,0,1,1,1,0,1},
	          {1,0,0,0,1,1,1,0,0,0,0,0,1},
	          {1,0,1,0,0,0,0,0,1,1,1,0,1},
	          {1,0,1,0,1,1,1,0,1,0,0,0,1},
	          {1,0,1,0,1,0,0,0,1,1,1,0,1},
	          {1,0,1,0,1,1,1,0,1,0,1,0,1},
	          {1,0,0,0,0,0,0,0,0,0,1,9,1},
	          {1,1,1,1,1,1,1,1,1,1,1,1,1}};
	     
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		testPath.removeAll(testPath);
	}

	@Test
	public void testFindPath() {
		Solver.findPath(testMaze, 1, 1, testPath);
		assertEquals("[11, 8, 11, 7, 11, 6, 11, 5, 11, 4, 11, 3, 10, 3, 9, 3,"
				+ " 8, 3, 7, 3, 7, 4, 7, 5, 7, 6, 7, 7, 7, 8, 6, 8, 5, 8, 4, 8,"
				+ " 3, 8, 2, 8, 1, 8, 1, 7, 1, 6, 1, 5, 1, 4, 1, 3, 1, 2, 1, 1]",
				testPath.toString());
	}	
}
