package src.Task4;

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
        else if (getLeft() != null && getRight() == null) return getLeft().getDepth() + 1;
        else if (getRight()!= null && getLeft() == null) return getRight().getDepth() + 1;
        else {
            return Math.max(getLeft().getDepth(), getRight().getDepth()) + 1;
        }
    }

    public BSTNode remove(BSTNode previous, int key) {
        if (previous == null) return null;

        if (key < previous.getValue()) previous.setLeft(remove(previous.getLeft(), key));
        else if (key > previous.getValue()) previous.setRight(remove(previous.getRight(), key));
        else if (previous.getLeft() != null && previous.getRight()!= null) {
            previous.setValue(previous.getRight().min().getValue());
            previous.setRight(remove(previous.getRight(), previous.getValue()));
        } else {
            return null;
        }
    }
}
