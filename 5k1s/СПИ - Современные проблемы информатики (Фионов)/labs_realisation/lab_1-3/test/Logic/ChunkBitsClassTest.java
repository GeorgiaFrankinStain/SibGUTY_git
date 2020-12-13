package Logic;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ChunkBitsClassTest {

    @Test
    public void initialization_stringArgumentContentNotOnlyZerosAndOnes() {
        boolean errorExist = false;
        String actualTextError = "";
        try {
            ChunkBits chunkBits = new ChunkBitsClass("0123");
        } catch (IllegalArgumentException error) {
            errorExist = true;
            actualTextError = error.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert(errorExist);
        String expectedStringError = "the string contains not only zeros and ones";
        assertEquals(expectedStringError, actualTextError);
    }
    @Test
    public void initialization_stringArgumentNotContentNotOnlyZerosAndOnes() {
        boolean errorExist = false;
        String actualTextError = "";
        try {
            ChunkBits chunkBits = new ChunkBitsClass("01010101");
        } catch (IllegalArgumentException error) {
            errorExist = true;
            actualTextError = error.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert(!errorExist);
    }
    @Test
    public void initialization_stringArgumentNotContentOnlyZeros() {
        boolean errorExist = false;
        String actualTextError = "";
        try {
            ChunkBits chunkBits = new ChunkBitsClass("00000");
        } catch (IllegalArgumentException error) {
            errorExist = true;
            actualTextError = error.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert(!errorExist);
    }
    @Test
    public void initialization_stringArgumentNotContentOnlyOnes() {
        boolean errorExist = false;
        String actualTextError = "";
        try {
            ChunkBits chunkBits = new ChunkBitsClass("1111");
        } catch (IllegalArgumentException error) {
            errorExist = true;
            actualTextError = error.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert(!errorExist);
    }

    @Test
    public void size_sizeByreArray9Bit() throws Exception {
        byte allOne = (byte) 0b11111111;
        byte sizeBits = 9;
        byte[] array = {allOne, allOne};

        ChunkBits chunkBits = new ChunkBitsClass("111111111");

        byte[] expected = {allOne, (byte) 0b10000000};
        byte[] actual = chunkBits.getAllContent();


        assert(Arrays.equals(expected, actual));
    }
    @Test
    public void size_sizeByreArray9BitZeros() throws Exception {
        byte allZero = (byte) 0b00000000;
        byte sizeBits = 9;
        byte[] array = {allZero, allZero};

        ChunkBits chunkBits = new ChunkBitsClass("000000000");

        byte[] expected = {allZero, (byte) 0b00000000};
        byte[] actual = chunkBits.getAllContent();


        assert(Arrays.equals(expected, actual));
    }
    @Test
    public void size_sizeByreArray8BitZeros() throws Exception {
        byte allZero = (byte) 0b00000000;
        byte sizeBits = 8;
        byte[] array = {allZero, allZero};

        ChunkBits chunkBits = new ChunkBitsClass("00000000");

        byte[] expected = {allZero};
        byte[] actual = chunkBits.getAllContent();


        assert(Arrays.equals(expected, actual));
    }
    @Test
    public void size_sizeByreArray8BitOnes() throws Exception {
        byte allZero = (byte) 0b11111111;
        byte sizeBits = 8;
        byte[] array = {allZero, allZero};

        ChunkBits chunkBits = new ChunkBitsClass("11111111");

        byte[] expected = {allZero};
        byte[] actual = chunkBits.getAllContent();


        assert(Arrays.equals(expected, actual));
    }
}