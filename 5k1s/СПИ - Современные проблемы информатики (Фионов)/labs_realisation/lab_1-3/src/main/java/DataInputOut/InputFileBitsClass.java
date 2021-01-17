package DataInputOut;

import Logic.ChunkBits;
import Logic.ChunkBitsClass;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class InputFileBitsClass implements InputFileBits {
    @Override
    public ChunkBits getAllContent(String fileWithChunkBits) throws Exception {
/*
        byte[] data = Files.readAllBytes(Path.of((new File(fileWithChunkBits)).getAbsolutePath()));
        int significantBitsInLastByte = data[data.length - 1];
        int sizeByteWithInformationAboutSignificantBitsInLastByte = 8;
        int numberNotSignificantBitsInLastByte = 8 - significantBitsInLastByte;
        int sizeTail = numberNotSignificantBitsInLastByte + sizeByteWithInformationAboutSignificantBitsInLastByte;
        int sizeChunkBits = data.length * 8 - sizeTail;

        return new ChunkBitsClass(data, sizeChunkBits);*/
        return null;
    }
}
