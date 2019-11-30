package lab_2;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class AdiShamirTest {

    @Test
    public void SHARED_TEST() throws Exception {
        for (int i = 0; i < 13; i++) {
            BigInteger materialBI = BigInteger.valueOf(i);
            CryptoAbonent aCryptAbon = new AdiShamir();
            CryptoAbonent bCryptAbon = new AdiShamir();
            aCryptAbon._self_send_create_shared_data(bCryptAbon);
            BigInteger encryptBI = bCryptAbon.encrypt(materialBI, aCryptAbon);

            BigInteger decryptBI = null;
            try {
                decryptBI = aCryptAbon.decrypt(encryptBI);
            }
            catch (Exception textExept) {
                System.out.println(textExept);
            }
            assertEquals(materialBI, decryptBI);
        }
    }
}