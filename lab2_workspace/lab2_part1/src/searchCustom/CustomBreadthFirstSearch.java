package searchCustom;

import java.util.Random;

public class CustomBreadthFirstSearch  extends CustomGraphSearch{
    public   CustomBreadthFirstSearch(int maxDepth){
	super(false); // No, we want to insert elements in the back (queue)
    }
};
