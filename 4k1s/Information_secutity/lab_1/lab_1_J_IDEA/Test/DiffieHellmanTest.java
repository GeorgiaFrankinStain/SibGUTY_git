import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class DiffieHellmanTest {

    @Test
    public void FOR_TEST___is_prime_number_BOOL() {
        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(17));
            boolean expectedBOOL = true;

            assertEquals(expectedBOOL, actualBOOL);
        }
        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(47));
            boolean expectedBOOL = true;

            assertEquals(expectedBOOL, actualBOOL);
        }




        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(49));
            boolean expectedBOOL = false;

            assertEquals(expectedBOOL, actualBOOL);
        }
        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(16));
            boolean expectedBOOL = false;

            assertEquals(expectedBOOL, actualBOOL);
        }
        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(1));
            boolean expectedBOOL = false;

            assertEquals(expectedBOOL, actualBOOL);
        }
        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(0));
            boolean expectedBOOL = false;

            assertEquals(expectedBOOL, actualBOOL);
        }
        {
            boolean actualBOOL = DiffieHellman.FOR_TEST___is_prime_number_BOOL(BigInteger.valueOf(-1));
            boolean expectedBOOL = false;

            assertEquals(expectedBOOL, actualBOOL);
        }
    }
}