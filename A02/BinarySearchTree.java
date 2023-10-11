import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Honestly, if this is gibberish, I'm sorry. I'm incredibly tired and have been super
 * stressed this week and sick last week. I'm trying my best to get this done, but my
 * eyes can barely focus. If it doesn't make sense, just some feedback would be nice.
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
  private BSTNode<T> root;
  private int size;

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int height() {
    return height(root);
  }

  private int height(BSTNode<T> node) {
    if (node == null)
      return -1;
    return 1 + Math.max(height(node.left), height(node.right));
  }

  public boolean contains(T value) {
    return contains(root, value);
  }

  private boolean contains(BSTNode<T> node, T value) {
    if (node == null)
      return false;

    int cmp = value.compareTo(node.value);

    if (cmp < 0)
      return contains(node.left, value);
    if (cmp > 0)
      return contains(node.right, value);
    return true;
  }

  public boolean add(T value) {
    if (contains(value))
      return false;

    root = add(root, value);
    size++;
    return true;
  }

  private BSTNode<T> add(BSTNode<T> node, T value) {
    if (node == null)
      return new BSTNode<T>(value);

    int cmp = value.compareTo(node.value);

    if (cmp < 0)
      node.left = add(node.left, value);
    else
      node.right = add(node.right, value);

    return node;
  }

  public boolean remove(T value) {
    if (!contains(value))
      return false;

    root = remove(root, value);
    size--;
    return true;
  }

  private BSTNode<T> remove(BSTNode<T> node, T value) {
    if (node == null)
      return null;

    int cmp = value.compareTo(node.value);

    if (cmp < 0) {
      node.left = remove(node.left, value);
    } else if (cmp > 0) {
      node.right = remove(node.right, value);
    } else {
      if (node.left == null)
        return node.right;
      else if (node.right == null)
        return node.left;
      else {
        BSTNode<T> temp = findMin(node.right);
        node.value = temp.value;
        node.right = remove(node.right, temp.value);
      }
    }
    return node;
  }

  private BSTNode<T> findMin(BSTNode<T> node) {
    while (node.left != null)
      node = node.left;
    return node;
  }

  public void removeKthLargest(int k) {
    if (k <= 0 || k > size)
      throw new NoSuchElementException();

    T value = findKthLargest(k);
    remove(value);
  }

  private T findKthLargest(int k) {
    Iterator<T> iter = new ReverseInOrderIterator();
    T value = null;
    while (k-- > 0 && iter.hasNext()) {
      value = iter.next();
    }
    if (k > 0)
      throw new NoSuchElementException();
    return value;
  }

  @Override
  public Iterator<T> iterator() {
    return new InOrderIterator();
  }

  private class InOrderIterator implements Iterator<T> {
    private Stack<BSTNode<T>> stack = new Stack<>();

    private InOrderIterator() {
      pushLeftChildren(root);
    }

    private void pushLeftChildren(BSTNode<T> node) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public T next() {
      BSTNode<T> node = stack.pop();
      pushLeftChildren(node.right);
      return node.value;
    }
  }

  private class ReverseInOrderIterator implements Iterator<T> {
    private Stack<BSTNode<T>> stack = new Stack<>();

    private ReverseInOrderIterator() {
      pushRightChildren(root);
    }

    private void pushRightChildren(BSTNode<T> node) {
      while (node != null) {
        stack.push(node);
        node = node.right;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public T next() {
      if (!hasNext())
        throw new NoSuchElementException();
      BSTNode<T> node = stack.pop();
      pushRightChildren(node.left);
      return node.value;
    }
  }

  private class PreOrderIterator implements Iterator<T> {
    private Stack<BSTNode<T>> stack = new Stack<>();

    private PreOrderIterator() {
      if (root != null) {
        stack.push(root);
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      BSTNode<T> node = stack.pop();
      if (node.right != null) {
        stack.push(node.right);
      }
      if (node.left != null) {
        stack.push(node.left);
      }
      return node.value;
    }
  }

  private class PostOrderIterator implements Iterator<T> {
    private Stack<BSTNode<T>> stack1 = new Stack<>();
    private Stack<BSTNode<T>> stack2 = new Stack<>();

    private PostOrderIterator() {
      if (root != null) {
        stack1.push(root);
        while (!stack1.isEmpty()) {
          BSTNode<T> node = stack1.pop();
          stack2.push(node);
          if (node.left != null) {
            stack1.push(node.left);
          }
          if (node.right != null) {
            stack1.push(node.right);
          }
        }
      }
    }

    @Override
    public boolean hasNext() {
      return !stack2.isEmpty();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return stack2.pop().value;
    }
  }
}
