package Logic.StackBooks;

public interface UnsignedByte {
    public static int getInt(byte bytee) {
        int res = 0;
        int signedOne = 0b10000000;

        return bytee + signedOne;
    }

    public static byte toSignedByte(int unsignedByte) {

        assert(unsignedByte >= 0 && unsignedByte < 256);

        int signedOne = 0b10000000;

        byte res = (byte) (unsignedByte - signedOne);
        return res;


    }
}
