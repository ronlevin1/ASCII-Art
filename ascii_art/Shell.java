package ascii_art;

//TODO: document
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImagePadder;
import image.SubImagesHolder;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class Shell {

    private static final int DEFUALT_RES = 2;
    private static final String DEFUALT_ROUND = "abs";
    private static final char[] DEFUALT_CHARSET = "0123456789".toCharArray();
    // all ascii chars in range 32 to 126, including.
    private static final char[] CHARSET_ALL_ASCII = new char[95];

    private static final String DEFUALT_OUTPUT_METHOD = "console";

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

    public Shell(String imagePath, char[] charset, int resolution,
                 String round) {
        // padd image
        try {
            Image original_image = new Image(imagePath);
//            original_image.saveImage("examples/original.jpg");
            ImagePadder padder = new ImagePadder(original_image);
            this.image = padder.pad();
//            this.image.saveImage("examples/padded.jpg");
        } catch (IOException e) {
            e.printStackTrace(); // todo: handle exception
        }
        this.matcher = new SubImgCharMatcher(charset, round);
        this.resolution = resolution;
        this.subImgsHolder = new SubImagesHolder(image, resolution);
//        this.subImgsHolder.getImage().saveImage("examples/afterHolder");

        for(int i = 32; i <= 126; i++) {
            CHARSET_ALL_ASCII[i - 32] = (char) i;
        }
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
        // Load and pad image
//        try {
//            Image original_image = new Image(imageName);
//            ImagePadder padder = new ImagePadder(original_image);
//            this.image = padder.pad();
//        } catch (IOException e) {
//            e.printStackTrace(); // todo: handle exception
//        }
        // ...
        char[][] asciiChars = new AsciiArtAlgorithm(matcher,
                subImgsHolder).run();
        //todo: choose/switch according to user input. def is console.
//        AsciiOutput consoleOutput = new ConsoleAsciiOutput();
//        consoleOutput.out(asciiChars);
        //
        if (true) {
            AsciiOutput htmlOutput = new HtmlAsciiOutput("ascii.html", "Courier New");
            htmlOutput.out(asciiChars);
        }
    }

    public static void main(String[] args) throws IOException {
        // send args to Ctor
        handleArgs(args);
        // run
        char[] allChars = new char[95];
        for(int i = 32; i <= 126; i++) {
            allChars[i - 32] = (char) i;
        }
        //todo
        Shell shell = new Shell("examples/cat.jpeg", allChars,
                256, DEFUALT_ROUND);
        shell.run("examples/cat.jpeg");


        // imagePath, charset, resolution, round
    }

    private static void handleArgs(String[] args) {

    }
}
