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
    /**
     * Plot the runs of two implementations of UnionFind.
     * For a Union Find of implementation of size N, we perform N unions.
     * @param N
     */
    public void qfRaceProportional(int N){
        Integer[] sizes = new Integer[N];
        for (int i = 0; i < N; i++) {
            sizes[i] = 10*i;
        }

        WeightedUnion wuf = new WeightedUnion(sizes[0]);
        QuickFind qf = new QuickFind(sizes[0]);
        Float[] wuftimes = getQfTimes(sizes, wuf);
        Float[] qftimes = getQfTimes(sizes, qf);

        Plotter plt = new Plotter("QuickFind.png", "Unions", "Time (ns)","Quick Find");
        plt.plot(sizes, qftimes);
        qftimes = new Float[1];

        plt = new Plotter("WeightedUnionFind.png", "Unions", "Time (ns)","Weighted Union Find");
        plt.plot(sizes, wuftimes);
        wuftimes = new Float[1];
    }


    /**
     * Get the times in nanoseconds to a number of runs on a given UnionFind algo of various sizes.
     * @param sizes
     * @param uf
     * @return
     */
    private Float[] getQfTimes(Integer[] sizes, UnionFind uf) {
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
                uf.reset(sizes[i]);
                timer = new Timeit((args) -> {
                    Integer[][] connections = (Integer[][]) args[0];
                    uf.Run(connections);
                });
                samples[x] = timer.measureNanos((Object) pairs);
            }
            times[i] = sampleMean(samples);
            //System.out.printf("Elapsed Time: %f ns%n", times[i]);
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

    public static void main(String[] args) {
        App app = new App();
        app.qfRaceProportional(10000);
    }
}
