package ascii_art.new_exceptions;

/**
 * Exception thrown when an invalid command is encountered in the ASCII art application.
 */
public class InvalidCommandException extends AsciiArtException {
    public static final String DEFAULT_MESSAGE = "Did not execute due to incorrect command.";

    /**
     * Constructs a new InvalidCommandException with the default message.
     */
    public InvalidCommandException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new InvalidCommandException with a specified message.
     *
     * @param message the detail message
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}