import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int openSites;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;

    // Creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0.");
        }
        this.n = n;
        this.openSites = 0;
        this.grid = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n + 2); // Additional 2 for virtual top and bottom nodes
    }

    // Converts 2D grid coordinates to 1D UF array index
    private int xyTo1D(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    // Opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row and column indices are out of bounds.");
        }

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSites++;

            // Connect to adjacent open sites
            int currentSite = xyTo1D(row, col);
            if (row == 1) {
                uf.union(currentSite, n * n);
            }
            if (row == n) {
                uf.union(currentSite, n * n + 1);
            }

            int[] dx = {0, 0, -1, 1};
            int[] dy = {-1, 1, 0, 0};

            for (int i = 0; i < 4; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];
                if (newRow >= 1 && newRow <= n && newCol >= 1 && newCol <= n && isOpen(newRow, newCol)) {
                    int adjacentSite = xyTo1D(newRow, newCol);
                    uf.union(currentSite, adjacentSite);
                }
            }
        }
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row and column indices are out of bounds.");
        }
        return grid[row - 1][col - 1];
    }

    // Is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row and column indices are out of bounds.");
        }
        int currentSite = xyTo1D(row, col);
        return uf.find(currentSite) == uf.find(n * n);
    }

    // Returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // Does the system percolate?
    public boolean percolates() {
        return uf.find(n * n) == uf.find(n * n + 1);
    }

    // Test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
