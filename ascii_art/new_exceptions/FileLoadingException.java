package ascii_art.new_exceptions;

/**
 * Exception thrown when there is an error loading an image file.
 */
public class FileLoadingException extends AsciiArtException {
    public static final String DEFAULT_MESSAGE = "Failed to load image file.";

    /**
     * Constructs a new FileLoadingException with the default message.
     */
    public FileLoadingException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new FileLoadingException with a specified message.
     *
     * @param message the detail message
     */
    public FileLoadingException(String message) {
        super(message);
    }
}
