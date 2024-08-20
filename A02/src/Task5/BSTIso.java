package src.Task5;

public class BSTIso {

    /**
     * Given the root Nodes of two trees, check them for "strict" isomorphism.
     * @param root1
     * @param root2
     * @return
     */
    public static boolean isIsomorphic(BSTNode root1, BSTNode root2) {
        /**
         * If both roots are null, return true.
         */
        if (root1 == null && root2 == null) {
            return true;
        }
        /**
         * If only one is null, return false.
         */
        if (root1 == null || root2 == null) {
            return false;
        }

        /**
         * If the values are not the same, return false.
         */
        if (root1.getValue() != root2.getValue()) {
            return false;
        }

        /**
         * Check all combinations of left and right nodes for the wo input nodes.
         * We don't need to explicitly return True because that will happen when a leaf is reached.
         * Either the nodes are exactly the same, or they are swapped.
         */
        return (isIsomorphic(root1.getLeft(), root2.getLeft()) && isIsomorphic(root1.getRight(), root2.getRight()) ||
                isIsomorphic(root1.getLeft(), root2.getRight()) && isIsomorphic(root1.getRight(), root2.getLeft()));
    }
}
