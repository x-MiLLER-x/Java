public class Main {
    public static void main(String[] args) {
        // Create an array of Point objects for testing
        Point[] points = new Point[5];
        points[0] = new Point(1, 1);
        points[1] = new Point(2, 2);
        points[2] = new Point(3, 3);
        points[3] = new Point(4, 4);
        points[4] = new Point(5, 5);

        // Test Point class methods
        Point origin = new Point(0, 0);
        for (Point p : points) {
            double slope = origin.slopeTo(p);
            System.out.println("Slope to " + p + " = " + slope);
        }


        // Test BruteCollinearPoints
        BruteCollinearPoints bruteCollinear = new BruteCollinearPoints(points);
        System.out.println("Number of line segments (Brute): " + bruteCollinear.numberOfSegments());
        for (BruteCollinearPoints.LineSegment segment : bruteCollinear.segments()) {
            System.out.println(segment);
        }

        // Test FastCollinearPoints
        FastCollinearPoints fastCollinear = new FastCollinearPoints(points);
        System.out.println("Number of line segments (Fast): " + fastCollinear.numberOfSegments());
        for (FastCollinearPoints.LineSegment segment : fastCollinear.segments()) {
            System.out.println(segment);
        }
    }
}
