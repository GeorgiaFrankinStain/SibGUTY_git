package lab_2;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class CryptoAbonentTest {
        private CryptoAbonent[] arrayAllTypeCryptAbon () {

                CryptoAbonent[] tArrCryptAbon = {
                                new ElGamal(),
//                                new RSA(),
//                                new AdiShamir(),
//                                new Vernam()
                };
                return tArrCryptAbon;
        }
        @Test
        public void file_encrypt_and_decrypt() throws Exception {

                CryptoAbonent[] senderArrCryptAbon = arrayAllTypeCryptAbon();
                CryptoAbonent[] revipientArrCryptAbon = arrayAllTypeCryptAbon();


                for (int i = 0; i < senderArrCryptAbon.length; i++) {
                        CryptoAbonent sender_current__CryptAbon = senderArrCryptAbon[i];
                        CryptoAbonent recipient_current__CryptAbon = revipientArrCryptAbon[i];

                        sender_current__CryptAbon._self_send_create_shared_data(recipient_current__CryptAbon);









                        File inputFile = new File(
                                        "tests_files/lab_2/_general.txt"
                        );
                        writeUsingOutputStream("test_text", inputFile);
                        File encryptFile = new File(
                                        "tests_files/lab_2/"
                                                        + recipient_current__CryptAbon.getClass().getSimpleName()
                                                        + "_encrypt_CryptoAbonentTest.txt"
                        );
                        File decryptFile = new File(
                                        "tests_files/lab_2/"
                                                        + recipient_current__CryptAbon.getClass().getSimpleName()
                                                        + "_decrypt_CryptoAbonentTest.txt"
                        );


                        sender_current__CryptAbon.file_encrypt_for(
                                        inputFile,
                                        encryptFile,
                                        recipient_current__CryptAbon
                        );

                        sender_current__CryptAbon.file_decrypt_for(
                                        encryptFile,
                                        decryptFile,
                                        recipient_current__CryptAbon
                        );














                        File input_image__File = new File(
                                        "tests_files/lab_2/_general.jpg"
                        );
                        File encrypt_image__File = new File(
                                        "tests_files/lab_2/"
                                                        + recipient_current__CryptAbon.getClass().getSimpleName()
                                                        + "_encrypt_image_CryptoAbonentTest.bin"
                        );
                        File decrypt_image__File = new File(
                                        "tests_files/lab_2/"
                                                        + recipient_current__CryptAbon.getClass().getSimpleName()
                                                        + "_decrypt_image_CryptoAbonentTest.jpg"
                        );
                        sender_current__CryptAbon.file_encrypt_for(
                                        input_image__File,
                                        encrypt_image__File,
                                        recipient_current__CryptAbon
                        );

                        sender_current__CryptAbon.file_decrypt_for(
                                        encrypt_image__File,
                                        decrypt_image__File,
                                        recipient_current__CryptAbon
                        );
                }
                //FIXME добавить сравнение файлов на одинаковость
        }


        // пишем в файл с помощью OutputStream
        private static void writeUsingOutputStream(String data, File inputFile) {
                OutputStream os = null;
                try {
                        os = new FileOutputStream(inputFile);
                        os.write(data.getBytes(), 0, data.length());
                } catch (IOException e) {
                        e.printStackTrace();
                }finally{
                        try {
                                os.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
}