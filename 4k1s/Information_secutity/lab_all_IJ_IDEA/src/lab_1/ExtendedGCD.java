package lab_1;

import java.math.BigInteger;
import java.util.Scanner;

public class ExtendedGCD {
    static int[] get(int aI, int bI) {
        int[] U_ArrI = {aI, 1, 0};
        int[] V_ArrI = {bI, 0, 1};

        return system_get(U_ArrI, V_ArrI);
    }

    private static int[] system_get(int[] U_ArrI, int[] V_ArrI) {
        int aI = U_ArrI[0];
        int bI = V_ArrI[0];
        int[] res_ArrI;


        if (bI == 0) {
            res_ArrI = U_ArrI;
        } else {
            int qI = aI / bI;

            int[] new_V_ArrI = {
                    aI % bI,
                    U_ArrI[1] - qI * V_ArrI[1],
                    U_ArrI[2] - qI * V_ArrI[2]};

            int[] new_U_ArrI = V_ArrI.clone();




            res_ArrI = system_get(new_U_ArrI, new_V_ArrI);
        }


        return res_ArrI;
    }




    static BigInteger[] get(BigInteger aBI, BigInteger bBI) {
        BigInteger[] U_ArrBI = {aBI, BigInteger.valueOf(1), BigInteger.valueOf(0)};
        BigInteger[] V_ArrBI = {bBI, BigInteger.valueOf(0), BigInteger.valueOf(1)};

        return system_get(U_ArrBI, V_ArrBI);
    }

    private static BigInteger[] system_get(BigInteger[] U_ArrBI, BigInteger[] V_ArrBI) {
        BigInteger aBI = U_ArrBI[0];
        BigInteger bBI = V_ArrBI[0];
        BigInteger[] res_ArrBI;

        boolean if_in_the_previous_iteration_the_remainder_of_the_division_is_zero =
                0 == bBI.compareTo(BigInteger.ZERO); //because you cannot divide by zero
        if (if_in_the_previous_iteration_the_remainder_of_the_division_is_zero) {
            res_ArrBI = U_ArrBI;
        } else {
            BigInteger qBI = aBI.divide(bBI);

            BigInteger[] new_V_ArrBI = {
                    (aBI.divideAndRemainder(bBI))[1],
                    (U_ArrBI[1]).subtract(qBI.multiply(V_ArrBI[1])),
                    (U_ArrBI[2]).subtract(qBI.multiply(V_ArrBI[2]))};

            BigInteger[] new_U_ArrBI = V_ArrBI.clone();




            res_ArrBI = system_get(new_U_ArrBI, new_V_ArrBI);
        }


        return res_ArrBI;
    }
}