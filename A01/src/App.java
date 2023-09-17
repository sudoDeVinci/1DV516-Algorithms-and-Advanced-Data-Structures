package src;
import src.uf.QuickFind;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;
import src.Util;
import src.ksum.ThreeSum;
import src.ksum.ThreeSumCached;

public class App {
    final int SAMPLES = 30;
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
        Integer[] unions = { 10000, 15000, 20000, 25000, 30000,
                             35000, 40000, 45000, 50000, 55000,
                             60000, 65000, 70000, 75000, 80000,
                             85000, 90000};
        
        WeightedUnionFind wuf = new WeightedUnionFind(UF_SIZE);
        Double[] times = getQfTimesFixedSize(unions, wuf);
        System.out.println("\nGraphing WUF FS 100_00");
        plt = new Plotter("uf/FS100_000_WeightedUnionFind.png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"Weighted Union Find 100_000 elements");
        plt.plot(unions, times);
        wuf = null;

        QuickFind qf = new QuickFind(UF_SIZE);
        times = getQfTimesFixedSize(unions, qf);
        System.out.println("\nGraphing QF FS 100_00");
        plt = new Plotter("uf/FS100_000_QuickFind.png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"Quick Find 100_000 elements");
        plt.plot(unions, times);
        qf = null;



        /**
         * Get graphs for unions on 1_000_000 items.
         */
        this.UF_SIZE = 1_000_000;
        unions = new Integer[] { 100000, 125000, 150000, 175000, 200000, 225000, 250000, 275000, 
                                 300000, 325000, 350000, 375000, 400000, 425000, 450000, 475000,
                                 500000, 525000, 550000, 575000, 600000, 625000, 650000, 675000,
                                 700000, 725000, 750000, 775000, 800000, 825000, 850000, 875000,
                                 900000, 925000, 950000};

        wuf = new WeightedUnionFind(UF_SIZE);
        times = getQfTimesFixedSize(unions, wuf);
        System.out.println("Graphing WUF FS 1_000_000");
        plt = new Plotter("uf/FS1_000_000_WeightedUnionFind.png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"Weighted Union Find 1_000_000 elements");
        plt.plot(unions, times);
        wuf = null;

        qf = new QuickFind(UF_SIZE);
        times = getQfTimesFixedSize(unions, qf);
        System.out.println("Graphing QF FS 1_000_000");
        plt = new Plotter("uf/FS1_000_000_QuickFind.png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"Quick Find 1_000_000 elements");
        plt.plot(unions, times);
        qf = null;
        
    }

    public void kSumRace() {
        Plotter plt;

        Integer[] sizes = { 100, 200, 300, 400, 500, 600,
                            700, 800, 900, 1000, 1100, 1200,
                            1300, 1400, 1500};
        Integer[] targets = Util.genIntArray(sizes.length);

        ThreeSumCached tsc = new ThreeSumCached();
        Double[] times = getksumTimes(sizes, tsc, targets);
        System.out.println("\nGraphing cached 3-Sum Max of 1500");
        plt = new Plotter("ksum/cached_1500.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Cached 3-Sum 1500 Max Elements.");
        plt.plot(sizes, times);

        ThreeSum ts = new ThreeSum();
        times = getksumTimes(sizes, ts, targets);
        System.out.println("\nGraphing brute force 3-Sum Max of 1500");
        plt = new Plotter("ksum/brute_force_1500.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"3-Sum Brute Force 1500 Max Elements.");
        plt.plot(sizes, times);

        sizes = new Integer[]{ 100, 200, 300, 400, 500, 600,
                700, 800, 900, 1000, 1100, 1200,
                1300, 1400, 1500, 1600, 1700, 1800,
                1900, 2000};
        targets = Util.genIntArray(sizes.length);

        times = getksumTimes(sizes, tsc, targets);
        System.out.println("\nGraphing cached 3-Sum Max of 2000");
        plt = new Plotter("ksum/cached_2000.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Cached 3-Sum 2000 Max Elements.");
        plt.plot(sizes, times);

        times = getksumTimes(sizes, ts, targets);
        System.out.println("\nGraphing brute force 3-Sum Max of 2000");
        plt = new Plotter("ksum/brute_force_2000.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"3-Sum Brute Force 2000 Max Elements.");
        plt.plot(sizes, times);
    }

    public Double[] getksumTimes(Integer[] sizes, ThreeSum ts, Integer[] targets) {
        int length = sizes.length;
        Double[] times = new Double[length];

        for (int i = 0; i < length; i++) {
            Integer[] nums = Util.genIntArray(sizes[i]);
            times[i] = measureKsumExecTime(ts, nums, targets[i]);
        }
        return times;
    }

    private Double[] getQfTimesFixedSize(Integer[] unions, UnionFind uf) {
        int length = unions.length;
        Double[] times = new Double[length];
        
        for (int i = 0; i < length; i++) {
            uf.reset();
            Integer[][] pairs = Util.genXYPairs(unions[i], UF_SIZE);

            times[i] = measureUfExecTime(uf, pairs);
        }

        return times;
    }


    private double measureKsumExecTime(ThreeSum ts, Integer[] nums, Integer target) {
        double[] samples = new double[SAMPLES];
        Timeit timer;

        for (int x = 0; x < SAMPLES; x++) {
            timer = new Timeit((args) -> {
                Integer t = (Integer) args[1];
                Integer [] n = (Integer[]) args[0];
                ts.threeSum(n, t);
            });
            samples[x] = timer.measureMilis(nums, target);
        }
        return Util.sampleMean(samples);
    }



    private double measureUfExecTime(UnionFind uf, Integer[][] pairs) {
        double[] samples = new double[SAMPLES];
        Timeit timer;

        for (int x = 0; x < SAMPLES; x++) {
            timer = new Timeit((args) -> {
                Integer[][] connections = (Integer[][]) args[0];
                uf.run_connected(connections);
            });
            samples[x] = timer.measureNanos((Object) pairs);
        }
        return Util.sampleMean(samples);
    }

    public static void main(String[] args) {
        App app = new App();
        app.qfRace();
        app.kSumRace();
    }
}
