package ascii_art.new_exceptions;

/**
 * Exception thrown when an invalid command is encountered in the ASCII art
 * application.
 */
public class ExecutionCommandException extends AsciiArtException {
    public static final String DEFAULT_MESSAGE = "Did not execute due to " +
            "incorrect command.";
    public static final String CHARSET_TOO_SMALL = "Did not execute. Charset" +
            " is too small.";

    /**
     * Constructs a new exception with charset too small message, or a
     * default message.
     *
     * @param isCharsetTooSmall true if the charset is too small, false
     *                          otherwise
     */
    public ExecutionCommandException(boolean isCharsetTooSmall) {
        super(isCharsetTooSmall ? CHARSET_TOO_SMALL : DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new InvalidCommandException with a specified message.
     *
     * @param message the detail message
     */
    public ExecutionCommandException(String message) {
        super(message);
    }
}