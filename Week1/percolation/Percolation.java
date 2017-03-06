
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by lw on 2017/2/24.
 */
public class Percolation {

    private int N;
    private int mNumberOfOpenSite = 0;
    private boolean[] openFlag = null;

    private WeightedQuickUnionUF mWeightedQuickUnionUF;

    private int calcIndex(int row, int col) {
        return row * N - row + col;
    }

    private void connectWithNeighbor(int row, int col) {
        int selfIndex = calcIndex(row, col);
        if(row > 1) { //not top
            int top = calcIndex(row-1, col);
            if(openFlag[top]) {
                mWeightedQuickUnionUF.union(selfIndex, top);
            }
        }

        if(row < N) { //not bottom
            int bottom = calcIndex(row+1, col);
            if(openFlag[bottom]) {
                mWeightedQuickUnionUF.union(selfIndex, bottom);
            }
        }

        if(col > 1) { //have a left neighbor
            int left = calcIndex(row, col-1);
            if(openFlag[left]) {
                mWeightedQuickUnionUF.union(selfIndex, left);
            }
        }

        if(col < N) {
            int right = calcIndex(row, col + 1);
            if(openFlag[right]) {
                mWeightedQuickUnionUF.union(selfIndex, right);
            }
        }
    }

    public Percolation(int n) { //create n-by-n grid, with all sites blocked
        if(n < 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        openFlag = new boolean[(n+1)*(n+1)];

        mWeightedQuickUnionUF = new WeightedQuickUnionUF((n+1)*(n+1));
    }

    public void open(int row, int col) { // open site(row, col) if it is not open already
        if(row < 1 || row > N || col < 1 || col > N) {
            throw new IndexOutOfBoundsException();
        }
        if(!isOpen(row, col)) {
            openFlag[calcIndex(row, col)] = true;
            ++mNumberOfOpenSite;

            this.connectWithNeighbor(row, col);

            if(row == 1) {
                mWeightedQuickUnionUF.union(0, calcIndex(row, col));
            }

            if(row == N) {
                mWeightedQuickUnionUF.union(calcIndex(N+1,1), calcIndex(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) { // is site(row, col) open?
        if(row < 1 || row > N || col < 1 || col > N) {
            throw new IndexOutOfBoundsException();
        }
        return openFlag[calcIndex(row,col)];
    }

    public boolean isFull(int row, int col) { // is site(row, col) full?
        if(row < 1 || row > N || col < 1 || col > N) {
            throw new IndexOutOfBoundsException();
        }
        return mWeightedQuickUnionUF.connected(0, calcIndex(row, col)); //point(0,0) is a virtual point connects to all of the top open points
    }

    public int numberOfOpenSite() { // number of open sites
        return mNumberOfOpenSite;
    }

    public boolean percolates() { // does the system percolate?
        return mWeightedQuickUnionUF.connected(0, calcIndex(N+1,1));    //point(N+1, 1) connects to all of the bottom open points;
    }

    public static void main(String[] args) {

    }
}
