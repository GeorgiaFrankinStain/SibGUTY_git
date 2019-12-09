package lab_3;

import lab_2.CryptoAbonent;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface DigitalSignature extends CryptoAbonent {
        public ArrayList<BigInteger> secret_key_encrypt(BigInteger material_for_crypt__BI);



        static public void secret_key_encrypt(
                        String materialStr,
                        File outputFile,
                        DigitalSignature personaDigitalSignature) throws Exception {

                FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outFileOutputStream);
                objectOutputStream.writeLong(materialStr.length());




                for (int i = 0; i < materialStr.length(); i++) {
                        char current__char = materialStr.charAt(i);

                        ArrayList<BigInteger> encryptArrayListBI = personaDigitalSignature.secret_key_encrypt(BigInteger.valueOf((int) current__char));

                        objectOutputStream.writeObject(encryptArrayListBI);
                }
        }

        static public void secret_key_encription(
                File inputFile,
                File outputFile,
                DigitalSignature personaDigitalSignature
        ) throws Exception {

                String md5Str = DigitalSignature.md5_hash(inputFile);

                DigitalSignature.secret_key_encrypt(md5Str, outputFile, personaDigitalSignature);
        }




        BigInteger public_key_compensation_secret_key(
                        ArrayList<BigInteger> material_for_decrypt__ArrayListBI,
                        ArrayList<BigInteger> public_data_of_verifiable__ArrayListBI
        );

        static boolean check_author_persona( //static po syti
                        File messageFile,
                        File crypt_hash__File,
                        DigitalSignature verifiaty_persona__DigitalSignature
        ) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
                String md5_of_message__Str = DigitalSignature.md5_hash(messageFile);


                FileInputStream crypt_hash__FileInputStream = new FileInputStream(crypt_hash__File);


                ObjectInputStream inObjectInputStream = new ObjectInputStream(crypt_hash__FileInputStream);

                long count_of_big_integer__long = inObjectInputStream.readLong();

                String decrypt_hash__Str = "";
                for (long i__long = 0; i__long < count_of_big_integer__long; i__long++) {

                        ArrayList<BigInteger> encrypt_from_file__ArrayListBI =
                                        (ArrayList<BigInteger>) inObjectInputStream.readObject();


                        ArrayList<BigInteger> public_data_of_verifiable__ArrayListBI
                                        = verifiaty_persona__DigitalSignature.get_public_data();

                        BigInteger decryptBI = verifiaty_persona__DigitalSignature.public_key_compensation_secret_key(
                                        encrypt_from_file__ArrayListBI,
                                        public_data_of_verifiable__ArrayListBI
                        );
                        char output__char = (char) decryptBI.intValue();
                        decrypt_hash__Str += output__char;
                }


                return decrypt_hash__Str.equals(md5_of_message__Str);
        }






        static public void digital_signature(
                        File inputFile,
                        File output_for_signature__File,
                        DigitalSignature personaDigitalSignature) throws Exception {

                String md5Str = md5_hash(inputFile);

                DigitalSignature.secret_key_encription(
                                inputFile,
                                output_for_signature__File,
                                personaDigitalSignature
                );        }


        static public String md5_hash(File inputFile) throws NoSuchAlgorithmException, IOException {
                MessageDigest md5Digest = MessageDigest.getInstance("MD5");
                String checksumStr = getFileChecksum(md5Digest, inputFile);

                return checksumStr.toLowerCase();
        }

        static public String md5Custom(String st) {
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

        static public String getFileChecksum(MessageDigest digest, File file) throws IOException {
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
