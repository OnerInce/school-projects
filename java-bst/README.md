# java-bst

A binary search tree(BST)is a rooted binary tree, whose internal nodes each store a key (and optionally, additionalvalues)  and  each  hastwo  distinguished  sub-trees,  commonly  denoted left and right. The tree additionally satisfies the binary search property, which states that the key in each node must be greater than or equal to any key stored in the left sub-tree, and less than or equal to any key stored in the right sub-tree. Equal keys cannot be stored in both left and subtrees and they must be stored inthe left subtrees or in the right subtrees. The leaves of the tree do not haveany children.

**CreateBSTn1,n2,...** ----> InsertN(whereN> 0) distinct integers (n1,n2, ...) into an empty binary search tree. For instance, if the     command line includes “CreateBST 6,3,8,1,9”, function should insert the integers 6, 3, 8, 1, and 9 into an empty binary search tree in the given order. Set  the  current  binary  search  tree  to  this  binary  search  tree  after  the execution of this command. Program should output "BST  created  with  elements  n1,n2,...". The elements should written with the inorder traversal.

**CreateBSTH H** -----> Create  a  full  binary  search  tree  with  a  height  of H(H>  0)  using  the integers 1, 2, .., 2H-1.Set  the  current  binary  search  tree  to  this  binary  search  tree  after  the execution of this command. Program should output "A full BST created with elements n1,n2 ...". The elements should written with the inorder traversal.

**FindHeight** ----> Find and write the height of the current binary search tree. 

**FindWidth** -----> Find and write the width of the current binary search tree. The width of a tree is the number of nodes at a level that contains the maximum number of nodes.

**Preorder** ----> Write all nodes of the current binary search tree using preorder traversal.

**LeavesAsc** ----> Write all  leaves  of  the  current  binary  search  tree  in  ascending  order without using a sorting algorithm.

**DelRoot** ----> Delete the root ofthe current binary search tree if it exists.

**DelRootLc** ----> Delete the left child of root of the current binary search tree if it exists.

**DelRootRc** ----> Delete the right child of root ofthe current binary search tree if it exists.
