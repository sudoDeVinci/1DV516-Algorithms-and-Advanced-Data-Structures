package src.Task3;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class to represent the subdirectories & files within as a tree structure.
 */
public class TreeLCRS {
  TreeNode root;

  public TreeLCRS() {}

  private TreeLCRS(String rootName) {
    root = new TreeNode(rootName);
  }

  /**
   * Get a given node by its file path.
   * @param path 
   * @return
   */
  public TreeNode getNodeByPath(String path) {
    String[] parts = path.split("[/\\\\]");
    TreeNode current = root;

    for (String part : parts) {
      current = findNode(current, part);
      if (current == null) return null;
    }

    return current;
  }

  /**
   * Get a given node by its file path.
   * @param path 
   * @return
   */
  public TreeNode getNodeByPath(File filePath) {
    String path = filePath.toString();
    String[] parts = path.split("[/\\\\]");
    TreeNode current = root;

    for (String part : parts) {
      current = findNode(current, part);
      if (current == null) return null;
    }

    return current;
  }

  /**
   * Return the first (highest) Node with the matching name to the one given.
   * @param name
   * @return
   */
  public TreeNode getNodebyName(String name) {
    return findNode(root, name);
  }

  /**
   * Add child node to the parent node.
   * @param parentName
   * @param childName
   */
  public TreeNode addChild(TreeNode parent, String childName) {

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
   * Find a Node with a matching name.
   * @param node
   * @param name
   * @return
   */
  public TreeNode findNode(TreeNode node, String name) {
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
  public static TreeLCRS makeDirTree(String path) throws FileNotFoundException{
    File rootDir = new File(path);

    if(!rootDir.exists()) throw new FileNotFoundException();

    TreeLCRS tree = new TreeLCRS(rootDir.getName());
    populateTree(tree.root, rootDir, tree);
    return tree;
  }

  /**
   * Given a path, populate the tree with its files and subdirectories.
   * @param path
   * @return
   */
  public static TreeLCRS makeDirTree(File rootDir) throws FileNotFoundException{

    if(!rootDir.exists()) throw new FileNotFoundException();

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
    if (node == null || node.firstChild == null) System.out.println("- empty");

    TreeNode child = node.firstChild;

    for (int i = 0; i < depth; i++) {
      System.out.print("   ");
    }
    if(depth!=0) {
      if (child != null) {
        System.out.print("└ ");
      } else {
        System.out.print("├ ");
      }
    }

    System.out.println(node.name);
    
    while (child != null) {
        printTree(child, depth + 1);
        child = child.nextSibling;
    }
  }

  /**
   * Check if the tree has a root.
   * @return true if the tree has a root, false otherwise.
   */
  public boolean hasRoot() {
      return root == null;
  }

  /**
   * Print the tree to show files/folder and subfiles/folders.
   */
  public void printTree() {
    printTree(this.root, 0);
  }
}
