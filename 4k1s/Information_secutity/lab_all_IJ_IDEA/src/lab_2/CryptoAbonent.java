package lab_2;

import lab_3.DigitalSignature;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;


public interface CryptoAbonent {
        void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception;

        void get_public_data(File output_for_data__File) throws IOException;
        void get_secret_data(File output_for_data__File) throws IOException;

        public ArrayList<BigInteger> get_public_data(); //LINK8798679665 правильно ли делать этот интерфейс здесь?

        void _set_receiv_shared_data(Object input_key__byte) throws Exception;

        public BigInteger encrypt(
                        BigInteger material_encrypt__BI,
                        CryptoAbonent communicatorCryptoAbonent
        ) throws Exception;


        public BigInteger decrypt(BigInteger material_decript__BI) throws Exception;


        default public ArrayList<BigInteger> encrypt_in_ArrList(
                        BigInteger material_encrypt__BI,
                        CryptoAbonent communicatorCryptoAbonent) throws Exception {

                //k = rand()   1 <= k <= p - 2
                //r = g^k mod p
                //e = m * Db^k mod p
                return new ArrayList<BigInteger>();
//                ArrayList<BigInteger> r_and_mes_crypt__ArrListBI = new ArrayList<BigInteger>();
//                return r_and_mes_crypt__ArrListBI;
        }
        default public BigInteger decrypt(ArrayList<BigInteger> r_and_mes_crypt__ArrListBI) {
                return null;
                //m_decrypt = e * r^(p - 1 - Cb) mod p
        }
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

        default public void file_encrypt_for(
                        File inputFile,
                        File outputFile,
                        CryptoAbonent recipientCrypAbon) throws Exception {

                CryptoAbonent senderCryptAbon = this;
                FileInputStream input = null;
                input = new FileInputStream(inputFile);
                FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);


                senderCryptAbon._self_send_create_shared_data(recipientCrypAbon);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outFileOutputStream);
                objectOutputStream.writeLong(inputFile.length());

                int current_byte = -1;
                while ((current_byte = input.read()) != -1) {

                        char current__char = (char) current_byte;

                        //возможно применение паттерна цепочка
                        BigInteger encryptBI = recipientCrypAbon.encrypt(
                                        BigInteger.valueOf(current_byte),
                                        senderCryptAbon
                        );
                        objectOutputStream.writeObject(encryptBI);
                }

                objectOutputStream.close();
        }

        default public void file_decrypt_for(
                        File inputFile,
                        File outputFile,
                        CryptoAbonent recipientCrypAbon) throws Exception {

                CryptoAbonent senderCryptAbon = this;


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
                outFileOutputStream.close();
        }
}