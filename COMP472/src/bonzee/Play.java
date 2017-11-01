package bonzee;
import java.util.Scanner;

public class Play {
	
	private static boolean moved = false;
	private static Board bonzeeBoard;
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		bonzeeBoard = new Board();
		bonzeeBoard.displayBoard();
		startGame();
	}
	
	/**
	 * Method to start the game
	 */
	public static void startGame() {
		Scanner scanner = new Scanner(System.in);
		boolean tokenG = true;

		System.out.println("Rules:\n 1. Input your next move in the following format:\n" + " \tA2,B2 where A2 is the token you want to move, and B2 is its final position, and where A is the Y-direction and 2 is the X-direction\n");
		System.out.println(" 2. Cases representation:");
		System.out.println("\t"+"a." + "|*|" + " Represents a black-case. " + "This means that you are allowed to move the token of one position in the following directions:");
		System.out.println( "\t\t Up, Down, Left, Right, Diagonaly");
		System.out.println("\t"+"b." + "Cases not indicated by a" +  " |*| "+ " mean that you are only allowed to move the token of one position in the following directions:");
		System.out.println("\t\t Up, Down, Left, Right.\n");

		System.out.println("Start playing!\n");

		// GAME LOOP: 
		// TODO: Find actual condition until the game is over
		// TODO: The Board should return us with a boolean to say if the move was valid or not, if it's not valid, continue in the game loop
		while(bonzeeBoard.getMaxConsecutiveMoves() > 0) {
			System.out.print("It's ");
			System.out.print(tokenG ? "Green's " : "Red's ");
			System.out.println("turn.");

			// READ INPUT
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
							moved = bonzeeBoard.moveToken(initialY, initialX, finalY, finalX, tokenG ? 'G' : 'R',false);
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
			if (bonzeeBoard.getMaxConsecutiveMoves() == 0)
				System.out.println("10 non-attacking consecutive moves have been made. The game is DRAW.");
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
}
