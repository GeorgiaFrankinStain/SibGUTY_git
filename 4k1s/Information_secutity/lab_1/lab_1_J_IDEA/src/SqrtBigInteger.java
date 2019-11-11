import java.math.BigInteger;

public class SqrtBigInteger {
    public static BigInteger get(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for (;;) {
//            if (div.compareTo(BigInteger.ZERO) == 0) //KOSTIL
//                return BigInteger.ONE;


            BigInteger y = div.add(x.divide(div)).shiftRight(1);
//            System.out.println(div); //DEBUG_DELETE
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
}
