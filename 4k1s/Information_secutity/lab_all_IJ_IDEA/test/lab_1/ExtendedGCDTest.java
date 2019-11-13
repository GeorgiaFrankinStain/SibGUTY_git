package lab_1;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class ExtendedGCDTest {

    @Test
    public void get() {
        {
            int[] actualArrI = ExtendedGCD.get(28, 19);
            int[] expectedArrI = {1, -2, 3};
            assertArrayEquals("28*(-2) + 19*(3) = 1", expectedArrI, actualArrI);
        }
    }

    @Test
    public void get1() {
        {
            BigInteger[] actualArrBI = ExtendedGCD.get(BigInteger.valueOf(28), BigInteger.valueOf(19));
            BigInteger[] expectedArrBI = {
                    BigInteger.valueOf(1),
                    BigInteger.valueOf(-2),
                    BigInteger.valueOf(3)
            };
            assertArrayEquals("BigInteger 28*(-2) + 19*(3) = 1", expectedArrBI, actualArrBI);
        }
        {
            BigInteger[] actualArrBI = ExtendedGCD.get(BigInteger.valueOf(28), BigInteger.valueOf(14));
            BigInteger[] expectedArrBI = {
                    BigInteger.valueOf(14),
                    BigInteger.valueOf(0),
                    BigInteger.valueOf(1)
            };
            assertArrayEquals("BigInteger 28*(-2) + 19*(3) = 1", expectedArrBI, actualArrBI);
        }
    }
}