package lab_3;

import lab_2.CryptoAbonent;
import lab_2.FileCrypt;
import lab_2.RSA;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class DigitalPunkRockTest {

    @Test
    public void md5_hash() throws IOException, NoSuchAlgorithmException {
        File testFile = new File("test_files/lab_3/test_md5.txt");
        String actualStr = DigitalPunkRock.md5_hash(testFile);
        String expectedStr = "9050bddcf415f2d0518804e551c1be98";
        assertEquals(expectedStr, actualStr);
    }

    @Test
    public void digital_signature() throws Exception {
        File messageFile = new File("tests_files/lab_3/test_md5.txt");
        File output_for_signature__File = new File("tests_files/lab_3/test_md5_signature.txt");
        output_for_signature__File.createNewFile();


        RSA personaRSA = new RSA();
        personaRSA._self_send_create_shared_data(null);

        DigitalPunkRock.digital_signature(
                messageFile,
                output_for_signature__File,
                personaRSA
        );

        assertTrue(FileCrypt.check_author_persona(
                messageFile,
                output_for_signature__File,
                personaRSA)
        );
    }
}