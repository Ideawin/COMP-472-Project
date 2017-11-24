package tests;

import bonzee.Board;
import naive.NaiveBoard;

public class AIAgainstNaiveHeuristic {
	
	public static final boolean IS_NAIVE_GREEN = true;
	
	public static void main (String [] args) {
		// Make a NaiveBoard and a real board
		NaiveBoard naiveBoard = new NaiveBoard();
		Board board = new Board();
		
		
		// Setup the turn
		boolean isNaiveTurn = IS_NAIVE_GREEN;
		
		// Play AI vs AI
		String move = "";
		while(!isFinal(naiveBoard) && !isFinal(board) && !checkIfConsecutiveDefensiveMoveReached(naiveBoard) && !checkIfConsecutiveDefensiveMoveReached(board)) {
			if(isNaiveTurn) {
				move = naiveBoard.playAI(IS_NAIVE_GREEN);
				System.out.println("Naive Move: " + move);
				int oldYPos  = move.charAt(0) - 'A';
				int oldXPos = move.charAt(1) - '0' - 1;
				int newYPos = move.charAt(3) - 'A';
				int newXPos = move.charAt(4) - '0' - 1;
				board.moveToken(oldYPos, oldXPos, newYPos, newXPos, IS_NAIVE_GREEN ? 'G' : 'R', false, false);
				isNaiveTurn = false;
			} else {
				move = board.playAI(!IS_NAIVE_GREEN);
				System.out.println("AI Move: " + move);
				int oldYPos  = move.charAt(0) - 'A';
				int oldXPos = move.charAt(1) - '0' - 1;
				int newYPos = move.charAt(3) - 'A';
				int newXPos = move.charAt(4) - '0' - 1;
				naiveBoard.moveToken(oldYPos, oldXPos, newYPos, newXPos, IS_NAIVE_GREEN ? 'R' : 'G', false, false);
				isNaiveTurn = true;
			}
		}
		
		// Display report
		int numberGreen = naiveBoard.getnumG();
		int numberRed = naiveBoard.getnumR();
		System.out.println("NAIVE: " + (IS_NAIVE_GREEN ? numberGreen : numberRed) + " | AI: " + (IS_NAIVE_GREEN ? numberRed : numberGreen));;
		
	}
	
	/**
	 * Method to check if game is over
	 * @return
	 */
	public static boolean isFinal(NaiveBoard bonzeeBoard) {
		boolean isFinal = true;
		// IF R TOKENS LEFT IS 0, THEN G WINS
		if (bonzeeBoard.getnumR() == 0)  
			System.out.println("Player with GREEN tokens won!!!");
		else
			//IF G TOKENS LEFT IS 0, THEN R WINSS
			if (bonzeeBoard.getnumG() == 0)  
				System.out.println("Player with RED tokens won!!!");
			else
				isFinal = false;
		return(isFinal);
	}
	
	/**
	 * Method to check if game is over
	 * @return
	 */
	public static boolean isFinal(Board bonzeeBoard) {
		boolean isFinal = true;
		// IF R TOKENS LEFT IS 0, THEN G WINS
		if (bonzeeBoard.getnumR() == 0)  
			System.out.println("Player with GREEN tokens won!!!");
		else
			//IF G TOKENS LEFT IS 0, THEN R WINSS
			if (bonzeeBoard.getnumG() == 0)  
				System.out.println("Player with RED tokens won!!!");
			else
				isFinal = false;
		return(isFinal);
	}
	
	/**
	 * Check if the max consecutive defensive moves have been reached
	 * @return true if it is reached, false otherwise
	 */
	public static boolean checkIfConsecutiveDefensiveMoveReached(Board bonzeeBoard) {
		if (bonzeeBoard.getMaxConsecutiveMoves() == 0) {
			System.out.println("10 non-attacking consecutive moves have been made. The game is DRAW.");
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the max consecutive defensive moves have been reached
	 * @return true if it is reached, false otherwise
	 */
	public static boolean checkIfConsecutiveDefensiveMoveReached(NaiveBoard bonzeeBoard) {
		if (bonzeeBoard.getMaxConsecutiveMoves() == 0) {
			System.out.println("10 non-attacking consecutive moves have been made. The game is DRAW.");
			return true;
		}
		return false;
	}
}
