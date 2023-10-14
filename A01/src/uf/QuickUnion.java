package src.uf;

public class QuickUnion extends UnionFind{
    public String name = "QU";

    private int[] S;

    public QuickUnion(int N) {
        this.S = new int[N];
        for(int i=0; i<N; i++) this.S[i] = i;
    }

    private int root(int a) {
        while(S[a]!=a) a=S[a];
        return a;
    }

    public boolean connected(int a, int b) {
        return root(a) == root(b);
    }

    public void union(int a, int b) {
        int rootA = root(a);
        int rootB = root(b);

        if (rootA == rootB) {return;}

        S[rootA] = rootB;
    }
}
