
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by lw on 2017/2/25.
 */
public class PercolationStats {

//    private Percolation mPercolation;
    private int mTrials;
    private double[] mX;
    private int sizeN;

    public PercolationStats(int n, int trials) {  // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw   new IllegalArgumentException();
        }

        sizeN = n;
//        mPercolation = new Percolation(n);
        mTrials = trials;
        mX = new double[mTrials];

        for (int i = 0; i < mTrials; ++i) {
            Percolation mPercolation = new Percolation(sizeN);
            while (!mPercolation.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                mPercolation.open(row, col);
            }
            mX[i] = mPercolation.numberOfOpenSites() / (double) (sizeN*sizeN);
        }
    }

    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(mX);
    }

    public double stddev() {                       // sample standard deviation of percolation threshold

        return StdStats.stddev(mX);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        double x = this.mean();
        double stdDev = this.stddev();
        double sqrtTrials = Math.sqrt(mTrials);

        return x - 1.96 * stdDev / sqrtTrials;
    }
    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        double x = this.mean();
        double stdDev = this.stddev();
        double sqrtTrials = Math.sqrt(mTrials);

        return x + 1.96 * stdDev / sqrtTrials;
    }

    public static void main(String[] args) {       // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, t);
        StdOut.printf("mean                    = %.16f\n", p.mean());
        StdOut.printf("stddev                  = %.16f\n", p.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]\n", p.confidenceLo(), p.confidenceHi());
    }
}
