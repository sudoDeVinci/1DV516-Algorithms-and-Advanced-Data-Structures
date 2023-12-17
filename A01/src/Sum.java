package src;

import src.race.kSumRace;

public class Sum {
    public static void main(String[] args) {

        int START = 10;
        int STEPS = 5;
        int SIZE = 100;


        Plotter<Integer, Double> plt;
        kSumRace race;

        int arraySize = (SIZE - START) / STEPS;
        Integer[] sizes = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            sizes[i] = START + i * STEPS;
        }

        plt = new Plotter<>("ksum/BruteForcevsCached_"+SIZE+".png", "Length", "Time (ms)", Plotter.Type.EXPONENTIAL, "Brute-Force versus Cached 3-Sum "+SIZE+" Max Elements");
        race = new kSumRace();
        race.run(SIZE, STEPS, START, plt);
        
    }
}