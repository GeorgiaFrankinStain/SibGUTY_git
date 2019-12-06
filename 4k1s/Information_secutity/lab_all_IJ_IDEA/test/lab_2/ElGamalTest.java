package lab_2;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ElGamalTest {

    @Test
    public void SHARED_TEST() throws Exception {
        {
            for (int i = 0; i < 13; i++) {
                BigInteger materialBI = BigInteger.valueOf(i);
                ElGamal aCryptAbon = new ElGamal();
                ElGamal bCryptAbon = new ElGamal();
                aCryptAbon._self_send_create_shared_data(bCryptAbon);
                ArrayList<BigInteger> encryptArrListBI = bCryptAbon.encrypt_in_ArrList(materialBI, null);

                BigInteger decryptBI = null;
                decryptBI = aCryptAbon.decrypt(encryptArrListBI);
                assertEquals(materialBI, decryptBI);
            }
        }
    }
}