package src.race;

import src.Plotter;
import src.Util;
import src.uf.QuickFind;
import src.uf.QuickUnion;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;

public class qfFixedRace {

    final int SIZE = 100_000;
    final int STEPS = 1000;
    final int START = 1000;
    final int SAMPLES = 50;
    /**
     * 
     * Get graphs for unions on 10_000_000 items.
     */
    public void start() {
        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }
    
        String max = unions[unions.length-1].toString();

        System.out.println("\nGraphing Weighted Union Find versus Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        WeightedUnionFind wuf = new WeightedUnionFind(SIZE);
        Double[] times = getUfTimes(unions, wuf);
        Plotter<Integer, Double> plt = new Plotter<>("uf/simple_WUFvsQF_fixed_"+SIZE+"_"+max+"elements.png", "Unions", "Time (ns)", Plotter.Type.SCATTER,"Weighted UnionFind v.s QuickFind @ fixed "+SIZE+" for varying Unions");
        plt.add(unions, times, wuf.name);
        wuf = null;

        QuickFind qf = new QuickFind(SIZE);
        times = getUfTimes(unions, qf);
        plt.add(unions, times, qf.name);
        qf = null;

        QuickUnion qu = new QuickUnion(SIZE);
        times = getUfTimes(unions, qu);
        plt.add(unions, times, qu.name);
        qu = null;

        plt.plot();
    }

    public Double[] getUfTimes(Integer[] unions, UnionFind uf) {
        Double[] measured = new Double[unions.length];
        double[] samples = new double[SAMPLES];

        for(int j = 0; j < measured.length; j++) {
            Integer[][] pairs = Util.genXYPairs(unions[j], SIZE);
            
            for(int i = 0; i< SAMPLES; i++) {
                long start = System.nanoTime();

                for (Integer[] pair: pairs) {
                    uf.union(pair[0], pair[1]);
                }

                long stop = System.nanoTime();
                samples[i] = stop-start;
            }

            measured[j] = Util.sampleMean(samples);
            samples = new double[SAMPLES];
        }
        return measured;
    }
}