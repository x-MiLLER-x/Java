public class LineSegment {
    private final Point p;
    private final Point q;

    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    public void draw() {
        // Implement draw method here
    }

    public String toString() {
        return p + " -> " + q;
    }
}
