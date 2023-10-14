package src;

import src.race.qfFixedRace;


public class App {
    public static void main(String[] args) {
    
    
        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQFvsQU_10_000_max.png", "Unions", "Time (ns)", Plotter.Type.SCATTER,"Weighted UnionFind vs QuickFind vs Quick Union  @ fixed 10_000 nodes");
        qfFixedRace race = new qfFixedRace();
        race.runAll(10000, 100, 1000, plt);
        race.runAll(20000, 200, 1000, plt);
        //race.run(100000, 10000, 10000);
    }
}
