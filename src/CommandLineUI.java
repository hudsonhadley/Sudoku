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
        SudokuPuzzle board = new SudokuPuzzle(30);
        System.out.println(board.toString());

        Scanner scan = new Scanner(System.in);

        while (game) {
            System.out.println(board.toString());
            System.out.print("Row Guess: ");
            int rowGuess = scan.nextInt();
            System.out.print("Column Guess: ");
            int colGuess = scan.nextInt();
            System.out.print("Number Guess: ");
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
