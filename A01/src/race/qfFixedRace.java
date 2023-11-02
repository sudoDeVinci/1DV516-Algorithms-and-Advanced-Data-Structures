package src.race;

import src.Plotter;
import src.Timeit;
import src.Util;
import src.uf.QuickFind;
import src.uf.QuickUnion;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;

public class qfFixedRace {
    private int SIZE;
    private int START;
    private int STEPS;
    
    private final int SAMPLES = 100;

    /**
     * 
     * Get graphs for unions on 10_000_000 items.
     */
    public void runAll(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {

        int arraySize = (SIZE - START) / STEPS;
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Weighted Union Find versus Quick Find versus Quick union at fixed sized "+SIZE+" for varying numbers of Unions");
        
        /**
         * Create individual graph.
         */
        
        Double[] qutimes = runQU(SIZE, unions);
        plt.add(unions, qutimes, "QU");


        /**
         * Create the combination graph.
         */
        
        Double[][] wufvqftimes = runWFvQF(SIZE, STEPS, START);

        plt.add(unions, wufvqftimes[0], "WUF");
        plt.add(unions, wufvqftimes[1], "QF");
        plt.plot();
    }


    public Double[][] runWFvQF(int SIZE, int STEPS, int START) {

        int arraySize = (SIZE - START) / STEPS; 
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Weighted Union Find versus Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQF_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.SCATTER,"Weighted Union Find vs Quick Find @ fixed "+SIZE+" nodes");

        Double[] wftimes = runWF(SIZE, unions);
        plt.add(unions, wftimes, "WF");

        
        Double[] qftimes = runQF(SIZE, unions);
        plt.add(unions, qftimes, "QF");

        plt.plot();

        return new Double[][]{wftimes, qftimes};
    }

    private Double[] runWF(int SIZE, Integer[] unions) {

        System.out.println("\nGraphing Weighted Union Find at fixed sized "+SIZE+" for varying numbers of Unions");
        Plotter<Integer, Double> plt = new Plotter<>("uf/WUF_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.LINEAR,"Weighted Union Find @ fixed "+SIZE+" nodes");
        WeightedUnionFind wuf = new WeightedUnionFind(SIZE);
        Double[] times = getUfTimes(unions, wuf);
        plt.add(unions, times, wuf.name);
        wuf = null;

        plt.plot();

        return times;
    }

    private Double[] runQF(int SIZE, Integer[] unions) {

        System.out.println("\nGraphing Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        Plotter<Integer, Double> plt = new Plotter<>("uf/QF_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.LINEAR,"Quick Find @ fixed "+SIZE+" nodes");
        QuickFind qf = new QuickFind(SIZE);
        Double[] times = getUfTimes(unions, qf);
        plt.add(unions, times, qf.name);
        qf = null;

        plt.plot();

        return times;
    }

    private Double[] runQU(int SIZE, Integer[] unions) {

        System.out.println("\nGraphing Quick Union at fixed sized "+SIZE+" for varying numbers of Unions");
        
        QuickUnion qu = new QuickUnion(SIZE);

        Plotter<Integer, Double> plt = new Plotter<>("uf/QU_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.SCATTER,"Quick Union @ fixed "+SIZE+" nodes");
        
        Double[] times = getUfTimes(unions, qu);
        
        plt.add(unions, times, qu.name);
        qu = null;

        plt.plot();

        return times;
    }


    private Double[] getUfTimes(Integer[] unions, UnionFind uf) {
        Double[] measured = new Double[unions.length];
        double[] samples = new double[SAMPLES];

        Timeit timer;
        timer = new Timeit((args) -> {
            Integer[][] pairs = (Integer[][]) args[0];
            UnionFind Unf = (UnionFind) args[1];
            exec(pairs, Unf);
        });

        for(int j = 0; j < measured.length; j++) {
            Integer[][] pairs = Util.genXYPairs(unions[j], SIZE);
            
            for(int i = 0; i< SAMPLES; i++) {
                samples[i] = timer.measureMilis(pairs, uf);     
            }

            measured[j] = Util.sampleMean(samples);
            samples = new double[SAMPLES];
        }
        return measured;
    }

    private void exec(Integer[][] pairs, UnionFind uf) {
        for (Integer[] pair: pairs) {
            uf.union(pair[0], pair[1]);
        }
    }

}