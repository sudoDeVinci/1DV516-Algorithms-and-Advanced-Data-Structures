package src.Task5;

public class BSTIso extends BST {

    /**
     * Given the root Nodes of two trees, check them for "strict" isomorphism.
     * O(min(m,n)), where m and n are the number of nodes in the two trees. 
     * @param root1 - Root of the first tree.
     * @param root2 - Root of the second tree.
     * @return
     */
    public static boolean isIsomorphic(BSTNode root1, BSTNode root2) {
        /**
         * If both roots are null, return true.
         */
        if (root1 == null && root2 == null) return true;
        /**
         * If only one is null, return false.
         */
        if (root1 == null || root2 == null) return false;

        /**
         * If the values are not the same, return false.
         */
        if (root1.value != root2.value) return false;

        /**
         * Check all combinations of left and right nodes for the wo input nodes.
         * We don't need to explicitly return True because that will happen when a leaf is reached.
         * Either the nodes are exactly the same, or they are swapped.
         */
        return (isIsomorphic(root1.left, root2.left) && isIsomorphic(root1.right, root2.right) ||
                isIsomorphic(root1.left, root2.right) && isIsomorphic(root1.right, root2.left));
    }
}
