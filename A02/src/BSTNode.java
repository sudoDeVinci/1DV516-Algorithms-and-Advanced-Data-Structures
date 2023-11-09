package src;

public class BSTNode<T extends Comparable<T>> {
  public T value;
  public BSTNode<T> left;
  public BSTNode<T> right;

  BSTNode(T value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}