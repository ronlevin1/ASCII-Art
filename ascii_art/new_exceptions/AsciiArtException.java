package ascii_art.new_exceptions;

/**
 * Base Exception Class for ASCII Art related exceptions.
 */
public class AsciiArtException extends Exception {
    /**
     * The detail message string of this AsciiArtException instance.
     */
    private String message;

    /**
     * Constructs a new AsciiArtException with the specified detail message.
     *
     * @param message the detail message.
     */
    public AsciiArtException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Returns the detail message string of this AsciiArtException instance.
     *
     * @return the detail message string.
     */
    public String getMessage() {
        return message;
    }
}