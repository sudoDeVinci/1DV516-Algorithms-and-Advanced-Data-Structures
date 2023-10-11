import java.io.File;
/**
 * Class to represent the subdirectories & files within as a tree structure.
 */
public class TreeLCRS {
  TreeNode root;

  public TreeLCRS(String rootName) {
    root = new TreeNode(rootName);
  }

  public void addChild(String parentName, String childName) {
    TreeNode parent = findNode(root, parentName);
    if (parent != null) {
      TreeNode child = new TreeNode(childName);
      if (parent.firstChild == null) {
        parent.firstChild = child;
      } else {
        TreeNode sibling = parent.firstChild;
        while (sibling.nextSibling != null) {
          sibling = sibling.nextSibling;
        }
        sibling.nextSibling = child;
      }
    }
  }

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

  public static TreeLCRS makeDirTree(String path) {
    File rootDir = new File(path);

    TreeLCRS tree = new TreeLCRS(rootDir.getName());
    populateTree(tree.root, rootDir, tree);
    return tree;
  }

  private static void populateTree(TreeNode treeNode, File file, TreeLCRS tree) {
    File[] files = file.listFiles();
    if (files != null) {
      for (File f : files) {
        tree.addChild(treeNode.name, f.getName());
        populateTree(tree.findNode(tree.root, f.getName()), f, tree);
      }
    }
  }
}
