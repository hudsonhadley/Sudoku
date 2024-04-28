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
     */
    public SudokuPuzzle() {
        // TODO: Complete method
    }

    /**
     * Constructs a sudoku puzzle from a given solution and puzzle.
     * @param puzzle the unfilled valid board
     * @param solution the filled, valid board to make a puzzle from
     * @throws IllegalArgumentException if the solution is invalid or not filled completely, or if the puzzle and
     * solution do not match
     */
    public SudokuPuzzle(SudokuBoard puzzle, SudokuBoard solution) throws IllegalArgumentException {
        // TODO: Complete method
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
        // TODO: Complete method
        return false;
    }

    /**
     * @return if the puzzle has been solved
     */
    public boolean isSolved() {
        // TODO: Complete method
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
     * @return a String representation of the board with columns separated by spaces and rows by newlines
     */
    @Override
    public String toString() {
        // TODO: Complete method
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 3; k++) {
                    boardString.append(puzzle.getCell(i, j));
                }
                boardString.append("|");
            }
            boardString.append("-----------");
        }
        return boardString.toString();
    }
}
