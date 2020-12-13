package DataInputOut;

import Logic.ChunkBits;
import Logic.ChunkBitsClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileWithMetadataTest {
    private String rootPath = "./test_files/";


    @Test
    public void getMetaDataTegs() throws IOException, ClassNotFoundException {
        String randomName = "wnldPNR.bin";
        String titleTeg = "teg";
        Integer recordedObjectForTeg = 5;

        OutputFileBits outputFileWithMetadata = writeMetadataInFile(titleTeg, randomName, recordedObjectForTeg);
        outputFileWithMetadata.close();

        InputFileWithMetadata inputFileWithMetadata =
                new InputFileWithMetadataNoRewriteClass(this.rootPath + randomName);


        Integer expectedInteger = recordedObjectForTeg;
        Integer actualInteger = (Integer) inputFileWithMetadata.getMetaDataTegs(titleTeg);
        assertEquals(expectedInteger, actualInteger);

        int actual = actualInteger;
        int expected = recordedObjectForTeg;
        assertEquals(expected, actual);
    }

    private OutputFileBits writeMetadataInFile(String titleTeg, String titleFile, Object recordedObjectForTeg) throws IOException {
        Map<String, Object> metadataArchive = new HashMap<String, Object>();
        metadataArchive.put(titleTeg, recordedObjectForTeg);

        OutputFileBits outputFileWithMetadata =
                new OutputFileWithMetadataClass(this.rootPath + titleFile, metadataArchive);

        return outputFileWithMetadata;
    }

    @Test
    public void readNextChunkDataBit() throws Exception {
        String randomName = "aTgKfoS.bin";
        String titleTeg = "teg";
        Integer recordedObjectForTeg = 5;

        OutputFileBits outputFileWithMetadata = writeMetadataInFile(titleTeg, randomName, recordedObjectForTeg);
        InputFileWithMetadata inputFileWithMetadata = writingArrayBytes(outputFileWithMetadata, randomName);
        outputFileWithMetadata.close();

        Integer expectedInteger = recordedObjectForTeg;
        Integer actualInteger = (Integer) inputFileWithMetadata.getMetaDataTegs(titleTeg);
        assertEquals(expectedInteger, actualInteger);

        int actual = actualInteger;
        int expected = recordedObjectForTeg;
        assertEquals(expected, actual);

        ChunkBits expectedChunkBits = createTestedObjectWithEqualOption();
        ChunkBits actualChunkBits = inputFileWithMetadata.readNextChunkDataBit(expectedChunkBits.size());
        assertEquals(expectedChunkBits, actualChunkBits);
    }

    private InputFileWithMetadata writingArrayBytes(
            OutputFileBits outputFileWithMetadata,
            String randomName
    ) throws Exception {
        ChunkBits recordedChunkBits = createTestedObjectWithEqualOption();
        outputFileWithMetadata.writeChunkDataInEnd(recordedChunkBits);

        return new InputFileWithMetadataNoRewriteClass(this.rootPath + randomName);
    }

    private ChunkBits createTestedObjectWithEqualOption() throws Exception {
        byte[] data = {0, 0, 10, 0};
        int countBits = 4 * 8;
        ChunkBits recordedChunkBits = new ChunkBitsClass(data, countBits);
        return recordedChunkBits;
    }


    @Test
    public void readWriteChunkBitsNotMultipleByte() throws Exception {
        String randomName = "HQuUqyS.bin";

        FactoryChunkBits factory = new FactoryChunkBitsClass();
        for (int varietyChunkBits = 0; varietyChunkBits < factory.getCountVarieties(); varietyChunkBits++) {
            OutputFileBits outputFileWithMetadata =
                    new OutputFileWithMetadataClass(this.rootPath + randomName);

            int sizeChunkBits = 4 * 8 + 3;
            ChunkBits chunkBitsNonMultipleByteSize = factory.getChunkBits(varietyChunkBits, sizeChunkBits);
            outputFileWithMetadata.writeChunkDataInEnd(chunkBitsNonMultipleByteSize);
            outputFileWithMetadata.close();


            InputFileWithMetadata inputFileWithMetadata =
                    new InputFileWithMetadataNoRewriteClass(this.rootPath + randomName);
            ChunkBits actual = inputFileWithMetadata.readNextChunkDataBit(sizeChunkBits);
            ChunkBits expected = factory.getChunkBits(varietyChunkBits, sizeChunkBits);
            assertEquals("variety chunk bits: " + varietyChunkBits, expected, actual);
        }

    }

    @Test
    public void sequenceReadWriteChunkBitsNotMultipleByte() throws Exception {
        String randomName = "jANiCkH.bin";

        FactoryChunkBits factory = new FactoryChunkBitsClass();
        int sizeSharedChunk = 10;
        for (int i = 1; i < sizeSharedChunk; i++) {
            for (int varietyChunkBits = 0; varietyChunkBits < factory.getCountVarieties(); varietyChunkBits++) {
                ChunkBits sharedChunk = factory.getChunkBits(varietyChunkBits, sizeSharedChunk);

                int size1Chunk = i;
                int size2Chunk = sharedChunk.size() - size1Chunk;
                sequenceWriteTwoChunkBits(sharedChunk, size1Chunk, size2Chunk, randomName);

                InputFileWithMetadata inputFileWithMetadata =
                        new InputFileWithMetadataNoRewriteClass(this.rootPath + randomName);

                ChunkBits actual = inputFileWithMetadata.readNextChunkDataBit(size1Chunk + size2Chunk);
                ChunkBits expected = sharedChunk;
                assertEquals("size first chunk: " + i + " variety chunk bits: " + varietyChunkBits, expected, actual);
            }
        }
    }

    private void sequenceWriteTwoChunkBits(
            ChunkBits sharedChunk,
            int size1ChunkBits,
            int size2ChunkBits,
            String randomName
    ) throws Exception {
        OutputFileBits outputFileWithMetadata =
                new OutputFileWithMetadataClass(this.rootPath + randomName);

        ChunkBits chunkBitsNonMultipleByteSize = sharedChunk.getBitsFromInfiniteArray(0, size1ChunkBits);
        outputFileWithMetadata.writeChunkDataInEnd(chunkBitsNonMultipleByteSize);

        ChunkBits chunkBits2NonMultipleByteSize = sharedChunk.getBitsFromInfiniteArray(size1ChunkBits, size1ChunkBits + size2ChunkBits);
        outputFileWithMetadata.writeChunkDataInEnd(chunkBits2NonMultipleByteSize);

        outputFileWithMetadata.close();

    }

    private interface FactoryChunkBits {
        public int getCountVarieties();

        public ChunkBits getChunkBits(int varietyNumber, int sizeChunkBits) throws Exception;
    }

    private class FactoryChunkBitsClass implements FactoryChunkBits {
        byte oneByte = (byte) 0b11111111;
        byte[][] varieties = {
                {oneByte, oneByte, oneByte, oneByte, oneByte},
                {1, 2, 3, 4, (byte) 0b11111111},
                {85, 85, 85, 85, 85}
        };


        @Override
        public int getCountVarieties() {
            return varieties.length;
        }

        @Override
        public ChunkBits getChunkBits(int varietyNumber, int sizeChunkBits) throws Exception {
            ChunkBits recordedChunkBits = new ChunkBitsClass(varieties[varietyNumber], sizeChunkBits);
            return recordedChunkBits;
        }
    }
}