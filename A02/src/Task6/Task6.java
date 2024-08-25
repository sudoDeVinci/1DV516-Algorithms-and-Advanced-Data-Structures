package src.Task6;

import src.plot.Plot;
import src.plot.Plotter;

public class Task6 {
    public static void main(String[] args) {
        int SIZE = 1_000;
        Double[][] stats;

        Integer[] x = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) x[i] = i;
        stats = TreeComp.runAdd(SIZE);

        Plotter<Integer, Double> graph_add = new Plotter<>("Tree_add_comp");
        graph_add.setTitle("Comparison of Tree Addition Operation Times");
        graph_add.setXLabel("Tree size");
        graph_add.setYLabel("Time (micro seconds)");
        graph_add.setFontSize(20);

        Plot<Integer, Double> p1 = new Plot<>("AVL Tree", Plot.Type.SCATTER, x, stats[0]);
        p1.setSize(40);

        Plot<Integer, Double> p2 = new Plot<>("BST Tree", Plot.Type.SCATTER, x, stats[1]);
        p2.setSize(40);

        graph_add.add(p1);
        graph_add.add(p2);
        graph_add.plot();


        Plotter<Integer, Double> graph_height = new Plotter<>("Tree_height_insertion_comp");
        graph_height.setTitle("Comparison of Tree heights after Consecutive Inserts");
        graph_height.setXLabel("Tree size");
        graph_height.setYLabel("Height");
        graph_height.setFontSize(20);

        p1 = new Plot<>("AVL Tree", Plot.Type.SCATTER, x, stats[2]); 
        p1.setSize(40);

        p2 = new Plot<>("BST Tree", Plot.Type.SCATTER, x, stats[3]);
        p2.setSize(40);

        graph_height.add(p1);
        graph_height.add(p2);
        graph_height.plot();



        for (int i = 1; i < SIZE; i++) x[SIZE - i] = i;
        stats = TreeComp.runDel(SIZE);

        Plotter<Integer, Double> graph_del = new Plotter<>("Tree_del_comp");
        graph_del.setTitle("Comparison of Tree Deletion Operation Times");
        graph_del.setXLabel("Tree size");
        graph_del.setYLabel("Time (micro seconds)");
        graph_del.setFontSize(20);

        p1 = new Plot<>("AVL Tree", Plot.Type.SCATTER, x, stats[0]);
        p1.setSize(40);

        p2 = new Plot<>("BST Tree", Plot.Type.SCATTER, x, stats[1]);
        p2.setSize(40);

        graph_del.add(p1);
        graph_del.add(p2);
        graph_del.plot();

        System.out.println("Done!");
    }
}
