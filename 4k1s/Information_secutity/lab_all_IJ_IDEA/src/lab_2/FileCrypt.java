package lab_2;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileCrypt {
    static public void encrypt(
            File inputFile,
            File outputFile,
            CryptoAbonent senderCryptAbon,
            CryptoAbonent recipientCrypAbon) throws Exception {

        FileInputStream input = null;

        input = new FileInputStream(inputFile);

        FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);


        senderCryptAbon._self_send_create_shared_data(recipientCrypAbon);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outFileOutputStream);
        objectOutputStream.writeLong(inputFile.length());


        int current_byte = -1;
        while ((current_byte = input.read()) != -1) {

            char current__char = (char) current_byte;
            BigInteger encryptBI = recipientCrypAbon.encrypt(
                    BigInteger.valueOf(current_byte),
                    senderCryptAbon
            );

            objectOutputStream.writeObject(encryptBI);
        }
    }
    static public void decrypt(
            File inputFile,
            File outputFile,
            CryptoAbonent senderCryptAbon,
            CryptoAbonent recipientCrypAbon) throws Exception {

        FileInputStream inFileInputStream = new FileInputStream(inputFile);
        FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);



        ObjectInputStream inObjectInputStream = new ObjectInputStream(inFileInputStream);
        long count_of_big_integer__long = inObjectInputStream.readLong();


        for (long i__long = 0; i__long < count_of_big_integer__long; i__long++) {

            BigInteger encrypt_from_file__BI = (BigInteger) inObjectInputStream.readObject();
            BigInteger decryptBI = senderCryptAbon.decrypt(encrypt_from_file__BI);
            char output__char = (char) decryptBI.intValue();
            outFileOutputStream.write(output__char);
        }
    }
}
