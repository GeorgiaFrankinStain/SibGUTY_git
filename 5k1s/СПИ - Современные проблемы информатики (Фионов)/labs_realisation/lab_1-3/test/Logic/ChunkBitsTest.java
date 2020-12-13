package Logic;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ChunkBitsTest {
    private byte allOne = (byte) 0b11111111;
    private byte allZero = (byte) 0b00000000;


    @Test
    public void size() throws Exception {
        byte sizeBits = 9;
        byte[] array = new byte[2];

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        assertEquals(sizeBits, chunkBits.size());
    }

    @Test
    public void size_sizeArraySmaller() {
        byte sizeBits = 9;
        byte[] array = new byte[1];

        boolean errorInterception = false;
        try {
            ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);
        } catch (Exception e) {
            errorInterception = true;
        }

        assert (errorInterception);
    }

    @Test
    public void size_sizeByreArray8Bit() throws Exception {
        byte sizeBits = 8;
        byte[] array = {allOne, allZero};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        byte[] expected = {allOne};
        byte[] actual = chunkBits.getAllContent();

        assert(Arrays.equals(expected, actual));
    }
    @Test
    public void size_sizeByreArray3Bit() throws Exception {
        byte sizeBits = 3;
        byte[] array = {allOne, allOne};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        byte[] expected = {(byte) 0b11100000};
        byte[] actual = chunkBits.getAllContent();

        assert(Arrays.equals(expected, actual));
    }
    @Test
    public void size_sizeByreArray0Bit() throws Exception {
        byte sizeBits = 0;
        byte[] array = {allOne, allOne};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        byte[] expected = {};
        byte[] actual = chunkBits.getAllContent();

        assertEquals(sizeBits, chunkBits.size());
        assert(Arrays.equals(expected, actual));
    }

    @Test
    public void size_sizeByreArray9Bit() throws Exception {
        byte sizeBits = 9;
        byte[] array = {allOne, allOne};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        byte[] expected = {allOne, (byte) 0b10000000};
        byte[] actual = chunkBits.getAllContent();

        assert(Arrays.equals(expected, actual));
    }

    @Test
    public void getBitFromIndex() throws Exception {
        byte sizeBits = 8;
        byte[] array = {(byte) 0b00100000};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        boolean expected = true;
        boolean actual = chunkBits.getBitFromIndex(2);

        assertEquals(expected, actual);
    }

    @Test
    public void getAllContent() throws Exception {
        byte sizeBits = 16;
        byte[] array = {127, 127};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        byte[] expected = {127, 127};
        byte[] actual = chunkBits.getAllContent();

        assert(Arrays.equals(expected, actual));
    }

    @Test
    public void getBitsFromInfiniteArray_returnYourCopy() throws Exception {
        int sizeChunkBits = 8;
        byte[] data = {(byte) 0b11111111};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        byte[] dataExpected = {(byte) 0b11111111};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeChunkBits);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(0, 8);

        assertEquals(expected, actual);
    }

    @Test
    public void getBitsFromInfiniteArray_returnHalfByte() throws Exception {
        int sizeChunkBits = 8;
        byte[] data = {(byte) 0b11111111};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        int sizeExpectedChunk = 4;
        byte[] dataExpected = {(byte) 0b11110000};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(0, 4);

        assertEquals(expected, actual);
    }

    @Test
    public void getBitsFromInfiniteArray_returnExtendedArrayLeft() throws Exception {
        int sizeChunkBits = 8;
        byte[] data = {(byte) 0b11111111};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        int sizeExpectedChunk = 8;
        byte[] dataExpected = {(byte) 0b00001111};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(-4, 4);

        assertEquals(expected, actual);
    }
    @Test
    public void getBitsFromInfiniteArray_returnExtendedArrayOverLeft() throws Exception {
        int sizeChunkBits = 8;
        byte[] data = {(byte) 0b11111111};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        int sizeExpectedChunk = 8;
        byte[] dataExpected = {(byte) 0b00000000};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(-8, 0);

        assertEquals(expected, actual);
    }
    @Test
    public void getBitsFromInfiniteArray_returnExtendedArrayRight() throws Exception {
        int sizeChunkBits = 8;
        byte[] data = {(byte) 0b11111111};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        int sizeExpectedChunk = 8;
        byte[] dataExpected = {(byte) 0b11110000};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(4, 12);

        assertEquals(expected, actual);
    }
    @Test
    public void getBitsFromInfiniteArray_bug() throws Exception {
        int sizeChunkBits = 10;
        byte oneByte = (byte) 0b11111111;
        byte[] data = {oneByte, oneByte, oneByte, oneByte, oneByte};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        int sizeExpectedChunk = 9;
        byte[] dataExpected = {(byte) 0b11111111, (byte) 0b10000000};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(1, 10);

        assertEquals(expected, actual);
    }
    @Test
    public void getBitsFromInfiniteArray_bug2() throws Exception {
        int sizeChunkBits = 9;
        byte oneByte = (byte) 0b11111111;
        byte[] data = {oneByte, oneByte, oneByte, oneByte, oneByte};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);



        int sizeExpectedChunk = 9;
        byte[] dataExpected = {(byte) 0b01111111, (byte) 0b10000000};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(-1, 8);

        assertEquals(expected, actual);
    }
    @Test
    public void getBitsFromInfiniteArray_bug3() throws Exception {
        int sizeChunkBits = 35;

        byte oneByte = (byte) 0b11111111;
        byte[] data = {oneByte, oneByte, oneByte, oneByte, oneByte};
        ChunkBits chunkBits = new ChunkBitsClass(data, sizeChunkBits);


        int sizeExpectedChunk = 32;
        byte[] dataExpected = {oneByte, oneByte, oneByte, oneByte};
        ChunkBits expected = new ChunkBitsClass(dataExpected, sizeExpectedChunk);
        ChunkBits actual = chunkBits.getBitsFromInfiniteArray(0, 32);

        assertEquals(expected, actual);
    }

    @Test
    public void testToString() throws Exception {
        byte sizeBits = 16;
        byte[] array = {127, 127};

        ChunkBits chunkBits = new ChunkBitsClass(array, sizeBits);

        String expected = "([size: 16] 01111111 01111111 )";
        String actual = chunkBits.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals() throws Exception {
        ChunkBits chunkBits1, chunkBits2;
        {
            byte sizeBits = 16;
            byte[] array = {127, 127};

            chunkBits1 = new ChunkBitsClass(array, sizeBits);
        }
        {
            byte sizeBits = 16;
            byte[] array = {127, 127};

            chunkBits2 = new ChunkBitsClass(array, sizeBits);
        }
        assertEquals(chunkBits1, chunkBits2);
    }

    @Test
    public void getBinareString() throws Exception {
        String number = "00000000";
        ChunkBits chunkBits = new ChunkBitsClass(number);
        assertEquals(number, chunkBits.getBinareString());
    }
    @Test
    public void getBinareString_all8Ones() throws Exception {
        String number = "11111111";
        ChunkBits chunkBits = new ChunkBitsClass(number);
        assertEquals(number, chunkBits.getBinareString());
    }
    @Test
    public void getBinareString_all6() throws Exception {
        String number = "101010";
        ChunkBits chunkBits = new ChunkBitsClass(number);
        assertEquals(number, chunkBits.getBinareString());
    }

    @Test
    public void deleteZerosInStart_notNeedDelete() throws Exception {
        String number = "101010";
        ChunkBits trimmed = new ChunkBitsClass(number);
        trimmed.deleteZerosInStart();

        ChunkBits expected = new ChunkBitsClass(number);
        assertEquals(expected, trimmed);
    }

    @Test
    public void deleteZerosInStart_needDelete() throws Exception {
        String number = "101010";
        String numberWithZerosInStart = "000" + number;
        ChunkBits trimmed = new ChunkBitsClass(numberWithZerosInStart);
        trimmed.deleteZerosInStart();

        ChunkBits expected = new ChunkBitsClass(number);
        assertEquals(expected, trimmed);
    }

    @org.junit.jupiter.api.Test
    void addInEnd() throws Exception {
        String firstNumber = "101010";
        String secondNumber = "1111";
        ChunkBits number = new ChunkBitsClass(firstNumber);
        ChunkBits additive = new ChunkBitsClass(secondNumber);
        number.addInEnd(additive);
        ChunkBits expected = new ChunkBitsClass(firstNumber + secondNumber);

        assertEquals(expected, number);
    }

    @org.junit.jupiter.api.Test
    void getPositionFirstOne_0() throws Exception {
        ChunkBits chunkBits = new ChunkBitsClass("11010101");
        assertEquals((Integer) 0, chunkBits.getPositionFirstOne());
    }

    @org.junit.jupiter.api.Test
    void getPositionFirstOne_1() throws Exception {
        ChunkBits chunkBits = new ChunkBitsClass("011010101");
        assertEquals((Integer) 1, chunkBits.getPositionFirstOne());
    }

    @org.junit.jupiter.api.Test
    void getPositionFirstOne_null() throws Exception {
        ChunkBits chunkBits = new ChunkBitsClass("00000");
        assertEquals(null, chunkBits.getPositionFirstOne());
    }

    @org.junit.jupiter.api.Test
    void getInt() throws Exception {
        int number = 5;
        ChunkBits chunkBits = new ChunkBitsClass(number);
        int actual = chunkBits.getInt();
        assertEquals(number, actual);
    }

    @org.junit.jupiter.api.Test
    void addOneInStart() throws Exception {
        String bits = "00000";
        ChunkBits actual = new ChunkBitsClass(bits);
        actual.addOneInStart();
        ChunkBits expected = new ChunkBitsClass("1" + bits);
        assertEquals(expected, actual);
    }
    @org.junit.jupiter.api.Test
    void addOneInStart_ones() throws Exception {
        String bits = "1111";
        ChunkBits actual = new ChunkBitsClass(bits);
        actual.addOneInStart();
        ChunkBits expected = new ChunkBitsClass("1" + bits);
        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void isEmpty() throws Exception {
        ChunkBits empty = new ChunkBitsClass("");
        assert(empty.isEmpty());
    }
    @org.junit.jupiter.api.Test
    void isEmpty_000000() throws Exception {
        ChunkBits empty = new ChunkBitsClass("0000000");
        assert(!empty.isEmpty());
    }
    @org.junit.jupiter.api.Test
    void isEmpty_0() throws Exception {
        ChunkBits empty = new ChunkBitsClass(0);
        assert(!empty.isEmpty());
    }
}