package src;
import java.lang.Math;

import src.race.qfFixedRace;

public class App {
    public static void main(String[] args) {
        qfFixedRace race = new qfFixedRace();


        Plotter<Integer, Double> plt = new Plotter<>("uf/WUFvsQF_100_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                            "Weighted UnionFind vs Quick Find @ fixed 100_000 nodes");
        race.runWFvQF(100_000, 10_000, 10_000, plt);

        /**
         * 
         * Integer[] x = new Integer[]{0,1,2,3,4,5,6,7,8,9,10};
        Double[] y1 = new Double[x.length];
        Double[] y2 = new Double[x.length];
        Double[] y3 = new Double[x.length];
        Double[] y4 = new Double[x.length];
        for(Integer xx: x) y1[xx] = Math.pow(xx,2);
        for(Integer xx: x) y2[xx] = 20.0*xx;
        for(Integer xx: x) y3[xx] = 1.0;
        for(Integer xx: x) y4[xx] = Math.pow(xx,2)+12;  

        plt = new Plotter<>("test/x2.png","X TEST","Y TEST");
        plt.add(x, y1, Plotter.Type.LINE, "Line Plot");
        plt.add(x, y2, Plotter.Type.SCATTER, "Scatter Plot");
        plt.add(x, y3, Plotter.Type.LINEAR, "Approximated Linear Plot");
        plt.add(x, y4, Plotter.Type.EXPONENTIAL, "Approximated Exponential Plot");
        plt.save();

        Plotter<Integer, Double> pltNew = Plotter.LoadPlotter("src/graphs/test/x2_plotter.ser");
        pltNew.plot();
         * 
         * 
         * 
        plt = new Plotter<>("uf/WUFvsQF_5_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                           "Weighted UnionFind vs Quick Find @ fixed 5_000 nodes");

        race.runWFvQF(5000, 100, 500, plt);
        

        plt = new Plotter<>("uf/WUFvsQF_10_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                           "Weighted UnionFind vs Quick Find @ fixed 10_000 nodes");

        race.runWFvQF(10000, 100, 1000, plt);



        plt = new Plotter<>("uf/WUFvsQF_20_000_max.png",
                         "Unions", "Time (ms)",
                                 Plotter.Type.LINEAR,
                            "Weighted UnionFind vs Quick Find @ fixed 20_000 nodes");
        race.runWFvQF(20000, 200, 2000, plt);
        */

        
        /**
        
        plt = new Plotter<>("test/x2.png","X TEST","Y TEST", Plotter.Type.LINE,"TEST PLOT");
        plt.add(new Integer[]{1,2,3,4,5,6,7,8,9,10}, new Double[]{2.0,8.0,18.0,32.0,50.0,72.0,98.0,128.0,162.0,200.0}, "Test Plot booyah!");
        plt.add(new Integer[]{1,2,3,4,5,6,7,8,9,10}, new Double[]{-3.0,3.0,13.0,27.0,45.0,67.0,93.0,123.0,157.0,195.0}, "test plot -5 lololo");
        plt.save();
        
        Plotter<Integer, Double> pltNew = Plotter.LoadPlotter("src/graphs/test/x2_plotter.ser");
        pltNew.plot();
        
         python src/scripts/pyplot.py src/graphs/test/x2.png [[1,2,3,4,5,6,7,8,9,10]] [[2.0,8.0,18.0,32.0,50.0,72.0,98.0,128.0,162.0,200.0]] X TEST Y TEST TEST PLOT ["Line"] ["test Plot label yeah"] 
         
         */
    }
}