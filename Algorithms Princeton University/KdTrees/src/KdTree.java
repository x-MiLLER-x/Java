import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        root = insert(root, p, true, new RectHV(0, 0, 1, 1));
    }

    private Node insert(Node node, Point2D point, boolean useX, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(point, rect);
        }

        if (node.point.equals(point)) {
            return node;
        }

        int cmp;
        if (useX) {
            cmp = Double.compare(point.x(), node.point.x());
            if (cmp < 0) {
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
                node.left = insert(node.left, point, !useX, leftRect);
            } else {
                RectHV rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, point, !useX, rightRect);
            }
        } else {
            cmp = Double.compare(point.y(), node.point.y());
            if (cmp < 0) {
                RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                node.left = insert(node.left, point, !useX, leftRect);
            } else {
                RectHV rightRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, point, !useX, rightRect);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D point, boolean useX) {
        if (node == null) {
            return false;
        }
        if (node.point.equals(point)) {
            return true;
        }
        int cmp;
        if (useX) {
            cmp = Double.compare(point.x(), node.point.x());
        } else {
            cmp = Double.compare(point.y(), node.point.y());
        }
        if (cmp < 0) {
            return contains(node.left, point, !useX);
        } else {
            return contains(node.right, point, !useX);
        }
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean useX) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        StdDraw.setPenRadius();
        if (useX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        draw(node.left, !useX);
        draw(node.right, !useX);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }
        LinkedList<Point2D> rangePoints = new LinkedList<>();
        range(root, rect, rangePoints, true);
        return rangePoints;
    }

    private void range(Node node, RectHV rect, LinkedList<Point2D> rangePoints, boolean useX) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.point)) {
            rangePoints.add(node.point);
        }
        if (useX) {
            if (rect.xmin() <= node.point.x()) {
                range(node.left, rect, rangePoints, !useX);
            }
            if (rect.xmax() >= node.point.x()) {
                range(node.right, rect, rangePoints, !useX);
            }
        } else {
            if (rect.ymin() <= node.point.y()) {
                range(node.left, rect, rangePoints, !useX);
            }
            if (rect.ymax() >= node.point.y()) {
                range(node.right, rect, rangePoints, !useX);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node node, Point2D queryPoint, Point2D closest, boolean useX) {
        if (node == null) {
            return closest;
        }
        double minDist = closest.distanceSquaredTo(queryPoint);
        if (node.rect.distanceSquaredTo(queryPoint) >= minDist) {
            return closest;
        }
        double dist = node.point.distanceSquaredTo(queryPoint);
        if (dist < minDist) {
            closest = node.point;
        }
        int cmp;
        if (useX) {
            cmp = Double.compare(queryPoint.x(), node.point.x());
        } else {
            cmp = Double.compare(queryPoint.y(), node.point.y());
        }
        if (cmp < 0) {
            closest = nearest(node.left, queryPoint, closest, !useX);
            closest = nearest(node.right, queryPoint, closest, !useX);
        } else {
            closest = nearest(node.right, queryPoint, closest, !useX);
            closest = nearest(node.left, queryPoint, closest, !useX);
        }
        return closest;
    }

    public static void main(String[] args) {
        // Example usage and testing
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        RectHV rangeRect = new RectHV(0.3, 0.2, 0.8, 0.7);
        System.out.println("Points in range:");
        for (Point2D p : kdTree.range(rangeRect)) {
            System.out.println(p);
        }

        Point2D nearestPoint = kdTree.nearest(new Point2D(0.6, 0.5));
        System.out.println("Nearest point: " + nearestPoint);
    }
}
