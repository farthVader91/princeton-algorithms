import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    public int sitesOpened;
    private boolean[][] grid;
    private WeightedQuickUnionUF wqu;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        grid = new boolean[n][n];
        wqu = new WeightedQuickUnionUF((n*n) + 2); // for applying "clever trick"
    }

    private int effectiveIndex(int row, int col) {
        return (row * N) + col;
    }

    public void open(int row, int col) {
        if (row > N || col > N) {
            throw new IndexOutOfBoundsException();
        }

        // for internal indexing
        row = row - 1; 
        col = col - 1;
        if(grid[row][col]) { return; }

        try {
            if(grid[row - 1][col]) {
                wqu.union(
                    effectiveIndex(row - 1, col),
                    effectiveIndex(row, col)
                );
            }
        } catch(IndexOutOfBoundsException e) {
            // System.out.println('!');
        } finally {
            try {
                if(grid[row + 1][col]) {
                    wqu.union(
                        effectiveIndex(row + 1, col),
                        effectiveIndex(row, col)
                    );
                }
            } catch(IndexOutOfBoundsException e) {
                // System.out.println('!');
            } finally {
                try {
                    if(grid[row][col - 1]) {
                        wqu.union(
                            effectiveIndex(row, col - 1),
                            effectiveIndex(row, col)
                        );
                    }
                } catch(IndexOutOfBoundsException e) {
                    // System.out.println('!');
                } finally {
                    try {
                        if(grid[row][col + 1]) {
                            wqu.union(
                                effectiveIndex(row, col + 1),
                                effectiveIndex(row, col)
                            );
                        }
                    } catch(IndexOutOfBoundsException e) {
                        // System.out.println('!');
                    }
                }
            }
        }

        grid[row][col] = true;
        sitesOpened++;
        if(row == N-1) {
            // connect to lower virtual root
            int lvr = (N * N) + 1;
            wqu.union(
                effectiveIndex(row, col),
                lvr
            );
        } else if(row == 0) {
            // connect to upper virtual root
            int uvr = (N * N) + 0;
            wqu.union(
                effectiveIndex(row, col),
                uvr
            );
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

        for(int i=0; i<N;++i) {
            if (wqu.connected(
                effectiveIndex(row, col),
                effectiveIndex(0, i)
            )) {
                return true;
            }
        }
        return false;
    }

    public boolean percolates() {
        for(int i=0; i<N; ++i) {
        }
        int gridSize = N * N;
        return wqu.connected(gridSize, gridSize + 1);
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


    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        Percolation p = new Percolation(size);
        p.printGrid();
    }
}