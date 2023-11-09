package src;

import java.util.Random;

/**
 * Class deedicated to checking tree isomorphism
 */
public class TreeIsomorphism <T extends Comparable<T>> {

    private static int SAMPLES = 500;

    private Timeit timer = new Timeit((args) -> {
                BSTNode<T> a = (BSTNode<T>) args[0];
                BSTNode<T> b = (BSTNode<T>) args[1];
                TreeIsomorphism.isIsomorphic(a,b);
            });

    /**
     * Get a random bounded integer.
     * @param rand
     * @param size
     * @return
     */

    @SuppressWarnings("unchecked")
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
    public static <T extends Comparable<T>> BSTNode<T> constructRandomBST(int size) {
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
        BSTNode<Integer> tree1 = constructRandomBST(50);
        BSTNode<Integer> tree2 = constructRandomBST(50);

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

    
    private static <T extends Comparable<T>> double execute(int size, Timeit timer) {
        double[] samples = new double[SAMPLES];

        BSTNode<T> A = new BSTNode<T>(null);
        BSTNode<T> B  =new BSTNode<T>(null);
        

        for (int x = 0; x < SAMPLES; x++) {
            
            A = constructRandomBST(size);
            B = constructRandomBST(size);
            samples[x] = timer.measureNanos(A, B);
        }

        return Util.sampleMean(samples);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> Double[] getTimes(Integer[] sizes) {
        int length = sizes.length;
        Double[] times = new Double[length];

        Timeit timer = new Timeit((args) -> {
                BSTNode<T> a = (BSTNode<T>) args[0];
                BSTNode<T> b = (BSTNode<T>) args[1];
                TreeIsomorphism.isIsomorphic(a,b);
            });

        for(int i = 0; i < length; i++) {
            times[i] = execute(sizes[i], timer);
        }

        return times;
    }

    public static void testComplexity() {
        Plotter<Integer, Double> plt;
        int SIZE = 10_000;
        int STEPS = 5_00;
        int START = 5_00;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] sizes = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            sizes[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Isomorphic testing up to "+ sizes[sizes.length-1] +" elements");

        Double[] times = getTimes(sizes);
        plt = new Plotter<>("Isomorphic_Integer_"+sizes[sizes.length-1]+".png", "Tree Size", "Time(ns)", Plotter.Type.LINE, "Time for Isomorphic Testing versus Tree Size");
        plt.add(sizes, times, "ISO");
        plt.plot();
        plt = null;
    }

    public static void main(String[] args) {
        TreeIsomorphism.testIsIsomorphic();
        TreeIsomorphism.testComplexity();
    }
}
