package logic;

import java.util.Arrays;

public class ChunkBitsClass implements ChunkBits {
    private byte[] arrayBits;
    private int sizeBitsChunk;

    public ChunkBitsClass(byte[] arrayBits, int sizeBitsChunk) throws Exception {
        int desiredSizeArray = ChunkBits.getSizeByteArrayForSaveBitsArray(sizeBitsChunk);

        if (arrayBits.length > desiredSizeArray) {
            arrayBits = newArrayFromOldChangedSize(arrayBits, desiredSizeArray);
        } else if (arrayBits.length < desiredSizeArray) {
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
        return arrayBits;
    }

    @Override
    public ChunkBits getBitsFromInfiniteArray(int fromBit, int toBit) throws Exception {
        boolean inputDataIsValide = fromBit < toBit;
        assert (inputDataIsValide);

        int bitSize = toBit - fromBit;
        int sizeArrayByte = sizeBitsArrayInSizeByteArray(bitSize);
        byte[] newArray = new byte[sizeArrayByte];

        writeToArray(newArray, fromBit, toBit);

        ChunkBits resChunkBits = new ChunkBitsClass(newArray, bitSize);
        return resChunkBits;
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
        return Arrays.equals(other.getAllContent(), this.arrayBits)
                && this.size() == other.size();
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

        if (countNonSignificanBits >= 8) {
            assert (false);
        }

        if (existExtraBits) {
            byte mask = (byte) (0b11111111 << countNonSignificanBits);
            byte fromArray = arrayBits[arrayBits.length - 1];
            fromArray &= mask;
            arrayBits[arrayBits.length - 1] &= mask;
        }
    }

    private byte[] newArrayFromOldChangedSize(byte[] oldArray, int newSize) {
        byte[] arrayNew = new byte[newSize];

        for (int i = 0; i < newSize; i++) {
            arrayNew[i] = oldArray[i];
        }

        return arrayNew;
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

    private int sizeBitsArrayInSizeByteArray(int bitSize) {
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
