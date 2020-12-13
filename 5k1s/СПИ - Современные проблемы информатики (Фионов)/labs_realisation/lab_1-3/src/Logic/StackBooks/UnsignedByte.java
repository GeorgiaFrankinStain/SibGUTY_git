package Logic.StackBooks;

public interface UnsignedByte {
    public static int getInt(byte bytee) {
        int res = 0;
        int signedOne = 0b10000000;
/*        if (bytee < 0) {
            res += signedOne;
        }

        byte mask = 0b01111111;
        byte withoutSignedOne = (byte) (bytee & mask);*/

//        return res + withoutSignedOne;
        return bytee + signedOne;
    }

    public static byte toSignedByte(int unsignedByte) {

        assert(unsignedByte >= 0 && unsignedByte < 256);

        int signedOne = 0b10000000;

        byte res = (byte) (unsignedByte - signedOne);
        return res;


        /*
        if (unsignedByte < 128) {
            return (byte) unsignedByte;
        } else {
            return (byte) ((unsignedByte - signedOne) * -1);
        }*/
    }
}
