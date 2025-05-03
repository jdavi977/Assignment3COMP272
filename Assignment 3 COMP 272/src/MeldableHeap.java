import java.util.*;

/**
 * 
 * @param <T>
 */
public class MeldableHeap<T extends Comparable<T>> {
    // Nested Node class
    class Node {
        T    val; // Value stored at the node
        Node left, right, parent; // Left child, right child, and parent for node

        // Constructor 
        Node(T val) {
            this.val    = val;
            this.left   = null;
            this.right  = null;
            this.parent = null;
        }
    }

    private Node root; // Root node

    /**
     * Merges two heaps h1 and h2 into one
     * @param h1 first heap
     * @param h2 second heap
     * @return returns the new root of the merged heap
     */
    private Node merge(Node h1, Node h2) {
    	// Return the other heap if one is empty
        if (h1 == null) return h2;
        if (h2 == null) return h1;
        
        // Makes sure that h1 has the smaller root value
        if (h2.val.compareTo(h1.val) < 0) {
            Node tmp = h1;
            h1 = h2;
            h2 = tmp;
        }
        
        // Randomly merge h2 into either the left or right subtree of h1
        if (Math.random() < 0.5) {
            h1.left = merge(h1.left, h2);
            if (h1.left != null) h1.left.parent = h1; // Fix parent pointer
        } else {
            h1.right = merge(h1.right, h2);
            if (h1.right != null) h1.right.parent = h1; // Fix parent pointer
        }
        return h1; // return new root of merged heap
    }

    /**
     * Removes a node from the heap
     * @param u is the node being removed
     */
    public void remove(Node u) {
        // Merge u’s two children into one subtree
        Node mergedSub = merge(u.left, u.right);

        // Fix parent pointer 
        Node p = u.parent;
        if (mergedSub != null) 
            mergedSub.parent = p;

        // Splice mergedSub into u’s position
        if (p == null) { // u was the root
            root = mergedSub;
        } else if (p.left == u) {
            p.left = mergedSub;
        } else {
            p.right = mergedSub;
        }
    }

    /**
     * Inserts new nodes into the heap
     * @param value node being added
     * @return newly created node
     */
    public Node insert(T value) {
        Node n = new Node(value);
        root = merge(root, n); // merge node into current root
        if (root != null) root.parent = null; // ensure root's parent is null
        return n;
    }

    /**
     * Prints the heap
     * @param node is the tree node
     */
    public void printHeap(Node node) {
        if (node == null) return;
        System.out.println(node.val);
        printHeap(node.left);
        printHeap(node.right);
    }

    /**
     * Test case to see if removing the node works
     * @param args unused command line arguments
     */
    public static void main(String[] args) {
    	// Create new meldableheap
        MeldableHeap<Integer> H = new MeldableHeap<>();
        // Map out to remember the nodes by value
        Map<Integer, MeldableHeap<Integer>.Node> refs = new HashMap<>();

        // Insert values
        for (int v : new int[]{5, 3, 8, 1, 6, 4, 7}) {
            MeldableHeap<Integer>.Node n = H.insert(v);
            refs.put(v, n);
        }

        System.out.println("Before removal:");
        H.printHeap(H.root);

        // Remove the node containing 3
        H.remove(refs.get(3));

        System.out.println("\nAfter removal of 3:");
        H.printHeap(H.root);
    }
}
