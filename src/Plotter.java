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
    
    private String graphPath = "graphs/";
    private String x_label = "_";
    private String y_label = "_";
    private String title = "_";

    private final String KEYWORD = "python";
    private final String SCRIPT_PATH = "src/scripts/plotter.py";

    /**
     * Overloaded constructors so the labels and title are optional.
     * Path is mandatory.
     * @param path
     */
    public Plotter(String path){
        this.graphPath += path;
    }

    /**
     * x and y labels additions
     * @param path
     * @param x_label
     * @param y_label
     */
    public Plotter(String path, String x_label, String y_label) {
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
    }

    /**
     * x & y labels, and title additions.
     * @param path
     * @param x_label
     * @param y_label
     * @param title
     */
    public Plotter(String path, String x_label, String y_label, String title) {
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
        this.title = title;
    }

    /**
     * Setters so the same Plotter can be reused instead of needing mutliple instances.
     * Changing attributes seems better than re-allocation. 
     */

    /**
     * change the x label.
     * @param xLabel
     */
    public void setXLabel(String xLabel) {
        this.x_label = xLabel;
    }

    /**
     * change y label.
     * @param yLabel
     */
    public void setYLabel(String yLabel) {
        this.y_label = yLabel;
    }

    /**
     * Change the title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Change the graph path
     * @param path
     */
    public void setGraphPath(String path) {
        this.graphPath = path;
    }

    /**
     * Plotter to take in generic arrays of numerical data.
     * These arrays cannot  be primitive types, but can be things such as Integer[] or Float[].
     * @param x
     * @param y
     */
    public <T,R> void plot(T[] x, R[] y) {
        String[] command;

        /**
         * Assemble command to be run.
         */
        command = new String[]{KEYWORD, SCRIPT_PATH, graphPath, Arrays.toString(x), Arrays.toString(y), x_label, y_label, title};
        // System.out.println(Arrays.toString(command));
        try {
            Process p = new ProcessBuilder(command).start();

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
    
                reader.close();
    
                int exitCode = p.waitFor();
                System.out.println("Python script exited with code: " + exitCode);
            }
        
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Waiting for process interrupted: " + e.getMessage());
        }

    }

}