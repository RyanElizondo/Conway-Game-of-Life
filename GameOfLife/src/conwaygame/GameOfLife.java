package conwaygame;
import java.util.ArrayList;
import java.util.List;

/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
        int row = StdIn.readInt();
        int col = StdIn.readInt();

        grid = new boolean[row][col];

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = StdIn.readBoolean();
            }
        }

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        if(grid[row][col] == true){
            return true;
        }
        else{
            return false;
        }
    }
    

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
        int rows = grid.length;
        int cols = grid[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(grid[i][j] == true){
                    return true;
                }
            }
        }

        return false;

    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        // WRITE YOUR CODE HERE
        int numRows[] = new int[8];
        int numCols[] = new int[8];
        int maxRows = grid.length;
        int maxCols = grid[0].length;
        int numAlive = 0;
        for(int i = 0; i < 8; i++){
            if(i == 0){ //top left diagnol
                numRows[i] = row - 1;
                numCols[i] = col - 1;
            }
            if(i == 1){ //top center
                numRows[i] = row - 1;
                numCols[i] = col;

            }
            if(i == 2){ //top right diagnol
                numRows[i] = row - 1;
                numCols[i] = col + 1;

            }
            if(i == 3){ //left 
                numRows[i] = row;
                numCols[i] = col - 1;

            }
            if(i == 4){ //right 
                numRows[i] = row;
                numCols[i] = col + 1;

            }
            if(i == 5){ //bottom left diagnol
                numRows[i] = row + 1;
                numCols[i] = col - 1;

            }
            if(i == 6){//bottom center
                numRows[i] = row + 1;
                numCols[i] = col;

            }
            if(i == 7){ //bottom right diagnol
                numRows[i] = row + 1;
                numCols[i] = col +1;

            }
            if(numRows[i] < 0 ){ //if at row 1 for example it will jump to row 3
                numRows[i] = maxRows-1;
            }
            if(numRows[i] == maxRows){ //it will jump to 0 if at the largest row
                numRows[i] = 0;
            }
            if(numCols[i] < 0){ //same idea as rows
                numCols[i] = maxCols-1;
            }
            if(numCols[i] == maxCols){ //same idea as cols
                numCols[i] = 0;
            }
            if(grid[numRows[i]][numCols[i]] == true){ //tests if the niegbors are alive
                numAlive++;
            }
            
        }

        return numAlive; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        // WRITE YOUR CODE HERE
        int rows = grid.length;
        int cols = grid[0].length;
        int aliveNeighbors;
        boolean[][] newgrid = new boolean[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                aliveNeighbors = numOfAliveNeighbors(i, j);

                //rule 1
                if((grid[i][j] == true) && (aliveNeighbors == 0 || aliveNeighbors == 1)){
                    newgrid[i][j] = false;
                }
                //rule 2
                else if((grid[i][j] == false) && (aliveNeighbors == 3)){
                    newgrid[i][j] = true;
                }
                //rule 3
                else if((grid[i][j] == true) && (aliveNeighbors == 2 || aliveNeighbors == 3)){
                    newgrid[i][j] = true;
                }
                //rule 4
                else if((grid[i][j] == true) && (aliveNeighbors >= 4)){
                    newgrid[i][j] = false;
                }
            }
        }

        return newgrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        grid = computeNewGrid();
        totalAliveCells = getTotalAliveCells();
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for(int i = 0; i < n; i++){
            grid = computeNewGrid();
        }
        
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        // WRITE YOUR CODE HERE
        WeightedQuickUnionUF unionFind = new WeightedQuickUnionUF(grid.length, grid[0].length);
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
              if(grid[i][j] == true){
                int topRow = i - 1;
                int bottomRow = i + 1;
                int leftCol = j - 1;
                int rightCol = j+1;

                if(topRow == -1){
                    topRow = grid.length - 1;
                }
                if(bottomRow == grid.length){
                    bottomRow = 0;
                }
                if(leftCol == -1){
                    leftCol = grid[0].length-1;
                }
                if(rightCol==grid.length){
                    rightCol = 0;
                }
                if(grid[topRow][leftCol]){
                    unionFind.union(i,j,topRow,leftCol);
                }
                if(grid[i][leftCol]){
                    unionFind.union(i, j, i, leftCol);
                }
                if(grid[bottomRow][leftCol]){
                    unionFind.union(i, j, bottomRow, leftCol);
                }
                if(grid[topRow][j]){
                    unionFind.union(i, j, topRow, j);
                }
                if(grid[bottomRow][j]){
                    unionFind.union(i, j, bottomRow, j);
                }
                if(grid[topRow][rightCol]){
                    unionFind.union(i, j, topRow, rightCol);
                }
                if(grid[i][rightCol]){
                    unionFind.union(i, j, i, rightCol);
                }
                if(grid[bottomRow][rightCol]){
                    unionFind.union(i, j, bottomRow, rightCol);
                }
              } 
            }
        }
        int communities = 0;
        //ArrayList<Integer> array = new ArrayList<Integer>();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j]){
                    if(numOfAliveNeighbors(i, j) == 0){
                        communities++;
                    }
                    else if(numOfAliveNeighbors(i, j) > 0){
                        if(unionFind.find(i, j) == (i*grid.length + j)){
                            communities++;
                        }
                    }
                }
            }
        }
        return communities; // update this line, provided so that code compiles
    }
}
