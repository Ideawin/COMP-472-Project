
public class Board {
	final int WIDTH = 9;
	final int HEIGHT = 5;
	int maxConsecutiveMoves = 10;
	char[][] boardArr;
		
	/**
	 * Constructor
	 */
	public Board() {
		// Create a new board
		boardArr = new char[HEIGHT][WIDTH];
		
		
		//black and white positions
		
		
		/*
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if ((i%2==0) && (j%2==0)) boardArr[i] [j] = 'B';
				else if ((i%2!=0) && (j%2!=0)) boardArr[i] [j] = 'B';
				else  boardArr[i] [j] = 'W';
			}
		} 
	 */
			
		
		// Set tokens on the board
		
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (i == 0 || i == 1)
					boardArr[i] [j] = 'R';
				else if (i == 3 || i == 4)
					boardArr[i] [j] = 'G';
				else if (i == 2 && j < 4)
					boardArr[i] [j] = 'G';
				else if (i == 2 && j > 4)
					boardArr[i] [j] = 'R';
				else boardArr[i] [j] = ' ';
			}
		}
	}

	
	/**
	 * Method to display contents of the board
	 */
	public void displayBoard() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++)  
				System.out.print(boardArr[i][j] + " "); // \t
			System.out.println();
		}
	}
	
	/**
	 * Method to set a token on the board
	 * @param x
	 * @param y
	 * @param token 'R' or 'G'
	 */
	/*public void moveToken(int newPos, int oldPos, char token) {
		// Check if it's your turn
		// Check if even or odd
		// Check direction of new position
		if (boardArr[oldPos] == ' ') {
			boardArr[oldPos] = token;
			boardArr[newPos] = ' ';
		}
		// Check if you can attack and how many tokens can be killed
	}*/
	
	/**
	 * Method to verify if a cell is black or white
	 * @param positionX  positionY
	 * @return true if cell is black , false is cell is white
	 */
	public boolean blackCell(int positionX, int positionY) {
		 	
				if ((positionX%2==0) && (positionY%2==0)) return true;  //black cell
				else if ((positionX%2!=0) && (positionY%2!=0)) return true;  //black cell
				else  return false;   //white cell
	}
	/**
	 * * Method to verify if a tile on a board is empty
	 * @param row index
	 * @param col index
	 * @return true if empty, false otherwise
	 
	 */
	public boolean isEmpty(int row, int col) {
		return boardArr[row][col] == ' ';
	}
	
	
	
	/**
	 * Get the token at a certain position
	 * @param pos
	 * @return
	 */
	public char getTokenAtPosition(char y, int x) {
		int yPos = y - 'A';
		int xPos = x - 1;
		return boardArr[yPos][xPos];
	}

}
