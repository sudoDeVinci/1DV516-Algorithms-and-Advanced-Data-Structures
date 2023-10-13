package src.uf;

/**
 * Base class for Union find implementations.
 * Reset is used somewhat as the constructor so that the same object can
 * be reset and used for multiple runs without reallocation. 
 */
public abstract class UnionFind {

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
        int length = pairs.length;
        for (int i=0;i<length;i++) {
            union(pairs[i][0], pairs[0][1]);
        }
    }

    /**
     * Reset the object but keep the size of the current Set.
     */
    public abstract void reset();

    /**
     * Reset the current object but resize the current set.
     * @param N
     */
    public abstract void reset(int N);

}