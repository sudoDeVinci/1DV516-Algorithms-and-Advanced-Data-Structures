package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class calls a matplotlib script and plots a set of two arrays passed in.
 * It also takes in and can change the x label, y label and title - (ANALYSIS).
 */
public class Plotter<T, R> implements Serializable{

    public static enum Type {
        LINEAR("Linear"),
        EXPONENTIAL("Exponential"),
        SCATTER("Scatter"),
        HISTOGRAM("Histogram"),
        LOGARITHMIC("Logarithmic"),
        LINE("Line"),
        NONE("None");

        private final String type;

        private Type(String type) {
            this.type = type;
        }

        public String toString() {
            return type;
        }
    }
    
    private String x_label = "_";
    private String y_label = "_";
    private String title = "_";
    private List<Type> types = new ArrayList<>();
    private List<T[]> x= new ArrayList<>();
    private List<R[]> y= new ArrayList<>();
    private List<String> labels = new ArrayList<>();

    private final String BASE_DIR = "src";
    private final String KEYWORD = "python";
    private final String SCRIPT_PATH = BASE_DIR+"/scripts/pyplot.py";
    private String graphPath = BASE_DIR + "/graphs/";


    /**
     * Load a plotter from an already serialized object.
     */
    @SuppressWarnings("unchecked")
    public static Plotter<Integer, Double> LoadPlotter(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Plotter plotter = (Plotter) in.readObject();
            System.out.println("Plotter state loaded from " + fileName);
            return plotter;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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
     * Plot type addition.
     * @param path
     * @param x_label
     * @param y_label
     * @param type
     */
    public Plotter(String path, String x_label, String y_label, Plotter.Type type) {
        this.types.add(type);
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
    }


    /**
     * Plot type addition.
     * @param path
     * @param x_label
     * @param y_label
     * @param type
     */
    public Plotter(String path, String x_label, String y_label, Plotter.Type[] type) {
        for(Plotter.Type tp: type) {
            types.add(tp);
        }
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
    }

    /**
     * x & y labels, and title additions.
     * @param path
     * @param x_label
     * @param y_label
     * @param type
     * @param title
     */
    public Plotter(String path, String x_label, String y_label, Plotter.Type type, String title) {
        this.types.add(type);
        this.graphPath += path;
        this.x_label = x_label;
        this.y_label = y_label;
        this.title = title;
    }

    /**
     * x & y labels, and title additions.
     * @param path
     * @param x_label
     * @param y_label
     * @param type
     * @param title
     */
    public Plotter(String path, String x_label, String y_label, Plotter.Type[] type, String title) {
        for(Plotter.Type tp: type) {
            types.add(tp);
        }
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
        this.graphPath = BASE_DIR + path;
    }

    /**
     * Change the plot type.
     * @param type
     */
    public void setType(Type type) {
        this.types.add(type);
    }

    public Plotter.Type getType() {
        return this.types.get(types.size()-1);
    }

    /**
     * Change the points to plot.
     * @param x_coords
     * @param y_coords
     */
    public void add(T[] x_coords, R[] y_coords) {
        if(types.isEmpty()) throw new IllegalStateException("Cannot plot without specifiying Plot type.");
        x.add(x_coords);
        y.add(y_coords);
        if (x.size() > types.size()) types.add(types.get(types.size()-1));
        labels.add("None");
    }


    /**
     * Change the points to plot but specify plot type.
     * @param x_coords
     * @param y_coords
     * @param t
     */
    public void add(T[] x_coords, R[] y_coords, Type t) {
        types.add(t);
        add(x_coords, y_coords);
    }

    /**
     * Change the points to plot but specify label
     * @param x_coords
     * @param y_coords
     * @param label
     */
    public void add(T[] x_coords, R[] y_coords, String label) {
        add(x_coords, y_coords);
        labels.set(labels.size()-1, label);
    }


    /**
     * Change the points to plot but specify label and type
     * @param x_coords
     * @param y_coords
     * @param label
     */
    public void add(T[] x_coords, R[] y_coords, Type t, String label) {
        add(x_coords, y_coords, t);
        labels.set(labels.size()-1, label);
    }

    /**
     * Plotter to take in generic arrays of numerical data.
     * These arrays cannot  be primitive types, but can be things such as Integer[] or Float[].
     * @param x
     * @param y
     */
    public void plot() {

        String[] command;
        // Convert the list of enums to a list of strings
        List<String> typeList = new ArrayList<>();
        for (Type type : this.types) typeList.add(type.toString());

        List<String> labelList = new ArrayList<>();
        for(String l: labels) labelList.add(l);

        /*
        List<String> analysisList = new ArrayList<>();
        for(Boolean a: this.analyze) analysisList.add('"'+a.toString()+'"');
        */

        List<String> xList = new ArrayList<>();
        List<String> yList = new ArrayList<>();
        for (int i = 0; i<x.size(); i++) {
            xList.add(i, Arrays.toString(x.get(i)).replaceAll("\\s+",""));
            yList.add(i, Arrays.toString(y.get(i)).replaceAll("\\s+",""));
        }

        

        /**
         * Assemble command to be run.
         */
        command = new String[]{KEYWORD, SCRIPT_PATH, graphPath, xList.toString(), yList.toString(), x_label.replaceAll("\\s+","_"), y_label.replaceAll("\\s+","_"), title.replaceAll("\\s+","_"), typeList.toString(), labelList.toString()};
        System.out.println(Arrays.toString(command));
        try {
            ProcessBuilder pBuilder = new ProcessBuilder(command);
            pBuilder.redirectErrorStream(true);
            Process p = pBuilder.start();

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))){
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
    
                reader.close();
    
                int exitCode = p.waitFor();
                System.out.println("Python script exited with code: " + exitCode);
            }
        
        save();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Waiting for process interrupted: " + e.getMessage());
        }

    }

    private String rmFileExtension()  {
        File file  = new File(this.graphPath);
        String neutralPath = file.getPath();
        int dot = neutralPath.lastIndexOf('.');
        if(dot != -1) {
            return neutralPath.substring(0, dot);
        } 
        return neutralPath;
    }

    public void save() {
        
        try {
            String saveName = rmFileExtension();
            String serializedName = saveName + "_plotter.ser";
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializedName))) {
                out.writeObject(this);
                System.out.println("Plotter state saved to " + serializedName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}