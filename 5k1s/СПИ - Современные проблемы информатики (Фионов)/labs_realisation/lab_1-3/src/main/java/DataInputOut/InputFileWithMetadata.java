package DataInputOut;

import Logic.ChunkBits;

import java.io.IOException;

public interface InputFileWithMetadata {

    public Object getMetaDataTegs(String title);

    public ChunkBits readNextChunkDataBit(int size) throws Exception;

    public void close() throws IOException;
}