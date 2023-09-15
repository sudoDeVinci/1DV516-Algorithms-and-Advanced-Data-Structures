package src;

import src.uf.QuickFind;
import src.uf.UnionFind;
import src.uf.WeightedUnion;
import java.util.Random;

public class App {

    /**
     * Plot 
     * @param N
     */
    public void qfRace(int N){
        Integer[] sizes = new Integer[N];
        for (int i = 0; i < N; i++) {
            sizes[i] = 10^i;
        }

        WeightedUnion wuf = new WeightedUnion(sizes[0]);
        QuickFind qf = new QuickFind(sizes[0]);
        Float[] qftimes = getQfTimes(sizes, wuf);
        Float[] wuftimes = getQfTimes(sizes, qf);

        Plotter plt = new Plotter("QuickFind.png", "Unions", "Time","Union Find");
        plt.plot(sizes, qftimes);
        plt = new Plotter("WeightedUnionFind.png", "Unions", "Time","Weighted Union Find");
        plt.plot(sizes, wuftimes);
    }


    private Float[] getQfTimes(Integer[] sizes, UnionFind uf) {
        int length  = sizes.length;
        Float[] times = new Float[length];

        //Init timer
        Timeit timer;

        for (int i = 0; i < length; i++) {
            Integer[][] pairs = genRandomArray(sizes[i]);

            timer = new Timeit((args) -> {
                Integer[][] connections = (Integer[][]) args[0];
                uf.Run(connections);
            });
            times[i] = timer.measureNanos((Object) pairs);
            
        }

        return times;
    }

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
        app.qfRace(5);
    }
}
