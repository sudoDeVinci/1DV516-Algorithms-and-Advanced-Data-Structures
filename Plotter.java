import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * This class calls a matplotlib script and plots a set of two arrays passed in.
 * It also takes in and can change the x label, y label and title.
 */
public class Plotter {

    String graphPath;
    String x_label = null;
    String y_label = null;
    String title;

    /**
     * Overloaded constructors so the labels and title are optional.
     * Path is mandatory.
     * @param path
     */
    public Plotter(String path){
        this.graphPath = path;
    }
    public Plotter(String path, String x_label, String y_label) {
        this.graphPath = path;
        this.x_label = x_label;
        this.y_label = y_label;
    }
    public Plotter(String path, String x_label, String y_label, String title) {
        this.graphPath = path;
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
        String scriptPath = "scripts/plotter.py";
        String[] command;

        // CReate command depending on the args passed
        if (x_label == null || y_label == null) {
            command = new String[]{keyword, scriptPath, graphPath, Arrays.toString(x), Arrays.toString(y)};
        } else {
            command = new String[]{keyword, scriptPath, graphPath, Arrays.toString(x), Arrays.toString(y), x_label, y_label};
        }
        
        System.out.println(Arrays.toString(command));
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

    public static void main(String[] args) {
        String graphPath1 = "graphs/path1.png";
        Plotter plotter = new Plotter(graphPath1);
        plotter.plot(new Integer[]{2,3,4,5,6}, new Integer[]{12,13,14,15,16});

        String graphPath2 = "graphs/path2.png";
        plotter = new Plotter(graphPath2);
        plotter.setXLabel("New X");
        plotter.setYLabel("New Y");
        plotter.plot(new Integer[]{2,3,4,5,6}, new Integer[]{12,13,14,15,16});
    }
}
