package src;

import java.io.File;

/**
 * Class to represent the subdirectories & files within as a tree structure.
 */
public class TreeLCRS {
  TreeNode root;

  public TreeLCRS(String rootName) {
    root = new TreeNode(rootName);
  }

  /**
   * Get a given node by its file path.
   * @param path 
   * @return
   */
  public TreeNode getnodeByPath(String path) {
    String[] parts = path.split("[/\\\\]");
    TreeNode current = root;

    for (String part : parts) {
      current = findNode(current, part);
      if (current == null) return null;
    }

    return current;
  }

  /**
   * Add child node to the parent node.
   * @param parentName
   * @param childName
   */
  TreeNode addChild(TreeNode parent, String childName) {
    TreeNode child = new TreeNode(childName);
    
    if (parent == null) return null;

    if (parent.firstChild == null) {
      parent.firstChild = child;
    } else {
      TreeNode sibling = parent.firstChild;
      while (sibling.nextSibling != null) {
        sibling = sibling.nextSibling;
      }
      sibling.nextSibling = child;
    }

    return child;
  }

  /**
   * Add child node to the parent node with only a parent's name.
   * @param parentName
   * @param childName
   */
  TreeNode addChild(String parentName, String childName) {
    TreeNode parent = findNode(root, parentName);
    return addChild(parent, childName);
  }

  /**
   * Find a Node with a matching name.
   * @param node
   * @param name
   * @return
   */
  private TreeNode findNode(TreeNode node, String name) {
    if (node == null)
      return null;
    if (node.name.equals(name))
      return node;

    TreeNode found = findNode(node.firstChild, name);
    if (found != null)
      return found;

    return findNode(node.nextSibling, name);
  }

  /**
   * Given a path, populate the tree with its files and subdirectories.
   * @param path
   * @return
   */
  public static TreeLCRS makeDirTree(String path) {
    File rootDir = new File(path);

    TreeLCRS tree = new TreeLCRS(rootDir.getName());
    populateTree(tree.root, rootDir, tree);
    return tree;
  }

  /**
   * Populate the tree with the files and folders of a file system.
   * @param treeNode
   * @param file
   * @param tree
   */
  private static void populateTree(TreeNode treeNode, File file, TreeLCRS tree) {
    /**
     * List files. If the file input is a file and not a directory, returns null.
     */
    File[] files = file.listFiles();

    if (files == null) return;

    for (File f : files) {
      /**
       * Add the child to the tree.
       */
      TreeNode fileNode = tree.addChild(treeNode, f.getName());
      populateTree(fileNode, f, tree);
    }
  }

  /**
   * Print the tree to show files/folder and subfiles/folders.
   */
  public void printTree(TreeNode node, int depth) {
    if (node == null) return;

    TreeNode child = node.firstChild;

    for (int i = 0; i < depth; i++) {
      System.out.print("  ");
    }
    if(depth!=0) {
      if (child != null) {
        System.out.print("└");
      } else {
        System.out.print("├");
      }
    }

    System.out.println(node.name);
    
    while (child != null) {
        printTree(child, depth + 1);
        child = child.nextSibling;
    }
  }

  /**
   * Print the tree to show files/folder and subfiles/folders.
   */
  public void printTree() {
    printTree(this.root, 0);
  }

  public static void main(String[] args) {
    TreeLCRS tree = TreeLCRS.makeDirTree("example");
    tree.printTree();
  }
}
