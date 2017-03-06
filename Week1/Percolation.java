
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by lw on 2017/2/24.
 */
public class Percolation {

    private final int mMAXSIZE;
    private final int mVirTopIndex;
    private final int mVirBottomIndex;
    private int mSizeN;
    private int mNumberOfOpenSite = 0;
    private boolean[] openFlag = null;
//    private boolean[] fullFlag = null;

    private WeightedQuickUnionUF mWeightedQuickUnionUF;
    private WeightedQuickUnionUF mUnionUFWihoutVirtualBottom;

    public Percolation(int mSizeN) { // create mSizeN-by-mSizeN grid, with all sites blocked
        if (mSizeN <= 0) {
            throw new IllegalArgumentException();
        }
        this.mSizeN = mSizeN;
        this.mMAXSIZE = mSizeN*mSizeN + 2;
        this.mVirTopIndex = 0;
        this.mVirBottomIndex = mMAXSIZE - 1;
        this.openFlag = new boolean[mMAXSIZE];
//        this.fullFlag = new boolean[mMAXSIZE];

        mWeightedQuickUnionUF = new WeightedQuickUnionUF(mMAXSIZE);
        mUnionUFWihoutVirtualBottom = new WeightedQuickUnionUF(mMAXSIZE);
    }

    private int calcIndex(int row, int col) {
        return row * mSizeN - mSizeN + col;
    }

    private void connectWithNeighbors(int row, int col) {

        int selfIndex = calcIndex(row, col);

        if (row == 1) {
            mWeightedQuickUnionUF.union(selfIndex, mVirTopIndex);
            mUnionUFWihoutVirtualBottom.union(selfIndex, mVirTopIndex);
        }

        if (row == mSizeN) {
            mWeightedQuickUnionUF.union(selfIndex, mVirBottomIndex);
        }

        if (row > 1) { // not top
            int up = calcIndex(row-1, col);
            connectOneNeighbor(selfIndex, up);
        }

        if (row < mSizeN) { // not bottom
            int down = calcIndex(row+1, col);
            connectOneNeighbor(selfIndex, down);
        }

        if (col > 1) { // have a left neighbor
            int left = calcIndex(row, col-1);
            connectOneNeighbor(selfIndex, left);
        }

        if (col < mSizeN) {
            int right = calcIndex(row, col + 1);
            connectOneNeighbor(selfIndex, right);
        }
    }

    private void connectOneNeighbor(int selfIndex, int neighborIndex) {
        if (isOpen(neighborIndex)) {
            mWeightedQuickUnionUF.union(selfIndex, neighborIndex);
            mUnionUFWihoutVirtualBottom.union(selfIndex, neighborIndex);
        }
    }

    public void open(int row, int col) { // open site(row, col) if it is not open already
        validate(row, col);
        if (!isOpen(row, col)) {
            int selfIndex = calcIndex(row, col);

            openFlag[selfIndex] = true;

            ++mNumberOfOpenSite;

            this.connectWithNeighbors(row, col);
        }
    }

    public boolean isOpen(int row, int col) { // is site(row, col) open?
        validate(row, col);
        int index = calcIndex(row, col);
        return openFlag[index];
    }

    private boolean isOpen(int index) {
        if (index < 0 || index > mMAXSIZE) {
            throw new IllegalArgumentException();
        }

        return openFlag[index];
    }

    public boolean isFull(int row, int col) { // is site(row, col) full?
        validate(row, col);
        int index = calcIndex(row, col);
        return isFull(index);
    }

    private boolean isFull(int index) {
        if (index < 0 || index > mMAXSIZE) {
            throw new IllegalArgumentException();
        }

        return mUnionUFWihoutVirtualBottom.connected(mVirTopIndex, index);
    }

    public int numberOfOpenSites() { // number of open sites
        return mNumberOfOpenSite;
    }

    public boolean percolates() { // does the system percolate?

        return mWeightedQuickUnionUF.connected(mVirTopIndex, mVirBottomIndex);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > mSizeN || col < 1 || col > mSizeN) {
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {

    }
}
