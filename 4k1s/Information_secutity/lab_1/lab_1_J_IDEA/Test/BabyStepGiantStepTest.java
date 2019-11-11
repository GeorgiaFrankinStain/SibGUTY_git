import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class BabyStepGiantStepTest {

    @Test
    public void get() {
        // при данных значениях решения нет
        {
            BigInteger aBI = BigInteger.valueOf(47);
            BigInteger yBI = BigInteger.valueOf(23);
            BigInteger pBI = BigInteger.valueOf(16);
            BigInteger xBI = BabyStepGiantStep.get(aBI, yBI, pBI);
            assertEquals(null, xBI);
        }
        {
            BigInteger aBI = BigInteger.valueOf(13);
            BigInteger yBI = BigInteger.valueOf(15);
            BigInteger pBI = BigInteger.valueOf(16);
            BigInteger xBI = BabyStepGiantStep.get(aBI, yBI, pBI);
            assertEquals(null, xBI);
        }
        {
            BigInteger aBI = BigInteger.valueOf(13);
            BigInteger yBI = BigInteger.valueOf(15);
            BigInteger pBI = BigInteger.valueOf(15);
            BigInteger xBI = BabyStepGiantStep.get(aBI, yBI, pBI);
            assertEquals(null, xBI);
        }


        // решение есть
        {
            BigInteger expected_x_BI = BigInteger.valueOf(5);
            BigInteger aBI = BigInteger.valueOf(2);
            BigInteger pBI = BigInteger.valueOf(23);
//            BigInteger yBI = BigInteger.valueOf(9); // FastExponentiationModulo.get(aBI, expected_x_BI, pBI); //9;
            BigInteger yBI = FastExponentiationModulo.get(aBI, expected_x_BI, pBI); //9;


            BigInteger xBI = BabyStepGiantStep.get(aBI, yBI, pBI);
            assertEquals(expected_x_BI, xBI);
        }
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 7; j++) {
                for (int k = 1; k < 7; k++) {
                    {
                        BigInteger expected_x_BI = BigInteger.valueOf(i);
                        BigInteger aBI = BigInteger.valueOf(j);
                        BigInteger pBI = BigInteger.valueOf(k);
                        BigInteger yBI = FastExponentiationModulo.get(aBI, expected_x_BI, pBI);


                        // System.out.println("IMPUT: " + aBI + "^" + expected_x_BI + " mod " + pBI + " = " + yBI); //DEBUG_DELETE
                        BigInteger xBI = BabyStepGiantStep.get(aBI, yBI, pBI);
//                        assertEquals(expected_x_BI, xBI);
                        BigInteger actual_fast_exp_mod_BI = FastExponentiationModulo.get(aBI, xBI, pBI);
                        // System.out.println(); //DEBUG_DELETE


                        assertEquals(yBI, actual_fast_exp_mod_BI);
                    }
                }
            }
        }
/*        {
            BigInteger expected_x_BI = BigInteger.valueOf(2);
            BigInteger aBI = BigInteger.valueOf(3);
            BigInteger pBI = BigInteger.valueOf(7);
            BigInteger yBI = FastExponentiationModulo.get(aBI, expected_x_BI, pBI);


            BigInteger xBI = BabyStepGiantStep.get(aBI, yBI, pBI);
            assertEquals(expected_x_BI, xBI);
        }*/
    }
}