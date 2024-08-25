package src.Task4;

public class Task4 {
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
        assert bst.contains(7) : "BST still contains value second largest value (7)";
        

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
