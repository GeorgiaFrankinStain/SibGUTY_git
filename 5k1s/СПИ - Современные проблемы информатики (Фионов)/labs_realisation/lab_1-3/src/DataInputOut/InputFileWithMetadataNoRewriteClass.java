package DataInputOut;

import logic.ChunkBits;
import logic.ChunkBitsClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class InputFileWithMetadataNoRewriteClass implements InputFileWithMetadata {

    private String path;
    private Map<String, Object> metadataArchive = new HashMap<String, Object>();
    private int positionReadBits = 0;
    ObjectInputStream fileWithMetadataArchive;


    public InputFileWithMetadataNoRewriteClass(String pathOpenFile) throws IOException, ClassNotFoundException {
        this.path = pathOpenFile;
        this.fileWithMetadataArchive = new ObjectInputStream(new FileInputStream(new File(pathOpenFile)));
        this.metadataArchive = (Map<String, Object>) fileWithMetadataArchive.readObject();
    }

    @Override
    public Object getMetaDataTegs(String title) {
        return metadataArchive.get(title);
    }


    @Override
    public ChunkBits readNextChunkDataBit(int countBits) throws Exception {
        int countByte = sizeReadByte(countBits);
        byte[] resArrayByte = new byte[countByte];
        resArrayByte[0] = 1; //to show changes to the array during test

        this.fileWithMetadataArchive = new ObjectInputStream(new FileInputStream(new File(this.path)));
        this.fileWithMetadataArchive.readObject(); //TODO I just to start reading the array after serializing this file
        int lengthRead = countByte;
        int res = fileWithMetadataArchive.read(resArrayByte, 0, countByte);

        this.positionReadBits += countBits;

        return new ChunkBitsClass(resArrayByte, countBits);
    }


    @Override
    public void close() throws IOException {
        this.fileWithMetadataArchive.close();
    }


    private int sizeReadByte(int countBits) {
        return ChunkBits.getSizeByteArrayForSaveBitsArray(countBits);
    }
}
