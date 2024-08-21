package src.Task5;

public class BSTNode {
    int value;
    BSTNode left;
    BSTNode right;

    public BSTNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Add a node to the tree.
     * The return of the top-most recursive call will set the root node.
     *   If the input node is null, we return our set node value. Other wise we 
     *   return the parent itself.
     * The bottom-most recursive call will set the added value for the leaf.
     * Eventually, the node called will be null, meaning we hit a leaf. This will return the
     *   new node we want to input.
     * @param value
     * @return
     */
    public void add(BSTNode node) {
        if (node.value < value) {
            if (left != null) left.add(node);
            else left = node;
        } else {
            if (right!= null) right.add(node);
            else right = node;
        }
    }

    public BSTNode min() {
        if (this.left == null) return this;
        else return this.left.min();
    }

    public BSTNode max() {
        if (this.right == null) return this;
        else return this.right.max();
    }

    public int getDepth() {
        if (left == null && right == null) return 1;
        else if (left != null && right == null) return left.getDepth() + 1;
        else if (right!= null && left == null) return right.getDepth() + 1;
        else {
            return Math.max(left.getDepth(), right.getDepth()) + 1;
        }
    }

    public BSTNode remove(BSTNode previous, int key) {
        if (previous == null) return null;

        if (key < previous.value) previous.left = (remove(previous.left, key));
        else if (key > previous.value) previous.right = remove(previous.right, key);
        else if (previous.left != null && previous.right!= null) {
            previous.value = previous.right.min().value;
            previous.right = remove(previous.right, previous.value);
        } else {
            if (previous.left != null) previous = previous.left;
            else previous = previous.right;
        }

        return previous;
    }

    public boolean contains (int invalue) {
        if(this.value == invalue) return true;
        else if (this.value < invalue) {
            if (this.right == null) return false;
            else return this.right.contains(invalue); 
        } else {
            if (this.left == null) return false;
            else return this.left.contains(invalue);  // left side of tree
        }
    }
}
