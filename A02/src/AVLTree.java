package src;

import java.util.Random;

public class AVLTree <T extends Comparable<T>>{

    private AVLNode<T> root;

    private int height(AVLNode<T> n) {
        return (n == null) ? -1 : n.height;
    }

    public void add(T key) {
        root = add(root, key);
    }

    private AVLNode<T> add(AVLNode<T> n, T key) {
        if (n == null) return new AVLNode<T>(key);

        int cmp = key.compareTo(n.value);

        // if key < n.value
        if (cmp < 0) n.left = add(n.left, key); 

        // if key > n.value
        if (cmp > 0) n.right = add(n.right, key);

        return balance(n);
    }

    private int max (int a, int b) {
        return (a > b) ? a : b;
    }

    private void updateHeight(AVLNode<T> node) {
        node.height = max(height(node.left), height(node.right)) + 1;
    }

    private AVLNode<T> RL(AVLNode<T> node) {
        AVLNode<T> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        updateHeight(node);
        updateHeight(leftChild);
        return leftChild;
    }

    private AVLNode<T> RLL(AVLNode<T> node) {
        node.left = RR(node.left);
        return RL(node); 
    }

    private AVLNode<T> RR(AVLNode<T> node) {
        AVLNode<T> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        updateHeight(node);
        updateHeight(rightChild);
        return rightChild;
    }

    private AVLNode<T> RRR(AVLNode<T> node) {
        node.right = RL(node.right);
        return RR(node);
    }

    private AVLNode<T> balance(AVLNode<T> n) {
        if(n == null) return n;

        if ( (height(n.left) - height(n.right)) > 1) {
            if ( height(n.left.left) >= height(n.left.right) ) {
                n = RL(n);
            } else {
                n = RLL(n);
            }
        } else if ( (height(n.right) - height(n.left) > 1) ) {
            if ( (height(n.right.right) >= height(n.right.left)) ) {
                n = RR(n);
            } else {
                n = RRR(n);
            }
        }

        updateHeight(n);
        return n;
    }

    

    private T findMin(AVLNode<T> node) {
        if(node.left == null) return node.value;
        else return findMin(node.left);
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

        int cmp = value.compareTo(node.value);

        if (cmp < 0) node.left = delete(node.left, value);
        else if (cmp > 0) node.right = delete(node.right, value);
        else {
            // Node to be deleted found
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;

            node.value = findMin(node.right);
            node.right = delete(node.right, node.value);
        }

        return balance(node);

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

    public void printTree() {
        printTree("", root, true);
    }

    private void printTree(String prefix, AVLNode<T> node, boolean isTail) {
        T nodeName = node.value;
        String nodeConnection = isTail ? "└── " : "├── ";
        System.out.println(prefix + nodeConnection + nodeName);
    
        AVLNode<T>[] children = getChildren(node);
        if (children!=null) {
            for (int i = 0; i < children.length; i++) {
                String newPrefix = prefix + (isTail ? "    " : "│   ");
                printTree(newPrefix, children[i], i == children.length - 1);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private AVLNode<T>[] getChildren(AVLNode<T> node) {

        if (node.left != null && node.right != null) {
            return new AVLNode[]{node.left, node.right};
        }

        if(node.left != null || node.right != null) {
            AVLNode<T> outNode = node.left == null ? node.right:node.left;
            return new AVLNode[]{outNode};
        }

        else return null;
    }
}
