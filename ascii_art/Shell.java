package ascii_art;


import ascii_art.new_exceptions.*;
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

/**
 * The Shell class provides a command-line interface for generating ASCII art
 * from images. It supports various commands for manipulating the character
 * set,
 * resolution, output method, and more.
 */
public class Shell {
    // default args
    private static final char[] DEFAULT_CHARSET = "0123456789".toCharArray();
    private static final String DEFAULT_OUTPUT_METHOD = "console";
    private static final String DEFAULT_ROUND_METHOD = "abs";
    private static final int DEFAULT_RES = 2;
    //more consts
    private static final String DEFAULT_FONT = "Courier New";
    private static final int MIN_ASCII = 32;
    private static final int MAX_ASCII = 126;
    private static final int EXIT_CHECK_NEXT_CONDITION = -1;
    private static final int EXIT_FAILURE = 0;
    private static final int EXIT_SUCCESS = 1;
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
    private static final String CMD_HTML = "html";

    // fields
    private Image image;
    private int resolution;
    private String outputMethod;
    private SubImagesHolder subImgsHolder;
    private SubImgCharMatcher matcher;

    /**
     * Constructs a Shell object with the specified charset, resolution, and
     * round method.
     *
     * @param charset     the character set to use for ASCII art
     * @param resolution  the resolution of the ASCII art
     * @param roundMethod the method to use for rounding brightness values
     */
    public Shell(char[] charset, int resolution,
                 String roundMethod) {

        this.matcher = new SubImgCharMatcher(charset, roundMethod);
        this.resolution = resolution;
        this.outputMethod = DEFAULT_OUTPUT_METHOD;
        VALID_COMMANDS.addAll(Arrays.asList(VALID_COMMANDS_ARR));
    }

    /**
     * Runs the Shell with the specified image name.
     *
     * @param imageName the name of the image file to process
     */
    public void run(String imageName) {
        try {
            // Init and pad image fields
            Image original_image = new Image(imageName);
            ImagePadder padder = new ImagePadder(original_image);
            this.image = padder.pad();
            this.subImgsHolder = new SubImagesHolder(image, resolution);
        } catch (IOException e) {
            // todo: print err msg?
            return; // terminate program
        }
        // Main user input loop
        while (true) {
            System.out.print(">>> ");
            String[] userInput = KeyboardInput.readLine().split(" ");
            String arg1 = userInput[0];
            if (arg1.equals(CMD_EXIT))
                break;
            String arg2 = "";
            if (userInput.length > 1)
                arg2 = userInput[1];
            try {
                executeCommand(arg1, arg2);
            } catch (AsciiArtException e) {
                System.out.println(e.getMessage());
            }
        } // while
    } // method

    /**
     * Executes the specified command with the given argument.
     *
     * @param arg1 the command to execute
     * @param arg2 the argument for the command
     * @throws AsciiArtException if an error occurs while executing the command
     */
    private void executeCommand(String arg1, String arg2) throws AsciiArtException {
        // execute cmd (ignore its tail if exists).
        switch (arg1) {
            case CMD_CHARS:
                matcher.printCharset();
                break;
            case CMD_ADD:
                handleAddRemove(arg2, true);
                break;
            case CMD_REMOVE:
                handleAddRemove(arg2, false);
                break;
            case CMD_RES:
                handleResolution(arg2);
                break;
            case CMD_ROUND:
                handleRoundMethod(arg2);
                break;
            case CMD_OUTPUT:
                handleOutputMethod(arg2);
                break;
            case CMD_RUN:
                handleRun();
                break;
            default:
                // throw incorrect command exception
                throw new ExecutionCommandException(false);
        } // switch
    }

    private void handleRun() throws AsciiArtException {
        if (this.matcher.getCharsetSize() < 2) {
            // throw a 'charset too small' exception
            throw new ExecutionCommandException(true);
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

    private void handleRoundMethod(String arg2) throws AsciiArtException {
        switch (arg2) {
            case DEFAULT_ROUND_METHOD:
                matcher.setRoundMethod(DEFAULT_ROUND_METHOD);
                break;
            case CMD_UP:
                matcher.setRoundMethod(CMD_UP);
                break;
            case CMD_DOWN:
                matcher.setRoundMethod(CMD_DOWN);
                break;
            default:
                throw new RoundingMethodException();
        }
    }

    private void handleOutputMethod(String arg2) throws AsciiArtException {
        switch (arg2) {
            case DEFAULT_OUTPUT_METHOD:
                this.outputMethod = DEFAULT_OUTPUT_METHOD;
                break;
            case CMD_HTML:
                this.outputMethod = "html";
                break;
            default:
                throw new OutputMethodException();
        }
    }

    private void handleResolution(String arg2) throws AsciiArtException {
        // init
        int maxCharsInRow = this.image.getWidth();
        int minCharsInRow = Math.max(1,
                this.image.getWidth() / this.image.getHeight());
        int newRes = this.resolution;
        String outMsg = "Resolution set to %d";
        //
        if (arg2.isEmpty()) {
            System.out.println(String.format(outMsg, this.resolution));
            return;
        } else if (arg2.equals(CMD_UP)) {
            newRes = this.resolution * 2;
        } else if (arg2.equals(CMD_DOWN)) {
            newRes = this.resolution / 2;
        } else { // illegal format
            throw new ResolutionException(true);
        }
        if (newRes < minCharsInRow || newRes > maxCharsInRow) {
            throw new ResolutionException(false);
        }
        // update res & create new subImagesHolder
        this.resolution = newRes;
        this.subImgsHolder = new SubImagesHolder(image, resolution);
        System.out.println(String.format(outMsg, this.resolution));
    }

    private void handleAddRemove(String arg2, boolean isAdd) throws AsciiArtException {
        String cmd = isAdd ? CMD_ADD : CMD_REMOVE;
        if (arg2.isEmpty()) {
            throw new CharsetException(isAdd);
        }
        List<Character> charset = new ArrayList<>();
        /*
        flag values meaning:
        1 if successful -> add char to charset and return.
        0 if failed -> print error message and return.
        -1 if not a single char -> continue to next check.
         */
        int flag1 = trySingleCharCMD(arg2, charset);
        int flag2 = tryAllOrSpaceCMD(arg2, charset, cmd);
        int flag3 = tryRangeCMD(arg2, charset, cmd);
        if (flag1 == EXIT_SUCCESS || flag2 == EXIT_SUCCESS || flag3 ==
                EXIT_SUCCESS) {
            executeAddRemoveOnMatcher(isAdd, charset);
            return;
        }
        // otherwise, print error message.
        throw new CharsetException(isAdd);
        //todo: delete after testing.
//        if (flag1 == EXIT_FAILURE || flag2 == EXIT_FAILURE || flag3 ==
//                EXIT_FAILURE) {
//            throw new CharsetException(isAdd);
//        }
        // flags are EXIT_CHECK_NEXT_CONDITION
//        throw new CharsetException(isAdd);
    } // method

    /**
     * @return 1 if successful, 0 if failed completely, or -1 to continue to
     * next check.
     */
    private static int trySingleCharCMD(String arg2, List<Character> charset) {
        if (arg2.length() == 1) {
            int asciiVal = arg2.charAt(0);
            if (MIN_ASCII <= asciiVal && asciiVal <= MAX_ASCII) {
                charset.add(arg2.charAt(0));
                return EXIT_SUCCESS;
            }
            // single char is illegal
            return EXIT_FAILURE;
        }
        // maybe arg2 is not a single char
        return EXIT_CHECK_NEXT_CONDITION;
    }

    /**
     * @return 1 if successful, 0 if failed completely, or -1 to continue to
     * next check.
     */
    private int tryRangeCMD(String arg2, List<Character> charset,
                            String cmd) {
        // check input of the format "add a-z"
        if (arg2.length() == 3) {
            if (arg2.charAt(1) == '-') {
                int start = Math.min(arg2.charAt(0), arg2.charAt(2));
                int end = Math.max(arg2.charAt(0), arg2.charAt(2));
                if (MIN_ASCII <= start && end <= MAX_ASCII) {
                    fillCharsetList(start, end, charset);
                    // fill charset to matcher
                    return EXIT_SUCCESS;
                } // if range
                // range is illegal
                return EXIT_FAILURE;
            } // if '-'
            // maybe arg2 is "all".
            return EXIT_CHECK_NEXT_CONDITION;
        } // if length
        // maybe arg2 is "space".
        return EXIT_CHECK_NEXT_CONDITION;
    }

    /**
     * @return 1 if successful, 0 if failed completely, or -1 to continue to
     * next check.
     */
    private int tryAllOrSpaceCMD(String arg2, List<Character> charset,
                                 String cmd) {
        // check input of the format "add/remove all" or "add/remove space"
        switch (arg2) {
            case CMD_ALL:
                fillCharsetList(MIN_ASCII, MAX_ASCII, charset);
                return EXIT_SUCCESS;
            case CMD_SPACE:
                charset.add(' ');
                return EXIT_SUCCESS;
            default:
                return EXIT_CHECK_NEXT_CONDITION;
        } // switch
    }

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
     */
    public static void main(String[] args) {
        // parse args. input format: java shell <imagePath>.
//        String imagePath = "/cs/usr/ron.levin1/IdeaProjects/ASCII_Art" +
//                "/examples/cat.jpeg";
        Shell shell = new Shell(DEFAULT_CHARSET,
                DEFAULT_RES, DEFAULT_ROUND_METHOD);
//        shell.run(imagePath);
        shell.run(args[0]);
    }
}
// CLI:
// java ascii_art/Shell.java /cs/usr/ron
// .levin1/IdeaProjects/ASCII_Art/ascii_art/Shell.java

// presubmit: CLI
// ~oop1/ex3_presubmit /cs/usr/ron.levin1/Desktop/ex3.zip
