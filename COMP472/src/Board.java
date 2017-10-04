
public class Board {
	final int WIDTH = 9;
	final int HEIGHT = 5;
	char[] boardArr;
	
	/**
	 * Constructor
	 */
	public Board() {
		// Create a new board
		boardArr = new char[WIDTH*HEIGHT];
		
		// Set tokens on the board
		for (int i = 0; i < boardArr.length; i++) {
			// A row
			if (i < WIDTH)
				boardArr[i] = 'R';
			// B row
			else if (i >= WIDTH && i < WIDTH*2)
			{
				boardArr[i] = 'R';
			}
			// C row
			else if (i >= WIDTH*2 && i < WIDTH*3) {
				if (i < WIDTH*2 + 4)
					boardArr[i] = 'G';
				else if (i == WIDTH*2 + 4)
					boardArr[i] = ' ';
				else
					boardArr[i] = 'R';
			}
			// D row
			else
				boardArr[i] = 'G';
		}
	}
	
	/**
	 * Method to display contents of the board
	 */
	public void displayBoard() {
		for (int i = 0; i < boardArr.length; i++)
		{
			if (i%WIDTH == 0 && i != 0) {
				System.out.println("");
			}
			System.out.print(boardArr[i] + " ");
		}
	}
	
	/**
	 * Method to set a token on the board
	 * @param x
	 * @param y
	 * @param token 'R' or 'G'
	 */
	public void moveToken(int currentPos, int oldPos, char token) {
		if (boardArr[oldPos] == ' ') {
			boardArr[oldPos] = token;
			boardArr[currentPos] = ' ';
		}
	}
	
	/**
	 * Method to verify if a tile on a board is empty
	 * @param pos
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty(int pos) {
		return boardArr[pos] == ' ';
	}
	
	/**
	 * Method to get the position index of a tile on the board
	 * @param y
	 * @param x
	 * @return
	 */
	public int getPositionIndex(char y, int x) {
		int arrayNum = -1;
		if (y >= 'A' && y <= 'E') {
			arrayNum = ((y-'A')*WIDTH) + (x-1);
		}
		return arrayNum;
	}
	
	/**
	 * Get the token at a certain position
	 * @param pos
	 * @return
	 */
	public char getTokenAtPosition(int pos) {
		return boardArr[pos];
	}
	
}
