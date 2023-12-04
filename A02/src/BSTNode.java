package src;

public class BSTNode<T extends Comparable<T>> extends LRNode<T, BSTNode<T>>{
  BSTNode(T data) {
    this.value = data;
    this.left = null;
    this.right = null;
  }
}