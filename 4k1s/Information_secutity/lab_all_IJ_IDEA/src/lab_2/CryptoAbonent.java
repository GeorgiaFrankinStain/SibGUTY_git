package lab_2;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;


public interface CryptoAbonent {
        enum MethodWrite {
                BIG_INTEGER,
                ARR_LIST_BIG_INTEGER
        }



        void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception;

        void _set_receiv_shared_data(Object input_key__byte) throws Exception;

        public BigInteger encrypt(
                        BigInteger material_encrypt__BI,
                        CryptoAbonent communicatorCryptoAbonent
        ) throws Exception;


        public BigInteger decrypt(BigInteger material_decript__BI) throws Exception;


        default public ArrayList<BigInteger> encrypt_in_ArrList(
                        BigInteger material_encrypt__BI,
                        CryptoAbonent communicatorCryptoAbonent) throws Exception {
                return new ArrayList<BigInteger>();
        }


        default public void file_encrypt(
                        File inputFile,
                        File outputFile,
                        CryptoAbonent recipientCrypAbon) throws Exception {

                CryptoAbonent senderCryptAbon = this;

                FileInputStream input = null;

                input = new FileInputStream(inputFile);

                FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);


                senderCryptAbon._self_send_create_shared_data(recipientCrypAbon);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outFileOutputStream);
                byte[] temp__arr_byte = new byte[1];
                objectOutputStream.write(temp__arr_byte, 0, 1); //оставляем пустое место для типа данных сериализации
                objectOutputStream.writeLong(inputFile.length());


                int current_byte = -1;
                while ((current_byte = input.read()) != -1) {

                        char current__char = (char) current_byte;

                        //возможно применение паттерна цепочка
                        BigInteger encryptBI = recipientCrypAbon.encrypt(
                                        BigInteger.valueOf(current_byte),
                                        senderCryptAbon
                        );
                        if (encryptBI != null) {
                                objectOutputStream.writeObject(encryptBI);
                                continue;
                        }




                        //обработку более широким типом данных
                        ArrayList<BigInteger> encryptArrListBI = encrypt_in_ArrList(
                                        BigInteger.valueOf(current_byte),
                                        senderCryptAbon
                        );
                        objectOutputStream.writeObject(encryptArrListBI);
                }
                byte[] type_writing__arr_byte = new byte[1];
                int test = (int) CryptoAbonent.MethodWrite.ARR_LIST_BIG_INTEGER.ordinal();;
                type_writing__arr_byte[0] = (byte) test;
                objectOutputStream.write((byte[]) type_writing__arr_byte, 0, 1); //записываем тип данных сериализации
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
        }
}