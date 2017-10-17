import java.util.List;

public class Board {
	final int WIDTH = 9;
	final int HEIGHT = 5;
	final int DEFAULT_MAX_CONSECUTIVE_PASSIVE_MOVE = 10;
	int maxConsecutiveMoves = DEFAULT_MAX_CONSECUTIVE_PASSIVE_MOVE;
	char[][] boardArr;
	int numR;
	int numG;
	
	public int getnumR()
	{
	     // Returns the numR  total token R remaining  
		return numR;
	}
	public int getnumG()
	{
	     // Returns the numG total token G remaining   
		return numG;
	}

	/**
	 * Constructor
	 */
	public Board() {
		numR = 22;
		numG = 22;
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
	 * Getter method for maxConsecutiveMoves
	 * @return
	 */
	public int getMaxConsecutiveMoves() {
		return this.maxConsecutiveMoves;
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
	public boolean moveToken(char oldY, int oldX, char newY, int newX, char tokenToMove) {
	
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
					return false;
				}
				// If next move is not adjacent
				if (direction > 2) {
					return false;
				}
				else
				{
					// Set old cell to empty
					boardArr[oldYPos][oldXPos] = ' ';
					// Set new cell to the token color
					boardArr[newYPos][newXPos] = tokenToMove;
					// Attack
					attack(newYPos,newXPos,xVector,yVector,token);
					return true;
				}
			}

			else {
				System.out.println("You cannot move the opponent's token. Try again.");
				return false;
			}
		}
		else {
			System.out.println("Cannot move this token. Try again.");
			return false;
		}
		
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
	
	/**
	 * Method to perform an attack by checking the direction of the user's move.
	 * A call to a recursive function is done to remove consecutive tokens.
	 * @param positionY
	 * @param positionX
	 * @param xVector
	 * @param yVector
	 * @param token
	 */
	public void attack(int positionY, int positionX, int xVector, int yVector, char token) {
		int i = positionX;
		int j = positionY;
		int ctr = 0;
		
		// DIAGONAL MOVES
		
		if (xVector > 0 && yVector > 0) {
			// Forward attack: Diagonal right-down
			ctr = consecutiveAttack(i+1, j+1, token, 'X', 0);
			if (ctr == 0) {
				// Backward attack: Diagonal left-up
				ctr = consecutiveAttack(i-2, j-2, token, 'Q', 0);
			}
		} 
		else if (xVector > 0 && yVector < 0) {
			// Forward attack: Diagonal right-up
			ctr = consecutiveAttack(i+1, j-1, token, 'E', 0);
			if (ctr == 0) {
				// Backward attack: Diagonal left-down
				ctr = consecutiveAttack(i-2, j+2, token, 'Z', 0);
			}
		}
		else if (xVector < 0 && yVector > 0) {
			// Forward attack: Diagonal left-down
			ctr = consecutiveAttack(i-1, j+1, token, 'Z', 0);
			if (ctr == 0) {
				// Backward attack: Diagonal right-up
				ctr = consecutiveAttack(i+2, j-2, token, 'E', 0);
			}
		}
		else if (xVector < 0 && yVector < 0) {
			// Forward attack: Diagonal left-up
			ctr = consecutiveAttack(i-1, j-1, token, 'Q', 0);
			if (ctr == 0) {
				// Backward attack: Diagonal right-down
				ctr = consecutiveAttack(i+2, j+2, token, 'X', 0);
			}
		}
		
		// HORIZONTAL/VERTICAL MOVES
		
		else if (xVector > 0 && yVector == 0) {
			// Forward attack: Right
			ctr = consecutiveAttack(i+1, j, token, 'R', 0);
			if (ctr == 0) {
				// Backward attack: Left
				ctr = consecutiveAttack(i-2, j, token, 'L', 0);
			}
		}
		else if (xVector < 0 && yVector == 0) {
			// Forward attack: Left
			ctr = consecutiveAttack(i-1, j, token, 'L', 0);
			if (ctr == 0) {
				// Backward attack: Right
				ctr = consecutiveAttack(i+2, j, token, 'R', 0);
			}
		}
		else if (xVector == 0 && yVector > 0) {
			// Forward attack: Down
			ctr = consecutiveAttack(i, j+1, token, 'D', 0);
			if (ctr == 0) {
				// Backward attack: Up
				ctr = consecutiveAttack(i, j-2, token, 'U', 0);
			}
		}
		else if (xVector == 0 && yVector < 0) {
			// Forward attack: Up
			ctr = consecutiveAttack(i, j-1, token, 'U', 0);
			if (ctr == 0) {
				// Backward attack: Down
				ctr = consecutiveAttack(i, j+2, token, 'D', 0);
			}
		}
		if (ctr == 0) {
			maxConsecutiveMoves--;
		} else {
			maxConsecutiveMoves = DEFAULT_MAX_CONSECUTIVE_PASSIVE_MOVE;
		}
	}
	
	// Recursive method
	public int consecutiveAttack(int i, int j, char token, char direction, int ctr) {
		if (i < 0 || i >= WIDTH || j < 0 || j >= HEIGHT) {
			return ctr;
		}
		if (boardArr[j][i] == ' ') {
			return ctr;
		}
		if (boardArr[j][i] == token) {
			return ctr;
		}
		if (direction == 'L') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(--i, j, token, direction, ++ctr);
		}
		else if (direction == 'R') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(++i, j, token, direction, ++ctr);
			
		}
		else if (direction == 'D') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(i,++j, token, direction, ++ctr);
		}
		else if (direction == 'U') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(i, --j, token, direction, ++ctr);
		}
		// Diagonals
		// Top-left
		else if (direction == 'Q') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(--i, --j, token, direction, ++ctr);
		}
		// Top-right
		else if (direction == 'E') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(++i, --j, token, direction, ++ctr);
		}
		// Down-left
		else if (direction == 'Z') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(--i, ++j, token, direction, ++ctr);
		}
		// Down-right
		else if (direction == 'X') {
			boardArr[j][i] = ' ';
			remainingTokens(token);
			return consecutiveAttack(++i, ++j, token, direction, ++ctr);
		}
		else
			return ctr;
	}
	
	/**
	 * Method to decrease the number of tokens
	 */
	public void remainingTokens(char token) {
		if (token == 'R') --numG;
		if (token == 'G') --numR;	
	}
}
