package Task6;

import Plot.Plot;
import Plot.Plotter;
import Task5.Task5;
import util.Timeit;

public class Task6 {

    public static Timeit iterative () {
        return new Timeit ((args) -> {
            Integer[] arr = (Integer[]) args[0];
            MergeSortiterative.sort(arr);
        });
    }

    public static Timeit recursive () {
        return new Timeit ((args) -> {
            Integer[] arr = (Integer[]) args[0];
            MergeSortRecursive.sort(arr);
        });
    }

    public static void main(String[] args) {
        int samples = 60;
        int items = 100;

        Double[] times_iterative = new Double[items];
        Double[] times_recursive = new Double[items];
        Integer[] X = new Integer[items];

        for (int i = 0; i < items; i++) {
            times_iterative[i] = 0.0;
            times_recursive[i] = 0.0;
        }

        for (int i = 0; i < items; i++) {
            int xplace = 10*i;
            Integer[] arr = Task5.getRandomArray(xplace);
            X[i] = xplace;

            for (int j = 0; j < samples; j++) {
                Integer[] arr_iterative = arr.clone();
                times_iterative[i] += Task6.iterative().measureMilis(arr_iterative, i);

                Integer[] arr_recursive = arr.clone();
                times_recursive[i] += Task6.recursive().measureMilis(arr_recursive,i);
            }

            times_iterative[i] /= samples;
            times_recursive[i] /= samples;
        }

        Plotter<Integer, Double> plotter = new Plotter<>("Merge sort - Recursive versus iterative");
        plotter.setTitle("Merge sort - Recursive versus iterative");
        plotter.setXLabel("Number of elements");
        plotter.setYLabel("Time (miliseconds)");
        plotter.setFontSize(30);

        Plot<Integer, Double> plot_iterative = new Plot<>("Iterative", Plot.Type.LINE, X, times_iterative);
        plot_iterative.setSize(10);
        Plot<Integer, Double> plot_recursive = new Plot<>("Recursive", Plot.Type.LINE, X, times_recursive);
        plot_recursive.setSize(10);

        plotter.add(plot_iterative);
        plotter.add(plot_recursive);
        plotter.plot();
    }
}
