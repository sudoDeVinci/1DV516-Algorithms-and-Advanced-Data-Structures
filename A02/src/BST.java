package src;

public abstract class BST<T extends Comparable<T>, U extends LRNode<T, U>> {

    private U root;
    
    public abstract void add(T key);
    public abstract void remove(T key);
    public abstract int height();
    public abstract boolean contains(T value);
    public abstract void printTree();

    public abstract void printTree(U n);
}