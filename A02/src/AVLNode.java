package src;

public class AVLNode<T extends Comparable<T>>{
    T value;
    AVLNode<T> left;
    AVLNode<T> right;

    int height;

    public AVLNode(T data) {
        this.value = data;
        left = null;
        right  = null;
    }
}
