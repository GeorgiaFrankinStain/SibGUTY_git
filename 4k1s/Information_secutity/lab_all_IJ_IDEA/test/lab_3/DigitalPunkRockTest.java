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
        String actualStr = DigitalPunkRock.md5_hash(testFile);
        String expectedStr = "9050bddcf415f2d0518804e551c1be98";
        assertEquals(expectedStr, actualStr);
    }
}