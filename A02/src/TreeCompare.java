package src;

import java.io.Serial;
import java.util.Random;

public class TreeCompare<T extends Comparable<T>>{

    private static int SAMPLES = 100;

    /**
     * Copy a generic array.
     * @param <T>
     * @param source
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] copyArray(T[] source) {
        if (source == null) return null;

        int length = source.length;
        T[] destination = (T[]) new Comparable[length];

        for (int i = 0; i < length; i++) {
            destination[i] = source[i];
        }

        return destination;
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

        Random rand = new Random(42);

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
        T[] keys;
        int x = 0;

        Random rand = new Random(42);

        try {
            keys = getGenericArray(rand, 3*n);
        } catch (IllegalArgumentException exc) {
            System.out.println(exc);
            return;
        }

        for(int i=0; i < keys.length; i++) {
            if (rand.nextBoolean()) {
                bst.add(keys[i]);
                avl.add(keys[i]);
                x+=1;
                if(x == n) return;
            
            } else {
                bst.remove(keys[i]);
                avl.remove(keys[i]);

            }
        }

        System.out.println("Couldn't randomize fully!");
    }

    private static <T extends Comparable<T>, U extends LRNode<T, U>> void execAdd(T[] values, BST<T, U> bst) {
        for(T val : values) bst.add(val);
    }

    /**
     * 
     * @param <T>
     * @param timerA Timer for AVLTree
     * @param timerB Timer for BSTTree
     * @param specs [no. of added values, number of intervals]
     * @return
     */
    private static <T extends Comparable<T>> double[][] run(Timeit timerA, Timeit timerB, Integer[] specs) {
        int length = specs[1];
        double[] timesA = new double[length];
        double[] timesB = new double[length];
        double[] heightsA = new double[length];
        double[] heightsB = new double[length];

        AVLTree<T> a = new AVLTree<>();
        BSTTree<T> b = new BSTTree<>();
        Randomize(a, b, specs[0]);

        for (int i = 0; i < specs[1]; i++) {
            T[] values = getGenericArray(specs[0]);
            //System.out.println(values[0]);
            timesA[i] = timerA.measureMicros(values, a);
            heightsA[i] = a.height(); 
            //System.out.println(heightsA[i]);
            timesB[i] = timerB.measureMicros(values, b);
            heightsB[i] = b.height();
        }

        //for(double x:heightsA) System.out.print(x + ", ");


        return new double[][]{timesA, timesB, heightsA, heightsB};
    }



    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> Double[][] treeStats(int size, int intervals) {
        double[][] runsA = new double[SAMPLES][intervals];
        double[][] runsB = new double[SAMPLES][intervals];
        double[][] heightsA = new double[SAMPLES][intervals];
        double[][] heightsB = new double[SAMPLES][intervals];
        double[][] stats = new double[4][intervals]; 

        Timeit timerA = new Timeit((args) -> {
                T[] values = (T[]) args[0];
                AVLTree<T> a = (AVLTree<T>) args[1];
                execAdd(values, a);
            });

        Timeit timerB = new Timeit((args) -> {
                T[] values = (T[]) args[0];
                BSTTree<T> b = (BSTTree<T>) args[1];
                execAdd(values, b);
            });
        
        for (int i = 0; i < SAMPLES; i++) {
            stats = run(timerA, timerB, new Integer[]{size, intervals});

            runsA[i] = stats[0];
            runsB[i] = stats[1];
            heightsA[i] = stats[2];
            heightsB[i] = stats[3];
        }

        //DDout(heightsA);

        Double[] timesA = Util.mean(runsA);
        Double[] timesB = Util.mean(runsB);
        Double[] hA = Util.mean(heightsA);
        Double[] hB = Util.mean(heightsB);


        return new Double[][] {timesA, timesB, hA, hB};
     }

    private static <X extends Object> void DDout(X[][] x) {
        for(X[] y : x) {
            for(X z : y) {
                System.out.print(z + " ");
            }
            System.out.println();
        }
    } 

    private static void DDout(double[][] x) {
        for(double[] y : x) {
            for(double z : y) {
                System.out.print(z + " ");
            }
            System.out.println();
        }
    } 

    private static void dout(double[] x){
        for(double y: x) System.out.print(y + ", ");
        System.out.println();
    }

    private static Integer[] xAxis(int SIZE, int START, int STEPS) {
        Integer[] x = new Integer[STEPS];

        for (int i = 0; i < STEPS; i++) {
            x[i] = START + (START * i);
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
        int SIZE = 1000;
        int INTERVAL = 50;
        Plotter<Integer, Double> plt;

        Integer[] x = xAxis(SIZE*INTERVAL+INTERVAL, SIZE, INTERVAL);

        /**
         * {timesA, timesB, heightsA, heightsB}
         */
        Double[][] stats = treeStats(SIZE, INTERVAL);
        
        plt = new Plotter<>("bst/AVLvsBST_ADD_" + SIZE*INTERVAL+INTERVAL + ".png", "Tree Size",  "Time (microsec)", Plotter.Type.SCATTER, "AVL Tree vs BST add operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.add(x, stats[1], "BST Tree");
        plt.plot();

        plt = new Plotter<>("bst/AVLvsBST_HEIGHT_" + SIZE*INTERVAL+INTERVAL + ".png", "Tree Size",  "Tree Height", Plotter.Type.SCATTER, "AVL Tree vs BST Height");
        plt.add(x, stats[2], "AVL Tree");
        plt.add(x, stats[3], "BST Tree");
        plt.plot();

        System.out.println("Done!");
    }    
}
