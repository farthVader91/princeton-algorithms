import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF wqu;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        grid = new boolean[n][n];
        wqu = new WeightedQuickUnionUF(n*n);
    }

    private int effectiveIndex(int row, int col) {
        return (row * N) + col;
    }

    public void open(int row, int col) {
        if (row > N || col > N) {
            throw new IndexOutOfBoundsException();
        }
        grid[row-1][col-1] = true;

        // for internal indexing
        row = row - 1; 
        col = col - 1;

        if(grid[row - 1][col]){
            wqu.union(
                effectiveIndex(row - 1, col),
                effectiveIndex(row, col)
            );
        }

        // perform necessary union operations
        if (row == 0) {
            // implies first row; ignore top check;
            if (col == 0) {
                // implies first col; ignore left check;

            } else if (col == (N - 1)) {
                // implies last col; ignore right check;
                
            } else {
                // implies padded col;
            }
        }
        else if (row == (N - 1)) {
            // implies last row; ignore bottom check;
            if (col == 0) {
                // implies first col; ignore left check;

            } else if (col == (N - 1)) {
                // implies last col; ignore right check;
                
            } else {
                // implies padded col;
            }
        } else {
            // implies some cell in between; move in every direction;
        }

    }

    public boolean isOpen(int row, int col) {
        if (row > N || col > N) {
            throw new IndexOutOfBoundsException();
        }
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col) {
        if (row > N || col > N) {
            throw new IndexOutOfBoundsException();
        }

        // for internal indexing
        row = row - 1; 
        col = col - 1;

        return true;
    }

    public boolean percolates() {
        return true;
    }

    public void printGrid() {
        for(int p=0;p<N;p++) {
            System.out.print("[ ");
            for(int q=0;q<N;q++) {
                System.out.print(Boolean.toString(grid[p][q]) + ' ');
            }
            System.out.print("]\n");
        }
    }

    public void runSimulation() {
        System.out.println(
            Boolean.toString(
                wqu.connected(
                    effectiveIndex(8,9), effectiveIndex(9,9)
                )
            )
        );
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        Percolation p = new Percolation(size);
        // p.open(1, 4);
        p.open(4, 3);
        p.open(9, 10);
        p.runSimulation();
        p.open(10, 10);
        p.runSimulation();
        p.printGrid();
    }
}