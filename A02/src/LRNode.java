package src;

public abstract class LRNode<T extends Comparable<T>, U extends LRNode<T, U>> {
    T value;
    U left;
    U right;
}
