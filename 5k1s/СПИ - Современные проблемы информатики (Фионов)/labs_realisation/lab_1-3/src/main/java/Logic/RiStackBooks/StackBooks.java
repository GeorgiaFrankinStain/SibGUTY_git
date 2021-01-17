package Logic.RiStackBooks;

import Logic.ChunkBits;

import java.util.List;

public interface StackBooks {
    public void archiveToBuffer(byte[] data);
    public ChunkBits getArchivedData();
}
