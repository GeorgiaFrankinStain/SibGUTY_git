package Logic;

public interface ChunkBits {
    public int size();

    public boolean getBitFromIndex(int index);

    public byte[] getAllContent();

    public ChunkBits getBitsFromInfiniteArray(int fromBit, int toBit) throws Exception;

    public String getBinareString();

    public ChunkBits concatinateWith(ChunkBits chunkBitsConcatinate) throws Exception;

    public void addInEnd(ChunkBits chunkBits) throws Exception;

    public void addOneInStart() throws Exception;

    public void deleteZerosInStart() throws Exception;

    public Integer getPositionFirstOne();

    public int getInt() throws Exception;

    public byte getByte() throws Exception;

    public boolean isEmpty() throws Exception;


    public static int getSizeByteArrayForSaveBitsArray(int sizeBitsArray) {
        if (sizeBitsArray == 0) {
            return 0;
        }

        int sizeReadByte = sizeBitsArray / 8;

        boolean isMultipleByte = sizeBitsArray % 8 == 0;
        if (!isMultipleByte) {
            sizeReadByte += 1;
        }

        return sizeReadByte;
    }
}
