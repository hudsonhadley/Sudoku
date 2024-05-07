import java.util.Random;

/**
 * This class represents a sudoku puzzle. Each puzzle has a corresponding solution. In other words, each class has
 * what the user is given, and what the correct answer is. When a user guesses, their attempt is then measured
 * against the solution (which should have only one solution).
 * @author Nathan Moore
 * @author Hudson Hadley
 */
public class SudokuPuzzle {
    /**
     * An uncompleted sudoku board. This puzzle corresponds to the other member representing the solution. (This should
     * only have one solution).
     */
    private SudokuBoard puzzle;

    /**
     * The solution to the other member representing the unfilled version of the board. Guesses will be compared against
     * this board.
     */
    private SudokuBoard solution;

    /**
     * Constructs a sudoku puzzle with a single solution.
     * @param numbersLeft the amount of numbers we want left on the board
     * @throws IllegalArgumentException if the numbers left is too small, generatePuzzle will throw an error
     */
    public SudokuPuzzle(int numbersLeft) throws IllegalArgumentException {
        solution = new SudokuBoard();
        solution.generateBoard();
        puzzle = solution.generatePuzzle(numbersLeft);

        // Flip and mirror stuff around to make it as random as possible
        Random random = new Random();

        int mirrorCounts = random.nextInt(2);
        int flipCounts = random.nextInt(4);

        for (int i = 0; i < mirrorCounts; i++)
            mirror();
        for (int i = 0; i < flipCounts; i++)
            flip();
    }

    /**
     * Constructs a sudoku puzzle from a given solution and puzzle.
     * @param puzzle the unfilled valid board
     * @param solution the filled, valid board to make a puzzle from
     * @throws IllegalArgumentException if the solution is invalid or not filled completely, or if the puzzle and
     * solution do not match
     */
    public SudokuPuzzle(SudokuBoard puzzle, SudokuBoard solution) throws IllegalArgumentException {
        // Verify the solution and puzzle is valid and the solution is full
        if (!solution.isFull() || !solution.isValid() || !puzzle.isValid())
            throw new IllegalArgumentException("Solution is not full or solution is invalid");

        // Verify that the solution and puzzle match if they have an entry
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // If an entry differs and the puzzle entry is not just empty, throw an exception
                if (solution.getCell(i, j) != puzzle.getCell(i, j) && puzzle.getCell(i, j) != 0)
                    throw new IllegalArgumentException("Puzzle and solution do not match");
            }
        }

        this.puzzle = new SudokuBoard(puzzle);
        this.solution = new SudokuBoard(solution);
    }

    /**
     * Guesses a cell is a certain number. Guesses are compared against the solution to see if the guess is correct.
     * Only correct guesses (those that return true) will be placed onto the puzzle.
     * @param row the row of the cell we want to guess
     * @param col the column of the cell we want to guess
     * @param cell the cell number we want to guess
     * @return if the guess was correct or not
     * @throws IndexOutOfBoundsException if the row or column is out of bounds
     * @throws IllegalArgumentException if the cell is already full, or if the cell is not 1-9
     */
    public boolean guess(int row, int col, int cell) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (solution.getCell(row, col) == cell) {
            puzzle.setCell(row, col, cell);
            return true;
        }
        return false;
    }

    /**
     * @param row the row of the cell to be guessed
     * @param col the column of the cell to be guessed
     * @return if the guess is valid (if the cell is empty)
     */
    public boolean validGuess(int row, int col) throws IndexOutOfBoundsException, IllegalArgumentException {
        return puzzle.getCell(row, col) == 0;
    }
    /**
     * @return if the puzzle has been solved
     */
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(puzzle.getCell(i, j) == solution.getCell(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Flips the puzzle and solution clockwise by 90 degrees.
     */
    private void flip() {
        solution.flip();
        puzzle.flip();
    }

    /**
     * Mirrors the puzzle and solution horizontally.
     */
    private void mirror() {
        solution.mirror();
        puzzle.mirror();
    }

    /**
     * @return a String representation of the board with columns separated by spaces and rows by newlines
     */
    @Override
    public String toString() {
        return puzzle.toString();
    }

    /**
     * @return a String representation of the solution
     */
    public String solveToString() {
        return solution.toString();
    }
}
