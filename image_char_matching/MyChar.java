package image_char_matching;

import java.util.Objects;

/**
 *  A wrapper class for a character with brightness.
 */
public class MyChar {

    private char c;
    private double booleanBrightness;
    private double normalizedBrightness;

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

    public char getChar() {
        return c;
    }

    public double getNormalizedBrightness() {
        return normalizedBrightness;
    }

    public double getBooleanBrightness() {
        return booleanBrightness;
    }

    public void setNormalizedBrightness(double normalizedBrightness) {
        this.normalizedBrightness = normalizedBrightness;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(c);
    }

}
