public class Grid {
    private static Grid uniqueInstance;
    private Templates templates;

    private boolean[][] grid;
    private boolean running, infinite, inverted;
    private int width, height;
    private int scale, generation, rowSize, colSize, cellSize, cellInnerGap, cellOuterGap;

    public Grid(int gridWidth, int gridHeight, int gridScale){
        scale = gridScale;
        width = gridWidth;
        height = gridHeight;

        generation = 0;
        running = false;
        infinite = false;
        inverted = false;

        initialiseGrid(scale);
    }

    public static Grid getInstance() {
        if(uniqueInstance == null){
            uniqueInstance = new Grid(1200,700,2);
        }
        return uniqueInstance;
    }

    public void initialiseGrid(int scale) {
        cellSize = 10 / scale;
        cellInnerGap = 1;
        cellOuterGap = 10;

        rowSize = (height / (cellSize + cellInnerGap)) - (cellOuterGap / cellSize + cellInnerGap);
        colSize = (width / (cellSize + cellInnerGap)) - (cellOuterGap / cellSize + cellInnerGap);

        grid = resetGrid();
    }

    public boolean[][] resetGrid() {
        grid = new boolean[rowSize][colSize];
        for(int r = 0; r < rowSize; r++){
            for(int c = 0; c < colSize; c++){
                grid[r][c] = false;
            }
        }
        generation = 0;
        return grid;
    }

    public void loadArray(Boolean center, Boolean[][] arrayGrid) {
        int xCord = 10,yCord = 10;
        resetGrid();

        if(center){
            xCord = (getRowSize() / 2) - (arrayGrid.length / 2);
            yCord = (getColSize() / 2) - (arrayGrid[0].length / 2);
        }

        for(int r = xCord; r < (xCord + arrayGrid.length); r++) {
            for(int c = yCord; c < (yCord + arrayGrid[0].length); c++) {
                grid[r][c] = arrayGrid[(r - xCord)][(c - yCord)];
            }
        }
    }

    public void nextGeneration() {
        boolean[][] newGrid = new boolean[getRowSize()][getColSize()];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {

                int liveNeighbours = 0;
                for (int r = row - 1; r < row + 2; r++) {
                    for (int c = col - 1; c < col + 2; c++) {
                        try {
                            if (grid[r][c] && !(r == row && c == col)) {
                                liveNeighbours++;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            if(isInfinite()){
                                int newR = r;
                                int newC = c;
                                if (r < 0) {
                                    newR = getRowSize() - 1;
                                } else if (r >= getRowSize()) {
                                    newR = 0;
                                }

                                if (c < 0) {
                                    newC = getColSize() - 1;
                                } else if (c >= getColSize()) {
                                    newC = 0;
                                }

                                if (grid[newR][newC]) {
                                    liveNeighbours++;
                                }
                            }
                        }
                    }
                }

                if (grid[row][col]) {
                    if (liveNeighbours < 2) {
                        newGrid[row][col] = false;
                    } else if (liveNeighbours > 3) {
                        newGrid[row][col] = false;
                    } else {
                        newGrid[row][col] = true;
                    }
                } else {
                    if (liveNeighbours == 3) {
                        newGrid[row][col] = true;
                    }
                }
            }
        }
        increaseGeneration();
        setGrid(newGrid);
    }

    public void setScale(int newScale)
    {
        scale = newScale;
        initialiseGrid(scale);
    }
    public int getScale() { return scale; }

    public void setCell(int row, int col, Boolean state) { grid[row][col] = state; }

    public void setRunning(boolean state) { running = state; }
    public boolean isRunning() { return running; }

    public void setInfinite(boolean state) { infinite = state; }
    public boolean isInfinite() { return infinite; }

    public void setInverted(boolean state) { inverted = state; }
    public boolean isInverted() { return inverted; }

    public void increaseGeneration() { generation++; }
    public int getGeneration() { return generation; }

    public void setGrid(boolean[][] newGrid) { grid = newGrid; }
    public boolean[][] getGrid() { return grid; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public int getCellSize() { return cellSize; }
    public int getRowSize() { return rowSize; }
    public int getColSize() { return colSize; }

    public int getCellInnerGap() { return cellInnerGap; }
    public int getCellOuterGap() { return cellOuterGap; }
}
