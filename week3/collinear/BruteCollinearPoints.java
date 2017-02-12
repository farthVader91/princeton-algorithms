import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private Point[] ps;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        int n = points.length;
        ps = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) { throw new java.lang.NullPointerException();}
            for (int j = 0; j < i; j++) {
                if (ps[j] == points[i]) { throw new java.lang.IllegalArgumentException(); }
            }
            ps[i] = points[i];
        }
        Arrays.sort(ps);
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        int n = ps.length;
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
        LineSegment[] out = new LineSegment[segmentIdx];
        for(int i = 0; i < segmentIdx; i++) out[i] = ls[i];
        return out;
    }

    public static void main(String[] args) {
        Point[] points = {
            new Point(0, 0),
            new Point(10, 10),
            new Point(100, 100),
            new Point(1000, 1000),
        };
        BruteCollinearPoints br = new BruteCollinearPoints(points);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 2000);
        StdDraw.setYscale(0, 2000);
        for(LineSegment seg: br.segments()) {
            seg.draw();
        }
        StdDraw.show();
    }
}
