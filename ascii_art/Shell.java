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

    // all ascii chars in range 32 to 126, including.
    // private static final char[] CHARSET_ALL_ASCII = new char[95];
    // fields
    private SubImgCharMatcher matcher;
    private Image image;
    private int resolution;
    private SubImagesHolder subImgsHolder;
    private String outputMethod;

    public Shell(char[] charset, int resolution,
                 String roundMethod) {

        this.matcher = new SubImgCharMatcher(charset, roundMethod);
        this.resolution = resolution;
        this.outputMethod = DEFAULT_OUTPUT_METHOD;
        VALID_COMMANDS.addAll(Arrays.asList(VALID_COMMANDS_ARR));
    }

    public void run(String imageName) {
        try {
            // Init and pad image fields
            Image original_image = new Image(imageName);
            ImagePadder padder = new ImagePadder(original_image);
            this.image = padder.pad();
            this.subImgsHolder = new SubImagesHolder(image, resolution);
            // todo: maybe invalid imageName should not terminate the
            //  program? check this!
        } catch (IOException e) {
            throw new RuntimeException(e); // todo
        }
        // Main user input loop
        while (true) {
            System.out.print(">>> "); // todo: change print location
            String[] userInput = KeyboardInput.readLine().split(" ");
            String arg1 = userInput[0];
            if (arg1.equals(CMD_EXIT))
                break;
            String arg2 = "";
            if (userInput.length > 1)
                arg2 = userInput[1];
//                // -----------FOR TESTING------------------
//                arg1 = "add";
//                arg2 = "$";
//                // ---------------------------------
            try {
                executeCommand(arg1, arg2);
            } catch (InvalidCommandException e) {
                e.printStackTrace(); // todo: handle exception
            } catch (Exception e) {
                throw new RuntimeException(e); // todo: handle exception
            }
        } // while
    } // method

    private void executeCommand(String arg1, String arg2) throws Exception {
        String errMsg = "Did not execute due to incorrect command.";
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
    }

    private void handleRun() throws Exception {
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

    private void handleRoundMethod(String arg2) throws Exception {
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

    private void handleOutputMethod(String arg2) throws Exception {
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

    private void handleResolution(String arg2) throws Exception {
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

    private void handleAddRemove(String arg2, boolean isAdd) throws Exception {
        String cmd = isAdd ? CMD_ADD : CMD_REMOVE;
        if (arg2.isEmpty()) {
            System.out.println("did not " + cmd + " due to incorrect" +
                    " format.");
            return;
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
            //todo: check if overlapping between flags is possible?
            executeAddRemoveOnMatcher(isAdd, charset);
            return;
        }
        if (flag1 == EXIT_FAILURE || flag2 == EXIT_FAILURE || flag3 ==
                EXIT_FAILURE) {
            System.out.println("did not " + cmd + " due to incorrect" +
                    " format.");
            return;
        }
        // flags are EXIT_CHECK_NEXT_CONDITION
        //todo: reformat err msg
        System.out.println("did not " + cmd + " due to incorrect" +
                " format.");
    } // method

    /**
     * @return 1 if successful, 0 if failed, -1 if not a single char.
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
     * @return true if successful, false otherwise.
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
     * @return 1 if successful, 0 if failed, -1 if not a single char.
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
     * @throws IOException if an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        // parse args. input format: java shell <imagePath>.
//        String imagePath = args[0];
        // todo: test valid image path
        String imagePath = "/cs/usr/ron.levin1/IdeaProjects/ASCII_Art" +
                "/examples/cat.jpeg";
        Shell shell = new Shell(DEFAULT_CHARSET,
                DEFAULT_RES, DEFAULT_ROUND_METHOD);
        shell.run(imagePath);
    }
}
// CLI:
// java ascii_art/Shell.java /cs/usr/ron
// .levin1/IdeaProjects/ASCII_Art/examples/cat.jpeg
