package src.uf;

/**
 * Base class for Union find implementations.
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
    
}