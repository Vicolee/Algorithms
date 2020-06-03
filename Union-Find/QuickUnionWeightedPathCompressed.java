import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionWeightedPathCompressed {
    // Instance Variables
    private int[] id;       //id[i] = root of i
    private int[] size;     //size[i] = number of elements in the subtree root i.
    private int count;

    // Constructor
    public QuickUnionWeightedPathCompressed(int n) {
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    // Search for the root of the tree. Intuition: id[i] is the root of the index
    public int root(int p) {
        validate(p);
        while (p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // Redirecting root of p to q
    public void union(int p, int q) {
        int rootP = root(p);                // Path compressed
        int rootQ = root(q);                // Path compressed
        if (rootP == rootQ) return;
        if (size[rootP] < size[rootQ]) {
            id[rootP] = rootQ;              // Add root of P to Q
            size[rootQ] += size[rootP];
        } else {
            id[rootQ] = rootP;              // Add root of Q to P
            size[rootP] += size[rootQ];
        }
        count--;
    }

    // Validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickUnionWeightedPathCompressed uf = new QuickUnionWeightedPathCompressed(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.root(p) == uf.root(q)) {
                continue;
            }
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count + " components");
    }
}

