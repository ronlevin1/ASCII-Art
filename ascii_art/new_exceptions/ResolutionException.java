package ascii_art.new_exceptions;

// Invalid Resolution Exception

/**
 * Exception thrown when an invalid resolution is encountered in ASCII art
 * processing.
 */
public class ResolutionException extends AsciiArtException {
    public static final String EXCEEDING_BOUNDARIES = "Did not change " +
            "resolution due to exceeding boundaries.";
    public static final String INCORRECT_FORMAT = "Did not change resolution" +
            " due to incorrect format.";

    /**
     * Constructs a new InvalidResolutionException with a default message
     * indicating exceeding boundaries.
     */
    public ResolutionException() {
        super(EXCEEDING_BOUNDARIES);
    }

    /**
     * Constructs a new InvalidResolutionException with a message based on
     * the provided flag.
     *
     * @param incorrectFormat if true, sets the message to indicate
     *                        incorrect format; otherwise, exceeding
     *                        boundaries.
     */
    public ResolutionException(boolean incorrectFormat) {
        super(incorrectFormat ? INCORRECT_FORMAT : EXCEEDING_BOUNDARIES);
    }

    /**
     * Constructs a new InvalidResolutionException with the specified detail
     * message.
     *
     * @param message the detail message.
     */
    public ResolutionException(String message) {
        super(message);
    }
}