import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int priority;

        public SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    private SearchNode solutionNode;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null.");
        }

        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        mainPQ.insert(new SearchNode(initial, null, 0));
        twinPQ.insert(new SearchNode(initial.twin(), null, 0));

        while (true) {
            solutionNode = expand(mainPQ);
            if (solutionNode != null || expand(twinPQ) != null) {
                return;
            }
        }
    }
    private SearchNode expand(MinPQ<SearchNode> pq) {
        if (pq.isEmpty()) {
            return null;
        }

        SearchNode minNode = pq.delMin();
        if (minNode.board.isGoal()) {
            return minNode;
        }

        for (Board neighbor : minNode.board.neighbors()) {
            if (minNode.previous == null || !neighbor.equals(minNode.previous.board)) {
                pq.insert(new SearchNode(neighbor, minNode, minNode.moves + 1));
            }
        }

        return null;
    }

    public boolean isSolvable() {
        return solutionNode != null;
    }

    public int moves() {
        return isSolvable() ? solutionNode.moves : -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        List<Board> solution = new ArrayList<>();
        SearchNode current = solutionNode;
        while (current != null) {
            solution.add(current.board);
            current = current.previous;
        }
        Collections.reverse(solution);
        return solution;
    }

    public static void main(String[] args) {
        // Input parsing and testing code here
    }
}
