package DataInputOut;

import Logic.ChunkBits;
import Logic.ChunkBitsClass;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OutputFileBitsClassTest {
    private String rootPath = "./test_files/";

    @Test
    void writeChunkDataInEnd() throws Exception {
        String address = rootPath + "tyq3467b.bin";
        OutputFileBits writerBits = new OutputFileBitsClass(address);

        ChunkBits chunk = new ChunkBitsClass("1010101010101010");
        writerBits.writeChunkDataInEnd(chunk);
        writerBits.close();

//        byte[] actual = Files.readAllBytes(Path.of((new File(address)).getAbsolutePath()));
        InputFileBits input = new InputFileBitsClass();

        ChunkBits actual = input.getAllContent(address);
        ChunkBits expected = chunk;

        assertEquals(expected, actual);
    }

    @Test
    void close() throws Exception {
        String address = rootPath + "tyq3467b.bin";
        OutputFileBits writerBits = new OutputFileBitsClass(address);

        ChunkBits chunk = new ChunkBitsClass("1010");
        writerBits.writeChunkDataInEnd(chunk);
        writerBits.writeChunkDataInEnd(chunk);

        writerBits.close();

//        byte[] actual = Files.readAllBytes(Path.of((new File(address)).getAbsolutePath()));
        InputFileBits input = new InputFileBitsClass();

        ChunkBits actual = input.getAllContent(address);
        ChunkBits expected = chunk.concatinateWith(chunk);

        assertEquals(expected, actual);
    }
}