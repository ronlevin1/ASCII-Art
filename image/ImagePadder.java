package image;

import java.awt.*;
import java.io.IOException;

/**
 * The ImagePadder class provides functionality to pad an image with white
 * pixels
 * such that its dimensions are powers of 2.
 */
public class ImagePadder {
    private static final int WHITE_RGB_VAL = 255;
    private static final String ERR_DIMENSION_NOT_EVEN = "Dimension must be even.";
    private static final int NUM_2 = 2;
    private final Image image;
    // half of the padding on each side.
    private final int paddingX;
    private final int paddingY;

    /**
     * Constructs an ImagePadder object with the specified image.
     * Calculates the padding needed to make the image's width and height
     * powers of 2.
     *
     * @param image the image to pad
     */
    public ImagePadder(Image image) {
        this.image = image;
        /*
        calc padding s.t. img width and height are both divisible by 2.
        meaning, find the smallest padding s.t. the new width and height are
         both 2^k for some k.
         padding is symmetric on each side.
         */
        this.paddingX = calcHalfPadding(image.getWidth());
        this.paddingY = calcHalfPadding(image.getHeight());
    }

    /**
     * Pads the image with white pixels and returns the padded image.
     *
     * @return the padded image
     */
    public Image pad() {
        int newWidth = image.getWidth() + NUM_2 * paddingX;
        int newHeight = image.getHeight() + NUM_2 * paddingY;
        Color[][] newPixelArray = new Color[newHeight][newWidth];
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                // if we are in the padding area, set the pixel to white.
                if (i < paddingY || i >= newHeight - paddingY || j < paddingX || j >= newWidth - paddingX) {
                    newPixelArray[i][j] = new Color(WHITE_RGB_VAL, WHITE_RGB_VAL, WHITE_RGB_VAL);
                } else {
                    // else, set the pixel to the original image's pixel.
                    newPixelArray[i][j] = image.getPixel(i - paddingY,
                            j - paddingX);
                }
            }
        }
        return new Image(newPixelArray, newWidth, newHeight);
    }

    /**
     * Calculates the padding needed to make the given dimension a power of 2.
     *
     * @param dim the dimension to pad. Must be positive and even.
     * @return the padding needed, ON EACH SIDE.
     */
    private int calcHalfPadding(int dim) {
        // if the dimension is not even, throw an exception.
        if (dim % NUM_2 != 0) {
            throw new IllegalArgumentException(ERR_DIMENSION_NOT_EVEN);
        }
        int k = 0;
        while (Math.pow(NUM_2, k) < dim) {
            k++;
        }
        int nextPowerOf2 = (int) Math.pow(NUM_2, k);
        return (nextPowerOf2 - dim) / NUM_2;
    }
}
