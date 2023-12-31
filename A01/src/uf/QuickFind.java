package src.uf;

/**
 * Simplest Quick Find implementation (no optimizations.)
 */
public class QuickFind extends UnionFind {

    public String name = "QF";
    private int[] S;

    public QuickFind (int N) {
        this.S = new int[N];
        for(int i=0; i<N; i++) this.S[i] = i;
    }

    /**
     * Whether two nodes are connected.
     * @param a
     * @param b
     * @return
     */
    public boolean connected(int a, int b) {
        return S[a] == S[b];
    }

    /**
     * Connect to given nodes a and b.
     * @param a Node a
     * @param b Node b
     */
    public void union(int a, int b) {
        int idA = S[a];
        int idB = S[b];

        for (int index = 0; index < S.length; index++) {
            if (S[index] == idA) {
                S[index] = idB;
            }
        }
    }
}
