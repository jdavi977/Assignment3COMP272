/**
 * This class demonstrates how to transform a binary tree into another using only AVL single rotations.
 */
public class SingleRotation {

	/**
	 * Binary tree node
	 */
    static class TreeNode {
        int key;
        TreeNode left, right;
        
        /**
         * Constructor to initialize the node with a key
         * @param key value of the node
         */
        TreeNode(int key) {
            this.key = key;
        }
    }

    /**
     * Performs a single right rotation around the node y
     * @param y is the root of the subtree to rotate
     * @return the new root of the subtree after the rotation
     */
    static TreeNode rightRotate(TreeNode y) {
    	
        TreeNode x = y.left; // x becomes new root
        TreeNode B = x.right; // B will become the left child of y
        x.right = y; // y becomes right child of x
        y.left = B; // B becomes left child of y
        return x; // return new root
    }

    /**
     * Performs a single left rotation around the node x
     * @param x is the root of the subtree to rotate
     * @return the new root of the subtree after the rotation
     */
    static TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right; // y becomes new root
        TreeNode B = y.left; // B will become right child of x
        y.left = x; // x becomes left child of y
        x.right = B; // B becomes right child of x
        return y; // return new root
    }

    /**
     * Moves the node with the key up to the root of the current subtree
     * @param root the root of the subtree
     * @param key the key to move up
     * @return new root of the subtree with the key at the top
     */
    static TreeNode moveUp(TreeNode root, int key) {
        if (root.key == key) {
            return root;
        }
        if (key < root.key) {
            root.left = moveUp(root.left, key); // Recursion left
            return rightRotate(root); // Rotate right to move the key up
        } else {
            root.right = moveUp(root.right, key); // Recursion right
            return leftRotate(root); // Rotate left to move key up
        }
    }

    /**
     * Transforms tree t1 so that it matches t2
     * @param t1 the tree to be transformed
     * @param t2 the target tree that t1 will be transformed into
     * @return transformed version of t1
     */
    static TreeNode transform(TreeNode t1, TreeNode t2) {
        if (t1 == null || t2 == null) {
            return t2;
        }
        t1 = moveUp(t1, t2.key); // Moves t2.key to root of t1
        t1.left  = transform(t1.left,  t2.left); // Recursively match left subtree
        t1.right = transform(t1.right, t2.right); // Recursively match right subtree
        
        return t1;
    }

    /**
     * Performs a pre-order traversal of the tree and prints the node keys
     * @param node the current node in traversal
     */
    static void preorder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.key + " ");
        preorder(node.left);
        preorder(node.right);
    }

    /**
     * Single rotation testing
     * @param args unused command line arguments
     */
    public static void main(String[] args) {
    	
    	// Making T1
        TreeNode T1 = new TreeNode(4);
        T1.left  = new TreeNode(2);
        T1.right = new TreeNode(5);
        T1.left.left  = new TreeNode(1);
        T1.left.right = new TreeNode(3);

        // Making T2, same keys but in different shape
        TreeNode T2 = new TreeNode(2);
        T2.left       = new TreeNode(1);
        T2.right      = new TreeNode(4);
        T2.right.left = new TreeNode(3);
        T2.right.right= new TreeNode(5);

        System.out.print("Preorder T1 before: ");
        preorder(T1);
        System.out.println();

        T1 = transform(T1, T2);

        System.out.print("Preorder of T2:     ");
        preorder(T2);
        System.out.println();
        
        System.out.print("Preorder T1 after rotation:  ");
        preorder(T1);
        System.out.println();

    }
}
