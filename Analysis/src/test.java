package src;
import java.util.ArrayList;
import java.util.List;
import src.Plotter;

public class test {
    public static void main(String[] args) {

        Plotter<Double,Integer> plt = new Plotter<>("test.png", "Items", "Times", Plotter.Type.LINEAR, "Title");
        plt.add(new Double[]{1.0,2.0,3.0,4.0,5.0}, new Integer[]{2,4,6,8,10}, "2x");
        plt.add(new Double[]{1.0,2.0,3.0,4.0,5.0}, new Integer[]{3,6,9,12,15}, "3x");
        plt.add(new Double[]{1.0,2.0,3.0,4.0,5.0}, new Integer[]{30,60,90,120,150}, "30x");
        plt.add(new Double[]{1.0,2.0,3.0,4.0,5.0}, new Integer[]{1,4,9,16,25}, Plotter.Type.EXPONENTIAL);
        plt.add(new Double[]{1.0,2.0,3.0,4.0,5.0}, new Integer[]{10,40,90,160,250});
        plt.plot();
    }
}
