package lab_3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigitalPunkRock {/*
    public void digital_signature (File inputFile, File outputFile) {

    }*/
    static public byte[] md5_hash (File inputFile) throws NoSuchAlgorithmException, IOException {
        String address_of_file__Str = "tests_files/lab_3/test_md5.txt";/*

        File f = new File(address_of_file__Str);
        System.out.println(f.exists() && !f.isDirectory());
*/


        MessageDigest md = MessageDigest.getInstance("MD5");
        InputStream is = Files.newInputStream(Paths.get(address_of_file__Str));
//        InputStream is = Files.newInputStream(Paths.get(inputFile.getAbsolutePath()));
        DigestInputStream dis = new DigestInputStream(is, md);
        byte[] digest = md.digest();



        System.out.printf("%h", digest.toString());
        System.out.println();
        System.out.println(digest.length + "test");


//        9050BDDCF415F2D0518804E551C1BE98
        return digest;
    }
}
