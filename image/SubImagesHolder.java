package image;
//TODO: document
import java.awt.*;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * This class is responsible for holding an image and its sub-images.
 */
public class SubImagesHolder {
    private final Image image;
    private int resolution;
    private int subImgSize;
    private SubImage[][] subImages;

    public SubImage[][] getSubImages() {
        return subImages;
    }

    public Image getImage() {
        return image;
    }

    public double getBrightness(int i, int j) {
        return subImages[i][j].getBrightness();
    }

    public SubImagesHolder(Image image, int resolution) {
        this.image = image;
        this.resolution = resolution;
        this.subImgSize = image.getWidth() / resolution;
        this.subImages =
                new SubImage[image.getHeight() / subImgSize][resolution];
        this.createSubImages();
    }

    private void createSubImages() {
        // Image -> subImages
        // iterate over the sub-images
        // (jump between 'squares' of subImageSize x subImageSize)
        for (int i = 0; i < image.getHeight(); i += subImgSize) {
            for (int j = 0; j < image.getWidth(); j += subImgSize) {
                // create a sub-image matrix of (subImageSize x subImageSize)
                Color[][] curSubImage = new Color[subImgSize][subImgSize];
                // iterate 'square' inner-pixels
                for (int k = 0; k < subImgSize; k++) {
                    for (int l = 0; l < subImgSize; l++) {
                        // fill sub-image matrix with appropriate pixels.
                        //todo: check pixel calculation
                        //int pixelX = Math.min(i + k, image.getHeight() - 1);
                        //int pixelY = Math.min(j + l, image.getWidth() - 1);
                        curSubImage[k][l] = image.getPixel(i + k, j + l);
                    }
                }
                // create a SubImage object from the sub-image matrix.
                this.subImages[i / subImgSize][j / subImgSize] =
                        new SubImage(curSubImage, subImgSize, subImgSize);
            }
        }
    }
}
