package test.custom.app.encrypt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class TestFileToTxt {
    public static void main(String[] args) {
//        parseDataToTxt();
        parseTxtToData();
    }

    public static void parseTxtToData() {
        System.out.println(" Starting parsing txt to data~ ");
        File file = new File("D:\\2.txt");
        if (file.exists() && file.isFile()) {
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            FileOutputStream fileOutputStream = null;
            DataOutputStream dataOutputStream = null;
            try {
                fileReader = new FileReader(file);
                StringBuilder readStringBuilder = new StringBuilder();
                bufferedReader = new BufferedReader(fileReader);
                String line = "";
                int count = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    if (count == 0) {
                        readStringBuilder.append(line);
                    } else {
                        readStringBuilder.append(getLineSeparator()).append(line);
                    }
                    count++;
                }
                byte[] parsedBytes = hexStringToBytes(readStringBuilder.toString());

                fileOutputStream = new FileOutputStream("D:\\3");
                dataOutputStream = new DataOutputStream(fileOutputStream);
                for (byte temp : parsedBytes) {
                    dataOutputStream.write(temp);
                }
                System.out.println(" Parsing success~ ");
            } catch (IOException e) {
                System.out.println(" Parsing failed~ ");
                e.printStackTrace();
            } finally {
                try {
                    if (fileReader != null) {
                        fileReader.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (dataOutputStream != null) {
                        dataOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void parseDataToTxt() {
        System.out.println(" Starting parsing data to txt~ ");
        File file = new File("D:\\1");
        if (file.exists() && file.isFile()) {
            FileInputStream fis = null;
            DataInputStream dis = null;
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fis = new FileInputStream("D:\\1");
                dis = new DataInputStream(fis);
                fileWriter = new FileWriter("D:\\2.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                int temp;
                while ((temp = dis.read()) != -1) {
                    final String newTemp = byteToHexString((byte) temp);
                    bufferedWriter.write(newTemp);
                }
                System.out.println(" Parsing success~ ");
            } catch (IOException e) {
                System.out.println(" Parsing failed~ ");
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (dis != null) {
                        dis.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String byteToHexString(byte sourceByte) {
        String stringReturn = "";
        StringBuilder builder = new StringBuilder();
        int convertedInteger = sourceByte & 0xff;
        String convertedString = Integer.toHexString(convertedInteger);
        if (convertedString.length() < 2) {
            builder.append(0);
        }
        builder.append(convertedString);
        stringReturn = builder.toString().toUpperCase(Locale.getDefault());

        return stringReturn;
    }

    public static byte[] hexStringToBytes(String sourceHexString) {
        byte[] bytesReturn = null;

        if (isNotEmpty(sourceHexString)) {
            final int sourceStringLength = sourceHexString.length();
            if ((sourceStringLength % 2) == 0) {
                final char[] sourceHexChars = sourceHexString.toCharArray();
                final int bytesLength = sourceStringLength / 2;
                bytesReturn = new byte[bytesLength];
                for (int i = 0; i < bytesLength; i++) {
                    final int positionInChars = i * 2;
                    final char highFourBitChar = sourceHexChars[positionInChars];
                    final char lowForBitChar = sourceHexChars[positionInChars + 1];
                    final byte highFourBitByte = hexCharToByte(highFourBitChar);
                    final byte lowFourBitByte = hexCharToByte(lowForBitChar);

                    bytesReturn[i] = (byte) ((highFourBitByte << 4) | lowFourBitByte);
                }
            }
        }

        return bytesReturn;
    }

    public static byte hexCharToByte(char sourceChar) {
        byte byteReturn = 0;
        final String judgeString = "0123456789ABCDEF";
        byteReturn = (byte) judgeString.indexOf(sourceChar);
        return byteReturn;
    }

    public static boolean isEmpty(CharSequence stringToJudge) {
        boolean isEmpty = false;
        if (stringToJudge == null || stringToJudge.length() == 0) {
            isEmpty = true;
        } else {
            isEmpty = false;
        }
        return isEmpty;
    }

    public static boolean isNotEmpty(CharSequence stringToJudge) {
        return !isEmpty(stringToJudge);
    }

    public static String getLineSeparator() {
        return System.getProperty("line.separator", "\n");
    }
}