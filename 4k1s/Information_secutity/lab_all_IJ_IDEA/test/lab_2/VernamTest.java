package lab_2;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class VernamTest {

    @Test
    public void shared_test() throws Exception {
        for (int i = -128; i < 128; i++) {
            BigInteger materialBI = BigInteger.valueOf(i);
            CryptoAbonent aCryptAbon = new Vernam();
            CryptoAbonent bCryptAbon = new Vernam();
            BigInteger encryptBI = aCryptAbon.encrypt(materialBI, bCryptAbon);

            BigInteger decryptBI = null;
            try {
                decryptBI = bCryptAbon.decrypt(encryptBI);
            }
            catch (Exception textExept) {
                System.out.println(textExept);
            }
            assertEquals(materialBI, decryptBI);
        }
    }
}