package src;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * This class calls a matplotlib script and plots a set of two arrays passed in.
 * It also takes in and can change the x label, y label and title.
 */
public class Plotter {

    String graphPath = "graphs/";
    String x_label = "Time";
    String y_label = "Elements";
    String title = "_";

    /**
     * Overloaded constructors so the labels and title are optional.
     * Path is mandatory.
     * @param path
     */
    public Plotter(String path){
        this.graphPath += path;
    }
    public Plotter(String path, String x_label, String y_label) {
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
    }
    public Plotter(String path, String x_label, String y_label, String title) {
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
        this.title = title;
    }
    /**
     * Setters so the same PLotter can be reused instead of needing mutliple instacnes.
     */
     public void setXLabel(String xLabel) {
        this.x_label = xLabel;
    }

    public void setYLabel(String yLabel) {
        this.y_label = yLabel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGraphPath(String path) {
        this.graphPath = path;
    }


    /**
     * Plotter to take in generic arrays of numerical data.
     * These arrays cannot  be primitice types, but can be thigns such as Integer[] or Float[].
     * @param <T>
     * @param <R>
     * @param x
     * @param y
     */
    public <T,R> void plot(T[] x, R[] y) {
        String keyword = "python";
        String scriptPath = "src/scripts/plotter.py";
        String[] command;

        // Create command of the args passed
        command = new String[]{keyword, scriptPath, graphPath, Arrays.toString(x), Arrays.toString(y), x_label, y_label, title};
        // .out.println(Arrays.toString(command));
        try{
            // Create and start python process.
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            // Read the output (if any) from the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    } 
}
