package logic;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ChunkBitsClassTest {
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
}