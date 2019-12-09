package lab_3;

import lab_2.ElGamal;
import lab_2.RSA;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DigitalSignatureTest {

        @Test
        public void sharedTEST() throws Exception {
                DigitalSignature personaDigitalSignature = new RSA();
//                DigitalSignature personaDigitalSignature = new ElGamal();
                personaDigitalSignature._self_send_create_shared_data(null);




                BigInteger material_for_crypt__BI = BigInteger.valueOf(143); //Я толком не понял, что сюда подавать нужно
                ArrayList<BigInteger> material_for_decrypt__ArrayListBI =
                                personaDigitalSignature.secret_key_encrypt(material_for_crypt__BI);




                ArrayList<BigInteger> public_data_of_verifiable__ArrayListBI =
                                personaDigitalSignature.get_public_data();


                personaDigitalSignature.public_key_compensation_secret_key(
                        material_for_decrypt__ArrayListBI,
                        public_data_of_verifiable__ArrayListBI
                );
                personaDigitalSignature._self_send_create_shared_data(null);



                File messageFile = new File("tests_files/lab_3/DigitalSignature/test_md5.txt");
                File output_for_signature__File = new File("tests_files/lab_3/DigitalSignature/test_md5_signature.txt");


                DigitalSignature.digital_signature(
                                messageFile,
                                output_for_signature__File,
                                personaDigitalSignature
                );

                assertTrue(DigitalSignature.check_author_persona(
                                messageFile,
                                output_for_signature__File,
                                personaDigitalSignature)
                );
        }
}