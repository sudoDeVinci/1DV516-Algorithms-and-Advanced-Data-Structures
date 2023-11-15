package src;

public class AVLNode<T extends Comparable<T>>{
    public T value;
    public AVLNode<T> left;
    public AVLNode<T> right;

    public int height = 0;

    public AVLNode(T data) {
        this.value = data;
        left = null;
        right  = null;
    }
}
