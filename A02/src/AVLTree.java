package src;

import java.util.Random;

public class AVLTree <T extends Comparable<T>>{

    private AVLNode<T> root;

    public int height() {
        return height(root);
    }

    private int height(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private int max (int a, int b) {
        return a > b ? a : b;
    }

    /**
     * Update a height of a node.
     * Used after a rotation.
     * @param <T>
     * @param node
     */
    private void updateHeight(AVLNode<T> node) {
        int leftChildHeight = height(node.left);
        int rightChildHeight = height(node.right);
        node.height = max(leftChildHeight, rightChildHeight) + 1;
    }

    /**
     * Calculate the balance factor of the AVL tree at a given node.
     * @param <T>
     * @param node
     * @return
     */
    private int BF(AVLNode<T> node) {
        return height(node.right) - height(node.left);
    }

    /** Perform a right rotation of an AVL tree and return the new root at that node.
     * 
     * @param <T>
     * @param node
     * @return
     */
    private AVLNode<T> RR(AVLNode<T> node) {
        AVLNode<T> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    /** Perform a right rotation of an AVL tree and return the new root at that node.
     * 
     * @param <T>
     * @param node
     * @return
     */
    private AVLNode<T> LR(AVLNode<T> node) {
        AVLNode<T> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    /**
     * Rebalance AVL trees based on their heaviness.
     * BF(N) < -1 && BF(L) ≤ 0	RR(N)
     * BF(N) < -1 && BF(L) > 0	LR(L) then RR(N)
     * BF(N) > +1 && BF(R) ≥ 0 	LR(N)
     * BF(N) > +1 && BF(R) < 0	RR(R) then LR(N)
     * @param node
     * @return New root of tree.
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        int bf = BF(node);
        /**
         * Check for left-heavy tree
         */
        if (bf < -1 && BF(node.left) <= 0) return RR(node);
        if (bf < -1 && BF(node.left) > 0) {node.left = LR(node.left); return RR(node);}
        /**
         * Check for right-heavy tree
         */
        if (bf > 1 && BF(node.right) >= 0) return LR(node);
        if (bf > 1 && BF(node.right) < 0) {node.right = RR(node.right); return LR(node);};
        return null;
    }

    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    /**
     * Insert a value into the tree.
     * Return boolean of insertion operation success.
     * @param value
     * @return Boolean inserted
     */
    public boolean insert(T value) {
        root = insert(value, root);
        return root == null ? false : true;
    }
    
    /**
     * Insert a value into the tree and return the new root after rebalanacing.
     * @param value
     * @param node
     * @return The node 
     */
    private AVLNode<T> insert(T value, AVLNode<T> node) {
        if (node == null) return new AVLNode<T>(value);

        int cmp = value.compareTo(node.value);
        
        if (cmp < 0) node.left = insert( value, node.left);
        else if (cmp > 0) node.right = insert(value, node.right);
        else return node;

        updateHeight(node);
        return balance(node);
    }

    /**
     * Delete a value from the tree.
     * @param value
     */
    public void delete(T value) {
        root = delete(root, value);
    }

    private AVLNode<T> delete(AVLNode<T> node, T value) {
        if (node == null) return null; // Value not found

        if (value.compareTo(node.value) < 0) node.left = delete(node.left, value);
        else if (value.compareTo(node.value) > 0) node.right = delete(node.right, value);
        else {
            // Node to be deleted found
            if (node.left == null || node.right == null) node = (node.left != null) ? node.left : node.right;
            else {
                // Case 2: Node with two children
                AVLNode<T> successor = findMin(node.right);
                node.value = successor.value;
                node.right = delete(node.right, successor.value);
            }
        }

        if (node != null) {
            // Update height of current node
            updateHeight(node);
            // Rebalance the node
            return balance(node);
        }

        return null;

    }

    @SuppressWarnings("unchecked") // This cast can end up bad but I am dumb.
    private T getValue(int size) {
        Random rand = new Random();
        return (T) Integer.valueOf(rand.nextInt(size)+1);
    }

    /**
     * Construct randomly made trees of integers and return the root.
     * @param size
     * @return
     */
    public AVLNode<T> randomizeTree(int size) {
        AVLNode<T> root = new AVLNode<T>(getValue(size));

        for (int i = 1; i<size; i++) insert(getValue(size));
        return root;
    }
}
