package src.uf;

public abstract class UnionFind {
    private int[] S;
    private int sz;
    public UnionFind(int N) {
        this.S = new int[N];
        // Each element points to itself as root initially.
        for (int i = 0; i < N; i++) {
            this.S[i] = i;
        }
        this.sz = N;
    }
    public abstract boolean connected(int a, int b);
    public abstract void union(int a, int b);

    public void Run(Integer[][] pairs) {
        int length = pairs.length;
        for(int i=0;i<length;i++){
            union(pairs[i][0],pairs[i][1]);
        }
    }

    public abstract void reset();
    public abstract void reset(int N);

}
