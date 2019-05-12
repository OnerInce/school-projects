public class BinarySearchTree {
	
	Node root;
	
	// Constructor sets the root to the null
	
	public BinarySearchTree(){
		
		root = null;
		
	}
	
	// Add an array of integers to the binary tree
	
	public void addArraytoTree(int array[]) {
		
		int numberofNodes = array.length;
		
		for(int i = 0; i < numberofNodes; i++) {
			
			insertNodetoTree(array[i]);
			
		}
		
		System.out.print("BST created with elements:");
		
	}
	
	public void insertNodetoTree(int key) {
		
		root = insertNodetoTree(root, key);
		
	}
	
	// Inserting a Node object to the tree
	
	public Node insertNodetoTree(Node root, int key) {
		
		if(root == null) {
			
			root = new Node(key);
			return root;
			
		}
		
		else if(key > root.value) {
			
			root.right = insertNodetoTree(root.right, key);
			
		}
		
		else if(key < root.value) {
			
			root.left = insertNodetoTree(root.left, key);
			
		}
		
		return root;
		
	}
	
	// Find the floor of the root Node
	
	public Node getMinNode(Node root) {
		
		if(root == null) {return null;}
		
		Node temp = root.right;
		
		while(temp.left != null) {
			
			temp = temp.left;
			
		}
		
		return temp;
		
	}
	
	public void deleteNode(Node toDelete) {
		
		root = deleteNode(root, toDelete);
		
	}
	
	public Node deleteNode(Node root, Node toDelete) {
		
		if (root == null) {return root;}
		
		if(toDelete.value < root.value) {
			
			root.left = deleteNode(root.left, toDelete);
			
		}
		
		else if(toDelete.value > root.value) {
			
			root.right = deleteNode(root.right, toDelete);
			
		}
		
		else {
		
		if (root.left == null) {return root.right;}
		
		else if (root.right == null) {return root.left;}
				
			Node temp = getMinNode(root);
			
			root.value  = temp.value;
			
			root.right = deleteNode(root.right, temp);
			
			temp = null;	// Delete the node after replacing root		
		
		}
		
		return root;
		
	}
	
	// Create a full binary tree with a recursive function
	
	public void createFullTree(int array[], int start, int end) {
		
			if(start > end) {return;}
		
			int middle = (start + end) / 2;
			
			insertNodetoTree(array[middle]);
			
			createFullTree(array, start, middle - 1);
			createFullTree(array, middle + 1, end);
		
	}
		

}
