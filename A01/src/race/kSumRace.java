package src.race;

import src.Plotter;
import src.Timeit;
import src.Util;
import src.ksum.ThreeSum;
import src.ksum.ThreeSumCached;
import src.uf.WeightedUnionFind;

public class kSumRace {
    private int SIZE;
    private int START;
    private int STEPS;
    private final int SAMPLES = 100;

    public void run(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {

        this.START = START;
        this.STEPS = STEPS;
        this.SIZE = SIZE;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] sizes = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            sizes[i] = START + i * STEPS;
        }

        Integer [] targets = Util.genIntArray(SIZE);
        
        System.out.println("\nBrute-Force versus Cached 3-Sum "+SIZE+" Max Elements.");
                
        
        Plotter<Integer, Double>pltTS = new Plotter<>("ksum/TS_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Brute-Force 3-Sum @ "+SIZE+" Max Elements.");
        Double[] times = runTS(SIZE, STEPS, START, sizes, targets, pltTS);
        plt.add(sizes, times, "Brute-force");

        Plotter<Integer, Double>pltTSC = new Plotter<>("ksum/TSC_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Cached 3-Sum @ "+SIZE+" Max Elements.");
        times = runTSC(SIZE, STEPS, START, sizes, targets, pltTSC);
        plt.add(sizes, times, "Cached");
        
        plt.plot();
    }

    private Double[] runTSC(int SIZE, int STEPS, int START, Integer[] sizes_threesum,Integer [] targets, Plotter<Integer,Double> plt) {

        System.out.println("\nGraphing Cached Threesum @ "+SIZE+" elements");
        
        ThreeSumCached tsc = new ThreeSumCached();
        Double[] times = getksumTimes(sizes_threesum, tsc, targets);
        plt.add(sizes_threesum, times, "Cached");
        plt.plot();

        return times;
    }

    private Double[] runTS(int SIZE, int STEPS, int START, Integer[] sizes_threesum,Integer [] targets, Plotter<Integer,Double> plt) {

        System.out.println("\nGraphing Cached Threesum @ "+SIZE+" elements");
        
        ThreeSum ts = new ThreeSum();
        Double[]times = getksumTimes(sizes_threesum, ts, targets);
        plt.add(sizes_threesum, times, "Cached");
        plt.plot();

        return times;
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

    private double measureKsumExecTime(ThreeSum ts, Integer[] nums, Integer target) {
        double[] samples = new double[SAMPLES];
        Timeit timer;
        timer = new Timeit((args) -> {
                Integer t = (Integer) args[1];
                Integer [] n = (Integer[]) args[0];
                ts.threeSum(n, t);
            });
        for (int x = 0; x < SAMPLES; x++) {
            
            samples[x] = timer.measureMilis(nums, target);
        }
        return Util.sampleMean(samples);
    }

}
