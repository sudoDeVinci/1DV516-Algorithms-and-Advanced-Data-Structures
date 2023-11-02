package src;

import src.uf.WeightedUnionFind;

public class Percolation {
    private WeightedUnionFind wuf;
    private boolean[][] grid;
    private int size;
    private int openCount = 0;
    private final int top = 0;
    private final int bottom;
    

    /**
     * Node 0 is the top node and connected to the input.
     * Node N+1 is the bottom node and connected to the output.
     * Node 1 is the upper left node.
     */
    public Percolation(int i) {
        if (i <= 0) throw new IllegalArgumentException("Percolation matrix must be more than 0.");
        wuf = new WeightedUnionFind(i*i + 2);
        grid = new boolean[i][i];
        this.size = i;
        this.bottom = i*i+1;
    }


    /**
     * Make sure a supplied row-column pair is within the bounds of the grid.
     * @param row 
     * @param col
     */
    private boolean isValid(int row, int col) {
        return (row >= 1 && row <= size) && (col >= 1 && col <= size);
    }

    /**
     * Throw an error if out of bounds.
     * @param row
     * @param col
     */
    private void assertValid(int row, int col) {
        if(!isValid(row, col)) throw new IllegalArgumentException("Invalid row and column.");
    }

    private int DDtoD(int row, int col) {
        assertValid(row, col);
        return (row-1)*size + col-1;
    }


    public boolean isFull(int row, int col) {
        assertValid(row, col);
        return isOpen(row, col) && wuf.connected(DDtoD(row, col), 0);
    }


    public boolean isOpen(int row, int col) {
        assertValid(row, col);
        return grid[row-1][col-1];
    }


    public boolean percolates() {
        return wuf.connected(top, bottom);
    }


    public void open(int row, int col) {
        assertValid(row, col);

        if (isOpen(row, col)) return;
        
        grid[row-1][col-1] = true;
        openCount+=1;


        /**
         * Now we connect the index to adjacent sites.
         * If in the top row, connect to the top node.
         * If in the bottom row, connect to the bottom node.
         */
        int index = DDtoD(row, col);
        if (row == 1) wuf.union(index, top);
        if (row == size) wuf.union(index, bottom);

        /**
         * Put the offsets for the four adjacent rows  
         */
        int[][] offsets = { {-1, 0},
                            { 1, 0},
                            { 0,-1},
                            { 0, 1} };
        
        for(int[] off: offsets) {
            int nRow = row + off[0];
            int nCol = col + off[1];

            /** 
             * If the adjacent site is open and valid, connect them.
             */
            if (isValid(nRow, nCol) && grid[nRow-1][nCol-1]) {
                int nIndex = DDtoD(nRow, nCol);
                wuf.union(nIndex, index);
            } 
        }

    }


    public int getOpenCount() {
        return openCount;
    }
}








