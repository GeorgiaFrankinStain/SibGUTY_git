package DataInputOut;

import logic.ChunkData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface FileWithMetadata {
    public Object getMetaDataTegs(String title);


    public ChunkData readNextChunkDataBit(int size) throws IOException, ClassNotFoundException;
    public void writeChunkDataInEnd(ChunkData chunkData);

    public void close();

}
