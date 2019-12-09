package lab_2;

import lab_1.MyBigInteger;
import lab_1.PrimeNumber;
import lab_3.DigitalSignature;

import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class ElGamal implements CryptoAbonent, DigitalSignature {
        final int range_for_biginteger = 10000;
        final BigInteger glob_range__BI = BigInteger.valueOf(range_for_biginteger);

        private BigInteger pBI, gBI, my_pub_key__BI, key_pub_of_interlocutor__BI;
        private BigInteger my_secret_key__BI;


        @Override
        public void get_public_data(File output_for_data__File) throws IOException {
                FileWriter outFileWriter = new FileWriter(output_for_data__File, false);
                outFileWriter.write("pBI: " + this.pBI.toString() + "\n");
                outFileWriter.write("gBI: " + this.gBI.toString() + "\n");
                outFileWriter.write("my_pub_key__BI: " + this.my_pub_key__BI.toString() + "\n");
                outFileWriter.write("key_pub_of_interlocutor__BI: " + this.key_pub_of_interlocutor__BI.toString() + "\n");
                outFileWriter.close();
        }

        @Override
        public void get_secret_data(File output_for_data__File) throws IOException {
                FileWriter outFileWriter = new FileWriter(output_for_data__File, false);
                outFileWriter.write("my_secret_key__BI: " + this.my_secret_key__BI.toString() + "\n");
                outFileWriter.close();
        }

@Override
public ArrayList<BigInteger> get_public_data() {
        return null;
}

        @Override
        public void _self_send_create_shared_data(CryptoAbonent communicatorCryptoAbonent) throws Exception {
                this.pBI = PrimeNumber.generate(glob_range__BI);


                Random tRandom = new Random();
                this.gBI = new BigInteger(glob_range__BI.bitLength(), tRandom).mod(this.pBI.subtract(BigInteger.valueOf(2)));


                //секретный ключ
                // 1 < my_secret_key__BI < p - 1
                this.my_secret_key__BI = MyBigInteger.generae_diapason(
                                BigInteger.valueOf(2),
                                this.pBI.subtract(BigInteger.ONE));


                //публичный ключ
                this.my_pub_key__BI = this.gBI.modPow(my_secret_key__BI, pBI);


                //отправляем публичный ключ и общие данные
                ArrayList<BigInteger> sending_shared__ArrListBI = new ArrayList<BigInteger>();
                sending_shared__ArrListBI.add(this.gBI);
                sending_shared__ArrListBI.add(this.pBI);
                sending_shared__ArrListBI.add(this.my_pub_key__BI);
                communicatorCryptoAbonent._set_receiv_shared_data(sending_shared__ArrListBI);

        }

        @Override
        public void _set_receiv_shared_data(Object input_shared_data__Object) {
                ArrayList<BigInteger> input_shared_data__ArrLIstBI = (ArrayList<BigInteger>) input_shared_data__Object;
                this.gBI = input_shared_data__ArrLIstBI.get(0);
                this.pBI = input_shared_data__ArrLIstBI.get(1);
                this.key_pub_of_interlocutor__BI = input_shared_data__ArrLIstBI.get(2);
        }

        @Override
        public BigInteger encrypt(BigInteger material_encrypt__BI, CryptoAbonent communicatorCryptoAbonent) {
                return null;
        }

        @Override
        public ArrayList<BigInteger> encrypt_in_ArrList(
                        BigInteger material_encrypt__BI,
                        CryptoAbonent communicatorCryptoAbonent) throws Exception {
                // 1 <= k <= p - 2
                BigInteger kBI = MyBigInteger.generae_diapason(
                                BigInteger.valueOf(1),
                                this.pBI.subtract(BigInteger.valueOf(2))
                );


                // r = g^k mod p
                BigInteger rBI = this.gBI.modPow(kBI, this.pBI);

                if (this.key_pub_of_interlocutor__BI == null) { //FIXME NOW невероятно жесткий ксотыль, я абсолютно не понимаю, что просиходит
                        this.key_pub_of_interlocutor__BI = this.my_pub_key__BI;
                }
                // m * key_pub_of_interlocutor^k mod p
                BigInteger m_crypt__BI = material_encrypt__BI.multiply(this.key_pub_of_interlocutor__BI.modPow(kBI, this.pBI));

                ArrayList<BigInteger> resArrListBI = new ArrayList<BigInteger>();
                resArrListBI.add(rBI);
                resArrListBI.add(m_crypt__BI);

                return resArrListBI;
        }


        @Override
        public BigInteger decrypt(BigInteger material_decript__BI) {
                return null;
        }

        @Override
        public BigInteger decrypt(ArrayList<BigInteger> r_and_mes_crypt__ArrListBI) {
                BigInteger rBI = r_and_mes_crypt__ArrListBI.get(0);
                BigInteger mes_crypt__BI = r_and_mes_crypt__ArrListBI.get(1);

                //mes_crypt * r^(p - 1 - my_secret_key) mod p
                BigInteger p_m_1_m_my_secret_key__BI = this.pBI.subtract(BigInteger.ONE).subtract(my_secret_key__BI);
                BigInteger mesBI = mes_crypt__BI.multiply(rBI.modPow(p_m_1_m_my_secret_key__BI, this.pBI)).mod(this.pBI);

                return mesBI;
        }

        @Override
        public void file_encrypt_for(
                        File inputFile,
                        File outputFile,
                        CryptoAbonent recipientCrypAbon) throws Exception {
                CryptoAbonent senderCryptAbon = this;
                FileInputStream input = new FileInputStream(inputFile);
                FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);


                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outFileOutputStream);
                objectOutputStream.writeLong(inputFile.length());

                int current_byte = -1;
                while ((current_byte = input.read()) != -1) {

                        char current__char = (char) current_byte;


                        ArrayList<BigInteger> encryptArrListBI = encrypt_in_ArrList(
                                        BigInteger.valueOf(current_byte),
                                        senderCryptAbon
                        );
                        objectOutputStream.writeObject(encryptArrListBI);
                }

                objectOutputStream.close();
        }


        @Override
        public void file_decrypt_for(
                        File inputFile,
                        File outputFile,
                        CryptoAbonent recipientCrypAbon) throws Exception {

                CryptoAbonent senderCryptAbon = this;


                FileInputStream inFileInputStream = new FileInputStream(inputFile);
                FileOutputStream outFileOutputStream = new FileOutputStream(outputFile);


                ObjectInputStream inObjectInputStream = new ObjectInputStream(inFileInputStream);

                long count_of_big_integer__long = inObjectInputStream.readLong();


                for (long i__long = 0; i__long < count_of_big_integer__long; i__long++) {

                        ArrayList<BigInteger> encrypt_from_file__ArrListBI =
                                        (ArrayList<BigInteger>) inObjectInputStream.readObject();
                        BigInteger decryptBI = senderCryptAbon.decrypt(encrypt_from_file__ArrListBI);
                        char output__char = (char) decryptBI.intValue();
                        outFileOutputStream.write(output__char);
                }
        //            System.out.println("ia tut bil med pivo pil"); //DEBUG_DELETE
        }

@Override
public ArrayList<BigInteger> secret_key_encrypt(BigInteger material_for_crypt__BI) {
        return null;
}

@Override
public BigInteger public_key_compensation_secret_key(ArrayList<BigInteger> material_for_decrypt__ArrayListBI, ArrayList<BigInteger> public_data_of_verifiable__ArrayListBI) {
        return null;
}
}
