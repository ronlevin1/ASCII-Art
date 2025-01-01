package image_char_matching;


import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 * A package-private class of the package image_char_matching.
 * This class is responsible for matching a sub-image to a character
 * in a given image.
 */
public class SubImgCharMatcher {
    // consts
    private static final String DEFUALT_ROUND = "abs";
    private static final String CMD_UP = "up";
    private static final String CMD_DOWN = "down";
    // fields
    private final HashSet<MyChar> charset = new HashSet<>();
    private String roundMethod;
    private double minBoolBrightness = Double.MAX_VALUE;
    private double maxBoolBrightness = Double.MIN_VALUE;

    /**
     * Default constructor.
     *
     * @param charset the characters to match to.
     */
    public SubImgCharMatcher(char[] charset) {
        setCharset(charset);
        updateMinMaxBoolBrightness();
        normalizeCharsetBrightness();
        this.roundMethod = DEFUALT_ROUND;
    }

    /**
     * Second Constructor to accept round method var.
     *
     * @param charset     the characters to match to.
     * @param roundMethod the rounding method to use.
     */
    public SubImgCharMatcher(char[] charset, String roundMethod) {
        //todo: change this Ctor to public method called resetMatcher?
        setCharset(charset);
        updateMinMaxBoolBrightness();
        normalizeCharsetBrightness();
        this.roundMethod = roundMethod;
    }

    private void setCharset(char[] charset) {
        for (char c : charset) {
            this.charset.add(new MyChar(c));
        }
    }

    private void updateMinMaxBoolBrightness() {
        // reset values
        minBoolBrightness = Double.MAX_VALUE;
        maxBoolBrightness = Double.MIN_VALUE;
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
            double curBrightness = c.getBooleanBrightness();
            c.setNormalizedBrightness((curBrightness - minBoolBrightness) / (maxBoolBrightness - minBoolBrightness));
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
            double diff;
            //double edgeCaseDiff;
            //todo: test this switch
            // todo: extract to outer scope/implement interface strategy
            switch (this.roundMethod) {
                case DEFUALT_ROUND:
                    diff = Math.abs(charBrightness - brightness);
                    break;
                case CMD_UP:
                    // makes choose of char with brightness closest from above
                    diff = (charBrightness - brightness);
                    if (diff < 0)
                        // char brightness is BELOW image brightness
                        continue;
                    break;
                case CMD_DOWN:
                    // makes choose of char with brightness closest from below
                    diff = (brightness - charBrightness);
                    if (diff < 0)
                        // char brightness is ABOVE image brightness
                        continue;
                    break;
                default:
                    diff = Math.abs(charBrightness - brightness);
            }
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
        if (charset.contains(newChar)) {
            return;
        }
        //
        boolean isMinMaxChanged = false;
        double curBrightness = newChar.getBooleanBrightness();
        charset.add(newChar);

        // update brightness: min, max (fields) and normalized (foreach char)
        if (curBrightness < minBoolBrightness) {
            minBoolBrightness = curBrightness;
            isMinMaxChanged = true;
        }
        // update top brightness
        if (curBrightness > maxBoolBrightness) {
            maxBoolBrightness = curBrightness;
            isMinMaxChanged = true;
        }
        // if changed: update normalized brightness for all chars
        if (isMinMaxChanged) {
            //updateMinMaxBoolBrightness();
            normalizeCharsetBrightness();
        } else {
            //otherwise, update only the new char's normalized brightness
            newChar.setNormalizedBrightness((curBrightness - minBoolBrightness) / (maxBoolBrightness - minBoolBrightness));
        }
//        normalizeCharsetBrightness();
    }

    /**
     * Removes a character from the charset.
     *
     * @param c the character to remove.
     */
    public void removeChar(char c) {
        MyChar newChar = new MyChar(c);
        if (!charset.contains(newChar)) {
            return;
        }
        double curBrightness = newChar.getBooleanBrightness();
        charset.remove(newChar);
        /*
        update brightness: min, max (fields) and normalized (foreach char).
        reset min and max brightness since removed char is not necessarily
        the min or max brightness char.
         */
        if (curBrightness == minBoolBrightness || curBrightness == maxBoolBrightness) {
            updateMinMaxBoolBrightness();
            normalizeCharsetBrightness();
        }
    }

    /**
     * Prints the charset.
     */
    public void printCharset() {
        if (this.charset.isEmpty()) {
            return;
        }
        // sort and print
        Stream<MyChar> charStream =
                charset.stream().sorted(Comparator.comparingInt(MyChar::getChar));
        charStream.forEach(c -> System.out.print(c.getChar() + " "));
        System.out.println();
    }

    /**
     * Setter for the round method.
     *
     * @return the round method.
     */
    public void setRoundMethod(String roundMethod) {
        this.roundMethod = roundMethod;
    }

    /**
     * Getter for the charset size.
     *
     * @return the size of the charset.
     */
    public int getCharsetSize() {
        return charset.size();
    }

    // uncomment for testing
//    static void addChar_addsNewCharToCharset() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b'});
//        matcher.addChar('c');
//        assert (3 == matcher.getCharsetSize());
//    }
//
//    static void addChar_doesNotAddDuplicateChar() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b'});
//        matcher.addChar('a');
//        assert (2 == matcher.getCharsetSize());
//    }
//
//    static void addChar_updatesMinMaxBrightness() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b'});
//        matcher.addChar('c');
//        assert (matcher.getCharsetSize() > 2);
//    }
//
//    static void addChar_normalizesBrightnessForAllChars() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b'});
//        matcher.addChar(' '); // zero brightness
//        matcher.addChar('#'); // max brightness
//        matcher.addChar('c'); // middle brightness
//    }
//
//    //
//    static void removeChar_removesExistingCharFromCharset() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b', 'c'});
//        matcher.removeChar('b');
//        assert (2 == matcher.getCharsetSize());
//    }
//
//    static void removeChar_doesNotRemoveNonExistingChar() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b'});
//        matcher.removeChar('c');
//        assert (2 == matcher.getCharsetSize());
//    }
//
//    static void removeChar_updatesMinMaxBrightnessAfterRemoval() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b', 'c'});
//        matcher.removeChar('a'); // assuming 'a' has mid brightness
//        assert (matcher.getCharsetSize() == 2);
//    }
//
//    static void removeChar_normalizesBrightnessForAllCharsAfterRemoval() {
//        SubImgCharMatcher matcher = new SubImgCharMatcher(new char[]{'a',
//                'b', ' ', '#'});
//        matcher.removeChar('#'); // assuming 'a' has min brightness
//        matcher.removeChar(' '); // assuming 'c' has max brightness
//        assert (matcher.getCharsetSize() == 2);
//    }
//
//    public static void main(String[] args) {
//        // test add
////        addChar_addsNewCharToCharset();
////        addChar_doesNotAddDuplicateChar();
////        addChar_updatesMinMaxBrightness();
////        addChar_normalizesBrightnessForAllChars();
//        // test remove
////        removeChar_removesExistingCharFromCharset();
////        removeChar_doesNotRemoveNonExistingChar();
////        removeChar_updatesMinMaxBrightnessAfterRemoval();
////        removeChar_normalizesBrightnessForAllCharsAfterRemoval();
//    }
//}
}


