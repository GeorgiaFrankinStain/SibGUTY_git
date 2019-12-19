package lab_5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyMd5 {
        static public byte[] getFileChecksum(MessageDigest digest, File file) throws IOException {
                //Get file input stream for reading the file content
                FileInputStream fis = new FileInputStream(file);

                //Create byte array to read data in chunks
                byte[] byteArray = new byte[1024];
                int bytesCount = 0;

                //Read file data and update in message digest
                while ((bytesCount = fis.read(byteArray)) != -1) {
                        digest.update(byteArray, 0, bytesCount);
                }
                ;

                //close the stream; We don't need it now.
                fis.close();

                //Get the hash's bytes
                byte[] bytes = digest.digest();

                return bytes;
        }

        static public String md5_hash(File inputFile) throws NoSuchAlgorithmException, IOException {
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                String checksumStr = getFileChecksumStr(md5Digest, inputFile);

                return checksumStr.toLowerCase();
        }

        static public String md5CustomStr(String st) {
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

                while (md5Hex.length() < 32) {
                        md5Hex = "0" + md5Hex;
                }

                return md5Hex;
        }

        static public String getFileChecksumStr(MessageDigest digest, File file) throws IOException {
                //Get file input stream for reading the file content
                FileInputStream fis = new FileInputStream(file);

                //Create byte array to read data in chunks
                byte[] byteArray = new byte[1024];
                int bytesCount = 0;

                //Read file data and update in message digest
                while ((bytesCount = fis.read(byteArray)) != -1) {
                        digest.update(byteArray, 0, bytesCount);
                }
                ;

                //close the stream; We don't need it now.
                fis.close();

                //Get the hash's bytes
                byte[] bytes = digest.digest();

                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }

                //return complete hash
                return sb.toString();
        }
}
