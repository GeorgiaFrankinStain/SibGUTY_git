package logic;

public class ChunkDataClass implements ChunkData {
    private byte[] arrayBits;
    private int sizeBitsChunk;

    public ChunkDataClass(byte[] arrayBits, int sizeBitsChunk) {
        this.arrayBits = arrayBits;
        this.sizeBitsChunk = sizeBitsChunk;
    }

    @Override
    public int size() {
        return this.sizeBitsChunk;
    }

    @Override
    public boolean getBitFromIndex() {
        return false; //FIXME
    }

    @Override
    public byte[] getContentFromStartTo(int toPosition) {
        return new byte[0]; //FIXME
    }
}
