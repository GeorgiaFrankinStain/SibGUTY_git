package DataInputOut;

import Logic.ChunkBits;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OutputFileWithMetadataClass implements OutputFileBits {

    private File file;
    private String path;
    private ObjectOutputStream fileWithMetadataArchive;

    private int leftBitsForBuferInEndLastTime = 0;

    private byte buferSaveNonMultipleRemainsByte = 0b00000000;
    private int numberBitsInBufer = 0;

    public OutputFileWithMetadataClass(String pathCreateFile) throws IOException {
        this(pathCreateFile, createEmptyMap());
    }

    public OutputFileWithMetadataClass(String pathCreateFile, Map<String, Object> metadataArchive) throws IOException {
        this.path = pathCreateFile;
        this.file = new File(pathCreateFile);

        this.file.getParentFile().mkdirs();
        this.file.createNewFile();

        this.fileWithMetadataArchive = new ObjectOutputStream(new FileOutputStream(this.file));
        this.fileWithMetadataArchive.writeObject(metadataArchive);
    }

    @Override
    public void writeChunkDataInEnd(ChunkBits chunkBits) throws Exception {
        int sizeHeadMergeWithBufer = numberBitsInBufer;
        int totalSizeOffsetArrayBits = sizeHeadMergeWithBufer + chunkBits.size();
        int sizeTailWriteInBufer = totalSizeOffsetArrayBits % 8;
        int sizeBodyWriteInFile = totalSizeOffsetArrayBits - sizeTailWriteInBufer;

        this.leftBitsForBuferInEndLastTime = 0;
        boolean enoughtDataForWriteFile = sizeBodyWriteInFile > 0;
        if (enoughtDataForWriteFile) {
            byte[] bodyArray = getBodyArrayWithDesiredMultiplicity(chunkBits);
            writeBuferInData(bodyArray);
            this.fileWithMetadataArchive.write(bodyArray);

        }
        if (sizeTailWriteInBufer > 0) {
            saveBitsInBufer(sizeTailWriteInBufer, chunkBits);
        }
    }

    private void writeBuferInData(byte[] data) {
        data[0] |= this.buferSaveNonMultipleRemainsByte;
        this.numberBitsInBufer = 0;
        this.buferSaveNonMultipleRemainsByte = 0b00000000;
    }

    private int startPositionReadLastByteWithRemainsForBuferUpdate(ChunkBits chunkBits) {


        int offsetRightArray = numberBitsInBufer;
        int startPositionReadLastByteWithRemainsForBuferUpdate =
                chunkBits.size() + offsetRightArray;
        int multipleStartPositionReadLastByteWithRemainsForBuferUpdate =
                startPositionReadLastByteWithRemainsForBuferUpdate - startPositionReadLastByteWithRemainsForBuferUpdate % 8;

        int startPositionReadLastByteWithRemainsForBuferUpdateInOldChunkBits =
                multipleStartPositionReadLastByteWithRemainsForBuferUpdate - offsetRightArray;
        this.leftBitsForBuferInEndLastTime = chunkBits.size() - startPositionReadLastByteWithRemainsForBuferUpdateInOldChunkBits;
        return startPositionReadLastByteWithRemainsForBuferUpdateInOldChunkBits;
    }

    private byte[] getBodyArrayWithDesiredMultiplicity(ChunkBits chunkBits) throws Exception {
        int startPositionWithSpacesForBitsFromBufer = -numberBitsInBufer;
        int end;
        ChunkBits dataChunkBits = chunkBits.getBitsFromInfiniteArray(
                startPositionWithSpacesForBitsFromBufer,
                end = startPositionReadLastByteWithRemainsForBuferUpdate(chunkBits)
        );
        return dataChunkBits.getAllContent();
    }


    private void saveBitsInBufer(int sizeTailWriteInBufer, ChunkBits chunkBits) throws Exception {
        int startPositionReadLastByteWithRemainsForBuferUpdate = chunkBits.size() - sizeTailWriteInBufer;
        int endPositionReadFromChunkBits = chunkBits.size();
        if (endPositionReadFromChunkBits - startPositionReadLastByteWithRemainsForBuferUpdate == 0) {
            return;
        }

        byte[] data = chunkBits.getBitsFromInfiniteArray(
                startPositionReadLastByteWithRemainsForBuferUpdate,
                endPositionReadFromChunkBits
        ).getAllContent();
        this.buferSaveNonMultipleRemainsByte |= data[0];
        this.numberBitsInBufer = endPositionReadFromChunkBits - startPositionReadLastByteWithRemainsForBuferUpdate;
    }

    @Override
    public void close() throws IOException {
        byte[] data = {this.buferSaveNonMultipleRemainsByte};
        this.fileWithMetadataArchive.write(data);
        this.fileWithMetadataArchive.close();
    }

    private static Map<String, Object> createEmptyMap() {
        Map<String, Object> metadataArchive = new HashMap<String, Object>();
        return metadataArchive;
    }

}
