package src;

//import src.race.kSumRace;
import src.race.qfFixedRace;

public class App {
    public static void main(String[] args) {
        Plotter<Integer, Double> plt;
        qfFixedRace race = new qfFixedRace();
        //kSumRace tsrace = new kSumRace();
    
        
        /**
        Plotter<Integer, Double> plt = new Plotter<>("ksum/BruteForcevsCached_100.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Brute-Force versus Cached 3-Sum @ 100 Max Elements.");
        tsrace.run(200, 5, 5, plt);
        plt = new Plotter<>("ksum/BruteForcevsCached_500.png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL,"Brute-Force versus Cached 3-Sum @ 500 Max Elements.");
        tsrace.run(500, 10, 100, plt);

        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQFvsQU_10_000_max.png", "Unions", "Time (ms)", Plotter.Type.SCATTER,"Weighted UnionFind vs QuickFind vs Quick Union @ fixed 10_000 nodes");
        race = new qfFixedRace();
        race.runAll(10000, 100, 1000, plt);




        plt = new Plotter<>("uf/WUFvsQFvsQU_10_000_max.png", "Unions", "Time (ms)", Plotter.Type.SCATTER,"Weighted UnionFind vs QuickFind vs Quick Union @ fixed 10_000 nodes");
        race.runAll(10000, 100, 1000, plt);

        plt = new Plotter<>("uf/WUFvsQFvsQU_20_000_max.png",
                             "Unions", "Time (ms)",
                            Plotter.Type.SCATTER, 
                            "Weighted UnionFind vs QuickFind vs Quick Union @ fixed 20_000 nodes");
        race.runAll(20000, 200, 1000, plt);
        


        plt = new Plotter<>("uf/WUFvsQF_10_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                           "Weighted UnionFind vs Quick Find  @ fixed 10_000 nodes");

        race.runWFvQF(10000, 100, 1000, plt);



        plt = new Plotter<>("uf/WUFvsQF_20_000_max.png", "Unions", "Time (ms)", Plotter.Type.LINEAR,"Weighted UnionFind vs Quick Find  @ fixed 20_000 nodes");
        race.runWFvQF(20000, 200, 2000, plt);

        plt = new Plotter<>("uf/WUFvsQFvsQU_5_000_max.png",
                             "Unions", "Time (ms)",
                            Plotter.Type.SCATTER, 
                            "Weighted UnionFind vs QuickFind vs Quick Union @ fixed 5_000 nodes");
        race.runAll(5000, 100, 500, plt);
        


        plt = new Plotter<>("uf/WUFvsQF_5_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                           "Weighted UnionFind vs Quick Find  @ fixed 5_000 nodes");

        race.runWFvQF(5000, 100, 500, plt);
        

        plt = new Plotter<>("uf/WUFvsQFvsQU_5000_max.png",
                            "Unions",
                            "Time (ms)",
                            Plotter.Type.SCATTER,
                            "Weighted UnionFind vs QuickFind vs Quick Union @ fixed 5,000 nodes");
        race.runAll(5000, 100, 200, plt);

        plt = new Plotter<>("uf/WUFvsQFvsQU_10_000_max.png",
                            "Unions",
                            "Time (ms)",
                            Plotter.Type.SCATTER,
                            "Weighted UnionFind vs QuickFind vs Quick Union @ fixed 10,000 nodes");
        race.runAll(10000, 100, 1000, plt);

        plt = new Plotter<>("uf/WUFvsQFvsQU_20_000_max.png",
                             "Unions", "Time (ms)",
                            Plotter.Type.SCATTER, 
                            "Weighted UnionFind vs QuickFind vs Quick Union @ fixed 20,000 nodes");
        race.runAll(20000, 200, 1000, plt);

        
        plt = new Plotter<>("uf/WUFvsQF_5_000_max.png",
                                                        "Unions",
                                                        "Time (ms)",
                                                        Plotter.Type.SCATTER,
                                                        "Weighted Union @ fixed 5,000 nodes");
        race.runWFvQF(5000, 100, 500, plt);
        */

        plt = new Plotter<>("test/x2.png","X TEST","Y TEST", Plotter.Type.LINE,"TEST PLOT");
        plt.add(new Integer[]{1,2,3,4,5,6,7,8,9,10}, new Double[]{2.0,8.0,18.0,32.0,50.0,72.0,98.0,128.0,162.0,200.0}, "Test Plot booyah!");
        plt.add(new Integer[]{1,2,3,4,5,6,7,8,9,10}, new Double[]{-3.0,3.0,13.0,27.0,45.0,67.0,93.0,123.0,157.0,195.0}, "test plot -5 lololo");
        plt.save();
        
        Plotter<Integer, Double> pltNew = Plotter.LoadPlotter("src/graphs/test/x2_plotter.ser");
        pltNew.plot();
        /**
         python src/scripts/pyplot.py src/graphs/test/x2.png [[1,2,3,4,5,6,7,8,9,10]] [[2.0,8.0,18.0,32.0,50.0,72.0,98.0,128.0,162.0,200.0]] X TEST Y TEST TEST PLOT ["Line"] ["test Plot label yeah"] 
         
         */
    }
}