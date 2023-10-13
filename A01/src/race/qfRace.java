package src.race;

import src.Plotter;
import src.Timeit;
import src.Util;
import src.uf.QuickFind;
import src.uf.QuickUnion;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;

public class qfRace {

    final int SAMPLES = 100;
    int UF_SIZE = 0;

    /**
     * Plot the runs of two implementations of UnionFind.
     * For a Union Find of implementation of size N, we perform N unions.
     * @param N
     */
    public void start() {
        /**
         * Get graphs for unions on 100_000 items.
         */
        
        Integer[] unions = { 10000, 12500, 15000, 17500, 20000, 22500, 25000, 27500, 30000, 32500,
                             35000, 37500, 40000, 42500, 45000, 47500, 50000, 52500, 55000, 57500,
                             60000, 62500, 65000, 67500, 70000, 72500, 75000, 77500, 80000, 82500,
                             85000, 87500, 90000, 92500, 95000, 100000};
        
        String max = unions[unions.length-1].toString();

        System.out.println("\nGraphing WUF vs QF vs QU with a Max of " + max + " elements");
        
        
        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQFvsQU_"+max+".png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"WUF vs QF vs QU with a Max of "+ max +" elements");
        
        
        WeightedUnionFind wuf = new WeightedUnionFind(UF_SIZE);
        Double[] times = getUfTimes(unions, wuf);
        plt.add(unions, times, "WUF");
        wuf = null;

        QuickFind qf = new QuickFind(UF_SIZE);
        times = getUfTimes(unions, qf);
        plt.add(unions, times, "QF");
        qf = null;

        QuickUnion qu = new QuickUnion(UF_SIZE);
        times = getUfTimes(unions, qu);
        plt.add(unions, times, "QU");
        qu = null;

        plt.plot();
    }

    private Double[] getUfTimes(Integer[] unions, UnionFind uf) {
        int length = unions.length;
        Double[] times = new Double[length];
        
        for (int i = 0; i < length; i++) {
            uf.reset(unions[i]*10);
            Integer[][] pairs = Util.genXYPairs(unions[i], unions[i]*10);

            times[i] = measureUfExecTime(uf, pairs);
        }

        return times;
    }

    private double measureUfExecTime(UnionFind uf, Integer[][] pairs) {
        double[] samples = new double[SAMPLES];
        Timeit timer;
        timer = new Timeit((args) -> {
                Integer[][] connections = (Integer[][]) args[0];
                uf.run_union(connections);
            });
        for (int x = 0; x < SAMPLES; x++) {
            samples[x] = timer.measureNanos((Object) pairs);
        }
        return Util.sampleMean(samples);
    }
}
