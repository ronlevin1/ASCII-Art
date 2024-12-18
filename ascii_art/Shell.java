package ascii_art;

import image.Image;
import image.SubImagesHolder;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class Shell {
    // todo: check which one is needed
//    private MyChar[] charset;
    private SubImgCharMatcher matcher;
    private Image image;
    private int resolution;
    private SubImagesHolder subImgsHolder;
    /*
    ---------------------
                           * In Shell Ctor *
        0. convert charset to MyChar[] (foreach char c, has char and brightness)
        1. create image from filename and save as Image obj.
            1.1. padd image.
    */

    public Shell() {
    }
    /*
    ---------------------
                * executed and saved in shell (outer scope) to *
                * save runtime complexity. *

            1.2. split image to sub-images, according to resolution.
        2. for each sub-image:
            2.1. convert sub-image to greyscale brightness.
     */
    public void run(String imageName) throws IOException {
        this.image = new Image(imageName);
        // ...
//        SubImage[][] subImages = subImgsHolder.getSubImages();
        char[][] asciiChars = new AsciiArtAlgorithm(matcher,
                subImgsHolder).run();
    }

    public static void main(String[] args) {
        // send args to Ctor
    }
}
