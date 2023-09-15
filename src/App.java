package src;

import src.uf.QuickFind;
import src.uf.UnionFind;
import src.uf.WeightedUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class App {
    final int SAMPLES = 100;
    final int UF_SIZE = 1_000_000;

    /**
     * Plot the runs of two implementations of UnionFind.
     * For a Union Find of implementation of size N, we perform N unions.
     * @param N
     */
    public void qfRace(){
        Plotter plt;
        Integer[] sizes = {10, 50, 100, 500, 1000, 5000, 10000};

        
        


        /**
         * Get graphs for proportional union scaling.
         */
        WeightedUnion wuf = new WeightedUnion(sizes[0]);
        Float[] wuftimes = getQfTimesProp(sizes, wuf);
        plt = new Plotter("uf/ProportionalWeightedUnionFind.png", "Unions", "Time (ns)","Weighted Union Find");
        plt.plot(sizes, wuftimes);
        wuftimes = new Float[1];

        QuickFind qf = new QuickFind(sizes[0]);
        Float[] qftimes = getQfTimesProp(sizes, qf);
        plt = new Plotter("uf/ProportionalQuickFind.png", "Unions", "Time (ns)","Quick Find");
        plt.plot(sizes, qftimes);
        qftimes = new Float[1];


        /**
         * Get graphs for fixed-sized union scaling.
         */
        qf = new QuickFind(UF_SIZE);
        qftimes = getQfTimesFixedSize(sizes, qf);
        plt = new Plotter("uf/FS1000000_QuickFind.png", "Unions", "Time (ns)","Quick Find");
        plt.plot(sizes, qftimes);
        qftimes = new Float[1];

        wuf = new WeightedUnion(UF_SIZE);
        wuftimes = getQfTimesFixedSize(sizes, wuf);
        plt = new Plotter("uf/FS1000000_WeightedUnionFind.png", "Unions", "Time (ns)","Weighted Union Find");
        plt.plot(sizes, wuftimes);
        wuftimes = new Float[1];

    }


    /**
     * Get the times in nanoseconds to a number of runs on a given UnionFind algo of various sizes.
     * @param sizes
     * @param uf
     * @return
     */
    private Float[] getQfTimesProp(Integer[] sizes, UnionFind uf) {
        int length  = sizes.length;
        Float[] times = new Float[length];
        Float[] samples = new Float[SAMPLES];

        //Init timer
        Timeit timer;

        for (int i = 0; i < length; i++) {
            uf.reset(sizes[i]);
            Integer[][] pairs = genRandomArray(sizes[i]);
            samples = new Float[SAMPLES];
            for(int x = 0;x < SAMPLES; x++) {
                // uf.reset(sizes[i]);
                timer = new Timeit((args) -> {
                    Integer[][] connections = (Integer[][]) args[0];
                    uf.Run(connections);
                });
                samples[x] = timer.measureNanos((Object) pairs);
            }
            times[i] = sampleMean(samples);
        }
        return times;
    }

    private Float[] getQfTimesFixedSize(Integer[] unions, UnionFind uf) {
        int length  = unions.length;
        Float[] times = new Float[length];
        Float[] samples = new Float[SAMPLES];

        //Init timer
        Timeit timer;

        for(int i = 0; i < length; i++) {
            uf.reset(UF_SIZE);
            Integer[][] pairs = genRandomArray(unions[i], UF_SIZE);
            samples = new Float[SAMPLES];
            for(int x = 0;x < SAMPLES; x++) {
                // uf.reset(UF_SIZE);
                timer = new Timeit((args) -> {
                    Integer[][] connections = (Integer[][]) args[0];
                    uf.Run(connections);
                });
                samples[x] = timer.measureNanos((Object) pairs);
            }
            times[i] = sampleMean(samples);
        }
        return times;
    }   

    /**
     * Find the IQR, remove outliers, then find the average.
     * @param samples
     * @return
     */
    private float sampleMean(Float[] samples) {
        Arrays.sort(samples);
        float q1 = Quartile(samples, 25);
        float q3 = Quartile(samples, 75);
        float iqr = q3 - q1;

        double ub =  q3 + 1.5*iqr;
        double lb =  q1 - 1.5*iqr;

        List<Float> cleaned = new ArrayList<>();

        for (Float value : samples) {
            if (value >= lb && value <= ub) {
                cleaned.add(value);
            }
        }

        float sum = 0;
        for (Float value : cleaned) {
            sum += value;
        }
        return sum / cleaned.size();
    }

    private static <T> T Quartile(T[] data, int percentile) {
        int index = (int) Math.ceil((percentile / 100.0) * data.length) - 1;
        return data[index];
    }

    /**
     * Generate a random N array of integer pairs.
     */
    private Integer[][] genRandomArray(int N) {
        Random rand = new Random();
        Integer[][] pairs = new Integer[N][2];

        for (int i = 0; i < N; i++) {
            int rand1 = rand.nextInt(N);
            int rand2 = rand.nextInt(N);

            pairs[i][0] = rand1;
            pairs[i][1] = rand2;
        }
        return pairs;
    }
    
    private Integer[][] genRandomArray(int N, int bound) {
        Random rand = new Random();
        Integer[][] pairs = new Integer[N][2];

        for (int i = 0; i < N; i++) {
            int rand1 = rand.nextInt(bound);
            int rand2 = rand.nextInt(bound);

            pairs[i][0] = rand1;
            pairs[i][1] = rand2;
        }
        return pairs;
    }

    public static void main(String[] args) {
        App app = new App();
        app.qfRace();
    }
}
