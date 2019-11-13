package lab_1;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class FastExponentiationModuloTest {

    @Test
    public void get() {
        {
            BigInteger actualBI = FastExponentiationModulo.get(
                    BigInteger.valueOf(13),
                    BigInteger.valueOf(17),
                    BigInteger.valueOf(15));
            BigInteger expectedBI = BigInteger.valueOf(13);
            assertEquals("13 = 13 ^ 17 % 15", expectedBI, actualBI);
        }


        {
            BigInteger actualBI = FastExponentiationModulo.get(
                    BigInteger.valueOf(13),
                    BigInteger.valueOf(7),
                    BigInteger.valueOf(15));
            BigInteger expectedBI = BigInteger.valueOf(7);
            assertEquals("7 = 13 ^ 7 % 15", expectedBI, actualBI);
        }
    }
}