package Task5;

import java.util.HashSet;
import java.util.Random;
import util.Timeit;

public class Task5 {
    static Random rand = new Random();

    public static Timeit QStoHS () {
        return new Timeit((args) -> {
            Integer[] arr = (Integer[]) args[0];
            Integer depth = (Integer) args[1];
            QuickSort.sort(arr, depth, QuickSort.Secondary.Heap);
        });
    }

    static int[] getRandomArray(int N) throws IllegalArgumentException {
    if (N < 0) throw new IllegalArgumentException("N and bound must be non-negative");

    HashSet<Integer> uniqueNumbers = new HashSet<>(N);
    while (uniqueNumbers.size() < N) {
        int number = rand.nextInt((int)(N*1.5) + 1);
        uniqueNumbers.add(number);
    }
    return uniqueNumbers.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {

        int items = 30;
        int samples = 20;

        Double[] times_qs = new Double[items];
        Double[] times_qs_hs = new Double[items];
        Double[] times_qs_is = new Double[items];
        
        Integer[] depths = new Integer[items];

        for (int i = 0; i < items; i++) {
            double average;

            for (int j = 0; j < samples; j++) {

            }

        }
    }
}
