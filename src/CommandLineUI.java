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

    /**
     * Gets input from the Scanner that is between bounds. The method also prints out a message
     * after each attempt.
     * @param scanner the Scanner used for input
     * @param message the message that will be printed
     * @param min the minimum bound a number can be (inclusive)
     * @param max the maximum bound a number can be (inclusive)
     * @return an integer the user has entered between min and max inclusive
     * @throws IllegalArgumentException if min is greater than max
     */
    public static int getNumber(Scanner scanner, String message, int min, int max) throws IllegalArgumentException {
        if (min > max)
            throw new IllegalArgumentException("Min must be less than or equal to max");

        int num;

        while (true) {
            System.out.print(message);
            try {
                num = Integer.parseInt(scanner.nextLine());

                if (min <= num && num <= max) {
                    break;
                } else {
                    System.out.printf("Enter a number %d - %d\n", min, max);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        }

        return num;
    }

    public static void main(String[] args) {
        boolean game = true; //game loop variable
        int incorrect = 0; //how many incorrect guesses the user has made
        Scanner scan = new Scanner(System.in);

        int[] levels = new int[]{35, 30, 36, 22}; // The numbers left corresponding to each difficulty
        int difficulty = getNumber(scan,
                "Enter difficulty level (1 = easy, 2 = medium, 3 = hard, 4 = impossible): ", 1, 4);


        // No matter the difficulty, there is a possibility the board generator will create a puzzle which cannot
        // have whatever amount of numbers left. So we need to put this in a while true block and keep trying
        // until we successfully make a board
        System.out.println("\nGenerating puzzle...");
        SudokuPuzzle board;
        while (true) {
            try {
                board = new SudokuPuzzle(levels[difficulty - 1]);
                break;
            } catch (IllegalArgumentException ignored) {}
        }

        // For debugging
        //System.out.println(board.solveToString());


        long start = System.currentTimeMillis();

        while (game) { //the game loop that prints the board, takes in a player guess, and turns it into a cell coordinate if valid
            System.out.println(board); //prints the board

            int rowGuess = getNumber(scan, "Row Guess: ", 1, 9);
            int colGuess = getNumber(scan, "Column Guess: ", 1, 9);
            int numGuess = getNumber(scan, "Number Guess: ", 1, 9);

            if (!board.validGuess(rowGuess - 1, colGuess - 1)) { //checks if the cell guessed is empty
                System.out.println("\nCell is already filled!\n");
            } else if (!board.guess(rowGuess - 1, colGuess - 1, numGuess)) { //if the guess is incorrect
                System.out.println("\nIncorrect guess\n");
                incorrect++;

                if (incorrect == 3) { //end the game if the player has 3 incorrect guesses
                    System.out.println("3 incorrect guesses. Game over!");
                    System.exit(0); // Quit the game
                }

            } else {
                System.out.println("\nCorrect guess\n");
            }

            if (board.isSolved()) { //ends game if the board is full
                game = false;
            }
        }

        long end = System.currentTimeMillis();

        System.out.println(board);
        System.out.printf("Time: %s\n", millisToClock(end - start));
        System.out.println("Incorrect guesses: " + incorrect);
    }
}
