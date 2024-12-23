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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Shell {
    // default args
    private static final char[] DEFAULT_CHARSET = "0123456789".toCharArray();
    private static final String DEFAULT_OUTPUT_METHOD = "console";
    private static final String DEFAULT_ROUND_METHOD = "abs";
    private static final int DEFAULT_RES = 2;
    //more consts
    private static final int MIN_ASCII = 32;
    private static final int MAX_ASCII = 126;
    // arg1 commands
    private static final String CMD_EXIT = "exit";
    private static final String CMD_CHARS = "chars";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_RES = "res";
    private static final String CMD_OUTPUT = "output";
    private static final String CMD_ROUND = "round";
    private static final String CMD_RUN = "asciiArt";
    private static final HashSet<String> VALID_COMMANDS = new HashSet<>();
    private static final String[] VALID_COMMANDS_ARR = {CMD_EXIT, CMD_CHARS,
            CMD_ADD, CMD_REMOVE, CMD_RES, CMD_OUTPUT, CMD_ROUND, CMD_RUN};
    // arg2 commands
    private static final String CMD_ALL = "all";
    private static final String CMD_SPACE = "space";
    private static final String CMD_UP = "up";
    private static final String CMD_DOWN = "down";
    public static final String CMD_HTML = "html";
    public static final String DEFAULT_FONT = "Courier New";

    // all ascii chars in range 32 to 126, including.
//    private static final char[] CHARSET_ALL_ASCII = new char[95];
    // fields
    private SubImgCharMatcher matcher;
    private Image image;
    private int resolution;
    private SubImagesHolder subImgsHolder;
    private String outputMethod;

    public Shell(String imagePath, char[] charset, int resolution,
                 String roundMethod) {
        // padd image
        try {
            Image original_image = new Image(imagePath);
            ImagePadder padder = new ImagePadder(original_image);
            this.image = padder.pad();
        } catch (IOException e) {
            e.printStackTrace(); // todo: handle exception
        }
        this.matcher = new SubImgCharMatcher(charset, roundMethod);
        this.resolution = resolution;
        this.subImgsHolder = new SubImagesHolder(image, resolution);
        this.outputMethod = DEFAULT_OUTPUT_METHOD;
        VALID_COMMANDS.addAll(Arrays.asList(VALID_COMMANDS_ARR));
    }

    public void run(String imageName) throws IOException {
        String errMsg = "Did not execute due to incorrect command.";
        //todo: catch exception HERE! and output error message.
        String[] userInput = {""};
        while (!userInput[0].equals(CMD_EXIT)) {
            System.out.print(">>> "); // todo: change print location
//            userInput = KeyboardInput.readLine().split(" ");
            // ---------------------------------
            String cmd = "add X";
            userInput = cmd.split(" ");
            // ---------------------------------
            String arg1 = userInput[0];
            String arg2 = "";
            if (userInput.length > 1) {
                arg2 = userInput[1];
            } // if
            // execute cmd (ignore its tail if exists).
            switch (arg1) { // todo: test all!
                case CMD_CHARS:
                    matcher.printCharset();
                    break;
                case CMD_ADD: // todo: handle single char input
                    handleAddRemove(arg2, true);
                    break;
                case CMD_REMOVE: // todo
                    handleAddRemove(arg2, false);
                    break;
                case CMD_RES: // todo
                    handleResolution(arg2);
                    break;
                case CMD_ROUND: // todo
                    handleRoundMethod(arg2);
                    break;
                case CMD_OUTPUT: // todo
                    handleOutputMethod(arg2);
                    break;
                case CMD_RUN: // todo
                    handleRun();
                    break;
                default:
                    System.out.println(errMsg); //todo: throw exception?
            } // switch
        } // while
    } // method


//        try {
//            Image original_image = new Image(imageName);
//            ImagePadder padder = new ImagePadder(original_image);
//            this.image = padder.pad();
//        } catch (IOException e) {
//            e.printStackTrace(); // todo: handle exception
//        }


    private void handleRun() {
        String errMsg = "Did not execute. Charset is too small.";
        if (this.matcher.getCharsetSize() < 2) {
            System.out.println(errMsg); //todo: throw exception?
            return;
        }
        char[][] asciiChars =
                new AsciiArtAlgorithm(matcher, subImgsHolder).run();
        if (this.outputMethod.equals(DEFAULT_OUTPUT_METHOD)) {
            AsciiOutput consoleOutput = new ConsoleAsciiOutput();
            consoleOutput.out(asciiChars);
        } else {
            AsciiOutput htmlOutput = new HtmlAsciiOutput("out.html",
                    DEFAULT_FONT);
            htmlOutput.out(asciiChars);
        }
    }

    private void handleRoundMethod(String arg2) {
        String errMsg = "Did not change rounding method due to incorrect " +
                "format.";
        switch (arg2) {
            case CMD_UP:
                matcher.setRoundMethod(CMD_UP);
                break;
            case CMD_DOWN:
                matcher.setRoundMethod(CMD_DOWN);
                break;
            default:
                System.out.println(errMsg);
        }
    }

    private void handleOutputMethod(String arg2) {
        String errMsg = "Did not change output method due to incorrect " +
                "format.";
        switch (arg2) {
            case DEFAULT_OUTPUT_METHOD:
                this.outputMethod = DEFAULT_OUTPUT_METHOD;
                break;
            case CMD_HTML:
                this.outputMethod = "html";
                break;
            default:
                System.out.println(errMsg); //todo: throw exception?
        }
    }

    private void handleResolution(String arg2) {
        // init
        int maxCharsInRow = this.image.getWidth();
        int minCharsInRow = Math.max(1,
                this.image.getWidth() / this.image.getHeight());
        int newRes = this.resolution;
        String outMsg = "Resolution set to %d";
        String boundriesErrMsg = "Did not change resolution due to exceeding" +
                " " +
                "boundaries.";
        String formatErrMsg2 = "Did not change resolution due to incorrect" +
                " format.";
        //
        if (arg2.isEmpty()) {
            System.out.println(String.format(outMsg, this.resolution));
            return;
        } else if (arg2.equals(CMD_UP)) {
            newRes = this.resolution * 2;
        } else if (arg2.equals(CMD_DOWN)) {
            newRes = this.resolution / 2;
        } else { // illegal format
            System.out.println(formatErrMsg2);
            //todo: throw exception?
            return;
        }
        if (newRes < minCharsInRow || newRes > maxCharsInRow) {
            System.out.println(boundriesErrMsg);
            //todo: throw exception?
            return;
        }
        this.resolution = newRes;
        System.out.println(String.format(outMsg, this.resolution));
    }

    private void handleAddRemove(String arg2, boolean isAdd) {
        String cmd = isAdd ? CMD_ADD : CMD_REMOVE;
        if (arg2.isEmpty()) {
            System.out.println("did not " + cmd + " due to incorrect" +
                    " format.");
            return;
        }
        List<Character> charset = new ArrayList<>();
        //todo: handle single char input

        // check input of the format "add m-p"
        if (arg2.length() == 3) {
            if (arg2.charAt(1) == '-') {
                int start = Math.min(arg2.charAt(0), arg2.charAt(2));
                int end = Math.max(arg2.charAt(0), arg2.charAt(2));
                if (MIN_ASCII <= start && end <= MAX_ASCII)
                    fillCharsetList(start, end, charset);
                    // fill charset to matcher
                    // return;
                else {
                    System.out.println("did not " + cmd + " due to incorrect" +
                            " format."); //todo: throw exception?
                    return;
                }
            } // if '-'
        } // if length
        // check input of the format "add/remove all" or "add/remove space"
        switch (arg2) {
            case CMD_ALL:
                fillCharsetList(MIN_ASCII, MAX_ASCII, charset);
                break;
            case CMD_SPACE:
                charset.add(' ');
                break;
            default:
                if (charset.isEmpty()) {
                    System.out.println("did not " + cmd + " due to incorrect" +
                            " format."); //todo: throw exception?
                    return;
                }
        } // switch
        // finally, execute given command according to charset
        executeAddRemoveOnMatcher(isAdd, charset);
    } // method

    private void executeAddRemoveOnMatcher(boolean isAdd,
                                           List<Character> charset) {
        if (isAdd)
            for (char c : charset)
                matcher.addChar(c);
        else
            for (char c : charset)
                matcher.removeChar(c);
    }

    private static void fillCharsetList(int start, int end,
                                        List<Character> charset) {
        for (int i = start; i <= end; i++)
            charset.add((char) i);
    }

    /**
     * Main method.
     *
     * @param args command line arguments.
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        // parse args. input format: java shell <imagePath>.
//        String imagePath = args[0];
        // todo: test valid image path
        String imagePath = "/cs/usr/ron.levin1/IdeaProjects/ASCII_Art" +
                "/examples/cat.jpeg";
        Shell shell = new Shell(imagePath, DEFAULT_CHARSET,
                DEFAULT_RES, DEFAULT_ROUND_METHOD);
        shell.run(imagePath);
    }
}
// CLI:
// java ascii_art/Shell.java /cs/usr/ron.levin1/IdeaProjects/ASCII_Art/examples/cat.jpeg
