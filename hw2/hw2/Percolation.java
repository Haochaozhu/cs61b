package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private boolean[][] sites;
    private WeightedQuickUnionUF wuf;
    private int count;
    private int topVirtualSite;
    private int bottomVirtualSite;

    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException();
        sites = new boolean[N][N];
        wuf = new WeightedQuickUnionUF(N * N + 2);
        count = 0;
        topVirtualSite = N * N;
        bottomVirtualSite = N * N + 1;
    }


    private int to1dIndex(int row, int col) {
        return row * sites.length + col;
    }

//    private boolean checkInput(int row, int col) {
//        if (row > sites.length - 1 || col > sites.length - 1) {
//            throw new java.lang.IndexOutOfBoundsException();
//        }
//    }

//    Returns the 1d index of the up neighbour that is open. Returns -1 if not open or not existed.
    private int isUpOpen(int row, int col) {
        if (row == 0) return -2;
        if (isOpen(row - 1, col)) return to1dIndex(row - 1, col);
        else return -1;
    }

    private int isRightOpen(int row, int col) {
        if (col == sites.length - 1) return -1;
        if (isOpen(row, col + 1)) return to1dIndex(row, col + 1);
        else return -1;
    }

    private int isLeftOpen(int row, int col) {
        if (col == 0) return -1;
        if (isOpen(row, col - 1)) return to1dIndex(row, col - 1);
        else return -1;
    }

    private int isDownOpen(int row, int col) {
        if (row == sites.length - 1) return -2;
        if (isOpen(row + 1, col)) return to1dIndex(row + 1, col);
        else return -1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
//        checkInput(row, col);
        if (isOpen(row, col)) return;

        sites[row][col] = true;
        count += 1;
        if (isUpOpen(row, col) >= 0) wuf.union(to1dIndex(row, col), isUpOpen(row, col));
        else if (isUpOpen(row, col) == -2) wuf.union(to1dIndex(row, col), topVirtualSite);

        if (isRightOpen(row, col) >= 0) wuf.union(to1dIndex(row, col), isRightOpen(row, col));
        if (isLeftOpen(row, col) >= 0) wuf.union(to1dIndex(row, col), isLeftOpen(row, col));

        if (isDownOpen(row, col) >= 0) wuf.union(to1dIndex(row, col), isDownOpen(row, col));
        else if (isDownOpen(row, col) == -2) {
            if (isFull(row,col)) wuf.union(to1dIndex(row, col), bottomVirtualSite);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return wuf.connected(to1dIndex(row, col), topVirtualSite);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return wuf.connected(topVirtualSite, bottomVirtualSite);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {

    }

}
