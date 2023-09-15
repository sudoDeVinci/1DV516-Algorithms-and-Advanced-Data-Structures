package src;

import java.util.function.Consumer;

public class Timeit {
    private long start;
    private long stop;
    private Consumer<Object[]> timedMethod;

    public Timeit(Consumer<Object[]> m) {
        this.timedMethod = m;
    }

    public float measureNanos(Object...args) {
        this.start = System.nanoTime();
        timedMethod.accept(args);
        this.stop = System.nanoTime();
        //System.out.printf("Elapsed Time: %dns%n", (stop - start));
        float elapsed = (this.stop - this.start);
        return elapsed;
    }
}
