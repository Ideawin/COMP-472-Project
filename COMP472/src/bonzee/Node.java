package bonzee;
import java.util.ArrayList;
import java.util.List;

public class Node {

    boolean isMAX;
    int maxLevelLookout;
    int score;
    String tokenMove;
    List<Node> children;
    char[][] currentState;
    String nextBestMove;
    
    /**
     * Constructor
     * @param isMAX
     * @param maxLevelLookout
     * @param move
     */
    public Node(boolean isMAX, int maxLevelLookout, String move) {
    	this.maxLevelLookout = maxLevelLookout;
    	this.isMAX = isMAX;
    	this.tokenMove = move;
    	this.currentState = null;
    	this.children = new ArrayList<Node>();
    }

    public List<Node> getChildren() {
    	return children;
    }
    
    public void addChild(Node node) {
    	this.children.add(node);
    }
    
    public int getScore() {
    	return score;
    }
    
    public boolean isMAX() {
    	return isMAX;
    }
    
    public int getMaxLevelLookout() {
    	return maxLevelLookout;
    }
    
    public void calculateScore() {
    	// Use the heuristics and then do
    }
    
    public void setScore(int score) {
    	this.score = score;
    }
    
	/**
	 * Set the current state
	 */
	public void setCurrentState(char[][] currentState) {
		this.currentState = currentState;
	}
	
	/**
	 * Get the current state
	 */
	public char[][] getCurrentState() {
		return currentState;
	}
	
	/**
	 * Set next best move
	 */
	public void setNextBestMove(String move) {
		this.nextBestMove = move;
	}
	
	/**
	 * Get next best move
	 */
	public String getNextBestMove() {
		return nextBestMove;
	}
	
	/**
	 * Get token move
	 * @return
	 */
	public String getTokenMove() {
		return tokenMove;
	}
}