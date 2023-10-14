package src;

import src.race.kSumRace;
import src.race.qfFixedRace;

public class App {
    public static void main(String[] args) {
        qfFixedRace race = new qfFixedRace();
        kSumRace tsrace = new kSumRace();
    
        
        /**
        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQFvsQU_10_000_max.png", "Unions", "Time (ns)", Plotter.Type.SCATTER,"Weighted UnionFind vs QuickFind vs Quick Union @ fixed 10_000 nodes");
        race = new qfFixedRace();
        race.runAll(10000, 100, 1000, plt);
        plt = new Plotter<>("uf/WUFvsQFvsQU_20_000_max.png", "Unions", "Time (ns)", Plotter.Type.SCATTER,"Weighted UnionFind vs QuickFind vs Quick Union @ fixed 20_000 nodes");
        race.runAll(20000, 200, 1000, plt);
        plt = new Plotter<>("uf/WUFvsQFvsQU_30_000_max.png", "Unions", "Time (ns)", Plotter.Type.SCATTER,"Weighted UnionFind vs QuickFind vs Quick Union @ fixed 30_000 nodes");
        race.runAll(30000, 300, 1000, plt);

        plt = new Plotter<>("uf/WUFvsQF_10_000_max.png", "Unions", "Time (ns)", Plotter.Type.LINEAR,"Weighted UnionFind vs Quick Find  @ fixed 10_000 nodes");
        race.runWFvQF(10000, 100, 1000, plt);
        */

        Plotter<Integer, Double> plt = new Plotter<>("ksum/BruteForcevsCached_100.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Brute-Force versus Cached 3-Sum @ 100 Max Elements.");
        tsrace.run(200, 5, 5, plt);
        plt = new Plotter<>("ksum/BruteForcevsCached_500.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Brute-Force versus Cached 3-Sum @ 500 Max Elements.");
        tsrace.run(500, 10, 100, plt);
    }
}
