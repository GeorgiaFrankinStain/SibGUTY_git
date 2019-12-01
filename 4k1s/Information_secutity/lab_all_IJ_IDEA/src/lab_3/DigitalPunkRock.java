package lab_3;

import lab_2.CryptoAbonent;
import lab_2.FileCrypt;
import lab_2.RSA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.sun.deploy.util.SystemUtils.getFileChecksum;


public class DigitalPunkRock {
    public void digital_signature (File inputFile, File output_for_signature__File) throws Exception {
        /*String md5Str = md5_hash(inputFile);

        //записываем в файл эту строку

        //шифруемм файл откртым замком при помощи RSA

        CryptoAbonent personaCryptAbon = new RSA();
        CryptoAbonent recipientCrypAbon = null;

        FileCrypt.encrypt(
                inputFile,
                output_for_signature__File,
                personaCryptAbon,
                recipientCrypAbon
        );*/
    }





    static public String md5_hash (File inputFile) throws NoSuchAlgorithmException, IOException {
        String address_of_file__Str = "tests_files/lab_3/test_md5.txt";
        File inFile = new File(address_of_file__Str);
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        String checksumStr = getFileChecksum(md5Digest, inFile);

        return checksumStr.toLowerCase();
    }
    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
