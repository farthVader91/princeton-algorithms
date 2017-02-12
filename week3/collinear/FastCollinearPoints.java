import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private Point[] ps;
    private int n;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        n = points.length;
        ps = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) { throw new java.lang.NullPointerException(); }
            for (int j = 0; j < i; j++) {
                if (ps[j] == points[i]) { throw new java.lang.IllegalArgumentException(); }
            }
            ps[i] = points[i];
        }
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        // Sort elements by natural order. This avoids redundantly calculating
        // relative slopes.
        Arrays.sort(ps);
        LineSegment[] lss = new LineSegment[n * n];
        int segIdx = -1;
        for (int i = 0; i < n - 3; i++) {
            Point p = ps[i];
            // Get comparator
            Comparator<Point> c = p.slopeOrder();
            Point[] tmpArr = Arrays.copyOfRange(ps, i + 1, n);
            // Sort elements in `tmp` array by slopeOrder
            Arrays.sort(tmpArr, c);
            // Identify groups of elements with same slope.
            // Each group containing a minimum of 3 elements;
            int matchedSoFar = 0;
            double curSlope, lastSlope = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < n - i - 1; j++) {
                curSlope = p.slopeTo(tmpArr[j]);
                if (curSlope == lastSlope) matchedSoFar++;
                else {
                    if (matchedSoFar >= 2) lss[++segIdx] = new LineSegment(p, tmpArr[j]);
                    matchedSoFar = 0;
                }
                lastSlope = curSlope;
            }
            // Handle
            if (matchedSoFar >= 2) lss[++segIdx] = new LineSegment(p, tmpArr[n - i - 2]);
        }
        LineSegment[] out = Arrays.copyOfRange(lss, 0, segIdx + 1);
        return out;
    }
}
