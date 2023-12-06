package src;

import java.util.Random;

public class TreeComp <T extends Comparable<T>>{

    private static int SAMPLES = 100;

    private static <T extends Comparable<T>> Timeit delTimerA() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    T value = (T) args[0];
                    AVLTree<T> a = (AVLTree<T>) args[1];
                    execDel(value, a);
        });

        return __;
    }

    private static <T extends Comparable<T>> Timeit delTimerB() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    T value = (T) args[0];
                    BSTTree<T> a = (BSTTree<T>) args[1];
                    execDel(value, a);
        });

        return __;
    }

    private static <T extends Comparable<T>> Timeit addTimerA() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    T value = (T) args[0];
                    AVLTree<T> a = (AVLTree<T>) args[1];
                    execAdd(value, a);
        });

        return __;
    }

    private static <T extends Comparable<T>> Timeit addTimerB() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    T value = (T) args[0];
                    BSTTree<T> b = (BSTTree<T>) args[1];
                    execAdd(value, b);
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
    private static <T extends Comparable<T>> void Randomize(AVLTree<T> avl, BSTTree<T> bst, int n) {
        int x = 0;

        Random rand = new Random();

        while (x != n) {
            T key = getValue(rand, n);
            if (rand.nextBoolean()) {
                bst.add(key);
                avl.add(key);
                x+=1;
            
            } else {
                bst.remove(key);
                avl.remove(key);
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
    private static <T extends Comparable<T>, U extends LRNode<T, U>> void execAdd(T val, BST<T, U> bst) {
        bst.add(val);
    }
    
    /**
     * Delete.
     * @param <T>
     * @param <U>
     * @param key
     * @param bst
     */
    private static <T extends Comparable<T>, U extends LRNode<T, U>> void execDel(T key, BST<T, U> bst) {
        bst.remove(key);
    }


    /**
     * 
     * @param <T>
     * @param specs [no. of added values, number of intervals]
     * @return
     */
    private static <T extends Comparable<T>> Double[][] runAdd(Integer amt) {

        double[][] runsA = new double[SAMPLES][amt];
        double[][] runsB = new double[SAMPLES][amt];
        double[][] heightsA = new double[SAMPLES][amt];
        double[][] heightsB = new double[SAMPLES][amt];
        T[] values;

        AVLTree<T> a = new AVLTree<>();
        BSTTree<T> b = new BSTTree<>();
        Randomize(a, b, amt);

        for (int i = 0; i < SAMPLES; i++) {
            values = getGenericArray(amt);


            for(int j = 0; j<amt; j++) {
                runsA[i][j] = addTimerA().measureMicros(values[j], a);
                runsB[i][j] = addTimerB().measureMicros(values[j], b);
                heightsA[i][j] = a.height();
                heightsB[i][j] = b.height();
            }
        }

        a = null;
        b = null;
        values = null;

        Double[] timesA = Util.sampleMean(runsA);
        runsA = null;
        Double[] timesB = Util.sampleMean(runsB);
        runsB = null;
        Double[] hA = Util.sampleMean(heightsA);
        heightsA = null;
        Double[] hB = Util.sampleMean(heightsB);
        heightsB = null;

        return new Double[][] {timesA, timesB, hA, hB};
    }

    /**
     * 
     * @param <T>
     * @param specs [no. of added values, number of intervals]
     * @return
     */
    private static <T extends Comparable<T>> Double[][] runDel(Integer amt) {

        double[][] runsA = new double[SAMPLES][amt];
        double[][] runsB = new double[SAMPLES][amt];
        T[] values;

        AVLTree<T> a = new AVLTree<>();
        BSTTree<T> b = new BSTTree<>();
        Randomize(a, b, 2*amt);

        for (int i = 0; i < SAMPLES; i++) {
            values = getGenericArray(amt);


            for(int j = 0; j<amt; j++) {
                runsA[i][j] = delTimerA().measureMicros(values[j], a);
                runsB[i][j] = delTimerB().measureMicros(values[j], b);
            }
        }

        a = null;
        b = null;
        values = null;

        Double[] timesA = Util.sampleMean(runsA);
        runsA = null;
        Double[] timesB = Util.sampleMean(runsB);
        runsB = null;

        return new Double[][] {timesA, timesB};
    }


    private static Integer[] xAxis(int SIZE) {
        Integer[] x = new Integer[SIZE];

        for(int i = 0; i<SIZE; i++) {
            x[i] = SIZE+i+1;
        }

        return x;
    }

        /**
     * Step 1: Randomize tree for N operations.
     * Step 2: Get the size and heights of the trees.
     * Step 3: Every x nodes that's added:
     *      Step 3.1: Get the average cost of adding an item.
     *      Step 3.2: Get the height of the tree.
     * Step 4: Every x nodes removed:
     *      Step 4.1: Get the average cost of deleting an item.
     */

     public static void main(String[] args) {
        int SIZE = 5_000;
        Plotter<Integer, Double> plt;
        Double[][] stats;

        Integer[] x = xAxis(SIZE);

        /**
         * {timesA, timesB, heightsA, heightsB}
         * 
         *  stats = runAdd(SIZE);

        plt = new Plotter<>("bst/AVL_ADD_" + SIZE*2 + ".png", "Tree Size",  "Time (ms)", Plotter.Type.LINE, "AVL Tree add operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/BST_ADD_" + SIZE*2 + ".png", "Tree Size",  "Time (ms)", Plotter.Type.LINE, "BST Tree add operation time");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();
        
        plt = new Plotter<>("bst/AVLvsBST_ADD_" + SIZE*2 + ".png", "Tree Size",  "Time (ms)", Plotter.Type.LINE, "AVL Tree vs BST add operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/AVL_HEIGHT_" + SIZE*2 + "_Inc.png", "Tree Size",  "Tree Height", Plotter.Type.LINE, "AVL Tree Height");
        plt.add(x, stats[2], "AVL Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/BST_HEIGHT_" + SIZE*2 + "_Inc.png", "Tree Size",  "Tree Height", Plotter.Type.LINE, "BST Tree Height");
        plt.add(x, stats[3], "BST Tree");
        plt.save();
        plt.plot();
        

        plt = new Plotter<>("bst/AVLvsBST_HEIGHT_" + SIZE*2 + "_Inc.png", "Tree Size",  "Tree Height", Plotter.Type.LINE, "AVL Tree vs BST Height");
        plt.add(x, stats[2], "AVL Tree");
        plt.add(x, stats[3], "BST Tree");
        plt.save();
        plt.plot();

        stats = runDel(SIZE);

        plt = new Plotter<>("bst/BST_DEL_" + SIZE*2 + ".png", "Tree Size",  "Time (ms)", Plotter.Type.LINE, "BST Tree delete operation time");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/AVL_DEL_" + SIZE*2 + ".png", "Tree Size",  "Time (ms)", Plotter.Type.LINE, "AVL Tree delete operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.save();
        plt.plot();
        */

        plt = Plotter.LoadPlotter("src/graphs/bst/BST_DEL_10000_plotter.ser");
        plt.plot();

        plt = Plotter.LoadPlotter("src/graphs/bst/AVL_DEL_10000_plotter.ser");
        plt.plot();



        System.out.println("Done!");
    }    
}
