package src.race;

import src.Plotter;
import src.Timeit;
import src.Util;
import src.uf.QuickFind;
import src.uf.QuickUnion;
import src.uf.UnionFind;
import src.uf.WeightedUnionFind;

public class qfFixedRace {
    private int SIZE;
    private int START;
    private int STEPS;
    private final int SAMPLES = 100;

    /**
     * 
     * Get graphs for unions on 10_000_000 items.
     */
    public void runAll(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {
        this.START = START;
        this.STEPS = STEPS;
        this.SIZE = SIZE;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Weighted Union Find versus Quick Find versus Quick union at fixed sized "+SIZE+" for varying numbers of Unions");
        
        
        Plotter<Integer, Double> pltWF = new Plotter<>("uf/WUF_"+SIZE+".png", "Unions", "Time (ms)", plt.getType(),"Weighted Union Find @ fixed "+SIZE+" nodes");
        Double[] times = runWF(SIZE, unions, pltWF);
        plt.add(unions, times, "WF");

        Plotter<Integer, Double> pltQF = new Plotter<>("uf/QF_"+SIZE+".png", "Unions", "Time (ms)", plt.getType(),"Quick Find @ fixed "+SIZE+" nodes");
        times = runQF(SIZE, unions, pltQF);
        plt.add(unions, times, "QF");

        Plotter<Integer, Double> pltQU = new Plotter<>("uf/QU_"+SIZE+".png", "Unions", "Time (ms)", plt.getType(),"Quick Union @ fixed "+SIZE+" nodes");
        times = runQU(SIZE,unions, pltQU);
        plt.add(unions, times, "QU");

        plt.plot();
    }


    public void runWFvQF(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {
        this.START = START;
        this.STEPS = STEPS;
        this.SIZE = SIZE;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Weighted Union Find versus Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        
        Plotter<Integer, Double> pltWF = new Plotter<>("uf/WUF_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.LINEAR,"Weighted Union Find @ fixed "+SIZE+" nodes");
        Double[] times = runWF(SIZE, unions, pltWF);
        plt.add(unions, times, "WF");

        Plotter<Integer, Double> pltQF = new Plotter<>("uf/QF_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.NONE,"Quick Find @ fixed "+SIZE+" nodes");
        times = runQF(SIZE, unions, pltQF);
        plt.add(unions, times, "QF");

        plt.plot();
    }

    public void runQUvQF(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {
        this.START = START;
        this.STEPS = STEPS;
        this.SIZE = SIZE;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Weighted Union Find versus Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        
        Plotter<Integer, Double> pltQU = new Plotter<>("uf/QU_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.NONE,"Quick Union @ fixed "+SIZE+" nodes");
        Double[] times = runQU(SIZE, unions, pltQU);
        plt.add(unions, times, "QU");

        Plotter<Integer, Double> pltQF = new Plotter<>("uf/QF_"+SIZE+".png", "Unions", "Time (ms)", Plotter.Type.NONE,"Quick Find @ fixed "+SIZE+" nodes");
        times = runQF(SIZE, unions, pltQF);
        plt.add(unions, times, "QF");

        plt.plot();
    }

    public void runQUvWF(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {
        this.START = START;
        this.STEPS = STEPS;
        this.SIZE = SIZE;

        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        System.out.println("\nGraphing Weighted Union Find versus Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        
        Plotter<Integer, Double> pltQU = new Plotter<>("uf/QU_"+SIZE+".png", "Unions", "Time (ms)", plt.getType(),"Quick Union @ fixed "+SIZE+" nodes");
        Double[] times = runQU(SIZE, unions, pltQU);
        plt.add(unions, times, "QU");

        Plotter<Integer, Double> pltWF = new Plotter<>("uf/WUF_"+SIZE+".png", "Unions", "Time (ms)", plt.getType(),"Weighted Union Find @ fixed "+SIZE+" nodes");
        times = runWF(SIZE, unions,pltWF);
        plt.add(unions, times, "WF");

        plt.plot();
    }

    private Double[] runWF(int SIZE, Integer[] unions, Plotter<Integer,Double> plt) {

        System.out.println("\nGraphing Weighted Union Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        WeightedUnionFind wuf = new WeightedUnionFind(SIZE);
        Double[] times = getUfTimes(unions, wuf);
        plt.add(unions, times, wuf.name);
        wuf = null;

        plt.plot();

        return times;
    }

    private Double[] runQF(int SIZE, Integer[] unions, Plotter<Integer,Double> plt) {

        System.out.println("\nGraphing Quick Find at fixed sized "+SIZE+" for varying numbers of Unions");
        
        QuickFind qf = new QuickFind(SIZE);
        Double[] times = getUfTimes(unions, qf);
        plt.add(unions, times, qf.name);
        qf = null;

        plt.plot();

        return times;
    }

    public Double[] runQUSubplot(int SIZE, int STEPS, int START, Plotter<Integer,Double> plt) {
        this.START = START;
        this.STEPS = STEPS;
        this.SIZE = SIZE;
        
        int arraySize = (SIZE - START) / STEPS; // Calculate the size of the array, rounding down
        Integer[] unions = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++) {
            unions[i] = START + i * STEPS;
        }

        Double[] times = runQU(SIZE, unions, plt);
        return times;   
    }

    private Double[] runQU(int SIZE, Integer[] unions, Plotter<Integer,Double> plt) {

        System.out.println("\nGraphing Quick Union at fixed sized "+SIZE+" for varying numbers of Unions");
        
        QuickUnion qu = new QuickUnion(SIZE);

        for(Integer u: unions) System.out.println((u));
        
        Double[] times = getUfTimes(unions, qu);
        
        plt.add(unions, times, qu.name);
        qu = null;

        plt.plot();

        return times;
    }





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