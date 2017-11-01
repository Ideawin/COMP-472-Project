package bonzee;
import java.util.List;

public class Node {

    boolean isMAX;
    int maxLevelLookout;
    int score;
    String tokenMove;
    List<Node> children;
    char[][] currentState;
    
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
	
}