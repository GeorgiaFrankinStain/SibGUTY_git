package Logic;

import Logic.ChunkBits;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ArchivatorNumber {
    public ChunkBits codeNumberToRequiredSize(ChunkBits number) throws Exception;

    public byte[] decodeBuferWhilePossible() throws Exception;

    public byte[] decodeFileByte(String archived) throws Exception;

    public boolean isExistRemainInBuffer();

    public void addToBuffer(ChunkBits addingChunk) throws Exception;

    public void deleteFirstCodeNumber() throws Exception;

    public ChunkBits decodeOneNumberFromBuffer() throws Exception;

    public void codeFileInFileIntFormat(String inputFile, String outputFile) throws IOException;

    public void decodeFileInFileIntFormat(String inputFile, String outputFile) throws Exception;
}
