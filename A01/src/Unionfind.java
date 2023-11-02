package src;

import src.race.qfFixedRace;

public class Unionfind {
     public static void main(String[] args) {

        Plotter<Integer, Double> plt;
        qfFixedRace race;
        int SIZE;
        /**
         * graph good yes.
         */
        SIZE = 5000;
        plt = new Plotter<>("uf/WUFvsQFvsQU_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.SCATTER, "Weighted UnionFind vs QuickFind vs Quick Union @ fixed "+SIZE+" nodes");
        race = new qfFixedRace();
        race.runAll(SIZE, 100, 500, plt);

        SIZE = 10_000;
        plt = new Plotter<>("uf/WUFvsQFvsQU_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.SCATTER, "Weighted UnionFind vs QuickFind vs Quick Union @ fixed "+SIZE+" nodes");
        race = new qfFixedRace();
        race.runAll(SIZE, 100, 1000, plt);

        SIZE = 20_000;
        plt = new Plotter<>("uf/WUFvsQFvsQU_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.SCATTER, "Weighted UnionFind vs QuickFind vs Quick Union @ fixed "+SIZE+" nodes");
        race = new qfFixedRace();
        race.runAll(SIZE, 200, 2000, plt);



    }
}
