package bonzee;
import java.util.Scanner;

public class Play {
	
	private static Board bonzeeBoard;
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		bonzeeBoard = new Board();
		startGame();
	}
	
	/**
	 * Method to start the game
	 */
	public static void startGame() {
		Scanner scanner = new Scanner(System.in);
		boolean tokenG = true;
		boolean playAgainstAI = true;
		boolean isAIGreen = true;
		boolean moved = false;
		
		// -----------------------------------------------------------------------
		// Ask the user for the game mode (AI or Player)
		// -----------------------------------------------------------------------
		System.out.println("Do you want to play against another player or against an AI? ('P' for player, 'AI' for AI)");
		String playAgainstAIString = "";
		do {
			playAgainstAIString = scanner.nextLine();
			if(playAgainstAIString.equalsIgnoreCase("P")) {
				playAgainstAI = false;
			} else if (!playAgainstAIString.equalsIgnoreCase("AI")) {
				System.out.println("Wrong option. Please choose between 'P' for player and 'AI' for AI");
			}
		} while (!playAgainstAIString.equalsIgnoreCase("P") && !playAgainstAIString.equalsIgnoreCase("AI"));
		
		// If we play against AI, pick at random who is which color
		if(playAgainstAI) {
			System.out.println("Do you want to be green or red? (G for green, R for red)");
			String userColor = "";
			do {
				userColor = scanner.nextLine();
				if(userColor.equalsIgnoreCase("G")) {
					isAIGreen = false;
				} else if (!userColor.equalsIgnoreCase("R")) {
					System.out.println("Wrong option. Please choose between 'G' for green or 'R' for red");
				}
			} while (!userColor.equalsIgnoreCase("G") && !userColor.equalsIgnoreCase("R"));
		}
		
		// -----------------------------------------------------------------------
		// Display the board then the rules
		// -----------------------------------------------------------------------
		bonzeeBoard.displayBoard();
		System.out.println("Rules:\n 1. Input your next move in the following format:\n" + " \tA2,B2 where A2 is the token you want to move, and B2 is its final position, and where A is the Y-direction and 2 is the X-direction\n");
		System.out.println(" 2. Cases representation:");
		System.out.println("\t"+"a." + "|*|" + " Represents a black-case. " + "This means that you are allowed to move the token of one position in the following directions:");
		System.out.println( "\t\t Up, Down, Left, Right, Diagonaly");
		System.out.println("\t"+"b." + "Cases not indicated by a" +  " |*| "+ " mean that you are only allowed to move the token of one position in the following directions:");
		System.out.println("\t\t Up, Down, Left, Right.\n");

		System.out.println("Start playing!\n");

		// -----------------------------------------------------------------------
		// Game Loop
		// -----------------------------------------------------------------------
		while(bonzeeBoard.getMaxConsecutiveMoves() > 0) {
			System.out.print("It's ");
			System.out.print(tokenG ? "Green's " : "Red's ");
			System.out.println("turn.");

			// READ INPUT
			// Check if it is AI's turn to play
			if(playAgainstAI && ((isAIGreen && tokenG) || (!isAIGreen && !tokenG))) {
				if(isAIGreen && tokenG) {
					// AI is green and it's green's turn
					bonzeeBoard.playAI(true);
				} else if (!isAIGreen && !tokenG) {
					// AI is red and it's red's turn 
					bonzeeBoard.playAI(false);					
				}
				
				bonzeeBoard.displayBoard();
				
				// Check if the game is over
				if(isFinal()) {
					break;
				} else if (checkIfConsecutiveDefensiveMoveReached()) {
					break;
				} else {
					tokenG = !tokenG;
					continue;
				}
			}
			
			String input = scanner.nextLine();
			input = input.toUpperCase();

			// VALIDATE INPUT
			if(input.length() != 5) {
				System.out.println("Incorrect input format: length of the input should be 5");
				continue;
			} else {
				String[] tokenPositions = input.split(",");
				if(tokenPositions.length != 2) {
					// Wrong: A,B,C OR 123,
					System.out.println("Incorrect input format: there should be only two token positions delimited by a single comma");
					continue;
				} else {
					if(tokenPositions[0].length() != 2 || tokenPositions[1].length() != 2) {
						// Wrong: A,B22
						System.out.println("Incorrect input format: each token position should have two characters");
						continue;
					} else {
						// A2,BP
						char initialY = tokenPositions[0].charAt(0);
						char finalY = tokenPositions[1].charAt(0);
						if(initialY < 'A' || initialY > 'E' || finalY < 'A' || finalY > 'E') {
							System.out.println("The Y-position should be between A and E");
							continue;
						}

						int initialX = -1;
						int finalX = -1;

						try {
							initialX = Integer.parseInt(tokenPositions[0].substring(1));
							finalX = Integer.parseInt(tokenPositions[1].substring(1));
							if(initialX < 1 || initialX > 9 || finalX < 1 || finalX > 9) {
								System.out.println("The X-position is wrong, it should be between 1 and 9.");
								continue;
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
							System.out.println("The X-position should be a digit.");
							continue;
						}

						// Call the game
						if(initialX != -1 && finalX != -1) {
							// Convert positions into index
							int oldYPos = initialY - 'A';
							int oldXPos = initialX - 1;
							int newYPos = finalY - 'A';
							int newXPos = finalX - 1;
							moved = bonzeeBoard.moveToken(oldYPos, oldXPos, newYPos, newXPos, tokenG ? 'G' : 'R',false,false);
							if (moved){
								bonzeeBoard.displayBoard();
								if (isFinal()) break;
							}
							else
								continue;
						}
					}
				}
			}

			// Switch turn
			tokenG = !tokenG;
			if(checkIfConsecutiveDefensiveMoveReached()) {
				break;
			}
		}
		scanner.close();
	}

	/**
	 * Method to check if game is over
	 * @return
	 */
	public static boolean isFinal() {
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
	public static boolean checkIfConsecutiveDefensiveMoveReached() {
		if (bonzeeBoard.getMaxConsecutiveMoves() == 0) {
			System.out.println("10 non-attacking consecutive moves have been made. The game is DRAW.");
			return true;
		}
		return false;
	}
}
