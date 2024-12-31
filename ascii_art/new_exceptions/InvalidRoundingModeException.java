package ascii_art.new_exceptions;

/**
 * Exception thrown when an invalid rounding mode is encountered.
 */
public class InvalidRoundingModeException extends AsciiArtException {
    public static final String INCORRECT_FORMAT = "Did not change rounding method due to incorrect format.";

    /**
     * Constructs a new InvalidRoundingModeException with the default message.
     */
    public InvalidRoundingModeException() {
        super(INCORRECT_FORMAT);
    }

    /**
     * Constructs a new InvalidRoundingModeException with a specified message.
     *
     * @param message the detail message
     */
    public InvalidRoundingModeException(String message) {
        super(message);
    }
}
