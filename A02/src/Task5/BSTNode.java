package src.Task5;

public class BSTNode {
    private int value;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BSTNode getLeft() {
        return this.left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return this.right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }

    public void add(BSTNode node) {
        if (node.getValue() < value) {
            if (getLeft() != null) getLeft().add(node);
            else setLeft(node);
        } else {
            if (getRight()!= null) getRight().add(node);
            else setRight(node);
        }
    }
}
