public class BSTNode<T extends Comparable<T>> {
  T value;
  BSTNode<T> left;
  BSTNode<T> right;

  BSTNode(T value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}
