import java.util.List;
/**
 * This class solves the maze by finding the correct path
 * 
 * @author Mattias Melchior, Sanna Lundqvist
 *
 */
public class Solver {
	
	/**
	 * This is a recursive class that sends out branches of itself in every direction to determine the way to the end.
	 * 
	 * @param maze - the maze
	 * @param x - the x-coordinate
	 * @param y - the y-coordinate
	 * @param pathList - the path needed to take to reach the end
	 * @return true if a way is found, false if standing on a wall or already visited grid
	 */
	public static boolean findPath(char[][] maze, int x, int y, List<Integer> pathList) {
	
		if (maze[x][y] == '9') {  // Slutdestinationen
			pathList.add(y);
			pathList.add(x);
			
			return true;
		
			
		} else if (maze[x][y] == '0' ) {  // Icke bes�kt ruta	
			 maze[x][y] = '2';  // Nu bes�kt rutan
			 
			 int dx = 0;
			 int dy = -1;
			 if (findPath(maze, x + dx, y + dy, pathList)) {  // Kollar p� rutan ovanf�r
				 pathList.add(y);
				 pathList.add(x);
				 
				 return true;
			 }
			 
			 dx = 1;
			 dy = 0;
			 if (findPath(maze, x + dx, y + dy, pathList)) {  // Kollar p� rutan h�ger 
				 pathList.add(y);
				 pathList.add(x);
				
				 return true;
			 }
			 
			 dx = 0;
			 dy = 1;
			 if (findPath(maze, x + dx, y + dy, pathList)) {  // Kollar p� rutan under 
				 pathList.add(y);
				 pathList.add(x);
			
				 return true;
			 }
			
			 dx = -1;
			 dy = 0;
			 if (findPath(maze, x + dx, y + dy, pathList)) {  // Kollar p� rutan v�nster
				 pathList.add(y);
				 pathList.add(x);
			
				 return true;
			 }
		}
		return false;
		
	}

}