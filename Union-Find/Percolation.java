import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Instance variables
    private int[][] grid;
    private int count;
    private final int gridLength;     // n is the length of the grid. n x n => grid
    private final int topRowIndex;    // virtual top index that goes before (1,1)
    private final int bottomRowIndex; // virtual bottom index that goes after (n, n)
    private final WeightedQuickUnionUF uf;

    // Constructor. Take value 0 to be blocked and value 1 to be open.
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid n. Must be at least 1.");
        gridLength = n;
        grid = new int[gridLength][gridLength];
        int gridSize = gridLength * gridLength;
        topRowIndex = 0;
        bottomRowIndex = gridLength * gridLength + 1;
        // Initialize uf such that it includes the two virtual top and bottom indexes.
        uf = new WeightedQuickUnionUF(gridSize + 2);
        for (int row = 0; row < gridLength; row++) {
            for (int column = 0; column < gridLength; column++) {
                grid[row][column] = 0;
            }
        }
    }

    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        // Open the site, then union with its neighbors if they are opened as well.
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;         // open the site
            count++;
            if (row == 1) uf.union(siteIndex(row, col), topRowIndex);       // union with virtual top
            if (row == gridLength) uf.union(siteIndex(row, col), bottomRowIndex);    // union with virtual bottom
            // Union with the left of the site
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(siteIndex(row, col), siteIndex(row, col - 1));
            }
            // Union with the right of the site
            if (col < gridLength && isOpen(row, col + 1)) {
                uf.union(siteIndex(row, col), siteIndex(row, col + 1));
            }
            // Union with the bottom of the site
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(siteIndex(row, col), siteIndex(row - 1, col));
            }
            // Union with the top of the site
            if (row < gridLength && isOpen(row + 1, col)) {
                uf.union(siteIndex(row, col), siteIndex(row + 1, col));
            }
        }
    }

    // Returns true if the site is open
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return grid[row - 1][col - 1] == 1;
    }

    // Returns true if the site is connected to the top
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        return uf.find(siteIndex(row, col)) == uf.find(topRowIndex);
    }

    // Returns the number of sites opened: with value 1
    public int numberOfOpenSites() {
        return count;
    }

    // Return true if the system percolates
    // Recall that WeightedQuickUnionUF connects the smaller tree
    // (bottomRowIndex root) with the larger tree(topRowIndex root)
    public boolean percolates() {
        return uf.find(bottomRowIndex) == uf.find(topRowIndex);
    }

    // Converts the grid's index from 2-D coordinate system which begins from (0, 0) ending with
    // (n-1, n-1) to a 1D index for the union-find algorithm to process.
    private int siteIndex(int row, int col) {
        validateSite(row, col);
        return (row - 1) * gridLength + col;
    }

    // Use this to check if the integer is within the prescribed range
    private void validateSite(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        if (shiftRow >= gridLength || shiftRow < 0) {
            throw new IllegalArgumentException("row " + gridLength + " is not between 0 and n.");
        }
        if (shiftCol >= gridLength) {
            throw new IllegalArgumentException("column " + col + " is greater than grid n.");
        }
        if (shiftCol < 0) {
            throw new IllegalArgumentException("column" + col + " is negative");
        }
    }
}
