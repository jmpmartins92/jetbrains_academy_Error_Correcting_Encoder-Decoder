package correcter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileProcessing {


    /*
     * Returns a string of the read file
     */
    public String inputReader(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader reader = new BufferedReader(fileReader);
        StringBuilder auxString = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            auxString.append(line);
        }
        reader.close();
        return auxString.toString();
    }

    public byte[] inputBytes(String path) throws IOException {
        Path p = Paths.get(path);
        return Files.readAllBytes(p);
    }


    /*
     * Creates a a file from a given array of bytes
     */
    public void incorrectOutput(byte[] errorBytes) throws IOException {
        File received = new File("received.txt");
        FileWriter writer = new FileWriter(received);
        for (byte charbytes : errorBytes) {
            writer.append((char) charbytes);
        }
        writer.close();
    }



    /*
     * Writes data for Encode mode
     */
    public File fileWriterEncode(String parityBytes) throws IOException {
        File encoded = new File("encoded.txt");
        OutputStream outputStream = new FileOutputStream(encoded);
        StringProcessor sp = new StringProcessor();
        String[] byteArray = parityBytes.split(" ");
        for (int i = 0; i < byteArray.length; i++) {
            outputStream.write((byte) Integer.parseInt(byteArray[i], 2));
        }
        return encoded;
    }


    /*
     * Writes data for send mode
     */
    public File fileWriterSend(String errorMessage) throws IOException {
        File received = new File("received.txt");
        OutputStream outputStream = new FileOutputStream(received);
        StringProcessor sp = new StringProcessor();
        String[] byteArray = errorMessage.split(" ");
        for (int i = 0; i < byteArray.length; i++) {
            outputStream.write((byte) Integer.parseInt(byteArray[i], 2));
        }
        return received;
    }

    /*
     * Writes data for send mode
     */
    public File fileWriterDecode(String removedBytes) throws IOException {
        File decoded = new File("decoded.txt");
        OutputStream outputStream = new FileOutputStream(decoded);
        StringProcessor sp = new StringProcessor();
        String[] byteArray = removedBytes.split(" ");
        for (int i = 0; i < byteArray.length; i++) {
            outputStream.write((byte) Integer.parseInt(byteArray[i], 2));
        }
        return decoded;
    }









}

