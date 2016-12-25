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

    private double calculatePercolationThreshold(Percolation p) {
        return (p.sitesOpened/(double)(N*N));
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
            thresholds[i] = calculatePercolationThreshold(p);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return (mean() - ((1.96 * stddev())/Math.sqrt(trials)));
    }

    public double confidenceHi() {
        return (mean() + ((1.96 * stddev())/Math.sqrt(trials)));
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
        String meanStr = String.format("%-25s = %.16f", "mean", ps.mean());
        String stddevStr = String.format("%-25s = %.16f", "stddev", ps.stddev());
        String confStr = String.format(
            "%-25s = %.16f, %.16f",
            "95% confidence interval",
            ps.confidenceLo(),
            ps.confidenceHi()
        );
        System.out.println(meanStr);
        System.out.println(stddevStr);
        System.out.println(confStr);
    }
}