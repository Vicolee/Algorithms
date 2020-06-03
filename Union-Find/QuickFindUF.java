public class QuickFindUF {
    private int[] id; // Create instance variable id

    // Create the constructor for QuickFindUF
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    // Method for testing connectivity between two values
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    // Method to union two values
    public void union(int p, int q) {
        int pid = id[p];           // Assign pid and qid values outside loop to avoid Insidious Bug
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }
}
