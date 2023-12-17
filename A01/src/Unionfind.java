package src;

import src.race.qfFixedRace;

public class Unionfind {
     public static void main(String[] args) {

        Plotter<Integer, Double> plt;
        qfFixedRace race;
        int SIZE;
        /**
         * To run a QuickFind test, create a Plotter Object and pass it to the 
         * "race" method of your choice. You are able to test All implementations
         *  against eachother, Quick Find against Weighted Union Find, or Weighted-
         *  Union Find alone.
         */

        SIZE = 5000;
        plt = new Plotter<>("uf/WUFvsQFvsQU_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.SCATTER, "Weighted UnionFind vs QuickFind vs Quick Union @ fixed "+SIZE+" nodes");
        race = new qfFixedRace(SIZE, 100, 500);
        race.runAll(plt);

        SIZE = 10_000;
        plt = new Plotter<>("uf/WUFvsQFvsQU_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.SCATTER, "Weighted UnionFind vs QuickFind vs Quick Union @ fixed "+SIZE+" nodes");
        race = new qfFixedRace(SIZE, 100, 1000);
        race.runAll(plt);

        SIZE = 20_000;
        plt = new Plotter<>("uf/WUFvsQFvsQU_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.SCATTER, "Weighted UnionFind vs QuickFind vs Quick Union @ fixed "+SIZE+" nodes");
        race = new qfFixedRace(SIZE, 200, 2000);
        race.runAll(plt);


        
        /**
         * Below shows the ability to Reload Serialized plotter object to redraw and reformat later.
         * This is so large runs can be tested later without having to re-run them.
         */
        
        SIZE = 50_000;
        Integer[] x = Util.seqIntArray(SIZE, 100,2000);
        Double[] y = Util.log2Arr(0.00035, 0.70, x);

        try {
            plt = Plotter.LoadPlotter("src/graphs/uf/WUF_UB_LOG_50000_plotter.ser");
            plt.setGraphPath("/graphs/uf/WUF_UB_LOG_50000.png");
            plt.add(x, y, "Upper Bound");
            plt.plot();
        } catch (Exception e){
            System.out.println("Could not load previous Plot.");
        }
        
        
        SIZE = 50_000;
        x = Util.seqIntArray(SIZE, 100, 2000);
        y = Util.log2Arr(0.000050, 0.70, x);

        try {
            plt = Plotter.LoadPlotter("src/graphs/uf/WUF_LB_LOG_50000_plotter.ser");
            plt.setGraphPath("/graphs/uf/WUF_LB_LOG_50000.png");
            plt.add(x, y, "Lower Bound");
            plt.plot(); 
        } catch (Exception e){
            System.out.println("Could not load previous Plot.");
        }
               
    }
}
