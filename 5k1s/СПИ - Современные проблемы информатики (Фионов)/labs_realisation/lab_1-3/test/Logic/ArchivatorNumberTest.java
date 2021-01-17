package Logic;

import org.junit.Test;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArchivatorNumberTest {

    private String testDirectory = "./test_files/";


    private boolean testCodeNumberToRequiredSize(int fi, String numberString, String code) throws Exception {
        ArchivatorNumber archivator = new ArchivarotNumberClass(fi);

        ChunkBits number = new ChunkBitsClass(numberString);
        ChunkBits expectedArchiving = new ChunkBitsClass(code);
        ChunkBits actualArchiving = archivator.codeNumberToRequiredSize(number);
        return expectedArchiving.equals(actualArchiving);
    }

    private boolean testCodeNumberToRequiredSize(int fi, int numberInt, String code) throws Exception {
        assert (fi > 0);
        ArchivatorNumber archivator = new ArchivarotNumberClass(fi);

        ChunkBits number = new ChunkBitsClass(numberInt);
        ChunkBits expectedArchiving = new ChunkBitsClass(code);
        ChunkBits actualArchiving = archivator.codeNumberToRequiredSize(number);
        return expectedArchiving.equals(actualArchiving);
    }

    @Test
    public void codeNumberToRequiredSizeF0_0() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "0", "1"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_1() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "1", "01"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_2() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "10", "001"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_3() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "11", "0001"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_4() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "100", "00001"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_5() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "101", "000001"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_6() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "110", "0000001"));
    }

    @Test
    public void codeNumberToRequiredSizeF0_7() throws Exception {
        assert (testCodeNumberToRequiredSize(0, "111", "00000001"));
    }

    @Test
    public void codeNumberToRequiredSizeF1_0() throws Exception {
        assert (testCodeNumberToRequiredSize(1, "0", "1"));
    }

    @Test
    public void codeNumberToRequiredSizeF1_1() throws Exception {
        assert (testCodeNumberToRequiredSize(1, "1", "01 "));
    }

    @Test
    public void codeNumberToRequiredSizeF1_2() throws Exception {
        assert (testCodeNumberToRequiredSize(1, "10", "001 0"));
    }

    @Test
    public void codeNumberToRequiredSizeF1_3() throws Exception {
        assert (testCodeNumberToRequiredSize(1, "11", "001 1"));
    }

    @Test
    public void codeNumberToRequiredSizeF1_4() throws Exception {
        assert (testCodeNumberToRequiredSize(1, "100", "0001 00"));
    }

    @Test
    public void codeNumberToRequiredSizeF1_m100() throws Exception {
        assert (testCodeNumberToRequiredSize(
                1,
                -100,
                "00000000 00000000 00000000 00000000 11111111 11111111 11111111 10011100")
        );
    }

    @Test
    public void codeNumberToRequiredSizeF1_65() throws Exception {
        assert (testCodeNumberToRequiredSize(1, "1000001", "00000001 000001"));
    }

    @Test
    public void codeNumberToRequiredSizeF2_0() throws Exception {
        assert (testCodeNumberToRequiredSize(2, "0", "1"));
    }

    @Test
    public void codeNumberToRequiredSizeF2_1() throws Exception {
        assert (testCodeNumberToRequiredSize(2, "1", "01"));
    }

    @Test
    public void codeNumberToRequiredSizeF2_2() throws Exception {
        assert (testCodeNumberToRequiredSize(2, "10", "001 0 0"));
    }

    @Test
    public void codeNumberToRequiredSizeF2_3() throws Exception {
        assert (testCodeNumberToRequiredSize(2, "11", "001 0 1"));
    }

    @Test
    public void codeNumberToRequiredSizeF2_4() throws Exception {
        assert (testCodeNumberToRequiredSize(2, "100", "001 1 00"));
    }

    @Test
    public void codeNumberToRequiredSizeF2_8() throws Exception {
        assert (testCodeNumberToRequiredSize(2, "1000", "0001 00 000"));
    }

    @org.junit.jupiter.api.Test
    void isExistRemainInBuffer() throws Exception {
        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(0);
        archivatorNumber.addToBuffer(new ChunkBitsClass("1111"));
        assert (archivatorNumber.isExistRemainInBuffer());
    }

    @org.junit.jupiter.api.Test
    void decodeOneNumberFromBuffer_fi0_4() throws Exception {
        assert (testDecodeOneNumberFromBuffer(0, 4));
    }

    @org.junit.jupiter.api.Test
    void decodeOneNumberFromBuffer_fi1_0() throws Exception {
        assert (testDecodeOneNumberFromBuffer(1, 0));
    }

    @org.junit.jupiter.api.Test
    void decodeOneNumberFromBuffer_fi0_fori() throws Exception {
        for (int i = 0; i < 100; i++) {
            assert (testDecodeOneNumberFromBuffer(0, i));
        }
    }

    @org.junit.jupiter.api.Test
    void decodeOneNumberFromBuffer_fi1_fori() throws Exception {
//        for (int i = 65; i < 100; i++) {
//        for (int i = 0; i < 100; i++) {
        for (int i = -100; i < 100; i++) {
            String message = "number code and decode: " + i;
            assertTrue(testDecodeOneNumberFromBuffer(1, i));
        }
    }

    private boolean testDecodeOneNumberFromBuffer(int fi, int numberInt) throws Exception {
        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(fi);
        ChunkBits number = new ChunkBitsClass(numberInt);
        ChunkBits imitationDataFromArchive = archivatorNumber.codeNumberToRequiredSize(number);
        archivatorNumber.addToBuffer(imitationDataFromArchive);
        ChunkBits decode = archivatorNumber.decodeOneNumberFromBuffer();
        int actual = decode.getInt();
        return numberInt == actual;
    }


    @org.junit.jupiter.api.Test
    void deleteFirstCodeNumber_01() throws Exception {
        ChunkBits imitationDataFromArchive = new ChunkBitsClass("010101");
        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(0);
        archivatorNumber.addToBuffer(imitationDataFromArchive);
        archivatorNumber.deleteFirstCodeNumber();
        archivatorNumber.deleteFirstCodeNumber();
        archivatorNumber.deleteFirstCodeNumber();

        assertEquals(false, archivatorNumber.isExistRemainInBuffer());
    }

    @org.junit.jupiter.api.Test
    void deleteFirstCodeNumber_1() throws Exception {
        ChunkBits imitationDataFromArchive = new ChunkBitsClass("111");
        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(0);
        archivatorNumber.addToBuffer(imitationDataFromArchive);
        archivatorNumber.deleteFirstCodeNumber();
        archivatorNumber.deleteFirstCodeNumber();
        archivatorNumber.deleteFirstCodeNumber();

        assertEquals(false, archivatorNumber.isExistRemainInBuffer());
    }

    @org.junit.jupiter.api.Test
    void deleteFirstCodeNumber_10() throws Exception {
        ChunkBits imitationDataFromArchive = new ChunkBitsClass("0010 0010 0010");
        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(1);
        archivatorNumber.addToBuffer(imitationDataFromArchive);
        archivatorNumber.deleteFirstCodeNumber();
        archivatorNumber.deleteFirstCodeNumber();
        archivatorNumber.deleteFirstCodeNumber();

        assertEquals(false, archivatorNumber.isExistRemainInBuffer());
    }


    @org.junit.jupiter.api.Test
    void decodeFileInFileIntFormat() throws Exception {
        String addressOriginal = testDirectory + "arrayByte.bin";
        String codeFile = testDirectory + "code.bin";
        writeArrayInFile(addressOriginal);

        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(1);
        String orinal = addressOriginal;
        archivatorNumber.codeFileInFileIntFormat(orinal, codeFile);
        String resultDecode = testDirectory + "backArrayByte.bin";
        archivatorNumber.decodeFileInFileIntFormat(codeFile, resultDecode);

        byte[] f1 = Files.readAllBytes(
                Path.of((new File(orinal)).getAbsolutePath()));
        byte[] f2 = Files.readAllBytes(Path.of((new File(resultDecode)).getAbsolutePath()));

        assert (Arrays.equals(f1, f2));
    }

    @org.junit.jupiter.api.Test
    void decodeFileInFileIntFormat_nonMultipleByte() throws Exception {
        String addressOriginal = testDirectory + "arrayByte.bin";
        String codeFile = testDirectory + "code.bin";
        writeArrayInFile_nonMultiple(addressOriginal);
        ArchivatorNumber archivatorNumber = new ArchivarotNumberClass(1);
        String orinal = addressOriginal;
        archivatorNumber.codeFileInFileIntFormat(orinal, codeFile);
        String resultDecode = testDirectory + "backArrayByte.bin";
        archivatorNumber.decodeFileInFileIntFormat(codeFile, resultDecode);
        Path path = FileSystems.getDefault().getPath("logs", "access.log");
        byte[] f1 = Files.readAllBytes(Path.of((new File(orinal)).getAbsolutePath()));
        byte[] f2 = Files.readAllBytes(Path.of((new File(resultDecode)).getAbsolutePath()));

        assert (Arrays.equals(f1, f2));
    }

    private void writeArrayInFile(String address) throws IOException {
        int[] data = {0, 0, 0, 0, 0, 0, 0, 0};
        FileOutputStream file = new FileOutputStream(address);
        for (int i = 0; i < data.length; i++)
            file.write(ByteBuffer.allocate(4).putInt(data[i]).array());
        file.close();
    }

    private void writeArrayInFile_nonMultiple(String address) throws IOException {
        int[] data = {0, 0, 0, 0, 0, 0};
        FileOutputStream file = new FileOutputStream(address);
        for (int i = 0; i < data.length; i++)
            file.write(ByteBuffer.allocate(4).putInt(data[i]).array());
        file.close();
    }
}