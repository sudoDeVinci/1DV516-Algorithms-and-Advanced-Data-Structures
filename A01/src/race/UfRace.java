package src.race;

import src.Timeit;
import src.Util;
import src.uf.UnionFind;

public class UfRace {
    private int SIZE;
    private int START;
    private int STEPS;
    private final int SAMPLES = 100;



    




    private Double[] getUfTimes(Integer[] unions, UnionFind uf) {
        Double[] measured = new Double[unions.length];
        double[] samples = new double[SAMPLES];

        Timeit timer;
        timer = new Timeit((args) -> {
            Integer[][] pairs = (Integer[][]) args[0];
            UnionFind Unf = (UnionFind) args[1];
            exec(pairs, Unf);
        });

        for(int j = 0; j < measured.length; j++) {
            Integer[][] pairs = Util.genXYPairs(unions[j], SIZE);
            
            for(int i = 0; i< SAMPLES; i++) {
                samples[i] = timer.measureMilis(pairs, uf);     
            }

            measured[j] = Util.sampleMean(samples);
            samples = new double[SAMPLES];
        }
        return measured;
    }


    private void exec(Integer[][] pairs, UnionFind uf) {
        for (Integer[] pair: pairs) {
            uf.union(pair[0], pair[1]);
        }
    }
}
