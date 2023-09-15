package src.uf;
import java.util.Arrays;

/**
 * Weighted Quick Union Find implementation.
 */
public class WeightedUnion extends UnionFind{
    private int sz;
    // array to hold nodes
    private int[] S;
    // corresponding array of tree sizes. initially all 1.
    private int[] size;

    public WeightedUnion(int N) {
        super(N);
        // Init the size array size and all 1s.
        this.size = new int[N];
        Arrays.fill(size, 1);
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
     * Return whether two nodes are connected (have the same root.)
     * @param a Node a
     * @param b Node b
     * @return
     */
    public boolean connected(int a, int b) {
        return find(a) == find(b);
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
        
        if (size[rootA] < size[rootB]) {
            S[rootA] = rootB;
            size[rootB] += size[rootA];
            return;
        }

        S[rootB] = rootA;
        size[rootA] += size[rootB];
    }

    @Override
    public void reset(int N) {
        // Each element points to itself as root initially.
        for (int i = 0; i < N; i++) {
            this.S[i] = i;
        }
        this.sz = N;
        // Init the size array size and all 1s.
        this.size = new int[N];
        Arrays.fill(size, 1);
    }


}