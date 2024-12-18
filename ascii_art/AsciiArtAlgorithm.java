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

    // ...
    public char[][] run() {
        /*
        ---------------------
                           * In Shell Ctor *
        0. convert charset to MyChar[] (foreach char c, has char and
        brightness)
        1. create image from filename and save as Image obj.
            1.1. padd image.
        ---------------------
                * executed and saved in shell (outer scope) to *
                * save runtime complexity. *

            1.2. split image to sub-images, according to resolution.
        2. for each sub-image:
            2.1. convert sub-image to greyscale brightness.
        ---------------------
                           * In run() *
            2.2. find the best matching char from charset.
            2.3. set the matching char in the asciiChars array.
        3. return asciiChars (or - save it in some format).
        ---------------------
         */
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
