package Task2;

import Task1.HashTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task2 {
    public static String getPlate() {
        Random rand = new Random();
        StringBuilder plate = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char randomChar = (char) rand.nextInt('A', 'Z' + 1);
            plate.append(randomChar);
        }
        plate.append(rand.nextInt(100, 1000));
        return plate.toString();
    }

    public static void main(String[] args) {
        Random rand = new Random();

        int TableSize = 100;
        int numVehicles = 50;


        HashTable<Vehicle, Integer> vehicleMap = new HashTable<>(TableSize); 
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < numVehicles; i++) {
            Vehicle vehicle = new Vehicle(
                    getPlate(),
                    rand.nextInt(1950, 2022),
                    Vehicle.Colour.values()[rand.nextInt(Vehicle.Colour.values().length)],
                    "Make " + rand.nextInt(1, 20)
            );

            vehicles.add(vehicle);
            vehicleMap.insert(vehicle, i);
        }
    }
}
