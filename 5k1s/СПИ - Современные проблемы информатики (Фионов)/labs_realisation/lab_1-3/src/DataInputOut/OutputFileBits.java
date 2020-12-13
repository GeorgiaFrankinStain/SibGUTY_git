package DataInputOut;

import Logic.ChunkBits;

import java.io.IOException;

public interface OutputFileBits {

    public void writeChunkDataInEnd(ChunkBits chunkBits) throws Exception;

    public void close() throws IOException;


}
