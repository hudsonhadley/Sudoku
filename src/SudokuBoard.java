import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
        // TODO: Complete method
        return -1;
    }

    /**
     * @param row the row of the cell we want to set
     * @param col the column of the cell we want to set
     * @param cell the cell number we want to set
     * @throws IndexOutOfBoundsException if row or col is out of bounds
     * @throws IllegalArgumentException if cell is not 0-9
     */
    public void setCell(int row, int col, int cell) {
        // TODO: Complete method
    }

    /**
     * @param coord the coordinate of the cell we want to set
     * @param cell the cell number we want to set
     * @throws IndexOutOfBoundsException if the coordinate is out of bounds
     * @throws IllegalArgumentException if cell is not 0-9
     */
    public void setCell(Coordinate coord, int cell) {
        // TODO: Complete method
    }

    /**
     * Clears the current board, setting each cell to be 0.
     */
    private void clearBoard() {
        // TODO: Complete method
    }

    /**
     * Generates a filled, valid sudoku board. This algorithm works by
     * going row by row in order, but randomizing the order in which it fills specific rows while
     * utilizing backtracking. This randomizing occurs to make each call of generateBoard()
     * produce a seemingly random board.
     */
    public void generateBoard() {
        // TODO: Complete method
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
     */
    private int[] getRandomNumbers(int n) {
        // TODO: Complete method
        return null;
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
        // TODO: Complete method
        return false;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions
     */
    private boolean isValidCell(Coordinate coord) {
        // TODO: Complete method
        return false;
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its row
     */
    private boolean isValidRow(int row, int col) {
        // TODO: Complete method
        return false;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its row
     */
    private boolean isValidRow(Coordinate coord) {
        // TODO: Complete method
        return false;
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its column
     */
    private boolean isValidCol(int row, int col) {
        // TODO: Complete method
        return false;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its column
     */
    private boolean isValidCol(Coordinate coord) {
        // TODO: Complete method
        return false;
    }

    /**
     * @param row the row of the cell we want to check
     * @param col the column of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its box
     */
    private boolean isValidBox(int row, int col) {
        // TODO: Complete method
        return false;
    }

    /**
     * @param coord the coordinate of the cell we want to check
     * @return if the cell at the specified coordinate creates any contradictions in its box
     */
    private boolean isValidBox(Coordinate coord) {
        // TODO: Complete method
        return false;
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    boardString.append(getCell(i, j));
                    boardString.append(" ");
                }
                if (j != 2)
                    boardString.append("| ");
                else
                    boardString.append("\n");
            }
            if (i == 2 || i == 5)
                boardString.append("---------------------\n");
        }
        return boardString.toString();
    }
}
