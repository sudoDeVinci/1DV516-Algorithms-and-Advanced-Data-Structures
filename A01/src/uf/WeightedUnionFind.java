package src.uf;

/**
 * Weighted Union find implementation with path compression.
 */
public class WeightedUnionFind extends UnionFind{
    private int[] S;
    private int[] heights;

    public WeightedUnionFind(int N) {
        reset(N);
    }

    /**
     * Return whether two nodes are connected (have the same root.)
     * @param a Node a
     * @param b Node b
     * @return
     */
    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    /**
     * Find the root of a given element.
     * @param a Node to find root of
     * @return
     */
    private int find(int a) {
        while (a != S[a]) {
            S[a] = S[S[a]];
            a = S[a];
        }
        return a;
    }

    /**
     * Connect to given nodes a and b.
     * Graft the smaller tree ont the larger by placing the root of the larger into
     * the place of the previous root of the smaller.
     * @param a Node a
     * @param b Node b
     */
    public void union(int a, int b) {

        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {return;}

        if (heights[rootA] < heights[rootB]) {
            S[rootA] = rootB;
            heights[b] += heights[a];
        } else {
            S[rootB] = rootA;
            heights[rootA] += heights[rootB];
        }
    }

    /**
     * Reset the current object but resize the current set to S = int[N].
     * @param N
     */
    @Override
    public void reset(int N) {
        this.S = new int[N];
        this.heights = new int[N];
        for (int i = 0; i < N; i++) {
            this.S[i] = i;
            this.heights[i] = 1;
        }
        
    }

    /**
     * Reset the object but keep the size of the current Set.
     */
    public void reset() {
        for (int i = 0; i < this.S.length; i++) {
            this.S[i] = i;
            this.heights[i] = 1;
        }
    }

}
