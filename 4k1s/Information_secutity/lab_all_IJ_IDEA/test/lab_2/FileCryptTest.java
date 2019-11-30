package lab_2;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FileCryptTest {

    @Test
    public void SHARED_TEST() throws Exception {
        CryptoAbonent senderCryptAbon = new RSA();
        CryptoAbonent recipientCrypAbon = new RSA();
        {
            File inputFile = new File("files/lab2/input.bin");
            File outputFile = new File("files/lab2/encrypt.bin");
            FileCrypt.encrypt(
                    inputFile,
                    outputFile,
                    senderCryptAbon,
                    recipientCrypAbon
            );
        }

        {
            File inputFile = new File("files/lab2/encrypt.bin");
            File outputFile = new File("files/lab2/decrypt.bin");
            FileCrypt.decrypt(
                    inputFile,
                    outputFile,
                    senderCryptAbon,
                    recipientCrypAbon
            );
        }


        //FIXME test equals file
    }
}