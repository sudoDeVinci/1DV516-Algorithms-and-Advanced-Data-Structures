package src.Task4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST implements Iterable<Integer> {
    private BSTNode root;
    private IterType iterType = IterType.INORDER;

    public static enum IterType{
      INORDER,
      REVINORDER,
      PREORDER,
      POSTORDER
    }

    public BSTNode getRoot() {
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
    private int height(BSTNode node) {
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
     * Check if the entire tree contains a given value.
     * @param value
     * @return
     */
    public boolean contains(int value) {
      return root.contains(value);
    }

    /**
     * Add a given value to the Tree.
     * @param value
     * @return
     */
    public void add(int value) {
      root.add(new BSTNode(value));
    }

    /**
     * Remove a value from the tree.
     * @param value
     * @return
     */
    public void remove(int value) {
        root = root.remove(root, value);
    }
    /**
     * Find the minimum node in a given subtree.
     * @param node
     * @return
     */
    public int min() {
        if (root.left == null) return root.value;
        else return root.min().value;
    }


    private class InOrderIterator implements Iterator<Integer> {
        private final Deque<BSTNode> stack = new Deque<>();
    
        private InOrderIterator() {
          pushLeftChildren(root);
        }
    
        private void pushLeftChildren(BSTNode node) {
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
        public Integer next() {
          BSTNode node = stack.removeFirst();
          pushLeftChildren(node.right);
          return node.value;
        }
    }

    private class ReverseInOrderIterator implements Iterator<Integer> {
        private final Deque<BSTNode> stack = new Deque<>();
    
        private ReverseInOrderIterator() {
          pushRightChildren(root);
        }
    
        private void pushRightChildren(BSTNode node) {
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
        public Integer next() {
          if (!hasNext())
            throw new NoSuchElementException();
          BSTNode node = stack.removeFirst();
          pushRightChildren(node.left);
          return node.value;
        }
    }

    
    private class PreOrderIterator implements Iterator<Integer> {
        private final Deque<BSTNode> stack = new Deque<>();
    
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
        public Integer next() {
          if (!hasNext()) {
            throw new NoSuchElementException();
          }
          BSTNode node = stack.removeFirst();
          if (node.right != null) {
            stack.addFirst(node.right);
          }
          if (node.left != null) {
            stack.addFirst(node.left);
          }
          return node.value;
        }
    }

    private class PostOrderIterator implements Iterator<Integer> {
        private final Deque<BSTNode> stack1 = new Deque<>();
        private final Deque<BSTNode> stack2 = new Deque<>();
    
        private PostOrderIterator() {
          if (root != null) {
            stack1.addFirst(root);
            while (!stack1.isEmpty()) {
              BSTNode node = stack1.removeFirst();
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
        public Integer next() {
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
    public Iterator<Integer> iterator() throws NoSuchElementException{
        Iterator<Integer> iter;

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
    
        int value = findKthLargest(k);
        remove(value);
      }
    

    private int findKthLargest(int k) {
      Iterator<Integer> iter = new ReverseInOrderIterator();
      Integer value = null;
      while (k-- > 0 && iter.hasNext()) {
          value = iter.next();
      }
      if (k > 0)
          throw new NoSuchElementException();
      return value;
    }

  private void printTree(String prefix, BSTNode node, boolean isTail) {
    int nodeName = node.value;
    String nodeConnection = isTail ? "└── " : "├── ";
    System.out.println(prefix + nodeConnection + nodeName);

    BSTNode[] children = getChildren(node);
    if (children!=null) {
        for (int i = 0; i < children.length; i++) {
            String newPrefix = prefix + (isTail ? "    " : "│   ");
            printTree(newPrefix, children[i], i == children.length - 1);
        }
    }
  }

  public void printTree(BSTNode n) {
    printTree("", n, true);
  }

  public void printTree() {
    printTree("", root, true);
  }
  
  private BSTNode[] getChildren(BSTNode node) {

      if (node.left != null && node.right != null) {
          return new BSTNode[]{node.left, node.right};
      }

      if(node.left != null || node.right != null) {
          BSTNode outNode = node.left == null ? node.right:node.left;
          return new BSTNode[]{outNode};
      }

      else return null;
  }

    public static void main(String[] args) {
        BST bst = new BST();

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
        bst.setIterType(BST.IterType.INORDER);

        // Iterate over the elements
        System.out.println("In-Order Traversal:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Set the traversal type (optional)
        bst.setIterType(BST.IterType.PREORDER);

        // Iterate over the elements
        System.out.println("Pre-Order Traversal:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Set the traversal type (optional)
        bst.setIterType(BST.IterType.POSTORDER);

        // Iterate over the elements
        System.out.println("Post-Order Traversal:");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Remove 2nd largest value (7)
        bst.removeKthLargest(2);
        

        // Test removal of kth largest element
         bst.setIterType(BST.IterType.INORDER);
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
