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
	
	@Test
	public void calculateScoreBoard1() {
		Node node = new Node(false, 2, null);
		char[][] mockBoard = new char[Board.HEIGHT][Board.WIDTH];
		for(int i=0; i<mockBoard.length; i++) {
			for(int j=0; j<mockBoard[i].length; j++) {
				mockBoard[i][j] = ' ';
			}
		}
		
		// Set some mock positions
		mockBoard[2][4] = 'G';
		mockBoard[1][6] = 'G';
		
		node.setCurrentState(mockBoard);
		
		// Create new mini max object
		MiniMax miniMaxObj = new MiniMax();
		miniMaxObj.calculateScore(node);
		
		// Assert result
		assertEquals(1100, node.getScore());
	}
	
	@Test
	public void calculateScoreBoard2() {
		Node node = new Node(false, 2, null);
		char[][] mockBoard = new char[Board.HEIGHT][Board.WIDTH];
		for(int i=0; i<mockBoard.length; i++) {
			for(int j=0; j<mockBoard[i].length; j++) {
				mockBoard[i][j] = ' ';
			}
		}
		
		// Set some mock positions
		mockBoard[2][0] = 'R';
		mockBoard[2][1] = 'G';
		mockBoard[2][2] = 'R';
		mockBoard[2][3] = 'G';
		mockBoard[2][5] = 'R';
		mockBoard[2][6] = 'R';
		mockBoard[2][7] = 'R';
		mockBoard[2][8] = 'G';
		
		node.setCurrentState(mockBoard);
		
		// Create new mini max object
		MiniMax miniMaxObj = new MiniMax();
		miniMaxObj.calculateScore(node);
		
		// Assert result
		assertEquals(-1100, node.getScore());
	}
	
	@Test
	public void calculateScoreBoard3() {
		Node node = new Node(false, 2, null);
		char[][] mockBoard = new char[Board.HEIGHT][Board.WIDTH];
		for(int i=0; i<mockBoard.length; i++) {
			for(int j=0; j<mockBoard[i].length; j++) {
				mockBoard[i][j] = ' ';
			}
		}
		
		// Set some mock positions
		mockBoard[2][0] = 'R';
		mockBoard[2][1] = 'G';
		mockBoard[2][2] = 'R';
		mockBoard[2][3] = 'G';
		mockBoard[0][5] = 'R';
		mockBoard[0][6] = 'R';
		mockBoard[2][7] = 'R';
		mockBoard[4][8] = 'G';
		
		node.setCurrentState(mockBoard);
		
		// Create new mini max object
		MiniMax miniMaxObj = new MiniMax();
		miniMaxObj.calculateScore(node);
		
		// Assert result
		assertEquals(-500, node.getScore());
	}

}
