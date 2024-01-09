package src;

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

    public Vehicle() {

    }
    
}
