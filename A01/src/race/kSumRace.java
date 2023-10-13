package src.race;

import src.Plotter;
import src.Timeit;
import src.Util;
import src.ksum.ThreeSum;
import src.ksum.ThreeSumCached;

public class kSumRace {
    final int SAMPLES = 50;
    public void start() {

        Integer[] sizes_threesum = new Integer[]{ 100,
                                                  110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 
                                                  210, 220, 230, 240, 250, 260, 270, 280, 290, 300,
                                                  310, 320, 330, 340, 350, 360, 370, 380, 390, 400,
                                                  410, 420, 430, 440, 450, 460, 470, 480, 490, 500};
        
        String max = sizes_threesum[sizes_threesum.length-1].toString();

        Integer [] targets = Util.genIntArray(sizes_threesum.length);
        
        System.out.println("\nBrute-Force versus Cached 3-Sum "+max+" Max Elements.");
                
        Plotter<Integer, Double>plt = new Plotter<>("ksum/BruteforcevsCached_"+max+".png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Brute-Force versus Cached 3-Sum "+max+" Max Elements.");
        

        ThreeSum ts = new ThreeSum();
        Double[] times = getksumTimes(sizes_threesum, ts, targets);
        plt.add(sizes_threesum, times, "Brute-force");

        ThreeSumCached tsc = new ThreeSumCached();
        times = getksumTimes(sizes_threesum, tsc, targets);
        plt.add(sizes_threesum, times, "Cached");
        plt.plot();
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
