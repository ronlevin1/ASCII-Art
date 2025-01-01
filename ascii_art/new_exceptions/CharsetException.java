package ascii_art.new_exceptions;

/**
 * Exception thrown when an invalid charset is encountered.
 */
public class CharsetException extends AsciiArtException {
    private static final String INCORRECT_FORMAT = "Did not %s due to " +
            "incorrect format.";

    /**
     * Constructs a new InvalidCharsetException with a message based on the
     * provided flags.
     *
     * @param isAdd if true, sets the message to indicate an add operation;
     *              otherwise, sets the message to indicate a remove operation.
     */
    public CharsetException(boolean isAdd) {
        super(String.format(INCORRECT_FORMAT, isAdd ? "add" : "remove"));
    }

    /**
     * Constructs a new InvalidCharsetException with the specified detail
     * message.
     *
     * @param message the detail message.
     */
    public CharsetException(String message) {
        super(message);
    }
}
