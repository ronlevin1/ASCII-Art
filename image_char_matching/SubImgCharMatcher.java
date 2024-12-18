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
    private double minBoolBrightness = Double.MAX_VALUE;
    private double maxBoolBrightness = Double.MIN_VALUE;

    /**
     * Default constructor.
     *
     * @param charset the characters to match to.
     */
    public SubImgCharMatcher(char[] charset) {
        unpackCharset(charset);
        updateMinMaxBoolBrightness();
        normalizeCharsetBrightness();
        this.round = DEFUALT_ROUND;
    }

    /**
     * Constructor.
     *
     * @param charset the characters to match to.
     * @param round   the rounding method to use.
     */
    public SubImgCharMatcher(char[] charset, String round) {
        unpackCharset(charset);
        updateMinMaxBoolBrightness();
        normalizeCharsetBrightness();
        this.round = round;
    }

    private void unpackCharset(char[] charset) {
        for (char c : charset) {
            this.charset.add(new MyChar(c));
        }
    }

    private void updateMinMaxBoolBrightness() {
        // update min and max brightness
        for (MyChar c : charset) {
            double curBrightness = c.getBooleanBrightness();
            if (curBrightness < this.minBoolBrightness) {
                this.minBoolBrightness = curBrightness;
            }
            if (curBrightness > this.maxBoolBrightness) {
                this.maxBoolBrightness = curBrightness;
            }
        }
    }

    private void normalizeCharsetBrightness() {
        for (MyChar c : charset) {
            c.setNormalizedBrightness((c.getNormalizedBrightness() - minBoolBrightness) / (maxBoolBrightness - minBoolBrightness));
        }
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
        //
        for (MyChar c : charset) {
            double charBrightness = c.getNormalizedBrightness();
            //TODO add switch for round
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


    /**
     * Adds a character to the charset.
     *
     * @param c the character to add.
     */
    public void addChar(char c) {
        MyChar newChar = new MyChar(c);
        double curBrightness = newChar.getBooleanBrightness();
        charset.add(newChar);
        boolean isMinMaxChanged = false;
        // update brightness: min, max (fields) and normalized (foreach char)
        if (curBrightness < minBoolBrightness) {
            minBoolBrightness = newChar.getBooleanBrightness();
            isMinMaxChanged = true;
        }
        // update top brightness
        if (curBrightness > maxBoolBrightness) {
            maxBoolBrightness = newChar.getBooleanBrightness();
            isMinMaxChanged = true;
        }
        // if changed: update normalized brightness for all chars
        if (isMinMaxChanged) {
            //updateMinMaxBoolBrightness();
            normalizeCharsetBrightness();
        } else {
            //otherwise, update only the new char's normalized brightness
            newChar.setNormalizedBrightness((newChar.getBooleanBrightness() - minBoolBrightness) / (maxBoolBrightness - minBoolBrightness));
        }
//        normalizeCharsetBrightness();
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
        minBoolBrightness = Double.MAX_VALUE;
        maxBoolBrightness = Double.MIN_VALUE;
        for (MyChar myChar : charset) {
            updateMinMaxBoolBrightness(myChar.getNormalizedBrightness());
        }
        normalizeCharsetBrightness();

    }
}
