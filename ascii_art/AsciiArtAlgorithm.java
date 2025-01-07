package ascii_art;

import image.SubImagesHolder;
import image_char_matching.SubImgCharMatcher;

/**
 * The AsciiArtAlgorithm class converts images into ASCII art.
 * It uses a SubImgCharMatcher to match sub-images to characters
 * and a SubImagesHolder to hold the sub-images.
 */
public class AsciiArtAlgorithm {
    private final SubImgCharMatcher matcher;
    private final SubImagesHolder subImagesHolder;

    /**
     * Constructs an AsciiArtAlgorithm with the specified matcher and
     * subImagesHolder.
     *
     * @param matcher         the SubImgCharMatcher to use for matching
     *                        sub-images to characters
     * @param subImagesHolder the SubImagesHolder to hold the sub-images
     */
    public AsciiArtAlgorithm(
            SubImgCharMatcher matcher,
            SubImagesHolder subImagesHolder) {
        this.matcher = matcher;
        this.subImagesHolder = subImagesHolder;
    }

    /**
     * Runs the ASCII art algorithm and returns a 2D array of characters
     * representing the ASCII art.
     *
     * @return a 2D array of characters representing the ASCII art
     */
    public char[][] run() {
        int numOfRows = subImagesHolder.getSubImages().length;
        int numOfCols = subImagesHolder.getSubImages()[0].length;
        char[][] asciiChars = new char[numOfRows][numOfCols];
        double curBrightness = 0;

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                curBrightness = subImagesHolder.getBrightness(i, j);
                char bestMatch =
                        matcher.getCharByImageBrightness(curBrightness);
                asciiChars[i][j] = bestMatch;
            }
        }
        return asciiChars;
    }
}
