import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int n;
    private int[] blankPos;
    private int[][] grid;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        grid = new int[n][n];
        int val;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++){
                val = blocks[i][j];
                grid[i][j] = val;
                if (val == 0) blankPos = new int[]{i, j};
            }
        }
    }

    // board dimension n
    public int dimension() { return n; }

    private int getValueAtPos(int row, int col) {
        return row * n + col + 1;
    }

    private int[] getPosForVal(int val) {
        int row, col;
        row = val / n;
        col = (val % n);
        if (col == 0) {
            row--;
            col = n - 1;
        }
        else col--;
        return new int[]{row, col};
    }

    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                int t = i * n + j + 1;
                if (grid[i][j]  != getValueAtPos(i, j)) distance++;
            }
        }
        return distance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        int[] pos;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;
                pos = getPosForVal(grid[i][j]);
                if (i != pos[0] || j != pos[1]) distance = distance + Math.abs(i - pos[0]) + Math.abs(j - pos[1]);
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    private boolean swap(int[] pos1, int[] pos2) {
        int temp = grid[pos1[0]][pos1[1]];
        if (temp == 0 || grid[pos2[0]][pos2[1]] == 0) return false;
        grid[pos1[0]][pos1[1]] = grid[pos2[0]][pos2[1]];
        grid[pos2[0]][pos2[1]] = temp;
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board clone = new Board(grid);
        while (true) {
            // Get 2 random positions
            int[] pos1 = new int[2], pos2 = new int[2];
            pos1[0] = StdRandom.uniform(n);
            pos1[1] = StdRandom.uniform(n);
            pos2[0] = StdRandom.uniform(n);
            pos2[1] = StdRandom.uniform(n);
            if(clone.swap(pos1, pos2)) break;
        }
        return clone;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) if (grid[i][j] != that.grid[i][j]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> s = new Stack<Board>();

        return s;
    }

    /*
    public String toString()               // string representation of this board (in the output format specified below)
    */

    // unit tests (not graded)
    public static void main(String[] args) {
        int arr[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(arr);
        Board c = b.twin();
        /*
        for(int i = 0; i < 3; i++) {
            for(int j =0 ; j < 3; j++) {
                System.out.println(c.grid[i][j]);
            }
        }
        */
        System.out.println(c.blankPos[0] + " " + c.blankPos[1]);
    }
}
