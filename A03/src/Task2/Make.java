package Task2;

import java.util.HashMap;
import java.util.Map;

public enum Make {
    VOLVO(2),
    AUDI(3),
    BMW(4),
    VW(5),
    FORD(6),
    CORVETTE(7),
    SAAB(8),
    CHEVROLET(9),
    HONDA(10),
    MERCEDES(11),
    LAMBORGHINI(12),
    PORSCHE(13),
    TOYOTA(14);
    public final int label;
    private static final Map<Integer, Make> labels = new HashMap<>();
    Make(int label) {this.label = label;}

    static {
        int count = 1;
        for (Make e: values()) {
            labels.put(count, e);
            count++;
        }
    }

    public static Make valueOfLabel(int label) {return labels.get(label);}
}
