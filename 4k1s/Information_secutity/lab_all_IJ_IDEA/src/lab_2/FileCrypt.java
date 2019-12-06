package lab_2;

import lab_3.DigitalPunkRock;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FileCrypt {
//FIXME NO NOW итератор можно добавить
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

static public void secret_key_encription(
                File inputFile,
                File outputFile,
                RSA personaRSA
) throws Exception {

        String md5Str = DigitalPunkRock.md5_hash(inputFile);

        StringCrypt.secret_key_encrypt(md5Str, outputFile, personaRSA);
}


static public boolean check_author_persona(
                File messageFile,
                File crypt_hash__File,
                RSA verifiaty_persona__CryptAbon
) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {

        String md5_of_message__Str = DigitalPunkRock.md5_hash(messageFile);


        FileInputStream crypt_hash__FileInputStream = new FileInputStream(crypt_hash__File);


        ObjectInputStream inObjectInputStream = new ObjectInputStream(crypt_hash__FileInputStream);

        long count_of_big_integer__long = inObjectInputStream.readLong();

        String decrypt_hash__Str = "";
        for (long i__long = 0; i__long < count_of_big_integer__long; i__long++) {

                BigInteger encrypt_from_file__BI = (BigInteger) inObjectInputStream.readObject();
                BigInteger decryptBI = RSA.public_key_compensation_secret_key(
                                encrypt_from_file__BI,
                                verifiaty_persona__CryptAbon.getdBI(),
                                verifiaty_persona__CryptAbon.getnBI()
                );
                char output__char = (char) decryptBI.intValue();
                decrypt_hash__Str += output__char;
        }


        return decrypt_hash__Str.equals(md5_of_message__Str);
}
}
