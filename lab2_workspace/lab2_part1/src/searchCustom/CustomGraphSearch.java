package searchCustom;

import java.util.ArrayList;
import java.util.HashSet;

import searchShared.NodeQueue;
import searchShared.Problem;
import searchShared.SearchObject;
import searchShared.SearchNode;

import world.GridPos;

public class CustomGraphSearch implements SearchObject {

    private HashSet<SearchNode> explored;
    private NodeQueue frontier;
    protected ArrayList<SearchNode> path;
    private boolean insertFront;

    /**
     * The constructor tells graph search whether it should insert nodes to front or back of the frontier 
     * The same as whether or not we should have a queue or stack <-> FIFO or LIFO <-> DFS or BFS
     */
    public CustomGraphSearch(boolean bInsertFront) {
	insertFront = bInsertFront;
    }

    /**
     * Implements "graph search", which is the foundation of many search algorithms
     */
    public ArrayList<SearchNode> search(Problem p) {
	// The frontier is a queue of expanded SearchNodes not processed yet
	frontier = new NodeQueue();
	/// The explored set is a set of nodes that have been processed 
	explored = new HashSet<SearchNode>();
	// The start state is given
	GridPos startState = (GridPos) p.getInitialState();
	// Initialize the frontier with the start state  
	frontier.addNodeToFront(new SearchNode(startState));

	// Path will be empty until we find the goal.
	path = new ArrayList<SearchNode>();

	// The current node.
	SearchNode node;

	while (true) {
	    // If the frontier is empty something bad has happened.
	    if (frontier.isEmpty())
		{	
		    System.out.println("Nothing found!");
		    return frontier.toList();
		}
		    
	    //Always take first element.
	    node = frontier.removeFirst(); 
	    explored.add(node);
		    
	    //Find allowed children (that aren't walls).
	    ArrayList<GridPos> childStates = p.getReachableStatesFrom(node.getState());
		    
	    for (GridPos childGridPos : childStates){
		       
		SearchNode childNode = new SearchNode(childGridPos, node);
		// If the child isn't in frontier or explored we set the parent.
		if ( !frontier.contains(childNode) && !explored.contains(childNode) ){
			    
		    //Create a child node from the child position and the parent.

		    // If the child is the goal we return path, otherwise insert in frontier.
		    if ( p.isGoalState(childNode.getState()) ){
			path = childNode.getPathFromRoot();
			return path;
		    }
		    else {				
			if (insertFront){
			    frontier.addNodeToFront(childNode);
			}
			else{
			    frontier.addNodeToBack(childNode);
			}
		    }
		}
	    }
	}
    }

    /*
     * Functions below are just getters used externally by the program 
     */
    public ArrayList<SearchNode> getPath() {
	return path;
    }

    public ArrayList<SearchNode> getFrontierNodes() {
	return new ArrayList<SearchNode>(frontier.toList());
    }
    public ArrayList<SearchNode> getExploredNodes() {
	return new ArrayList<SearchNode>(explored);
    }
    public ArrayList<SearchNode> getAllExpandedNodes() {
	ArrayList<SearchNode> allNodes = new ArrayList<SearchNode>();
	allNodes.addAll(getFrontierNodes());
	allNodes.addAll(getExploredNodes());
	return allNodes;
    }

}
