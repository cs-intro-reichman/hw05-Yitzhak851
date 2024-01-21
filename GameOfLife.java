public class GameOfLife {

	public static void main(String[] args) {
		String fileName = args[0]; // Gets the fileName from the command line.
		// System.out.println("=================Test 1 V ====================");
		test1(fileName); // Get file ==> initial board from the file ==> print
		// System.out.println("==================Test 2 V ===================");
		test2(fileName); // Get file ==> initial board from the file ==> print ==> count ==> cellValue
		// System.out.println("==================Test 3 V ===================");
		test3(fileName, 3);
		// play(fileName);
	}

	// Reads the data file and prints the initial board.
	public static void test1(String fileName) {
		int[][] board = read(fileName);
		print(board);
	}

	// Reads the data file => run test that checks count && cellValue fofo
	public static void test2(String fileName) {
		int[][] board = read(fileName);
		// TODO : Write here code that tests the count fofo
		System.out.println("the count of alive neighbors: " + count(board, 2, 2));
		// TODO : Write here code that tests the cellValue fofo
		System.out.println("cellValue at next generation: " + cellValue(board, 2, 2));
	}

	// Reads the data file, plays Ngen, print board at beginning of each gen.
	public static void test3(String fileName, int Ngen) {
		int[][] board = read(fileName);
		for (int gen = 0; gen < Ngen; gen++) {
			System.out.println("Generation " + gen + ":");
			print(board);
			board = evolve(board);
		}
	}

	// Reads the data file and plays the game, for ever.
	public static void play(String fileName) {
		int[][] board = read(fileName);
		while (true) {
			show(board);
			board = evolve(board);
		}
	}

	// Reads the data file and returns the initial board.
	public static int[][] read(String fileName) {
		In in = new In(fileName);
		int rows = Integer.parseInt(in.readLine());
		int cols = Integer.parseInt(in.readLine());
		int[][] board = new int[rows + 2][cols + 2]; // Create array with 2 extra rows and columns
		// TODO : Write here code that reads the data file and initializes the board.
		boolean end2ReadFile = false;
		String readNextline = "";
		int startRow = 1;
		while (!end2ReadFile) {
			readNextline = in.readLine(); // line = Read the next line
			for (int i = 0; i < readNextline.length(); i++) {
				if (readNextline.charAt(i) == 'x') {
					board[startRow][i + 1] = 1; // i+1 its because we have 2 extra rows and columns
				}
			}
			end2ReadFile = in.isEmpty(); // end2Read "blabla.dot" ? end2ReadFile = true : false ;
			startRow++; // increment the row
		}
		return board;
	}

	// Creates a new board from the given board, using the rules of the game.
	public static int[][] evolve(int[][] board) {
		// Creates a new board from the given board, using the rules of the game.
		int[][] newBoard = new int[board.length][board[0].length];
		// TODO : Replace the following statement with your code.
		for (int i = 1; i < board.length - 1; i++) { // i = row
			for (int j = 1; j < board[0].length - 1; j++) { // j = col
				newBoard[i][j] = cellValue(board, i, j);
			}
		}
		return newBoard;
	}

	// Returns the value that cell (i,j) should have in the next generation.
	public static int cellValue(int[][] board, int i, int j) {
		// TODO : Replace the following statement with your code.
		int neighbors = count(board, i, j);
		int cellValue = board[i][j];

		if (cellValue == 1) {
			// cell isAlive and has less than 2 live neighbors, it dies.
			// cell isAlive and has more than 3 live neighbors, it dies.
			// cell isAlive and has 2 or 3 live neighbors, it remains alive.
			if (neighbors == 2 || neighbors == 3) {
				return 1;
			} else {
				return 0;
			}
		} else {
			// cell isDead and has 3 live neighbors, it becomes alive.
			if (neighbors == 3) {
				return 1;
			}
		}
		// Otherwise the cell does not change.
		return 0;
	}

	// Counts and returns the number of living neighbors of the given cell.
	public static int count(int[][] board, int i, int j) {
		// TODO : Replace the following statement with your code.
		int count = 0;
		for (int row = i - 1; row <= i + 1; row++) {
			for (int col = j - 1; col <= j + 1; col++) {
				if (board[row][col] == 1 && !(row == i && col == j)) {
					count++;
				}
			}
		}
		return count;
	}

	// Prints the board. Alive and dead cells are printed as 1 and 0, respectively.
	public static void print(int[][] arr) {
		int rows = arr.length; // Does not include the frame rows.
		int cols = arr[0].length; // Does not include the frame columns.
		// Prints the number of rows and columns.
		for (int i = 1; i < rows - 1; i++) {
			for (int j = 1; j < cols - 1; j++) {
				System.out.printf("%3s", arr[i][j]);
			}
			System.out.println();
		}
	}

	// Displays the board. Living and dead cells are represented by black and white
	// squares, respectively.
	// We use a fixed-size canvas of 900 pixels by 900 pixels for displaying game
	// boards of different sizes.
	// In order to handle any given board size, we scale the X and Y dimensions
	// according to the board size.
	// This results in the following visual effect: The smaller the board, the
	// larger the squares
	// representing cells.
	public static void show(int[][] board) {
		StdDraw.setCanvasSize(900, 900);
		int rows = board.length;
		int cols = board[0].length;
		StdDraw.setXscale(0, cols);
		StdDraw.setYscale(0, rows);

		// Enables drawing graphics in memory and showing it
		// on the screen only when the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();

		// For each cell (i,j), draws a filled square of size 1 by 1
		// remember that the canvas was already scaled to the dimensions rows by cols,
		// which were read from the data file.
		// Uses i and j to calculate the (x,y) location of the square's center, i.e.
		// where it will be drawn in the overall canvas.
		// If the cell contains 1, sets the square's color to black;
		// otherwise, sets it to white.
		// In the RGB (Red-Green-Blue) color scheme used by StdDraw,
		// the RGB codes of black and white are, respetively, (0,0,0) and (255,255,255).
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int color = 255 * (1 - board[i][j]);
				StdDraw.setPenColor(color, color, color);
				StdDraw.filledRectangle(j + 0.5, rows - i - 0.5, 0.5, 0.5);
			}
		}
		StdDraw.show();
		StdDraw.pause(100);
	}
}
