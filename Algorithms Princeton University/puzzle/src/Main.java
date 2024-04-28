public class Main {
    public static void main(String[] args) {
        int[][] initialTiles = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };

        Board initialBoard = new Board(initialTiles);
        Solver solver = new Solver(initialBoard);

        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        } else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
    }
}
