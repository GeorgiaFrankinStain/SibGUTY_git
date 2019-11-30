package lab_1;

import java.math.BigInteger;
import java.util.Random;

public class PrimeNumber {
    static public boolean is_prime(BigInteger numberBI) {
        boolean numberBI_less_or_equal_than_0_BOOL =
                numberBI.compareTo(BigInteger.ONE) <= 0;

        if (numberBI_less_or_equal_than_0_BOOL) {
            return false;
        }




        BigInteger sqrt_numberEBI = SqrtBigInteger.get(numberBI);
        for (BigInteger iBI = BigInteger.valueOf(2);
             iBI.compareTo(sqrt_numberEBI) <= 0; //i <= sqrt
             iBI = iBI.add(BigInteger.ONE)) {

            boolean remainder_of_division_equal_0_BOOL =
                    (numberBI.mod(iBI)).compareTo(BigInteger.ZERO) == 0;
            if (remainder_of_division_equal_0_BOOL) {
                return false;
            }
        }




        return true;
    }
    static public BigInteger generate(BigInteger rangeBI) {
        BigInteger rand_modBI;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(rangeBI.bitLength(), tRandom);
            rand_modBI = randBI.mod(rangeBI);
        } while (!is_prime(rand_modBI));




        return rand_modBI;
    }
    static public BigInteger coprime_generate(BigInteger inputBI, BigInteger rangeBI) {
        BigInteger rand_modBI = null;
        BigInteger gcdBI = null;

        do {
            Random tRandom = new Random();
            BigInteger randBI = new BigInteger(rangeBI.bitLength(), tRandom);
            rand_modBI = randBI.mod(rangeBI);
            gcdBI = ExtendedGCD.get(inputBI, rand_modBI)[0];
        } while (gcdBI.compareTo(BigInteger.ONE) != 0);




        return rand_modBI;
    }
}