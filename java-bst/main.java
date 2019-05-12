import java.io.*;
import java.util.*;

public class Exp2 {

	public static void main(String[] args) {
		
		// Reading input file
		
		FileReader fr = null;
		try {
			fr = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		// Set output stream to output.txt
		
		PrintStream fileOut = null;
		try {
			fileOut = new PrintStream(args[1]);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		System.setOut(fileOut);

		BufferedReader br = new BufferedReader(fr);

		String line = null;
		
		// Create objects
		
		BinarySearchTree currentTree = new BinarySearchTree();
		Operations operation = new Operations();

		try {
			
			// Read input file line-by-line and create corresponding objects of each command's classes
			
			while ((line = br.readLine()) != null) {
				
				String splittedCommand[] = line.split(" ",2);
				
				if(splittedCommand[0].equals("CreateBST")) {
					
					currentTree.root = null;
					
					String[] elementArray = splittedCommand[1].split(",");
					int[] intElementArray = new int[elementArray.length];
					
				      for(int i = 0; i < elementArray.length; i++) {
				          intElementArray[i] = Integer.parseInt(elementArray[i]);
				       }
				      
				      currentTree.addArraytoTree(intElementArray);
				      operation.inOrder(currentTree);
				      System.out.println();
				      		
				}
				
				else if(splittedCommand[0].equals("FindHeight")) {
						
					System.out.printf("Height:%d\n", operation.getHeight(currentTree.root) - 1);
					
				}
				
				else if(splittedCommand[0].equals("FindWidth")) {
					
					System.out.println("Width:" + operation.getWidth(currentTree.root));
					
				}
				
				else if(splittedCommand[0].equals("Preorder")) {
					
					System.out.print("Preorder:");
					operation.preOrder(currentTree);
					System.out.println();
					
				}
				
				else if(splittedCommand[0].equals("LeavesAsc")) {
					
					System.out.print("LeavesAsc:");
					operation.printLeavesAsc(currentTree);
					System.out.println();
					
				}
				
				else if(splittedCommand[0].equals("DelRoot")) {
					
					System.out.print("Root Deleted:" + currentTree.root.value);
					currentTree.deleteNode(currentTree.root);
					System.out.println();
					
				}
				
				else if(splittedCommand[0].equals("DelRootLc")) {
					
					if(currentTree.root.left == null) {System.out.println("error"); continue;}
					
					System.out.println("Left Child of Root Deleted:" + currentTree.root.left.value);
					currentTree.deleteNode(currentTree.root.left);
					
				}
				
				else if(splittedCommand[0].equals("DelRootRc")) {
					
					System.out.println("Right Child of Root Deleted:" + currentTree.root.right.value);
					currentTree.deleteNode(currentTree.root.right);
					
				}
				
				else if (splittedCommand[0].equals("CreateBSTH")) {
					
					if(Integer.parseInt(splittedCommand[1]) <= 0) {System.out.println("error"); continue;}
					
					System.out.print("A full BST created with elements:");
					
					int height = Integer.parseInt(splittedCommand[1]) + 1;
					
					int numberOfTotalNodes = (int) Math.pow(2, height) - 1;
					
					int nodeArray[] = new int[numberOfTotalNodes];
					
					for(int i = 0; i < numberOfTotalNodes; i++) {
						
						nodeArray[i] = i + 1;						
					}
					
					currentTree.root = null; // Delete the current tree and create a new tree
					currentTree.createFullTree(nodeArray, 0, numberOfTotalNodes - 1);
					operation.inOrder(currentTree);
					System.out.println();
					
				}
				

			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
