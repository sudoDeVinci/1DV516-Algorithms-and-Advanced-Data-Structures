package src;

import java.util.Random;

public class PercEstimator {

    /**
     * Calculate the percolation threshold for a given set of runs
     * for grids of a given size.
     * @param N
     * @param SAMPLES
     * @return
     */
    public static double[] getThreshold(int N, int SAMPLES) {
        Random rand = new Random();
        double size = N*N;
        double[] thresholds = new double[SAMPLES];

        for (int i = 0; i < SAMPLES; i++) {
            Percolation perc = new Percolation(N);

            while (true) {
                int row = rand.nextInt(N) + 1;
                int col = rand.nextInt(N) + 1;
                
                perc.open(row, col);
                
                if (perc.percolates()) {
                    thresholds[i] = perc.getOpenCount() / size;
                    break;
                }
            }
        }
        return thresholds;
    }

    /**
     * Run Percolation and check threshold for a given grid.
     * @param N
     * @param SAMPLES
     * @return
     */
    public static double[] run(int N, int SAMPLES) {
        double[] thresholds = PercEstimator.getThreshold(N, SAMPLES);
        double mean = Util.mean(thresholds);
        double stdDev = Util.stdDev(thresholds);

        String out = String.format("Grid Size: %d\nThreshold: %,.4f\nstd Dev: %,.4f\n",
                                    N, mean, stdDev);
        System.out.println(out);

        return new double[] {mean, stdDev};
    }


    public static void main(String[] args) {
        int SIZE = 1000;
        int SAMPLES = 100;
        int STEPS = 50;
        int START = 10;
        

        int arraySize = (SIZE - START) / STEPS; 
        Integer[] sizes = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizes[i] = START + i * STEPS;
        }

        Double[] thresholds = new Double[sizes.length];
        Double[] stdDevs = new Double[sizes.length];
        String[][] out = new String[sizes.length][3];

        for (int i = 0; i < sizes.length; i++) {
            double[] result = run(sizes[i], SAMPLES); 
            thresholds[i] = result[0];
            stdDevs[i] = result[1];
        }

        for (int i = 0; i < sizes.length; i++) {
            out[i] = new String[]{String.format("%d", sizes[i]),
                                  String.format("%,.5f", thresholds[i]),
                                  String.format("%,.5f", stdDevs[i])};
        }
        Util.writeCSV("src/graphs/perc/runs.csv", new String[]{"Sizes", "Thresholds", "Standard Dev"}, out);



        Plotter<Integer, Double> plt = new Plotter<>("perc/thresholds.png",
                                                    "Grid Size",
                                                    "probability",
                                                    Plotter.Type.LINE,
                                                    "Percolation thresholds of varying grid sizes");

        plt.add(sizes, thresholds, "Threshold");
        plt.save();
        plt.plot();
    }
}
