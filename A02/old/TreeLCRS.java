package src;

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
   * Add child node to the parent node with only a parent's name.
   * @param parentName
   * @param childName
   */
  private TreeNode addChild(String parentName, String childName) {
    TreeNode parent = findNode(root, parentName);
    return addChild(parent, childName);
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
    if (node == null) {
      System.out.println("- empty");
    }

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

  public boolean hasRoot() {
      return root == null;
  }

  /**
   * Print the tree to show files/folder and subfiles/folders.
   */
  public void printTree() {
    printTree(this.root, 0);
  }

  public static void main(String[] args) {
    /**
     * Trees can be initialized using an empty constructor. 
     * The root node of this empty tree is null. 
     * They must be constructed via the makeDirTree static method.
     * This can be done with either a String Path, or a File object.
     * If the path to the file/Dir is not valid, throw a FileNotFoundException.
     */
    TreeLCRS tree = new TreeLCRS();
    try {
      tree = TreeLCRS.makeDirTree("example");
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }

    /**
     * We can print the tree to see its structure.
     */
    System.out.println("Current tree:");
    tree.printTree();
    System.out.println();

    /**
     * You can find a Node via its name. 
     * This will return the top-most Node matching the name.
     */
    TreeNode node01 = tree.getNodebyName("curseforge.bat");
    System.err.println("Got Node: " + node01.name);
    System.out.println();

    /**
     * You can also find a Node via the path.
     * Either as a File object or String
     */
    TreeNode node02 = tree.getNodeByPath("example/curseforge-cli/modpack/exported/cf.json");
    if (node02 != null) System.err.println("Got Node: " + node02.name);
    else System.out.println("empty");
    System.out.println();

    TreeNode node03 = tree.getNodeByPath("example/curseforge-cli/modpack/curseforge-cli.jar");
    if (node03 != null) System.err.println("Got Node: " + node03.name);
    else System.out.println("empty");
    System.out.println();

    /**
     * You can add a child to an existing Node using addChild().
     * This also returns the pointer to the Node Object. If null, add was not successful.
     * I thought of adding a generic add() whihc takes in the object path, but this seems redundant.
     * This could have been a method on the Node class itself but I left all methods within the Tree
     *  class for simplicity.
     */
    TreeNode node04 = tree.addChild(node03, "new Node!");
    System.out.println(node04 == null ? "Add unsuccessful.":"Successully added " + node04.name);
    tree.printTree(node03, 0);
    System.out.println();


    System.out.println("Tree After modifications: ");
    tree.printTree();
  }
}
