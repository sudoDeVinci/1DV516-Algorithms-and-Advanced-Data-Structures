package src;
import java.util.Arrays;
import java.util.Random;

/**
 * A Utility class made for statistical functions and useful static methods.
 */
public class Util {
    /**
     * This finds the quartile specified in a list of some number type.
     * The type must be an object, not a primitive.
     * @param <T> Array type, eg: Float, Integer, etc. 
     * @param data
     * @param percentile
     * @return
     */
    public static <T> T Quartile(T[] data, int percentile) {
        int index = (percentile * data.length + 99) / 100 - 1;
        return data[index];
    }

    /**
     * This finds the quartile specified in a list of floats.
     * @param data
     * @param percentile
     * @return
     */
    public static double Quartile(double[] data, int percentile) {
        int index = (percentile * data.length + 99) / 100 - 1;
        return data[index];
    }

    /**
     * Find the IQR, remove outliers, then find the average.
     * @param samples
     * @return
     */
    public static double sampleMean(double[] samples) {
        Arrays.sort(samples);
        double q1 = Quartile(samples, 25);
        double q3 = Quartile(samples, 75);
        double iqr = q3 - q1;

        double ub =  q3 + 1.5*iqr;
        double lb =  q1 - 1.5*iqr;

        int count = 0;
        double sum = 0;
    
        for (int i = 0; i < samples.length; i++) {
            double value = samples[i];
            if (value >= lb && value <= ub) {
                sum += value;
                count++;
            }
        }

        /**
         * Round the answer to 4 decimal places arbitrarily.
         
        double avg = sum / count;
        BigDecimal bd = new BigDecimal(avg);
        bd = bd.round(new MathContext(10));
        double rounded_avg = bd.doubleValue();
        */

        return count > 0 ? sum / count : 0;
    }

    /**
     * Generate an N-long array of bounded x-y coords.
     * @param N
     * @param bound
     * @return
     */
    public static Integer[][] genXYPairs(int N, int bound) {
        if (N < 0 || bound < 0) {
            throw new IllegalArgumentException("N and bound must be non-negative");
        }
    
        Random rand = new Random(42); // Seed for reproducibility
        Integer[][] pairs = new Integer[N][2];
    
        for (int i = 0; i < N; i++) {
            int xCoordinate = rand.nextInt(bound);
            int yCoordinate = rand.nextInt(bound);
    
            pairs[i][0] = xCoordinate;
            pairs[i][1] = yCoordinate;
        }
    
        return pairs;
    }

    public static Integer[] genIntArray(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("N and bound must be non-negative");
        }
    
        Random rand = new Random(42); // Seed for reproducibility
        Integer[] arr = new Integer[N];
        for (int i = 0; i < N; i++) {
            int x = rand.nextInt();
    
            arr[i] = x;
        }
    
        return arr;
    }
}
