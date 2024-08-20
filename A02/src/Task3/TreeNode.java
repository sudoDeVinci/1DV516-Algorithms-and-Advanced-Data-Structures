package src.Task3;

class TreeNode {
  String name; // both files or directories work, it's a name
  TreeNode firstChild; // left-most child
  TreeNode nextSibling; // right sibling

  public TreeNode(String name) {
    this.name = name;
    this.firstChild = null;
    this.nextSibling = null;
  }
}
