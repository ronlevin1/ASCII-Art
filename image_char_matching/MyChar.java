package image_char_matching;

import java.util.Objects;

/**
 * A wrapper class for a character with brightness.
 */
public class MyChar {

    private char c;
    private double booleanBrightness;
    private double normalizedBrightness;

    /**
     * Constructs a MyChar object with the specified character.
     * Calculates the boolean brightness of the character.
     *
     * @param c the character to wrap
     */
    public MyChar(char c) {
        this.c = c;
        this.booleanBrightness = calcBooleanBrightness(c);
        this.normalizedBrightness = 0;
        // TODO: consider moving to a map at SubImgCharMatcher.java
        //       char -> norm

    }

    private double calcBooleanBrightness(char c) {
        boolean[][] charBrightnessArr = CharConverter.convertToBoolArray(c);
        double charBrightness = 0;
        for (boolean[] booleans : charBrightnessArr) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    charBrightness++;
                }
            }
        }
        return charBrightness / Math.pow(CharConverter.DEFAULT_PIXEL_RESOLUTION, 2);
    }

    /**
     * Returns the character wrapped by this object.
     *
     * @return the character wrapped by this object
     */
    public char getChar() {
        return c;
    }

    /**
     * Returns the normalized brightness of the character.
     *
     * @return the normalized brightness of the character
     */
    public double getNormalizedBrightness() {
        return normalizedBrightness;
    }

    /**
     * Returns the boolean brightness of the character.
     *
     * @return the boolean brightness of the character
     */
    public double getBooleanBrightness() {
        return booleanBrightness;
    }

    /**
     * Sets the normalized brightness of the character.
     *
     * @param normalizedBrightness the normalized brightness to set
     */
    public void setNormalizedBrightness(double normalizedBrightness) {
        this.normalizedBrightness = normalizedBrightness;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false
     * otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MyChar myChar = (MyChar) obj;
        return c == myChar.c;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(c);
    }

}
