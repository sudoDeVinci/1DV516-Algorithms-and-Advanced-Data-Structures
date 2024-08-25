package src.Task6;

import java.util.HashSet;
import java.util.Random;
import src.Timeit;
import src.Util;

public class TreeComp {

    private static final int SAMPLES = 100;
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

    static int[] getGenericArray(int N) throws IllegalArgumentException {
        if (N < 0) throw new IllegalArgumentException("N and bound must be non-negative");

        HashSet<Integer> uniqueNumbers = new HashSet<>(N);
        while (uniqueNumbers.size() < N) {
            int number = rand.nextInt((int)(N*1.5) + 1);
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

    static Double[][] runAdd(Integer amount) {
        double[][] runsA = new double[SAMPLES][amount];
        double[][] runsB = new double[SAMPLES][amount];
        double[][] heightsA = new double[SAMPLES][amount];
        double[][] heightsB = new double[SAMPLES][amount];

        int[] values;

        AVLTree a = new AVLTree();
        BST b = new BST();

        for (int i = 0; i < TreeComp.SAMPLES; i++) {
            
            a.randomizeTree(amount);
            b.randomizeTree(amount);

            values = getGenericArray(amount);

            for (int j = 0; j < amount; j++) {
                runsA[i][j] = addTimerA().measureMicros(values[j], a);
                runsB[i][j] = addTimerB().measureMicros(values[j], b);

                heightsA[i][j] = a.height();
                heightsB[i][j] = b.height();
            }

            System.out.println("SAMPLE: " + i);
        }

        Double[] timesA = Util.sampleMean(runsA);
        Double[] timesB = Util.sampleMean(runsB);

        Double[] hA = Util.sampleMean(heightsA);
        Double[] hB = Util.sampleMean(heightsB);

        return new Double[][]{timesA, timesB, hA, hB};
    }

    static Double[][] runDel(Integer amount) {
        double[][] runsA = new double[SAMPLES][amount];
        double[][] runsB = new double[SAMPLES][amount];

        AVLTree a = new AVLTree();
        BST b = new BST();

        for (int i = 0; i < TreeComp.SAMPLES; i++) {
            a.randomizeTree(amount*2);
            b.randomizeTree(amount*2);

            DequeRand<Integer> dqrandA = new DequeRand<>(amount*2);
            DequeRand<Integer> dqrandB = new DequeRand<>(amount*2);

            for (int itemb : b) dqrandB.enqueue(itemb);
            for (int itema : a) dqrandA.enqueue(itema);

            for (int j = 0; j < amount; j++) {
                runsA[i][j] = delTimerA().measureMicros(dqrandA.dequeue(), a);
                runsB[i][j] = delTimerB().measureMicros(dqrandB.dequeue(), b);
            }
        }

        Double[] timesA = Util.sampleMean(runsA);
        Double[] rev_A = Util.reverse(timesA, timesA.length);
        Double[] A = Util.copy(rev_A, 0);
        Double[] timesB = Util.sampleMean(runsB);
        Double[] rev_B = Util.reverse(timesB, timesB.length);
        Double[] B = Util.copy(rev_B, 0);

        return new Double[][]{A, B};
    }
}