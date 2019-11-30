package lab_1;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MyBigIntegerTest {

    @Test
    public void pow_positive_ingeter() {
        {
            BigInteger baseBI = BigInteger.valueOf(2);
            BigInteger exponentBI = BigInteger.valueOf(2);

            BigInteger actualBI = MyBigInteger.pow_positive_ingeter(baseBI, exponentBI);
            BigInteger expectedBI = BigInteger.valueOf(4);
            assertEquals(expectedBI, actualBI);
        }
        {
            BigInteger baseBI = BigInteger.valueOf(1);
            BigInteger exponentBI = BigInteger.valueOf(2);

            BigInteger actualBI = MyBigInteger.pow_positive_ingeter(baseBI, exponentBI);
            BigInteger expectedBI = BigInteger.valueOf(1);
            assertEquals(expectedBI, actualBI);
        }
        {
            BigInteger baseBI = BigInteger.valueOf(0);
            BigInteger exponentBI = BigInteger.valueOf(2);

            BigInteger actualBI = MyBigInteger.pow_positive_ingeter(baseBI, exponentBI);
            BigInteger expectedBI = BigInteger.valueOf(0);
            assertEquals(expectedBI, actualBI);
        }
    }
}