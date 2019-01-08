

import java.util.ArrayList;
import java.util.Random;

/**
 * This class generates a maze using recursive backtracker.
 * 
 * @author Mattias Melchior, Sanna Lundqvist
 *
 */

public class MazeGenerator {
	private int cols;
	private int rows;
	private Integer[][] grid; // output grid
	private Cell[][] cellArray; // 2d array of Cells
	private Random random; // The random object
	private int gridxPos, gridyPos; // dimension of output grid

	/**
	 * Contructor for equal sized width and height
	 * The Maze will scale up to about the double size due to the walls
	 * @param xAndY is the size of the maze
	 */
	public MazeGenerator(int xAndY) {
		this(xAndY, xAndY);
	}

	/**
	 * Constructor for different sized width and height
	 * The Maze will scale up to about the double size due to the walls
	 * @param cols is the number of columns of the maze - except walls
	 * @param rows is the number of rows of the maze - except walls
	 */
	public MazeGenerator(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;

		gridxPos = cols * 2 + 1;
		gridyPos = rows * 2 + 1;

		random = new Random();

		grid = new Integer[gridxPos][gridyPos];
		createCellArray();
		generateMaze();
	}

	/**
	 * Creates the array in witch the maze can be generated
	 * No walls here
	 */
	private void createCellArray() {
		cellArray = new Cell[cols][rows];
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				cellArray[x][y] = new Cell(x, y, false); // create cell (see Cell constructor)
			}
		}
	}

	/**
	 * A cell keeps track of its neighbors, coordinates in the array
	 * and if it has been used already for generation
	 * @author Mattias Melchior, Sanna Lundqvist
	 *
	 */
	private class Cell {
		int x, y; 
		ArrayList<Cell> neighbors = new ArrayList<>();
		boolean avalible = true;
		/**
		 * Constructor with the cells coordinates
		 * This cell is considerd as a wall
		 * @param x the coordinate in the columns
		 * @param y the coordinate in the rows
		 */
		Cell(int x, int y) {
			this(x, y, true);
		}
		/**
		 * Constructor with the cells coordinates and if it is a wall
		 * @param x the coordinate in the columns
		 * @param y the coordinate in the rows
		 * @param isWall if the cell is a wall or not
		 */
		Cell(int x, int y, boolean isWall) {
			this.x = x;
			this.y = y;
		}
		/**
		 * Adds a cell as neighbor isn't already
		 * @param other the cell to be added as neighbor
		 */
		void addNeighbor(Cell other) {
			if (!this.neighbors.contains(other)) { 
				this.neighbors.add(other);
			}
			if (!other.neighbors.contains(this)) { 
				other.neighbors.add(this);
			}
		}
		/**
		 * Checks if the cell below is a neighbor
		 * @return true if it is a neighbor
		 */
		boolean isCellBelowNeighbor() {
			return this.neighbors.contains(new Cell(this.x, this.y + 1));
		}
		/**
		 * Checks if the cell to the right is a neighbor
		 * @return true if it is a neighbor
		 */
		boolean isCellRightNeighbor() {
			return this.neighbors.contains(new Cell(this.x + 1, this.y));
		}
		/**
		 * Prints the cell and its coordinates
		 */
		@Override
		public String toString() {
			return String.format("Cell(%s, %s)", x, y);
		}
		/**
		 * Checks it two cells are equal to each other
		 * @param other is the object for comparison
		 */
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Cell)) return false;
			Cell otherCell = (Cell) other;
			return (this.x == otherCell.x && this.y == otherCell.y);
		}

	}
	/**
	 * Generates a maze with start position (0,0)
	 */
	private void generateMaze() {
		generateMaze(0, 0);
	}
	/**
	 * Generates a maze with given start position
	 * Uses depth first
	 * @param x the start position in width
	 * @param y the start position in height
	 */
	private void generateMaze(int x, int y) {
		generateMaze(getCell(x, y));
	}
	/**
	 * Generates a maze with given start cell
	 * Uses depth first
	 * @param startAt the cell at start position
	 */
	private void generateMaze(Cell startAt) {
		if (startAt == null) return;
		startAt.avalible = false;
		ArrayList<Cell> cells = new ArrayList<>();
		cells.add(startAt);

		while (!cells.isEmpty()) {
			Cell cell;
			if (random.nextInt(10)==0)
				cell = cells.remove(random.nextInt(cells.size()));
			else 
				cell = cells.remove(cells.size() - 1);

			ArrayList<Cell> neighbors = new ArrayList<>();

			Cell[] potentialNeighbors = new Cell[]{
					getCell(cell.x + 1, cell.y),
					getCell(cell.x, cell.y + 1),
					getCell(cell.x - 1, cell.y),
					getCell(cell.x, cell.y - 1)
			};
			for (Cell other : potentialNeighbors) {
				if (other==null || !other.avalible) continue;
				neighbors.add(other);
			}
			if (neighbors.isEmpty()) continue;

			Cell selected = neighbors.get(random.nextInt(neighbors.size()));
			selected.avalible = false;
			cell.addNeighbor(selected);
			cells.add(cell);
			cells.add(selected);
		}
	}
	/**
	 * Returns the cell at the given position, if non existing or
	 * out of bounds it returns null
	 * @param x the x position of the cell
	 * @param y the y position of the cell
	 * @return the cell at the given position, if non existing or
	 * out of bounds it returns null
	 */
	private Cell getCell(int x, int y) {
		try {
			return cellArray[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Generates a grid containing 1 for walls and
	 * 0 for path from the path from the cell array
	 */
	private void generateGrid() {
		Integer wall = 1, hallway = 0, target = 9;
		
		for (int x = 0; x < gridxPos; x ++) {
			for (int y = 0; y < gridyPos; y ++) {
					grid[x][y] = wall;
			}
		}
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				Cell current = getCell(x, y);
				int gridX = x * 2 + 1, gridY = y * 2 + 1;

				if (x == cols - 1 && y == rows - 1) {
					grid[gridX][gridY] = target;
				} else {
					grid[gridX][gridY] = hallway;
					if (current.isCellBelowNeighbor()) {
						grid[gridX][gridY + 1] = hallway;
					}
					if (current.isCellRightNeighbor()) {
						grid[gridX + 2][gridY] = hallway;
						grid[gridX + 1][gridY] = hallway;
					}
				}
				
				
			}
		}
	}
	/**
	 * Generates an array with walls represented as 1 and path as 0
	 * @return an Integer[][] representing the maze
	 */
	public Integer[][] getMazeArray(){
		generateGrid();
		return grid;
	}
}