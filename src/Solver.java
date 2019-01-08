import java.util.List;
/**
 * This class solves the maze by finding the correct path with depth first search.
 * 
 * @author Mattias Melchior, Sanna Lundqvist
 *
 */
public class Solver {
	
	/**
	 * This is a recursive class that sends out branches of itself in every direction to determine the way to the end.
	 * 
	 * @param maze - the maze
	 * @param x - the start x-coordinate
	 * @param y - the start y-coordinate
	 * @param pathList - the path needed to take to reach the end
	 * @return true if a way is found, false if standing on a wall or already visited grid
	 */
	public static boolean findPath(Integer[][] maze, int x, int y, List<Integer> pathList) {
		if (maze[x][y] == 9) {  // Slutdestinationen
			pathList.add(y);
			pathList.add(x);
			
			return true;
		} else if (maze[x][y] == 0 ) {  // Icke besökt ruta	
			 maze[x][y] = 2;  // Nu besökt rutan
			 
			 int deltax = 0;
			 int deltay = -1;
			 if (findPath(maze, x + deltax, y + deltay, pathList)) {  // Inspects the cell above
				 pathList.add(y);
				 pathList.add(x);
				 
				 return true;
			 }
			 
			 deltax = 1;
			 deltay = 0;
			 if (findPath(maze, x + deltax, y + deltay, pathList)) {  // Inspects the cell to the right 
				 pathList.add(y);
				 pathList.add(x);
				
				 return true;
			 }
			 
			 deltax = 0;
			 deltay = 1;
			 if (findPath(maze, x + deltax, y + deltay, pathList)) {  // Inspects the cell below
				 pathList.add(y);
				 pathList.add(x);
			
				 return true;
			 }
			
			 deltax = -1;
			 deltay = 0;
			 if (findPath(maze, x + deltax, y + deltay, pathList)) {  // Inspects the cell to the left
				 pathList.add(y);
				 pathList.add(x);
			
				 return true;
			 }
		}
		return false;	
	}
}