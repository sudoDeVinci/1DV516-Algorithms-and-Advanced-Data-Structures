package src.Task5;


public class Task5 {

    public static void main(String[] args) {

        // Two empty trees should pass
        BSTIso tree1 = new BSTIso();
        BSTIso tree2 = new BSTIso();
        assert BSTIso.isIsomorphic(tree1.getRoot(), tree2.getRoot()) == true; 

        // Same single value trees pass
        tree1 = new BSTIso();
        tree2 = new BSTIso();
        tree1.add(5);
        tree2.add(5);
        assert BSTIso.isIsomorphic(tree1.getRoot(), tree2.getRoot()) == true;

        // Different single value trees fail
        tree1 = new BSTIso();
        tree2 = new BSTIso();
        tree1.add(5);
        tree2.add(6);
        assert BSTIso.isIsomorphic(tree1.getRoot(), tree2.getRoot()) == false;

        // Two trees with same values and same structure pass
        tree1 = new BSTIso();
        tree2 = new BSTIso();

        tree1.add(5);
        tree1.add(3);
        tree1.add(7);

        tree2.add(5);
        tree2.add(3);
        tree2.add(7);

        assert BSTIso.isIsomorphic(tree1.getRoot(), tree2.getRoot()) == true;

        // Two trees with same values but different structure fail
        tree1 = new BSTIso();
        tree2 = new BSTIso();

        tree1.add(5);
        tree1.add(3);
        tree1.add(7);

        tree2.add(5);
        tree2.getRoot().left = new BSTNode(7);
        tree2.getRoot().right = new BSTNode(3);

        assert BSTIso.isIsomorphic(tree1.getRoot(), tree2.getRoot()) == false;

        // Example from the assignment
        tree1 = new BSTIso();
        tree1.add(0);
        BSTNode root = tree1.getRoot();
        root.left = new BSTNode(1);
        root.right = new BSTNode(2);
        root.left.left = new BSTNode(3);
        root.left.right = new BSTNode(4);
        root.left.right.left = new BSTNode(5);
        root.right.left = new BSTNode(6);
        root.left.left = new BSTNode(7);

        tree2 = new BSTIso();
        tree2.add(0);
        BSTNode root2 = tree2.getRoot();
        root2.right = new BSTNode(1);
        root2.left = new BSTNode(2);
        root2.right.right = new BSTNode(3);
        root2.right.left = new BSTNode(4);
        root2.right.left.left = new BSTNode(5);
        root2.left.left = new BSTNode(6);
        root2.left.left.right = new BSTNode(7);

        assert BSTIso.isIsomorphic(root, root2) == true;

        System.out.println("All tests passed!");
    }

}
