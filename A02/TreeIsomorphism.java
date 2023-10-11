import java.util.Random;

/**
 * Class deedicated to checking tree isomorphism
 */
public class TreeIsomorphism <T extends Comparable<T>> {

    private static int SAMPLES = 300;

    /** 
     * Get a random bounded integer.
     * @param rand
     * @param size
     * @return
     */

    @SuppressWarnings("uncehcked")
    private static <T extends Comparable<T>> T getValue(Random rand, int size) {
        return (T) Integer.valueOf(rand.nextInt(size)+1);
    }

    /**
     * Add a Node to an exsiting Tree.
     * @param parent
     * @param value
     */
    private static <T extends Comparable<T>> void addNode(BSTNode<T> parent, T value) {
        int cmp = value.compareTo(parent.value);
        if (cmp < 0) {
                if (parent.left == null) {
                    parent.left = new BSTNode<>(value);
                } else {
                    addNode(parent.left, value);
                }
        } else if (cmp > 0) {
            if (parent.right == null) {
                parent.right = new BSTNode<>(value);
            } else {
                addNode(parent.right, value);
            }
        } else {
            return;
        }
    } 

    /**
     * Construct randomly made trees of integers.
     * @param size
     * @return
     */
    public static <T extends Comparable<T>> BSTNode<T> constructRandomTree(int size) {
        Random random = new Random();
        BSTNode<T> root = new BSTNode<T>(getValue(random, size));

        for (int i = 1; i<size; i++) {
            addNode(root, getValue(random, size));
        }
        return root;
    } 

    /**
     * Given the root Nodes of two trees, check them for "strict" isomorphism.
     * @param root1
     * @param root2
     * @return
     */
    public static <T extends Comparable<T>> boolean isIsomorphic(BSTNode<T> root1, BSTNode<T> root2) {
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
        if (root1.value != root2.value) {
            return false;
        }

        /**
         * Check all combinations of left and right nodes for the two input nodes.
         * We don't need to explicitly return True because that will happen when a leaf is reached.
         * Either the nodes are exactly the same, or they are swapped.
         */
        return (isIsomorphic(root1.left, root2.left) && isIsomorphic(root1.right, root2.right) ||
                isIsomorphic(root1.left, root2.right) && isIsomorphic(root1.right, root2.left));
    }

    /**
     * Testing the ability to check if two trees are isomorphic.
     * 
     */
    public static void testIsIsomorphic() {

        System.out.println("Creating two Random Trees: ");
        BSTNode<Integer> tree1 = constructRandomTree(50);
        BSTNode<Integer> tree2 = constructRandomTree(50);

        System.out.println("\t>> Are the BSTs isomorphic: " + isIsomorphic(tree1, tree2));
        
        System.out.println("Testing Two copies of the same Tree: ");
        BSTNode<Integer> tree3 = tree1;
        System.out.println("\t>> Are the BSTs isomorphic: " + isIsomorphic(tree1, tree3));


        System.out.println("Manually creating two Isomorphic Trees");
        BSTNode<String> TreeA = new BSTNode<String>("A");
        TreeA.left = new BSTNode<String>("B");
        TreeA.right = new BSTNode<String>("C");
        TreeA.left.left = new BSTNode<String>("D");
        TreeA.left.right  =new BSTNode<String>("E");
        TreeA.left.right.left = new BSTNode<String>("F");
        TreeA.right.left = new BSTNode<String>("G");
        TreeA.right.left.left = new BSTNode<String>("H");

        BSTNode<String> TreeB = new BSTNode<String>("A");
        TreeB.left = new BSTNode<String>("C");
        TreeB.left.left = new BSTNode<String>("G");
        TreeB.left.left.right = new BSTNode<String>("H");
        TreeB.right = new BSTNode<String>("B");
        TreeB.right.left = new BSTNode<String>("E");
        TreeB.right.right = new BSTNode<String>("D");
        TreeB.right.left.left = new BSTNode<String>("F");

        System.out.println("\t>> Are the BSTs isomorphic: " + isIsomorphic(TreeA, TreeB));
    }

    private static <T extends Comparable<T>> double execute(BSTNode<T> A, BSTNode<T> B) {
        double[] samples = new double[SAMPLES];
        Timeit timer;

        for (int x = 0; x < SAMPLES; x++) {
            timer = new Timeit((args) -> {
                BSTNode<T> a = (BSTNode<T>) args[0];
                BSTNode<T> b = (BSTNode<T>) args[1];
                TreeIsomorphism.isIsomorphic(a,b);
            });
            samples[x] = timer.measureMicros(A, B);
        }

        return Util.sampleMean(samples);
    }

    private static <T extends Comparable<T>> Double[] getTimes(Integer[] sizes, BSTNode<T> A, BSTNode<T> B) {
        int length = sizes.length;
        Double[] times = new Double[length];

        for(int i = 0; i < length; i++) {
            A = constructRandomTree(sizes[i]);
            B = constructRandomTree(sizes[i]);
            times[i] = execute(A,B);
        }

        return times;
    }

    public static void testComplexity() {
        Random random = new Random();
        Plotter plt;
        Integer[] sizes = new Integer[]{ 100, 200, 300, 400, 500, 600,
            700, 800, 900, 1000, 1100, 1200,
            1300, 1400, 1500, 1600, 1700, 1800,
            1900, 2000};
        
        BSTNode<Integer> A = new BSTNode<Integer>(getValue(random, SAMPLES));
        BSTNode<Integer> B = new BSTNode<Integer>(getValue(random, SAMPLES));

        Double[] times = getTimes(sizes, A, B);

        System.out.println("\nGraphing Isomorphic testing up to 1800 elements");
        plt = new Plotter("Isomorphic_Integer_1800.png", "Tree Size", "Time(micro sec)", Plotter.Type.LINEAR, "Time for Isomorphic Testing versus Tree Size");
        plt.plot(sizes, times);
        A = null;
        B =null;
        plt = null;
    }

    public static void main(String[] args) {
        TreeIsomorphism.testIsIsomorphic();
        testComplexity();
    }
}
