import java.util.*;

/**
 * This class represents a Sudoku board (solved or unsolved, valid or invalid). This class includes all the
 * logic that come with Sudoku boards such as seeing if the board is valid or if the board is full. The board
 * in this class is represented with a 2D ArrayList. As such, the Coordinate class is used sometimes to make
 * setting and getting cells easier both for the user and reader.
 * @see Coordinate
 * @author Nathan Moore
 * @author Hudson Hadley
 */
public class SudokuBoard {
    /**
     * A 2D ArrayList representing the board.
     */
    private ArrayList<ArrayList<Integer>> cells;

    /**
     * A default constructor initializing an empty sudoku board. In practicality, this means setting each
     * cell to be 0, which will be our indicator of an empty cell.
     */
    public SudokuBoard() {
        cells = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(0);
            }

            cells.add(row);
        }
    }

    /**
     * A constructor which initializes a sudoku board from a string representation of a board.
     * @param board a String representation of the board with columns separated with spaces and rows separated
     *              with newlines
     * @throws IllegalArgumentException if the board is improperly formatted
     */
    public SudokuBoard(String board) throws IllegalArgumentException {
        /* Board will look like:

        0 0 0 | 0 0 0 | 0 0 0
        0 0 0 | 0 0 0 | 0 0 0
        0 0 0 | 0 0 0 | 0 0 0
        ---------------------
        0 0 0 | 0 0 0 | 0 0 0
        0 0 0 | 0 0 0 | 0 0 0
        0 0 0 | 0 0 0 | 0 0 0
        ---------------------
        0 0 0 | 0 0 0 | 0 0 0
        0 0 0 | 0 0 0 | 0 0 0
        0 0 0 | 0 0 0 | 0 0 0

         */
        cells = new ArrayList<>();

        Scanner boardScanner = new Scanner(board);

        // Go row by row
        for (int i = 0; i < 11; i++) {
            Scanner rowScanner = new Scanner(boardScanner.nextLine());

            if (i != 3 && i != 7) { // At rows 3 and 7, it should be a row of '-'s
                // Each row will be 0 0 0 | 0 0 0 | 0 0 0
                // We need to parse through the ints and the non ints

                ArrayList<Integer> row = new ArrayList<>();

                // Each row will have 3 numbers and then a non int 3 + 1 + 3 + 1 + 3 = 11
                for (int j = 0; j < 11; j++) {
                    String nextString = rowScanner.next();

                    if (j != 3 && j != 7) { // at indices 3 and 7, the character should be |
                        try {
                            row.add(Integer.parseInt(nextString));
                        } catch (NumberFormatException nfe) {
                            throw new IllegalArgumentException("Improper formatting");
                        }
                    }
                }

                cells.add(row);
            }
        }
    }

    /**
     * Copies a SudokuBoard making a deep copy.
     * @param other another SudokuBoard we want to copy
     */
    public SudokuBoard(SudokuBoard other) {
        cells = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(other.getCell(i, j));
            }

            cells.add(row);
        }
    }

    /**
     * @param row the row of the cell number we want
     * @param col the column of the cell number we want
     * @return the cell number at the row and column in the board
     * @throws IndexOutOfBoundsException if row or col is not 0-8
     */
    public int getCell(int row, int col) {
        if (row < 0 || 8 < row || col < 0 || 8 < col)
            throw new IndexOutOfBoundsException("Invalid row or col");
        return cells.get(row).get(col);
    }

    /**
     * @param coord the coordinate of the cell number we want
     * @return the cell number at the coordinate in the board
     * @throws IndexOutOfBoundsException if the coordinate is out of bounds
     */
    public int getCell(Coordinate coord) {
        return getCell(coord.getRow(), coord.getCol());
    }

    /**
     * @param row the row of the cell we want to set
     * @param col the column of the cell we want to set
     * @param cell the cell number we want to set
     * @throws IndexOutOfBoundsException if row or col is out of bounds
     * @throws IllegalArgumentException if cell is not 0-9
     */
    public void setCell(int row, int col, int cell) {
        cells.get(row).set(col, cell);
    }

    /**
     * @param coord the coordinate of the cell we want to set
     * @param cell the cell number we want to set
     * @throws IndexOutOfBoundsException if the coordinate is out of bounds
     * @throws IllegalArgumentException if cell is not 0-9
     */
    public void setCell(Coordinate coord, int cell) {
        setCell(coord.getRow(), coord.getCol(), cell);
    }

    /**
     * Clears the current board, setting each cell to be 0.
     */
    private void clearBoard() {
        // Set each cell to be 0
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setCell(i, j, 0);
            }
        }
    }

    /**
     * Generates a filled, valid sudoku board. This algorithm works by
     * going row by row in order, but randomizing the order in which it fills specific rows while
     * utilizing backtracking. This randomizing occurs to make each call of generateBoard()
     * produce a seemingly random board.
     */
    public void generateBoard() {
        // TODO: Complete method
        clearBoard();

        Deque<Coordinate> unfilledCells = new ArrayDeque<>();
        Deque<Coordinate> filledCells = new ArrayDeque<>();

        // Push a random ordering of coordinates to the unfilled cells
        for (int row = 0; row < 9; row++) {
            int[] randomCols = getRandomNumbers(9);

            for (int i = 0; i < 9; i++)
                unfilledCells.push(new Coordinate(row, randomCols[i]));
        }

        // Fill the puzzle in the predefined order until there are no unfilled cells
        while (!unfilledCells.isEmpty()) {
            // Set the cell at the top of the stack to be one more than it is (if it is empty it will now be 1)
            setCell(unfilledCells.peek(), getCell(unfilledCells.peek()) + 1);

            // If it is a valid cell, push it to the filled cells, removing it from the unfilled cells
            if (isValidCell(unfilledCells.peek())) {
                filledCells.push(unfilledCells.pop());
            } else { // If it is not valid
                // Keep backtracking until we find a cell that isn't 9
                while (getCell(unfilledCells.peek()) == 9) {
                    setCell(unfilledCells.peek(), 0);
                    unfilledCells.push(filledCells.pop());
                }
            }
        }
    }

    /**
     * Generates a partially filled sudoku board from a fully filled board. This algorithm ensures that there is only
     * one solution for the puzzle generated. Note that if the board is not filled, an entirely new board will be
     * created and a puzzle will be generated from it.
     * @return a sudoku puzzle with one solution
     */
    public SudokuBoard generatePuzzle() {
        // TODO: Complete method
        return null;
    }

    /**
     * Tries to solve the board using a backtracking algorithm.
     * @return the solved board (if possible). Null if the board cannot be solved
     */
    private SudokuBoard solved() {
        return null;
    }

    /**
     * Generates an int array that is a certain length with numbers 0 - (n - 1) randomly placed throughout
     * @param n the amount of random numbers we want
     * @return an int array that is n long with randomly placed numbers 0 - (n - 1) placed throughout
     * @throws IllegalArgumentException if n is less than 1
     */
    private int[] getRandomNumbers(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be positive");
        Random random = new Random();

        // We need a list of number 0 - (n - 1) to draw numbers from
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < n; i++)
            numbers.add(i);

        int[] randomNumbers = new int[n];
        // Pick a random index and remove it, adding it to the array
        for (int i = 0; i < n; i++) {
            int index = random.nextInt(numbers.size());
            randomNumbers[i] = numbers.get(index);
            numbers.remove(index);
        }

        return randomNumbers;
    }

    /**
     * Generates a list of Coordinates in the current board that have a contradiction. The contradiction may arise
     * in the row, column, or box.
     * @return an ArrayList of Coordinates representing the locations of contradictions in the puzzle
     */
    public ArrayList<Coordinate> getContradictions() {
        // TODO: Complete method
        return null;
    }

    /**
     * @return if the puzzle does not have any contradictions
     */
    public boolean isValid() {
        // TODO: Complete method
        return false;
    }

    /**
     * @return if the puzzle has no empty (0) cells
     */
    public boolean isFull() {
        // TODO: Complete method
        return false;
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the row and column specified creates any contradictions
     */
    private boolean isValidCell(int row, int col) {
        return isValidRow(row, col) && isValidCol(row, col) && isValidBox(row, col);
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions
     */
    private boolean isValidCell(Coordinate coord) {
        return isValidCell(coord.getRow(), coord.getCol());
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its row
     */
    private boolean isValidRow(int row, int col) {
        int cellNum = getCell(row, col);

        // Go through all the columns
        for (int i = 0; i < 9; i++)
            // If the column isn't the one we're checking, and it has the same number, it is not valid
            if (i != col && getCell(row, i) == cellNum)
                return false;

        return true;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its row
     */
    private boolean isValidRow(Coordinate coord) {
        return isValidRow(coord.getRow(), coord.getCol());
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its column
     */
    private boolean isValidCol(int row, int col) {
        int cellNum = getCell(row, col);

        // Go through all the rows
        for (int i = 0; i < 9; i++)
            // If the row isn't the one we're checking, and it has the same number, it is not valid
            if (i != row && getCell(i, col) == cellNum)
                return false;

        return true;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its column
     */
    private boolean isValidCol(Coordinate coord) {
        return isValidCol(coord.getRow(), coord.getCol());
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its box
     */
    private boolean isValidBox(int row, int col) {
        int cellNum = getCell(row, col);

        int boxRow = row / 3; // the box row will be 0 - 2
        int boxCol = col / 3; // the box col will be 0 - 2

        // Check the surrounding box of the cell
        for (int i = boxRow * 3; i < boxRow * 3 + 3; i++) {
            for (int j = boxCol * 3; j < boxCol * 3 + 3; j++) {
                // If it is not the cell we are checking, and it has the same number, it is not valid
                if (i != row && j != col && getCell(i, j) == cellNum)
                    return false;
            }
        }

        return true;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its box
     */
    private boolean isValidBox(Coordinate coord) {
        return isValidBox(coord.getRow(), coord.getCol());
    }

    /**
     * Flips a board clockwise by 90 degrees.
     */
    private void flip() {

    }

    /**
     * Mirrors a board along the horizontal line such that the top and the bottom mirror each other.
     */
    private void mirror() {

    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        // For every row
        for (int i = 0; i < 9; i++) {
            // We need 3 iterations of numbers 0 0 0 | 0 0 0 | 0 0 0
            for (int j = 0; j < 3; j++) {
                // For every 3 numbers
                for (int k = 0; k < 3; k++) {
                    boardString.append(getCell(i, j * 3 + k)); // Append the next number and a space
                    boardString.append(" ");
                }
                // If it is not the last set, we want a separator
                if (j != 2)
                    boardString.append("| ");
                else // If it is the last set, we want a newline
                    boardString.append("\n");
            }
            // If it is row index 2 or 5, we want a separator
            if (i == 2 || i == 5)
                boardString.append("---------------------\n");
        }

        return boardString.toString();
    }
}
