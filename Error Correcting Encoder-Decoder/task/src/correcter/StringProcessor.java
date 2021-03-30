package correcter;

public class StringProcessor {

       /*
     * Converts a normal string to a hex Array
     */
    public String[] stringToHex(String stringToHex) {
        char[] hexArray = stringToHex.toCharArray();
        String[] hexStringArray = new String[stringToHex.length()];
        for (int i = 0; i < hexArray.length; i++) {
            hexStringArray[i] = Integer.toHexString(hexArray[i]);
        }
        return hexStringArray;
    }

    public String hexStringToText(String stringHex) {
        String[] hexArray = stringHex.split(" ");
        StringBuilder text = new StringBuilder();
        for (String hex : hexArray) {
            text.append((char) Integer.parseInt(hex, 16));
        }
        return text.toString();
    }

    /*
     * Returns a string with a more readable format of the hex String array
     */
    public String formatHexArray(String[] hexStringArray) {
        StringBuilder auxString = new StringBuilder();
        for (String s : hexStringArray) {
            auxString.append(s).append(" ");
        }
        return auxString.toString();
    }

    /*
     * Creates an array of bytes from a given String
     */
    public byte[] stringToByteArray(String string) {
        char[] arrayString = string.toCharArray();
        byte[] byteArray = new byte[arrayString.length];

        for (int i = 0; i < arrayString.length; i++) {
            byteArray[i] = (byte)arrayString[i];
        }
        return byteArray;
    }

    /*
     * Returns a byte representation as string, with 8 bits;
     */
    public String byteToString(byte b) {
        StringBuilder bString = new StringBuilder();
        bString.append(Integer.toBinaryString(b));
        if (bString.length() > 8) {
            bString.delete(0, 24);
        }
        while (bString.length() < 8) {
            bString.insert(0, "0");
        }
        return bString.toString();
    }

    /*
     * Returns a string with a more readable format of the byte array
     */
    public String byteArrayToString(byte[] byteArray) {
        StringBuilder auxString = new StringBuilder();
        for (byte b : byteArray) {
            auxString.append(byteToString(b)).append(" ");
        }
        return auxString.toString();
    }

    /*
     * Returns a string with a more readable format of a String array
     */
    public String stringFromStringArray(String[] StringArray) {
        StringBuilder auxString = new StringBuilder();
        for (String value : StringArray) {
            auxString.append(value).append(" ");
        }
        auxString.replace(auxString.length() - 1, auxString.length() - 1, "");
        return auxString.toString();
    }

    /*
     * Returns an array of bytes with parity
     */
    public String[] parityBytes(String expandedBytes) {
        String[] expandedByteArray = expandedBytes.split(" ");
        String[] parityByteArray = new String[expandedByteArray.length];
        for (int byteIndex = 0; byteIndex < expandedByteArray.length; byteIndex++) {
            parityByteArray[byteIndex] = byteParityHamming(expandedByteArray[byteIndex]);
        }
        return parityByteArray;
    }




    /*
     * Returns an hex String array from a byte String array
     */
    public String[] byteArrayToHexArray(String[] byteArray) {
        String[] hexArray = new String[byteArray.length];
        byte[] byteArrayAux = new byte[byteArray.length];
        for (int index = 0; index < byteArray.length; index++) {
            byteArrayAux[index] = (byte) Integer.parseInt(byteArray[index], 2);
        }
        for (int index = 0; index < byteArray.length; index++) {
            hexArray[index] = String.format("%02X", byteArrayAux[index]);
        }
        return hexArray;
    }

    /*
     * Corrects a String array of bytes
     */
    public String[] correctByteStrings(String[] byteStringArrays) {
        String[] correctByteStrings = new String[byteStringArrays.length];
        for (int index = 0; index < correctByteStrings.length; index++) {
            correctByteStrings[index] = correctByte(byteStringArrays[index]);
        }
        return correctByteStrings;
    }



    /*
     * Corrects the bytes using Hamming correction.
     */
    public String correctByte(String byteString) {
        StringBuilder correctByte = new StringBuilder();
        int bit1Parity = Character.getNumericValue(byteString.charAt(0));
        int bit2Parity = Character.getNumericValue(byteString.charAt(1));
        int bit1Significant = Character.getNumericValue(byteString.charAt(2));
        int bit3Parity = Character.getNumericValue(byteString.charAt(3));
        int bit2Significant = Character.getNumericValue(byteString.charAt(4));
        int bit3Significant = Character.getNumericValue(byteString.charAt(5));
        int bit4Significant = Character.getNumericValue(byteString.charAt(6));
        int lastBit = Character.getNumericValue(byteString.charAt(7));
        int parity1 = bit1Significant ^ bit2Significant ^ bit4Significant;
        int parity2 = bit1Significant ^ bit3Significant ^ bit4Significant;
        int parity3 = bit2Significant ^ bit3Significant ^ bit4Significant;


        if (lastBit != 0) {
            correctByte.append(byteString, 0, 7);
            correctByte.append("0");
            return correctByte.toString();

        } else if (bit1Parity != parity1 && bit2Parity != parity2 && bit3Parity != parity3) {
            correctByte.append(byteString, 0, 6);
            correctByte.append(switchBit(bit4Significant));
            correctByte.append("0");
            return correctByte.toString();

        }else if (bit1Parity != parity1 && bit2Parity != parity2) {
            correctByte.append(byteString, 0, 2);
            correctByte.append(switchBit(bit1Significant));
            correctByte.append(byteString, 3, 8);
            return correctByte.toString();

        } else if (bit1Parity != parity1 && bit3Parity != parity3) {
            correctByte.append(byteString, 0, 4);
            correctByte.append(switchBit(bit2Significant));
            correctByte.append(byteString, 5, 8);
            return correctByte.toString();

        } else if (bit2Parity != parity2 && bit3Parity != parity3) {
            correctByte.append(byteString, 0, 5);
            correctByte.append(switchBit(bit3Significant));
            correctByte.append(byteString, 6, 8);
            return correctByte.toString();

        } else if (bit1Parity != parity1) {
            correctByte.append(parity1);
            correctByte.append(byteString, 1, 8);
            return correctByte.toString();

        } else if (bit2Parity != parity2) {
            correctByte.append(bit1Parity).append(parity2);
            correctByte.append(byteString, 2, 8);
            return correctByte.toString();

        } else if (bit3Parity != parity3) {
            correctByte.append(byteString, 0, 3);
            correctByte.append(parity3);
            correctByte.append(byteString, 4, 8);
            return correctByte.toString();
        } else {
            return "error not found";
        }


    }

    private String switchBit(int bit) {
        if (bit == 0) {
            return "1";
        } else {
            return "0";
        }
    }

    /*
     * Decodes the full corrected string by removing parity bits.
     */
    public String decodedByteString(String byteString) {
        byteString = byteString.replace(" ", "");
        StringBuilder decodedBytes = new StringBuilder();

        int bitEnd = 0;
        try {
            for (int bitIndex = 0; bitIndex < byteString.length(); bitIndex++) {
                bitIndex = bitIndex + 2;
                decodedBytes.append(byteString.charAt(bitIndex));
                bitIndex = bitIndex + 2;
                decodedBytes.append(byteString, bitIndex, bitIndex + 3);
                bitIndex = bitIndex + 3;
                bitEnd++;

                if (bitEnd == 2) {
                    decodedBytes.append(" ");
                    bitEnd = 0;
                }

            }
        } catch (IndexOutOfBoundsException ignored) {}

        return decodedBytes.toString();
    }

    /*
     * Removes useless last 0 bits
     */
    public String removeLastByte(String byteString) {
        String[] bytes = byteString.split(" ");
        StringBuilder cleanString = new StringBuilder();
        if (!bytes[bytes.length - 1].contains("1")) {
            for (int byteIndex = 0; byteIndex < bytes.length - 1 ; byteIndex++) {
                cleanString.append(bytes[byteIndex]).append(" ");
            }
            return cleanString.deleteCharAt(cleanString.length() - 1).toString();
        } else {
            return byteString;
        }
    }


    /**
     * @param inputByteArray Transforms a byteArray in an expansion of bits compatible with hamming correction
     * @return String
     */
    public String expandedBytesHamming(byte[] inputByteArray) {
        String bitString = byteArrayToString(inputByteArray).replace(" ", "");
        StringBuilder expandedByteHamming = new StringBuilder();
        int bitNumber = 0;
        for (char bit : bitString.toCharArray()) {

            if (bitNumber == 0) {
                expandedByteHamming.append("..").append(bit).append(".");
                bitNumber++;
            } else if (bitNumber == 3) {
                expandedByteHamming.append(bit).append(". ");
                bitNumber = 0;
            } else {
                expandedByteHamming.append(bit);
                bitNumber++;
            }

        }

        return expandedByteHamming.toString();
    }


    /*
     * Replaces the dots into bits of parity according to the Hamming correction
     */
    public String byteParityHamming(String byteExpanded) {
        StringBuilder parityHamming = new StringBuilder();
        int significantBit1 = Character.getNumericValue(byteExpanded.charAt(2));
        int significantBit2 = Character.getNumericValue(byteExpanded.charAt(4));
        int significantBit3 = Character.getNumericValue(byteExpanded.charAt(5));
        int significantBit4 = Character.getNumericValue(byteExpanded.charAt(6));

        int parity1 = significantBit1 ^ significantBit2 ^ significantBit4;
        parityHamming.append(parity1);
        int parity2 = significantBit1 ^ significantBit3 ^ significantBit4;
        parityHamming.append(parity2).append(significantBit1);
        int parity3 = significantBit2 ^ significantBit3 ^ significantBit4;
        parityHamming.append(parity3).append(significantBit2).append( significantBit3).append(significantBit4).append("0");

        return parityHamming.toString();
    }





}

