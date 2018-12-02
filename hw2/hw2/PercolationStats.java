package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class PercolationStats {

    private double[] fractionPercent;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N  <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();

        fractionPercent = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation prc = pf.make(N);
            while(!prc.percolates()) {
                prc.open(StdRandom.uniform(N),StdRandom.uniform(N));
            }
            fractionPercent[i] = (double)prc.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
       return StdStats.mean(fractionPercent);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return 0;
    }

    public static void main(String[] args) {
        StdOut.println(-1 % 4);
        StdOut.println(Math.floorMod(-1, 4));

    }
}
