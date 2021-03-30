package correcter;

import java.util.Random;


public class Randomizer {

    /*
     * Changes randomly a single bit in every given byte of a byteArray
     */
    public byte[] byteRandomizer(byte[] inputBytes) {
        Random random = new Random();
        int byteLength = 8;
        byte[] errorBytes = inputBytes.clone();

        for (int byteIndex = 0; byteIndex < errorBytes.length; byteIndex++) {
            errorBytes[byteIndex] ^= 1 << random.nextInt(byteLength - 1);
        }

        return errorBytes;
    }


}
