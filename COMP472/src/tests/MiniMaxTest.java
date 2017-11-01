package tests;

import static org.junit.Assert.*;

import bonzee.*;
import junit.framework.Assert;

import org.junit.Test;

public class MiniMaxTest {

	@Test
	public void calculateScoreEmptyBoard() {
		Node node = new Node(false, 2, null);
		char[][] mockBoard = new char[Board.HEIGHT][Board.WIDTH];
		for(int i=0; i<mockBoard.length; i++) {
			for(int j=0; j<mockBoard[i].length; j++) {
				mockBoard[i][j] = ' ';
			}
		}
		node.setCurrentState(mockBoard);
		
		// Create new mini max object
		MiniMax miniMaxObj = new MiniMax();
		miniMaxObj.calculateScore(node);
		
		// Assert result
		assertEquals(0, node.getScore());
	}

}
