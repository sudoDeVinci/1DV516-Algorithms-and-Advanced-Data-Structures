package src.Task6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class AVLTree implements Iterable<Integer>{
    private final Random rand = new Random();
    private AVLNode root;


    private IterType iterType = IterType.INORDER;

    public static enum IterType{
      INORDER,
      REVINORDER,
      PREORDER,
      POSTORDER
    }

    public int size() {
      int count = 0;
      InOrderIterator in = new InOrderIterator();
      while (in.hasNext()){
        in.next();
        count++;
      }  
      return count;
    }

    public void setIterType(IterType type) {
        this.iterType = type;
    }
    public int height() {
        return height(root);
    }

    private int height(AVLNode n) {
        return (n == null) ? -1 : n.height;
    }

    public void add(int key) {
        root = add(root, key);
    }

    private AVLNode add(AVLNode n, Integer key) {
        if (n == null) return new AVLNode(key);
        
        if (n.value > key) n.left = add(n.left, key);
        else if (n.value < key) n.right = add(n.right, key);

        return balance(n);
    }

    private int max (int a, int b) {
        return (a > b) ? a : b;
    }

    private void updateHeight(AVLNode node) {
        node.height = max(height(node.left), height(node.right)) + 1;
    }

    private AVLNode RL(AVLNode node) {
        AVLNode leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        updateHeight(node);
        updateHeight(leftChild);
        return leftChild;
    }

    private AVLNode RLL(AVLNode node) {
        node.left = RR(node.left);
        return RL(node); 
    }

    private AVLNode RR(AVLNode node) {
        AVLNode rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        updateHeight(node);
        updateHeight(rightChild);
        return rightChild;
    }

    private AVLNode RRR(AVLNode node) {
        node.right = RL(node.right);
        return RR(node);
    }

    private AVLNode balance(AVLNode n) {
        if(n == null) return n;

        if ( (height(n.left) - height(n.right)) > 1) {
            if ( height(n.left.left) >= height(n.left.right) ) {
                n = RL(n);
            } else {
                n = RLL(n);
            }
        } else if ( (height(n.right) - height(n.left) > 1) ) {
            if ( (height(n.right.right) >= height(n.right.left)) ) {
                n = RR(n);
            } else {
                n = RRR(n);
            }
        }

        updateHeight(n);
        return n;
    }

    /**
     * Check if a subtree contains a value.
     * @param node
     * @param value
     * @return
     */
    private boolean contains(AVLNode node, Integer value) {
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
    public boolean contains(Integer value) {
        return contains(this.root, value);
    }

    private int findMin(AVLNode node) {
        if(node.left == null) return node.value;
        else return findMin(node.left);
    }

    /**
     * remove a value from the tree.
     * @param value
     */
    public void remove(Integer value) {
        root = remove(root, value);
    }

    private AVLNode remove(AVLNode node, Integer value) {
        if (node == null) return null; // Value not found

        if (node.value > value) node.left = remove(node.left, value);
        else if (node.value < value) node.right = remove(node.right, value);
        else if (node.left != null && node.right != null) {
            node.value = findMin(node.right);
            node.right = remove(node.right, node.value);
        }else {
            if (node.left != null) node = node.left;
            else node = node.right;
        }

        return balance(node);

    }

    /**
     * Construct randomly made trees of integers and return the root.
     * @param size
     * @return
     */
    public void randomizeTree(int size) {
        this.root = null;
        ArrayList<Integer> values = new ArrayList<>();
    
        // Fill the array with values from 1 to size
        for (int i = 0; i < (int)(size*1.5); i++) {
            values.add(i);
        }
    
        // Shuffle the array to randomize the values
        Collections.shuffle(values);
    
        // Insert the values into the AVL tree
        for (Integer value : values) {
            this.add(value);
        }
        
        int count = 0;

        // Randomly delete items from the AVL tree
        while (count < size) {
            int i = rand.nextInt(values.size()-1);
            remove(values.get(i));
            values.remove(i);
            count++;
        }
    }
    

    private void printTree(String prefix, AVLNode node, boolean isTail) {
        int nodeName = node.value;
        String nodeConnection = isTail ? "└── " : "├── ";
        System.out.println(prefix + nodeConnection + nodeName);
    
        AVLNode[] children = getChildren(node);
        if (children!=null) {
            for (int i = 0; i < children.length; i++) {
                String newPrefix = prefix + (isTail ? "    " : "│   ");
                printTree(newPrefix, children[i], i == children.length - 1);
            }
        }
      }
  
      public void printTree(AVLNode n) {
        printTree("", n, true);
      }
  
      public void printTree() {
        printTree("", root, true);
      }
    
    private AVLNode[] getChildren(AVLNode node) {
  
        if (node.left != null && node.right != null) {
            return new AVLNode[]{node.left, node.right};
        }
  
        if(node.left != null || node.right != null) {
            AVLNode outNode = node.left == null ? node.right:node.left;
            return new AVLNode[]{outNode};
        }
  
        else return null;
    }

    private class InOrderIterator implements Iterator<Integer> {
        private final Deque<AVLNode> stack = new Deque<>();
    
        private InOrderIterator() {
          pushLeftChildren(root);
        }
    
        private void pushLeftChildren(AVLNode node) {
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
          AVLNode node = stack.removeFirst();
          pushLeftChildren(node.right);
          return node.value;
        }
    }

    private class ReverseInOrderIterator implements Iterator<Integer> {
        private final Deque<AVLNode> stack = new Deque<>();
    
        private ReverseInOrderIterator() {
          pushRightChildren(root);
        }
    
        private void pushRightChildren(AVLNode node) {
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
          AVLNode node = stack.removeFirst();
          pushRightChildren(node.left);
          return node.value;
        }
    }

    
    private class PreOrderIterator implements Iterator<Integer> {
        private final Deque<AVLNode> stack = new Deque<>();
    
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
          AVLNode node = stack.removeFirst();
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
        private final Deque<AVLNode> stack1 = new Deque<>();
        private final Deque<AVLNode> stack2 = new Deque<>();
    
        private PostOrderIterator() {
          if (root != null) {
            stack1.addFirst(root);
            while (!stack1.isEmpty()) {
              AVLNode node = stack1.removeFirst();
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



    public static void main(String[] args) {
      AVLTree bst = new AVLTree();

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
      assert bst.height() == 3 : "Expected height 3, got " + bst.height();

      // Test size
      System.out.println("Size: " + bst.size());
      assert bst.size() == 8 : "Expected size 8, got " + bst.size();

      // test removes
      int val = 8; 
      bst.remove(val);
      System.out.println(!bst.contains(8) ? "Succesfully removed value "+val+"!" : "Remove of value " + val + " unsuccessful!");
      assert !bst.contains(8) : "BST still contains removed value 8";

      bst.printTree();
      
      // Test height
      System.out.println("Height: " + bst.height());
      assert bst.height() == 2 : "Expected height 2, got " + bst.height();

      // Test size
      System.out.println("Size: " + bst.size());
      assert bst.size() == 7 : "Expected size 7, got " + bst.size();

      // Test contains
      System.out.println("Contains 4: " + bst.contains(4)); // Should be true
      assert bst.contains(4) : "BST does not contain value 4";
      System.out.println("Contains 11: " + bst.contains(11)); // Should be false
      assert !bst.contains(11) : "BST contains value 11";

      // Set the traversal type (optional)
      bst.setIterType(AVLTree.IterType.INORDER);

      // Iterate over the elements
      System.out.println("In-Order Traversal:");
      for (Integer value : bst) {
          System.out.print(value + " ");
      }
      System.out.println();

      // Set the traversal type (optional)
      bst.setIterType(AVLTree.IterType.PREORDER);

      // Iterate over the elements
      System.out.println("Pre-Order Traversal:");
      for (Integer value : bst) {
          System.out.print(value + " ");
      }
      System.out.println();

      // Set the traversal type (optional)
      bst.setIterType(AVLTree.IterType.POSTORDER);

      // Iterate over the elements
      System.out.println("Post-Order Traversal:");
      for (Integer value : bst) {
          System.out.print(value + " ");
      }
      System.out.println();

      // Removing 5, show rotations.
      bst.remove(5);
      bst.printTree();

      /*
       *           6
       *        /     \
       *       3        7
       *     /   \       \
       *    2     4       9
       */

      // Test contains
      System.out.println("Contains 5: " + bst.contains(5)); // Should be falseT still contains value 5 after rotation";
      assert !bst.contains(5) : "BST still contains value 5 after rotation";
    }
}
