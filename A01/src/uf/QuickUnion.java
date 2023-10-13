package src.uf;

public class QuickUnion extends UnionFind{
    private int[] S;

    public QuickUnion(int N) {
        this.S = new int[N];
         for(int i=0; i<N; i++) S[i] = i;
    }

    public int find(int a) {
        while(S[a]!=a) a=S[a];
        return a;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {return;}

        S[rootA] = rootB;
    }

    /**
     * Reset the current object but resize the current set to S = int[N].
     * @param N
     */
    @Override
    public void reset(int N) {
        this.S = new int[N];
        for (int i = 0; i < N; i++) {
            this.S[i] = i;
        }
        
    }

    /**
     * Reset the object but keep the size of the current Set.
     */
    public void reset() {
        for (int i = 0; i < this.S.length; i++) {
            this.S[i] = i;
        }
    }
}
