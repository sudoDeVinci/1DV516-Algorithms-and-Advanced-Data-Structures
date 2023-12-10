package src;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTTree<T extends Comparable<T>> extends BST<T, BSTNode<T>> implements Iterable<T>{
    private BSTNode<T> root;
    private IterType iterType = IterType.INORDER;

    public static enum IterType{
      INORDER,
      REVINORDER,
      PREORDER,
      POSTORDER
    }

    public BSTNode<T> getRoot() {
      return this.root;
    }

    public void setIterType(IterType type) {
        this.iterType = type;
    }

    /**
     * Get the size
     * @return
     */
    public int size() {
      int count = 0;
      InOrderIterator in = new InOrderIterator();
      while (in.hasNext()){
        in.next();
        count++;
      }  
      return count;
    }

    /**
     * Check if BST is empty.
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Get the height recursively of a subtree.
     * @param node
     * @return
     */
    private int height(BSTNode<T> node) {
        if (node == null)
            return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Public version to get height of whole tree.
     * @return
     */
    public int height() {
        return height(root);
    }

    /**
     * Check if a subtree contains a value.
     * @param node
     * @param value
     * @return
     */
    private boolean contains(BSTNode<T> node, T value) {
        if (node == null) return false;

        int cmp = value.compareTo(node.value);

        if (cmp < 0) return contains(node.left, value);
        if (cmp > 0) return contains(node.right, value);
        
        return true;
    }

    /**
     * Check if the entire tree contains a given value.
     * @param value
     * @return
     */
    public boolean contains(T value) {
        return contains(this.root, value);
    }

    /**
     * Add a node to the tree.
     * The return of the top-most recursive call will set the root node.
     *   If the input node is null, we return our set node value. Other wise we 
     *   return the parent itself.
     * The bottom-most recursive call will set the added value for the leaf.
     * Eventually, the node called will be null, meaning we hit a leaf. This will return the
     *   new node we want to input.
     * @param parent Usually the root node
     * @param value
     * @return
     */
    private BSTNode<T> add(BSTNode<T> parent, T value) {
        if (parent == null) return new BSTNode<T>(value);
    
        int cmp = value.compareTo(parent.value);
    
        if (cmp < 0) parent.left = add(parent.left, value);
        else parent.right = add(parent.right, value);
    
        return parent;
    }

    /**
     * Add a given value to the Tree.
     * @param value
     * @return
     */
    public void add(T value) {
        root = add(root, value);
    }

    /**
     * Remove a value from the tree.
     * @param value
     * @return
     */
    public void remove(T value) {
        root = remove(root, value);
    }

    /**
     * 
     * @param node
     * @param value
     * @return
     */
    private BSTNode<T> remove(BSTNode<T> node, T value) {
      if (node == null) return null;

      int cmp = value.compareTo(node.value);

      if (cmp < 0) node.left = remove(node.left, value);
      else if (cmp > 0) node.right = remove(node.right, value);
      else {
        if (node.right == null) return node.left;
        else if (node.left == null) return node.right;

        node.value = findMin(node.right);
        node.right = remove(node.right, node.value);
      }

      return node;
    }

    /**
     * Find the minimum node in a given subtree.
     * @param node
     * @return
     */
    private T findMin(BSTNode<T> node) {
        if (node.left == null) return node.value;
        else return findMin(node.left);
    }


    private class InOrderIterator implements Iterator<T> {
        private Deque<BSTNode<T>> stack = new Deque<>();
    
        private InOrderIterator() {
          pushLeftChildren(root);
        }
    
        private void pushLeftChildren(BSTNode<T> node) {
          while (node != null) {
            stack.addFirst(node);
            node = node.left;
          }
        }
    
        @Override
        public boolean hasNext() {
          return !stack.isEmpty();
        }
    
        @Override
        public T next() {
          BSTNode<T> node = stack.removeFirst();
          pushLeftChildren(node.right);
          return node.value;
        }
    }

    private class ReverseInOrderIterator implements Iterator<T> {
        private Deque<BSTNode<T>> stack = new Deque<>();
    
        private ReverseInOrderIterator() {
          pushRightChildren(root);
        }
    
        private void pushRightChildren(BSTNode<T> node) {
          while (node != null) {
            stack.addFirst(node);
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
          BSTNode<T> node = stack.removeFirst();
          pushRightChildren(node.left);
          return node.value;
        }
    }

    
    private class PreOrderIterator implements Iterator<T> {
        private Deque<BSTNode<T>> stack = new Deque<>();
    
        private PreOrderIterator() {
          if (root != null) {
            stack.addFirst(root);
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
          BSTNode<T> node = stack.removeFirst();
          if (node.right != null) {
            stack.addFirst(node.right);
          }
          if (node.left != null) {
            stack.addFirst(node.left);
          }
          return node.value;
        }
    }

    private class PostOrderIterator implements Iterator<T> {
        private Deque<BSTNode<T>> stack1 = new Deque<>();
        private Deque<BSTNode<T>> stack2 = new Deque<>();
    
        private PostOrderIterator() {
          if (root != null) {
            stack1.addFirst(root);
            while (!stack1.isEmpty()) {
              BSTNode<T> node = stack1.removeFirst();
              stack2.addFirst(node);
              if (node.left != null) {
                stack1.addFirst(node.left);
              }
              if (node.right != null) {
                stack1.addFirst(node.right);
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
          return stack2.removeFirst().value;
        }
    }

    /**
     * Return an iterator according to which iteration type has been previously set.
     */
    @Override
    public Iterator<T> iterator() throws NoSuchElementException{
        Iterator<T> iter;

        switch(this.iterType) {
            case INORDER -> iter = new InOrderIterator();
            case REVINORDER -> iter = new ReverseInOrderIterator();
            case POSTORDER -> iter = new PostOrderIterator();
            case PREORDER -> iter  = new PreOrderIterator();
            default -> throw new NoSuchElementException();
        }
        
        return iter;
    }

    public void removeKthLargest(int k) {
        if (k <= 0 || k > size())
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

  private void printTree(String prefix, BSTNode<T> node, boolean isTail) {
    T nodeName = node.value;
    String nodeConnection = isTail ? "└── " : "├── ";
    System.out.println(prefix + nodeConnection + nodeName);

    BSTNode<T>[] children = getChildren(node);
    if (children!=null) {
        for (int i = 0; i < children.length; i++) {
            String newPrefix = prefix + (isTail ? "    " : "│   ");
            printTree(newPrefix, children[i], i == children.length - 1);
        }
    }
  }

  public void printTree(BSTNode<T> n) {
    printTree("", n, true);
  }

  public void printTree() {
    printTree("", root, true);
  }
  
  @SuppressWarnings("unchecked")
  private BSTNode<T>[] getChildren(BSTNode<T> node) {

      if (node.left != null && node.right != null) {
          return new BSTNode[]{node.left, node.right};
      }

      if(node.left != null || node.right != null) {
          BSTNode<T> outNode = node.left == null ? node.right:node.left;
          return new BSTNode[]{outNode};
      }

      else return null;
  }

    public static void main(String[] args) {
        BSTTree<Integer> bst = new BSTTree<>();

        // Add elements to the BST
        bst.add(5);
        bst.add(3);
        bst.add(7);
        bst.add(2);
        bst.add(4);
        bst.add(6);
        bst.add(8);
        bst.add(9);
        bst.printTree();

        /*
         *            5
         *        /       \
         *       3         7
         *     /   \     /   \
         *    2     4   6     8
         *                      \
         *                       9
         */

        // Test height
        System.out.println("Height: " + bst.height());

        // Test size
        System.out.println("Size: " + bst.size());

        // test removes
        int val = 8; 
        bst.remove(val);
        System.out.println(!bst.contains(8) ? "Succesfully removed value "+val+"!" : "Remove of value " + val + " unsuccessful!");

        bst.printTree();
        
        // Test height
        System.out.println("Height: " + bst.height());

        // Test size
        System.out.println("Size: " + bst.size());
        // Test contains
        System.out.println("Contains 4: " + bst.contains(4)); // Should be true
        System.out.println("Contains 11: " + bst.contains(11)); // Should be false

        // Set the traversal type (optional)
        bst.setIterType(BSTTree.IterType.INORDER);

        // Iterate over the elements
        System.out.println("In-Order Traversal:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Set the traversal type (optional)
        bst.setIterType(BSTTree.IterType.PREORDER);

        // Iterate over the elements
        System.out.println("Pre-Order Traversal:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Set the traversal type (optional)
        bst.setIterType(BSTTree.IterType.POSTORDER);

        // Iterate over the elements
        System.out.println("Post-Order Traversal:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Remove 2nd largest value (7)
        bst.removeKthLargest(2);
        

        // Test removal of kth largest element
         bst.setIterType(BSTTree.IterType.INORDER);
        System.out.println("In-Order Traversal after removing 2nd largest:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Try removing a non-existent kth largest element
        System.out.println("Attempting to remove 10th largest (Should throw error): ");
        try {
            bst.removeKthLargest(10); // This will throw an exception
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
