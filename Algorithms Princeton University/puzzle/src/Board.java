import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int hamming;
    private final int manhattan;

    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        int hammingCount = 0;
        int manhattanDistance = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) {
                    hammingCount++;
                    int targetRow = (tiles[i][j] - 1) / n;
                    int targetCol = (tiles[i][j] - 1) % n;
                    manhattanDistance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }

        this.hamming = hammingCount;
        this.manhattan = manhattanDistance;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board other = (Board) y;
        return Arrays.deepEquals(tiles, other.tiles);
    }

    public Iterable<Board> neighbors() {
        List<Board> neighborBoards = new ArrayList<>();
        int zeroRow = -1;
        int zeroCol = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        int[][] neighborsDelta = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] delta : neighborsDelta) {
            int newRow = zeroRow + delta[0];
            int newCol = zeroCol + delta[1];

            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                int[][] neighborTiles = copyTiles();
                swap(neighborTiles, zeroRow, zeroCol, newRow, newCol);
                neighborBoards.add(new Board(neighborTiles));
            }
        }

        return neighborBoards;
    }

    public Board twin() {
        int[][] twinTiles = copyTiles();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (twinTiles[i][j] != 0 && twinTiles[i][j + 1] != 0) {
                    swap(twinTiles, i, j, i, j + 1);
                    return new Board(twinTiles);
                }
            }
        }

        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private int[][] copyTiles() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    private void swap(int[][] arr, int i, int j, int x, int y) {
        int temp = arr[i][j];
        arr[i][j] = arr[x][y];
        arr[x][y] = temp;
    }
}
