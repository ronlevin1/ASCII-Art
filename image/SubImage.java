package image;


import image_char_matching.SubImgCharMatcher;

import java.awt.*;

/**
 * this class recieves a sub-image and calculates its grey-scale brightness.
 */
public class SubImage extends Image {
    private static final double RED_WEIGHT = 0.2126;
    private static final double GREEN_WEIGHT = 0.7152;
    private static final double BLUE_WEIGHT = 0.0722;
    private static final int MAX_RGB_VALUE = 255;

    private final Color[][] subImage;
    private double brightness;

    /**
     * Constructs a SubImage object with the specified pixel array, width,
     * and height.
     * Calculates the brightness of the sub-image.
     *
     * @param pixelArray the 2D array of Color objects representing the
     *                   sub-image
     * @param width      the width of the sub-image
     * @param height     the height of the sub-image
     */
    public SubImage(Color[][] pixelArray, int width, int height) {
        super(pixelArray, width, height);
        this.subImage = pixelArray;
        this.brightness = calcBrightness();
    }

    /**
     * Returns the grey-scale brightness of the sub-image, normalized.
     *
     * @return the brightness of the sub-image.
     */
    public double getBrightness() {
        return brightness;
    }

    /**
     * Returns the brightness of the sub-image.
     *
     * @return the brightness of the sub-image.
     */
    private double calcBrightness() {
        double totalBrightness = 0;
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                double greyPixel =
                        this.getPixel(i, j).getRed() * RED_WEIGHT
                                + this.getPixel(i, j).getGreen() * GREEN_WEIGHT
                                + this.getPixel(i, j).getBlue() * BLUE_WEIGHT;
                totalBrightness += greyPixel;
            }
        }
        double dims = this.getHeight() * this.getWidth();
        // normalize the brightness to be between 0 and 1.
        return totalBrightness / (dims * MAX_RGB_VALUE);
    }
}
