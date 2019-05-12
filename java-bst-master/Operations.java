import java.util.*;

public class Operations {
		
	public int getHeight(Node root) {
		
		if(root == null) {return 0;}
		
		return Math.max(getHeight(root.left),getHeight(root.right)) + 1;
				
	}
	
	public int getWidth(Node root) {
		
		if(root == null) {return 0;}
		
		int currentSize = 0, maxSize = 0;
		
	    Queue<Node> q = new LinkedList<>();
	    
	    q.add(root);
	    
	    currentSize = maxSize = 1;
		
		// Traverse the tree with Breadth-first Search Algorith with using queue
	    
	    while(!q.isEmpty()) {
	    	
	    	Node temp = q.poll();
	    	
	    	currentSize--;
	    	
	    	if(currentSize > maxSize) {maxSize = currentSize;}
	    	
	    	if(temp.left != null) {q.add(temp.left); currentSize++;}
	    	
	    	if(temp.right != null) {q.add(temp.right); currentSize++;}
	    	
	    	if(currentSize > maxSize) {maxSize = currentSize;} // Maxsize = width of the tree
	    	
	    }
	    
	    return maxSize;

		
	}
	
	public void inOrder(BinarySearchTree myTree) {
		
		inOrder(myTree.root);
		
	}
	
	public void inOrder(Node root) {
		
		if(root != null) {
		
		inOrder(root.left);
		System.out.print(root.value + " ");
		inOrder(root.right);
		
		}
	}
	
	public void preOrder(BinarySearchTree myTree) {
		
		preOrder(myTree.root);
		
	}
	
	public void preOrder(Node root) {
		
		if(root != null) {
		
		System.out.print(root.value + " ");
		preOrder(root.left);
		preOrder(root.right);
		
		}
	}
	
	public void printLeavesAsc(BinarySearchTree myTree) {
		
		printLeavesAsc(myTree.root);
		
	}
	
	public void printLeavesAsc(Node root) {
		
		// Similar to post-order traversal, just print the nodes with both children equals the null
		
		if(root != null) {
			
		printLeavesAsc(root.left);
		printLeavesAsc(root.right);
		if(root.left == null && root.right == null) {
		System.out.print(root.value + " ");}
		
		}
		
	}
	
	

}
