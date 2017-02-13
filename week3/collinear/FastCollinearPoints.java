import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private Point[] ps;
    private int n;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        n = points.length;
        ps = Arrays.copyOf(points, n);
        Arrays.sort(ps);
        Point curVal, lastVal = null;
        for (int i = 0; i < n; i++) {
            curVal = ps[i];
            if (curVal == null) throw new java.lang.NullPointerException();
            if (curVal == lastVal) throw new java.lang.IllegalArgumentException();
            lastVal = curVal;
        }
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        // Sort elements by natural order. This avoids redundantly calculating
        // relative slopes.
        LineSegment[] lss = new LineSegment[n * n];
        int segIdx = -1;
        for (int i = 0; i < n - 3; i++) {
            Point p = ps[i];
            // Get comparator
            Comparator<Point> c = p.slopeOrder();
            Point[] tmpArr = Arrays.copyOfRange(ps, i + 1, n);
            int tmpArrLen = tmpArr.length;
            // Sort elements in `tmp` array by slopeOrder
            Arrays.sort(tmpArr, c);
            // Identify groups of elements with same slope.
            // Each group containing a minimum of 3 elements;
            int lastMatchIdx = 0;
            int j = 1;
            for (j = 1; j < tmpArrLen; j++) {
                if (c.compare(tmpArr[lastMatchIdx], tmpArr[j]) != 0) {
                    if (j - lastMatchIdx >= 2) lss[++segIdx] = new LineSegment(p, tmpArr[j - 1]);
                    lastMatchIdx = j;
                }
            }
            if (j - lastMatchIdx > 2) lss[++segIdx] = new LineSegment(p, tmpArr[j - 1]);
        }
        LineSegment[] out = Arrays.copyOfRange(lss, 0, segIdx + 1);
        return out;
    }
}
