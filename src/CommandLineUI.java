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
        int incorrect = 0;
        SudokuPuzzle board = new SudokuPuzzle(30);
        System.out.println(board.toString());

        Scanner scan = new Scanner(System.in);

        while (game) {
            System.out.print("Row Guess: ");
            int rowGuess = scan.nextInt();
            System.out.print("Column Guess: ");
            int colGuess = scan.nextInt();
            System.out.print("Number Guess: ");
            int numGuess = scan.nextInt();

            Coordinate playerGuess = new Coordinate(rowGuess, colGuess);

            if (!board.guess(rowGuess-1, colGuess-1, numGuess)) {
                System.out.println("Incorrect guess");
                incorrect++;
                System.out.println(board.toString());
                if (incorrect == 3) {
                    System.out.println("3 incorrect guesses. Game over!");
                    break;
                }
                continue;
            } else {
                System.out.println("Correct guess");
                System.out.println(board.toString());
            }

            if (board.isSolved()) {
                game = false;
            }

        }


    }
}
