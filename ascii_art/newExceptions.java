package ascii_art;

// Base Exception Class
public class AsciiArtException extends Exception {
    public AsciiArtException(String message) {
        super(message);
    }
}

// Invalid Command Exception
public class InvalidCommandException extends AsciiArtException {
    public static final String DEFAULT_MESSAGE = "Did not execute due to incorrect command.";

    public InvalidCommandException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}

// File Loading Exception
public class FileLoadingException extends AsciiArtException {
    public static final String DEFAULT_MESSAGE = "Failed to load image file.";

    public FileLoadingException() {
        super(DEFAULT_MESSAGE);
    }

    public FileLoadingException(String message) {
        super(message);
    }
}

// Invalid Charset Exception
public class InvalidCharsetException extends AsciiArtException {
    public static final String CHARSET_TOO_SMALL = "Did not execute. Charset is too small.";
    public static final String INCORRECT_FORMAT = "Did not add due to incorrect format.";

    public InvalidCharsetException() {
        super(CHARSET_TOO_SMALL);
    }

    public InvalidCharsetException(boolean incorrectFormat) {
        super(incorrectFormat ? INCORRECT_FORMAT : CHARSET_TOO_SMALL);
    }

    public InvalidCharsetException(String message) {
        super(message);
    }
}

// Invalid Resolution Exception
public class InvalidResolutionException extends AsciiArtException {
    public static final String EXCEEDING_BOUNDARIES = "Did not change resolution due to exceeding boundaries.";
    public static final String INCORRECT_FORMAT = "Did not change resolution due to incorrect format.";

    public InvalidResolutionException() {
        super(EXCEEDING_BOUNDARIES);
    }

    public InvalidResolutionException(boolean incorrectFormat) {
        super(incorrectFormat ? INCORRECT_FORMAT : EXCEEDING_BOUNDARIES);
    }

    public InvalidResolutionException(String message) {
        super(message);
    }
}

// Invalid Rounding Mode Exception
public class InvalidRoundingModeException extends AsciiArtException {
    public static final String INCORRECT_FORMAT = "Did not change rounding method due to incorrect format.";

    public InvalidRoundingModeException() {
        super(INCORRECT_FORMAT);
    }

    public InvalidRoundingModeException(String message) {
        super(message);
    }
}

// ASCII Art Execution Exception
public class AsciiArtExecutionException extends AsciiArtException {
    public static final String EXECUTION_FAILURE = "Failed to execute ASCII art algorithm.";

    public AsciiArtExecutionException() {
        super(EXECUTION_FAILURE);
    }

    public AsciiArtExecutionException(String message) {
        super(message);
    }
}
