import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segmentsList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input points array is null");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Input contains null points");
            }

            Point[] sortedPoints = Arrays.copyOf(points, points.length);
            Arrays.sort(sortedPoints, p.slopeOrder());

            int j = 1;
            while (j < sortedPoints.length) {
                int count = 1;
                double currentSlope = p.slopeTo(sortedPoints[j]);

                while (j + count < sortedPoints.length && Double.compare(currentSlope, p.slopeTo(sortedPoints[j + count])) == 0) {
                    count++;
                }

                if (count >= 3) {
                    Point[] collinearPoints = new Point[count + 1];
                    collinearPoints[0] = p;
                    for (int k = 0; k < count; k++) {
                        collinearPoints[k + 1] = sortedPoints[j + k];
                    }

                    Arrays.sort(collinearPoints);
                    if (collinearPoints[0] == p) {
                        segmentsList.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
                    }
                }

                j += count;
            }
        }
    }

    public int numberOfSegments() {
        return segmentsList.size();
    }

    public LineSegment[] segments() {
        return segmentsList.toArray(new LineSegment[0]);
    }
}
