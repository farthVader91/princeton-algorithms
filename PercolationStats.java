import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size;
    private int trials;
    private double[] thresholds;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[t];
        size = n;
        trials = t;
        for (int i = 0; i < t; ++i) {
            Percolation p = new Percolation(n);
	    while (!p.percolates()) {
                // open random row and column
                int row = getRandInt();
                int col = getRandInt();
		p.open(row,col);
            }
	    addThreshold(i, p.numberOfOpenSites());
        }	
    }

    private double calculatePercolationThreshold(int sitesOpened) {
        return (sitesOpened / (double)(size * size));
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(trials)));
    }

    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(trials)));
    }

    private int getRandInt() {
        return 1 + StdRandom.uniform(size);
    }

    private void addThreshold(int pos, int opened) {
	thresholds[pos] = calculatePercolationThreshold(opened);
    }
    
    public static void main(String[] args) {
	int n = Integer.parseInt(args[0]);
	int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

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
