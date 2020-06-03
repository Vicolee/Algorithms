public class QuickUnionPathCompression {
    // Instance Variables
    private int[] id;

    // Constructor
    public QuickUnionPathCompression(int N) {
        id = new int[N];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    // Search for the root of the tree. Intuition: id[i] is the root of the index
    // Path compression improvement added.
    private int root(int p) {
        int i = p;
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // Redirecting root of p to q
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;              // Set root of i to j.
    }
}
