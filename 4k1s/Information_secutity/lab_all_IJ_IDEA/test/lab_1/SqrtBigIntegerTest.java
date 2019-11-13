package lab_1;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class SqrtBigIntegerTest {

    @Test
    public void get() {
        {
            BigInteger actualBI = SqrtBigInteger.get(BigInteger.valueOf(4));
            BigInteger expectedBI = BigInteger.valueOf(2);

            assertEquals(expectedBI, actualBI);
        }




        {
            BigInteger actualBI = SqrtBigInteger.get(BigInteger.valueOf(9));
            BigInteger expectedBI = BigInteger.valueOf(3);

            assertEquals(expectedBI, actualBI);
        }
    }
}