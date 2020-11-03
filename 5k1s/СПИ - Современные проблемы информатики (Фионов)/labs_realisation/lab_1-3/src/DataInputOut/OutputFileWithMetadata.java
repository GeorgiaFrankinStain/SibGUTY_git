package DataInputOut;

import logic.ChunkBits;

import java.io.IOException;

public interface OutputFileWithMetadata {

    public void writeChunkDataInEnd(ChunkBits chunkBits) throws Exception;

    public void close() throws IOException;


}
