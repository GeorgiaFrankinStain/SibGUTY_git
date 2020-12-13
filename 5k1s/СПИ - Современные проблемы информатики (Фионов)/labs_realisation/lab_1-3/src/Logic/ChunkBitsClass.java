package Logic;

import Logic.StackBooks.UnsignedByte;

import javax.swing.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ChunkBitsClass implements ChunkBits {
    private byte[] arrayBits;
    private int sizeBitsChunk;


    public ChunkBitsClass(int number) throws Exception {
        this(intToByteArray(number), 32);
        this.deleteZerosInStart();
    }
    public ChunkBitsClass(byte number) throws Exception {
        this(byteToByteArray(number), 8);
        this.deleteZerosInStart();
    }
    private static byte[] byteToByteArray(byte number) {
        byte[] res = new byte[1];
        res[0] = number;
        return res;
    }

    private static byte[] intToByteArray(int numberInt) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(numberInt).array();
        return bytes;
    }

    public ChunkBitsClass(String number) throws Exception {
        this(getByteFromString(number), getBitSizeNumber(number));
    }

    public ChunkBitsClass(byte[] arrayBits, int sizeBitsChunk) throws Exception {
        int desiredByteSizeArray = ChunkBits.getSizeByteArrayForSaveBitsArray(sizeBitsChunk);

        if (sizeBitsChunk == 0) {
            arrayBits = new byte[1];
        } else if (arrayBits.length > desiredByteSizeArray) {
            arrayBits = newArrayFromOldChangedSize(arrayBits, desiredByteSizeArray);
        } else if (arrayBits.length < desiredByteSizeArray) {
            throw new Exception("the declared array size is smaller than the actual one");
        }

        toZeroNonSignificantBytes(arrayBits, sizeBitsChunk);

        this.arrayBits = arrayBits;

        this.sizeBitsChunk = sizeBitsChunk;
    }

    @Override
    public int size() {
        return this.sizeBitsChunk;
    }

    @Override
    public boolean getBitFromIndex(int index) {
        int lengthArrayInBits = this.arrayBits.length * 8;

        if (index < 0 || index >= lengthArrayInBits) {
            assert (false);
        }

        int toBit = index + 1;
        ScannerChunkDataLessThanByte scannerChunkDataLessThanByte =
                new ScannerChunkDataLessThanByteClass(index, toBit);
        byte byteScan = scannerChunkDataLessThanByte.scanFromInfiniteArray();

        return firstBitByte(byteScan);
    }

    @Override
    public byte[] getAllContent() {
        if (this.size() == 0) {
            return new byte[0];
        } else {
            return arrayBits;
        }
    }

    @Override
    public ChunkBits getBitsFromInfiniteArray(int fromBit, int toBit) throws Exception {
        boolean inputDataIsValide = fromBit < toBit;
        if (!inputDataIsValide) {
            return new ChunkBitsClass(null, 0);
        }
        int bitSize = toBit - fromBit;
        byte[] newArray = getByteArrayFromInfiniteArray(fromBit, toBit);

        ChunkBits resChunkBits = new ChunkBitsClass(newArray, bitSize);
        return resChunkBits;
    }

    private byte[] getByteArrayFromInfiniteArray(int fromBit, int toBit) {
        int bitSize = toBit - fromBit;
        int sizeArrayByte = sizeBitsArrayInSizeByteArray(bitSize);
        byte[] newArray = new byte[sizeArrayByte];

        writeToArray(newArray, fromBit, toBit);
        return newArray;
    }

    @Override
    public String getBinareString() {
        String res = "";

        for (int i = 0; i < this.arrayBits.length; i++) {
            res += byteToBinareView(this.arrayBits[i]);
        }

        return res.substring(0, size());
    }

    @Override
    public ChunkBits concatinateWith(ChunkBits chunkBitsConcatinate) throws Exception {
        int sumBitsSizes = this.size() + chunkBitsConcatinate.size();
        int sizeNewArray = ChunkBits.getSizeByteArrayForSaveBitsArray(sumBitsSizes);

        byte[] newArray = newArrayFromOldChangedSize(this.arrayBits, sizeNewArray);
//        printArrayInOtherArray(this.arrayBits, newArray, 0);

        byte[] secondArray = getSecondArrayMergeWithLastByteOfFirstArray(chunkBitsConcatinate);

        int startSecondArray = this.arrayBits.length - 1;
/*        boolean inFirstArrayExistPlaceForBitsFromSecondArray = this.size() % 8 != 0;
        if (inFirstArrayExistPlaceForBitsFromSecondArray) {
            startSecondArray--;
        }*/
        printArrayInOtherArray(secondArray, newArray, startSecondArray);

        return new ChunkBitsClass(newArray, sumBitsSizes);
    }

    @Override
    public void addInEnd(ChunkBits chunkBits) throws Exception {
        this.arrayBits = this.concatinateWith(chunkBits).getAllContent();
        this.sizeBitsChunk += chunkBits.size();
    }

    @Override
    public void addOneInStart() throws Exception {
        ChunkBits chunkWithOne = new ChunkBitsClass("1");
        this.arrayBits = chunkWithOne.concatinateWith(this).getAllContent();
        this.sizeBitsChunk++;
    }

    private byte[] getSecondArrayMergeWithLastByteOfFirstArray(ChunkBits chunkBitsConcatinate) throws Exception {

        int offsetRightSecondArray = this.size() % 8;
        if (offsetRightSecondArray == 0 && this.size() > 7) {
            offsetRightSecondArray = 8;
        }
        byte[] secondArray = chunkBitsConcatinate.getBitsFromInfiniteArray(
                -offsetRightSecondArray,
                chunkBitsConcatinate.size()
        ).getAllContent();

        byte endByteFirstArray = this.arrayBits[this.arrayBits.length - 1];
        secondArray[0] |= endByteFirstArray;

        return secondArray;
    }

    @Override
    public void deleteZerosInStart() throws Exception {
        int zerosInStart = countZerosInStartBeforeFirstOne();
        boolean allIsZeros = zerosInStart == this.size();
        int numberDeleteingZerosInStart = zerosInStart;
        if (allIsZeros) {
            numberDeleteingZerosInStart--;
        }

        this.arrayBits = this.getBitsFromInfiniteArray(numberDeleteingZerosInStart, size()).getAllContent();
        this.sizeBitsChunk -= numberDeleteingZerosInStart;
    }

    @Override
    public Integer getPositionFirstOne() {
        for (int i = 0; i < this.size(); i++) {
            boolean currentBitIsOne = this.getBitFromIndex(i) == true;
            if (currentBitIsOne) {
                return i;
            }
        }

        return null;
    }

    @Override
    public int getInt() throws Exception {

        ChunkBits chunkBits = this.getBitsFromInfiniteArray(this.size() - 32, size());
        return convertByteArrayToInt2(chunkBits.getAllContent());
    }

    @Override
    public byte getByte() throws Exception {/*
        int size = this.size();
        System.out.println("size234: " + size);
        assert(0 < arrayBits.length && arrayBits.length < 5 && 0 < this.size() && this.size() < 33);


        int missingBitsOnLeft = 8 - this.size();

        byte[] shiftChunk = getByteArrayFromInfiniteArray(-missingBitsOnLeft, this.size());
        assert(shiftChunk.length == 1);

        return shiftChunk[0];*/
        int res = this.getInt();
        byte result = UnsignedByte.toSignedByte(res);
        System.out.println("res from int: " + result);
        assert(-129 < result && result < 128);

        return result;
    }

    @Override
    public boolean isEmpty() throws Exception {
        ChunkBits emptyChunk = new ChunkBitsClass("");
        boolean res = this.equals(emptyChunk);
        return res;
    }

    public int convertByteArrayToInt2(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);
    }


    private int countZerosInStartBeforeFirstOne() {
        int counterZeros = 0;
        for (int i = 0; i < this.size(); i++) {
            boolean currentBitIsZero = this.getBitFromIndex(i) == false;
            if (currentBitIsZero) {
                counterZeros++;
            } else {
                break;
            }
        }

        return counterZeros;
    }

    @Override
    public String toString() {
        int maxCountPrint = 20;
        String numbersString = "";

        for (int i = 0; i < this.arrayBits.length && i < maxCountPrint; i++) {
            numbersString += byteToBinareView(this.arrayBits[i]) + " ";
        }

        String continuedSymbol = "";
        if (this.arrayBits.length > maxCountPrint)
            continuedSymbol = "...";

        return "([size: " + this.size() + "] " + numbersString + continuedSymbol + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;


        ChunkBits other = (ChunkBits) obj;
        boolean arraysEquals = Arrays.equals(other.getAllContent(), this.getAllContent());
        boolean sizeEquals = this.size() == other.size();
        return arraysEquals && sizeEquals;
    }


    private boolean firstBitByte(byte bits) {
        byte oneInStart = (byte) 0b10000000;
        return (bits & oneInStart) == oneInStart;
    }

    private void toZeroNonSignificantBytes(byte[] arrayBits, int sizeBitsChunk) {
        int numberBitsInByteArray = arrayBits.length * 8;
        int significanBitsNumber = sizeBitsChunk;

        int countNonSignificanBits = numberBitsInByteArray - significanBitsNumber;
        boolean existExtraBits = countNonSignificanBits > 0;
/*
        if (countNonSignificanBits >= 8) {
            assert (false);
        }*/

        if (existExtraBits) {
            byte mask = (byte) (0b11111111 << countNonSignificanBits);
            arrayBits[arrayBits.length - 1] &= mask;
        }
    }

    private byte[] newArrayFromOldChangedSize(byte[] oldArray, int newByteSize) {
        byte[] arrayNew = new byte[newByteSize];


        for (int i = 0; i < Math.min(newByteSize, oldArray.length); i++) {
            arrayNew[i] = oldArray[i];
        }

        return arrayNew;
    }

    private void printArrayInOtherArray(byte[] fromArray, byte[] toArray, int from) {
        int end = from + fromArray.length;
        for (int i = from, j = 0; i < end; i++, j++) {
            toArray[i] = fromArray[j];
        }
    }

    private void writeToArray(byte[] newArray, int fromBit, int toBit) {
        int newArrayStartPositionByteWrite = getNewStartPositionByte(fromBit);
        int bitPositionStartReadFirstByte = skippingIterationWithWritingZeros(fromBit);
        for (;
             bitPositionStartReadFirstByte < toBit;
             bitPositionStartReadFirstByte += 8, newArrayStartPositionByteWrite++) {

            ScannerChunkDataLessThanByte scannerChunkDataLessThanByte =
                    new ScannerChunkDataLessThanByteClass(bitPositionStartReadFirstByte, toBit);

            byte byteScan = scannerChunkDataLessThanByte.scanFromInfiniteArray();
            newArray[newArrayStartPositionByteWrite] = byteScan;
        }
    }

    private String byteToBinareView(byte numberByte) {
        return String.format("%8s", Integer.toBinaryString(numberByte & 0xFF)).replace(' ', '0');
    }

    private static int sizeBitsArrayInSizeByteArray(int bitSize) {
        int byteSize = bitSize / 8;

        boolean isMultipleByte = bitSize % 8 == 0;
        if (!isMultipleByte) {
            byteSize++;
        }

        return byteSize;
    }


    private int getNewStartPositionByte(int oldStartPosition) {
        boolean newArrayWillBeIndentedWithZeroInStart = oldStartPosition < 0;
        int newStartPositionBit;
        if (newArrayWillBeIndentedWithZeroInStart) {
            int sizeIndedtedFillZero = Math.abs(oldStartPosition);
            newStartPositionBit = sizeIndedtedFillZero;
        } else {
            newStartPositionBit = 0;
        }

        int newStartPositionByte = newStartPositionBit / 8;
        return newStartPositionByte;
    }

    private int skippingIterationWithWritingZeros(int fromBit) {
        boolean positionFromBitIncludedOldArray = fromBit >= 0;
        if (positionFromBitIncludedOldArray) {
            return fromBit;
        } else {
            return fromBit % 8;
        }
    }


    private static byte[] getByteFromString(String number) {
        number = deleteSpace(number);
        boolean stringContainsOnlyZerosAndOnes = number.matches("([0-1])*");
        if (!stringContainsOnlyZerosAndOnes) {
            throw new IllegalArgumentException("the string contains not only zeros and ones");
        }

        int bitSizeNumber = getBitSizeNumber(number);
        int sizeByteArray = sizeBitsArrayInSizeByteArray(bitSizeNumber);
        byte[] array = new byte[sizeByteArray];
        for (int numberByteRead = 0; numberByteRead < sizeByteArray; numberByteRead++) {
            int bitStartPosition = numberByteRead * 8;
            int bitEndPosition = getEndPosition(bitStartPosition, bitSizeNumber);

            array[numberByteRead] = getNextByte(number, bitStartPosition, bitEndPosition);
        }

        return array;
    }

    private static String deleteSpace(String string) {
        return string.replaceAll("\\s+", "");
    }

    private static int getEndPosition(int bitStartPosition, int bitSizeNumber) {
        int bitEndPosition = bitStartPosition + 8;
        if (bitEndPosition > bitSizeNumber) {
            bitEndPosition = bitSizeNumber;
        }
        return bitEndPosition;
    }

    private static int getBitSizeNumber(String number) {
        number = deleteSpace(number);
        return number.length();
    }

    private static byte getNextByte(String number, int from, int to) {

        byte res = 0b00000000;
        for (int i = from, positionInByte = 0; i < to; i++, positionInByte++) {
            if (number.charAt(i) == '1') {
                int offsetFromRight = 7 - positionInByte;
                byte maskAddBitOne = (byte) (0b00000001 << offsetFromRight);
                res |= maskAddBitOne;
            }
        }

        return res;
    }


    //====Private Classes====================

    private interface ScannerChunkDataLessThanByte {
        public byte scanFromInfiniteArray();
    }

    private class ScannerChunkDataLessThanByteClass implements ScannerChunkDataLessThanByte {

        int bitPositionStartReadFirstByte;
        int countNonSignificantBitsLeft;
        int toBit;

        public ScannerChunkDataLessThanByteClass(int bitPositionStartReadFirstByte, int toBit) {
            this.toBit = toBit;
            this.bitPositionStartReadFirstByte = bitPositionStartReadFirstByte;

            int remainderDivizion = bitPositionStartReadFirstByte % 8;

            countNonSignificantBitsLeft = remainderDivizion;
            if (remainderDivizion < 0) {
                countNonSignificantBitsLeft = 8 - Math.abs(remainderDivizion);
            }
        }

        @Override
        public byte scanFromInfiniteArray() {
            return scanByteFromOldArray();
        }


        private byte scanByteFromOldArray() {
            byte leftPart = getLeftPartFromFirstByte();
            byte rightPart = getRightPartFromSecondByte();


            byte byteScan = (byte) (leftPart | rightPart);

            int significantBitsOnLeftTrySend = 8;
            int significantBitsOnLeftNeed = this.toBit - this.bitPositionStartReadFirstByte;


            if (significantBitsOnLeftTrySend > significantBitsOnLeftNeed) {
                byteScan = zeroingLastBits(byteScan, 8 - significantBitsOnLeftNeed);
            }

            return byteScan;
        }


        private byte getRightPartFromSecondByte() {
            byte rightPart = (byte) 0b00000000;

            boolean secondByteStartOnMultiplePosition = countNonSignificantBitsLeft == 0;
            boolean returnByteThatDoesNotAffectMultiplicate = secondByteStartOnMultiplePosition;
            if (!returnByteThatDoesNotAffectMultiplicate) {
                byte secondByte = getSecondByteForRightPart();

                rightPart = cutExtaraBitsInSecondByte(
                        secondByte);
            }

            return rightPart;
        }


        private byte getSecondByteForRightPart() {
            int bytePositionSecondByte = getPositionSecondByte();
            boolean endOfCurrentReturnByteGoesRightEdgeOldArray =
                    (arrayBits.length * 8) < (bitPositionStartReadFirstByte + 8);
            boolean enabledSimulationSecondByte = endOfCurrentReturnByteGoesRightEdgeOldArray;
            byte secondByte = 0b00000000;
            if (!enabledSimulationSecondByte) {
                secondByte = arrayBits[bytePositionSecondByte];
            }

            return secondByte;
        }

        private byte cutExtaraBitsInSecondByte(
                byte secondByte
        ) {
            int significantBitsOnLeftInSecondByte = countNonSignificantBitsLeft;
            byte rightPart = getSignificantLeftPartOfSecondByte(secondByte, significantBitsOnLeftInSecondByte);
            return rightPart;
        }

        private int getPositionFirstByte() {

            int bytePosition = bitPositionStartReadFirstByte / 8;
            if (-8 < bitPositionStartReadFirstByte && bitPositionStartReadFirstByte < 0) {
                bytePosition--;
            }

            return bytePosition;
        }

        private int getPositionSecondByte() {
            return getPositionFirstByte() + 1;
        }

        private byte getLeftPartFromFirstByte() {
            byte firstByte = 0b00000000;
            boolean enabledSimulationFirstByte = bitPositionStartReadFirstByte < 0;

            int bytePosition = getPositionFirstByte();

            if (!enabledSimulationFirstByte) {
                assert (bytePosition != -1);
                firstByte = arrayBits[bytePosition];
            }

            byte leftPart = getSignificantRightPartOfFirstByte(firstByte);
            return leftPart;
        }

        private byte getSignificantRightPartOfFirstByte(byte bits) {
            return (byte) (bits << countNonSignificantBitsLeft);
        }

        private byte zeroingLastBits(byte bits, int numberZeroingBitsInEnd) {
            byte maskZeroingBitsInEnd = (byte) (0b11111111 << numberZeroingBitsInEnd);
            return (byte) (bits & maskZeroingBitsInEnd);
        }

        private byte getSignificantLeftPartOfSecondByte(byte bits, int countSignificantBitsOnLeft) {
            int countNonSignificantBitsOnRight = 8 - countSignificantBitsOnLeft;
            return offsetRightFillZero(bits, countNonSignificantBitsOnRight);

            //TODO don't work >>> offset right with fill zero
        }

        private byte offsetRightFillZero(byte bits, int countOffsetRight) {
            byte offsetBits = (byte) (bits >> countOffsetRight);
            byte zeroMaskOnLeft = maskDeliteBitsOnLeft(countOffsetRight);
            return (byte) (offsetBits & zeroMaskOnLeft);
        }

        private byte maskDeliteBitsOnLeft(int countZeroOnLeft) {
            if (countZeroOnLeft == 0) {
                return (byte) 0b11111111;
            } else if (countZeroOnLeft == 1) {
                return 0b01111111;
            } else {
                int offsetLengthGivenExistZero = countZeroOnLeft - 1;
                return (byte) (0b01111111 >> offsetLengthGivenExistZero);
            }
        }
    }
}
