package correcter;


import java.io.IOException;
import java.util.Scanner;


public class Menu {


    /*
     * Requests which mode should the program run to the user
     */

    private String modeRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a mode:");
        return scanner.next();
    }


    /*
     * Runs program according to requested mode
     */
    public void modeAnswer() throws IOException {
        switch (modeRequest()) {
            case "encode":
                encodeMode();
                break;
            case "send":
                sendMode();
                break;
            case "decode":
                decodeMode();
                break;
            default:
                System.out.println("Error, mode not recognized");
        }
    }


    /*
     * Output information for encode mode
     */
    private void encodeMode() throws IOException {
        FileProcessing fp = new FileProcessing();
        StringProcessor sp = new StringProcessor();
        String pathInput = "send.txt";
        String input = fp.inputReader(pathInput);
        System.out.printf("\n%s\n", pathInput);
        System.out.printf("text view: %s\n", input);
        String inputToHex = sp.formatHexArray(sp.stringToHex(input));
        System.out.printf("hex view: %s\n", inputToHex);
        String inputToByte = sp.byteArrayToString(sp.stringToByteArray(input));
        System.out.printf("bin view: %s\n", inputToByte);
        System.out.println();
        String pathOutput = "encoded.txt";
        System.out.printf("\n%s\n", pathOutput);
        String expandedBytes = sp.expandedBytesHamming(sp.stringToByteArray(input));
        System.out.printf("expand: %s\n", expandedBytes);
        String parityBytes = sp.stringFromStringArray(sp.parityBytes(expandedBytes));
        System.out.printf("parity: %s\n", parityBytes);
        String parityHex = sp.stringFromStringArray(sp.byteArrayToHexArray(sp.parityBytes(expandedBytes)));
        System.out.printf("hex view: %s", parityHex);
        fp.fileWriterEncode(parityBytes);
    }


    /*
     * Output information for send mode
     */
    private void sendMode() throws IOException {
        Randomizer randomizer = new Randomizer();
        FileProcessing fp = new FileProcessing();
        StringProcessor sp = new StringProcessor();
        String pathInput = "encoded.txt";
        byte[] input = fp.inputBytes(pathInput);
        String pathOutput = "received.txt";
        System.out.printf("\n%s\n", pathOutput);
        String errorMessage = sp.byteArrayToString(randomizer.byteRandomizer(input));
        System.out.printf("bin view: %s\n", errorMessage);
        String hexErrorMessage = sp.stringFromStringArray(sp.byteArrayToHexArray(errorMessage.split(" ")));
        System.out.printf("hex view: %s", hexErrorMessage);
        fp.fileWriterSend(errorMessage);
    }




    /*
     * Output information for decode mode
     */
    private void decodeMode() throws IOException {
        FileProcessing fp = new FileProcessing();
        StringProcessor sp = new StringProcessor();
        String pathInput = "received.txt";
        byte[] input = fp.inputBytes(pathInput);
        System.out.printf("\n%s\n", pathInput);

        System.out.println();
        String pathOutput = "decoded.txt";
        System.out.printf("\n%s\n", pathOutput);
        String correctBytes = sp.stringFromStringArray(sp.correctByteStrings(sp.byteArrayToString(input).split(" ")));
        System.out.printf("correct: %s\n", correctBytes);
        String decodedBytes = sp.decodedByteString(correctBytes);
        System.out.printf("decode: %s\n", decodedBytes);
        String removedBytes = sp.removeLastByte(decodedBytes);
        System.out.printf("remove: %s\n", removedBytes);
        String hexClean = sp.stringFromStringArray(sp.byteArrayToHexArray(removedBytes.split(" ")));
        System.out.printf("hex view: %s\n", hexClean);
        String finalText = sp.hexStringToText(hexClean);
        System.out.printf("test view: %s", finalText);
        fp.fileWriterDecode(removedBytes);

    }




}

