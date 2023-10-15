package src.uf;

public class QuickUnion extends UnionFind{
    public String name = "QU";
    private int[] S;

    public QuickUnion(int N) {
        this.S = new int[N];
        for(int i=0; i<N; i++) this.S[i] = i;
    }

    public boolean connected(int a, int b) {
        return root(a) == root(b);
    }

    private int root(int a) {
        while (a != S[a]) {
            a = S[a];
        }
        return a;
    }

    

    public void union(int a, int b) {
        int rootA = root(a);
        int rootB = root(b);

        S[rootA] = rootB;
    }
}
