package src.Task6;

import java.util.HashSet;
import java.util.Random;
import src.Timeit;
import src.Util;
import src.plot.Plot;
import src.plot.Plotter;

public class TreeComp {

    private static int SAMPLES = 60;
    private static final Random rand = new Random();

    private static Timeit delTimerA() {
        return new Timeit((args) -> {
            AVLTree a = (AVLTree) args[1];
            a.remove((Integer) args[0]);
        });
    }

    private static Timeit delTimerB() {
        return new Timeit((args) -> {
            BST b = (BST) args[1];
            b.remove((Integer) args[0]);
        });
    }

    private static Timeit addTimerA() {
        return new Timeit((args) -> {
            AVLTree a = (AVLTree) args[1];
            a.add((Integer) args[0]);
        });
    }

    private static Timeit addTimerB() {
        return new Timeit((args) -> {
            BST b = (BST) args[1];
            b.add((Integer) args[0]);
        });
    }

    private static int getValue(Random rand, int N) {
        return rand.nextInt(N*2 + 1);
    }

    private static int[] getGenericArray(int N) throws IllegalArgumentException {
        if (N < 0) throw new IllegalArgumentException("N and bound must be non-negative");

        HashSet<Integer> uniqueNumbers = new HashSet<>((int) (N * 2));
        while (uniqueNumbers.size() < N) {
            int number = rand.nextInt(N*2 + 1);
            uniqueNumbers.add(number);
        }
        return uniqueNumbers.stream().mapToInt(Integer::intValue).toArray();
    }



    private static void execAdd(int[] vals, AVLTree a, BST b) {
        for (int val : vals) {
            a.add(val);
            b.add(val);
        }
    }

    private static void execDel(int[] vals, AVLTree a, BST b) {
        for (int val : vals) {
            a.remove(val);
            b.remove(val);
        }
    }

    private static Double[][] runAdd(Integer amt, int spacing) {
        int amount = amt / spacing;
        double[][] runsA = new double[SAMPLES][amount];
        double[][] runsB = new double[SAMPLES][amount];
        double[][] heightsA = new double[SAMPLES][amount];
        double[][] heightsB = new double[SAMPLES][amount];

        int[] values;
        int[] padders;
        int[] inits;
        double __;

        AVLTree a;
        BST b;

        for (int i = 0; i < TreeComp.SAMPLES; i++) {
            a = new AVLTree();
            b = new BST();
            a.randomizeTree(amt/2);
            b.randomizeTree(amt/2);
            inits = getGenericArray(1);
            __ = addTimerA().measureMicros(inits[0], a);
            __ = addTimerB().measureMicros(inits[0], b);

            values = getGenericArray(amount);

            for (int j = 0; j < amount; j++) {
                System.out.println("SAMPLE: " + i + "\t|\t" + "Point: " + j);
                padders = getGenericArray(spacing - 1);
                execAdd(padders, a, b);

                runsA[i][j] = addTimerA().measureMicros(values[j], a);
                runsB[i][j] = addTimerB().measureMicros(values[j], b);

                heightsA[i][j] = a.height();
                heightsB[i][j] = b.height();
            }
        }

        Double[] timesA = Util.sampleMean(runsA);
        Double[] timesB = Util.sampleMean(runsB);

        Double[] hA = Util.sampleMean(heightsA);
        Double[] hB = Util.sampleMean(heightsB);

        return new Double[][]{timesA, timesB, hA, hB};
    }

    private static Double[][] runDel(Integer amt, int spacing) {
        int amount = amt / spacing;
        double[][] runsA = new double[SAMPLES][amount];
        double[][] runsB = new double[SAMPLES][amount];

        int[] values = getGenericArray(amount);
        int[] padders = getGenericArray(spacing - 1);
        int init;
        double __;

        AVLTree a;
        BST b;
        for (int i = 0; i < SAMPLES; i++) {
            a = new AVLTree();
            b = new BST();
            a.randomizeTree(amt*2);
            b.randomizeTree(amt*2);

            DequeRand<Integer> dqrand = new DequeRand<>(b.size());
            for (int item : b) dqrand.enqueue(item);
            init = dqrand.dequeue();
            __ = delTimerA().measureMicros(init, a);
            __ = delTimerB().measureMicros(init, b);

            for (int xxx = 0; xxx < amount - 1; xxx++) values[xxx] = dqrand.dequeue();

            for (int j = 0; j < amount; j++) {
                for (int xx = 0; xx < spacing - 1; xx++) padders[xx] = dqrand.dequeue();
                execDel(padders, a, b);
                runsA[i][j] = delTimerA().measureMicros(values[j], a);
                runsB[i][j] = delTimerB().measureMicros(values[j], b);
            }
        }

        Double[] timesA = Util.sampleMean(runsA);
        Double[] rev_A = Util.reverse(timesA, timesA.length);
        Double[] A = Util.copy(rev_A, 1);
        Double[] timesB = Util.sampleMean(runsB);
        Double[] rev_B = Util.reverse(timesB, timesB.length);
        Double[] B = Util.copy(rev_B, 1);

        return new Double[][]{A, B};
    }

    private static Integer[] xAxisInc(int SIZE, int spacing) {
        int amount = SIZE / spacing;
        Integer[] x = new Integer[amount];

        for (int i = 0; i < amount; i++) {
            x[i] = SIZE / 10 - 1 + i * spacing;
        }

        return x;
    }

    private static Integer[] xAxisDec(int SIZE, int spacing) {
        int amount = SIZE / spacing;
        Integer[] x = new Integer[amount];

        for (int i = 0; i < x.length; i++) {
            x[i] = SIZE * 2 + 1 - i * spacing;
        }

        x = Util.reverse(x, x.length);
        return Util.copy(x, 1);
    }

    public static void main(String[] args) {
        int SIZE = 25_000;
        int SPACING = 250;
        Double[][] stats;

        Integer[] x = xAxisInc(SIZE, SPACING);
        stats = runAdd(SIZE, SPACING);

        Plotter<Integer, Double> graph_add = new Plotter<>("Tree_add_comp");
        graph_add.setTitle("Comparison of Tree Addition Operation Times");
        graph_add.setXLabel("Tree size");
        graph_add.setYLabel("Time (micro seconds)");
        graph_add.setFontSize(20);

        Plot<Integer, Double> p1 = new Plot<>("AVL Tree", Plot.Type.EXPONENTIAL, x, stats[0]);
        p1.setSize(40);

        Plot<Integer, Double> p2 = new Plot<>("BST Tree", Plot.Type.EXPONENTIAL, x, stats[1]);
        p2.setSize(40);

        graph_add.add(p1);
        graph_add.add(p2);
        graph_add.plot();


        Plotter<Integer, Double> graph_height = new Plotter<>("Tree_height_insertion_comp");
        graph_height.setTitle("Comparison of Tree heights after Consecutive Inserts");
        graph_height.setXLabel("Tree size");
        graph_height.setYLabel("Height");
        graph_height.setFontSize(20);

        p1 = new Plot<>("AVL Tree", Plot.Type.LOGARITHMIC, x, stats[2]); 
        p1.setSize(40);

        p2 = new Plot<>("BST Tree", Plot.Type.LOGARITHMIC, x, stats[3]);
        p2.setSize(40);

        graph_height.add(p1);
        graph_height.add(p2);
        graph_height.plot();



        Integer[] x_dec = xAxisDec(SIZE, SPACING);
        stats = runDel(SIZE, SPACING);

        Plotter<Integer, Double> graph_del = new Plotter<>("Tree_del_comp");
        graph_del.setTitle("Comparison of Tree Deletion Operation Times");
        graph_del.setXLabel("Tree size");
        graph_del.setYLabel("Time (micro seconds)");
        graph_del.setFontSize(20);

        p1 = new Plot<>("AVL Tree", Plot.Type.EXPONENTIAL, x_dec, stats[0]);
        p1.setSize(40);

        p2 = new Plot<>("BST Tree", Plot.Type.EXPONENTIAL, x_dec, stats[1]);
        p2.setSize(40);

        graph_del.add(p1);
        graph_del.add(p2);
        graph_del.plot();

        System.out.println("Done!");
    }
}