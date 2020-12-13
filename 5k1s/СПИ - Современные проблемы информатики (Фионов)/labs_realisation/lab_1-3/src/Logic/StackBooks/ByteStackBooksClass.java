package Logic.StackBooks;

import DataInputOut.InputFileBits;
import DataInputOut.InputFileBitsClass;
import DataInputOut.OutputFileBits;
import DataInputOut.OutputFileBitsClass;
import Logic.ArchivarotNumberClass;
import Logic.ArchivatorNumber;
import Logic.ChunkBits;
import Logic.ChunkBitsClass;

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


            System.out.println("numberForCode indexOf: " + indexOf);
            //сжатие кода
//            byte code = UnsignedByte.toSignedByte(indexOf);
            ChunkBits archivedIndexOf = archivator.codeNumberToRequiredSize(new ChunkBitsClass(indexOf));
            System.out.println("archived indexOf: " + archivedIndexOf);
            System.out.println("-----------------------------------\n");
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
        System.out.println("length-----------------------");
        file.write(alphabetByte);
        System.out.println(alphabetByte.length);
        file.write(archivedStackBooksArray);
        System.out.println("archived: " + archivedStackBooksArray.length);
        file.close();
/*
        RandomAccessFile raf = new RandomAccessFile(archivedFile, "rw");
        raf.seek(0);


        raf.write(alphabetByte);
        raf.close();*/
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
/*
        InputFileBits input = new InputFileBitsClass();
        byte[] all = input.getAllContent(archiveStackBooks).getAllContent();
*/


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

        for (int i = 0; i < decodeArray.length; i++) {

            //0 символ алфавита добавляется в конец строки
            byte firstItem = alphabet.get(0);
            System.out.println("firstItem: " + firstItem);
            array.add(0, firstItem);

            //0 символ из алфавита вставляется в позицию нормер (код) в массив
            alphabet.remove(0);
            int code = UnsignedByte.getInt((byte) decodeArray[i]);
            alphabet.add(code, firstItem);
        }



        //в конце проверяем, что алфавит в исходном порядке
//        assert (alphabet.get(0) < alphabet.get(1));

        byte[] res = listToArrayByte(array);


        Files.write(Path.of((new File(data)).getAbsolutePath()), res);
    }

}
