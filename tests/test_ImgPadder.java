//package tests;
//import image.Image;
//import image.ImagePadder;
//
//import java.io.IOException;
////import org.junit.jupiter.api.Test;
////
////import static org.junit.jupiter.api.Assertions.*;
//
//public class test_ImgPadder {
////    @Test
//    void test_calcPaddingReturnsCorrectPaddingForPowerOfTwo() throws IOException {
//        Image img = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(img);
//        assert(padder.calcPadding(2) == 0);
//        assert(padder.calcPadding(4) == 0);
//        assert(padder.calcPadding(8) == 0);
//    }
//
////    @Test
//    void test_calcPaddingReturnsCorrectPaddingForNonPowerOfTwo() throws IOException {
//        Image img = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(img);        assert(padder.calcPadding(3) == 4);
//        assert(padder.calcPadding(5) == 3);
//        assert(padder.calcPadding(9) == 16-9);
//        assert(padder.calcPadding(30) == 32-30);
//    }
//
////    @Test
//    void test_calcPaddingReturnsCorrectPaddingForZero() throws IOException {
//        Image img = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(img);
//        assert(padder.calcPadding(0) == 2);
//    }
//
////    @Test
//    void test_calcPaddingReturnsCorrectPaddingForOne() throws IOException {
//        Image img = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(img);
//        assert(padder.calcPadding(1) == 200);
//    }
//
//    public static void main(String[] args) throws IOException {
//        test_ImgPadder test = new test_ImgPadder();
//        test.test_calcPaddingReturnsCorrectPaddingForPowerOfTwo();
//        test.test_calcPaddingReturnsCorrectPaddingForNonPowerOfTwo();
//        test.test_calcPaddingReturnsCorrectPaddingForZero();
//        test.test_calcPaddingReturnsCorrectPaddingForOne();
//        System.out.println("All tests passed!");
//    }
//}
//

//Testing of ImagePadder.java
//    public static void main(String[] args) throws IOException {
//        Image image = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(image);
//        Image paddedImage = padder.pad();
//        paddedImage.saveImage("padded_board");
//    }

// test for SubImagesHolder
//public static void main(String[] args) throws IOException {
////        Image image = new Image("examples/cat.jpeg");
////        ImagePadder padder = new ImagePadder(image);
////        Image paddedImage = padder.pad();
////        paddedImage.saveImage("padded_cat");
////        // create sub images of the padded image.
////        SubImagesHolder sub = new SubImagesHolder(paddedImage, 2);
////        //output subImage[0][0], subImage[1][1]
////        SubImage[][] subImages = sub.getSubImages();
////        subImages[0][0].saveImage("subImage_0_0");
////        subImages[0][1].saveImage("subImage_0_1");
////        subImages[1][0].saveImage("subImage_1_0");
////        subImages[1][1].saveImage("subImage_1_1");
//
//        Image image = new Image("examples/board.jpeg");
//        ImagePadder padder = new ImagePadder(image);
//        Image paddedImage = padder.pad();
//        paddedImage.saveImage("padded_board");
//        // create sub images of the padded image.
//        SubImagesHolder sub = new SubImagesHolder(paddedImage, 2);
//        //output subImage[0][0], subImage[1][1]
//        SubImage[][] subImages = sub.getSubImages();
//        subImages[0][0].saveImage("subBoard_0_0");
//        subImages[0][1].saveImage("subBoard_0_1");
//        subImages[1][0].saveImage("subBoard_1_0");
//        subImages[1][1].saveImage("subBoard_1_1");
//    }
