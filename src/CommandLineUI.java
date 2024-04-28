import java.util.Scanner;

/**
 * A rudimentary command line user interface. The command line offers the user a puzzle and allows them to guess. If
 * their guess is incorrect, they are told they were wrong.
 * @author Nathan Moore
 * @author Hudson Hadley
 */
public class CommandLineUI {
    public static void main(String[] args) {
        // TODO: Complete method
        boolean game = true;
        SudokuPuzzle board = new SudokuPuzzle();
        System.out.println(board.puzzle.toString());

        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("");

        while (game) {
            System.out.println(board.toString());
            System.out.print("Guess: ");
            int rowGuess = scan.nextInt();
            int colGuess = scan.nextInt();
            int numGuess = scan.nextInt();

            Coordinate playerGuess = new Coordinate(rowGuess, colGuess);

            if (!board.guess(rowGuess, colGuess, numGuess)) {
                System.out.println("Incorrect guess");
                continue;
            } else {
                System.out.println("Correct guess");
            }

            if (board.isSolved()) {
                game = false;
            }

        }


    }
}
