package src;

import src.race.kSumRace;

public class Algos {
    public static void main(String[] args) {

        int START = 100;
        int STEPS = 50;
        int SIZE = 1000;


        Plotter<Integer, Double> plt;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] sizes = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizes[i] = START + i * STEPS;
        }

        Double[] y_coords = new Double[arraySize];
        for(int i = 0; i<arraySize; i++) y_coords[i] = 2.9 * (Math.pow(10,-6)) * Math.pow(sizes[i], 2);

        plt = Plotter.LoadPlotter("src/graphs/ksum/TSC_1000_plotter.ser");
        plt.add(sizes, y_coords, Plotter.Type.LINE, "2.9\\cdot10^{-6}\\cdot N^{2}");
        plt.plot();

        
        /**
         * int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] sizes = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizes[i] = START + i * STEPS;
        }

        Double[] y_coords = new Double[arraySize];
        for(int i = 0; i<arraySize; i++) y_coords[i] = 0.000001 * Math.pow(sizes[i], 3); 
        plt.add(sizes, y_coords, Plotter.Type.LINE, "X^{3}");
        y_coords = new Double[arraySize];
        for(int i = 0; i<arraySize; i++) y_coords[i] = 0.000029 * Math.pow(sizes[i], 2); 
        plt.add(sizes, y_coords, Plotter.Type.LINE, "X^{2}");
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         *  plt = new Plotter<>("ksum/BruteForcevsCached_1000.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL, "Brute-Force versus Cached 3-Sum 1000 Max Elements");
        race = new kSumRace();
        race.run(1000, 50, 100, plt);
        plt = new Plotter<>("uf/WUFvsQF_5_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                           "Weighted UnionFind vs Quick Find @ fixed 5_000 nodes");

        race.runWFvQF(5000, 100, 500, plt);
        

        plt = new Plotter<>("uf/WUFvsQF_10_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                           "Weighted UnionFind vs Quick Find @ fixed 10_000 nodes");

        race.runWFvQF(10000, 100, 1000, plt);



        plt = new Plotter<>("uf/WUFvsQF_20_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                            "Weighted UnionFind vs Quick Find @ fixed 20_000 nodes");
        race.runWFvQF(20000, 200, 2000, plt);

        python3 src/scripts/pyplot.py src/graphs/test/x2.png [[1,2,3,4,5,6,7,8,9,10]] [[2.0,8.0,18.0,32.0,50.0,72.0,98.0,128.0,162.0,200.0]] X TEST Y TEST TEST PLOT ["Line"] ["test Plot label yeah"] 
        
        */
    }
}