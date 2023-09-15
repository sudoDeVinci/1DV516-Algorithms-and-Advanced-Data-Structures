package src.uf;

import java.util.Arrays;

/**
 * Quick Find implementation.
 */
public class QuickFind extends UnionFind{
    // array to hold nodes.
    private int[] S;
    // hold ont array size for use later.
    private int sz;

    public QuickFind(int N) {
        super(N);
    }

    /**
     * Return whether two nodes are connected.
     * @param a Node a
     * @param b Node b
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

        for (int index = 0; index < sz; index++) {
            int value = S[index];
            if (value == idA) {
                S[index] = idB;
            }
        }
    }   

    @Override
    public void reset(int N) {
        this.S = new int[N];
        // Each element points to itself as root initially.
        for (int i = 0; i < N; i++) {
            this.S[i] = i;
        }
        this.sz = N;
    }


}