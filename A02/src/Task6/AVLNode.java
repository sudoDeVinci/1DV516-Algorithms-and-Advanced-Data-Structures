package src.Task6;

public class AVLNode {
    public int height;
    public Integer value;
    public AVLNode left, right;

    public AVLNode(int data) {
        this.height = 0;
        this.value = data;
        this.left = null;
        this.right = null;
    }
}
