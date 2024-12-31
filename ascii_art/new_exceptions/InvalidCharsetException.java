package ascii_art.new_exceptions;

/**
 * Exception thrown when an invalid charset is encountered.
 */
public class InvalidCharsetException extends AsciiArtException {
    public static final String CHARSET_TOO_SMALL = "Did not execute. Charset" +
            " is too small.";
    public static final String INCORRECT_FORMAT = "Did not %s due to " +
            "incorrect format.";

    /**
     * Constructs a new InvalidCharsetException with a default message
     * indicating the charset is too small.
     */
    public InvalidCharsetException() {
        super(CHARSET_TOO_SMALL);
    }

    /**
     * Constructs a new InvalidCharsetException with a message based on the
     * provided flags.
     *
     * @param isFormat if true, sets the message to indicate incorrect
     *                 format; otherwise, sets the message to indicate the
     *                 charset is too small.
     * @param isAdd    if true and isFormat is true, sets the message to
     *                 indicate an add operation; otherwise, sets the
     *                 message to indicate a remove operation.
     */
    public InvalidCharsetException(boolean isFormat, boolean isAdd) {
//        super(isFormat ? INCORRECT_FORMAT : CHARSET_TOO_SMALL);
        super(isFormat ? String.format(INCORRECT_FORMAT, isAdd ? "add" :
                "remove") : CHARSET_TOO_SMALL);
    }

    /**
     * Constructs a new InvalidCharsetException with the specified detail
     * message.
     *
     * @param message the detail message.
     */
    public InvalidCharsetException(String message) {
        super(message);
    }
}
