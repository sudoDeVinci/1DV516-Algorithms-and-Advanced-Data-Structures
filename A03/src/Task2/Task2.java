package Task2;

import Plot.Plot;
import Plot.Plotter;
import Task1.HashTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task2 {
    public static String getPlate() {
        Random rand = new Random();
        StringBuilder plate = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char randomChar = (char) (rand.nextInt(26)+'a');
            plate.append(randomChar);
        }
        plate.append(rand.nextInt(100, 1000));
        return plate.toString();
    }

    public static void main(String[] args) {
        Random rand = new Random();

        int STEPS = 15;
        int TableSize = 50;
        int numVehicles = 50;
        int stepRatio = 2;

        Integer[] buckets = new Integer[STEPS];
        Double[] collisions_direct = new Double[STEPS];
        Double[] collisions_percent = new Double[STEPS];


        for (int i = 0; i < STEPS; i++) {
            HashTable<Vehicle, Integer> vehicleMap = new HashTable<>(TableSize); 
            List<Vehicle> vehicles = new ArrayList<>();
            int unique = 0;

            // Generate a bunch of random vehcles then add to the Table.
            for (int j = 0; j < numVehicles; j++) {
                Vehicle vehicle = new Vehicle(
                        getPlate(),
                        rand.nextInt(1900, 2024),
                        Vehicle.Colour.values()[rand.nextInt(Vehicle.Colour.values().length)],
                        Make.valueOfLabel(rand.nextInt(1, 13))
                );
    
                boolean added = vehicles.add(vehicle);
                if (added){
                    vehicleMap.insert(vehicle, j);
                    unique++;
                }
            }

            int collisions = vehicleMap.conflicts();
            double loadFactor = vehicleMap.loadFactor();
            int duplicates = numVehicles - unique;
            System.out.printf(
                    "Vehicles: %d \t Buckets: %d \t Collisions: %d, \t Load Factor: %.2f, \t Duplicates: %d%n",
                    numVehicles, TableSize, collisions, loadFactor, duplicates
            );

            buckets[i] = TableSize;
            collisions_direct[i] = (double) collisions;
            collisions_percent[i] = (double) collisions / numVehicles;

            TableSize *= stepRatio;
            numVehicles *= stepRatio;
        }

        Plotter<Integer, Double> plotter = new Plotter<>("collisions_direct");
        plotter.setTitle("Direct Collision Rate");
        plotter.setXLabel("Number of Buckets");
        plotter.setYLabel("Number of collisions");
        plotter.setFontSize(40);
        
        Plot<Integer, Double> cols = new Plot<>("Collisions", Plot.Type.LINE, buckets, collisions_direct);
        cols.setSize(10);
        plotter.add(cols);
        plotter.plot();


        plotter = new Plotter<>("collisions_percent");
        plotter.setTitle("Collision Percentage Rate");
        plotter.setXLabel("Number of Buckets");
        plotter.setYLabel("Percentage of collisions");
        plotter.setFontSize(40);

        Plot<Integer, Double> cols_percent = new Plot<>("Collisions (%)", Plot.Type.LINE, buckets, collisions_percent);
        cols_percent.setSize(10);
        plotter.add(cols_percent);
        plotter.plot();
    }
}
