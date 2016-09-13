package searchCustom;

import java.util.Random;


public class CustomDepthFirstSearch extends CustomGraphSearch{
    public CustomDepthFirstSearch(int maxDepth){
	super(true); // Yes, we want to insert elements in the front (stack)
    }
};
