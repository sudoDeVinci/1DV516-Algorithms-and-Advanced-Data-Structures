package src.uf;
import java.util.Arrays;

/**
 * Weighted Union find implementation with path compression.
 */
public class WeightedUnionFind extends UnionFind{
    private int size;
    private int[] S;
    private int[] heights;

    public WeightedUnionFind(int N) {
        super(N);
        this.heights = new int[N];
        Arrays.fill(heights, 1);
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
        } else if (heights[rootA] > heights[rootB]) {
            S[rootB] = rootA;
        } else {
            S[rootB] = rootA;
            heights[rootA]++;
        }
    }

    /**
     * Reset the current object but resize the current set to S = int[N].
     * @param N
     */
    @Override
    public void reset(int N) {
        this.S = new int[N];
        this.size = N;
        for (int i = 0; i < N; i++) {
            this.S[i] = i;
        }

        this.heights = new int[N];
        Arrays.fill(this.heights, 1);
    }

    /**
     * Reset the object but keep the size of the current Set.
     */
    public void reset() {
        for (int i = 0; i < this.size; i++) {
            this.S[i] = i;
        }
         Arrays.fill(this.heights, 1);
    }

}
