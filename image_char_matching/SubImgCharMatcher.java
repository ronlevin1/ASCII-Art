package image_char_matching;


import java.util.HashSet;

/**
 * A package-private class of the package image_char_matching.
 * This class is responsible for matching a sub-image to a character
 * in a given image.
 */
public class SubImgCharMatcher {
    // consts
    private static final String DEFUALT_ROUND = "abs";
    // fields
    private final HashSet<MyChar> charset = new HashSet<>();
    private final String round;
    private double minBrightness = Double.MAX_VALUE;
    private double maxBrightness = Double.MIN_VALUE;

    /**
     * Default constructor.
     *
     * @param charset the characters to match to.
     */
    public SubImgCharMatcher(char[] charset) {
        for (char c : charset) {
            this.charset.add(new MyChar(c));
        }
        this.round = DEFUALT_ROUND;
    }

    /**
     * Constructor.
     *
     * @param charset the characters to match to.
     * @param round   the rounding method to use.
     */
    public SubImgCharMatcher(char[] charset, String round) {
        for (char c : charset) {
            this.charset.add(new MyChar(c));
        }
        this.round = round;
    }

    /**
     * Compares the brightness of a character to a sub-image.
     * if two chars have the same brightness, the one with the lower
     * ASCII value is returned.
     *
     * @param brightness the brightness of the sub-image.
     * @return the difference in brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        double minDiff = Double.MAX_VALUE;
        char bestMatch = charset.iterator().next().getChar();
        for (MyChar c : charset) {
            double charBrightness = c.getBrightness();
            updateMinMaxBrightness(charBrightness);
            //TODO check if this is the right way to round according to the round field
            double diff = Math.abs(charBrightness - brightness);
            if (diff < minDiff) {
                minDiff = diff;
                bestMatch = c.getChar();
            } else if (diff == minDiff && c.getChar() < bestMatch) {
                bestMatch = c.getChar();
            }
        }
        return bestMatch;
    }

    private void updateMinMaxBrightness(double charBrightness) {
        // update min and max brightness
        if(charBrightness < minBrightness) {
            minBrightness = charBrightness;
        }
        if(charBrightness > maxBrightness) {
            maxBrightness = charBrightness;
        }
    }

    /**
     * Adds a character to the charset.
     *
     * @param c the character to add.
     */
    public void addChar(char c) {
        MyChar newChar = new MyChar(c);
        charset.add(newChar);
        // update brightness: min, max (fields) and normalized (foreach char)
        updateMinMaxBrightness(newChar.getBrightness());
        normalizeSetBrightness();
    }

    /**
     * Removes a character from the charset.
     *
     * @param c the character to remove.
     */
    public void removeChar(char c) {
        charset.remove(new MyChar(c));
        /*
        update brightness: min, max (fields) and normalized (foreach char).
        reset min and max brightness since removed char is not necessarily
        the min or max brightness char.
         */
        minBrightness = Double.MAX_VALUE;
        maxBrightness = Double.MIN_VALUE;
        for (MyChar myChar : charset) {
            updateMinMaxBrightness(myChar.getBrightness());
        }
        normalizeSetBrightness();

    }

    public void normalizeSetBrightness() {
        for (MyChar c : charset) {
            c.setNormalizedBrightness((c.getBrightness() - minBrightness) / (maxBrightness - minBrightness));
        }
    }
}
