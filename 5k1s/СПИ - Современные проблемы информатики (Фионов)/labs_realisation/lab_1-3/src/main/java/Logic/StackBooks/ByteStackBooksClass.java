package Logic.StackBooks;

import DataInputOut.InputFileBits;
import DataInputOut.InputFileBitsClass;
import DataInputOut.OutputFileBits;
import DataInputOut.OutputFileBitsClass;
import Logic.ArchivarotNumberClass;
import Logic.ArchivatorNumber;
import Logic.ChunkBits;
import Logic.ChunkBitsClass;
import Logic.RiStackBooks.StackBooks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;









public class ByteStackBooksClass implements StackBooks {


    @Override
    public void code(String data, String archivedFile) throws Exception {
        List<Byte> alphabet = alphabetCreate();

        byte[] actual = Files.readAllBytes(Path.of((new File(data)).getAbsolutePath()));

        OutputFileBits writerBits = new OutputFileBitsClass(archivedFile);
        byte[] offset = new byte[256];


        ArchivatorNumber archivator = new ArchivarotNumberClass(1);


        for (int i = 0; i < actual.length; i++) {
            byte currentSimbol = actual[i];

            //поиск кода (позиция в алфавите)
            int indexOf = alphabet.indexOf(currentSimbol);


            //сжатие кода
//            byte code = UnsignedByte.toSignedByte(indexOf);
            ChunkBits archivedIndexOf = archivator.codeNumberToRequiredSize(new ChunkBitsClass(indexOf));
            //запись кода в файл
            writerBits.writeChunkDataInEnd(archivedIndexOf);

            //найденную букву ставим в начало
            alphabet.remove(indexOf);
            alphabet.add(0, currentSimbol);
        }
        writerBits.close();







        byte[] archivedStackBooksArray = Files.readAllBytes(Path.of((new File(archivedFile)).getAbsolutePath()));
        byte[] alphabetByte = listToArrayByte(alphabet);

        FileOutputStream file = new FileOutputStream(archivedFile);
        file.write(alphabetByte);
        file.write(archivedStackBooksArray);
        file.close();
        RandomAccessFile raf = new RandomAccessFile(archivedFile, "rw");
        raf.seek(0);


        raf.write(alphabetByte);
        raf.close();
    }
    private byte[] listToArrayByte(List<Byte> pdu) {
        Byte[] bytes = pdu.toArray(new Byte[pdu.size()]);


        byte[] buasdfasdfsdf = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            buasdfasdfsdf[i] = bytes[i];
        }

        return buasdfasdfsdf;
    }

    private ArrayList<Byte> alphabetCreate() {
        ArrayList<Byte> res = new ArrayList<Byte>();
        int placeForAlpabet = 256;
        for (int i = 0; i < placeForAlpabet; i++) {
            res.add(UnsignedByte.toSignedByte(i));
        }

        return res;
    }

    @Override
    public void decode(String archiveStackBooks, String data) throws Exception {
        byte[] all = Files.readAllBytes(Path.of((new File(archiveStackBooks)).getAbsolutePath()));


        ArrayList<Byte> alphabet = new ArrayList<Byte>();
        int placeForAlpabet = 256;
        for (int i = 0; i < placeForAlpabet; i++) {
            alphabet.add(all[i]);
        }



        ArrayList<Byte> array = new ArrayList<>();

        int sizeArrayCode = all.length - placeForAlpabet;
        byte[] arrayCode = new byte[sizeArrayCode];

        for (int i = placeForAlpabet, j = 0; i < all.length; j++, i++) {
            arrayCode[j] = all[i];
        }

        String tempNameFile = "./test_files/codeTail.bin";
        Files.write(Path.of((new File(tempNameFile)).getAbsolutePath()), arrayCode);

        ArchivatorNumber archivator = new ArchivarotNumberClass(1);
        byte[] decodeArray = archivator.decodeFileByte(tempNameFile);

        for (int i = decodeArray.length - 1; i >= 0 ; i--) {

            //0 символ алфавита добавляется в конец строки
            byte firstItem = alphabet.get(0);
            array.add(0, firstItem);

            //0 символ из алфавита вставляется в позицию нормер (код) в массив
            firstSymbolInsertPosition(decodeArray, alphabet, i, firstItem);
        }

        assert (alphabetReturnedToOriginalOrder(alphabet));

        byte[] res = listToArrayByte(array);


        Files.write(Path.of((new File(data)).getAbsolutePath()), res);
    }

    private void firstSymbolInsertPosition(byte[] decodeArray, ArrayList<Byte> alphabet, int position, byte firstItem) {
        alphabet.remove(0);
        int code = UnsignedByte.getInt((byte) decodeArray[position]);
        alphabet.add(code, firstItem);
    }

    private boolean alphabetReturnedToOriginalOrder(ArrayList<Byte> alphabet) {
        for (int i = 1; i < alphabet.size(); i++) {
            int indexPrevious = i - 1;
            System.out.println(alphabet.get(indexPrevious));
            boolean rightOrder = alphabet.get(indexPrevious) < alphabet.get(i);
            if (!rightOrder) {
                System.out.println("-------> ne poriadok: " + alphabet.get(indexPrevious) + " , " + alphabet.get(i));
                return false;
            }
        }
        return true;
    }

    @Override
    public void archiveToBuffer(byte[] data) {

    }

    @Override
    public ChunkBits getArchivedData() {
        return null;
    }
}
