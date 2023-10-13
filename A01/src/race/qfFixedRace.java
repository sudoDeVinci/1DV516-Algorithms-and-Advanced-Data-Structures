package src.race;

import src.Plotter;
import src.Timeit;
import src.Util;
import src.uf.QuickFind;
import src.uf.QuickUnion;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;

public class qfFixedRace {

    final int UF_SIZE = 100_000;
    final int SAMPLES = 100;
    /**
     * 
     * Get graphs for unions on 10_000_000 items.
     */
    public void start() {
        Integer[] unions = { 10000, 12500, 15000, 17500, 20000, 22500, 25000, 27500, 30000, 32500,
                            35000, 37500, 40000, 42500, 45000, 47500, 50000, 52500, 55000, 57500,
                            60000, 62500, 65000, 67500, 70000, 72500, 75000, 77500, 80000, 82500,
                            85000, 87500, 90000, 92500, 95000 };
    
        String max = unions[unions.length-1].toString();

        System.out.println("\nGraphing Weighted Union Find versus Quick Find at fixed sized "+UF_SIZE+" for varying numbers of Unions");
        
        WeightedUnionFind wuf = new WeightedUnionFind(UF_SIZE);
        Double[] times = getUfTimes(unions, wuf);
        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQF_fixed_"+UF_SIZE+"_"+max+"elements.png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"Weighted UnionFind v.s QuickFind @ fixed "+UF_SIZE+" for varying Unions");
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
        double[][] samples = new double[SAMPLES][unions.length];
        for (int i=0; i<SAMPLES; i++) samples[i] = getUfTimeSample(unions, uf);
        return Util.sampleMean(samples);
    }

    private double[] getUfTimeSample(Integer[] unions, UnionFind uf) {
        int length = unions.length;
        double[] times = new double[length];

        Timeit timer = new Timeit((args) -> {
                Integer[][] connections = (Integer[][]) args[0];
                uf.run_union(connections);
            });
        
        for (int i = 0; i < length; i++) {
            Integer[][] pairs = Util.genXYPairs(unions[i], UF_SIZE);
            times[i] = timer.measureNanos((Object) pairs);
        }
        return times;
    }
}