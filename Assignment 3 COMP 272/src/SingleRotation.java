public class SingleRotation {

    /** Basic TreeNode definition */
    static class TreeNode {
        int key;
        TreeNode left, right;
        TreeNode(int key) {
            this.key = key;
        }
    }

    /** Right rotate around y:
             y                x
            / \     ==>      / \
           x   C            A   y
          / \                  / \
         A   B                B   C
    */
    static TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode B = x.right;
        x.right = y;
        y.left = B;
        return x;
    }

    /** Left rotate around x:
         x                   y
        / \      ==>        / \
       A   y               x   C
          / \             / \
         B   C           A   B
    */
    static TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode B = y.left;
        y.left = x;
        x.right = B;
        return y;
    }

    /**
     * Bubble the node with value 'key' up to the root of this subtree,
     * using single rotations only.
     */
    static TreeNode bubbleUp(TreeNode root, int key) {
        if (root.key == key) {
            return root;
        }
        if (key < root.key) {
            // Bubble up within left subtree
            root.left = bubbleUp(root.left, key);
            // Now root.left.key == key, rotate right to move it up
            return rightRotate(root);
        } else {
            // Bubble up within right subtree
            root.right = bubbleUp(root.right, key);
            // Now root.right.key == key, rotate left to move it up
            return leftRotate(root);
        }
    }

    /**
     * Transform tree t1 so that its structure matches tree t2,
     * using only single rotations. Assumes both contain the same keys.
     */
    static TreeNode transform(TreeNode t1, TreeNode t2) {
        if (t1 == null || t2 == null) {
            return t2;
        }
        // Step 1: bubble t2.key up to root of t1
        t1 = bubbleUp(t1, t2.key);
        // Step 2: match left and right subtrees recursively
        t1.left  = transform(t1.left,  t2.left);
        t1.right = transform(t1.right, t2.right);
        return t1;
    }

    /** Pre-order traversal (root, left, right) to display structure */
    static void preorder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.key + " ");
        preorder(node.left);
        preorder(node.right);
    }

    public static void main(String[] args) {
        // Build original tree T1:
        //     4
        //    / \
        //   2   5
        //  / \
        // 1   3
        TreeNode T1 = new TreeNode(4);
        T1.left  = new TreeNode(2);
        T1.right = new TreeNode(5);
        T1.left.left  = new TreeNode(1);
        T1.left.right = new TreeNode(3);

        // Build target tree T2:
        //     3
        //    / \
        //   1   5
        //    \  /
        //     2 4
        TreeNode T2 = new TreeNode(2);
        T2.left       = new TreeNode(1);
        T2.right      = new TreeNode(4);
        T2.right.left = new TreeNode(3);
        T2.right.right= new TreeNode(5);

        System.out.print("Preorder T1 before: ");
        preorder(T1);
        System.out.println();

        // Transform T1 into the shape of T2
        T1 = transform(T1, T2);

        System.out.print("Preorder of T2:     ");
        preorder(T2);
        System.out.println();
        
        System.out.print("Preorder T1 after rotation:  ");
        preorder(T1);
        System.out.println();

    }
}
