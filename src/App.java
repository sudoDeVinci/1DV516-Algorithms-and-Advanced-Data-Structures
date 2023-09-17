package src;
import src.uf.QuickFind;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;
import src.Util;

public class App {
    final int SAMPLES = 1000;
    int UF_SIZE = 2;

    /**
     * Plot the runs of two implementations of UnionFind.
     * For a Union Find of implementation of size N, we perform N unions.
     * @param N
     */
    public void qfRace() {
        Plotter plt;

        /**
         * Get graphs for unions on 100_000 items.
         */
        this.UF_SIZE = 100_000;
        Integer[] unions = { 1000, 5000, 10000, 15000, 20000, 25000,
                            30000, 35000, 40000, 45000, 50000, 50000,
                            60000, 70000, 80000, 90000};
        
        WeightedUnionFind wuf = new WeightedUnionFind(UF_SIZE);
        Double[] times = getQfTimesFixedSize(unions, wuf);
        System.out.println("Graphing WUF FS");
        plt = new Plotter("uf/FS100_000_WeightedUnionFind.png", "Unions", "Time (ms)","Weighted Union Find 100_000 elements");
        plt.plot(unions, times);
        wuf = null;

        QuickFind qf = new QuickFind(UF_SIZE);
        times = getQfTimesFixedSize(unions, qf);
        System.out.println("Graphing QF FS");
        plt = new Plotter("uf/FS100_000_QuickFind.png", "Unions", "Time (ms)","Quick Find 100_000 elements");
        plt.plot(unions, times);
        qf = null;



        /**
         * Get graphs for unions on 1_000_000 items.
         */
        this.UF_SIZE = 1_000_000;
        unions = new Integer[] { 50000, 100000, 150000, 200000, 250000, 300000,
                                350000, 400000, 450000, 500000, 550000,
                                650000, 700000, 800000, 900000, 950000};

        wuf = new WeightedUnionFind(UF_SIZE);
        times = getQfTimesFixedSize(unions, wuf);
        System.out.println("Graphing WUF FS");
        plt = new Plotter("uf/FS1_000_000_WeightedUnionFind.png", "Unions", "Time (ms)","Weighted Union Find 1_000_000 elements");
        plt.plot(unions, times);
        wuf = null;

        qf = new QuickFind(UF_SIZE);
        times = getQfTimesFixedSize(unions, qf);
        System.out.println("Graphing QF FS");
        plt = new Plotter("uf/FS1_000_000_QuickFind.png", "Unions", "Time (ms)","Quick Find 1_000_000 elements");
        plt.plot(unions, times);
        qf = null;
    }

    private Double[] getQfTimesFixedSize(Integer[] unions, UnionFind uf) {
        int length = unions.length;
        Double[] times = new Double[length];
        
        for (int i = 0; i < length; i++) {
            uf.reset();
            Integer[][] pairs = Util.genXYPairs(unions[i], UF_SIZE);

            times[i] = measureExecutionTime(uf, pairs);
        }

        return times;
    }

    private double measureExecutionTime(UnionFind uf, Integer[][] pairs) {
        double[] samples = new double[SAMPLES];
        Timeit timer;

        for (int x = 0; x < SAMPLES; x++) {
            timer = new Timeit((args) -> {
                Integer[][] connections = (Integer[][]) args[0];
                uf.run_connected(connections);
            });
            samples[x] = timer.measureMilis((Object) pairs);
        }
        return Util.sampleMean(samples);
    }

    public static void main(String[] args) {
        App app = new App();
        app.qfRace();
    }
}
