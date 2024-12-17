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
