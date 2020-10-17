package logic;

public interface ChunkData {
    public int size();
    public boolean getBitFromIndex();
    public byte[] getContentFromStartTo(int toPosition);
}
