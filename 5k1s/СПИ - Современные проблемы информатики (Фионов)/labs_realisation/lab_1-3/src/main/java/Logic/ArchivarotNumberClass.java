package Logic;

import DataInputOut.*;
import Logic.StackBooks.UnsignedByte;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ArchivarotNumberClass implements ArchivatorNumber {
    private FiMinimizerNumber fi0 = new Fi0();
    private FiMinimizerNumber fiMinimizerNumber;
    private ChunkBits buffer = new ChunkBitsClass("");
    private Integer endFirstCodeNumber = null;

    public ArchivarotNumberClass(int fi) throws Exception {
        assert(0 <= fi);
        if (fi == 0) {
            this.fiMinimizerNumber = new Fi0();
        } else if (fi == 1) {
            this.fiMinimizerNumber = new Fi1();
        } else {
            this.fiMinimizerNumber = new Fi2AndMore(fi);
        }
    }

    @Override
    public ChunkBits codeNumberToRequiredSize(ChunkBits number) throws Exception {
        return fiMinimizerNumber.code(number);
    }

    @Override
    public byte[] decodeBuferWhilePossible() throws Exception {
        List<Byte> arr = new ArrayList<>();

        while (true) {
            ChunkBits test = this.decodeOneNumberFromBuffer();
            if (test.isEmpty()) {
                break;
            }
            this.deleteFirstCodeNumber();
            int decodeByte = test.getInt();
            assert(-129 < decodeByte && decodeByte < 128);
            arr.add((byte) decodeByte);
        }

        byte[] res = new byte[arr.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = arr.get(i);
        }

        return res;
    }

    @Override
    public byte[] decodeFileByte(String archived) throws Exception {
        InputFileBits input = new InputFileBitsClass();
        ChunkBits allContent = input.getAllContent(archived);
        this.addToBuffer(allContent);


        List<Byte> array = new ArrayList<Byte>();

        while (true) {
            ChunkBits test = this.decodeOneNumberFromBuffer();
            if (test.isEmpty()) {
                break;
            }
            this.deleteFirstCodeNumber();
            byte decodeByte = test.getByte();


            array.add((byte) decodeByte);
        }


        byte[] res = new byte[array.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = array.get(i);
        }

        return res;

    }

    @Override
    public boolean isExistRemainInBuffer() {
        return buffer.size() > 0;
    }

    @Override
    public void addToBuffer(ChunkBits addingChunk) throws Exception {
        this.buffer.addInEnd(addingChunk);

    }

    @Override
    public void deleteFirstCodeNumber() throws Exception {
        if (this.endFirstCodeNumber == null) {
            this.decodeOneNumberFromBuffer();
        }

        this.buffer = this.buffer.getBitsFromInfiniteArray(this.endFirstCodeNumber, this.buffer.size());
    }


    @Override
    public ChunkBits decodeOneNumberFromBuffer() throws Exception {
        return this.fiMinimizerNumber.decode();
    }

    @Override
    public void codeFileInFileIntFormat(String inputFile, String outputFile) throws IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(inputFile));
        OutputFileBits outputFile2 =
                new OutputFileBitsClass(outputFile);
        try {
            while (true) {
                int read = in.readInt();
                ChunkBits code = this.codeNumberToRequiredSize(new ChunkBitsClass(read));
                outputFile2.writeChunkDataInEnd(code);
            }
        } catch (EOFException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        in.close();
        outputFile2.close();
    }


    @Override
    public void decodeFileInFileIntFormat(String inputFile, String outputFile) throws Exception {

        InputFileBits input = new InputFileBitsClass();
        this.addToBuffer(input.getAllContent(inputFile));

        try {
            FileOutputStream file = new FileOutputStream(outputFile);

            while (true) {
                ChunkBits test = this.decodeOneNumberFromBuffer();
                if (test.isEmpty()) {
                    break;
                }
                this.deleteFirstCodeNumber();
                int decodeInt = test.getInt();
                byte[] intArray = intToArrayByte(decodeInt);
                file.write(intArray);

            }
            file.close();
        } catch (IOException e) {
        }
    }

    private byte[] intToArrayByte(int yourInt) {
        return ByteBuffer.allocate(4).putInt(yourInt).array();
    }

    private ChunkBits codeFiMore0(int fi, ChunkBits number) throws Exception {
        if (fi == 0) {
            return this.fi0.code(number);
        } else if (number.equals(new ChunkBitsClass("0"))) {
            return new ChunkBitsClass("1");
        } else if (number.equals(new ChunkBitsClass("1"))) {
            return new ChunkBitsClass("01");
        } else {
            ChunkBits sizeNumber = new ChunkBitsClass(number.size());
            ChunkBits codeNumberBitsForRead = codeFiMore0(fi - 1, sizeNumber);
            ChunkBits withoutFirtOne = number.getBitsFromInfiniteArray(1, number.size());
            return codeNumberBitsForRead.concatinateWith(withoutFirtOne);
        }
    }

    private ChunkBits decodeFi(int fi) throws Exception {
        ChunkBits res = new ChunkBitsClass(null, 0);
        Integer startReadNextData = fi0Decode();
        if (startReadNextData == null) {
            return res;
        }

        if (fi == 0 || (startReadNextData < 2)) {
            this.endFirstCodeNumber = startReadNextData;
            return new ChunkBitsClass(startReadNextData);
        }

        int sizeNextChunk = startReadNextData;
        int endReadNextData = startReadNextData + sizeNextChunk;
        startReadNextData++; //потому что первая единица относиться к f0 //FIXME

        for (int i = 0; i < fi; i++) {
            res = buffer.getBitsFromInfiniteArray(startReadNextData, endReadNextData);
            int impliedOneInStartInNextChunk = 1;
            res.addOneInStart();
            ChunkBits chunkWithImpliedOneInStart = res;
            sizeNextChunk = chunkWithImpliedOneInStart.getInt() - impliedOneInStartInNextChunk;
            startReadNextData = endReadNextData;
            endReadNextData = startReadNextData + sizeNextChunk;
        }


        this.endFirstCodeNumber = startReadNextData;


        return res;
    }
    private Integer fi0Decode() {
        return buffer.getPositionFirstOne();
    }





    private interface FiMinimizerNumber {
        public ChunkBits code(ChunkBits number) throws Exception;
        public ChunkBits decode() throws Exception;
    }
    private class Fi0 implements FiMinimizerNumber {

        @Override
        public ChunkBits code(ChunkBits number) throws Exception {
            if (number.equals(new ChunkBitsClass("0"))) {
                return new ChunkBitsClass("1");
            }

            return f0(number);
        }
        private ChunkBits f0(ChunkBits number) throws Exception {
            int lengthNumber = number.getInt();
            assert(lengthNumber >= 0);

            String codeNumber = "";
            for (int i = 0; i < lengthNumber; i++) {
                codeNumber += "0";
            }

            codeNumber += "1";
            return new ChunkBitsClass(codeNumber);
        }
        @Override
        public ChunkBits decode() throws Exception {
            return decodeFi(0);
        }
    }
    private class Fi1 implements FiMinimizerNumber {

        @Override
        public ChunkBits code(ChunkBits number) throws Exception {
            return codeFiMore0(1, number);
        }

        @Override
        public ChunkBits decode() throws Exception {
            return decodeFi(1);
        }
    }
    private class Fi2AndMore implements FiMinimizerNumber {
        private int fi;

        public Fi2AndMore(int fi) {
            this.fi = fi;
        }

        @Override
        public ChunkBits code(ChunkBits number) throws Exception {
            return codeFiMore0(fi, number);
        }

        @Override
        public ChunkBits decode() throws Exception {
            return decodeFi(fi);
        }
    }
}
