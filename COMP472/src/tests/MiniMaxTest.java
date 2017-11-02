package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import bonzee.*;

import org.junit.Test;

public class MiniMaxTest {

	/**
	 * Calculate the score for an empty board
	 */
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
	
	/**
	 * Calculate the score for board 1 in the handout example
	 */
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
	
	/**
	 * Calculate the score for board 2 in the handout example
	 */
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
	
	/**
	 * Calculate the score for board 3 in the handout example
	 */
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
	
//	/**
//	 * Try to find the best node given a null list
//	 */
//	@Test
//	public void findBestNodeNullListTest() {
//		boolean isMAX = true;
//		List<Node> children = null;
//		
//		MiniMax miniMaxObject = new MiniMax();
//		Node node = miniMaxObject.findBestNode(isMAX, children, 3);
//		
//		assertNull(node);
//	}
	
	/**
	 * Find the best node for a MAX player 
	 */
	@Test
	public void findBestNodeMaxTest() {
		// Mock variables
		boolean isMAX = true;
		List<Node> children = new ArrayList<Node>();
		
		// Mock nodes: random values for the scores
		Node node1 = new Node(false, 3, null);
		node1.setScore(ThreadLocalRandom.current().nextInt(1,Integer.MAX_VALUE));
		Node node2 = new Node(false, 3, null);
		node2.setScore(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, -1));
		
		// Add all mock nodes to the mock list
		children.add(node1);
		children.add(node2);
		
		// Test		
		MiniMax miniMaxObject = new MiniMax();
		Node node = miniMaxObject.findBestNode(isMAX, children, 2);
		
		// Assert
		assertSame(node, node1);
	}
	
	/**
	 * Find the best node for a MIN player
	 */
	@Test
	public void findBestNodeMinTest() {
		// Mock variables
		boolean isMAX = false;
		List<Node> children = new ArrayList<Node>();
		
		// Mock nodes: random values for the scores
		Node node1 = new Node(false, 3, null);
		node1.setScore(ThreadLocalRandom.current().nextInt(1,Integer.MAX_VALUE));
		Node node2 = new Node(false, 3, null);
		node2.setScore(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, -1));
		
		// Add all mock nodes to the mock list
		children.add(node1);
		children.add(node2);
		
		// Test		
		MiniMax miniMaxObject = new MiniMax();
		Node node = miniMaxObject.findBestNode(isMAX, children, 2);
		
		// Assert
		assertSame(node, node2);
	}
	
	@Test
	public void computeNextMoves() {
		// Mock variables
		boolean isMAX = true;
		MiniMax minimax = new MiniMax();
		Node root = new Node(isMAX, 3, "");
		root.setCurrentState(minimax.getBoard().getBoardArr());
		
		List<String> list = minimax.computeNextMoves(root);
		assert(list.size() > 0);
		for (String s : list) {
			System.out.println(s);
		}
	}

}
