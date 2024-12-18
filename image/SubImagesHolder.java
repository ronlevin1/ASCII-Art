package image;

import java.awt.*;

/**
 * A package-private class of the package image.
 * This class is responsible for holding an image and its sub-images.
 */
public class SubImagesHolder {

    private final Image image;
    private int resolution;
    private int subImageSquareSize;
    private SubImage[][] subImages;

    public SubImage[][] getSubImages() {
        return subImages;
    }


    public SubImagesHolder(Image image, int resolution) {
        this.image = image;
        this.resolution = resolution;
        this.subImageSquareSize = image.getWidth() / resolution;

        this.subImages =
                new SubImage[image.getHeight() / resolution][image.getWidth() / resolution];
//        this.createSubImages();
    }

    //TODO: test this method
    private void createSubImages() {
        // Image -> subImages
        for (int i = 0; i < image.getHeight(); i += subImageSquareSize) {
            for (int j = 0; j < image.getWidth(); j += subImageSquareSize) {
                // iterate over the sub-image
                // (jump between 'squares' of resolution x resolution)
                Color[][] curSubImage =
                        new Color[subImageSquareSize][subImageSquareSize];
                for (int k = 0; k < subImageSquareSize; k++) {
                    for (int l = 0; l < subImageSquareSize; l++) {
                        // iterate 'square' inner-pixels
                        curSubImage[k][l] = image.getPixel(i + k, j + l);
                    }
                }
                this.subImages[i / subImageSquareSize][j / subImageSquareSize] =
                        new SubImage(curSubImage, subImageSquareSize,
                                subImageSquareSize);
            }
        }
    }

    public double getBrightness(int i, int j) {
        return subImages[i][j].getBrightness();
    }
}
