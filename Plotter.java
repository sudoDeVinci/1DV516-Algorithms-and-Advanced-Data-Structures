import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Plotter {
    public static void plot(String graphPath, int[] x, float[] y) {
        String keyword = "python";
        String scriptPath = "scripts/plotter.py";

        String [] command = new String[]{keyword, scriptPath, graphPath, Arrays.toString(x), Arrays.toString(y)};
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
        String graphPath = "graphs/path.png";
    }
}
