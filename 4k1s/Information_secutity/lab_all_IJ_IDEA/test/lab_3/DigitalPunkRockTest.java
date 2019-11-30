package lab_3;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class DigitalPunkRockTest {

    @Test
    public void md5_hash() throws IOException, NoSuchAlgorithmException {
        File testFile = new File("test_files/lab_3/test_md5.txt");
        byte[] actual__Arr_byte = DigitalPunkRock.md5_hash(testFile);

//        9050BDDCF415F2D0518804E551C1BE98
    }
}