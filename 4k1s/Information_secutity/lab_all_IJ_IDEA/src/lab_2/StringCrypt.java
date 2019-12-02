package lab_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public class StringCrypt {
    static public void secret_key_encrypt(
            String materialStr,
            File outputFile,
            RSA personaRSA) throws Exception {

        FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outFileOutputStream);
        objectOutputStream.writeLong(materialStr.length());




        for (int i = 0; i < materialStr.length(); i++) {
            char current__char = materialStr.charAt(i);

            BigInteger encryptBI = personaRSA.secret_key_encrypt(BigInteger.valueOf((int) current__char));

            objectOutputStream.writeObject(encryptBI);
        }
    }
}
