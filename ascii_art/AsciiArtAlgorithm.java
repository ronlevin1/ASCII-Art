package ascii_art;

import image.SubImagesHolder;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {
    private final SubImgCharMatcher matcher;
    private final SubImagesHolder subImagesHolder;

    public AsciiArtAlgorithm(
            SubImgCharMatcher matcher,
            SubImagesHolder subImagesHolder) {
        this.matcher = matcher;
        this.subImagesHolder = subImagesHolder;
    }

    public char[][] run() {
        int numOfRows = subImagesHolder.getSubImages().length;
        int numOfCols = subImagesHolder.getSubImages()[0].length;
        char[][] asciiChars = new char[numOfRows][numOfCols];
        double curBrightness = 0;

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {

                curBrightness = subImagesHolder.getBrightness(i, j);
//                String outName = (String.format("tests/asciiAlgo/%d_%d",
//                i, j));
//                subImagesHolder.getSubImages()[i][j].saveImage(outName);
                char bestMatch =
                        matcher.getCharByImageBrightness(curBrightness);
                asciiChars[i][j] = bestMatch;
            }
        }
        return asciiChars;
    }
}
