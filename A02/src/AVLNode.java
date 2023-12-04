package src;

public class AVLNode<T extends Comparable<T>> extends LRNode<T,AVLNode<T>>{
    public int height;

    public AVLNode(T data) {
        this.height = 0;
        this.value = data;
        this.left = null;
        this.right = null;
    }
}
