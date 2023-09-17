package src.uf;

/**
 * Base class for Union find implementations.
 * Reset is used somewhat as the constructor so that the same object can
 * be reset and used for multiple runs without reallocation. 
 */
public abstract class UnionFind {
    private int[] S;
    private int size;

    public UnionFind(int N) {
        reset(N);
    }

    /**
     * Whether two nodes are connected.
     * @param a
     * @param b
     * @return
     */
    public abstract boolean connected(int a, int b);

    /**
     * Union of two nodes, a and b.
     * @param a
     * @param b
     */
    public abstract void union(int a, int b);
    
    /**
     * Run a given number of unions. This is used for benchmarks.
     * @param pairs
     */
    public void run_union(Integer[][] pairs) {
        for (Integer[] pair : pairs) {
            union(pair[0], pair[1]);
        }
    }

    /**
     * Run a given number of checks for connections. This is used for benchmarks.
     * @param pairs
     */
    public void run_connected(Integer[][] pairs) {
        for (Integer[] pair : pairs) {
            connected(pair[0], pair[1]);
        }
    }

    /**
     * Reset the object but keep the size of the current Set.
     */
    public void reset(){
        for (int i = 0; i < this.size; i++) this.S[i] = i;
    }

    /**
     * Reset the current object but resize the current set.
     * @param N
     */
    public void reset(int N) {
        this.S = new int[N];
        for (int i = 0; i < N; i++) this.S[i] = i;
        this.size = N;
    }

}