
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

		// Black and white positions

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
		System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8\t9");
		for (int i = 0; i < HEIGHT; i++) {
			System.out.print(((char)(i + 'A')) + "\t");
			for (int j = 0; j < WIDTH; j++)  
				System.out.print(boardArr[i][j] + "\t"); // \t
			System.out.println();
		}
	}

	/**
	 * Method to set a token on the board
	 * @param x
	 * @param y
	 * @param token 'R' or 'G'
	 */
	public void moveToken(char oldY, int oldX, char newY, int newX, char tokenToMove) {
	
		// Convert positions into index
		int oldYPos = oldY - 'A';
		int oldXPos = oldX - 1;
		int newYPos = newY - 'A';
		int newXPos = newX - 1;

		if (isEmpty(newXPos, newYPos)) {
			// Check if it's your turn
			char token = getTokenAtPosition(oldY,oldX);
			if (token == tokenToMove) {
				int xVector = newX - oldX;
				int yVector =  newY - oldY;
				int direction = Math.abs(xVector) + Math.abs(yVector);
				boolean isBlack = blackCell(oldXPos,oldYPos);
				// If next move is not horizontal/vertical and is white
				if (direction > 1 && !isBlack) {
					return;
				}
				// If next move is not adjacent
				if (direction > 2) {
					return;
				}
				else
				{
					// Set old cell to empty
					boardArr[oldYPos][oldXPos] = ' ';
					// Set new cell to the token color
					boardArr[newYPos][newXPos] = tokenToMove;
					// Attack
				}
			}

			// Check if you can attack and how many tokens can be killed
			else
				System.out.println("You cannot move this token. >:(");
		}
		else
			System.out.println("This cell is not empty!!!");
		
	}

	/**
	 * Method to verify if a cell is black or white
	 * @param positionX  positionY
	 * @return true if cell is black , false is cell is white
	 */
	public boolean blackCell(int positionX, int positionY) {
		if ((positionX%2==0) && (positionY%2==0))
			return true;  //black cell
		else if ((positionX%2!=0) && (positionY%2!=0))
			return true;  //black cell
		else
			return false;   //white cell
	}
	/**
	 * * Method to verify if a tile on a board is empty
	 * @param row index
	 * @param col index
	 * @return true if empty, false otherwise

	 */
	public boolean isEmpty(int positionX, int positionY) {
		return boardArr[positionY][positionX] == ' ';
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
	
	public void attack(int xVector, int yVector, char token) {
		
	}
}
