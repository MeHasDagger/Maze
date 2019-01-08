

import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
	private int cols;
	private int rows;
	private Integer[][] grid; // output grid
	private Cell[][] cellArray; // 2d array of Cells
	private Random random; // The random object
	private int gridxPos, gridyPos; // dimension of output grid

	public MazeGenerator(int xAndY) {
		this(xAndY, xAndY);
	}
	// constructor
	public MazeGenerator(int gridxPos, int gridyPos) {
		this.gridxPos = gridxPos;
		this.gridyPos = gridyPos;
		
	
		if (gridxPos % 2 == 0) {
			gridxPos = gridxPos + 1;
			gridyPos = gridyPos + 1;
			
		}
		System.out.println("xpos: " + gridxPos + " ypos: " + gridyPos);
		
		
		

		cols = (int) Math.floor(gridxPos / 2);
		rows = (int) Math.floor(gridyPos / 2);
		System.out.println("cols: " + cols + "rows: " + rows);
		
		random = new Random();

		
		
		grid = new Integer[gridxPos][gridyPos];
		createCellArray();
		generateMaze();
	}

	private void createCellArray() {
		// create cells
		cellArray = new Cell[cols][rows];
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				cellArray[x][y] = new Cell(x, y, false); // create cell (see Cell constructor)
			}
		}
	}

	// inner class to represent a cell
	private class Cell {
		int x, y; // coordinates
		// cells this cell is connected to
		ArrayList<Cell> neighbors = new ArrayList<>();
		// if true, has yet to be used in generation
		boolean avalible = true;
		// construct Cell at x, y
		Cell(int x, int y) {
			this(x, y, true);
		}
		// construct Cell at x, y and with whether it isWall
		Cell(int x, int y, boolean isWall) {
			this.x = x;
			this.y = y;
		}
		// add a neighbor to this cell, and this cell as a neighbor to the other
		void addNeighbor(Cell other) {
			if (!this.neighbors.contains(other)) { // avoid duplicates
				this.neighbors.add(other);
			}
			if (!other.neighbors.contains(this)) { // avoid duplicates
				other.neighbors.add(this);
			}
		}
		// used in updateGrid()
		boolean isCellBelowNeighbor() {
			return this.neighbors.contains(new Cell(this.x, this.y + 1));
		}
		// used in updateGrid()
		boolean isCellRightNeighbor() {
			return this.neighbors.contains(new Cell(this.x + 1, this.y));
		}
		// useful Cell representation
		public String toString() {
			return String.format("Cell(%s, %s)", x, y);
		}
		// useful Cell equivalence
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Cell)) return false;
			Cell otherCell = (Cell) other;
			return (this.x == otherCell.x && this.y == otherCell.y);
		}
		// should be overridden with equals
		@Override
		public int hashCode() {
			// random hash code method designed to be usually unique
			return this.x + this.y * 256;
		}
	}
	// generate from upper left (In computing the y increases down often)
	private void generateMaze() {
		generateMaze(0, 0);
	}
	// generate the maze from coordinates x, y
	private void generateMaze(int x, int y) {
		generateMaze(getCell(x, y)); // generate from Cell
	}
	private void generateMaze(Cell startAt) {
		// don't generate from cell not there
		if (startAt == null) return;
		startAt.avalible = false; // indicate cell closed for generation
		ArrayList<Cell> cells = new ArrayList<>();
		cells.add(startAt);

		while (!cells.isEmpty()) {
			Cell cell;
			// this is to reduce but not completely eliminate the number
			//   of long twisting halls with short easy to detect branches
			//   which results in easy mazes
			if (random.nextInt(2) == 0)
				cell = cells.remove(random.nextInt(cells.size()));
			else 
				cell = cells.remove(cells.size() - 1);
			// for collection
			ArrayList<Cell> neighbors = new ArrayList<>();
			// cells that could potentially be neighbors
			Cell[] potentialNeighbors = new Cell[]{
					getCell(cell.x + 1, cell.y),
					getCell(cell.x, cell.y + 1),
					getCell(cell.x - 1, cell.y),
					getCell(cell.x, cell.y - 1)
			};
			for (Cell other : potentialNeighbors) {
				// jag tog bort || other.wall
				if (other == null || !other.avalible) continue;
				neighbors.add(other);
			}
			if (neighbors.isEmpty()) continue;
			// get random cell
			Cell selected = neighbors.get(random.nextInt(neighbors.size()));
			// add as neighbor
			selected.avalible = false; // indicate cell closed for generation
			cell.addNeighbor(selected);
			cells.add(cell);
			cells.add(selected);
		}
	}
	// used to get a Cell at x, y; returns null out of bounds
	public Cell getCell(int x, int y) {
		try {
			return cellArray[x][y];
		} catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
			return null;
		}
	}
	
	// draw the maze
		public void generateGrid() {
			Integer wall = 1, hallway = 0, target = 9;
			
			// build walls
			for (int x = 0; x < gridxPos; x ++) {
				for (int y = 0; y < gridyPos; y ++) {
					//if (x % 4 == 0 || y % 2 == 0)
						grid[x][y] = wall;
				}
			}
			// make meaningful representation
			for (int x = 0; x < cols; x++) {
				for (int y = 0; y < rows; y++) {
					Cell current = getCell(x, y);
					//int gridX = x * 4 + 2, gridY = y * 2 + 1;
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
							//grid[gridX + 3][gridY] = cellChar;
						}
					}
					
					
				}
			}
		}
	public Integer[][] getMazeArray(){
		generateGrid();
		return grid;
	}

	// simply prints the map
	public void draw() {
		System.out.print(this);
	}
	// forms a meaningful representation
	
	public String toString() {
		//updateGrid();
		generateGrid();
		String output = "";
		for (int y = 0; y < gridyPos; y++) {
			for (int x = 0; x < gridxPos; x++) {
				output += grid[x][y];
			}
			output += "\n";
		}
		return output;
	}

	// run it
	
	public static void main(String[] args) {
		MazeGenerator maze = new MazeGenerator(8);
		maze.draw();
	}
	
}