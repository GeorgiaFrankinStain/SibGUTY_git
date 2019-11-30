package lab_1;

import java.math.BigInteger;
import java.util.Random;

public class MyBigInteger {
    public static BigInteger pow_positive_ingeter(BigInteger baseBI, BigInteger exponentBI) {
        BigInteger resultBI = BigInteger.ONE;
        for (BigInteger iBI = BigInteger.ZERO;
                iBI.compareTo(exponentBI) == -1; //i < exponent
                iBI = iBI.add(BigInteger.ONE)) {
            resultBI = resultBI.multiply(baseBI);
        }

        return  resultBI;
    }



    static public BigInteger generae_diapason(BigInteger fromBI, BigInteger toBI) throws Exception {
        BigInteger differenceBI = toBI.subtract(fromBI);

        boolean differenceBI__is_not_positive__BOOL =
                differenceBI.compareTo(BigInteger.ZERO) == -1;
        if (differenceBI__is_not_positive__BOOL) {
            throw new Exception("differenceBI__is_not_positive");
        }




        Random tRandom = new Random();
        BigInteger randBI = new BigInteger(differenceBI.bitLength(), tRandom);
        BigInteger rand_modBI = randBI.mod(differenceBI);


        return rand_modBI.add(fromBI);
    }
}
