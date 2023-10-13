package src.uf;

/**
 * Simplest Quick Find implementation (no optimizations.)
 */
public class QuickFind extends UnionFind {
    private int[] S;
    private int size;

    public QuickFind (int N) {
        reset(N);
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
        
        if(idA == idB) return;

        for (int index = 0; index < size; index++) {
            if (S[index] == idA) {
                S[index] = idB;
            }
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
        this.size = N;
        this.S = new int[N];
        for (int i = 0; i < N; i++) this.S[i] = i;
        
    }
}
