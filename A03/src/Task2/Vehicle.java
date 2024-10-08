package Task2;

import java.math.BigInteger;
import util.Util;

public class Vehicle {

    public enum Colour {
        RED("Red"),
        BLUE("Blue"),
        GREEN("Green"),
        WHITE("White"),
        BLACK("Black"),
        SILVER("Silver"),
        GRAY("Gray"),
        YELLOW("Yellow"),
        ORANGE("Orange"),
        PURPLE("Purple"),
        PINK("Pink"),
        BROWN("Brown"),
        BEIGE("Beige"),
        GOLD("Gold"),
        COPPER("Copper"),
        MAROON("Maroon"),
        NAVY_BLUE("Navy Blue"),
        TURQUOISE("Turquoise"),
        LIME_GREEN("Lime Green"),
        CHARCOAL("Charcoal"),
        MAGENTA("Magenta"),
        INDIGO("Indigo"),
        TEAL("Teal"),
        OLIVE_GREEN("Olive Green"),
        BABY_BLUE("Baby Blue"),
        SAGE_GREEN("Sage Green"),
        CREAM("Cream"),
        CORAL("Coral"),
        MAUVE("Mauve"),
        VIOLET("Violet"),
        AQUA("Aqua"),
        PEACH("Peach"),
        LAVENDER("Lavender");
    
        private final String colorName;
    
        Colour(String colorName) {
            this.colorName = colorName;
        }
    
        public String ColourName() {
            return colorName;
        }
    }
    
    
    private String plate;
    private int year;
    private Colour colour;
    private Make make;

    public Vehicle(String plate, int year, Colour colour, Make make) {
        this.plate = plate;
        this.year = year;
        this.colour = colour;
        this.make = make;
    }

    public String getPlate() {
        return plate;
    }

    public int getYear() {
        return year;
    }

    public Colour getColour() {
        return colour;
    }

    public Make getMake() {
        return make;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plate='" + plate + '\'' +
                ", year=" + year +
                ", colour=" + colour +
                ", make='" + make + '\'' +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return plate.equals(vehicle.plate) &&
                year == vehicle.year &&
                colour == vehicle.colour &&
                make.equals(vehicle.make);
    }

    @Override
    public int hashCode() {
        int yearSuffix;
        if (year < 2000) yearSuffix = year % 1900;
        else yearSuffix = year % 2000;

        BigInteger hashval = BigInteger.ZERO;
        for (char c : plate.toCharArray()) {
            int num = Character.getNumericValue(c);
            hashval = hashval.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(num));
        }

        BigInteger colorMake = BigInteger.valueOf(colour.hashCode()).multiply(BigInteger.valueOf(Util.nextPrime(yearSuffix))).multiply(BigInteger.valueOf(make.label));
        if (colorMake.compareTo(BigInteger.ZERO) < 0) colorMake = colorMake.negate();

        BigInteger longformatted = hashval.multiply(BigInteger.valueOf(Util.nextPrime(yearSuffix))).add(colorMake);
        return longformatted.mod(BigInteger.valueOf(Integer.MAX_VALUE)).intValue();
    }
}
