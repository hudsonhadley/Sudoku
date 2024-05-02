import java.util.Scanner;

/**
 * A rudimentary command line user interface. The command line offers the user a puzzle and allows them to guess. If
 * their guess is incorrect, they are told they were wrong.
 * @author Nathan Moore
 * @author Hudson Hadley
 */
public class CommandLineUI {
    /**
     * Converts milliseconds into a printable String with clock time hh:mm:ss (note that hh can be more than 24)
     * @param milliseconds the amount of milliseconds we want to convert
     * @return the time in hh:mm:ss format
     * @throws IllegalArgumentException if a negative number is entered
     */
    public static String millisToClock(long milliseconds) throws IllegalArgumentException {
        if (milliseconds < 0)
            throw new IllegalArgumentException("Time must be non-negative");

        int seconds = (int) (milliseconds / 1000); // Divide to get seconds and cut it off at the decimal

        int minutes = seconds / 60;
        seconds %= 60;

        int hours = minutes / 60;
        minutes %= 60;

        // Make sure we fill in 0's before hours numbers if they need it
        StringBuilder hourBuilder = new StringBuilder();
        hourBuilder.append(hours);

        while (hourBuilder.length() < 2)
            hourBuilder.insert(0, "0");

        // Fill in minutes
        StringBuilder minuteBuilder = new StringBuilder();
        minuteBuilder.append(minutes);

        while (minuteBuilder.length() < 2)
            minuteBuilder.insert(0, "0");

        // Fill in seconds
        StringBuilder secondBuilder = new StringBuilder();
        secondBuilder.append(seconds);

        while (secondBuilder.length() < 2)
            secondBuilder.insert(0, "0");

        // Format the string properly
        return String.format("%s:%s:%s", hourBuilder, minuteBuilder, secondBuilder);
    }
    public static void main(String[] args) {
        // TODO: Complete method
        boolean game = true;
        int incorrect = 0;
        SudokuPuzzle board = new SudokuPuzzle(30);
        System.out.println(board.toString());
        System.out.println(board.solveToString());

        Scanner scan = new Scanner(System.in);

        long start = System.currentTimeMillis();

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

        long end = System.currentTimeMillis();

        System.out.printf("Time: %s", millisToClock(end - start));
    }
}
