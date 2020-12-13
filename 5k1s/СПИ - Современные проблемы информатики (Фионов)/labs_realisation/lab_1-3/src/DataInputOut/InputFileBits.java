package DataInputOut;

import Logic.ChunkBits;

public interface InputFileBits {
    public ChunkBits getAllContent(String fileWithChunkBits) throws Exception;
}
