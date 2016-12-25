import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int trials;
    private double[] thresholds;

    public PercolationStats(int n, int t) {
        if(n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[t];
        N = n;
        trials = t;
    }

    public void runSimulation() {
        for(int i=0; i<trials; ++i) {
            Percolation p = new Percolation(N);
            while(!p.percolates()) {
                // open random row and column
                int row = getRandInt();
                int col = getRandInt();
                p.open(row, col);
            }
            thresholds[i] = p.getPercolationThreshold();
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return 1.0;
    }

    public double confidenceHi() {
        return 1.0;
    }

    private int getRandInt() {
        return 1 + StdRandom.uniform(N);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
            Integer.parseInt(args[0]),
            Integer.parseInt(args[1])
        );
        ps.runSimulation();
        System.out.println(Double.toString(ps.mean()));
    }
}