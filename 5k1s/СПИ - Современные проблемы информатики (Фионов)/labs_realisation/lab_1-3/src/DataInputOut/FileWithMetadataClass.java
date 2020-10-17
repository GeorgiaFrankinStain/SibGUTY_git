package DataInputOut;

import logic.ChunkData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileWithMetadataClass implements FileWithMetadata {

    private File file;
    private Map<String, Object> metadataArchive = new HashMap<String, Object>();
    private int positionReadBits = 0;
    private String path;
    

    public FileWithMetadataClass(String pathCreateFile, Map<String, Object> metadataArchive) throws IOException {
        this.path = pathCreateFile;
        this.file = new File(pathCreateFile);

        this.file.getParentFile().mkdirs();
        this.file.createNewFile();

        ObjectOutputStream fileWithMetadataArchive = new ObjectOutputStream(new FileOutputStream(this.file));
        fileWithMetadataArchive.writeObject(metadataArchive);
        fileWithMetadataArchive.close();
    }

    public FileWithMetadataClass(String pathOpenFile) throws IOException, ClassNotFoundException {
        this.path = pathOpenFile;
        ObjectInputStream fileWithMetadataArchive = new ObjectInputStream(new FileInputStream(new File(pathOpenFile)));
        this.metadataArchive = (Map<String, Object>) fileWithMetadataArchive.readObject();
        fileWithMetadataArchive.close();
    }


    @Override
    public Object getMetaDataTegs(String title) {
        return metadataArchive.get(title);
    }


    @Override
    public ChunkData readNextChunkDataBit(int size) throws IOException, ClassNotFoundException {
        byte[] resArrayByte = new byte[size];

        ObjectInputStream fileWithMetadataArchive = new ObjectInputStream(new FileInputStream(new File(this.path)));
        fileWithMetadataArchive.readObject(); //FIXME I just to start reading the array after serializing this file
        int lengthRead = size;
        fileWithMetadataArchive.read(resArrayByte, 0, lengthRead);
        fileWithMetadataArchive.close();

        this.positionReadBits += size;



        return null; //FIXME
    }

    @Override
    public void writeChunkDataInEnd(ChunkData chunkData) {
        //FIXME
    }

    @Override
    public void close() {

    }

}
