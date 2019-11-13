package lab_1;

import java.math.BigInteger;

public class FastExponentiationModulo {
    public static BigInteger get (BigInteger aBI, BigInteger degree_xBI, BigInteger moduloBI) {

        BigInteger resultBI = new BigInteger("1");

        while (true) {
            boolean degree_xBI_more_than_0 = 1 == degree_xBI.compareTo(BigInteger.ZERO);
            if (!degree_xBI_more_than_0)
                break;

            boolean last_bit_is_1BOOL = 0 == (degree_xBI.and(BigInteger.valueOf(1))).compareTo(BigInteger.ONE);
            boolean sum_up_the_current_part_of_the_polynomialBOOL = last_bit_is_1BOOL;
            if (sum_up_the_current_part_of_the_polynomialBOOL) {
                BigInteger remainder_of_divisionBI = (resultBI.multiply(aBI).divideAndRemainder(moduloBI))[1] ;
                resultBI = remainder_of_divisionBI;
            }

            BigInteger next_part_of_polynomiaBI = ((aBI.multiply(aBI)).divideAndRemainder(moduloBI))[1];
            aBI = next_part_of_polynomiaBI;
            degree_xBI = degree_xBI.shiftRight(1);
        }

        return resultBI;

    }

    public static BigInteger fastExponentiationModuloCodeStyle2 (
            BigInteger aBI,
            BigInteger degree_xBI,
            BigInteger moduloBI) {

        BigInteger resultBI = new BigInteger("1");

        while (true) {
            boolean degree_xBI___more_than_0_BOOL =
                    1 == degree_xBI.compareTo(BigInteger.ZERO);

            if (!degree_xBI___more_than_0_BOOL)
                break;




            boolean last_bit_is_1_BOOL =
                    0 == (degree_xBI.and(BigInteger.valueOf(1))).compareTo(BigInteger.ONE);
            boolean to_sum_up_the_current_part_of_the_polynomial_BOOL =
                    last_bit_is_1_BOOL;

            if (to_sum_up_the_current_part_of_the_polynomial_BOOL) {

                BigInteger remainder_DivisionBI =
                        (resultBI.multiply(aBI).divideAndRemainder(moduloBI))[1] ;

                resultBI = remainder_DivisionBI;
            }




            BigInteger next__PArt_Polynom_BI =
                    ((aBI.multiply(aBI)).divideAndRemainder(moduloBI))[1];
            aBI = next__PArt_Polynom_BI;

            degree_xBI = degree_xBI.shiftRight(1);
        }

        return resultBI;

    }
}


/*
*     uint64_t result = 1;
    for (; x; x >>= 1) {
        if (x & 1)
            result = (result * a) % p;
        a = (a * a) % p;
    }
    return result;
  */