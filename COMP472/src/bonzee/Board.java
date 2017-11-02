package bonzee;
/**
 * Class that represents the game board
 *
 */
public class Board {

	public static final int WIDTH = 9;
	public static final int HEIGHT = 5;
	public static final int MINIMAX_MAX_LEVEL_LOOKUP = 3;
	final int DEFAULT_MAX_CONSECUTIVE_PASSIVE_MOVE = 10;
	final int MAX_NUMBER_OF_TOKENS_PER_PLAYER = 22;

	int maxConsecutiveMoves;
	char[][] boardArr;
	int numR;
	int numG;
	
	/**
	 * Get the height
	 */
	public int getHeight() {
		return HEIGHT;
	}
	
	/**
	 * Get the width
	 * @return
	 */
	public int getWidth() {
		return WIDTH;
	}

	/**
	 * Set the boardArr
	 */
	public void setBoardArr(char[][] boardArr) {
		this.boardArr = boardArr;
	}
	
	/**
	 * Get the boardArr
	 */
	public char[][] getBoardArr() {
		return boardArr;
	}
	
	/**
	 * Method to get the number of RED tokens currently on the board
	 * @return an integer representing the number of RED tokens
	 */
	public int getnumR()
	{
		return numR;
	}

	/**
	 * Method to get the number of GREEN tokens currently on the board
	 * @return an integer representing the number of GREEN tokens
	 */
	public int getnumG()
	{ 
		return numG;
	}

	/**
	 * Constructor
	 */
	public Board() {

		// Initialize number of tokens
		numR = MAX_NUMBER_OF_TOKENS_PER_PLAYER;
		numG = MAX_NUMBER_OF_TOKENS_PER_PLAYER;

		// Initialize max number of consecutive moves for the game
		maxConsecutiveMoves = DEFAULT_MAX_CONSECUTIVE_PASSIVE_MOVE;

		// Create a new board
		boardArr = new char[HEIGHT][WIDTH];

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
	 * @return an integer value representing the max number of consecutive moves
	 */
	public int getMaxConsecutiveMoves() {
		return this.maxConsecutiveMoves;
	}

	/**
	 * Method to display contents of the board
	 */
	public void displayBoard() {
		System.out.println("\t|   1\t|   2\t|   3\t|   4\t|   5\t|   6\t|   7\t|   8\t|   9\t|");
		System.out.println("---------------------------------------------------------------------------------");
		for (int i = 0; i < HEIGHT; i++) {
			System.out.print(((char)(i + 'A')) + "\t");
			for (int j = 0; j < WIDTH; j++)  
				if(this.blackCell(j,  i)) 
					System.out.print("|   " + boardArr[i][j] + "*\t"); // \t
				else 
					System.out.print("|   " + boardArr[i][j] + "\t"); // \t
			System.out.println("|");
			System.out.println("---------------------------------------------------------------------------------");
		}
	}

	/**
	 * Method to set a token on the board
	 * @param x
	 * @param y
	 * @param token 'R' or 'G'
	 * @param checkValidityForMinMax True if it is used in MiniMax to check if a move is valid
	 * */
	public boolean moveToken(int oldYPos, int oldXPos, int newYPos, int newXPos, char tokenToMove, boolean checkValidityForMinMax) {

		if (isEmpty(newXPos, newYPos)) {

			char token = getTokenAtPosition(oldYPos,oldXPos);
			if (token == tokenToMove) {
				int xVector = newXPos - oldXPos;
				int yVector =  newYPos - oldYPos;
				int direction = Math.abs(xVector) + Math.abs(yVector);
				boolean isBlack = blackCell(oldXPos,oldYPos);

				// If next move is not horizontal/vertical and is white
				if (direction > 1 && !isBlack) {
					System.out.println("Invalid Move: Cannot move diagonally on white cases. Try Again!!");
					return false;
				}
				// If next move is not adjacent
				if (Math.abs(xVector) > 1 || Math.abs(yVector) > 1) {
					System.out.println("Invalid Move: The next position is not adjacent to the current token. Try Again!!");
					return false;
				}
				else
				{
					if (!checkValidityForMinMax) {
						// Set old cell to empty
						boardArr[oldYPos][oldXPos] = ' ';
						// Set new cell to the token color
						boardArr[newYPos][newXPos] = tokenToMove;
						// Attack
						attack(newYPos,newXPos,xVector,yVector,token);
					}
					return true;
				}
			}
			else {
				if (token == ' ') {
					System.out.println("Invalid Move: There is no token to move at this position. Try again!!");
				}
				else
					System.out.println("Invalid Move: Cannot move the opponent's token. Try again!!");
				return false;
			}
		}
		else {
			System.out.println("Invalid Move: This position is already taken. Try again!!");
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
	public char getTokenAtPosition(int y, int x) {
		return boardArr[y][x];
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
			System.out.println("No attack has been made in this turn.");
			System.out.println("NUMBER OF CONSECUTIVE MOVES LEFT: " + maxConsecutiveMoves );
		} else {
			maxConsecutiveMoves = DEFAULT_MAX_CONSECUTIVE_PASSIVE_MOVE;
			System.out.println("Opponent has been attacked! " + ctr + " token(s) were removed.");
		}
	}

	/**
	 * Method to perform attack recursively on one token or more
	 * @param i Y position
	 * @param j X position
	 * @param token current player's token color
	 * @param direction
	 * @param ctr
	 * @return
	 */
	public int consecutiveAttack(int i, int j, char token, char direction, int ctr) {

		// If recursion reaches outside board boundaries, return
		if (i < 0 || i >= WIDTH || j < 0 || j >= HEIGHT) {
			return ctr;
		}
		// If recursion reaches an empty case, return
		if (boardArr[j][i] == ' ') {
			return ctr;
		}
		// If recursion reaches one of the player's own token, return
		if (boardArr[j][i] == token) {
			return ctr;
		}

		// HORIZONTAL/VERTICAL MOVES
		// Left
		if (direction == 'L') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(--i, j, token, direction, ++ctr);
		}
		// Right
		else if (direction == 'R') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(++i, j, token, direction, ++ctr);

		}
		// Down
		else if (direction == 'D') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(i,++j, token, direction, ++ctr);
		}
		// Up
		else if (direction == 'U') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(i, --j, token, direction, ++ctr);
		}

		// DIAGONAL MOVES
		// Top-left
		else if (direction == 'Q') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(--i, --j, token, direction, ++ctr);
		}
		// Top-right
		else if (direction == 'E') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(++i, --j, token, direction, ++ctr);
		}
		// Down-left
		else if (direction == 'Z') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(--i, ++j, token, direction, ++ctr);
		}
		// Down-right
		else if (direction == 'X') {
			boardArr[j][i] = ' ';
			decrementTokenCount(token);
			return consecutiveAttack(++i, ++j, token, direction, ++ctr);
		}
		else
			return ctr;
	}

	/**
	 * Method to decrease the number of tokens
	 * @param token the current player's token
	 */
	public void decrementTokenCount(char token) {
		if (token == 'R') --numG;
		if (token == 'G') --numR;	
	}
	
	/**
	 * Method to make the AI play the next move
	 * @param isGreen indicates if the token color of the AI is green, false if red
	 */
	public void playAI(boolean isGreen) {
		MiniMax miniMax = new MiniMax();
		miniMax.makeTree(MINIMAX_MAX_LEVEL_LOOKUP, isGreen, boardArr.clone());
		
	}
}
