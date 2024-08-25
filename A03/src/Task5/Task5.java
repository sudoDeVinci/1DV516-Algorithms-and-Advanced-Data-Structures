package Task5;

import Plot.Plot;
import Plot.Plotter;
import java.util.HashSet;
import java.util.Random;
import util.Timeit;

public class Task5 {
    static Random rand = new Random();

    public static Timeit QStoHS () {
        return new Timeit((args) -> {
            Integer[] arr = (Integer[]) args[0];
            Integer depth = (Integer) args[1];
            QuickSort.sort(arr, QuickSort.Secondary.Heap, depth);
        });
    }

    public static Timeit QStoIS () {
        return new Timeit((args) -> {
            Integer[] arr = (Integer[]) args[0];
            Integer depth = (Integer) args[1];
            QuickSort.sort(arr, QuickSort.Secondary.Insertion, depth);
        });
    }

    public static Timeit QSt () {
        return new Timeit((args) -> {
            Integer[] arr = (Integer[]) args[0];
            QuickSort.sort(arr, QuickSort.Secondary.None, arr.length);
        });
    }

    public static Integer[] getRandomArray(int N) throws IllegalArgumentException {
    if (N < 0) throw new IllegalArgumentException("N and bound must be non-negative");

    HashSet<Integer> uniqueNumbers = new HashSet<>(N);
    while (uniqueNumbers.size() < N) {
        int number = rand.nextInt(N + 1);
        uniqueNumbers.add(number);
    }
    return uniqueNumbers.toArray(Integer[]::new);
    }

    public static void main(String[] args) {

        int items = 25;
        int samples = 60;

        Double[] times_qs = new Double[items];
        Double[] times_qs_hs = new Double[items];
        Double[] times_qs_is = new Double[items];

        for (int idx = 0; idx < items; idx++) {
            times_qs[idx] = 0.0;
            times_qs_hs[idx] = 0.0;
            times_qs_is[idx] = 0.0;
        }

        Integer[] depths = new Integer[items];

        for (int i = 0; i < items; i++) {
            
            depths[i] = i;
            System.out.println("Depth " + depths[i]);

            Integer[] arr = Task5.getRandomArray(1000);

            for (int j = 0; j < samples; j++) {
                // Deep copy arr to new arr for sorting,
                Integer[] arr_qs_hs = arr.clone();
                times_qs[i] += Task5.QStoHS().measureMilis(arr_qs_hs, i);

                Integer[] arr_qs_is = arr.clone();
                times_qs_is[i] += Task5.QStoIS().measureMilis(arr_qs_is, i);

                Integer[] arr_qs = arr.clone();
                times_qs_hs[i] += Task5.QSt().measureMilis(arr_qs, i);
            }
        
            times_qs_hs[i] /= samples;
            times_qs_is[i] /= samples;
            times_qs[i] /= samples;
            }

        Plotter<Integer, Double> plotter = new Plotter<>("");
        plotter.setTitle("QuickSort vs HeapSort vs Insertion Sort");
        plotter.setXLabel("Depth");
        plotter.setYLabel("Time (miliseconds)");
        plotter.setFontSize(30);

        Plot<Integer, Double> plot_qs_hs = new Plot<>("HeapSort", Plot.Type.LINE, depths, times_qs_hs);
        plot_qs_hs.setSize(10);
        Plot<Integer, Double> plot_qs_is = new Plot<>("Insertion Sort", Plot.Type.LINE, depths, times_qs_is);
        plot_qs_is.setSize(10);
        Plot<Integer, Double> plot_qs = new Plot<>("QuickSort", Plot.Type.LINE, depths, times_qs);
        plot_qs.setSize(10);

        plotter.add(plot_qs_hs);
        plotter.add(plot_qs_is);
        plotter.add(plot_qs);
        plotter.plot();
    }
}
