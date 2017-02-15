import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private Point[] ps;
    private LineSegment[] out;
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
            if ((lastVal != null) && (curVal.compareTo(lastVal) == 0)) throw new java.lang.IllegalArgumentException();
            lastVal = curVal;
        }
        // Sort elements by natural order. This avoids redundantly calculating
        // relative slopes.
        LineSegment[] lss = new LineSegment[(n -1) * (n - 1)];
        int segIdx = -1;
        Point[] copy = Arrays.copyOf(ps, n);
        for (int i = 0; (i < n) && (n >= 4); i++) {
            Point p = copy[i];
            Arrays.sort(ps, 0, n, p.slopeOrder());
            int lo = 1, hi = 2;
            boolean flag = p.compareTo(ps[lo]) < 0 ? true : false;
            while(hi < n) {
                if(ps[lo].slopeTo(p) == ps[hi].slopeTo(p)) {
                    if (p.compareTo(ps[hi]) >= 0) flag = false;
                } else {
                    if (flag && hi - lo >= 3) {
                        Arrays.sort(ps, lo, hi);
                        lss[++segIdx] = new LineSegment(p, ps[hi - 1]);
                    }
                    // Reset lo, flag
                    lo = hi;
                    flag = p.compareTo(ps[lo]) < 0 ? true : false;
                }
                hi++;
            }
            if (flag && hi - lo >= 3) {
                Arrays.sort(ps, lo, hi);
                lss[++segIdx] = new LineSegment(p, ps[hi - 1]);
            }
        }
        out = new LineSegment[segIdx + 1];
        for(int i = 0; i <= segIdx; i++) out[i] = lss[i];
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(out, out.length);
    }
}
