package lab_3;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class GostTest {

        @Test
        public void sharedTEST() throws Exception {
                Gost personaGost = new Gost();
                File messageFile = new File("tests_files/lab_3/Gost/test_md5.txt");
                File place_of_digital_signature__File =
                                new File("tests_files/lab_3/Gost/test_md5_gost_signature.txt");
                personaGost.create_digital_signature(
                                messageFile,
                                place_of_digital_signature__File
                );

                assertTrue(personaGost.check_digital_signature(
                                messageFile,
                                place_of_digital_signature__File,
                                personaGost
                ));
        }
}