package ascii_art.new_exceptions;

/**
 * Exception thrown when there is a failure in executing the ASCII art algorithm.
 */
public class AsciiArtExecutionException extends AsciiArtException {
    public static final String EXECUTION_FAILURE = "Failed to execute ASCII art algorithm.";

    /**
     * Constructs a new AsciiArtExecutionException with a default error message.
     */
    public AsciiArtExecutionException() {
        super(EXECUTION_FAILURE);
    }

    /**
     * Constructs a new AsciiArtExecutionException with the specified error message.
     *
     * @param message the detail message
     */
    public AsciiArtExecutionException(String message) {
        super(message);
    }
}
