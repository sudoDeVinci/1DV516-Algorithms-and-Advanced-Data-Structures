package src;

import java.util.Random;

import src.Task4.BSTTree;

public class TreeComp <T extends Comparable<T>>{

    private static int SAMPLES = 200;

    private static <T extends Comparable<T>> Timeit delTimerA() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    AVLTree<T> a = (AVLTree<T>) args[1];
                    a.remove((T) args[0]);
        });

        return __;
    }

    private static <T extends Comparable<T>> Timeit delTimerB() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    BSTTree<T> b = (BSTTree<T>) args[1];
                    b.remove((T) args[0]);
        });

        return __;
    }

    private static <T extends Comparable<T>> Timeit addTimerA() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    AVLTree<T> a = (AVLTree<T>) args[1];
                    a.add((T) args[0]);
        });

        return __;
    }

    private static <T extends Comparable<T>> Timeit addTimerB() {
        @SuppressWarnings("unchecked")
        Timeit __ =  new Timeit((args) -> {
                    BSTTree<T> b = (BSTTree<T>) args[1];
                    b.add((T) args[0]);
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
            if ((rand.nextInt(10) + 1) > 0.42) {
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
    private static <T extends Comparable<T>, U extends LRNode<T, U>> void execAdd(T[] vals, AVLTree<T> a, BSTTree<T> b) {
        for(T val: vals) {
            a.add(val);
            b.add(val);
        }
    }

    /**
     * Add.
     * @param <T>
     * @param <U>
     * @param val
     * @param bst
     */
    private static <T extends Comparable<T>, U extends LRNode<T, U>> void execDel(T[] vals, AVLTree<T> a, BSTTree<T> b) {
        for(T val: vals) {
            a.remove(val);
            b.remove(val);
        }
    }
    

    /**
     * 
     * @param <T>
     * @param amt [no. of added values]
     * @return
     */
    private static <T extends Comparable<T>> Double[][] runAdd(Integer amt, int spacing) {

        int amount = (int)(amt/spacing);
        double[][] runsA = new double[SAMPLES][amount];
        double[][] runsB = new double[SAMPLES][amount];
        double[][] heightsA = new double[SAMPLES][amount];
        double[][] heightsB = new double[SAMPLES][amount];
        T[] values;
        T[] padders;
        T[] inits; 
        double __;

        AVLTree<T> a = new AVLTree<>();
        BSTTree<T> b = new BSTTree<>();
        

        for (int i = 0; i < SAMPLES; i++) {
            a = new AVLTree<>();
            b = new BSTTree<>();
            Randomize(a, b, (int) (amt/10)-1);
            inits = getGenericArray(1); 
            __ = addTimerA().measureMicros(inits[0], a);
            __ = addTimerB().measureMicros(inits[0], b);

            values = getGenericArray(amount);

            for(int j = 0; j<amount; j++) {
                System.out.println("SAMPLE: "+  i + "\t|\t" + "Point: " + j);
                padders = getGenericArray(spacing-1);
                execAdd(padders, a, b);

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
    private static <T extends Comparable<T>> Double[][] runDel(Integer amt, int spacing) {

        int amount = (int)(amt/spacing);
        double[][] runsA = new double[SAMPLES][amount];
        double[][] runsB = new double[SAMPLES][amount];
        T[] values = getGenericArray(amount);
        T[] padders = getGenericArray(spacing-1);
        T init; 
        double __;

        AVLTree<T> a = new AVLTree<>();
        BSTTree<T> b = new BSTTree<>();
        for (int i = 0; i < SAMPLES; i++) {

            a = new AVLTree<>();
            b = new BSTTree<>();
            Randomize(a, b, amt*2+1);
            /**
             * Enqueue items we know for certain are in the tree.
             */
            DequeRand<T> dqrand = new DequeRand<>();
            for(T item : b) dqrand.enqueue(item);
            init = dqrand.dequeue();
            __ = delTimerA().measureMicros(init, a);
            __ = delTimerB().measureMicros(init, b);


            

            for(int xxx = 0; xxx < amount; xxx++) values[xxx] = dqrand.dequeue();

            for(int j = 0; j<amount; j++) {

                for(int xx = 0; xx < spacing-1; xx++) padders[xx] = dqrand.dequeue();
                execDel(padders, a, b);
                runsA[i][j] = delTimerA().measureMicros(values[j], a);
                runsB[i][j] = delTimerB().measureMicros(values[j], b);
            }
        }

        a = null;
        b = null;
        values = null;

        Double[] timesA = Util.sampleMean(runsA);
        Double[] rev_A = Util.reverse(timesA, timesA.length);
        Double[] A = Util.copy(rev_A, 1);
        runsA = null;
        timesA = null;
        Double[] timesB = Util.sampleMean(runsB);
        Double[] rev_B = Util.reverse(timesB, timesB.length);
        Double[] B = Util.copy(rev_B, 1);
        runsB = null;
        timesB = null;

        return new Double[][] {A, B};
    }


    private static Integer[] xAxisInc(int SIZE, int spacing) {
        int amount = (int)(SIZE/spacing);
        Integer[] x = new Integer[amount];
        

        for(int i = 0; i<amount; i++) {
            x[i] = ((int)SIZE/10)-1+(i*spacing);
        }

        return x;
    }

    private static Integer[] xAxisDec(int SIZE, int spacing) {
        int amount = (int)(SIZE/spacing);
        Integer[] x = new Integer[amount];
        

        for(int i = 0 ; i<x.length; i++) {
            x[i] = (SIZE*2)+1 - (i*spacing);
        }

        x = Util.reverse(x, x.length);
        Integer[] out = Util.copy(x, 1);
        return out;
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
        int SIZE = 100_000;
        int SPACING = 500;
        Plotter<Integer, Double> plt;
        Double[][] stats;

        Integer[] x = xAxisInc(SIZE, SPACING);
        stats = runAdd(SIZE, SPACING);

        plt = new Plotter<>("bst/AVL_ADD_" + SIZE + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LOGARITHMIC, "AVL Tree add operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/BST_ADD_" + SIZE + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LOGARITHMIC, "BST Tree add operation time");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();
        
        plt = new Plotter<>("bst/AVLvsBST_ADD_" + SIZE + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LOGARITHMIC, "AVL Tree vs BST add operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/AVL_HEIGHT_" + SIZE + "_Inc.png", "Tree Size",  "Tree Height", Plotter.Type.LINE, "AVL Tree Height");
        plt.add(x, stats[2], "AVL Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/BST_HEIGHT_" + SIZE + "_Inc.png", "Tree Size",  "Tree Height", Plotter.Type.LINE, "BST Tree Height");
        plt.add(x, stats[3], "BST Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/AVLvsBST_HEIGHT_" + SIZE + "_Inc.png", "Tree Size",  "Tree Height", Plotter.Type.LINE, "AVL Tree vs BST Height");
        plt.add(x, stats[2], "AVL Tree");
        plt.add(x, stats[3], "BST Tree");
        plt.save();
        plt.plot();

        Integer[] xx = xAxisDec(SIZE, SPACING);
        stats = runDel(SIZE, SPACING);
        plt = new Plotter<>("bst/AVL_DEL_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "AVL Tree remove operation time");
        plt.add(xx, stats[0], "AVL Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/BST_DEL_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "BST Tree remove operation time");
        plt.add(xx, stats[1], "BST Tree");
        plt.save();
        plt.plot();



        System.out.println("Done!");

        /**
         * {timesA, timesB, heightsA, heightsB}
         * 
         *  stats = runAdd(SIZE);

        plt = new Plotter<>("bst/AVL_ADD_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "AVL Tree add operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/BST_ADD_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "BST Tree add operation time");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();
        
        plt = new Plotter<>("bst/AVLvsBST_ADD_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "AVL Tree vs BST add operation time");
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

        plt = new Plotter<>("bst/BST_DEL_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "BST Tree delete operation time");
        plt.add(x, stats[1], "BST Tree");
        plt.save();
        plt.plot();

        plt = new Plotter<>("bst/AVL_DEL_" + SIZE*2 + ".png", "Tree Size",  "Time (micro s)", Plotter.Type.LINE, "AVL Tree delete operation time");
        plt.add(x, stats[0], "AVL Tree");
        plt.save();
        plt.plot();

        plt = Plotter.LoadPlotter("src/graphs/bst/BST_DEL_10000_plotter.ser");
        plt.plot();

        plt = Plotter.LoadPlotter("src/graphs/bst/AVL_DEL_10000_plotter.ser");
        plt.plot();
        */
    }    
}
