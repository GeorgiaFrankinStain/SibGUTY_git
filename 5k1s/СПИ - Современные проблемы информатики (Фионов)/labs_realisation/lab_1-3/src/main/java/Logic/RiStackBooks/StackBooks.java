package Logic.RiStackBooks;

import Logic.ChunkBits;

import java.util.List;

public interface StackBooks {
    public void archiveToBuffer(byte[] data);
    public ChunkBits getArchivedData();
    public void code(String inputFile, String outputFile) throws Exception;
    public void decode(String decodeObject, String outputFile) throws Exception;
}
