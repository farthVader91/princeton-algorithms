import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private Point[] ps;
    private LineSegment[] out;
    private int n;

    public BruteCollinearPoints(Point[] points) {
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
        LineSegment[] ls = new LineSegment[n * n];
        double s1, s2, s3;
        int segmentIdx = 0;
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                s1 = ps[i].slopeTo(ps[j]);
                for (int k = j + 1; k < n - 1; k++) {
                    s2 = ps[i].slopeTo(ps[k]);
                    for (int l = k + 1; l < n; l++) {
                        s3 = ps[i].slopeTo(ps[l]);
                        if ((s1 == s2) && (s1 == s3)) ls[segmentIdx++] = new LineSegment(ps[i], ps[l]);
                    }
                }
            }
        }
        out = new LineSegment[segmentIdx];
        for (int i = 0; i < segmentIdx; i++) out[i] = ls[i];
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(out, out.length);
    }

    public static void main(String[] args) {
        Point[] points = {
            new Point(10, 10),
            new Point(100, 100),
            new Point(1000, 1000),
            new Point(2000, 2000),
            new Point(3000, 3000),
            new Point(4000, 4000),
        };
        BruteCollinearPoints br = new BruteCollinearPoints(points);
        for (LineSegment seg: br.segments()) {
            System.out.println(seg);
        }
        br.out[0] = new LineSegment(new Point(1,2), new Point(2,3));
        System.out.println();
        br.ps[3] = new Point(6000, 7000);
        for (LineSegment seg: br.segments()) {
            System.out.println(seg);
        }
    }
}
