import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class Main {
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        RectHV rangeRect = new RectHV(0.3, 0.2, 0.8, 0.7);
        System.out.println("Points in range (KdTree):");
        for (Point2D p : kdTree.range(rangeRect)) {
            System.out.println(p);
        }

        PointSET pointSet = new PointSET();
        pointSet.insert(new Point2D(0.2, 0.3));
        pointSet.insert(new Point2D(0.4, 0.7));
        pointSet.insert(new Point2D(0.9, 0.6));

        System.out.println("Points in range (PointSET):");
        for (Point2D p : pointSet.range(rangeRect)) {
            System.out.println(p);
        }

        Point2D nearestPointInKdTree = kdTree.nearest(new Point2D(0.6, 0.5));
        System.out.println("Nearest point (KdTree): " + nearestPointInKdTree);

        Point2D nearestPointInPointSet = pointSet.nearest(new Point2D(0.6, 0.5));
        System.out.println("Nearest point (PointSET): " + nearestPointInPointSet);
    }
}
