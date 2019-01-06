import java.util.List;

public class Solver {
	
	public static boolean findPath(int[][] maze, int x, int y, List<Integer> correctPath) {
		if (maze[x][y] == 9) {  // Slutdestinationen
			correctPath.add(y);
			correctPath.add(x);
			
			return true;
		} else if (maze[x][y] == 0) {  // Icke besökt ruta	
			 maze[x][y] = 2;  // Nu besökt rutan
			 
			 int dx = 0;
			 int dy = -1;
			 if (findPath(maze, x + dx, y + dy, correctPath)) {  // Kollar på rutan ovanför
				 correctPath.add(y);
				 correctPath.add(x);
				 
				 return true;
			 }
			 
			 dx = 1;
			 dy = 0;
			 if (findPath(maze, x + dx, y + dy, correctPath)) {  // Kollar på rutan höger 
				 correctPath.add(y);
				 correctPath.add(x);
				
				 return true;
			 }
			 
			 dx = 0;
			 dy = 1;
			 if (findPath(maze, x + dx, y + dy, correctPath)) {  // Kollar på rutan under 
				 correctPath.add(y);
				 correctPath.add(x);
			
				 return true;
			 }
			
			 dx = -1;
			 dy = 0;
			 if (findPath(maze, x + dx, y + dy, correctPath)) {  // Kollar på rutan vänster
				 correctPath.add(y);
				 correctPath.add(x);
			
				 return true;
			 }
		}
		return false;
		
	}

}
