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
     * @param numRow the index of the row to be returned
     * @return the list of numbers in the row
     */
    public ArrayList<Integer> getRow(int numRow) {
        return new ArrayList<>(cells.get(numRow));
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
        clearBoard();

        Deque<Coordinate> path = new ArrayDeque<>();

        // Push a random ordering of coordinates to the unfilled cells
        for (int row = 0; row < 9; row++) {
            int[] randomCols = getRandomNumbers(9);

            for (int i = 0; i < 9; i++)
                path.push(new Coordinate(row, randomCols[i]));
        }

        solvePath(path);
    }

    /**
     * Generates a partially filled sudoku board from a fully filled board. This algorithm ensures that there is only
     * one solution for the puzzle generated. Note that if the board is not filled, an exception will be thrown.
     * @return a sudoku puzzle with one solution
     * @param numbersLeft the amount of numbers we want left on the board
     * @throws IllegalStateException if the board is not full or invalid
     * @throws IllegalArgumentException if the numbers left is too small and a unique puzzle cannot be created
     * or if the numbers left is greater than or equal to 81 (i.e. the user wants to generate a filled board)
     */
    public SudokuBoard generatePuzzle(int numbersLeft) {
        if (!isFull())
            throw new IllegalStateException("Board has not been generated");
        else if (!isValid())
            throw new IllegalStateException("Board is invalid");
        else if (numbersLeft >= 81)
            throw new IllegalArgumentException("Numbers left must be less than 81");

        // We will remove from the copy as to not lose the original (the solution)
        SudokuBoard puzzleBoard = new SudokuBoard(this);
        Random random = new Random();

        /* Construct a list of half of the coordinates such that j >= i for each coordinate
         * This gets the upper triangle such that coordinates above the main diagonal are not counted right now
         * i.e. only values of 1 are counted
         *
         * 1 1 1 1 1 1 1 1 1
         * 0 1 1 1 1 1 1 1 1
         * 0 0 1 1 1 1 1 1 1
         * 0 0 0 1 1 1 1 1 1
         * 0 0 0 0 1 1 1 1 1
         * 0 0 0 0 0 1 1 1 1
         * 0 0 0 0 0 0 1 1 1
         * 0 0 0 0 0 0 0 1 1
         * 0 0 0 0 0 0 0 0 1
         *
         */
        ArrayList<Coordinate> halfCoordinates = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                halfCoordinates.add(new Coordinate(i, j));
            }
        }

        // We will then put each coordinate in a line where for each coordinate that has a symmetric complement (not on
        // the diagonal) the next coordinate in line is that complement (so, (6, 0) is followed by (0, 6), etc.)
        Queue<Coordinate> lineToRemove = new ArrayDeque<>();

        // Add each element to the line
        while (!halfCoordinates.isEmpty()) {
            // Pick a random one
            int index = random.nextInt(halfCoordinates.size());
            Coordinate c = halfCoordinates.get(index);
            halfCoordinates.remove(index);

            lineToRemove.offer(c);
            // If it is not the diagonal, add its complement
            if (c.getRow() != c.getCol())
                lineToRemove.offer(new Coordinate(c.getCol(), c.getRow()));
        }

        // We want to have a certain amount of numbers left on the board (note that if this is too small,
        // an exception will be thrown)
        int numLeft = 81;

        // We want to stop once we have N numbers left on the board
        while (numLeft > numbersLeft) {

            if (lineToRemove.isEmpty())
                throw new IllegalArgumentException("The board must have more numbers left on the board to " +
                        "have a unique solution");

            Coordinate coord = lineToRemove.poll();
            // Remove it and record what number it was
            int cellNum = puzzleBoard.getCell(coord);
            puzzleBoard.setCell(coord, 0);

            // Go through every other number and try to solve it
            boolean broke = false;
            for (int i = 1; i <= 9; i++) {
                if (i != cellNum) {
                    puzzleBoard.setCell(coord, i);
                    // If we are able to solve the puzzle using a different number, we cannot change that cell
                    if (puzzleBoard.solved() != null) {
                        broke = true;
                        break;
                    }
                }
            }

            // If we broke, we want to keep the cell as it is
            if (broke) {
                puzzleBoard.setCell(coord, cellNum);
            } else { // If we didn't break, we want to remove it
                puzzleBoard.setCell(coord, 0);
                numLeft--;
            }
        }

        return puzzleBoard;
    }

    /**
     * Solves the board by the path described using a backtracking algorithm on the path using it as a stack
     * @param path A stack (Deque) holding the path we want to go
     * @throws IllegalStateException if the board is unsolvable
     */
    private void solvePath(Deque<Coordinate> path) {
        Deque<Coordinate> filledCells = new ArrayDeque<>();

        // Fill the puzzle in the predefined order until there are no unfilled cells
        while (!path.isEmpty()) {
            // Set the cell at the top of the stack to be one more than it is (if it is empty it will now be 1)
            setCell(path.peek(), getCell(path.peek()) + 1);

            // If it is a valid cell, push it to the filled cells, removing it from the unfilled cells
            if (isValidCell(path.peek())) {
                filledCells.push(path.pop());
            } else { // If it is not valid
                // Keep backtracking until we find a cell that isn't 9
                while (getCell(path.peek()) == 9) {
                    setCell(path.peek(), 0);
                    // If we have reached the end of possible filledCells, there's nothing else to try
                    if (filledCells.isEmpty())
                        throw new IllegalStateException("Board unsolvable");

                    path.push(filledCells.pop());
                }
            }
        }
    }

    /**
     * Tries to solve the board using a backtracking algorithm.
     * @return the solved board (if possible). Null if the board cannot be solved
     */
    public SudokuBoard solved() {
        if (!isValid())
            return null;

        SudokuBoard boardCopy = new SudokuBoard(this);
        Deque<Coordinate> path = new ArrayDeque<>();

        // Add to the path any cell that is empty
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (getCell(i, j) == 0) {
                    path.push(new Coordinate(i, j));
                }

            }
        }

        try {
            boardCopy.solvePath(path);
            return boardCopy;
        } catch (IllegalStateException ise) { // If we are unable to solve the board
            return null;
        }
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
        ArrayList<Coordinate> contradictions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!isValidCell(i, j))
                    contradictions.add(new Coordinate(i, j));
            }
        }
        return contradictions;
    }

    /**
     * @return if the puzzle does not have any contradictions
     */
    public boolean isValid() {
        // If we find no contradictions, the board must be valid
        return getContradictions().isEmpty();
    }

    /**
     * @return if the puzzle has no empty (0) cells
     */
    public boolean isFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // If we find a zero, the board is not full
                if (getCell(i, j) == 0)
                    return false;
            }
        }
        // If we find no zeros, the board must be full
        return true;
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the row and column specified creates any contradictions
     */
    private boolean isValidCell(int row, int col) {
        // For a cell to be valid, it must either be empty or have no repetitions in column, row, or box
        return getCell(row, col) == 0 || (isValidRow(row, col) && isValidCol(row, col) && isValidBox(row, col));
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
    public void flip() {
        SudokuBoard boardCopy = new SudokuBoard(this);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setCell(i, j, boardCopy.getCell(8 - j, i));
            }
        }
    }

    /**
     * Mirrors a board along the horizontal line such that the top and the bottom mirror each other.
     */
    public void mirror() {
        SudokuBoard boardCopy = new SudokuBoard(this);
        Deque< ArrayList<Integer> > rowStack = new ArrayDeque<>();

        for (int i = 0; i < 9; i++)
            rowStack.push(boardCopy.getRow(i));

        for (int i = 0; i < 9; i++)
            cells.set(i, rowStack.pop());
    }

    /**
     * Gets a list of the numbers 1-9. Note that numbers that have been solved (there are nine of them in the puzzle)
     * will be represented with a -1. So, for a puzzle which has solved all the 2, 4, and 9s, this method would
     * return {1, -1, 3, -1, 5, 6, 7, 8, -1}
     * @return a list of numbers that have not all been placed with -1 designated a solved number
     */
    public ArrayList<Integer> getUnsolvedNums() {
        ArrayList<Integer> unsolved = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int count = 0;
            for (int j = 0; j < 9; j++) {
                if (getRow(j).contains(i+1)) {
                    count++;
                }
            }
            if (!(count == 9)) {
                unsolved.add(i+1);
            } else {
                unsolved.add(-1);
            }
        }
        return unsolved;
    }
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        // We want to have column designators which will just be a line of 1-9 to indicate which is which

        // We need three spaces to start. The for loop adds two spaced between them if they are in different boxes,
        // so we need an extra space at the start
        boardString.append(" ");

        // Add all the numbers 1 - 9 to the top and separate them properly
        for (int i = 0; i < 9; i++) {
            // If the number is in a new box, add an extra space
            if (i % 3 == 0)
                boardString.append("  ");

            boardString.append(i + 1);
            boardString.append(" ");

        }
        // Enclose the box with a top line
        boardString.append("\n   ---------------------\n");

        // For every row
        for (int i = 0; i < 9; i++) {
            // The start of each row will be the row indicator followed by the side of the box
            boardString.append(i + 1);
            boardString.append(" |");

            // We need 3 iterations of numbers 0 0 0 | 0 0 0 | 0 0 0
            for (int j = 0; j < 3; j++) {
                // For every 3 numbers
                for (int k = 0; k < 3; k++) {
                    int cellNum = getCell(i, j * 3 + k);

                    // If the cell is empty, put a dash
                    if (cellNum == 0)
                        boardString.append("-");
                    else // Otherwise append the number
                        boardString.append(cellNum);

                    // Also if it is not the last number, add a space so the numbers are spaced out a bit
                    if (j * 3 + k < 8)
                        boardString.append(" ");
                }

                // If it is not the last set, we want a separator
                if (j < 2)
                    boardString.append("| ");
                else // If it is the last set, enclose the box on the left and add a newline
                    boardString.append("|\n");
            }
            // If it is row index 2 or 5, we want a separator
            if (i == 2 || i == 5)
                boardString.append("  |---------------------|\n");
        }

        boardString.append("   ---------------------\n     "); // Enclose the box on the bottom with a line

        ArrayList<Integer> unsolved = getUnsolvedNums();
        for (int i = 0; i < unsolved.size(); i++) {
            if (unsolved.get(i) == i+1) {
                boardString.append(i+1).append(" ");
            } else {
                boardString.append("  ");
            }
        }

        return boardString.toString();
    }
}
