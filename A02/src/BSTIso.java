package src;

import java.util.Random;

public class BSTIso {

    private static int SAMPLES = 200;

    private static <T extends Comparable<T>> Timeit Timer() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
            isIsomorphic((BSTNode<T>) args[0], (BSTNode<T>) args[1]);
        });
        return __;
    }

    /**
     * Get a random bounded integer.
     * @param rand
     * @param size
     * @return
     */

     @SuppressWarnings("unchecked")
     private static <T extends Comparable<T>> T getValue(Random rand, int N) {
         return (T) Integer.valueOf(rand.nextInt(N)+1);
    }

    /**
     * Get an array of generic elements. 
     * @param <T>
     * @param N
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] getGenericArray(int N) throws IllegalArgumentException{
        if (N < 0)  throw new IllegalArgumentException("N and bound must be non-negative");

        Random rand = new Random();

        T[] arr = (T[]) new Comparable[N];

        for(int i = 0 ; i<N; i++) arr[i] = getValue(rand, N);

        return arr;

    }

    /**
     * Get an array of generic elements. 
     * @param <T>
     * @param N
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] getGenericArray(Random rand, int N) throws IllegalArgumentException{
        if (N < 0)  throw new IllegalArgumentException("N and bound must be non-negative");

        T[] arr = (T[]) new Comparable[N];

        for(int i = 0 ; i<N; i++) arr[i] = getValue(rand, N);

        return arr;

    }
    
    /**
     * Randomize Trees.
     * Return an array representing the order of operations done on the trees.
     * @param <T>
     * @param <U>
     * @param avl
     * @param bst
     * @param n
     */
    private static <T extends Comparable<T>> void Randomize(BSTTree<T> a, int n) {
        int x = 0;

        Random rand = new Random();

        while (x != n) {
            T key = getValue(rand, n);
            if ((rand.nextInt(10) + 1) > 0.42) {
                a.add(key);
                x+=1;
            
            } else {
                a.remove(key);
                x-=1;
            }
        }

        if(x != n) System.out.println("Couldn't randomize fully!");
    }

    private static <T extends Comparable<T>> void Randomize(BSTTree<T> a, BSTTree<T> b, int n) {
        int x = 0;

        Random rand = new Random();

        while (x != n) {
            T key = getValue(rand, n);
            if ((rand.nextInt(10) + 1) > 0.42) {
                a.add(key);
                b.add(key);
                x+=1;
            
            } else {
                a.remove(key);
                b.remove(key);
                x-=1;
            }
        }

        if(x != n) System.out.println("Couldn't randomize fully!");
    }

   /**
     * Add.
     * @param <T>
     * @param <U>
     * @param val
     * @param bst
     */
    private static <T extends Comparable<T>, U extends LRNode<T, U>> void execAdd(T[] vals, BST<T, U> a, BST<T, U> b) {
        for(T val: vals) {
            a.add(val);
            b.add(val);
        }
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
        if (!root1.value.equals(root2.value)) {
            return false;
        }

        /**
         * Check all combinations of left and right nodes for the wo input nodes.
         * We don't need to explicitly return True because that will happen when a leaf is reached.
         * Either the nodes are exactly the same, or they are swapped.
         */
        return (isIsomorphic(root1.left, root2.left) && isIsomorphic(root1.right, root2.right) ||
                isIsomorphic(root1.left, root2.right) && isIsomorphic(root1.right, root2.left));
    }


    /**
     * 
     * @param <T>
     * @param specs [no. of added values, number of intervals]
     * @return
     */
    private static <T extends Comparable<T>> Double[][] runSame(Integer amt, int spacing) {
        int amount = (int)(amt/spacing);
        double[][] runs = new double[SAMPLES][amount];
        double[][] heights = new double[SAMPLES][amount];
        T[] padders;
        double __;


        BSTTree<T> a = new BSTTree<>();
        BSTTree<T> b = new BSTTree<>();

        for (int i = 0; i < SAMPLES; i++) {
            a = new BSTTree<>();
            b = new BSTTree<>();
            BSTNode<T> aN = a.getRoot();
            BSTNode<T> bN = b.getRoot();
            Randomize(a, b, amt);
            __ = Timer().measureMicros(aN, bN);

            for(int j = 0; j<amount; j++) {
                padders = getGenericArray(spacing);
                execAdd(padders, a, b);

                runs[i][j] = Timer().measureMicros(aN, bN);
                heights[i][j] = a.height();
                System.out.println("SAMPLE: "+  i + "\t|\t" + "Point: " + j + "\t|\t" + "Heights:" + heights[i][j]);
            }
        }

        Double[] times = Util.sampleMean(runs);
        runs = null;
        Double[] h = Util.sampleMean(heights);
        heights = null;

        return new Double[][] {times, h};
    }

    private static <T extends Comparable<T>> Double[][] runDiff(Integer amt, int spacing) {
        int amount = (int)(amt/spacing);
        double[][] runs = new double[SAMPLES][amount];
        double[][] heightsA = new double[SAMPLES][amount];
        double[][] heightsB = new double[SAMPLES][amount];
        T[] padders;
        double __;


        BSTTree<T> a = new BSTTree<>();
        BSTTree<T> b = new BSTTree<>();

        for (int i = 0; i < SAMPLES; i++) {
            a = new BSTTree<>();
            b = new BSTTree<>();
            BSTNode<T> aN = a.getRoot();
            BSTNode<T> bN = b.getRoot();
            Randomize(a, amt);
            Randomize(b, amt);
            __ = Timer().measureMicros(aN, bN);

            for(int j = 0; j<amount; j++) {
                padders = getGenericArray(spacing);
                execAdd(padders, a, b);

                runs[i][j] = Timer().measureMicros(aN, bN);
                heightsA[i][j] = a.height();
                heightsB[i][j] = b.height();
                System.out.println("SAMPLE: "+  i + "\t|\t" + "Point: " + j + "\t|\t" + "Heights:" + heightsA[i][j] + " & " + heightsB[i][j]);
            }
        }

        Double[] times = Util.sampleMean(runs);
        runs = null;
        Double[] hA = Util.sampleMean(heightsA);
        heightsA = null;
        Double[] hB = Util.sampleMean(heightsB);
        heightsB = null;

        return new Double[][] {times, hA, hB};
    }

    private static Integer[] xAxis(int SIZE, int spacing) {
        int amount = (int)(SIZE/spacing);
        Integer[] x = new Integer[amount];
        

        for(int i = 0; i<amount; i++) {
            x[i] = ((int)SIZE/10)+(i*spacing);
        }

        return x;
    }

    public static void main(String[] args) {
        int SIZE = 50_000;
        int SPACING = 1000;
        Double[][] stats;
        
        /**
         * The following shows results of testing two identical trees for isomorphism as tree size increases.
         */
        Integer[] x = xAxis(SIZE, SPACING);
        stats = runSame(SIZE, SPACING);

        Plotter<Integer, Double> plt = new Plotter<>("iso/ISOMORPH_SAME_" + SIZE + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LOGARITHMIC, "Tree Isomorphism Check Time");
        plt.add(x, stats[0], "BST Tree");
        plt.save();
        plt.plot();

        Plotter<Double, Double> pltH = new Plotter<>("iso/ISOMORPH_SAME_HEIGHT_" + SIZE + ".png", "Tree Height",  "Time (micro s)", Plotter.Type.LOGARITHMIC, "Tree Isomorphism Check Time");
        pltH.add(stats[1], stats[0], "BST Tree");
        pltH.save();
        pltH.plot();

        Plotter<Integer, Double> pltHH = new Plotter<>("iso/HEIGHT_SAME_" + SIZE + ".png", "Tree Size",  "Time Height", Plotter.Type.LINEAR, "Tree Heights");
        pltHH.add(x, stats[1], "BST Tree Height");
        pltHH.save();
        pltHH.plot();
        


        /**
         * The following shows results of testing two non-identical trees for isomorphism as tree size increases.
         */
        stats = runDiff(SIZE, SPACING);
        plt = new Plotter<>("iso/ISOMORPH_DIFF_" + SIZE + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINEAR, "Tree Isomorphism Check Time");
        plt.add(x, stats[0], "BST Tree");
        plt.save();
        plt.plot();

        pltH = new Plotter<>("iso/ISOMORPH_DIFF_HEIGHT_" + SIZE + ".png", "Tree Height",  "Time (micro s)", Plotter.Type.LINEAR, "Tree Isomorphism Check Time");
        pltH.add(stats[1], stats[0], "BST Tree");
        pltH.save();
        pltH.plot();

        pltHH = new Plotter<>("iso/HEIGHT_DIFF_" + SIZE + ".png", "Tree Size",  "Time Height", Plotter.Type.LINE, "Tree Heights");
        pltHH.add(x, stats[1], "BST A Tree Height");
        pltHH.add(x, stats[2], "BST B Tree Height");
        pltHH.save();
        pltHH.plot();


        /**
         * The following shows an existing graph being loaded and an upper and lower bound being drawn on it.
         */
        try {
            x = xAxis(SIZE, SPACING);
            Double[] ub = Util.line(0.20,0.000003, x);
            Double[] lb = Util.line(0.10, 0.000003, x);
            plt = Plotter.LoadPlotter("src/graphs/iso/ISOMORPH_DIFF_50000_plotter.ser");
            plt.setGraphPath("/graphs/iso/ISOMORPH_DIFF_UB_50000.png");
            plt.add(x, ub, Plotter.Type.LINEAR, "upper bound");
            plt.add(x, lb, Plotter.Type.LINEAR, "lower bound");
            //plt.save();
            plt.plot();
        } catch(Exception e) {
            System.out.println("Could not load specified plotter data.");
        }

        System.out.println("Done!");
        
    }
}
