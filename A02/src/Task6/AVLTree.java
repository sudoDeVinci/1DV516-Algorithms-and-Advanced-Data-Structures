package src.Task6;

import java.util.Random;

public class AVLTree {
    private final Random rand = new Random();
    private AVLNode root;

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

    // This cast can end up bad but I am dumb.
    private Integer getValue(int size) {
        return this.rand.nextInt(size + 1);
    }

    /**
     * Construct randomly made trees of integers and return the root.
     * @param size
     * @return
     */
    public void randomizeTree(int size) {
        this.root = new AVLNode(size);
    
        int val;
        int sz = 0;


    
        while (sz < size) {
            val = getValue(size*2);
            if (rand.nextDouble() < 0.48) {
                if (val != size && this.contains(val)) {
                    this.remove(val);
                    sz -= 1;
                }
            } else {
                if(!this.contains(val)) {
                    this.add(val);
                    sz += 1;
                }
            }
            
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


    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        for(int i = 0; i < 10; i++) tree.add(i);
        tree.printTree();
        System.out.println('\n');

        tree = new AVLTree();
        tree.randomizeTree(10);
        tree.printTree();
    }
}
