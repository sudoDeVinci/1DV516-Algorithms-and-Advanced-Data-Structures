package src.Task3;

import java.io.FileNotFoundException;

public class Task3 {
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
