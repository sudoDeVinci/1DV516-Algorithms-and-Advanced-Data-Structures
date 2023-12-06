package src;

public abstract class LRNode<T extends Comparable<T>, U extends LRNode<T, U>> {
    public T value;
    public U left;
    public U right;
}
