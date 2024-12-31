package ascii_art.new_exceptions;

/**
 * Exception thrown when an invalid rounding mode is encountered.
 */
public class OutputMethodException extends AsciiArtException {
    public static final String INCORRECT_FORMAT = "Did not change output method due to incorrect format.";

    /**
     * Constructs a new InvalidRoundingModeException with the default message.
     */
    public OutputMethodException() {
        super(INCORRECT_FORMAT);
    }

    /**
     * Constructs a new InvalidRoundingModeException with a specified message.
     *
     * @param message the detail message
     */
    public OutputMethodException(String message) {
        super(message);
    }
}
